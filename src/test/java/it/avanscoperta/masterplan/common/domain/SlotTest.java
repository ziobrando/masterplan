package it.avanscoperta.masterplan.common.domain;

import it.avanscoperta.masterplan.planning.domain.PotentialActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[MasterPlan | Common]: Slot Value Object")
public class SlotTest {

    private PotentialActivity twoHoursActivity;
    private LocalDate tomorrow;
    private Slot fullDayTomorrow;
    private LocalDateTime midnight;
    private LocalDateTime nextMidnight;
    private Slot tomorrowMorning;
    private Slot tomorrowAfternoon;

    @BeforeEach
    void setUp() {
        twoHoursActivity = new PotentialActivity(Duration.ofHours(2));
        tomorrow = LocalDate.now().plusDays(1);
        midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
        nextMidnight = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);

        fullDayTomorrow = new Slot(midnight, nextMidnight);
        tomorrowMorning = new Slot(
                LocalDateTime.now().plusDays(1).withHour(7).truncatedTo(ChronoUnit.HOURS),
                LocalDateTime.now().plusDays(1).withHour(13).truncatedTo(ChronoUnit.HOURS));
        tomorrowAfternoon = new Slot(
                LocalDateTime.now().plusDays(1).withHour(14).truncatedTo(ChronoUnit.HOURS),
                LocalDateTime.now().plusDays(1).withHour(19).truncatedTo(ChronoUnit.HOURS)
        );
    }

    @Test
    @DisplayName("Building blocks are safe")
    void building_blocks_are_safe() {
        assertEquals(tomorrow, midnight.toLocalDate());

        assertTrue(tomorrowMorning.timeInterval().spansOver(tomorrow));
        assertTrue(tomorrowAfternoon.timeInterval().spansOver(tomorrow));
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
        PotentialActivity twentyFiveHoursActivity = new PotentialActivity(Duration.ofHours(25));
        PotentialActivity twentyFourHoursActivity = new PotentialActivity(Duration.ofHours(24));

        assertTrue(fullDayTomorrow.hasRoomFor(twoHoursActivity));
        assertTrue(fullDayTomorrow.hasRoomFor(twentyFourHoursActivity));
        assertFalse(fullDayTomorrow.hasRoomFor(twentyFiveHoursActivity));
    }


    @Test
    @DisplayName("Can check availability for anchored activities")
    void can_check_availability_for_anchored_activities() {
        PotentialActivity fourHoursInTheMorning = new PotentialActivity(Duration.ofHours(4)).happeningBetween(LocalTime.of(8,0), LocalTime.of(13, 30));

        assertTrue(fullDayTomorrow.hasRoomFor(fourHoursInTheMorning),
                "Slot " + fullDayTomorrow + " should have room for " + fourHoursInTheMorning);
        assertTrue(tomorrowMorning.hasRoomFor(fourHoursInTheMorning));
        assertFalse(tomorrowAfternoon.hasRoomFor(fourHoursInTheMorning));
    }


}
