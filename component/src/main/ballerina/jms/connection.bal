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

public native function init(ConnectionConfiguration config);
