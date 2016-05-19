package info.treyturner.qa.util

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

    static def configureDriver(String browserLocation, String browserType, String platform) {
        Closure driver
        switch (browserLocation) {
            case 'local':
                switch (browserType) {
                    case 'firefox':
                        driver = { new FirefoxDriver() }
                        break
                    case 'chrome':
                        def chromeDriverVersion = "2.21"
                        switch (platform) {
                            case 'windows32':
                            case 'windows64':
                                System.setProperty("webdriver.chrome.driver", "../util/drivers/chrome/$chromeDriverVersion/chromedriver_win32.exe")
                                break
                            case 'osx':
                                System.setProperty("webdriver.chrome.driver", "../util/drivers/chrome/$chromeDriverVersion/chromedriver_mac32")
                                break
                            case 'linux32':
                                System.setProperty("webdriver.chrome.driver", "../util/drivers/chrome/$chromeDriverVersion/chromedriver_linux32")
                                break
                            case 'linux64':
                                System.setProperty("webdriver.chrome.driver", "../util/drivers/chrome/$chromeDriverVersion/chromedriver_linux64")
                                break
                        }
                        driver = { new ChromeDriver() }
                        break
                    case 'phantomjs':
                        def driverPath
                        switch (platform) {
                            case 'windows32':
                            case 'windows64':
                                driverPath = '/some/windows/path'
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
                            //You must setup your own grid and configure the IP or domain name here
                            new URL("http://your.grid.ip.address:4444/wd/hub"), capabilities
                    )
                }
                break
        }
        assert driver, "Couldn't configure WebDriver (location:'$browserLocation', type:'$browserType', platform:'$platform')"
        driver
    }
}
