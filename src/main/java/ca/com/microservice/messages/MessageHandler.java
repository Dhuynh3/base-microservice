package ca.com.microservice.messages;

import ca.com.microservice.businesslogic.BusinessLogic;
import ca.com.microservice.utility.Logger;
import io.vertx.ext.web.RoutingContext;

/**
 * The MessageHandler class will process all incoming messages from the
 * Gateway's endpoints.
 */
public class MessageHandler {
    Logger log = new Logger();
    BusinessLogic logic = new BusinessLogic();
    MessageValidator validator = new MessageValidator();

    public MessageHandler() {
        log.info("MessageHandler Constructor Called");
    }

    public void handleMessageBody(Void handle) {

    }

    public void handleMessage(RoutingContext rc) {
        String pathParam = rc.pathParam("default");
        log.info("Middleware Filtering at : " + pathParam + "\n");

        rc.addBodyEndHandler(this::handleMessageBody);

        rc.response().end("Hey testing");
    }

    /**
     * FUTURE IMPLEMENTATOIN: This handler will perform health checks on the status
     * of the service
     * 
     * @param rc RoutingContext
     */
    public void handleStatus(RoutingContext rc) {
        log.info("[MessageHandler] : Handling '/status' Context");

        
    }
}