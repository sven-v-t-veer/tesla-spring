package be.sven.tesla.restclient;

import be.sven.tesla.core.Token;
import be.sven.tesla.exception.InvalidParameterException;
import be.sven.tesla.exception.TaskNotFoundException;

public interface PollingScheduler {

    void scheduleVehiclePolling(Token token, Long id, boolean wakeup);
    boolean cancelVehiclePolling(Long id) throws InvalidParameterException, TaskNotFoundException;
    boolean setWakeupForVehicle(Long id, boolean wakeup) throws InvalidParameterException, TaskNotFoundException;
    boolean isWakeupForVehicle(Long id) throws InvalidParameterException, TaskNotFoundException;
}
