package search_engine_exercises

import util.Util

class Globals {

    //Change these for local IDEA runs
    final static browserLocation = System.getProperty('browserLocation', 'local')
    final static browserType = System.getProperty('geb.local.browser', 'chrome')

    final static baseUrl = ""
    final static reportsDir = 'build/reports'
    final static platform = Util.getPlatform()
}
