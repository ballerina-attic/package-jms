import ballerina.lang.system;
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

        // Read all the supported headers from the message.
        string correlationId = m.getCorrelationID();
        int timestamp = m.getTimestamp();
        string messageType = m.getType();
        string messageId = m.getMessageID();
        boolean redelivered = m.getRedelivered();
        int expirationTime = m.getExpiration();
        int priority = m.getPriority();
        int deliveryMode = m.getDeliveryMode();

        // Print the header values.
        system:println("correlationId : " + correlationId);
        system:println("timestamp : " + timestamp);
        system:println("message type : " + messageType);
        system:println("message id : " + messageId);
        system:println("is redelivered : " + redelivered);
        system:println("expiration time : " + expirationTime);
        system:println("priority : " + priority);
        system:println("delivery mode : " + deliveryMode);
        system:println("----------------------------------");

        map properties = {
                             "initialContextFactory":"wso2mbInitialContextFactory",
                             "configFilePath":"../jndi.properties",
                             "connectionFactoryName": "QueueConnectionFactory",
                             "connectionFactoryType" : "queue"};

        jmsEP = create jms:ClientConnector(properties);
        jms:JMSMessage responseMessage = jms:createTextMessage(jmsEP);

        responseMessage.setCorrelationID("response-001");
        responseMessage.setPriority(8);
        responseMessage.setDeliveryMode(1);
        responseMessage.setTextMessageContent("{\"WSO2\":\"Ballerina\"}");
        responseMessage.setType("application/json");

        jmsEP.send("MySecondQueue", responseMessage);
    }
}
