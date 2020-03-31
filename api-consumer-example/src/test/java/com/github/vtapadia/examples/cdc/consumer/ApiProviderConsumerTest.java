package com.github.vtapadia.examples.cdc.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "api-provider-app", port = "9001")
public class ApiProviderConsumerTest {

    @Pact(consumer="api-consumer-app")
    public RequestResponsePact getApi(PactDslWithProvider builder) {
        return builder
                .given("user exists 1")
                .uponReceiving("ExampleJavaConsumerPactTest test interaction")
                .path("/api/employee/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody().stringType("name", "Varesh").integerType("id", 1))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getApi")
    void testArticles(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/api/employee/1").execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
        assertThat(IOUtils.toString(httpResponse.getEntity().getContent(), Charset.forName("UTF-8")),
                is(equalTo("{\"name\":\"Varesh\",\"id\":1}")));
    }

}
