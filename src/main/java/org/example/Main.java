package org.example;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;

public class Main {

    private static final URI BASE_URI = URI.create("http://localhost:8080/rest/");
    public static final String ROOT_PATH = "upload";

    public static void main(String[] args) {
        try {
            System.out.println("Benno Rest upload file example");

            /**
             * Required for MULTIPART_FORM_DATA. Without the package the server doesn't start
             */
            final ResourceConfig resourceConfig = new ResourceConfig()
                    .packages("org.glassfish.jersey.examples.multipart")
                    // Required for MULTIPART_FORM_DATA
                    .register(MultiPartFeature.class)
                    // Register own Servlet
                    .register(Upload.class);


            // Start server
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));
            server.start();

            System.out.println(String.format("Application started.\nTry out %s%s\nStop the application using CTRL+C",
                    BASE_URI, ROOT_PATH));
            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}