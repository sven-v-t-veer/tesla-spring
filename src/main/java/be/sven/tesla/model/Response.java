package be.sven.tesla.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("result")
    private boolean result;
}
