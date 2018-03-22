import ballerina/net.jms;

function funcSetJMSTextContent(jms:Message msg, string content) returns (jms:Message) {
    msg.setTextMessageContent(content);
    return msg;
}
function funcGetJMSTextContent(jms:Message msg) returns (string) {
    return msg.getTextMessageContent();
}
function funcClearJMSTextContent(jms:Message msg) returns (string) {
    msg.clearBody();
    return msg.getTextMessageContent();
}
