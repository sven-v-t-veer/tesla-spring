package be.sven.tesla.restclient;

import be.sven.tesla.core.*;

public interface DataClient {

    VehicleData getVehicleData(Token token, Long id);
    VehicleState getVehicleState(Token token, Long id);
    ChargeState getChargeState(Token token, Long id);
    ClimateState getClimateState(Token token, Long id);
    DriveState getDriveState(Token token, Long id);
    GUISettings getGuiSettings(Token token, Long id);
}
