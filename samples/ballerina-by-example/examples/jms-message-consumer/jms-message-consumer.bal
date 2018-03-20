import ballerina.net.jms;
import ballerina.io;

endpoint jms:ServiceEndpoint ep1 {
    initialContextFactory: "wso2mbInitialContextFactory",
    providerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
};

@jms:serviceConfig {
    destination: "testQueue"
}
service<jms:Service> jmsService bind ep1 {

    onMessgae (endpoint client, jms:Message message) {
        io:println("test message");
        io:println(message.getTextMessageContent());
    }
}
