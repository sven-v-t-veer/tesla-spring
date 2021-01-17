package be.sven.tesla.core;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DriveStateTest {

    private DriveState driveState = new DriveState();

    @Test
    public void testDriveState() {
        driveState.setShiftState(null);
        driveState.setSpeed(null);
        Assertions.assertThat(driveState.isParked()).isTrue();
        driveState.setShiftState("R");
        Assertions.assertThat(driveState.isParked()).isFalse();
        driveState.setShiftState("D");
        driveState.setSpeed(0);
        Assertions.assertThat(driveState.isParked()).isFalse();
        driveState.setShiftState("P");
        driveState.setSpeed(0);
        Assertions.assertThat(driveState.isParked()).isTrue();
    }
}
