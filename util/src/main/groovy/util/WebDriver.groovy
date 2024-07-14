package util

import geb.js.JavascriptInterface
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
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
     * Since driver management is handled by Gradle, IDEA is unaware where to find the drivers
     * when you right click a spec to execute it. This requires that you first run a Gradle test
     * task which will automatically download the drivers. We'll then store the driver paths into a
     * driver.properties file in the project root so that we can later inject them into IDEA
     * executions.
     *
     * @param propertyName  'webdriver.chrome.driver', 'webdriver.gecko.driver', etc.
     */
    static void setOrRetrieveDriverPath(String propertyName) {
        // Create/read a properties file to read/write driver data for local IDEA executions
        Properties props = new Properties()
        File propsFile = new File('../driver-paths.properties')
        if (!propsFile.exists()) { propsFile.createNewFile() }
        props.load(propsFile.newDataInputStream())

        // Store the property if we have it, read it if we don't
        if (System.getProperty(propertyName)) {
            props.setProperty(propertyName, System.getProperty(propertyName))
            props.store(propsFile.newWriter(), null)
        } else {
            if (props.getProperty(propertyName)) {
                System.setProperty(propertyName, props.getProperty(propertyName))
            } else {
                throw new Exception("Couldn't determine driver path. You must run a gradle test task before executing a spec in IDEA.")
            }
        }
    }

    /**
     * Sets (or gets) the driver locations for Chrome, Firefox, and Internet Explorer
     */
    static void setOrRetrieveDriverPaths() {
        setOrRetrieveDriverPath('webdriver.chrome.driver')
        setOrRetrieveDriverPath('webdriver.gecko.driver')
    }

    /**
     * This method, generally called from within each module's GebConfig.groovy,
     * configures a driver Closure that Geb needs to start a Selenium session.
     * It sets whether the session is local or remote, which browser to use,
     * and other browser- and platform-specific options as needed.
     *
     * @param browserLocation   'local' or 'grid'
     * @param browserType       'chrome', 'chromeHeadless', 'firefox', and 'firefoxHeadless' are supported
     * @return                  a driver Closure that Geb can use to configure the Selenium session
     */
    static Closure configureDriver(String browser, String location) {
        // Set driver paths for later use if we have them, fetch them if we don't
        setOrRetrieveDriverPaths()

        // Make the driver
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
                        FirefoxOptions options = new FirefoxOptions()
                        options.addArguments("--headless")
                        driver = { new FirefoxDriver(options) }
                        break
                    default:
                        assert ['chrome', 'chromeHeadless', 'firefox', 'firefoxHeadless'].contains(browser),
                                "Only chrome, chromeHeadless, firefox, and firefoxHeadless are supported for local browser sessions."
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
                            assert ['chrome', 'chromeHeadless', 'firefox', 'fireHeadless'].contains(browser),
                                    "Only chrome, chromeHeadless, firefox, and firefoxHeadless are supported for remote browser sessions."
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
