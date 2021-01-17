package be.sven.tesla.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleStatesTest {

    private static final String DISCONNECTED = "Disconnected";
    private static final String NOPOWER = "NoPower";
    private static final String CHARGING = "Charging";
    private static final String COMPLETE = "Complete";

    @Mock
    private VehicleData vehicleData;

    @Mock
    private DriveState driveState;

    @Mock
    private ChargeState chargeState;

    @Mock
    private ClimateState climateState;

    @InjectMocks
    private VehicleStates testObj;

    @BeforeEach
    public void setup() {
        when(vehicleData.getDriveState()).thenReturn(driveState);
        when(vehicleData.getChargeState()).thenReturn(chargeState);
        when(vehicleData.getClimateState()).thenReturn(climateState);
    }

    @Test
    public void testVehicleStates() {
        //Object created.
        Assertions.assertThat(testObj.isOnline()).isFalse();
        Assertions.assertThat(testObj.canPollVehicle()).isFalse();
        //Toggle Online
        testObj.setOnline();
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();
        //Toggle Asleep
        testObj.setAsleep();
        Assertions.assertThat(testObj.isOnline()).isFalse();
        Assertions.assertThat(testObj.canPollVehicle()).isFalse();
        //Toggle Online
        testObj.setOnline();
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        //With vehicle online, parkd and climate off
        when(driveState.isParked()).thenReturn(true);
        when(climateState.isClimateOn()).thenReturn(false);

        // Set State Disconnected
        when(chargeState.getChargingState()).thenReturn(DISCONNECTED);
        testObj.setStates(vehicleData);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        // Set State NoPower
        when(chargeState.getChargingState()).thenReturn(NOPOWER);
        testObj.setStates(vehicleData);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        // Set State Charging
        when(chargeState.getChargingState()).thenReturn(CHARGING);
        testObj.setStates(vehicleData);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        // Set State Charging Complete
        when(chargeState.getChargingState()).thenReturn(COMPLETE);
        testObj.setStates(vehicleData);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        // Set State Disconnected & climate on
        when(chargeState.getChargingState()).thenReturn(DISCONNECTED);
        when(climateState.isClimateOn()).thenReturn(true);
        testObj.setStates(vehicleData);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        //Set last changed to 6 minutes ago
        Instant sixMinutesAgo = Instant.now().minus(6, ChronoUnit.MINUTES);


        // Set State Disconnected & climate off
        when(chargeState.getChargingState()).thenReturn(DISCONNECTED);
        when(climateState.isClimateOn()).thenReturn(false);
        testObj.setStates(vehicleData);
        testObj.setLastChanged(sixMinutesAgo);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isFalse();

        // Set State Charging & climate off
        when(chargeState.getChargingState()).thenReturn(CHARGING);
        when(climateState.isClimateOn()).thenReturn(false);
        testObj.setStates(vehicleData);
        testObj.setLastChanged(sixMinutesAgo);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        // Set State Disconnected & climate off
        when(chargeState.getChargingState()).thenReturn(COMPLETE);
        when(climateState.isClimateOn()).thenReturn(false);
        testObj.setStates(vehicleData);
        testObj.setLastChanged(sixMinutesAgo);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isFalse();

        // Set State NoPower & climate off
         //What does NoPower really mean? Should car sleep
        when(climateState.isClimateOn()).thenReturn(false);
        testObj.setStates(vehicleData);
        testObj.setLastChanged(sixMinutesAgo);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isFalse();

        // Set State Disconnected & climate on
        when(chargeState.getChargingState()).thenReturn(DISCONNECTED);
        when(climateState.isClimateOn()).thenReturn(true);
        testObj.setStates(vehicleData);
        testObj.setLastChanged(sixMinutesAgo);
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();

        //Set last changed to 12 minutes ago
        // the car should be asleep. Some state must have changed in the period we allowed the car to sleep.
        Instant twelveMinutesAgo = Instant.now().minus(12, ChronoUnit.MINUTES);
        testObj.setOnline();
        testObj.setLastChanged(twelveMinutesAgo);
        testObj.setOnline();
        Assertions.assertThat(testObj.isOnline()).isTrue();
        Assertions.assertThat(testObj.canPollVehicle()).isTrue();
    }
}
