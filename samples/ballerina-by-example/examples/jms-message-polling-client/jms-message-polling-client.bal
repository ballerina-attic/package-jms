import ballerina.net.jms;

function main (string[] args) {
    jmsClientConsume();
}

function jmsClientConsume() {
    endpoint<jms:JmsClient> jmsEP {
        create jms:JmsClient (getConnectorConfig());
    }

    // Poll message from message broker
    jms:JMSMessage message = jmsEP.poll("MyQueue", 1000);
    if (message != null) {
        println(message.getTextMessageContent());
    }
}

function getConnectorConfig () (jms:ClientProperties) {
     // We define the connection properties as a map. 'providerUrl' or 'configFilePath' and the 'initialContextFactory' vary according to the JMS provider you use.
     // In this example we connect to the WSO2 MB server.
    jms:ClientProperties properties = {   initialContextFactory:"wso2mbInitialContextFactory",
                                          providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
                                          connectionFactoryName: "QueueConnectionFactory",
                                          connectionFactoryType : "queue"};
    return properties;
}