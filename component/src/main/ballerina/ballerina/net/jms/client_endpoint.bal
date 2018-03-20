package ballerina.net.jms;


@Description {value:"Represents a JMS client endpoint"}
@Field {value:"epName: The name of the endpoint"}
@Field {value:"config: The configurations associated with the endpoint"}
public struct ClientEndpoint {
    string epName;
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
    string initialContextFactory;
    string providerUrl;
    string connectionFactoryName;
    string connectionFactoryType = "queue";
    string acknowledgementMode = "AUTO_ACKNOWLEDGE";
    boolean clientCaching = true;
    string connectionUsername;
    string connectionPassword;
    string configFilePath;
    int connectionCount = 5;
    int sessionCount = 10;
    map properties;
}

public struct ClientConnector {
    string connectorId;
    ClientEndpointConfiguration config;
}

public function <ClientEndpoint ep> init (ClientEndpointConfiguration config) {
    ep.config = config;
    ep.initEndpoint();
}

public native function<ClientEndpoint ep> initEndpoint ();

public native function<ClientEndpoint ep> createTextMessage () (Message);

public function <ClientEndpoint ep> register (typedesc serviceType) {

}

public function <ClientEndpoint ep> start () {

}

@Description { value:"Returns the connector that client code uses"}
@Return { value:"The connector that client code uses" }
public native function <ClientEndpoint ep> getClient () (ClientConnector);

@Description { value:"Stops the registered service"}
@Return { value:"Error occured during registration" }
public function <ClientEndpoint ep> stop () {

}

@Description {value:"SEND action implementation of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"message: Message"}
public native function<ClientConnector ep> send (string destinationName, Message m);

@Description {value:"POLL action implementation of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"time: Timeout that needs to blocked on"}
public native function<ClientConnector ep> poll (string destinationName, int time) (Message);

@Description {value:"POLL action implementation with selector support of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"time: Timeout that needs to blocked on"}
@Param {value:"selector: Selector to filter out messages"}
public native function<ClientConnector ep> pollWithSelector (string destinationName, int time, string selector) (Message);
