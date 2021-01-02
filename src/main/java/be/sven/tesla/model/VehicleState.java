package be.sven.tesla.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleState {
    @JsonProperty("api_version")
    private int apiVersion;
    @JsonProperty("autopark_state_v2")
    private String autoParkStateV2;
    @JsonProperty("autopark_style")
    private String autoParkStyle;
    @JsonProperty("calendar_supported")
    private boolean calendarSupported;
    @JsonProperty("car_version")
    private String carVersion;
    @JsonProperty("center_display_state")
    private int centerDisplayState;
    @JsonProperty("df")
    private int df;
    @JsonProperty("dr")
    private int dr;
    @JsonProperty("fd_window")
    private int fdWindow;
    @JsonProperty("fp_window")
    private int fpWindow;
    @JsonProperty("ft")
    private int ft;
    @JsonProperty("homelink_device_count")
    private int homelinkDeviceCount;
    @JsonProperty("homelink_nearby")
    private boolean homelinkNearby;
    @JsonProperty("is_user_present")
    private boolean isUserPresent;
    @JsonProperty("last_autopark_error")
    private String lastAutoparkError;
    @JsonProperty("locked")
    private boolean locked;
    @JsonProperty("media_state")
    private MediaState mediaState;
    @JsonProperty("notifications_supported")
    private boolean notificationsSupported;
    @JsonProperty("odometer")
    private BigDecimal odometer;
    @JsonProperty("parsed_calendar_supported")
    private boolean parsedCalendarSupported;
    @JsonProperty("pf")
    private int pf;
    @JsonProperty("pr")
    private int pr;
    @JsonProperty("rd_window")
    private int rdWindow;
    @JsonProperty("remote_start")
    private boolean remoteStart;
    @JsonProperty("remote_start_enabled")
    private boolean remoteStartEnabled;
    @JsonProperty("remote_start_supported")
    private boolean remoteStartSupported;
    @JsonProperty("rp_window")
    private int rpWindow;
    @JsonProperty("rt")
    private int rt;
    @JsonProperty("sentry_mode")
    private boolean sentryMode;
    @JsonProperty("sentry_mode_available")
    private boolean sentryModeAvailable;
    @JsonProperty("smart_summon_available")
    private boolean smartSummonAvailable;
    @JsonProperty("software_update")
    private SoftwareUpdate softwareUpdate;
    @JsonProperty("speed_limit_mode")
    private SpeedLimitMode speedLimitMode;
    @JsonProperty("summon_standby_mode_enabled")
    private boolean summonStandbyModeEnabled;
    @JsonProperty("sun_roof_percent_open")
    private int sunRoofPercentOpen;
    @JsonProperty("sun_roof_state")
    private String sunRoofState;
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty("valet_mode")
    private boolean valetMode;
    @JsonProperty("valet_pin_needed")
    private boolean pinNeeded;
    @JsonProperty("vehicle_name")
    private String vehicleName;
}
