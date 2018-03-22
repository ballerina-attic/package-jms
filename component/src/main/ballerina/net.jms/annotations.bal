package ballerina.net.jms;


@Description {value:"Configurations for a JMS service"}
@Field {value:"destination: "}
@Field {value:"acknowledgementMode: "}
@Field {value:"subscriptionId: "}
@Field {value:"clientId: "}
@Field {value:"properties: "}
public struct JmsServiceConfig {
    string destination;
    string connectionFactoryName = "ConnectionFactory";
    string destinationType = "queue";
    string acknowledgementMode = "AUTO_ACKNOWLEDGE";
    string subscriptionId;
    string clientId;
    map properties;
}

@Description {value:"Configurations annotation for a JMS service"}
public annotation <service> ServiceConfig JmsServiceConfig;
