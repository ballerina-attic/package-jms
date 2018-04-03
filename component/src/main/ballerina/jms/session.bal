package ballerina.jms;

public struct Session {
    Connection jmsConnection;
    SessionConfiguration config;
}

public struct SessionConfiguration {
    string acknowledgementMode;
}
