import ballerina.net.jms;
import ballerina.lang.system;

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
        map properties = {   "initialContextFactory":"wso2mbInitialContextFactory",
                             "configFilePath":"../jndi.properties",
                             "connectionFactoryName": "QueueConnectionFactory",
                             "connectionFactoryType" : "queue",
                             "acknowledgementMode": jms:SESSION_TRANSACTED
                         };

        //Process the message
        system:println("Payload: " + request.getTextMessageContent());

        jmsEP = create jms:ClientConnector(properties);
        jms:JMSMessage message2 = jms:createTextMessage(jmsEP);
        message2.setTextMessageContent("{\"WSO2\":\"Ballerina\"}");
        transaction {
            jmsEP.send("MyQueue2", message2);
            jmsEP.send("MyQueue3", message2);
        } failed {
            system:println("Reliable delivery process failed and rollbacked");
            request.acknowledge(jms:DELIVERY_ERROR);
        } committed {
            system:println("Reliable delivery process successed and committed");
            request.acknowledge(jms:DELIVERY_SUCCESS);
        }
    }
}

