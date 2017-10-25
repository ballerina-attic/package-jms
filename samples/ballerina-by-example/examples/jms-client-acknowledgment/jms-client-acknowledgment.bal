import ballerina.lang.system;
import ballerina.net.jms;

@jms:configuration {
    initialContextFactory:"wso2mbInitialContextFactory",
    providerUrl:
    "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
    connectionFactoryName:"QueueConnectionFactory",
    destination:"MyQueue",
    connectionFactoryType:jms:TYPE_QUEUE,
    acknowledgementMode:jms:CLIENT_ACKNOWLEDGE
}
service<jms> jmsService {
    resource onMessage (jms:JMSMessage m) {

        string stringPayload = m.getTextMessageContent();
        system:println("Payload: " + stringPayload);
        // acknowledge the message with positive acknowledgment. If we want to reject the message due to some error
        // use 'jms:DELIVERY_ERROR'
        m.acknowledge(jms:DELIVERY_SUCCESS);

    }
}
