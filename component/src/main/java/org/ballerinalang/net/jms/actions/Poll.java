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
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.connector.api.ConnectorUtils;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.nativeimpl.actions.ClientConnectorFuture;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
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
@BallerinaAction(packageName = "ballerina.net.jms",
                 actionName = "poll",
                 connectorName = Constants.CONNECTOR_NAME,
                 args = {
                         @Argument(name = "queueName",
                                   type = TypeKind.STRING),
                         @Argument(name = "timeout", type = TypeKind.INT)
                 },
                 returnType = {@ReturnType(type = TypeKind.STRUCT, elementType = TypeKind.STRUCT,
                                           structPackage = "ballerina.net.jms", structType = "JMSMessage"),
                               @ReturnType(type = TypeKind.STRUCT)})
public class Poll extends AbstractJMSAction {
    private static final Logger log = LoggerFactory.getLogger(Poll.class);

    @Override
    public ConnectorFuture execute(Context context) {
        // pass selector as null, because this is invoked under non-selector poll
        String messageSelector = null;
        return executePollAction(context, messageSelector);
    }

    protected ConnectorFuture executePollAction(Context context, String messageSelector) {
        ClientConnectorFuture future = new ClientConnectorFuture();

        // Extract argument values
        BConnector bConnector = (BConnector) getRefArgument(context, 0);
        String destination = getStringArgument(context, 0);
        int timeout = getIntArgument(context, 0);

        // Get the map of properties.
        BStruct  connectorConfig = ((BStruct) bConnector.getRefField(0));
        String acknowledgementMode = connectorConfig.getStringField(Constants.CLIENT_CONFIG_ACK_FIELD_INDEX);

        // Retrieve transport client
        JMSClientConnector jmsClientConnector = (JMSClientConnector) bConnector
                .getnativeData(Constants.JMS_TRANSPORT_CLIENT_CONNECTOR);

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
                future.notifySuccess();
                return future;
            }

            // Inject the Message (if received) into a JMSMessage struct.
            BStruct bStruct = ConnectorUtils
                    .createAndGetStruct(context, Constants.PROTOCOL_PACKAGE_JMS, Constants.JMS_MESSAGE_STRUCT_NAME);
            bStruct.addNativeData(org.ballerinalang.net.jms.Constants.JMS_API_MESSAGE,
                    JMSUtils.buildBallerinaJMSMessage(message));
            bStruct.addNativeData(Constants.INBOUND_REQUEST, Boolean.FALSE);

            future.notifyReply(bStruct);
        } catch (JMSConnectorException e) {
            throw new BallerinaException("failed to poll message. " + e.getMessage(), e, context);
        }
        return future;
    }
}
