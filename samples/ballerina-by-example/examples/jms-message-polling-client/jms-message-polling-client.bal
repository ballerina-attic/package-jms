import ballerina/io;
import ballerina/net.jms;

endpoint jms:ClientEndpoint jmsEP {
    initialContextFactory:"wso2mbInitialContextFactory",
    configFilePath:"../../../resources/jndi.properties",
    connectionFactoryName: "QueueConnectionFactory",
    connectionFactoryType : "queue"
};

function main (string[] args) {
    // Poll message from message broker
    jms:JMSMessage message = jmsEP->poll("MyQueue", 1000);

    if (message != null) {
        io:println(message.getTextMessageContent());
    } else {
        io:println("No message recieved");
    }
}
