import ballerina/jms;
import ballerina/log;

// Initialize a JMS connection with the provider
endpoint jms:Connection jmsConnection {
    initialContextFactory: "wso2mbInitialContextFactory",
    providerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
};

// Initialize a JMS session on top of the created connection
endpoint jms:Session jmsSession {
    connection: jmsConnection,
    acknowledgementMode: "AUTO_ACKNOWLEDGE"
};

// Initialize a Queue sender on top of the the created sessions
endpoint jms:QueueSender queueSender {
    session: jmsSession,
    queueName: "requestQueue"
};

public function main (string[] args) {
    // Create a Text message.
    jms:Message m = jmsSession.createTextMessage("Test Text");
    // Send the Ballerina message to the JMS provider.
    queueSender -> send(m);
}