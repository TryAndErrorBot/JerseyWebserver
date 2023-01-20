package org.example.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

public class AuthorizationRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic")) {
            requestContext.abortWith(Response.status(401).header("WWW-Authenticate", "Basic").build());
            return;
        }

        //Send new Request to another auth interface to validate the auth header
        //https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest3x/client.html
        /**
        JsonObject responseEntity = ClientBuilder.newClient()
                .target("https://rest-interface").path("resources")
                .request().header("Authorization", authHeader).get(JsonObject.class);
        **/
        String[] tokens = (new String(Base64.getDecoder().decode(authHeader.split(" ")[1]), StandardCharsets.UTF_8)).split(":");
        final String username = tokens[0];
        final String password = tokens[1];
        final String response = "This is the response";
        requestContext.setProperty("response", response);

        if (!username.equals("basicauth") || !password.equals("password")) {
            requestContext.abortWith(Response.status(401).build());
        }
    }
}