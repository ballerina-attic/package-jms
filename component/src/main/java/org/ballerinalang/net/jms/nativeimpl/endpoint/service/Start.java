package org.ballerinalang.net.jms.nativeimpl.endpoint.service;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.connector.api.Struct;
import org.ballerinalang.model.NativeCallableUnit;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.wso2.transport.jms.contract.JMSServerConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;

import java.util.Objects;

/**
 * Get the ID of the connection.
 *
 * @since 0.966
 */
@BallerinaFunction(
        orgName = "ballerina", packageName = "net.jms",
        functionName = "start",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "ConsumerEndpoint",
                structPackage = "ballerina.net.jms"),
        isPublic = true
)
public class Start implements NativeCallableUnit {

    @Override
    public void execute(Context context, CallableUnitCallback callableUnitCallback) {
        Struct consumerEndpoint = BLangConnectorSPIUtil.getConnectorEndpointStruct(context);
        Object connectorObject = consumerEndpoint.getNativeData(Constants.SERVER_CONNECTOR);
        try {
            if (Objects.nonNull(connectorObject) && connectorObject instanceof JMSServerConnector) {
                ((JMSServerConnector) connectorObject).start();
            } else {
                throw new BallerinaException("Cannot start service. Connection to service endpoint "
                                                     + consumerEndpoint.getName() + " not properly registered");
            }
        } catch (JMSConnectorException e) {
            throw new BallerinaException(
                    "Error when starting to listen to the queue/topic with service endpoint "
                            + consumerEndpoint.getName(), e);

        }
    }

    @Override
    public boolean isBlocking() {
        return true;
    }
}
