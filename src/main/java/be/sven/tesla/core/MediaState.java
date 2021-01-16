package be.sven.tesla.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MediaState {
    @JsonProperty("remote_control_enabled")
    private boolean remoteControlEnabled;
}
