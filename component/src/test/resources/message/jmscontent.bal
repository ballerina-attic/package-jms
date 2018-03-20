import ballerina.net.jms;

function funcSetJMSTextContent(jms:Message msg, string content) (jms:Message) {
    msg.setTextMessageContent(content);
    return msg;
}
function funcGetJMSTextContent(jms:Message msg) (string) {
    return msg.getTextMessageContent();
}
function funcClearJMSTextContent(jms:Message msg) (string) {
    msg.clearBody();
    return msg.getTextMessageContent();
}
