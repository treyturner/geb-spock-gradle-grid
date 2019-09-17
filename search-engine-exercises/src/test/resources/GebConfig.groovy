import search_engine_exercises.Globals
import util.WebDriver

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
            timeout = 180
            retryInterval = 1
        }
    }
}

//Globals (available to specs via browser.config.rawConfig)
reportsDir = Globals.reportsDir
baseUrl = Globals.baseUrl
driver = WebDriver.configureDriver(Globals.browser, Globals.location)
