import ballerina/jms;
import ballerina/log;

//endpoint jms:Connection jmsConnection {
//    initialContext:"wso2mbInitialContextFactory",
//    providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
//};
//
//endpoint jms:Session jmsSession {
//    connection:jmsConnection,
//    ackMode:AUTO_ACKNOWLEDGE
//};



public function main (string[] args) {
    endpoint jms:QueueSender queueSender {
                                      ////    session: jmsSession, // mandatory
                                          queueName:"requestQueue"// mandatory
                                      };

    //jms:Message m = jmsSession.createTextMessage("Test Text");
    log:printInfo("Main called");
 //   queueSender -> send(m);
}