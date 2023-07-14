package ca.com.microservice.gateway;

import javax.enterprise.event.Observes;

import ca.com.microservice.messages.MessageHandler;
import ca.com.microservice.utility.Logger;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

/**
 * The Gateway class will create one or multiple endpoints to the microservice.
 */
public class Gateway {

    Logger log = new Logger();
    MessageHandler msgHandler = new MessageHandler();

    private static Router router;

    /**
     * This constructor is always called before the onStartup in Microservice Class
     */
    public Gateway() {
        log.info("[Gateway] : Constructor Called");
    }

    /**
     * NOTES: The @Observes Router executes first than StartupEvent.
     * @param route
     */
    public void initRoute(@Observes Router route) {
        
        // Log the route startup
        log.info("[Gateway] : initRoute called");

        // Store the route
        Gateway.router = route;

        // Required routes to setup on microservice startup
        // "/sidecar-inbound" For adapters to send messages back
        router.route(HttpMethod.GET, "/sidecar-inbound").handler(msgHandler::handleMessage);

        // "/status" To query about the status of the microservice
        router.route(HttpMethod.GET, "/status").handler(msgHandler::handleStatus);
    }
}