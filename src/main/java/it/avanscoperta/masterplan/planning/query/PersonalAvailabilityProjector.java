package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityGenerated;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PersonalAvailabilityProjector {

    static Logger logger = LoggerFactory.getLogger(PersonalAvailabilityProjector.class);

    final PersonalAvailabilityRepository repository;

    public PersonalAvailabilityProjector(PersonalAvailabilityRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void handle(PersonalAvailabilityGenerated event) {
        // TODO: maybe a place for a factory.
        PersonalAvailabilityView personalAvailabilityView = new ClusteredPersonalAvailabilityView(
                event.personalAvailabilityId().id(),
                event.userId(),
                event.planningHorizon()
        );

        repository.save(personalAvailabilityView);
        logger.info("Saved personal availability: " + personalAvailabilityView);
    }
}
