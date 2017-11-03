import ballerina.net.jms;

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
        jms:ClientConnector jmsEP;

        // Get and Print message properties values.
        // Ballerina Supports JMS property types of string, boolean, float and int
        println("String Property : " + m.getStringProperty("string-prop"));
        println("Boolean Property : " + m.getBooleanProperty("boolean-prop"));
        println("----------------------------------");

        jms:ConnectorProperties conProperties = {
            initialContextFactory:"wso2mbInitialContextFactory",
            configFilePath:"../jndi.properties",
            connectionFactoryName: "QueueConnectionFactory",
            connectionFactoryType : "queue"
                                                };

        jmsEP = create jms:ClientConnector(conProperties);
        jms:JMSMessage responseMessage = jms:createTextMessage(jmsEP);

        responseMessage.setIntProperty("int-prop",777);
        responseMessage.setFloatProperty("float-prop",123);

        jmsEP.send("MySecondQueue", responseMessage);
    }
}
