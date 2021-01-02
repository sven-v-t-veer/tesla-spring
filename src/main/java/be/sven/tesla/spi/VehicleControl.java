package be.sven.tesla.spi;

import be.sven.tesla.model.Token;

public interface VehicleControl {

    boolean wakeUp(Token token, Long id);
    boolean honkHorn(Token token, Long id);
    boolean flashLights(Token token, Long id);
    boolean remoteStart(Token token, Long id, String password);
}
