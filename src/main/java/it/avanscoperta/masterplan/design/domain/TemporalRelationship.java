package it.avanscoperta.masterplan.design.domain;

import java.time.Duration;

public enum TemporalRelationship {
    RIGHT_BEFORE {
        @Override
        public Duration getRelativeOffsetFrom(Moment self, Moment primary) {
            return Duration.ZERO.minus(self.duration());
        }
    },
    RIGHT_AFTER {
        @Override
        public Duration getRelativeOffsetFrom(Moment self, Moment primary) {
            return primary.duration().plus(self.duration());
        }
    };

    public abstract Duration getRelativeOffsetFrom(Moment self, Moment primary);
}
