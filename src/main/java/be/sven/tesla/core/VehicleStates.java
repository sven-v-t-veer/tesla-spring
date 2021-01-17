package be.sven.tesla.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class VehicleStates {

    private static final int TIME_TO_SLEEP = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleStates.class);

    private VehicleActiveState activeSate = VehicleActiveState.UNKNOWN;
    private ParkedState parkedState;
    private Instant lastChanged;

    public VehicleStates() {
        lastChanged = Instant.now();
    }

    public void setOnline() {
        Instant now = Instant.now();
        if (activeSate != VehicleActiveState.ONLINE) {
            parkedState = new ParkedState();
            activeSate = VehicleActiveState.ONLINE;
            LOGGER.info("Setting state to ONLINE");
            stateChanged(true);
            return;
        }
        if (activeSate == VehicleActiveState.ONLINE && lastChanged.plus((TIME_TO_SLEEP * 2) + 1, ChronoUnit.MINUTES).isBefore(now)) {
            LOGGER.info("Setting state to ONLINE: Vehicle should be asleep now. What Happend?");
            stateChanged(true);
            return;
        }
        stateChanged(false);
    }

    public void setAsleep() {
        if (activeSate != VehicleActiveState.ASLEEP) {
            parkedState = null;
            activeSate = VehicleActiveState.ASLEEP;
            LOGGER.info("Setting state to ASLEEP");
            stateChanged(true);
        }
        stateChanged(false);
    }

    public void setStates(VehicleData data) {
        if (data != null) {
            if (data.getDriveState().isParked()) {
                switch (ChargingState.valueOf(data.getChargeState().getChargingState().toUpperCase())) {
                    case NOPOWER:
                        setNoPower();
                        break;
                    case CHARGING:
                        setCharging();
                        break;
                    case DISCONNECTED:
                        setDisconnected();
                        break;
                    case COMPLETE:
                        setChargingComplete();
                        break;
                }
                setClimateOn(data.getClimateState().isClimateOn());
            }
        }
    }

    public boolean canPollVehicle() {
        Instant now = Instant.now();
        if (activeSate == VehicleActiveState.ASLEEP) {
            LOGGER.info("Vehicle is asleep");
            return false;
        }
        if (activeSate == VehicleActiveState.ONLINE && parkedState.isActive()) {
            LOGGER.info("Vehicle is online and active");
            return true;
        }
        if (activeSate == VehicleActiveState.ONLINE && lastChanged.plus(TIME_TO_SLEEP, ChronoUnit.MINUTES).isAfter(now)) {
            LOGGER.info("Vehicle in online and not yet going to sleep");
            return true;
        }
        if (activeSate == VehicleActiveState.ONLINE && lastChanged.plus(TIME_TO_SLEEP, ChronoUnit.MINUTES).isBefore(now)) {
            LOGGER.info("Vehicle should be allowed to sleep");
            return false;
        }
        return false;
    }

    public boolean isOnline() {
        return activeSate == VehicleActiveState.ONLINE || activeSate == VehicleActiveState.DOZING_OFF;
    }


    private void setCharging() {
        if (activeSate == VehicleActiveState.ASLEEP) return;
        LOGGER.info("Setting state to charging");
        stateChanged(parkedState.setChargingState(ChargingState.CHARGING));
    }

    private void setDisconnected() {
        if (activeSate == VehicleActiveState.ASLEEP) return;
        LOGGER.info("Setting state to disconnected");
        stateChanged(parkedState.setChargingState(ChargingState.DISCONNECTED));
    }

    private void setNoPower() {
        if (activeSate == VehicleActiveState.ASLEEP) return;
        LOGGER.info("Setting state to no power");
        stateChanged(parkedState.setChargingState(ChargingState.NOPOWER));
    }

    private void setChargingComplete() {
        if (activeSate == VehicleActiveState.ASLEEP) return;
        LOGGER.info("Setting state to charging complete");
        stateChanged(parkedState.setChargingState(ChargingState.COMPLETE));
    }

    private void setClimateOn(boolean on) {
        if (activeSate == VehicleActiveState.ASLEEP) return;
        LOGGER.info("Setting climateOn to {}", on);
        stateChanged(parkedState.setClimateOn(on));
    }

    private void stateChanged(boolean hasChanged) {
        if (hasChanged) {
            LOGGER.info("State has changed");
            lastChanged = Instant.now();
            return;
        }
        LOGGER.info("State has NOT changed");
    }

    protected void setLastChanged(Instant instant) {
        this.lastChanged = instant;
    }

    protected Instant getLastChanged() {
        return lastChanged;
    }
}
