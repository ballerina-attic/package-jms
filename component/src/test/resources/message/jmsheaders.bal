import ballerina/net.jms;

function funcSetJMSCorrelationIDHeader (jms:Message msg, string value) (jms:Message) {
    msg.setCorrelationID(value);
    return msg;
}
function funcSetJMSDeliveryModeHeader (jms:Message msg, int value) (jms:Message) {
    msg.setDeliveryMode(value);
    return msg;
}
function funcSetJMSPriorityHeader (jms:Message msg, int value) (jms:Message) {
    msg.setPriority(value);
    return msg;
}
function funcSetJMSTypeHeader (jms:Message msg, string value) (jms:Message) {
    msg.setType(value);
    return msg;
}
function funcSetJMSExpirationHeader (jms:Message msg, int value) (jms:Message) {
    msg.setExpiration(value);
    return msg;
}

function funcGetJMSDeliveryModeHeader (jms:Message msg) (int) {
    return msg.getDeliveryMode();
}

function funcGetJMSExpirationHeader (jms:Message msg) (int) {
    return msg.getExpiration();
}

function funcGetJMSPriorityHeader (jms:Message msg) (int) {
    return msg.getPriority();
}

function funcGetJMSCorrelationIDHeader (jms:Message msg) (string) {
    return msg.getCorrelationID();
}

function funcGetJMSTypeHeader (jms:Message msg) (string) {
    return msg.getType();
}

function funcGetJMSMessageIDHeader (jms:Message msg) (string) {
    return msg.getMessageID();
}

function funcGetJMSTimestampHeader (jms:Message msg) (int) {
    return msg.getTimestamp();
}

function funcGetJMSRedeliveredHeader (jms:Message msg) (boolean) {
    return msg.getRedelivered();
}

