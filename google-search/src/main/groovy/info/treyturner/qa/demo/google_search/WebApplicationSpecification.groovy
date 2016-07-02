package info.treyturner.qa.demo.google_search

import org.slf4j.LoggerFactory
import spock.lang.Shared
import geb.spock.GebReportingSpec

class WebApplicationSpecification extends GebReportingSpec {

    @Shared log = LoggerFactory.getLogger(this.getClass())

    def setupSpec() {
        def capabilities = driver.capabilities
        log.info "$capabilities.browserName $capabilities.version ($capabilities.platform) - $driver.sessionId"
    }
}
