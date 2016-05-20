import info.treyturner.qa.demo.google.Globals
import info.treyturner.qa.demo.util.WebDriver

waiting {
    //default (ie. wait:true)
    timeout = 10
    retryInterval = 0.5

    presets {
        shortest {
            timeout = 3
            retryInterval = 0.25
        }
        shorter {
            timeout = 6
            retryInterval = 0.25
        }
        longer {
            timeout = 20
            retryInterval = 0.5
        }
        longest {
            timeout = 45
            retryInterval = 0.5
        }
        coffee {
            timeout = 120
            retryInterval = 1
        }
    }
}

//Globals (available to specs via browser.config.rawConfig.property)
reportsDir = Globals.reportsDir
//baseUrl = Globals.baseUrl
driver = WebDriver.configureDriver(Globals.browserLocation, Globals.browserType, Globals.platform)
