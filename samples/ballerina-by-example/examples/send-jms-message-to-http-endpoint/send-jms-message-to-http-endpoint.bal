import ballerina.net.jms;
import ballerina.net.http;

@jms:configuration {
    initialContextFactory: "wso2mbInitialContextFactory",
    providerUrl:
    "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
    connectionFactoryType:"queue",
    connectionFactoryName:"QueueConnectionFactory",
    destination:"MyQueue",
    acknowledgementMode: "AUTO_ACKNOWLEDGE"
}
service<jms> jmsService {
    resource onMessage (jms:JMSMessage m) {
        http:ClientConnector httpConnector;
        http:Options connectorOptions = {};
        httpConnector = create http:ClientConnector("http://localhost:8080", connectorOptions);
        http:Request req = {};

        // Retrieve the string payload using native function and set as a json payload.
        req.setStringPayload(req, m.getTextMessageContent());

        http:Response resp = httpConnector.post("/my-webapp/echo", req);
        println("POST response: ");
        println(resp.getJsonPayload());
    }
}
