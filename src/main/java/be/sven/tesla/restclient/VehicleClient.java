package be.sven.tesla.restclient;

import be.sven.tesla.core.Token;
import be.sven.tesla.core.Vehicle;

import java.util.List;

public interface VehicleClient {

    List<Vehicle> getVehicles(Token token);
    Vehicle getVehicle(Token token, Long id);
    boolean isVehicleOnline(Token token, Long id);
}
