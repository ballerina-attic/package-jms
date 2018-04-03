package ballerina.jms;

public struct QueueConsumer {
    Session jmsSession;
    QueueConsumerEndpointConfiguration config;
}

public struct QueueConsumerEndpointConfiguration {
    string queueName
    string identifier
}

public native function <QueueConsumer ep> init(QueueConsumerEndpointConfiguration config);


