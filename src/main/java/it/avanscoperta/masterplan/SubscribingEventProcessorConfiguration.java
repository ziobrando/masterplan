package it.avanscoperta.masterplan;

import org.axonframework.config.EventProcessingConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;


/**
 * This one skips past events in development.
 * https://www.infoq.com/articles/axon-server-cqrs-event-sourcing-java/
 */
@Profile("development")
@Component
public class SubscribingEventProcessorConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public void configure(EventProcessingConfigurer config) {
        log.info("Setting using Subscribing event processors.");
        config.usingSubscribingEventProcessors();
    }
}

