import ballerina.net.jms;

@jms:configuration {
    initialContextFactory:"wso2mbInitialContextFactory",
    providerUrl:
    "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
    connectionFactoryName:"QueueConnectionFactory",
    destination:"MyQueue",
    acknowledgementMode:jms:CLIENT_ACKNOWLEDGE
}
service<jms> jmsService {
    resource onMessage (jms:JMSMessage request) {
        jms:ClientConnector jmsEP;
        jms:ConnectorProperties conProperties = {
                            initialContextFactory:"wso2mbInitialContextFactory",
                            configFilePath:"../jndi.properties",
                            connectionFactoryName: "QueueConnectionFactory",
                            connectionFactoryType : "queue",
                             acknowledgementMode: "SESSION_TRANSACTED"
                         };

        //Process the message
        println("Payload: " + request.getTextMessageContent());

        jmsEP = create jms:ClientConnector(conProperties);
        jms:JMSMessage message2 = jms:createTextMessage(jmsEP);
        message2.setTextMessageContent("{\"WSO2\":\"Ballerina\"}");
        transaction {
            jmsEP.send("MyQueue2", message2);
            jmsEP.send("MyQueue3", message2);
        } failed {
            println("Reliable delivery process failed and rollbacked");
            request.acknowledge(jms:DELIVERY_ERROR);
        } committed {
            println("Reliable delivery process successed and committed");
            request.acknowledge(jms:DELIVERY_SUCCESS);
        }
    }
}

