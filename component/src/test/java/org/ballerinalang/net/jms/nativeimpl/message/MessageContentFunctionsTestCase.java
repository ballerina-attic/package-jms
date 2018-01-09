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

import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.jms.BallerinaJMSMessage;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.net.jms.nativeimpl.util.BTestUtils;
import org.ballerinalang.net.jms.nativeimpl.util.CompileResult;
import org.ballerinalang.net.jms.nativeimpl.util.MockJMSTextMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Test cases for jms message content related native functions.
 */
public class MessageContentFunctionsTestCase {
    private final String textContent = "Hello WSO2!";
    private CompileResult result;
    private BStruct messageStruct1;
    private BStruct messageStruct2;

    @BeforeClass
    public void setup() throws JMSException {
        result = BTestUtils.compile("message/jmscontent.bal");

        TextMessage jmsMessage1 = new MockJMSTextMessage();
        messageStruct1 = BTestUtils.createAndGetStruct(result.getProgFile(), Constants.PROTOCOL_PACKAGE_JMS,
                Constants.JMS_MESSAGE_STRUCT_NAME);

        messageStruct1.addNativeData(Constants.JMS_API_MESSAGE, new BallerinaJMSMessage(jmsMessage1));

        TextMessage jmsMessage2 = new MockJMSTextMessage();
        jmsMessage2.setText(textContent);
        messageStruct2 = BTestUtils.createAndGetStruct(result.getProgFile(), Constants.PROTOCOL_PACKAGE_JMS,
                Constants.JMS_MESSAGE_STRUCT_NAME);

        messageStruct2.addNativeData(Constants.JMS_API_MESSAGE, new BallerinaJMSMessage(jmsMessage2));
    }

    @Test(description = "Test Ballerina native JMSMessage setTextContent ")
    public void testSetTextContent() throws JMSException {
        String resultValue = "";

        BValue[] inputBValues = { messageStruct1, new BString(textContent) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetJMSTextContent", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            TextMessage jmsMessage = (TextMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            resultValue = jmsMessage.getText();
        }

        Assert.assertEquals(textContent, resultValue, "Text Content is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getTextContent ")
    public void testGetTextContent() throws JMSException {
        String resultValue = "";

        BValue[] inputBValues = { messageStruct2 };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcGetJMSTextContent", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BString) {
            resultValue = returnBValues[0].stringValue();
        }

        Assert.assertEquals(textContent, resultValue, "Text content is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getTextContent ", dependsOnMethods = { "testGetTextContent" })
    public void testClearTextContent() throws JMSException {
        String resultValue = "";

        BValue[] inputBValues = { messageStruct2 };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcClearJMSTextContent", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BString) {
            resultValue = returnBValues[0].stringValue();
        }

        Assert.assertNull(resultValue, "Message content is not cleared from the JMS Message");
    }

}
