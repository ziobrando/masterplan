package it.avanscoperta.masterplan.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FixedTimeIntervalTest {

    @Test
    void can_create_an_interval() {
        FixedTimeInterval interval = new FixedTimeInterval(
                LocalDateTime.now().minusSeconds(20),
                LocalDateTime.now().plusMinutes(30));
        assertNotNull(interval);
    }

    @Test
    @DisplayName("Extremes should be in the right order")
    void extremes_should_be_in_the_right_order() {
        assertThrows(IllegalArgumentException.class,
                () -> new FixedTimeInterval(
                        LocalDateTime.now(),
                        LocalDateTime.now().minusSeconds(2))
        );
    }


    @Test
    void should_report_overlappings() {
        FixedTimeInterval eightToTen = new FixedTimeInterval(
                LocalDateTime.of(2023, 9, 10, 8,0),
                LocalDateTime.of(2023, 9, 10, 10, 0)
        );
        FixedTimeInterval nineToEleven = new FixedTimeInterval(
                LocalDateTime.of(2023,9,10,9,0),
                LocalDateTime.of(2023, 9,10,11,0)
        );

        assertTrue(eightToTen.overlaps(eightToTen));

        assertTrue(eightToTen.overlaps(nineToEleven));
        assertTrue(nineToEleven.overlaps(eightToTen));
    }


}
