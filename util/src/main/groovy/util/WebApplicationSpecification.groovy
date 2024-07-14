package util

import org.slf4j.LoggerFactory
import spock.lang.Shared
import geb.spock.GebReportingSpec

class WebApplicationSpecification extends GebReportingSpec {

    @Shared log = LoggerFactory.getLogger(this.getClass())

    def setupSpec() {
        with(driver.capabilities) { log.info "${browserName} ${browserVersion} (${platformName})" }
    }
}
