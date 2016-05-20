package info.treyturner.qa.demo.util.spock

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo

class BeforeFeatureSetupExtension extends AbstractAnnotationDrivenExtension<BeforeFeature> {

    @Override
    void visitFeatureAnnotation(BeforeFeature annotation, FeatureInfo feature) {
        def methodToInvoke = annotation.value()
        def interceptor = new BeforeFeatureSetupInterceptor(methodToInvoke: methodToInvoke)
        feature.addInterceptor(interceptor)
    }
}
