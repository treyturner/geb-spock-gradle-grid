package util.spock

import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

class AfterFeatureCleanupInterceptor extends AbstractMethodInterceptor {

    String methodToInvoke

    @Override
    void interceptFeatureExecution(IMethodInvocation invocation) throws Throwable {
        def currentlyRunningSpec = invocation.sharedInstance
        invocation.proceed()
        currentlyRunningSpec."$methodToInvoke"()
    }
}
