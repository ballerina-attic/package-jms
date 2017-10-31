import ballerina.net.jms;

function main (string[] args) {
    jmsTransactedSender();
}

function jmsTransactedSender() {
    // We define the connection properties as a map. 'providerUrl' or 'configFilePath' and the 'initialContextFactory' vary according to the JMS provider you use.
    // In this example we connect to the WSO2 MB server.
    jms:ClientConnector jmsEP;
    jms:ConnectorProperties conProperties = {
                         initialContextFactory:"wso2mbInitialContextFactory",
                         configFilePath:"../jndi.properties",
                         connectionFactoryName: "QueueConnectionFactory",
                         connectionFactoryType : "queue",
                         acknowledgementMode: "SESSION_TRANSACTED"
                     };
    // Create the JMS client Connector using the connection properties we defined earlier.
    jmsEP = create jms:ClientConnector(conProperties);
    // Create an empty Ballerina message.
    jms:JMSMessage queueMessage = jms:createTextMessage(jmsEP);
    // Set a string payload to the message.
    queueMessage.setTextMessageContent("Hello from Ballerina!");
    // Send the Ballerina message to the JMS provider using JMS Local transactions. Local transactions can only be used
    // when you are sending multiple messages using the same ClientConnector
    transaction {
        jmsEP.send("MyQueue", queueMessage);
        jmsEP.send("MySecondQueue", queueMessage);
    }
}
