package be.sven.tesla.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DriveState {
    @JsonProperty("gps_as_of")
    private Long gpsAsOf;
    @JsonProperty("heading")
    private BigDecimal heading;
    @JsonProperty("latitude")
    private BigDecimal latitude;
    @JsonProperty("longitude")
    private BigDecimal longitude;
    @JsonProperty("native_latitude")
    private BigDecimal nativeLatitude;
    @JsonProperty("native_location_supported")
    private int nativeLocationSupported;
    @JsonProperty("native_longitude")
    private BigDecimal nativeLongitude;
    @JsonProperty("native_type")
    private String nativeType;
    @JsonProperty("power")
    private int power;
    @JsonProperty("shift_state")
    private Object shiftState;
    @JsonProperty("speed")
    private Integer speed;
    @JsonProperty("timestamp")
    private long timestamp;
}
