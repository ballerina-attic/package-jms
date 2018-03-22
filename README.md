# Ballerina JMS Connector

Ballerina JMS Connector is used to connect Ballerina with JMS Message Brokers. With the JMS Connector Ballerina can act as JMS Message Consumers and JMS Message Producers.

Steps to configure,
1. Extract `ballerina-jms-connector-<version>.zip` and copy containing jars in to `<BRE_HOME>/bre/lib/`
2. Copy JMS Broker Client jars into `<BRE_HOME>/bre/lib/`

Ballerina as a JMS Consumer

```ballerina
import ballerina.net.jms;

@Description{value : "Service level annotation to provide connection details. Connection factory type can be either queue or topic depending on the requirement. "}
@jms:configuration {
    initialContextFactory:"wso2mbInitialContextFactory",
    providerUrl:
    "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
    connectionFactoryName:"QueueConnectionFactory",
    concurrentConsumers:300,
    destination:"MyQueue"
}
service<jms> jmsService {
    resource onMessage (jms:JMSMessage m) {

        // Retrieve the string payload using native function.
        string stringPayload = m.getTextMessageContent();

        // Print the retrieved payload.
        println("Payload: " + stringPayload);
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
