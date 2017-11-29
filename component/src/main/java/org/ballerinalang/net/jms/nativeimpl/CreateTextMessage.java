/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
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

package org.ballerinalang.net.jms.nativeimpl;

import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.ConnectorUtils;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.impl.JMSConnectorFactoryImpl;
import org.wso2.transport.jms.utils.JMSConstants;

import java.util.Map;
import javax.jms.Message;

/**
 * Create Text JMS Message.
 */
@BallerinaFunction(packageName = "ballerina.net.jms", functionName = "createTextMessage", args = {
        @Argument(name = "clientConnector", type = TypeKind.STRUCT) },
                   returnType = {@ReturnType(type = TypeKind.STRUCT, structPackage = "ballerina.net.jms",
                                             structType = "JMSMessage")},
                   isPublic = true)
public class CreateTextMessage extends AbstractNativeFunction {
    private static final Logger log = LoggerFactory.getLogger(CreateTextMessage.class);

    public BValue[] execute(Context context) {

        BStruct propertiesStruct = ((BStruct) this.getRefArgument(context, 0));
        Map<String, String> propertyMap = JMSUtils.preProcessJmsConfig(propertiesStruct);

        Message jmsMessage = null;

        try {
            jmsMessage = new JMSConnectorFactoryImpl().createClientConnector(propertyMap)
                    .createMessage(JMSConstants.TEXT_MESSAGE_TYPE);
        } catch (JMSConnectorException e) {
            throw new BallerinaException("Failed to create message. " + e.getMessage(), e, context);
        }

        BStruct bStruct = ConnectorUtils
                .createAndGetStruct(context, Constants.PROTOCOL_PACKAGE_JMS, Constants.JMS_MESSAGE_STRUCT_NAME);

        bStruct.addNativeData(org.ballerinalang.net.jms.Constants.JMS_API_MESSAGE, jmsMessage);
        bStruct.addNativeData(Constants.INBOUND_REQUEST, Boolean.FALSE);

        return this.getBValues(bStruct);
    }
}
