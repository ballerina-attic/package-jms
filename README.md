# Ballerina JMS Connector

Ballerina JMS Connector is used to connect Ballerina with JMS Message Brokers. With the JMS Connector Ballerina can act as JMS Message Consumers and JMS Message Producers.

Steps to configure,
1. Extract `ballerina-jms-connector-<version>.zip` and copy containing jars in to `<BRE_HOME>/bre/lib/`
2. Copy JMS Broker Client jars into `<BRE_HOME>/bre/lib/`

Ballerina as a JMS Consumer

```ballerina
import ballerina/net.jms;
import ballerina/io;

endpoint jms:ConsumerEndpoint ep1 {
    initialContextFactory: "wso2mbInitialContextFactory",
    providerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
};

@jms:ServiceConfig {
    destination: "testQueue"
}
service<jms:Service> jmsService bind ep1 {

    onMessage (endpoint client, jms:Message message) {
        string messageText = message.getTextMessageContent();
        io:println("Message: " + messageText);
    }
}
````
    
 
Ballerina as a JMS Producer

```ballerina
import ballerina/net.jms;

endpoint jms:ClientEndpoint jmsEP {
    initialContextFactory:"wso2mbInitialContextFactory",
    providerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
};

function main (string[] args) {
    // Create a Text message.
    jms:Message queueMessage = jmsEP.createTextMessage("Hello from Ballerina!");
    // Send the Ballerina message to the JMS provider.
    jmsEP->send("MyQueue", queueMessage);
}

````
     


 For more JMS Connector Ballerina configurations please refer to the samples directory.
