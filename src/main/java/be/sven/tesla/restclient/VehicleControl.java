package be.sven.tesla.restclient;

import be.sven.tesla.core.Token;

public interface VehicleControl {

    boolean wakeUp(Token token, Long id);
    boolean honkHorn(Token token, Long id);
    boolean flashLights(Token token, Long id);
    boolean remoteStart(Token token, Long id, String password);
}
