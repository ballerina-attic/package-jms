import ballerina/net.jms;

endpoint jms:ClientEndpoint jmsEP {
    initialContextFactory:"wso2mbInitialContextFactory",
    configFilePath:"../../../resources/jndi.properties",
    connectionFactoryName: "QueueConnectionFactory",
    connectionFactoryType : "queue",
    acknowledgementMode: "SESSION_TRANSACTED"
};

function main (string[] args) {
    // Create an empty Ballerina message.
    jms:JMSMessage queueMessage = jmsEP.createTextMessage();
    // Set a string payload to the message.
    queueMessage.setTextMessageContent("Hello from Ballerina!");
    // Send the Ballerina message to the JMS provider using JMS Local transactions. Local transactions can only be used
    // when you are sending multiple messages using the same ClientConnector
    transaction {
        jmsEP->send("MyQueue", queueMessage);
        jmsEP->send("MySecondQueue", queueMessage);
    }
}

