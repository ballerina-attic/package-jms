import ballerina/io;
import ballerina/net.jms;
import ballerina/net.mb;

endpoint mb:ClientEndpoint mbEP {
    brokerUrl: "amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'"
};

function main (string[] args) {
    // Poll message from message broker
    var result = mbEP->receive("MyQueue", 1000);

    match result {
        jms:Message message => {
            io:println(message.getTextMessageContent());
        }
        any | null => {
            io:println("No message recieved");
        }
    }
}
