import ballerina/io;
import ballerina/net.jms;

endpoint jms:ClientEndpoint jmsEP {
    initialContextFactory:"wso2mbInitialContextFactory",
    configFilePath:"../../../resources/jndi.properties",
    connectionFactoryName: "QueueConnectionFactory",
    connectionFactoryType : "queue",
    acknowledgementMode: "XA_TRANSACTED"
};

function main (string[] args) {
    // Create an empty Ballerina message.
    jms:JMSMessage queueMessage = jmsEP.createTextMessage();
    // Set a string payload to the message.
    queueMessage.setTextMessageContent("Hello from Ballerina!");

    // Send the Ballerina message to the JMS provider using Ballerina-JMS XA transactions.
    // XA transactions can use used when multiple JMS Client Connector sends or other any other Ballerina Transacted
    // action(s) in present.
    // This example scenario can be achieved with Local transactions as well and it is the ideal way to do it. XA is
    // used for demonstration purpose
    transaction {
        jmsEP->send("MyQueue", queueMessage);
        //jmsEP->send("MySecondQueue", queueMessage);
    } onretry {
        io:println("Rollbacked");
    }
}
