package it.avanscoperta.masterplan.planning.domain;

import org.axonframework.test.saga.FixtureConfiguration;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.jupiter.api.DisplayName;

@DisplayName("[Masterplan | Planning] ActivationPolicy Saga")
public class ActivationPolicyTest {

    FixtureConfiguration fixture = new SagaTestFixture<ActivationPolicy>(ActivationPolicy.class);

}
