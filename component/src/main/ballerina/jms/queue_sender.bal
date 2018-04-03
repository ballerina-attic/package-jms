package ballerina.jms;

import ballerina/log;

public struct QueueSender {
    //Session jmsSession;
    QueueSenderEndpointConfiguration config;
}

public struct QueueSenderConnector {

}

public struct QueueSenderEndpointConfiguration {
    string queueName;
}

public function <QueueSender ep> init(QueueSenderEndpointConfiguration config) {
    log:printInfo("Queue consumer init called");
}

public function <QueueSender ep> register (typedesc serviceType) {
    log:printInfo("Queue consumer register called");
}

public function <QueueSender ep> start () {
    log:printInfo("Queue consumer start called");
}

public function <QueueSender ep> getClient () returns (QueueSenderConnector) {
    log:printInfo("Queue consumer getClient called");
    return {};
}

public function <QueueSender ep> stop () {
    log:printInfo("Queue consumer stop called");
}


