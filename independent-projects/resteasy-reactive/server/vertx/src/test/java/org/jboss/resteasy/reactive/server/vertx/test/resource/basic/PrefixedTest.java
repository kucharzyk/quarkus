package org.jboss.resteasy.reactive.server.vertx.test.resource.basic;

import java.util.function.Supplier;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;

import org.jboss.resteasy.reactive.server.vertx.test.framework.ResteasyReactiveUnitTest;
import org.jboss.resteasy.reactive.server.vertx.test.resource.basic.resource.PrefixedResource;
import org.jboss.resteasy.reactive.server.vertx.test.simple.PortProviderUtil;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * @tpSubChapter Resteasy-client
 * @tpChapter Integration tests
 * @tpTestCaseDetails Test that @Prefixed params are passed correctly
 * @tpSince RESTEasy 3.0.16
 */
@DisplayName("Prefixed Params Test")
public class PrefixedTest {

    static Client client;
    @RegisterExtension
    static ResteasyReactiveUnitTest testExtension = new ResteasyReactiveUnitTest()
            .setArchiveProducer(new Supplier<>() {
                @Override
                public JavaArchive get() {
                    JavaArchive war = ShrinkWrap.create(JavaArchive.class);
                    war.addClasses(PrefixedResource.class, PortProviderUtil.class);
                    return war;
                }
            });

    @BeforeEach
    public void init() {
        client = ClientBuilder.newClient();
    }

    @AfterEach
    public void after() throws Exception {
        client.close();
    }

    private String generateURL(String path) {
        return PortProviderUtil.generateURL(path, PrefixedTest.class.getSimpleName());
    }

    /**
     * @tpTestDetails Test that @Prefixed params are passed correctly
     * @tpSince RESTEasy 3.0.16
     */
    @Test
    @DisplayName("Test @Prefixed")
    public void testPrefixed() throws Exception {
        Form form = new Form()
                .param("foo", "A")
                .param("baz", "B")
                .param("user.username", "L")
                .param("user.age", "42");

        Response response = client.target(generateURL("/prefixed")).request().post(Entity.form(form));
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        response.close();
    }
}
