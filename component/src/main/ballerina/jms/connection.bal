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

public struct ConnectionConnector {
}

public function <Connection ep> init(ConnectionConfiguration config) {
}

public function <Connection ep> register (typedesc serviceType) {
}

public function <Connection ep> start () {
}

public function <Connection ep> getClient () returns (ConnectionConnector) {
    return {};
}

public function <Connection ep> stop () {
}