/*
*   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.ballerinalang.net.jms.nativeimpl.message;

import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.jms.BallerinaJMSMessage;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.nativeimpl.util.BTestUtils;
import org.ballerinalang.net.jms.nativeimpl.util.MockJMSMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Test cases for jms:JMSMessage get headers native functions.
 */
public class GetHeadersFunctionsTestCase {
    private CompileResult compileResult;
    private BStruct messageStruct;

    private String jmsMessageID = "message-1234";
    private long jmsTimestamp = 123456789;
    private String jmsCorrelationID = "correlation-1234";
    private int jmsDeliveryMode = 2;
    private int jmsPriority = 5;
    private boolean jmsRedelivered = true;
    private long jmsExpiration = 987654321;
    private String jmsType = "jms-my-type";

    @BeforeClass
    public void setup() throws JMSException {
        compileResult = BTestUtils.compile("message/jmsheaders.bal");
        Message jmsMessage = new MockJMSMessage();

        jmsMessage.setJMSMessageID(jmsMessageID);
        jmsMessage.setJMSTimestamp(jmsTimestamp);
        jmsMessage.setJMSCorrelationID(jmsCorrelationID);
        jmsMessage.setJMSDeliveryMode(jmsDeliveryMode);
        jmsMessage.setJMSPriority(jmsPriority);
        jmsMessage.setJMSRedelivered(jmsRedelivered);
        jmsMessage.setJMSExpiration(jmsExpiration);
        jmsMessage.setJMSType(jmsType);

        messageStruct = BTestUtils.createAndGetStruct(compileResult.getProgFile(), Constants.PROTOCOL_PACKAGE_JMS,
                Constants.JMS_MESSAGE_STRUCT_NAME);

        messageStruct.addNativeData(Constants.JMS_API_MESSAGE, new BallerinaJMSMessage(jmsMessage));
    }

    @Test(description = "Test Ballerina native JMSMessage getDeliveryMode ")
    public void testGetDeliveryMode() throws JMSException {
        int resultValue = 10;

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSDeliveryModeHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BInteger) {
            resultValue = (int) ((BInteger) returnBValues[0]).intValue();
        }

        Assert.assertEquals(resultValue, jmsDeliveryMode,
                "DeliveryMode header is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getTimestamp ")
    public void testGetTimestamp() throws JMSException {
        long resultValue = 0;

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSTimestampHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BInteger) {
            resultValue = ((BInteger) returnBValues[0]).intValue();
        }

        Assert.assertEquals(resultValue, jmsTimestamp,
                "Timestamp header is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getPriority ")
    public void testGetPriority() throws JMSException {
        int resultValue = 0;

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSPriorityHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BInteger) {
            resultValue = (int) ((BInteger) returnBValues[0]).intValue();
        }

        Assert.assertEquals(resultValue, jmsPriority,
                "Priority header is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getCorrelationID ")
    public void testGetCorrelationID() throws JMSException {
        String resultValue = "";

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSCorrelationIDHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BString) {
            resultValue = returnBValues[0].stringValue();
        }

        Assert.assertEquals(resultValue, jmsCorrelationID,
                "CorrelationID header is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getType ")
    public void testGetType() throws JMSException {
        String resultValue = "";

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSTypeHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BString) {
            resultValue = returnBValues[0].stringValue();
        }

        Assert.assertEquals(resultValue, jmsType, "Type header is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getMessageID ")
    public void testGetMessageID() throws JMSException {
        String resultValue = "";

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSMessageIDHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BString) {
            resultValue = returnBValues[0].stringValue();
        }

        Assert.assertEquals(resultValue, jmsMessageID,
                "MessageID header is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getExpiration ")
    public void testGetExpiration() throws JMSException {
        long resultValue = 0;

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSExpirationHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BInteger) {
            resultValue = ((BInteger) returnBValues[0]).intValue();
        }

        Assert.assertEquals(resultValue, jmsExpiration,
                "Expiration header is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getRedelivered ")
    public void testGetRedelivered() throws JMSException {
        boolean resultValue = false;

        BValue[] inputBValues = { messageStruct };
        BValue[] returnBValues = BTestUtils.invoke(compileResult, "funcGetJMSRedeliveredHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BBoolean) {
            resultValue = ((BBoolean) returnBValues[0]).booleanValue();
        }

        Assert.assertEquals(resultValue, jmsRedelivered,
                "Redelivered header is not correctly retrieved from the JMS Message");
    }
}
