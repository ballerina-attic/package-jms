import ballerina/net.jms;

endpoint jms:ClientEndpoint jmsEP {
    initialContextFactory:"wso2mbInitialContextFactory",
    configFilePath:"../../../resources/jndi.properties",
    connectionFactoryName: "QueueConnectionFactory",
    connectionFactoryType : "queue"
};

function main (string[] args) {
    // Create an empty Ballerina message.
    jms:JMSMessage queueMessage = jmsEP.createTextMessage();
    // Set a string payload to the message.
    queueMessage.setTextMessageContent("Hello from Ballerina!");
    // Send the Ballerina message to the JMS provider.
    jmsEP->send("MyQueue", queueMessage);
}
