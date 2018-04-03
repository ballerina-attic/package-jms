package ballerina.jms;


public struct Connection {
    ConnectionConfiguration config;
}

public struct ConnectionConfiguration {
    string initialContextFactory;
    string providerUrl;
    string connectionFactoryName;
    map properties;

}

public function <ConnectionConfiguration config> ConnectionConfiguraton() {
    config.initialContextFactory = "wso2mbInitialContextFactory";
    config.providerUrl = "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'";
    config.connectionFactoryName = "ConnectionFactory";
}

public function<Connection connection> init(ConnectionConfiguration config) {
    connection.config = config;
    connection.initEndpoint();
}

public struct ConnectionClient {}

public native function<Connection connection> initEndpoint();

public native function <Connection connection> register (typedesc serviceType);

public native function <Connection connection> start ();

public native function <Connection connection> getClient () returns (ConnectionClient);

public native function <Connection connection> stop ();
