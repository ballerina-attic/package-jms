package org.ballerinalang.net.jms.nativeimpl.endpoint.service;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.model.NativeCallableUnit;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;

/**
 * Get the ID of the connection.
 *
 * @since 0.966
 */
@BallerinaFunction(
        orgName = "ballerina", packageName = "net.jms",
        functionName = "initEndpoint",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "ConsumerEndpoint",
                             structPackage = "ballerina.net.jms"),
        isPublic = true
)
public class InitEndpoint implements NativeCallableUnit {

    @Override
    public void execute(Context context, CallableUnitCallback callableUnitCallback) {
        // Nothing to do. JMS listener is created on register phase.
    }

    @Override
    public boolean isBlocking() {
        return true;
    }
}
