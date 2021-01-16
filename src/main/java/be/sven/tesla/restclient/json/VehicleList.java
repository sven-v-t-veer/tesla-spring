package be.sven.tesla.restclient.json;

import be.sven.tesla.core.Vehicle;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VehicleList {
    @JsonProperty("response")
    private List<Vehicle> vehicles;
    @JsonProperty("count")
    private int count;
}
