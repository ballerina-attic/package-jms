import ballerina/jms;
import ballerina/log;

endpoint jms:Connection jmsConnection {
    initialContextFactory:"wso2mbInitialContextFactory",
    providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
};

endpoint jms:Session jmsSession {
    connection:jmsConnection,
    acknowledgementMode: "AUTO_ACKNOWLEDGE"
};

endpoint jms:QueueSender queueSender {
    session: jmsSession, // mandatory
    queueName:"requestQueue"// mandatory
};

public function main (string[] args) {
    //jms:Message m = jmsSession.createTextMessage("Test Text");
    log:printInfo("Main called");
 //   queueSender -> send(m);
}