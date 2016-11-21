package util.spock

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Unroll

@Slf4j
class AfterFeatureSpec extends Specification {

    @Shared cleanupCount = 0

    def cleanupFeature() {
        //only runs once, even if the feature iterates using a 'where' block
        log.info "closing a file or database connection..."
        cleanupCount++
    }

    @Unroll
    @AfterFeature("cleanupFeature")
    def "#myInt squared should equal #myInt times itself"() {

        expect: "the square of an Integer to equal the number times itself"
        //Just some dummy test context
        myInt**2 == myInt * myInt

        and: "we should only ever cleanup once"
        //Here is the important assert
        cleanupCount == 0

        where:
        //Iterates the test, filling myInt with each value of the Array
        myInt << [1, 2, 3, 4, 5]
    }
}
