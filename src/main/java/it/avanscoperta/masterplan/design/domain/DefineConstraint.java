package it.avanscoperta.masterplan.design.domain;

import it.avanscoperta.masterplan.configuration.domain.UserId;

import java.time.LocalTime;

/**
 * TODO: there hasn't been much design here. Should we have a PersonalSetting or a Profile here?
 * @param constraintId under the assumption that it will be necessary.
 * @param userId the id of the associated user.
 * @param constraintName the locally unique name of the defined constraint.
 * @param fromTime
 * @param toTime
 */
public record DefineConstraint(ConstraintId constraintId, UserId userId, String constraintName, LocalTime fromTime,
                               LocalTime toTime,
                               ConstraintType constraintType) {
}

