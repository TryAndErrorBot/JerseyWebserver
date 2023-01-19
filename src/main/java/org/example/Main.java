package org.example;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;

public class Main {

    private static final String KEYSTORE_SERVER_FILE = "src/main/resources/cert.p12";
    private static final String KEYSTORE_SERVER_PWD = "pass";

    private static final URI BASE_URI = URI.create("https://127.0.0.1:8010/rest/");
    public static final String ROOT_PATH = "upload";

    public static void main(String[] args) {
        try {
            System.out.println("Benno Rest upload file example");

            SSLContextConfigurator sslContext = new SSLContextConfigurator();

            // set up security context
            sslContext.setKeyStoreFile(new File(KEYSTORE_SERVER_FILE).getAbsolutePath()); // contains server keypair
            sslContext.setKeyStorePass(KEYSTORE_SERVER_PWD);


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
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                    BASE_URI,
                    resourceConfig,
                    true,
                    new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(false)
            );
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