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
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.jms.BallerinaJMSMessage;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.net.jms.nativeimpl.util.BTestUtils;
import org.ballerinalang.net.jms.nativeimpl.util.MockJMSMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Test cases for jms message Set headers native functions.
 */
public class SetHeadersFunctionsTestCase {
    private CompileResult result;
    private BStruct messageStruct;

    @BeforeClass
    public void setup() {
        result = BTestUtils.compile("message/jmsheaders.bal");

        Message jmsMessage = new MockJMSMessage();
        messageStruct = BTestUtils.createAndGetStruct(result.getProgFile(), Constants.PROTOCOL_PACKAGE_JMS,
                Constants.JMS_MESSAGE_STRUCT_NAME);

        messageStruct.addNativeData(Constants.JMS_API_MESSAGE, new BallerinaJMSMessage(jmsMessage));
    }

    @Test(description = "Test Ballerina native JMSMessage setDeliveryMode Header ")
    public void testSetDeliveryMode() throws JMSException {
        int expectedValue = 2;
        int actualValue = 10;

        BValue[] inputBValues = { messageStruct, new BInteger(expectedValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetJMSDeliveryModeHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            actualValue = jmsMessage.getJMSDeliveryMode();
        }

        Assert.assertEquals(actualValue, expectedValue, "DeliveryMode Header is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage setPriority Header ")
    public void testSetPriority() throws JMSException {
        int expectedValue = 2;
        int actualValue = 10;

        BValue[] inputBValues = { messageStruct, new BInteger(expectedValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetJMSPriorityHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            actualValue = jmsMessage.getJMSPriority();
        }

        Assert.assertEquals(actualValue, expectedValue, "Priority Header is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage setCorrelationID Header ")
    public void testSetCorrelationID() throws JMSException {
        String expectedValue = "abcd-1234";
        String actualValue = "";

        BValue[] inputBValues = { messageStruct, new BString(expectedValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetJMSCorrelationIDHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            actualValue = jmsMessage.getJMSCorrelationID();
        }

        Assert.assertEquals(actualValue, expectedValue, "CorrelationID Header is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage setType Header ")
    public void testSetType() throws JMSException {
        String expectedValue = "jms-type-1";
        String actualValue = "";

        BValue[] inputBValues = { messageStruct, new BString(expectedValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetJMSTypeHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            actualValue = jmsMessage.getJMSType();
        }

        Assert.assertEquals(actualValue, expectedValue, "Type Header is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage setExpiration Header ")
    public void testSetExpiration() throws JMSException {
        long expectedValue = 2;
        long actualValue = 10;

        BValue[] inputBValues = { messageStruct, new BInteger(expectedValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetJMSExpirationHeader", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            actualValue = jmsMessage.getJMSExpiration();
        }

        Assert.assertEquals(actualValue, expectedValue, "Expiration Header is not correctly set to the JMS Message");
    }

}
