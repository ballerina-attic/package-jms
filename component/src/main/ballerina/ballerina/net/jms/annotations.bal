package ballerina.net.jms;


@Description {value:"Configurations for a JMS service"}
@Field {value:"destination: "}
@Field {value:"acknowledgementMode: "}
@Field {value:"subscriptionId: "}
@Field {value:"clientId: "}
@Field {value:"properties: "}
public struct JmsServiceConfig {
    string destination;
    string acknowledgementMode = "AUTO_ACKNOWLEDGE";
    string subscriptionId;
    string clientId;
    string[] properties;
}

@Description {value:"Configurations annotation for a JMS service"}
public annotation <service> serviceConfig JmsServiceConfig;
