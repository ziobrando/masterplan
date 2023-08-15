package it.avanscoperta.masterplan.configuration.query;

import it.avanscoperta.masterplan.configuration.domain.UserRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BusyPersonProjector {

    static Logger logger = LoggerFactory.getLogger(BusyPersonProjector.class);

    final BusyPersonRepository repository;

    public BusyPersonProjector(BusyPersonRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void handle(UserRegistered event) {
        BusyPersonView busyPersonView = new BusyPersonView(
                event.userId(), event.username()
        );
        repository.save(busyPersonView);
        logger.info("Saved busy person: " + busyPersonView.getUserId());
    }
}
