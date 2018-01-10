/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.jms.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code Poll} is the poll action implementation of the JMS Client Connector.
 *
 * @since 0.95.5
 */
@BallerinaAction(packageName = "ballerina.net.jms",
                 actionName = "pollWithSelector",
                 connectorName = Constants.CONNECTOR_NAME,
                 args = {
                         @Argument(name = "queueName",
                                   type = TypeKind.STRING),
                         @Argument(name = "timeout", type = TypeKind.INT),
                         @Argument(name = "selector", type = TypeKind.STRING)
                 },
                 returnType = {@ReturnType(type = TypeKind.STRUCT, elementType = TypeKind.STRUCT,
                                           structPackage = "ballerina.net.jms", structType = "JMSMessage"),
                               @ReturnType(type = TypeKind.STRUCT)})
public class SelectorPoll extends Poll {
    private static final Logger log = LoggerFactory.getLogger(SelectorPoll.class);

    @Override
    public ConnectorFuture execute(Context context) {
        String messageSelector = getStringArgument(context, 1);
        return executePollAction(context, messageSelector);
    }
}
