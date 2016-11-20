package info.treyturner.qa.demo.search_engine_exercises

import info.treyturner.qa.demo.util.Util

class Globals {

    //Change these for local IDEA runs
    final static browserLocation = System.getProperty('browserLocation', 'local')
    final static browserType = System.getProperty('geb.local.browser', 'chrome')

    final static reportsDir = 'build/reports'
    final static platform = Util.getPlatform()
}
