package it.avanscoperta.masterplan.planning.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class PersonalAvailability {

    @AggregateIdentifier
    PersonalAvailabilityId personalAvailabilityId;

    PersonalAvailability() {}

    @CommandHandler
    PersonalAvailability(GeneratePersonalAvailability command) {
        apply(new PersonalAvailabilityGenerated(
                        command.personalAvailabilityId(),
                        command.userId()
                )
        );
    }

    @EventSourcingHandler
    public void on(PersonalAvailabilityGenerated event) {
        this.personalAvailabilityId = event.personalAvailabilityId();
    }

    @CommandHandler
    public void registerEvent(RegisterEvent command) {
        apply(new EventRegistered(
                command.personalAvailabilityId(),
                command.eventLabel(),
                command.eventParty(),
                command.duration()
        ));
    }

    @EventSourcingHandler
    public void on(EventRegistered event) {
        // TODO: find what to do here.
    }

}
