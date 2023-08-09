package it.avanscoperta.masterplan.common.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityTest {

    @Test
    public void canDefineAPriority() {
        Priority priority = new Priority("Label", 99);
        assertNotNull(priority);
    }

    @Test
    public void prioritiesTrumpEachOther() {
        Priority weaker = new Priority("Weaker", 25);
        Priority stronger = new Priority("Stronger", 50);

        assertTrue(stronger.trumps(weaker));
        assertFalse(weaker.trumps(stronger));
    }

    @Test
    public void samePriorityDoesNotTrump() {
        Priority original = new Priority("Same level", 50);
        Priority same = new Priority("Same level", 50);

        assertFalse(original.trumps(same));
    }

    @Test
    public void prioritiesArePositive() {
        assertThrows(IllegalArgumentException.class, () -> {
            Priority negative = new Priority("negative", -1);
        });
    }

    @Test
    public void prioritiesAreBelow100() {
        assertThrows(IllegalArgumentException.class, () -> {
            Priority exceeding = new Priority("exceeding", 101);
        });
    }
}
