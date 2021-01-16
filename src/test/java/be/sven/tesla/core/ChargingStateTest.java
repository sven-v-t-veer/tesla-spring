package be.sven.tesla.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChargingStateTest {

    @Test
    public void testIs() {
        Assertions.assertThat(ChargingState.NOPOWER.is("NoPower")).isTrue();
        Assertions.assertThat(ChargingState.NOPOWER.is("NoShit")).isFalse();
    }

    @Test void testWillNotCharge() {
        Assertions.assertThat(ChargingState.CHARGING.willNotCharge()).isFalse();
        Assertions.assertThat(ChargingState.NOPOWER.willNotCharge()).isFalse();
        Assertions.assertThat(ChargingState.DISCONNECTED.willNotCharge()).isTrue();
        Assertions.assertThat(ChargingState.COMPLETE.willNotCharge()).isTrue();
    }
}
