package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonalAvailabilityRepository
        extends MongoRepository<PersonalAvailabilityView, PersonalAvailabilityId> {

}
