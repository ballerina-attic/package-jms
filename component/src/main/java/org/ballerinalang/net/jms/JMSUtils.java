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

package org.ballerinalang.net.jms;

import org.ballerinalang.connector.api.AnnAttrValue;
import org.ballerinalang.connector.api.Annotation;
import org.ballerinalang.connector.api.BallerinaConnectorException;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.wso2.carbon.kernel.utils.StringUtils;
import org.wso2.transport.jms.utils.JMSConstants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Message;

/**
 * Utility class for JMS related common operations.
 */
public class JMSUtils {

    /**
     * Utility class cannot be instantiated.
     */
    private JMSUtils() {
    }

    /**
     * Creates the JMS connector friendly properties Map. Converting properties as required.
     *
     * @param jmsConfig {@link Annotation}
     * @return Map of String key value properties.
     */
    public static Map<String, String> preProcessJmsConfig(Annotation jmsConfig) {
        Map<String, String> configParams = new HashMap<>();
        addStringParamIfPresent(Constants.ALIAS_INITIAL_CONTEXT_FACTORY, jmsConfig, configParams);
        addStringParamIfPresent(Constants.ALIAS_PROVIDER_URL, jmsConfig, configParams);
        addStringParamIfPresent(Constants.ALIAS_CONNECTION_FACTORY_TYPE, jmsConfig, configParams);
        addStringParamIfPresent(Constants.ALIAS_CONNECTION_FACTORY_NAME, jmsConfig, configParams);
        addStringParamIfPresent(Constants.ALIAS_DESTINATION, jmsConfig, configParams);
        addStringParamIfPresent(Constants.ALIAS_CLIENT_ID, jmsConfig, configParams);
        addStringParamIfPresent(Constants.ALIAS_DURABLE_SUBSCRIBER_ID, jmsConfig, configParams);
        addStringParamIfPresent(Constants.ALIAS_ACK_MODE, jmsConfig, configParams);
        addStringParamIfPresent(Constants.CONFIG_FILE_PATH, jmsConfig, configParams);
        addIntParamIfPresent(JMSConstants.CONCURRENT_CONSUMERS, jmsConfig, configParams);
        addStringParamIfPresent(JMSConstants.CONNECTION_USERNAME, jmsConfig, configParams);
        addStringParamIfPresent(JMSConstants.CONNECTION_PASSWORD, jmsConfig, configParams);

        processPropertiesArray(jmsConfig, configParams);
        preProcessIfWso2MB(configParams);
        updateMappedParameters(configParams);
        return configParams;
    }

    private static void preProcessIfWso2MB(Map<String, String> configParams) {
        if (Constants.MB_ICF_ALIAS.equalsIgnoreCase(configParams.get(Constants.ALIAS_INITIAL_CONTEXT_FACTORY))) {

            configParams.put(Constants.ALIAS_INITIAL_CONTEXT_FACTORY, Constants.MB_ICF_NAME);
            String connectionFactoryName = configParams.get(Constants.ALIAS_CONNECTION_FACTORY_NAME);
            if (configParams.get(Constants.ALIAS_PROVIDER_URL) != null) {
                if (!StringUtils.isNullOrEmptyAfterTrim(connectionFactoryName)) {
                    configParams.put(Constants.MB_CF_NAME_PREFIX + connectionFactoryName,
                            configParams.get(Constants.ALIAS_PROVIDER_URL));
                    configParams.remove(Constants.ALIAS_PROVIDER_URL);
                } else {
                    throw new BallerinaException(Constants.ALIAS_CONNECTION_FACTORY_NAME + " property should be set");
                }
            } else if (configParams.get(Constants.CONFIG_FILE_PATH) != null) {
                configParams.put(Constants.ALIAS_PROVIDER_URL, configParams.get(Constants.CONFIG_FILE_PATH));
                configParams.remove(Constants.CONFIG_FILE_PATH);
            }
        }
    }

    private static void processPropertiesArray(Annotation jmsConfig, Map<String, String> configParams) {
        AnnAttrValue attributeValue = jmsConfig.getAnnAttrValue(Constants.PROPERTIES_ARRAY);
        if (attributeValue != null) {
            AnnAttrValue[] attributeValueArray = attributeValue.getAnnAttrValueArray();
            for (AnnAttrValue annAttributeValue : attributeValueArray) {
                String stringValue = annAttributeValue.getStringValue();
                int index = stringValue.indexOf("=");
                if (index != -1) {
                    String key = stringValue.substring(0, index).trim();
                    String value = stringValue.substring(index + 1).trim();
                    configParams.put(key, value);
                } else {
                    throw new BallerinaException("Invalid " + Constants.PROPERTIES_ARRAY + " provided. Key value"
                            + " pair is not separated by an '='");
                }
            }
        }
    }

    private static void updateMappedParameters(Map<String, String> configParams) {
        Iterator<Map.Entry<String, String>> iterator = configParams.entrySet().iterator();
        Map<String, String> tempMap = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String mappedParam = Constants.MAPPING_PARAMETERS.get(entry.getKey());
            if (mappedParam != null) {
                tempMap.put(mappedParam, entry.getValue());
                iterator.remove();
            }
        }
        configParams.putAll(tempMap);
    }

    private static void addStringParamIfPresent(String paramName, Annotation jmsConfig, Map<String, String> paramsMap) {
        AnnAttrValue value = jmsConfig.getAnnAttrValue(paramName);
        if (value != null && value.getStringValue() != null) {
            paramsMap.put(paramName, value.getStringValue());
        }
    }

    private static void addIntParamIfPresent(String paramName, Annotation jmsConfig, Map<String, String> paramsMap) {
        AnnAttrValue value = jmsConfig.getAnnAttrValue(paramName);
        if (value != null) {
            paramsMap.put(paramName, String.valueOf(value.getIntValue()));
        }
    }

    /**
     * Convert Client Connector Property Map into String key-value pair map
     *
     * @param connectorConfig Client Connector configuration map
     * @return String key-value pair map
     */
    public static Map<String, String> preProcessJmsConfig(BStruct connectorConfig) {
        Map<String, String> configParams = new HashMap<>();

        String initialContextFactory = connectorConfig.getStringField(0);
        String providerUrl = connectorConfig.getStringField(1);
        String connectionFactoryName = connectorConfig.getStringField(2);
        String connectionFactoryType = connectorConfig.getStringField(3);
        String acknowledgementMode = connectorConfig.getStringField(4);
        boolean clientCaching = (connectorConfig.getBooleanField(0) != 0 ? Boolean.TRUE : Boolean.FALSE);
        String connectionUsername = connectorConfig.getStringField(5);
        String connectionPassword = connectorConfig.getStringField(6);
        String configFilePath = connectorConfig.getStringField(7);
        int connectionCount = (int) connectorConfig.getIntField(0);
        int sessionCount = (int) connectorConfig.getIntField(1);

        // Add to the map
        configParams.put(Constants.ALIAS_INITIAL_CONTEXT_FACTORY, initialContextFactory);
        configParams.put(Constants.ALIAS_CONNECTION_FACTORY_NAME, connectionFactoryName);
        configParams.put(Constants.ALIAS_CONNECTION_FACTORY_TYPE, connectionFactoryType);
        configParams.put(Constants.ALIAS_ACK_MODE, acknowledgementMode);
        configParams.put(JMSConstants.PARAM_JMS_CACHING, String.valueOf(clientCaching));
        if (!Constants.EMPTY_STRING.equals(providerUrl)) {
            configParams.put(Constants.ALIAS_PROVIDER_URL, providerUrl);
        }
        if (!Constants.EMPTY_STRING.equals(configFilePath)) {
            configParams.put(Constants.CONFIG_FILE_PATH, configFilePath);
        }
        if (!Constants.EMPTY_STRING.equals(connectionUsername)) {
            configParams.put(JMSConstants.CONNECTION_USERNAME, connectionUsername);
            configParams.put(JMSConstants.CONNECTION_PASSWORD, connectionPassword);
        }
        configParams.put(JMSConstants.PARAM_MAX_CONNECTIONS, String.valueOf(connectionCount));
        configParams.put(JMSConstants.PARAM_MAX_SESSIONS_ON_CONNECTION, String.valueOf(sessionCount));

        if (connectorConfig.getRefField(0) != null) {
            preProcessJmsConfig(configParams, (BMap<String, BString>) connectorConfig.getRefField(0));
        }

        preProcessIfWso2MB(configParams);
        updateMappedParameters(configParams);

        return configParams;
    }

    /**
     * Process the provided properties in the {@link BMap} and convert it to jms connector friendly Map.
     *
     * @param configParams Map instance that is getting filled.
     * @param properties   {@link BMap} of properties.
     * @return updated map for JMS connector.
     */
    public static Map<String, String> preProcessJmsConfig(Map<String, String> configParams,
            BMap<String, BString> properties) {

        for (String key : properties.keySet()) {
            configParams.put(key, properties.get(key).stringValue());
        }

        return configParams;
    }

    /**
     * Extract JMS Message from the struct
     *
     * @param messageStruct ballerina struct
     * @return {@link Message} instance located in struct
     */
    public static Message getJMSMessage(BStruct messageStruct) {
        if (messageStruct.getNativeData(Constants.JMS_API_MESSAGE) != null) {
            return (Message) messageStruct.getNativeData(Constants.JMS_API_MESSAGE);
        } else {
            throw new BallerinaException("JMS message has not been created.");
        }
    }

    /**
     * Extract JMS Resource from the Ballerina Service
     *
     * @param service Service instance
     * @return extracted resource
     * @throws BallerinaConnectorException if there is no Resource or more than one Resource inside the service
     */
    public static Resource extractJMSResource(Service service) throws BallerinaConnectorException {
        Resource[] resources = service.getResources();
        if (resources.length == 0) {
            throw new BallerinaException("No resources found to handle the JMS message in " + service.getName());
        }
        if (resources.length > 1) {
            throw new BallerinaException("More than one resources found in JMS service " + service.getName()
                    + ".JMS Service should only have one resource");
        }
        return resources[0];
    }
}
