package be.sven.tesla.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeState {
    @JsonProperty("battery_heater_on")
    private boolean batteryHeaterOn;
    @JsonProperty("battery_level")
    private int batteryLevel;
    @JsonProperty("battery_range")
    private BigDecimal batteryRange;
    @JsonProperty("charge_current_request")
    private int chargeCurrentRequest;
    @JsonProperty("charge_current_request_max")
    private int chargeCurrentRequestMax;
    @JsonProperty("charge_enable_request")
    private boolean chargeEnableRequest;
    @JsonProperty("charge_energy_added")
    private BigDecimal chargeEnergyAdded;
    @JsonProperty("charge_limit_soc")
    private int chargeLimitSoc;
    @JsonProperty("charge_limit_soc_max")
    private int chargeLimitSocMax;
    @JsonProperty("charge_limit_soc_min")
    private int chargeLimitSocMin;
    @JsonProperty("charge_limit_soc_std")
    private int chargeLimitSocStd;
    @JsonProperty("charge_miles_added_ideal")
    private BigDecimal chargeMilesAddedIdeal;
    @JsonProperty("charge_miles_added_rated")
    private BigDecimal chargeMilesAddedRated;
    @JsonProperty("charge_port_cold_weather_mode")
    private String chargePortColdWeatherMode;
    @JsonProperty("charge_port_door_open")
    private boolean chargePortDoorOpen;
    @JsonProperty("charge_port_latch")
    private String chargePortLatch;
    @JsonProperty("charge_rate")
    private BigDecimal chargeRate;
    @JsonProperty("charge_to_max_range")
    private boolean chargeToMaxRate;
    @JsonProperty("charger_actual_current")
    private int chargerActualCurrent;
    @JsonProperty("charger_phases")
    private String chargerPhases;
    @JsonProperty("charger_pilot_current")
    private int chargerPilotCurrent;
    @JsonProperty("charger_power")
    private int chargerPower;
    @JsonProperty("charger_voltage")
    private int chargerVoltage;
    @JsonProperty("charging_state")
    private String chargingState;
    @JsonProperty("conn_charge_cable")
    private String connChargeCable;
    @JsonProperty("est_battery_range")
    private BigDecimal estBatteryRange;
    @JsonProperty("fast_charger_brand")
    private String fastChargerBrand;
    @JsonProperty("fast_charger_present")
    private boolean fastChargerPresent;
    @JsonProperty("fast_charger_type")
    private String fastChargerType;
    @JsonProperty("ideal_battery_range")
    private BigDecimal idealBatteryRange;
    @JsonProperty("managed_charging_active")
    private boolean managedChargingActive;
    @JsonProperty("managed_charging_start_time")
    private String managedChargeTime;
    @JsonProperty("managed_charging_user_canceled")
    private boolean managedChargingUserCanceled;
    @JsonProperty("max_range_charge_counter")
    private int maxRangeChargeCounter;
    @JsonProperty("minutes_to_full_charge")
    private int minutesToFullCharge;
    @JsonProperty("not_enough_power_to_heat")
    private boolean notEnoughPowerToHeat;
    @JsonProperty("scheduled_charging_pending")
    private boolean scheduledChargingPending;
    @JsonProperty("scheduled_charging_start_time")
    private Long scheduledChargingStartTime;
    @JsonProperty("time_to_full_charge")
    private BigDecimal timeToFullCharge;
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty("trip_charging")
    private boolean tripCharging;
    @JsonProperty("usable_battery_level")
    private int usableBatteryLevel;
    @JsonProperty("user_charge_enable_request")
    private String userChargeEnableRequest;
}
