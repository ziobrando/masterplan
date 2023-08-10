package it.avanscoperta.masterplan.common.steps;

import io.cucumber.java.en.*;
import it.avanscoperta.masterplan.configuration.domain.RegisterUser;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSteps {

    @Autowired
    ScenarioContext context;

    @Autowired
    CommandGateway commandGateway;


    @Given("{string} is a registered user")
    public void is_a_registered_user(String username) {
        UserId userId = UserId.generate();
        RegisterUser registerUser = new RegisterUser(userId, username);
        commandGateway.send(registerUser);

        context.rememberUser(username, userId);
    }

}
