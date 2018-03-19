/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ballerinalang.net.jms.actions;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BLangVMErrors;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.jms.contract.JMSClientConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.sender.wrappers.SessionWrapper;
import org.wso2.transport.jms.utils.JMSConstants;

import javax.jms.Message;

/**
 * {@code Poll} is the poll action implementation of the JMS Client Connector.
 *
 * @since 0.95.2
 */
@BallerinaFunction(packageName = "ballerina.net.jms",
                   functionName = "poll",
                   receiver = @Receiver(type = TypeKind.STRUCT,
                                        structType = "ClientConnector",
                                        structPackage =
                                                "ballerina.net.jms"),
                   args = {
                           @Argument(name = "client",
                                     type = TypeKind.STRUCT),
                           @Argument(name = "destinationName",
                                     type = TypeKind.STRING),
                           @Argument(name = "timeout",
                                     type = TypeKind.INT)
                   },
                   returnType = {
                           @ReturnType(type = TypeKind.STRUCT,
                                       structPackage = "ballerina.net.jms",
                                       structType = "JMSMessage")
                   }
)
public class Poll extends AbstractJMSAction {
    private static final Logger log = LoggerFactory.getLogger(Poll.class);

    @Override
    public void execute(Context context, CallableUnitCallback callableUnitCallback) {
        // pass selector as null, because this is invoked under non-selector poll
        String messageSelector = null;
        executePollAction(context, callableUnitCallback, messageSelector);
    }

    protected void executePollAction(Context context,
                                     CallableUnitCallback callableUnitCallback,
                                     String messageSelector) {
        // Extract argument values
        BStruct bConnector = (BStruct) context.getRefArgument(0);
        String destination = context.getStringArgument(0);
        int timeout = Math.toIntExact(context.getIntArgument(0));

        // Get the map of properties.
        BStruct connectorConfig = ((BStruct) bConnector.getRefField(0));
        String acknowledgementMode = connectorConfig.getStringField(Constants.CLIENT_CONFIG_ACK_FIELD_INDEX);

        // Retrieve transport client
        JMSClientConnector jmsClientConnector = (JMSClientConnector) bConnector
                .getNativeData(Constants.JMS_TRANSPORT_CLIENT_CONNECTOR);

        // Get the connector key
        String connectorKey = bConnector.getStringField(0);

        try {
            Message message;
            if (log.isDebugEnabled()) {
                log.debug("polling JMS Message from " + destination);
            }
            if (JMSConstants.SESSION_TRANSACTED_MODE.equalsIgnoreCase(acknowledgementMode)
                    || JMSConstants.XA_TRANSACTED_MODE.equalsIgnoreCase(acknowledgementMode)) {
                // if the action is not called inside a transaction block
                if (!context.isInTransaction()) {
                    throw new BallerinaException(
                            "jms transacted poll action should perform inside a transaction block ", context);
                }
                SessionWrapper sessionWrapper = getTxSession(context, jmsClientConnector, connectorKey);
                message = jmsClientConnector.pollTransacted(destination, timeout, sessionWrapper, messageSelector);
            } else {
                message = jmsClientConnector.poll(destination, timeout, messageSelector);
            }

            // Return from here if no message received
            if (message == null) {
                callableUnitCallback.notifySuccess();
            } else {
                // Inject the Message (if received) into a JMSMessage struct.
                BStruct bStruct = BLangConnectorSPIUtil
                        .createBStruct(context, Constants.PROTOCOL_PACKAGE_JMS, Constants.JMS_MESSAGE_STRUCT_NAME);
                bStruct.addNativeData(org.ballerinalang.net.jms.Constants.JMS_API_MESSAGE,
                                      JMSUtils.buildBallerinaJMSMessage(message));
                bStruct.addNativeData(Constants.INBOUND_REQUEST, Boolean.FALSE);

                context.setReturnValues(bStruct);
                callableUnitCallback.notifySuccess();
            }
        } catch (JMSConnectorException e) {
            callableUnitCallback.notifyFailure(BLangVMErrors.createError(context,
                                                                         "failed to poll message. " + e.getMessage()));
        }
    }
}
