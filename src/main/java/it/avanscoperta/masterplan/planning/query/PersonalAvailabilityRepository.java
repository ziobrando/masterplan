package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonalAvailabilityRepository
        extends MongoRepository<PersonalAvailabilityView, String> {

    Optional<PersonalAvailabilityView> findByUserId(UserId userId);
}
