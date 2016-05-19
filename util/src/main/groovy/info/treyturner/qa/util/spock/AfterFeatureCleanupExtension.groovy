package info.treyturner.qa.util.spock

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo

class AfterFeatureCleanupExtension extends AbstractAnnotationDrivenExtension<AfterFeature> {

    @Override
    void visitFeatureAnnotation(AfterFeature annotation, FeatureInfo feature) {
        def methodToInvoke = annotation.value()
        def interceptor = new AfterFeatureCleanupInterceptor(methodToInvoke: methodToInvoke)
        feature.addInterceptor(interceptor)
    }
}
