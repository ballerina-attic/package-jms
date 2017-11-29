/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.net.jms;

import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.connector.api.Annotation;
import org.ballerinalang.connector.api.BallerinaConnectorException;
import org.ballerinalang.connector.api.BallerinaServerConnector;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.wso2.transport.jms.contract.JMSListener;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.impl.JMSConnectorFactoryImpl;
import org.wso2.transport.jms.utils.JMSConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code JMSServerConnector} This is the JMS implementation for the {@code BallerinaServerConnector} API.
 *
 * @since 0.94
 */
@JavaSPIService("org.ballerinalang.connector.api.BallerinaServerConnector")
public class JMSServerConnector implements BallerinaServerConnector {

    private Map<String, org.wso2.transport.jms.contract.JMSServerConnector> connectorMap = new HashMap<>();

    @Override
    public String getProtocolPackage() {
        return Constants.PROTOCOL_PACKAGE_JMS;
    }

    @Override
    public void serviceRegistered(Service service) throws BallerinaConnectorException {
        Annotation jmsConfig = service.getAnnotation(Constants.JMS_PACKAGE, Constants.ANNOTATION_JMS_CONFIGURATION);

        if (jmsConfig == null) {
            throw new BallerinaException("Error jms 'configuration' annotation missing in " + service.getName());
        }

        Map<String, String> configParams = JMSUtils.preProcessJmsConfig(jmsConfig);

        String serviceId = service.getName();
        configParams.putIfAbsent(JMSConstants.PARAM_DESTINATION_NAME, serviceId);

        try {
            // Create a new JMS Listener for this this JMS Service and include it in a new JMS Server Connector
            JMSListener jmsListener = new JMSListenerImpl(JMSUtils.extractJMSResource(service));
            org.wso2.transport.jms.contract.JMSServerConnector serverConnector = new JMSConnectorFactoryImpl()
                    .createServerConnector(serviceId, configParams, jmsListener);

            connectorMap.put(serviceId, serverConnector);
            serverConnector.start();
        } catch (JMSConnectorException e) {
            throw new BallerinaException(
                    "Error when starting to listen to the queue/topic while " + serviceId + " deployment", e);
        }
    }

    @Override
    public void serviceUnregistered(Service service) throws BallerinaConnectorException {
        String serviceId = service.getName();
        try {
            org.wso2.transport.jms.contract.JMSServerConnector serverConnector = connectorMap.get(serviceId);
            if (null != serverConnector) {
                serverConnector.stop();
            }
        } catch (JMSConnectorException e) {
            throw new BallerinaException(
                    "Error while stopping the jms server connector related with the service " + serviceId, e);
        }
    }

    @Override
    public void deploymentComplete() throws BallerinaConnectorException {

    }
}
