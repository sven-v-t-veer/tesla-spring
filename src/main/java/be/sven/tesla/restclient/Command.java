package be.sven.tesla.restclient;

public enum Command {
    VEHICLE_DATA("/vehicle_data"),
    VEHICLE_STATE("/data_request/vehicle_state"),
    DRIVE_STATE("/data_request/drive_state"),
    GUI_SETTINGS("/data_request/gui_settings"),
    SOFTWARE_UPDATE("/data_request/software_update"),
    WAKE("/wake_up"),
    HONK_HORN("/command/honk_horn"),
    FLASH_LIGHTS("/command/flash_lights"),
    REMOTE_START_DRIVE("/command/remote_start_drive"),
    TRIGGER_HOME_LINK("/command/trigger_homelink"),
    SPEED_LIMIT("/command/speed_limit_set_limit"),
    SPEED_LIMIT_ACTIVATE("/command/speed_limit_activate"),
    SPEED_LIMIT_DEACTIVATE("/command/speed_limit_deactivate"),
    SPEED_LIMIT_CLEAR_PIN("/command/speed_limit_clear_pin"),
    VALET_MODE("/command/set_valet_mode"),
    VALET_MODE_RESET_PIN("/command/reset_valet_pin"),
    SENTRY_MODE("/command/set_sentry_mode"),
    DOOR_LOCK("/command/door_lock"),
    DOOR_UNLOCK("/command/door_unlock"),
    TRUNK("/command/actuate_trunk"),
    WINDOW("/command/window_control"),
    SUNROOF("/command/sun_roof_control"),
    CHARGE_STATE("/data_request/charge_state"),
    CHARGE_PORT_OPEN("/command/charge_port_door_open"),
    CHARGE_PORT_CLOSE("/command/charge_port_door_close"),
    CHARGE_START("/command/charge_start"),
    CHARGE_STOP("/command/charge_stop"),
    CHARGE_STANDARD("/command/charge_standard"),
    CHARGE_MAX("/command/charge_max_range"),
    CHARGE_SET_LIMIT("/command/set_charge_limit"),
    CLIMATE_STATE("/data_request/climate_state"),
    CLIMATE_START("/command/auto_conditioning_start"),
    CLIMATE_STOP("/command/auto_conditioning_stop"),
    CLIMATE_SET_TEMP("/command/set_temps"),
    CLIMATE_DEFROST("/command/set_preconditioning_max"),
    CLIMATE_SEAT_HEATER("/command/remote_seat_heater_request"),
    CLIMATE_STEERING_WHEEL("/command/remote_steering_wheel_heater_request"),
    MEDIA_PLAYBACK("/command/media_toggle_playback"),
    MEDIA_NEXT_TRACK("/command/media_next_track"),
    MEDIA_PREVIOUS_TRACK("/command/media_previous_track"),
    MEDIA_NEXT_FAV("/command/media_next_fav"),
    MEDIA_PREVIOUS_FAV("/command/media_previous_fav"),
    MEDIA_VOLUME_UP("/command/media_volume_up"),
    MEDIA_VOLUME_DOWN("/command/media_volume_down"),
    SHARE("/command/share"),
    UPDATE_SCHEDULE("/command/schedule_software_update"),
    UPDATE_CANCEL("/command/cancel_software_update");


    Command(String url) {
        this.url = url;
    }

    private String url;

    @Override
    public String toString() {
        return this.url;
    }
}
