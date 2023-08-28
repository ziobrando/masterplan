package it.avanscoperta.masterplan.common.domain;

import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[MasterPlan | Common]: Slot Value Object")
public class SlotTest {

    private PlannedActivity twoHoursActivity;
    private Slot fullDayTomorrow;
    private LocalDateTime midnight;
    private LocalDateTime nextMidnight;

    @BeforeEach
    void setUp() {
        twoHoursActivity = new PlannedActivity(Duration.ofHours(2));
        midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
        nextMidnight = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);

        fullDayTomorrow = new Slot(midnight, nextMidnight);
    }

    @Test
    @DisplayName("Can create a slot")
    void can_create_a_slot() {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().plusMonths(3);

        Slot slot = new Slot(from, to);

        assertNotNull(slot);
    }

    @Test
    @DisplayName("Can check availability for floating activities")
    void can_check_availability_for_floating_activities() {
        PlannedActivity twentyFiveHoursActivity = new PlannedActivity(Duration.ofHours(25));
        PlannedActivity twentyFourHoursActivity = new PlannedActivity(Duration.ofHours(24));

        assertTrue(fullDayTomorrow.hasRoomFor(twoHoursActivity));
        assertTrue(fullDayTomorrow.hasRoomFor(twentyFourHoursActivity));
        assertFalse(fullDayTomorrow.hasRoomFor(twentyFiveHoursActivity));
    }


    @Test
    @DisplayName("Can check availability for anchored activities")
    void can_check_availability_for_anchored_activities() {
        PlannedActivity fourHoursInTheMorning = new PlannedActivity(Duration.ofHours(4)).happeningBetween(LocalTime.of(8,0), LocalTime.of(13, 30));

        assertTrue(fullDayTomorrow.hasRoomFor(fourHoursInTheMorning));
    }


}
