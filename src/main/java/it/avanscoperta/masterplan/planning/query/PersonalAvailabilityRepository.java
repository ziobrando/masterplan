package it.avanscoperta.masterplan.planning.query;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonalAvailabilityRepository
        extends MongoRepository<PersonalAvailabilityView, String> {

}
