package ballerina.net.jms;


@Description {value:"Configurations for a JMS service"}
@Field {value:"initialContextFactory: "}
@Field {value:"providerUrl: "}
@Field {value:"connectionFactoryType: "}
@Field {value:"connectionFactoryName: "}
@Field {value:"destination: "}
@Field {value:"acknowledgementMode: "}
@Field {value:"subscriptionId: "}
@Field {value:"clientId: "}
@Field {value:"configFilePath: "}
@Field {value:"connectionFactoryNature: "}
@Field {value:"concurrentConsumers: "}
@Field {value:"connectionUsername: "}
@Field {value:"initialContextFactory: "}
@Field {value:"connectionPassword: "}
@Field {value:"properties: "}
public struct JmsServiceConfig {
    string initialContextFactory;
    string providerUrl;
    string connectionFactoryType;
    string connectionFactoryName;
    string destination;
    string acknowledgementMode;
    string subscriptionId;
    string clientId;
    string configFilePath;
    string connectionFactoryNature;
    int concurrentConsumers;
    string connectionUsername;
    string connectionPassword;
    string[] properties;
}

@Description {value:"Configurations annotation for a JMS service"}
public annotation <service> serviceConfig JmsServiceConfig;
