package ballerina.jms;

public struct QueueConsumer {
    Session jmsSession;
    QueueConsumerEndpointConfiguration config;
}

public struct QueueConsumerEndpointConfiguration {
    string queueName;
    string identifier;
}

public struct QueueConsumerConnector {
}

public function <QueueConsumer ep> init(QueueConsumerEndpointConfiguration config) {
}

public function <QueueConsumer ep> register (typedesc serviceType) {
}

public function <QueueConsumer ep> start () {
}

public function <QueueConsumer ep> getClient () returns (QueueConsumerConnector) {
    return {};
}

public function <QueueConsumer ep> stop () {
}

