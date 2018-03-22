import ballerina/net.jms;
import ballerina/io;

endpoint jms:ConsumerEndpoint ep1 {
    initialContextFactory: "wso2mbInitialContextFactory",
    providerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
};

@jms:serviceConfig {
    destination: "testQueue"
}
service<jms:Service> jmsService bind ep1 {

    onMessage (endpoint client, jms:Message message) {
        string messageText = message.getTextMessageContent();
        io:println("Message: " + messageText);
    }
}
