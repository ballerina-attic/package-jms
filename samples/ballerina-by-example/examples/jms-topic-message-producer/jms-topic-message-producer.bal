import ballerina/net.jms;

endpoint jms:ClientEndpoint jmsEP {
    initialContextFactory:"wso2mbInitialContextFactory",
    providerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
    connectionFactoryName: "TopicConnectionFactory",
    destinationType : "topic"
};

function main (string[] args) {
    // Create an empty Ballerina message.
    jms:Message queueMessage = jmsEP.createTextMessage("Hello from Ballerina!");
    // Send the Ballerina message to the JMS provider.
    jmsEP->send("mytopic", queueMessage);
}
