import ballerina/jms;
import ballerina/io;

endpoint jms:Connection conn {
    initialContextFactory: "wso2mbInitialContextFactory",
    providerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
};

endpoint jms:Session jmsSession {
    connection: conn
};

endpoint jms:QueueConsumer consumer {
    session: jmsSession,
    queueName: "requestQueue"
};

service<jms:Consumer> jmsListener bind consumer {

    onMessage(endpoint consumer, jms:Message message) {
        string messageText = message.getTextMessageContent();
        io:println("Message: " + messageText);
    }
}
