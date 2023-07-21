package ca.com;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import ca.com.microservice.messages.MessageSender;
import ca.com.microservice.utility.Logger;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;

/**
 * This Microservice class serves as the entrypoint for the application and also
 * the lifecycle bean.
 */
@Startup
@ApplicationScoped
public class Microservice {

    Logger log = new Logger();

    public static String SIDECAR_ONE_IP[];

    public Microservice() {
        SIDECAR_ONE_IP = System.getenv().getOrDefault("SIDECAR_ONE_IP", "localhost:8081").split(":");
    }

    public void initialization() {
        log.info("Microservice Initialization Sequence S");

        MessageSender send = new MessageSender.HTTPBuilder()
        .setHostname(SIDECAR_ONE_IP[0])
        .setPort(Integer.parseInt(SIDECAR_ONE_IP[1]))
        .setURI("/microservice-inbound")
        .setMethod("POST")
        .build();

        send.sendHTTPRequest().onSuccess(event -> {
            
            event.send("Sending some kind of message").onSuccess(res -> {
                log.info("HTTP Request Sent " );
                res.end();
            });
            log.info("HTTP Request");
        }).onFailure(event2 -> {
            log.info("Failure" +  event2.getMessage());
        });

    }

    public void shutdown() {
        log.info("Microservice Shutting Down");
    }

    /**
     * Observes Quarkus StartupEvent, then runs our initialization function.
     */
    public void onStartup(@Observes StartupEvent ev) {
        initialization();
    }

    public void onShutdown(@Observes ShutdownEvent ev) {
        shutdown();
    }
}