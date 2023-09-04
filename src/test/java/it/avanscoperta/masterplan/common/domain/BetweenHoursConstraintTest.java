package it.avanscoperta.masterplan.common.domain;

import it.avanscoperta.masterplan.planning.domain.BetweenHoursConstraint;
import it.avanscoperta.masterplan.planning.domain.PotentialActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BetweenHoursConstraintTest {


    private PotentialActivity oneHourActivity;
    private LocalTime dailyEnd;
    private BetweenHoursConstraint officeHoursConstraint;
    private Slot
            tomorrowLateMorningSlot,
            tomorrowEarlyMorningSlot,
            tomorrowVeryEarlyMorningSlot;


    @BeforeEach
    void setUp() {
        LocalTime dailyStart = LocalTime.of(9, 0);
        oneHourActivity = new PotentialActivity(Duration.ofHours(1));
        dailyEnd = LocalTime.of(18,0);
        officeHoursConstraint = new BetweenHoursConstraint(oneHourActivity, dailyStart, dailyEnd);

        tomorrowLateMorningSlot = new Slot(
                LocalDateTime.of(2023, 9,5,11,0),
                LocalDateTime.of(2023, 9, 5, 13, 0)
        );
        tomorrowVeryEarlyMorningSlot = new Slot(
                LocalDateTime.of(2023, 9, 5, 8, 0),
                LocalDateTime.of(2023, 9,5,9, 0)
        );
        tomorrowEarlyMorningSlot = new Slot(
                LocalDateTime.of(2023, 9, 5, 8, 30),
                LocalDateTime.of(2023, 9,5,9, 30)
        );
    }

    @Test
    @DisplayName("Returns true on slots between hours")
    public void returns_true_on_slot_between_hours() {
        assertTrue(officeHoursConstraint.isSatisfiedBy(tomorrowLateMorningSlot));
    }


    @Test
    @DisplayName("Returns false on slots outside hours")
    public void returns_false_on_slot_outside_hours() {
        assertFalse(officeHoursConstraint.isSatisfiedBy(tomorrowVeryEarlyMorningSlot));
    }

    @Test
    @DisplayName("Returns false on slots partially outside hours")
    public void returns_false_on_slot_partially_outside_hours() {
        assertFalse(officeHoursConstraint.isSatisfiedBy(tomorrowEarlyMorningSlot));
    }


}
