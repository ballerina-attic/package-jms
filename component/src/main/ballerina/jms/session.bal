package ballerina.jms;

public struct Session {
    Connection jmsConnection;
    SessionConfiguration config;
}

public struct SessionConfiguration {
    string acknowledgementMode;
}


public struct SessionConnector {
}

public function <Session ep> init(SessionConfiguration config) {
}

public function <Session ep> register (typedesc serviceType) {
}

public function <Session ep> start () {
}

public function <Session ep> getClient () returns (SessionConnector) {
    return {};
}

public function <Session ep> stop () {
}
