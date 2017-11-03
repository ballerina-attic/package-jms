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
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.nativeimpl.util.BTestUtils;
import org.ballerinalang.net.jms.nativeimpl.util.CompileResult;
import org.ballerinalang.net.jms.nativeimpl.util.MockJMSMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Test cases for jms message Get property native functions.
 */
public class GetPropertyFunctionsTestCase {
    private CompileResult result;
    private BStruct messageStruct;

    //Property Names and values
    private String stringPropName = "stringPropName";
    private String intPropName = "intPropName";
    private String booleanPropName = "booleanPropName";
    private String floatPropName = "floatPropName";

    private String stingPropValue = "stringPropValue";
    private int intPropValue = 1;
    private boolean booleanPropValue = Boolean.TRUE;
    private float floatPropValue = 1.1f;

    @BeforeClass
    public void setup() throws JMSException {
        result = BTestUtils.compile("message/jmsproperties.bal");

        Message jmsMessage = new MockJMSMessage();
        jmsMessage.setStringProperty(stringPropName, stingPropValue);
        jmsMessage.setIntProperty(intPropName, intPropValue);
        jmsMessage.setBooleanProperty(booleanPropName, booleanPropValue);
        jmsMessage.setFloatProperty(floatPropName, floatPropValue);

        messageStruct = BTestUtils.createAndGetStruct(result.getProgFile(), Constants.PROTOCOL_PACKAGE_JMS,
                Constants.JMS_MESSAGE_STRUCT_NAME);

        messageStruct.addNativeData(Constants.JMS_API_MESSAGE, jmsMessage);
    }

    @Test(description = "Test Ballerina native JMSMessage getStringProperty ")
    public void testGetStringProperty() throws JMSException {
        String resultValue = "";

        BValue[] inputBValues = { messageStruct, new BString(stringPropName) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcGetStringProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BString) {
            resultValue = returnBValues[0].stringValue();
        }

        Assert.assertEquals(stingPropValue, resultValue,
                "String property is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getStringProperty ")
    public void testGetBooleanProperty() throws JMSException {
        boolean resultValue = Boolean.FALSE;

        BValue[] inputBValues = { messageStruct, new BString(booleanPropName) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcGetBooleanProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BBoolean) {
            resultValue = ((BBoolean) returnBValues[0]).value();
        }

        Assert.assertEquals(booleanPropValue, resultValue,
                "Boolean property is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getIntProperty ")
    public void testGetIntProperty() throws JMSException {
        int resultValue = 0;

        BValue[] inputBValues = { messageStruct, new BString(intPropName) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcGetIntProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BInteger) {
            resultValue = (int) ((BInteger) returnBValues[0]).intValue();
        }

        Assert.assertEquals(intPropValue, resultValue, "Int property is not correctly retrieved from the JMS Message");
    }

    @Test(description = "Test Ballerina native JMSMessage getFloatProperty ")
    public void testGetFloatProperty() throws JMSException {
        float resultValue = 0f;

        BValue[] inputBValues = { messageStruct, new BString(floatPropName) };
        BValue[] returnBValues = BTestUtils.invoke(result, "funcGetFloatProperty", inputBValues);

        if (returnBValues != null && returnBValues.length == 1 && returnBValues[0] instanceof BFloat) {
            resultValue = (float) ((BFloat) returnBValues[0]).floatValue();
        }

        Assert.assertEquals(floatPropValue, resultValue,
                "Float property is not correctly retrieved from the JMS Message");
    }

}
