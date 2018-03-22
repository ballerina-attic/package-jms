package ballerina.net.mb;

import ballerina/net.jms;
import ballerina/io;

public struct ConsumerEndpoint {
    jms:ConsumerEndpoint jmsConsumerEP;
    ServiceEndpointConfiguration config;
}

public struct ServiceEndpointConfiguration {
    string brokerUrl;
    string destinationType;
    boolean clientCaching = true;
    int connectionCount;
    int sessionCount;
    map properties;
}

public function <ServiceEndpointConfiguration config> ServiceEndpointConfiguration() {
    config.brokerUrl = "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'";
    config.destinationType = "queue";
    config.clientCaching = true;
    config.connectionCount = 5;
    config.sessionCount = 10;
}

public function <ConsumerEndpoint ep> ConsumerEndpoint () {
    ep.jmsConsumerEP = {};
}

public function <ConsumerEndpoint ep> init (ServiceEndpointConfiguration config) {
    jms:ServiceEndpointConfiguration jmsConf = {
       initialContextFactory:"wso2mbInitialContextFactory",
       providerUrl:config.brokerUrl,
       connectionFactoryName:"brokerConnectionFactory",
       destinationType:config.destinationType,
       clientCaching:config.clientCaching,
       connectionCount:config.connectionCount,
       sessionCount:config.sessionCount,
       properties:config.properties
    };
    ep.jmsConsumerEP.init(jmsConf);
}

public function <ConsumerEndpoint ep> register (typedesc serviceType) {
    ep.jmsConsumerEP.register(serviceType);
}

public function <ConsumerEndpoint ep> start () {
    ep.jmsConsumerEP.start();
}

public function <ConsumerEndpoint ep> getClient () returns (jms:Context) {
    return {};
}

public function <ConsumerEndpoint ep> stop () {
    ep.jmsConsumerEP.stop();
}
