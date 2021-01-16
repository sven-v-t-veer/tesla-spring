package be.sven.tesla.core;

public enum Seat {
    DRIVER(0),
    PASSENGER(1),
    RL(2),
    RC(4),
    RR(5);

    Seat(int id) {
        this.id = id;
    }
    private int id;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
