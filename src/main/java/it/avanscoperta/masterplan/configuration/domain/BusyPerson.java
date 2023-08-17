package it.avanscoperta.masterplan.configuration.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class BusyPerson {

    private static final Logger logger = LoggerFactory.getLogger(BusyPerson.class);

    @AggregateIdentifier
    private UserId userId;

    private BusyPerson() {}

    @CommandHandler
    public BusyPerson(RegisterUser command) {
        apply(new UserRegistered(
                command.userId(),
                command.username(),
                command.planningHorizon()
        ));
    }

    @EventSourcingHandler
    public void handle(UserRegistered event) {
        logger.debug("Updating the aggregate id");
        this.userId = event.userId();
    }


    @CommandHandler
    public void adjustPlanningHorizon(AdjustPlanningHorizon command) {
        // TODO: No guards yet.
        apply(new PlanningHorizonAdjusted(
                command.userId(),
                command.duration()
        ));
    }

    @EventSourcingHandler
    public void handle(PlanningHorizonAdjusted event) {

    }

}
