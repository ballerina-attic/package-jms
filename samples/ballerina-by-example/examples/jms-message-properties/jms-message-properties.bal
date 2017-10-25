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
        string correlationId = m.getCorrelationID(m);
        int timestamp = m.getTimestamp(m);
        string messageType = m.getType(m);
        string messageId = m.getMessageID(m);
        boolean redelivered = m.getRedelivered(m);
        int expirationTime = m.getExpiration(m);
        int priority = m.getPriority(m);
        int deliveryMode = m.getDeliveryMode(m);

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

        responseMessage.setCorrelationID(responseMessage, "response-001");
        responseMessage.setPriority(responseMessage, 8);
        responseMessage.setDeliveryMode(responseMessage, 1);
        responseMessage.setTextMessageContent(responseMessage, "{\"WSO2\":\"Ballerina\"}");
        responseMessage.setType(responseMessage, "application/json");

        jmsEP.send("MySecondQueue", responseMessage);
    }
}
