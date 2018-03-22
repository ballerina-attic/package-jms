package ballerina.net.jms;

public struct ConsumerEndpoint {
    Context context;
    ServiceEndpointConfiguration config;
}

public struct ServiceEndpointConfiguration {
    string initialContextFactory;
    string providerUrl;
    string connectionFactoryName = "QueueConnectionFactory";
    string connectionFactoryType = "queue";
    boolean clientCaching = true;
    string connectionUsername;
    string connectionPassword;
    string configFilePath;
    int connectionCount = 5;
    int sessionCount = 10;
    map properties;
}

public function <ConsumerEndpoint ep> init (ServiceEndpointConfiguration config) {
    ep.config = config;
    var err = ep.initEndpoint();
    if (err != null ) {
        throw err;
    }
}

public native function<ConsumerEndpoint ep> initEndpoint () returns (error);

public native function <ConsumerEndpoint ep> register (typedesc serviceType);

public native function <ConsumerEndpoint ep> start ();

public native function <ConsumerEndpoint ep> getClient () returns (Context);

public native function <ConsumerEndpoint ep> stop ();
