package util

import geb.js.JavascriptInterface
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

@Slf4j
class WebDriver {

    /**
     * This is where you can optionally supply the domain name or IP address of your
     * Selenium Grid hub for remote test execution. For more info on easily setting
     * up a scalable grid, visit https://github.com/SeleniumHQ/docker-selenium
     */
    static String gridUri = "http://your.grid.ip.address:4444/wd/hub";

    /**
     * This method, generally called from within each module's GebConfig.groovy,
     * configures a driver Closure that Geb needs to start a Selenium session.
     * It sets whether the session is local or remote, which browser to use,
     * and other browser- and platform-specific information such as driver paths.
     *
     * @param browserLocation   'local' or 'remote'
     * @param browserType       'firefox' and 'chrome' are supported for local/remote, 'phantomjs' is supported for local
     * @param platform          resolved by Util.getPlatform()
     * @return                  a driver Closure that Geb can use to configure the Selenium session
     */
    static Closure configureDriver(String browserLocation, String browserType, String platform) {
        Closure driver
        String driverPath = "../util/drivers"
        switch (browserLocation) {
            case 'local':
                switch (browserType) {
                    case 'firefox':
                        def geckoDriverVersion = "0.15.0"
                        switch (platform) {
                            case 'windows32':
                                System.setProperty("webdriver.gecko.driver",
                                        "$driverPath/gecko/geckodriver-v$geckoDriverVersion-win32/geckodriver.exe")
                                break
                            case 'windows64':
                                System.setProperty("webdriver.gecko.driver",
                                        "$driverPath/gecko/geckodriver-v$geckoDriverVersion-win64/geckodriver.exe")
                                break
                            case 'osx':
                                System.setProperty("webdriver.gecko.driver",
                                        "$driverPath/gecko/geckodriver-v$geckoDriverVersion-macos/geckodriver")
                                break
                            case 'linux32':
                            case 'linux64':
                                System.setProperty("webdriver.gecko.driver",
                                        "$driverPath/gecko/geckodriver-v$geckoDriverVersion-linux64/geckodriver")
                                break
                        }
                        driver = { new FirefoxDriver() }
                        break
                    case 'chrome':
                        def chromeDriverVersion = "2.28"
                        switch (platform) {
                            case 'windows32':
                            case 'windows64':
                                System.setProperty("webdriver.chrome.driver",
                                        "$driverPath/chrome/$chromeDriverVersion/chromedriver_win32/chromedriver.exe")
                                break
                            case 'osx':
                                System.setProperty("webdriver.chrome.driver",
                                        "$driverPath/chrome/$chromeDriverVersion/chromedriver_mac64/chromedriver")
                                break
                            case 'linux32':
                                System.setProperty("webdriver.chrome.driver",
                                        "$driverPath/chrome/$chromeDriverVersion/chromedriver_linux32/chromedriver")
                                break
                            case 'linux64':
                                System.setProperty("webdriver.chrome.driver",
                                        "$driverPath/chrome/$chromeDriverVersion/chromedriver_linux64/chromedriver")
                                break
                        }
                        driver = { new ChromeDriver() }
                        break
                    case 'phantomjs':
                        /**
                         * PhantomJS was only tested on OSX, and never worked very well. If you want to
                         * try, you'll need to acquire binaries for your OS, set the paths appropriately,
                         * and quite possibly set some other driver options not covered here.
                         */
                        switch (platform) {
                            case 'windows32':
                                driverPath = "$driverPath/phantomjs/phantomjs_win32.exe"
                                break
                            case 'windows64':
                                driverPath = "$driverPath/phantomjs/phantomjs_win64.exe"
                                break
                            case 'osx':
                                driverPath = '/usr/local/bin/phantomjs'
                                break
                            case 'linux32':
                            case 'linux64':
                                driverPath = '/usr/bin/phantomjs'
                                break
                        }
                        driver = {
                            log.trace "Resolved PhantomJS driver path: $driverPath"
                            def caps = new DesiredCapabilities()
                            caps.setCapability(
                                    PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                                    driverPath
                            )
                            def d = new PhantomJSDriver(caps)
                            d.manage().window().setSize(new Dimension(1024,768))
                            d
                        }
                }
                break
            case 'remote':
                driver = {
                    DesiredCapabilities capabilities
                    switch (browserType) {
                        case 'firefox':
                            capabilities = DesiredCapabilities.firefox()
                            break
                        case 'chrome':
                            capabilities = DesiredCapabilities.chrome()
                            break
                        default:
                            assert ['firefox', 'chrome'].contains(browserType), "Only Firefox and Chrome are currently supported for remote WebDriver sessions."
                    }
                    new RemoteWebDriver(
                            new URL(gridUri), capabilities
                    )
                }
                break
        }
        assert driver, "Couldn't configure WebDriver (location:'$browserLocation', type:'$browserType', platform:'$platform')"
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
     * as well. This method gives easy access to the text contained in just the targeted element.
     * @param element   the web element containing the text to be extracted
     * @return          the text from only that element, omitting text in any children elements
     */
    static String getElementTextNonRecursively(element) {
        element.children().inject(element.text()) { text, child ->
            text - child.text()
        }.trim()
    }
}
