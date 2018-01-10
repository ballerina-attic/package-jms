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

import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.jms.BallerinaJMSMessage;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.net.jms.nativeimpl.util.BTestUtils;
import org.ballerinalang.net.jms.nativeimpl.util.CompileResult;
import org.ballerinalang.net.jms.nativeimpl.util.MockJMSMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Test cases for jms message Set property native functions.
 */
public class SetPropertyFunctionsTestCase {
    private CompileResult result;
    private BStruct messageStruct;

    @BeforeClass
    public void setup() {
        result = BTestUtils.compile("message/jmsproperties.bal");

        Message jmsMessage = new MockJMSMessage();
        messageStruct = BTestUtils.createAndGetStruct(result.getProgFile(), Constants.PROTOCOL_PACKAGE_JMS,
                Constants.JMS_MESSAGE_STRUCT_NAME);

        messageStruct.addNativeData(org.ballerinalang.net.jms.Constants.JMS_API_MESSAGE,
                new BallerinaJMSMessage(jmsMessage));
    }

    @Test(description = "Test Ballerina native JMSMessage setStringProperty ")
    public void testSetStringProperty() throws JMSException {
        String propKey = "string_property_key";
        String propValue = "string_property_value";
        String resultValue = "";

        BValue[] inputBValues = { messageStruct, new BString(propKey), new BString(propValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetStringProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            resultValue = jmsMessage.getStringProperty(propKey);
        }

        Assert.assertEquals(propValue, resultValue, "String property is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage setStringProperty ")
    public void testSetBooleanProperty() throws JMSException {
        String propKey = "boolean_property_key";
        boolean propValue = Boolean.TRUE;
        boolean resultValue = Boolean.FALSE;

        BValue[] inputBValues = { messageStruct, new BString(propKey), new BBoolean(propValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetBooleanProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            resultValue = jmsMessage.getBooleanProperty(propKey);
        }

        Assert.assertEquals(propValue, resultValue, "Boolean property is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage setIntProperty ")
    public void testSetIntProperty() throws JMSException {
        String propKey = "int_property_key";
        int propValue = 1;
        int resultValue = 0;

        BValue[] inputBValues = { messageStruct, new BString(propKey), new BInteger(propValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetIntProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            resultValue = jmsMessage.getIntProperty(propKey);
        }

        Assert.assertEquals(propValue, resultValue, "Boolean property is not correctly set to the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage setFloatProperty ")
    public void testSetFloatProperty() throws JMSException {
        String propKey = "float_property_key";
        float propValue = 1.1f;
        float resultValue = 0f;

        BValue[] inputBValues = { messageStruct, new BString(propKey), new BFloat(propValue) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcSetFloatProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BStruct) {
            MockJMSMessage jmsMessage = (MockJMSMessage) JMSUtils.getJMSMessage((BStruct) returnBValues[0]);
            resultValue = jmsMessage.getFloatProperty(propKey);
        }

        Assert.assertEquals(propValue, resultValue, "Float property is not correctly set to the JMS Message");
    }

}
