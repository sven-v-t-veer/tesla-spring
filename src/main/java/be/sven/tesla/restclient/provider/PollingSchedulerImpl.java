package be.sven.tesla.restclient.provider;

import be.sven.tesla.core.*;
import be.sven.tesla.exception.InvalidParameterException;
import be.sven.tesla.exception.TaskNotFoundException;
import be.sven.tesla.restclient.DataClient;
import be.sven.tesla.restclient.PollingScheduler;
import be.sven.tesla.restclient.VehicleClient;
import be.sven.tesla.restclient.VehicleControl;
import be.sven.tesla.streaming.WssClientConfig;
import com.neovisionaries.ws.client.WebSocket;
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
    private static final String ONLINE = "online";

    private final DataClient dataClient;
    private final VehicleClient vehicleClient;
    private final ThreadPoolTaskScheduler scheduler;
    private final VehicleControl vehicleControl;

    private final Map<Long, PollingTask> tasks = new IdentityHashMap<>();

    @Autowired
    public PollingSchedulerImpl(DataClient dataClient, VehicleClient vehicleClient, @Qualifier("TaskScheduler") ThreadPoolTaskScheduler scheduler,
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


    class PollingTask implements Runnable {
        private final Token token;
        private final Long id;
        private boolean wakeup;
        private VehicleStates state = new VehicleStates();
        private ScheduledFuture<?> future;
        private ChargingState chargingState;
        private boolean climateOn;
        private boolean parked;
        private WebSocket socket;

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
            boolean online = vehicleClient.isVehicleOnline(token, id);
            if (online) state.setOnline();
            else state.setAsleep();
            if (wakeup || state.canPollVehicle()) {
                LOGGER.info("Polling vehicle for data.");
                VehicleData data = dataClient.getVehicleData(token, id);
                state.setStates(data);
                if (data != null && !data.getDriveState().isParked() && socket != null && !socket.isOpen()) {
                    socket = new WssClientConfig().createSocket(token, id, true);
                }
                return;
            }
            LOGGER.info("Car is not online. Not waking it up.");
        }
    }
}
