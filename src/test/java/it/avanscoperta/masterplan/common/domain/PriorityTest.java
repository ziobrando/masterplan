package it.avanscoperta.masterplan.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[MasterPlan | Common]: Priority Value Object")
public class PriorityTest {

    @Test
    @DisplayName("Can define a priority")
    public void canDefineAPriority() {
        Priority priority = new Priority("Label", 99);
        assertNotNull(priority);
    }

    @Test
    @DisplayName("Priorities trump each other")
    public void prioritiesTrumpEachOther() {
        Priority weaker = new Priority("Weaker", 25);
        Priority stronger = new Priority("Stronger", 50);

        assertTrue(stronger.trumps(weaker));
        assertFalse(weaker.trumps(stronger));
    }

    @Test
    @DisplayName("Same priority does not trump")
    public void samePriorityDoesNotTrump() {
        Priority original = new Priority("Same level", 50);
        Priority same = new Priority("Same level", 50);

        assertFalse(original.trumps(same));
    }

    @Test
    @DisplayName("Only positive priorities are allowed")
    public void prioritiesArePositive() {
        assertThrows(IllegalArgumentException.class,
                () -> new Priority("negative", -1));
    }

    @Test
    @DisplayName("Priorities are below 100")
    public void prioritiesAreBelow100() {
        assertThrows(IllegalArgumentException.class,
                () -> new Priority("exceeding", 101));
    }
}
