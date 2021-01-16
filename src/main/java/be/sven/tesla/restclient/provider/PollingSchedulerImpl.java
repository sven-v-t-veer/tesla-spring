package be.sven.tesla.restclient.provider;

import be.sven.tesla.core.Token;
import be.sven.tesla.core.VehicleData;
import be.sven.tesla.restclient.VehicleClient;
import be.sven.tesla.restclient.VehicleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PollingSchedulerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollingSchedulerImpl.class);

    private final DataClientImpl dataClient;
    private final VehicleClient vehicleClient;
    private final ThreadPoolTaskScheduler scheduler;
    private final VehicleControl vehicleControl;

    private final HashMap<Long, PollingTask> runningTasks = new HashMap<>();

    @Autowired
    public PollingSchedulerImpl(DataClientImpl dataClient, VehicleClient vehicleClient, @Qualifier("TaskScheduler") ThreadPoolTaskScheduler scheduler,
                                VehicleControl vehicleControl) {
        this.dataClient = dataClient;
        this.vehicleClient = vehicleClient;
        this.scheduler = scheduler;
        this.vehicleControl = vehicleControl;
    }

    public void schedulePollingVehicle(Token token, Long id, boolean wakeup) {
        PollingTask pollingTask = new PollingTask(token, id, wakeup);
        scheduler.scheduleAtFixedRate(new PollingTask(token, id, wakeup), 30000);
    }

    enum State {
        ONLINE,
        CAN_SLEEP,
        ASLEEP
    }

    class PollingTask implements Runnable {
        private final Token token;
        private final Long id;
        private final boolean wakeup;
        private State state = State.ASLEEP;

        public PollingTask(final Token token, final Long id, final boolean wakeup) {
            this.token = token;
            this.id = id;
            this.wakeup = wakeup;
        }

        @Override
        public void run() {
            if (state == State.ASLEEP && vehicleClient.isVehicleOnline(token, id)) {
                LOGGER.info("Car was asleep and is now online;");
                state = State.ONLINE;
            } else if (state == State.ASLEEP && wakeup) {
                LOGGER.info("Car is asleep and we should wake it up");
                vehicleControl.wakeUp(token, id);
                return; //takes 20 seconds to wake up;
            } else if (state == State.CAN_SLEEP) {
                LOGGER.info("The car should be able to go to sleep. Not polling for information");
                return;
            }
            if (state == State.ONLINE) {
                LOGGER.info("Was online was set to true.");
                VehicleData data = dataClient.getVehicleData(token, id);
                String chargingState = data.getChargeState().getChargingState();
                int power = data.getDriveState().getPower();
                boolean climateOn = data.getClimateState().isClimateOn();
                if (!wakeup && "Disconnected".equals(chargingState) && power == 0 && !climateOn) { // need other states for charging
                    LOGGER.info("Vehicle should be able to go to sleep");
                    state = State.CAN_SLEEP;
                }
                return;
            }
            LOGGER.info("Car is not online. Not waking it up.");
        }
    }
}
