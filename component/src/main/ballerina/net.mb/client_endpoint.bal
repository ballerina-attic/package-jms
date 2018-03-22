package ballerina.net.mb;

import ballerina/net.jms;

@Description {value:"Represents a JMS client endpoint"}
@Field {value:"epName: The name of the endpoint"}
@Field {value:"config: The configurations associated with the endpoint"}
public struct ClientEndpoint {
    jms:ClientEndpoint jmsClientEP;
    ClientEndpointConfiguration config;
}

@Description { value:"JMS Client connector properties to pass JMS client connector configurations"}
@Field {value:"initialContextFactory: Initial context factory name, specific to the provider"}
@Field {value:"providerUrl: Connection URL of the provider"}
@Field {value:"connectionFactoryName: Name of the connection factory"}
@Field {value:"connectionFactoryType: Type of the connection factory (queue/topic)"}
@Field {value:"acknowledgementMode: Ack mode (auto-ack, client-ack, dups-ok-ack, transacted, xa)"}
@Field {value:"clientCaching: Is client caching enabled (default: enabled)"}
@Field {value:"connectionUsername: Connection factory username"}
@Field {value:"connectionPassword: Connection factory password"}
@Field {value:"configFilePath: Path to be used for locating jndi configuration"}
@Field {value:"connectionCount: Number of pooled connections to be used in the transport level (default: 5)"}
@Field {value:"sessionCount: Number of pooled sessions to be used per connection in the transport level (default: 10)"}
@Field {value:"properties: Additional Properties"}
public struct ClientEndpointConfiguration {
    string brokerUrl;
    string destinationType;
    string acknowledgementMode;
    boolean clientCaching = true;
    int connectionCount;
    int sessionCount;
    map properties;
}

public function <ClientEndpointConfiguration config> ClientEndpointConfiguration() {
    config.brokerUrl = "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'";
    config.destinationType = "queue";
    config.acknowledgementMode = "AUTO_ACKNOWLEDGE";
    config.clientCaching = true;
    config.connectionCount = 5;
    config.sessionCount = 10;
}

public struct ClientConnector {
    ClientEndpoint clientEP;
}

public function <ClientEndpoint ep> init (ClientEndpointConfiguration config) {
    endpoint jms:ClientEndpoint jmsEP {
        initialContextFactory: "wso2mbInitialContextFactory",
        providerUrl: config.brokerUrl,
        connectionFactoryName: "brokerCnnectionFactory",
        destinationType: config.destinationType,
        acknowledgementMode: config.acknowledgementMode,
        clientCaching: config.clientCaching,
        connectionCount: config.connectionCount,
        sessionCount: config.sessionCount,
        properties: config.properties
    };
    ep.jmsClientEP = jmsEP;
    ep.config = config;
}

public function<ClientEndpoint ep> createTextMessage (string content) returns (jms:Message) {
    jms:Message msg = ep.jmsClientEP.createTextMessage(content);
    return msg;
}

public function <ClientEndpoint ep> register (typedesc serviceType) {

}

public function <ClientEndpoint ep> start () {

}

@Description { value:"Returns the connector that client code uses"}
@Return { value:"The connector that client code uses" }
public function <ClientEndpoint ep> getClient () returns (ClientConnector) {
    return {clientEP:ep};
}

@Description { value:"Stops the registered service"}
@Return { value:"Error occured during registration" }
public function <ClientEndpoint ep> stop () {

}

@Description {value:"SEND action implementation of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"message: Message"}
public function<ClientConnector ep> send (string destinationName, jms:Message m) {
    endpoint jms:ClientEndpoint jmsClient = ep.clientEP.jmsClientEP;
    jmsClient->send(destinationName, m);
}


@Description {value:"POLL action implementation of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"time: Timeout that needs to blocked on"}
public function<ClientConnector ep> poll (string destinationName, int time) returns (jms:Message | null) {
    endpoint jms:ClientEndpoint jmsClient = ep.clientEP.jmsClientEP;
    var result = jmsClient->poll(destinationName, time);
    return result;
}

@Description {value:"POLL action implementation with selector support of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"time: Timeout that needs to blocked on"}
@Param {value:"selector: Selector to filter out messages"}
public function <ClientConnector ep> pollWithSelector (string destinationName, int time, string selector)
                                                                                        returns (jms:Message| null) {
    endpoint jms:ClientEndpoint jmsClient = ep.clientEP.jmsClientEP;
    var result = jmsClient->pollWithSelector(destinationName, time, selector);
    return result;
}
