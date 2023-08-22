package it.avanscoperta.masterplan.common.domain;

import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("[MasterPlan | Common]: Slot Value Object")
public class SlotTest {

    @Test
    @DisplayName("Can create a slot")
    void can_create_a_slot() {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().plusMonths(3);

        Slot slot = new Slot(from, to);

        assertNotNull(slot);
    }

    @Test
    @DisplayName("Can check availability")
    void can_check_availability() {

        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime nextMidnight = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);

        Slot fullDayTomorrow = new Slot(midnight, nextMidnight);

        PlannedActivity twoHoursActivity = new PlannedActivity(Duration.ofHours(2));

        assertTrue(fullDayTomorrow.hasRoomFor(twoHoursActivity));
    }

}
