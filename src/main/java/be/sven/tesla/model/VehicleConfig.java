package be.sven.tesla.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VehicleConfig {
    @JsonProperty("can_accept_navigation_requests")
    private boolean canAcceptNavigationRequests;
    @JsonProperty("can_actuate_trunks")
    private boolean canActuateTrunks;
    @JsonProperty("car_special_type")
    private String carSpecialType;
    @JsonProperty("car_type")
    private String carType;
    @JsonProperty("charge_port_type")
    private String chargePortType;
    @JsonProperty("default_charge_to_max")
    private boolean defaultChargeToMax;
    @JsonProperty("ece_restrictions")
    private boolean eceRestrictions;
    @JsonProperty("eu_vehicle")
    private boolean euVehicle;
    @JsonProperty("exterior_color")
    private boolean exteriorColor;
    @JsonProperty("has_air_suspension")
    private boolean airSuspension;
    @JsonProperty("has_ludicrous_mode")
    private boolean ludicrousMode;
    @JsonProperty("motorized_charge_port")
    private boolean motorizedChargePort;
    @JsonProperty("plg")
    private boolean plg;
    @JsonProperty("rear_seat_heaters")
    private int rearSeatHeaters;
    @JsonProperty("rear_seat_type")
    private int rearSeatType;
    @JsonProperty("rhd")
    private boolean rhd;
    @JsonProperty("roof_color")
    private String roofColor;
    @JsonProperty("seat_type")
    private int seatType;
    @JsonProperty("spoiler_type")
    private String spoilerType;
    @JsonProperty("sun_roof_installed")
    private int sunroofinstalled;
    @JsonProperty("third_row_seats")
    private String thirdRowSeats;
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty("trim_badging")
    private String trimBadging;
    @JsonProperty("use_range_badging")
    private boolean useRangeBadging;
    @JsonProperty("wheel_type")
    private String wheelType;
}
