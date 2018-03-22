import ballerina/net.jms;

function testAcknowledge(jms:Message msg, string s) {
    msg.acknowledge(s);
}

function testCommit(jms:Message msg) {
    msg.commit();
}

function testRollback(jms:Message msg) {
    msg.rollback();
}
