package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.configuration.domain.UserRegistered;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class ActivationPolicy {

    private static Logger logger = LoggerFactory.getLogger(ActivationPolicy.class);

    @Autowired
    transient CommandGateway commandGateway;

    @SagaEventHandler(associationProperty = "userId")
    @StartSaga
    public void handle(UserRegistered userRegistered){
        logger.info("Reacting to UserRegistered: " + userRegistered);
        PersonalAvailabilityId personalAvailabilityId = PersonalAvailabilityId.generate();

        GeneratePersonalAvailability generatePersonalAvailability = new GeneratePersonalAvailability(
                personalAvailabilityId,
                userRegistered.userId()
        );

        commandGateway.send(generatePersonalAvailability);
        logger.info("Sent command: " + generatePersonalAvailability);

    }

}
