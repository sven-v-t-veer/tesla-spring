package be.sven.tesla.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SoftwareUpdate {
    @JsonProperty("download_perc")
    private int downloadPercentage;
    @JsonProperty("expected_duration_sec")
    private int expectedDurationSeconds;
    @JsonProperty("install_perc")
    private int installPercentage;
    @JsonProperty("status")
    private String status;
    @JsonProperty("version")
    private String version;
}
