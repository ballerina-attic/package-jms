import ballerina/net.jms;
import ballerina/net.http;
import ballerina/util;

@http:configuration {
    basePath:"/hello"
}
service<http> helloService  {

    endpoint<jms:JmsClient> jmsEP {
        create jms:JmsClient(getConnectorConfig());
    }

    @http:resourceConfig {
        methods:["POST"],
        path:"/sayHello"
    }
    resource sayHello (http:Request httpReq, http:Response httpRes) {

        string requestQueueName = "RequestQueue";
        string responseQueueName = "ResponseQueue";
        int pollWaitTimeout = 10000;

        string correlationID = util:uuid();
        string messageSelector = "JMSCorrelationID = '" + correlationID + "'";

        // create the jms message with ReplyTo header and correlationID
        jms:JMSMessage jmsReq = jms:createTextMessage(getConnectorConfig());
        jmsReq.setTextMessageContent(httpReq.getStringPayload());
        jmsReq.setCorrelationID(correlationID);
        jmsReq.setReplyTo(responseQueueName);

        // send to jms request queue
        jmsEP.send(requestQueueName, jmsReq);

        // poll and wait for response message using the correlationID selector
        jms:JMSMessage jmsRes = jmsEP.pollWithSelector(responseQueueName, pollWaitTimeout, messageSelector);

        // respond to the client accordingly
        // if the backend didn't reply within interval
        if (jmsRes == null) {
            httpRes.setStatusCode(504);
            _ = httpRes.send();
            return;
        }

        // if the backend response received
        string responseBody = jmsRes.getTextMessageContent();
        var jsonContent,_ = <json> responseBody;
        httpRes.setJsonPayload(jsonContent);
        httpRes.setStatusCode(200);
        _ = httpRes.send();
    }
}

@jms:configuration {
    initialContextFactory:"wso2mbInitialContextFactory",
    providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
    connectionFactoryName: "QueueConnectionFactory",
    destination:"RequestQueue"
}
service<jms> jmsService  {

    endpoint<jms:JmsClient> jmsEP {
        create jms:JmsClient(getConnectorConfig());
    }

    resource jmsResource (jms:JMSMessage request) {

        print("JMS backend received the request: ");
        println(request.getTextMessageContent());

        // process the request and create the response
        jms:JMSMessage response = jms:createTextMessage(getConnectorConfig());
        response.setTextMessageContent("{\"Hello\" :\"From JMS Backend\"}");
        response.setCorrelationID(request.getCorrelationID());

        // push response to the ReplyTo queue
        jmsEP.send(request.getReplyTo(), response);
    }
}

function getConnectorConfig () (jms:ClientProperties) {
    // We define the connection properties as a map. 'providerUrl' or 'configFilePath' and the 'initialContextFactory' vary according to the JMS provider you use.
    // In this example we connect to the WSO2 MB server.
    jms:ClientProperties properties = {   initialContextFactory:"wso2mbInitialContextFactory",
                                          providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
                                          connectionFactoryName: "QueueConnectionFactory"};
    return properties;
}
