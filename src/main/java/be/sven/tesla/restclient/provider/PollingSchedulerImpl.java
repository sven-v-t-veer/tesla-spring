package be.sven.tesla.restclient.provider;

import be.sven.tesla.core.ChargingState;
import be.sven.tesla.core.DriveState;
import be.sven.tesla.core.Token;
import be.sven.tesla.core.VehicleData;
import be.sven.tesla.exception.InvalidParameterException;
import be.sven.tesla.exception.TaskNotFoundException;
import be.sven.tesla.restclient.PollingScheduler;
import be.sven.tesla.restclient.VehicleClient;
import be.sven.tesla.restclient.VehicleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class PollingSchedulerImpl implements PollingScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollingSchedulerImpl.class);

    private final DataClientImpl dataClient;
    private final VehicleClient vehicleClient;
    private final ThreadPoolTaskScheduler scheduler;
    private final VehicleControl vehicleControl;

    private final Map<Long, PollingTask> tasks = new IdentityHashMap<>();

    @Autowired
    public PollingSchedulerImpl(DataClientImpl dataClient, VehicleClient vehicleClient, @Qualifier("TaskScheduler") ThreadPoolTaskScheduler scheduler,
                                VehicleControl vehicleControl) {
        this.dataClient = dataClient;
        this.vehicleClient = vehicleClient;
        this.scheduler = scheduler;
        this.vehicleControl = vehicleControl;
    }

    @Override
    public void scheduleVehiclePolling(Token token, Long id, boolean wakeup) {
        PollingTask task = new PollingTask(token, id, wakeup);
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 30000);
        task.setFuture(future);
        tasks.put(id, task);
    }

    @Override
    public boolean cancelVehiclePolling(Long id) throws InvalidParameterException, TaskNotFoundException {
        if (id == null) throw new InvalidParameterException("VehicleId should not be null");
        PollingTask task = tasks.get(id);
        if (task == null) throw new TaskNotFoundException(String.format("Task not found for Vehicle: %s", id));
        ScheduledFuture<?> future = task.getFuture();
        future.cancel(true);
        boolean canceled = future.isCancelled();
        if (canceled) {
            task.setFuture(null);
            tasks.remove(id);
        }
        return canceled;
    }

    @Override
    public boolean isWakeupForVehicle(Long id) throws InvalidParameterException, TaskNotFoundException {
        if (id == null) throw new InvalidParameterException("VehicleId should not be null");
        PollingTask task = tasks.get(id);
        if (task == null) throw new TaskNotFoundException(String.format("Task not found for Vehicle: %s", id));
        return task.isWakeup();
    }

    @Override
    public boolean setWakeupForVehicle(Long id, boolean wakeup) throws InvalidParameterException, TaskNotFoundException {
        if (id == null) throw new InvalidParameterException("VehicleId should not be null");
        PollingTask task = tasks.get(id);
        if (task == null) throw new TaskNotFoundException(String.format("Task not found for Vehicle: %s", id));
        if (wakeup == task.isWakeup()) return false;
        task.setWakeup(wakeup);
        return true;
    }

    enum State {
        ONLINE,
        CAN_SLEEP,
        ASLEEP
    }

    class PollingTask implements Runnable {
        private final Token token;
        private final Long id;
        private boolean wakeup;
        private State state = State.ASLEEP;
        private ScheduledFuture<?> future;

        public PollingTask(final Token token, final Long id, final boolean wakeup) {
            this.token = token;
            this.id = id;
            this.wakeup = wakeup;
        }

        public ScheduledFuture<?> getFuture() {
            return future;
        }

        public void setFuture(ScheduledFuture<?> future) {
            this.future = future;
        }

        public boolean isWakeup() {
            return wakeup;
        }

        public void setWakeup(boolean wakeup) {
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
                if (canSleep(data)) state = State.CAN_SLEEP;
                return;
            }
            LOGGER.info("Car is not online. Not waking it up.");
        }

        private boolean canSleep(VehicleData data) {
            ChargingState chargingState = ChargingState.valueOf(data.getChargeState().getChargingState().toUpperCase());
            DriveState driveState = data.getDriveState();
            boolean climateOn = data.getClimateState().isClimateOn();
            if (!wakeup && chargingState.willNotCharge() && driveState.isParked() && !climateOn) { // need other states for charging
                LOGGER.info("Vehicle should be able to go to sleep");
                return true;
            }
            return false;
        }

    }
}
