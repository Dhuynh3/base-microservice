package ca.com.microservice.messages;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;

/**
 * The MessageSender class will define protocols and options for
 * communicating between microservice and sidecar.
 * This class will follow the Java builder pattern.
 */
public class MessageSender {

    // HTTP Implementation
    HttpClient client = null;
    RequestOptions reqOptions = null;
    HttpClientOptions httpOption = null;
    Vertx vertx = null;

    public MessageSender(HTTPBuilder builder) {
        vertx = Vertx.vertx();

        reqOptions = new RequestOptions();

        reqOptions.setHost(builder.hostname);
        reqOptions.setPort(builder.port);
        reqOptions.setMethod(builder.method);
        reqOptions.setURI(builder.uri);

        httpOption = new HttpClientOptions().setDefaultHost(builder.hostname).setDefaultPort(builder.port);

        client = vertx.createHttpClient(httpOption);
    }
    public static class HTTPBuilder {
        private HttpMethod method = HttpMethod.GET;
        private String hostname;
        private String uri;
        private int port;
        public HTTPBuilder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }
        public HTTPBuilder setURI(String uri) {
            this.uri = uri;
            return this;
        }

        public HTTPBuilder setPort(int port) {
            this.port = port;
            return this;
        }
        public HTTPBuilder setMethod(String method) {

            switch (method) {
                case "GET": {
                    this.method = HttpMethod.GET;
                }
                case "POST": {
                    this.method = HttpMethod.POST;
                }
                default: {
                    this.method = HttpMethod.GET;
                }
            }

            return this;
        }
        public MessageSender build() {
            return new MessageSender(this);
        }
    }
    public Future<HttpClientRequest> sendHTTPRequest() {
        return client.request(reqOptions);
    }
    /////////////////////////////////////////////////////


    ////////////////////////////////////////////
    /**
     * FUTURE: GRPC SUPPORT
     * @param builder
     */
    public MessageSender(GRPCBuilder builder) {

    }
    public static class GRPCBuilder {
        private String hostname;
        private int port;

        public MessageSender build() {
            return new MessageSender(this);
        }
    }
    ////////////////////////////////////////////
};