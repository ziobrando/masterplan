package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityGenerated;
import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("[MasterPlan | Planning]: PersonalAvailability Projector")
public class PersonalAvailabilityProjectorTest {

    @Autowired
    PersonalAvailabilityProjector projector;

    @Autowired
    PersonalAvailabilityRepository repository;
    private PersonalAvailabilityId personalAvailabilityId;
    private UserId userId;

    @BeforeEach
    void setUp() {
        personalAvailabilityId = PersonalAvailabilityId.generate();
        userId = UserId.generate();
    }

    @Test
    @DisplayName("Will persist on PersonalAvailabilityGenerated")
    public void will_persist_on_PersonalAvailabilityGenerated() {

        PersonalAvailabilityGenerated personalAvailabilityGenerated = new PersonalAvailabilityGenerated(personalAvailabilityId, userId, PlanningHorizon.DEFAULT);

        projector.handle(personalAvailabilityGenerated);

        assertTrue(repository.findById(personalAvailabilityId.id()).isPresent());
    }

}
