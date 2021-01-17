package be.sven.tesla.core;

public enum VehicleActiveState {
    UNKNOWN,
    ONLINE, // vehicle is online
    DOZING_OFF, // there have been no state changes for a while
    ASLEEP // vehicle is asleep
}
