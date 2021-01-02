package be.sven.tesla.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Vehicle {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("vehicle_id")
    private Long vehicleId;
    @JsonProperty("vin")
    private String vin;
    @JsonProperty("display_name")
    private String name;
    @JsonProperty("option_codes")
    private String optionCodes;
    @JsonProperty("color")
    private String color;
    @JsonProperty("tokens")
    private List<String> tokens;
    @JsonProperty("state")
    private String state;
    @JsonProperty("in_service")
    private boolean inService;
    @JsonProperty("id_s")
    private String ids;
    @JsonProperty("calendar_enabled")
    private boolean calendarEnabled;
    @JsonProperty("api_version")
    private int apiVersion;
    @JsonProperty("backseat_token")
    private String backseatToken;
    @JsonProperty("backseat_token_updated_at")
    private Long backseatTokenUpdatedAt;
}
