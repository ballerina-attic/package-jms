# Ballerina JMS Connector

Ballerina JMS Connector is used to connect Ballerina with JMS Message Brokers. With the JMS Connector Ballerina can act as JMS Message Consumers and JMS Message Producers.

Steps to configure,
1. Copy following jars in to <BRE_HOME>/bre/lib/
2. Copy JMS Broker Client jars into <BRE_HOME>/bre/lib/
3. (Optional) Copy src/net/jms into Ballerina src

Ballerina as a JMS Consumer

    import ballerina.lang.system;
    import ballerina.net.jms;
    import ballerina.doc;
     
    @doc:Description{value : "Service level annotation to provide connection details. Connection factory type can be either queue or topic depending on the requirement. "}
    @jms:configuration {
        initialContextFactory:"wso2mbInitialContextFactory",
        providerUrl:
        "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
        connectionFactoryType:"queue",
        connectionFactoryName:"QueueConnectionFactory",
        destination:"MyQueue"
    }
    service<jms> jmsService {
        resource onMessage (jms:JMSMessage m) {
     
            // Retrieve the string payload using native function.
            string stringPayload = m.getTextMessageContent();
     
            // Print the retrieved payload.
            system:println("Payload: " + stringPayload);
        }
    }
    
 
Ballerina as a JMS Producer

    import ballerina.net.jms;
     
    function main (string[] args) {
        jmsSender();
    }
     
    function jmsSender() {
        // We define the connection properties as a map. 'providerUrl' or 'configFilePath' and the 'initialContextFactory' vary according to the JMS provider you use.
        // In this example we connect to the WSO2 MB server.
        jms:ClientConnector jmsEP;
        map properties = {
                             "initialContextFactory":"wso2mbInitialContextFactory",
                             "configFilePath":"../jndi.properties",
                             "connectionFactoryName": "QueueConnectionFactory",
                             "connectionFactoryType" : "queue"};
                              
        // Create the JMS client Connector using the connection properties we defined earlier.
        jmsEP = create jms:ClientConnector(properties);
         
        // Create an empty Ballerina message.
        jms:JMSMessage queueMessage = jms:createTextMessage(jmsEP);
         
        // Set a string payload to the message.
        queueMessage.setTextMessageContent("Hello from Ballerina!");
         
        // Send the Ballerina message to the JMS provider.
        jmsEP.send("MyQueue", queueMessage);
    }
     


 For more JMS Connector Ballerina configurations please refer to the samples directory.
