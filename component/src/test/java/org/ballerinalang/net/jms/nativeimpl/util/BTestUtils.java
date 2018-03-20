/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.net.jms.nativeimpl.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ballerinalang.bre.bvm.BLangScheduler;
import org.ballerinalang.bre.bvm.BLangVMStructs;
import org.ballerinalang.bre.bvm.WorkerExecutionContext;
import org.ballerinalang.launcher.util.BCompileUtil;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.jms.JMSConnectorFutureListener;
import org.ballerinalang.util.codegen.FunctionInfo;
import org.ballerinalang.util.codegen.PackageInfo;
import org.ballerinalang.util.codegen.ProgramFile;
import org.ballerinalang.util.codegen.StructInfo;
import org.ballerinalang.util.diagnostic.Diagnostic;
import org.ballerinalang.util.program.BLangFunctions;
import org.testng.Assert;

import java.util.Arrays;

/**
 * Utility methods for unit tests.
 * 
 * @since 0.94
 */
public class BTestUtils {

    private static final Log log = LogFactory.getLog(BTestUtils.class);

    public static CompileResult compile(String programPath) {
        CompileResult compileResult = BCompileUtil.compileAndSetup(programPath);
        printDiagnostics(compileResult);
        if (compileResult.getDiagnostics().length > 0) {
            String errorsStr = "";
            for (Diagnostic diagnostic : compileResult.getDiagnostics()) {
                errorsStr = errorsStr.concat(diagnostic.getMessage() + System.lineSeparator());
            }
            Assert.fail("Compilation Errors" + System.lineSeparator() + errorsStr);
        }
        return compileResult;
    }
    private static void printDiagnostics(CompileResult timerCompileResult) {
        Arrays.asList(timerCompileResult.getDiagnostics()).
                forEach(e -> log.info(e.getMessage() + " : " + e.getPosition()));
    }

    public static BStruct createAndGetStruct(ProgramFile programFile, String packageName, String messageName) {
        PackageInfo jmsPackageInfo = programFile.getPackageInfo(packageName);
        StructInfo messageProperties = jmsPackageInfo.getStructInfo(messageName);
        BStruct messageStruct = BLangVMStructs.createBStruct(messageProperties);
        return messageStruct;
    }

    public static void invoke(CompileResult compileResult, 
                                String functionName, 
                                BValue[] args, 
                                JMSConnectorFutureListener callback) {
        WorkerExecutionContext parentCtx = new WorkerExecutionContext(compileResult.getProgFile());
        invoke(compileResult, functionName, args, parentCtx, callback);
    }

    public static void invoke(CompileResult compileResult, 
                                String functionName, 
                                BValue[] args, 
                                WorkerExecutionContext context, 
                                JMSConnectorFutureListener callback) {
        ProgramFile programFile = compileResult.getProgFile();
        PackageInfo packageInfo = programFile.getPackageInfo(programFile.getEntryPkgName());
        FunctionInfo callableUnitInfo = packageInfo.getFunctionInfo(functionName);

        //Initialise the Progam
        BLangFunctions.invokePackageInitFunction(packageInfo.getInitFunctionInfo(), context);
        BLangFunctions.invokeVMUtilFunction(packageInfo.getStartFunctionInfo(), context);

        //Execute
        BLangFunctions.invokeCallable(callableUnitInfo, context, args, callback);
        BLangScheduler.waitForWorkerCompletion();
        FutureWaiter ackCallback = (FutureWaiter) callback.getCallback();
        while (!ackCallback.isComplete()) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {

            }
            log.info("waiting for complete");
        }
    }

    public static BValue[] invoke(CompileResult compileResult, String functionName, BValue[] args) {
        WorkerExecutionContext parentCtx = new WorkerExecutionContext(compileResult.getProgFile());
        return invoke(compileResult, functionName, args, parentCtx);
    }

    public static BValue[] invoke(CompileResult compileResult, 
                                    String functionName, BValue[] args, 
                                    WorkerExecutionContext context) {
        ProgramFile programFile = compileResult.getProgFile();
        PackageInfo packageInfo = programFile.getPackageInfo(programFile.getEntryPkgName());
        FunctionInfo callableUnitInfo = packageInfo.getFunctionInfo(functionName);

        BValue[] result = BLangFunctions.invokeCallable(callableUnitInfo, context, args);
        BLangScheduler.waitForWorkerCompletion();
        return result;
    }
}
