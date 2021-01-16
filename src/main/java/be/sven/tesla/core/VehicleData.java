package be.sven.tesla.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
public class VehicleData {
    @MongoId
    @JsonIgnore
    private UUID uuid;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("vehicle_id")
    private Long vehicleId;
//    @JsonProperty("vin")
//    private String vin;
//    @JsonProperty("display_name")
//    private String name;
//    @JsonProperty("option_codes")
//    private String optionCodes;
//    @JsonProperty("color")
//    private String color;
//    @JsonProperty("tokens")
//    private List<String> tokens;
    @JsonProperty("state")
    private String state;
    @JsonProperty("in_service")
    private boolean inService;
//    @JsonProperty("id_s")
//    private String ids;
//    @JsonProperty("calendar_enabled")
//    private boolean calendarEnabled;
//    @JsonProperty("api_version")
    private int apiVersion;
    @JsonProperty("backseat_token")
    private String backseatToken;
    @JsonProperty("backseat_token_updated_at")
    private Long backseatTokenUpdatedAt;
    @JsonProperty("drive_state")
    @Transient
    private DriveState driveState;
    @JsonProperty("climate_state")
    @Transient
    private ClimateState climateState;
    @JsonProperty("charge_state")
    @Transient
    private ChargeState chargeState;
    private long timestamp;
}
