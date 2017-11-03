import ballerina.net.jms;

function testAcknowledge(jms:JMSMessage msg, string s){
    msg.acknowledge(s);
}

function testCommit(jms:JMSMessage msg){
    msg.commit();
}

function testRollback(jms:JMSMessage msg){
    msg.rollback();
}
