package it.avanscoperta.masterplan.configuration.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class BusyPerson {

    @AggregateIdentifier
    private UserId userId;

    private BusyPerson() {}

    @CommandHandler
    public BusyPerson(RegisterUser command) {
        apply(new UserRegistered(
                command.userId(),
                command.username()
        ));
    }

    @EventSourcingHandler
    public void on(UserRegistered event) {
        this.userId = event.userId();
    }

}
