package be.sven.tesla.spi;

import be.sven.tesla.model.*;

public interface DataClient {

    VehicleData getVehicleData(Token token, Long id);
    VehicleState getVehicleState(Token token, Long id);
    ChargeState getChargeState(Token token, Long id);
    ClimateState getClimateState(Token token, Long id);
    DriveState getDriveState(Token token, Long id);
    GUISettings getGuiSettings(Token token, Long id);
}
