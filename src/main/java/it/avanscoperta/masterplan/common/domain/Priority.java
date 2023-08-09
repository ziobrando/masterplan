package it.avanscoperta.masterplan.common.domain;

/**
 * A ValueObject to encapsulate priorities.
 * @param label
 * @param priority
 */
public record Priority(String label, int priority) {

    public boolean trumps(Priority other) {
        return this.priority > other.priority();
    }

    public Priority {
        if (priority < 0) throw new IllegalArgumentException("No negative priority");
        if (priority > 100) throw new IllegalArgumentException("Priority is max 100");
    }
}
