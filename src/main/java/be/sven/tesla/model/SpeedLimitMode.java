package be.sven.tesla.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpeedLimitMode {
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("current_limit_mph")
    private BigDecimal currentLimitMph;
    @JsonProperty("max_limit_mph")
    private int maxLimitMph;
    @JsonProperty("min_limit_mph")
    private int minLimitMph;
    @JsonProperty("pin_code_set")
    private boolean pincodeSet;
}
