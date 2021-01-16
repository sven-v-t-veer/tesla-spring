package be.sven.tesla.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
public class ClimateState {
    @MongoId
    @JsonIgnore
    private UUID uuid;
    @JsonIgnore
    private Long id;
    @JsonProperty("battery_heater")
    private boolean batteryHeater;
    @JsonProperty("battery_heater_no_power")
    private boolean batteryHeaterNoPower;
    @JsonProperty("climate_keeper_mode")
    private String climateKeeperMode;
    @JsonProperty("defrost_mode")
    private int defrostMode;
    @JsonProperty("driver_temp_setting")
    private BigDecimal driverTempSetting;
    @JsonProperty("fan_status")
    private int fanStatus;
    @JsonProperty("inside_temp")
    private BigDecimal insideTemp;
    @JsonProperty("is_auto_conditioning_on")
    private boolean autoConditioningOn;
    @JsonProperty("is_climate_on")
    private boolean climateOn;
    @JsonProperty("is_front_defroster_on")
    private boolean frontDefrosterOn;
    @JsonProperty("is_preconditioning")
    private boolean preconditioning;
    @JsonProperty("is_rear_defroster_on")
    private boolean rearDefrosterOn;
    @JsonProperty("left_temp_direction")
    private int leftTempDirection;
    @JsonProperty("max_avail_temp")
    private BigDecimal maxAvailTemp;
    @JsonProperty("min_avail_temp")
    private BigDecimal minAvailTemp;
    @JsonProperty("outside_temp")
    private BigDecimal outsideTemp;
    @JsonProperty("passenger_temp_setting")
    private BigDecimal passengerTempSetting;
    @JsonProperty("remote_heater_control_enabled")
    private boolean remotHeaterControlEnabled;
    @JsonProperty("right_temp_direction")
    private int rightTempDirection;
    @JsonProperty("seat_heater_left")
    private int seatHeaterLeft;
    @JsonProperty("seat_heater_right")
    private int seatHeaterRight;
    @JsonProperty("side_mirror_heaters")
    private boolean sideMirrorHeaters;
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty("wiper_blade_heater")
    private boolean wiperBladeHeater;
}
