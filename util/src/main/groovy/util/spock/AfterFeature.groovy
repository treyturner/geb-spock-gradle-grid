package util.spock

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

@org.spockframework.runtime.extension.ExtensionAnnotation(AfterFeatureCleanupExtension)

public @interface AfterFeature {

    String value();
}
