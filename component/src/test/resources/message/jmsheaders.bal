import ballerina/net.jms;

function funcSetJMSCorrelationIDHeader (jms:Message msg, string value) returns (jms:Message) {
    msg.setCorrelationID(value);
    return msg;
}
function funcSetJMSDeliveryModeHeader (jms:Message msg, int value) returns (jms:Message) {
    msg.setDeliveryMode(value);
    return msg;
}
function funcSetJMSPriorityHeader (jms:Message msg, int value) returns (jms:Message) {
    msg.setPriority(value);
    return msg;
}
function funcSetJMSTypeHeader (jms:Message msg, string value) returns (jms:Message) {
    msg.setType(value);
    return msg;
}
function funcSetJMSExpirationHeader (jms:Message msg, int value) returns (jms:Message) {
    msg.setExpiration(value);
    return msg;
}

function funcGetJMSDeliveryModeHeader (jms:Message msg) returns (int) {
    return msg.getDeliveryMode();
}

function funcGetJMSExpirationHeader (jms:Message msg) returns (int) {
    return msg.getExpiration();
}

function funcGetJMSPriorityHeader (jms:Message msg) returns (int) {
    return msg.getPriority();
}

function funcGetJMSCorrelationIDHeader (jms:Message msg) returns (string) {
    return msg.getCorrelationID();
}

function funcGetJMSTypeHeader (jms:Message msg) returns (string) {
    return msg.getType();
}

function funcGetJMSMessageIDHeader (jms:Message msg) returns (string) {
    return msg.getMessageID();
}

function funcGetJMSTimestampHeader (jms:Message msg) returns (int) {
    return msg.getTimestamp();
}

function funcGetJMSRedeliveredHeader (jms:Message msg) returns (boolean) {
    return msg.getRedelivered();
}

