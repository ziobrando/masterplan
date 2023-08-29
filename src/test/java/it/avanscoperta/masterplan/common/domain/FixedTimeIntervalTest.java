package it.avanscoperta.masterplan.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FixedTimeIntervalTest {

    @Test
    void can_create_an_interval() {
        FixedTimeInterval interval = new FixedTimeInterval(
                LocalDateTime.now().minusSeconds(20),
                LocalDateTime.now().plusMinutes(30));
        assertNotNull(interval);
    }

    @Test
    @DisplayName("Extremes should bve in the right order")
    void extremes_should_be_in_the_right_order() {
        assertThrows(IllegalArgumentException.class,
                () -> new FixedTimeInterval(
                        LocalDateTime.now(),
                        LocalDateTime.now().minusSeconds(2))
        );
    }


}
