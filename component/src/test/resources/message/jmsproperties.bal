import ballerina/net.jms;

function funcSetStringProperty (jms:Message msg, string name, string value) returns (jms:Message) {
    msg.setStringProperty(name, value);
    return msg;
}
function funcSetIntProperty (jms:Message msg, string name, int value) returns (jms:Message) {
    msg.setIntProperty(name, value);
    return msg;
}
function funcSetBooleanProperty (jms:Message msg, string name, boolean value) returns (jms:Message) {
    msg.setBooleanProperty(name, value);
    return msg;
}
function funcSetFloatProperty (jms:Message msg, string name, float value) returns (jms:Message) {
    msg.setFloatProperty(name, value);
    return msg;
}

function funcGetStringProperty (jms:Message msg, string name) returns (string) {
    return msg.getStringProperty(name);
}
function funcGetIntProperty (jms:Message msg, string name) returns (int) {
    return msg.getIntProperty(name);
}
function funcGetBooleanProperty (jms:Message msg, string name) returns (boolean) {
    return msg.getBooleanProperty(name);
}
function funcGetFloatProperty (jms:Message msg, string name) returns (float) {
    return msg.getFloatProperty(name);
}
