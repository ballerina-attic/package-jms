import ballerina.net.jms;

function main (string[] args) {
    jmsTransactedSender();
}

function jmsTransactedSender() {
    // We define the connection properties as a map. 'providerUrl' or 'configFilePath' and the 'initialContextFactory' vary according to the JMS provider you use.
    // In this example we connect to the WSO2 MB server.
    jms:ClientConnector jmsEP;
    map properties = {
                         "initialContextFactory":"wso2mbInitialContextFactory",
                         "configFilePath":"../jndi.properties",
                         "connectionFactoryName": "QueueConnectionFactory",
                         "connectionFactoryType" : "queue",
                         "acknowledgementMode": jms:XA_TRANSACTED
                     };
    // Create the JMS client Connector using the connection properties we defined earlier.
    jmsEP = create jms:ClientConnector(properties);
    // Create an empty Ballerina message.
    jms:JMSMessage queueMessage = jms:createTextMessage(jmsEP);
    // Set a string payload to the message.
    queueMessage.setTextMessageContent("Hello from Ballerina!");
    // Send the Ballerina message to the JMS provider using Ballerina-JMS XA transactions.
    // XA transactions can use used when multiple JMS Client Connector sends or other any other Ballerina Transacted
    // action(s) in present.
    // This example scenario can be achieved with Local transactions as well and it is the ideal way to do it. XA is
    // used for demonstration purpose
    transaction {
        jmsEP.send("MyQueue", queueMessage);
        jmsEP.send("MySecondQueue", queueMessage);
    } failed {
        println("Rollbacked");
    } committed {
        println("Committed");
    }
}
