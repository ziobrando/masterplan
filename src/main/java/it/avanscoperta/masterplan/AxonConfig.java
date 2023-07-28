package it.avanscoperta.masterplan;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AxonConfig {

    @Bean
    CommandBus commandBus() {
        return SimpleCommandBus.builder().build();
    }

    @Bean
    CommandGateway commandGateway() {
        return CommandGatewayFactory
                .builder()
                .commandBus(commandBus())
                .build()
                .createGateway(CommandGateway.class);
    }

}
