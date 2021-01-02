package be.sven.tesla.spi;

import be.sven.tesla.model.Token;
import be.sven.tesla.model.Vehicle;

import java.util.List;

public interface VehicleClient {

    List<Vehicle> getVehicles(Token token);
    Vehicle getVehicle(Token token, Long id);
}
