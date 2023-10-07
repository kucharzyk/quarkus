package org.jboss.resteasy.reactive.server.vertx.test.resource.basic.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.Prefixed;
import org.junit.jupiter.api.Assertions;

@Path("/prefixed")
public class PrefixedResource {

    private static class MyParams {
        @FormParam(value = "foo")
        String foo;

        @FormParam(value = "baz")
        String bar;

        User user;

    }

    private static class User {
        @FormParam(value = "username")
        String login;

        @FormParam(value = "age")
        Long age;

    }

    @Prefixed
    MyParams params;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("text/plain")
    public String post() {
        Assertions.assertNotNull(params);
        Assertions.assertEquals("A", params.foo);
        Assertions.assertEquals("B", params.bar);
        Assertions.assertNotNull(params.user);
        Assertions.assertEquals("L", params.user.login);
        Assertions.assertEquals(42, params.user.age);
        return "OK";
    }

}
