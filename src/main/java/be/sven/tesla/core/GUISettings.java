package be.sven.tesla.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GUISettings {
    @JsonProperty("gui_24_hour_time")
    private boolean twentyfourHourTime;
    @JsonProperty("gui_charge_rate_units")
    private String chargeRateUnits;
    @JsonProperty("gui_distance_units")
    private String distanceUnits;
    @JsonProperty("gui_range_display")
    private String rangeDisplay;
    @JsonProperty("gui_temperature_units")
    private String temperatureUnits;
    @JsonProperty("show_range_units")
    private boolean showRangeunits;
    @JsonProperty("timestamp")
    private long timestamp;
}
