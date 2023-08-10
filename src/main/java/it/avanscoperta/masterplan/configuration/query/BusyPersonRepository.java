package it.avanscoperta.masterplan.configuration.query;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusyPersonRepository extends MongoRepository<BusyPersonView, UserId> {

}
