package ballerina.net.jms;

public struct ServiceEndpoint {
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

public function <ServiceEndpoint ep> init (ServiceEndpointConfiguration config) {
    ep.config = config;
    var err = ep.initEndpoint();
    if (err != null ) {
        throw err;
    }
}

public native function<ServiceEndpoint  ep> initEndpoint () returns (error);

public native function <ServiceEndpoint ep> register(type ServiceType);

public native function <ServiceEndpoint ep> start();

public native function <ServiceEndpoint ep> getClient () returns (Context);

public native function <ServiceEndpoint ep> stop();
