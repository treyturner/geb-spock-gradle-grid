package util

import geb.js.JavascriptInterface
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

@Slf4j
class WebDriver {

    /**
     * This is where you can optionally supply the domain name or IP address of your
     * Selenium Grid hub for headless execution. A docker-compose.yml is included in the project
     * root; if you have docker and docker-compose installed, you can type `docker-compose up`
     * to start containers that log to console, or `docker-compose up -d` to start
     * the containers in daemon mode. You can then use `docker-compose scale firefox=5 chrome=5`
     * or similar to scale the nodes as needed.
     *
     * When the hub is running, you can visit http://localhost:4444/grid/console in a browser to
     * check the number and state of nodes.
     *
     * To stop all containers, you can issue `docker-compose stop`.
     *
     * For more information on available docker-selenium images and configuration, check out
     * https://github.com/SeleniumHQ/docker-selenium
     */
    static String gridUrl = System.getenv('GRID_URL') ?: 'http://localhost:4444/wd/hub'

    /**
     * This method, generally called from within each module's GebConfig.groovy,
     * configures a driver Closure that Geb needs to start a Selenium session.
     * It sets whether the session is local or remote, which browser to use,
     * and other browser- and platform-specific information such as driver paths.
     *
     * @param browserLocation   'local' or 'remote'
     * @param browserType       'firefox' and 'chrome' are supported for local/remote, 'phantomjs' is supported for local
     * @return                  a driver Closure that Geb can use to configure the Selenium session
     */
    static Closure configureDriver(String browser, String location) {
        Closure driver
        switch (location) {
            case 'local':
                switch (browser) {
                    case 'chrome':
                        driver = { new ChromeDriver() }
                        break
                    case 'chromeHeadless':
                        ChromeOptions options = new ChromeOptions().setHeadless(true)
                        driver = { new ChromeDriver(options) }
                        break
                    case 'firefox':
                        driver = { new FirefoxDriver() }
                        break
                    case 'firefoxHeadless':
                        FirefoxBinary binary = new FirefoxBinary()
                        binary.addCommandLineOptions("--headless")
                        FirefoxOptions options = new FirefoxOptions()
                        options.setBinary(binary)
                        driver = { new FirefoxDriver(options) }
                        break
                    default:
                        assert ['chrome', 'chromeHeadless', 'firefox', 'firefoxHeadless'].contains(browser), "Only chrome, chromeHeadless, firefox, and firefoxHeadless are supported for local browser sessions."
                }
                break
            case 'grid':
                driver = {
                    DesiredCapabilities capabilities
                    switch (browser) {
                        case 'chrome':
                            capabilities = DesiredCapabilities.chrome()
                            break
                        case 'chromeHeadless':
                            ChromeOptions options = new ChromeOptions()
                            options.setHeadless(true)
                            capabilities = DesiredCapabilities.chrome()
                            capabilities.setCapability(ChromeOptions.CAPABILITY, options)
                            break
                        case 'firefox':
                            capabilities = DesiredCapabilities.firefox()
                            break
                        case 'firefoxHeadless':
                            capabilities = DesiredCapabilities.firefox()
                            FirefoxOptions options = new FirefoxOptions()
                            options.setHeadless(true)
                            capabilities.merge(options)
                            break
                        default:
                            assert ['chrome', 'chromeHeadless', 'firefox', 'fireHeadless'].contains(browser), "Only chrome, chromeHeadless, firefox, and firefoxHeadless are supported for remote browser sessions."
                    }
                    new RemoteWebDriver(new URL(gridUrl), capabilities)
                }
                break
            default:
                assert ['local', 'grid'].contains(location), "Only local and grid locations are supported."
        }
        assert driver, "Couldn't configure WebDriver (browser:'$browser', location:'$location')"
        driver
    }

    /**
     * Returns a LinkedHashMap of all CSS properties and their
     * respective values for a given Navigator whose size() == 1
     *
     * @param   js          the JavaScript interface to the running Selenium session
     * @param   navigator   the navigator (with size() == 1) to inspect
     * @return              a LinkedHashMap of all CSS properties for the object and their respective values
     */
    static LinkedHashMap getComputedStyle(JavascriptInterface js, Navigator navigator) {
        assert navigator.size() == 1, "WebDriver.getComputedStyle() can only be called on Navigators with size() == 1"
        def map = [:],
            props
        if (navigator.@id)
            props = js."window.getComputedStyle(window.document.getElementById('${navigator.@id}'))"
        else {
            log.warn "Fetching computed style via class names; specific results are not guaranteed."
            props = js."window.getComputedStyle(window.document.getElementsByClassName('${navigator.classes().join("','")}'))"
        }
        props.each { map[it] = navigator.css(it) }
        map
    }

    /**
     * Calling .text() on an element typically returns the text from any elements nested underneath
     * as well. This method gives easy access to the text contained in just the selected element.
     * @param element   the web element containing the text to be extracted
     * @return          the text from only that element, omitting text in any children elements
     */
    static String getElementTextNonRecursively(element) {
        element.children().inject(element.text()) { text, child ->
            text - child.text()
        }.trim()
    }
}
