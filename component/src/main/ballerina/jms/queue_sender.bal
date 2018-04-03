package ballerina.jms;

import ballerina/log;

public struct QueueSender {
    QueueSenderConnector connector;
    QueueSenderEndpointConfiguration config;
}

public function <QueueSender sender> QueueSender() {
    sender.connector = {};
}

public struct QueueSenderConnector {

}

public struct QueueSenderEndpointConfiguration {
    Session session;
    string queueName;
}

public function <QueueSender ep> init(QueueSenderEndpointConfiguration config) {
    log:printInfo("Queue consumer init called");
    ep.config = config;
    SessionConnector sessionConnector = config.session.getClient();
    ep.initQueueSender(sessionConnector);
}

public native function <QueueSender ep> initQueueSender(SessionConnector connector);

public function <QueueSender ep> register (typedesc serviceType) {
    log:printInfo("Queue consumer register called");
}

public function <QueueSender ep> start () {
    log:printInfo("Queue consumer start called");
}

public function <QueueSender ep> getClient () returns (QueueSenderConnector) {
    return ep.connector;
}

public function <QueueSender ep> stop () {
    log:printInfo("Queue consumer stop called");
}


