package be.sven.tesla.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParkedState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkedState.class);
    private boolean parked = true;
    private boolean climateOn = false;
    private ChargingState chargingState = ChargingState.DISCONNECTED;

    public boolean setChargingState(ChargingState newState) {
        if (newState == chargingState) {
            return false;
        }
        chargingState = newState;
        return true;
    }

    public ChargingState getChargingState() {
        return chargingState;
    }

    public boolean isParked() {
        return parked;
    }

    public boolean isActive() {
        if (climateOn) {
            LOGGER.info("Vehicle is active, climate is on");
            return true;
        }
        if (chargingState == ChargingState.CHARGING || chargingState == ChargingState.NOPOWER) {
            LOGGER.info("Vehicle is active, Charging state: {} ", chargingState);
            return true;
        }
        return false;
    }

    public boolean setParked(boolean parked) {
        if (this.parked == parked) return false;
        this.parked = parked;
        return true;
    }

    public boolean isClimateOn() {
        return climateOn;
    }

    public boolean setClimateOn(boolean climateOn) {
        if (this.climateOn == climateOn) return false;
        this.climateOn = climateOn;
        return true;
    }
}
