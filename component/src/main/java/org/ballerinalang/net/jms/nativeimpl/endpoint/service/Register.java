package org.ballerinalang.net.jms.nativeimpl.endpoint.service;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.connector.api.Annotation;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.connector.api.BallerinaConnectorException;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.connector.api.Struct;
import org.ballerinalang.model.NativeCallableUnit;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSListenerImpl;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.wso2.transport.jms.contract.JMSListener;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.impl.JMSConnectorFactoryImpl;
import org.wso2.transport.jms.utils.JMSConstants;

import java.util.List;
import java.util.Map;

/**
 * Get the ID of the connection.
 *
 * @since 0.966
 */

@BallerinaFunction(
        packageName = "ballerina.net.jms",
        functionName = "register",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "ServiceEndpoint",
                structPackage = "ballerina.net.jms"),
        args = {@Argument(name = "serviceType", type = TypeKind.TYPE)},
        isPublic = true
)
public class Register implements NativeCallableUnit {
    @Override
    public void execute(Context context, CallableUnitCallback callableUnitCallback) {
        Service service = BLangConnectorSPIUtil.getServiceRegistered(context);
        Struct serviceEndpoint = BLangConnectorSPIUtil.getConnectorEndpointStruct(context);

        Map<String, String> endpointConfigs =
                JMSUtils.preProcessEndpointConfig(serviceEndpoint.getStructField(Constants.ENDPOINT_CONFIG_KEY));
        List<Annotation> annotationList = service.getAnnotationList(Constants.JMS_PACKAGE,
                                                                    Constants.ANNOTATION_JMS_CONFIGURATION);

        String serviceKey = getServiceKey(service);
        if (annotationList == null) {
            throw new BallerinaConnectorException("Unable to find the associated configuration " +
                                                          "annotation for given service: " + serviceKey);
        }
        if (annotationList.size() > 1) {
            throw new BallerinaException(
                    "multiple service configuration annotations found in service: " + service.getName());
        }
        Annotation jmsConfig = annotationList.get(0);
        if (jmsConfig == null) {
            throw new BallerinaException("Error jms 'configuration' annotation missing in " + service.getName());
        }

        Map<String, String> configParams = JMSUtils.preProcessServiceConfig(jmsConfig);
        configParams.putAll(endpointConfigs);
        String serviceId = service.getName();
        configParams.putIfAbsent(JMSConstants.PARAM_DESTINATION_NAME, serviceId);

        try {
            // Create a new JMS Listener for this this JMS Service and include it in a new JMS Server Connector
            Resource resource = JMSUtils.extractJMSResource(service);
            JMSListener jmsListener = new JMSListenerImpl(resource);
            org.wso2.transport.jms.contract.JMSServerConnector serverConnector =
                    new JMSConnectorFactoryImpl().createServerConnector(serviceId, configParams, jmsListener);

            serviceEndpoint.addNativeData(Constants.SERVER_CONNECTOR, serverConnector);
            serverConnector.start();
        } catch (JMSConnectorException e) {
            throw new BallerinaException(
                    "Error when starting to listen to the queue/topic while " + serviceId + " deployment", e);
        }

        context.setReturnValues();
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    private String getServiceKey(Service service) {
        return service.getPackage() != null ? (service.getPackage() + "_" + service.getName()) : service.getName();
    }
}
