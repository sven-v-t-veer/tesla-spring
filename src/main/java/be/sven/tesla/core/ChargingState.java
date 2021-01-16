package be.sven.tesla.core;

public enum ChargingState {
    DISCONNECTED("Disconnected"),
    NOPOWER("NoPower"),
    CHARGING("Charging"),
    COMPLETE("Complete");

    private String value;

    ChargingState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public boolean is(String rhs) {
        return rhs != null && rhs.equals(this.value);
    }

    public boolean willNotCharge() {
        return this == DISCONNECTED || this == COMPLETE;
    }
}
