package com.github.vtapadia.examples.cdc.provider;

import au.com.dius.pact.provider.junit.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Provider("api-provider-app")
//@PactFolder("pacts")
@PactBroker(scheme = "https", host = "${pactbroker.host}",
        authentication = @PactBrokerAuth(token = "${pactbroker.auth.token}")
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@IgnoreNoPactsToVerify
public class PactVerificationTest {
    @LocalServerPort
    private int port;
    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void testTemplate(//Pact pact, Interaction interaction, HttpRequest request,
                      PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void setContext(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @State("user exists 1")
    public void setupUser1() {

    }
}
