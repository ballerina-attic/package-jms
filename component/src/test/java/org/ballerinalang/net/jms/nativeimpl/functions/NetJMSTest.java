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

package org.ballerinalang.net.jms.nativeimpl.functions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ballerinalang.bre.bvm.WorkerExecutionContext;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSConnectorFutureListener;
import org.ballerinalang.net.jms.nativeimpl.util.BTestUtils;
import org.ballerinalang.net.jms.nativeimpl.util.TestAcknowledgementCallback;
import org.ballerinalang.net.jms.nativeimpl.util.TestTransactionCallback;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test cases for ballerina.net.jms native functions.
 */
public class NetJMSTest {
    private CompileResult compileResult;
    private static final Log log = LogFactory.getLog(NetJMSTest.class);

    @BeforeClass
    public void setup() {
        compileResult = BTestUtils.compile("samples/netJMS.bal");
    }

    @Test (enabled = false, description = "Test Ballerina native JMS Acknowledgement method for success scenario ")
    public void testAcknowledge() {
        
        //Create the Context for the Execution
        WorkerExecutionContext parentCtx = new WorkerExecutionContext(compileResult.getProgFile());
        parentCtx.localProps.put(Constants.JMS_SESSION_ACKNOWLEDGEMENT_MODE,  javax.jms.Session.CLIENT_ACKNOWLEDGE);

        //Create the Callback
        TestAcknowledgementCallback jmsCallback = new TestAcknowledgementCallback(null);
        JMSConnectorFutureListener future = new JMSConnectorFutureListener(jmsCallback);

        //Construct the JMS Message
        BStruct messageStruct = BTestUtils.createAndGetStruct(compileResult.getProgFile(), 
                Constants.PROTOCOL_PACKAGE_JMS, 
                Constants.JMS_MESSAGE_STRUCT_NAME);
        BValue[] args = {messageStruct, new BString("SUCCESS") };
        
        //Execute
        BTestUtils.invoke(compileResult, "testAcknowledge", args, parentCtx, future);

        Assert.assertTrue(jmsCallback.isAcknowledged(), "JMS message is not acknowledged properly");
    }

    @Test (enabled = false, description = "Test Ballerina native JMS Acknowledgement method for failure scenario ")
    public void testAcknowledgeReset() {

        //Create the Context for the Execution
        WorkerExecutionContext parentCtx = new WorkerExecutionContext(compileResult.getProgFile());
        parentCtx.globalProps.put(Constants.JMS_SESSION_ACKNOWLEDGEMENT_MODE,  javax.jms.Session.CLIENT_ACKNOWLEDGE);

        //Create the Callback
        TestAcknowledgementCallback jmsCallback = new TestAcknowledgementCallback(null);
        JMSConnectorFutureListener future = new JMSConnectorFutureListener(jmsCallback);

        //Construct the JMS Message
        BStruct messageStruct = BTestUtils.createAndGetStruct(compileResult.getProgFile(), 
                Constants.PROTOCOL_PACKAGE_JMS, 
                Constants.JMS_MESSAGE_STRUCT_NAME);
        BValue[] args = {messageStruct, new BString("ERROR") };
        
        //Execute
        BTestUtils.invoke(compileResult, "testAcknowledge", args, parentCtx, future);

        Assert.assertTrue(jmsCallback.isReseted(), "JMS message is not unacknowledged properly");
    }

    @Test (enabled = false, description = "Test Ballerina native JMS Transaction commit method ")
    public void testTransactionCommit() {

        //Create the Context for the Execution
        WorkerExecutionContext parentCtx = new WorkerExecutionContext(compileResult.getProgFile());
        parentCtx.globalProps.put(Constants.JMS_SESSION_ACKNOWLEDGEMENT_MODE,  javax.jms.Session.SESSION_TRANSACTED);

        //Create the Callback
        TestTransactionCallback jmsCallback = new TestTransactionCallback(null);
        JMSConnectorFutureListener future = new JMSConnectorFutureListener(jmsCallback);

        //Construct the JMS Message
        BStruct messageStruct = BTestUtils.createAndGetStruct(compileResult.getProgFile(), 
                Constants.PROTOCOL_PACKAGE_JMS, 
                Constants.JMS_MESSAGE_STRUCT_NAME);
        BValue[] args = {messageStruct, null };

        //Execute
        BTestUtils.invoke(compileResult, "testCommit", args, parentCtx, future);        

        Assert.assertTrue(jmsCallback.isCommited(), "JMS message is not committed properly");
    }

    @Test (enabled = false, description = "Test Ballerina native JMS Transaction rollback")
    public void testTransactionRollback() {
        
        //Create the Context for the Execution
        WorkerExecutionContext parentCtx = new WorkerExecutionContext(compileResult.getProgFile());
        parentCtx.globalProps.put(Constants.JMS_SESSION_ACKNOWLEDGEMENT_MODE,  javax.jms.Session.SESSION_TRANSACTED);

        //Create the Callback
        TestTransactionCallback jmsCallback = new TestTransactionCallback(null);
        JMSConnectorFutureListener future = new JMSConnectorFutureListener(jmsCallback);

        //Construct the JMS Message
        BStruct messageStruct = BTestUtils.createAndGetStruct(compileResult.getProgFile(), 
                Constants.PROTOCOL_PACKAGE_JMS, 
                Constants.JMS_MESSAGE_STRUCT_NAME);
        BValue[] args = {messageStruct, null };

        //Execute
        BTestUtils.invoke(compileResult, "testRollback", args, parentCtx, future);        

        Assert.assertTrue(jmsCallback.isRollbacked(), "JMS message is not rollbacked properly");
    }
}
