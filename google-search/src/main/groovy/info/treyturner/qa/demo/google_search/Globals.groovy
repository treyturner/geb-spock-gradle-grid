package info.treyturner.qa.demo.google_search

import info.treyturner.qa.demo.util.Util

class Globals {

    //Might need to change these for local IDEA runs
    final static browserLocation = System.getProperty('browserLocation', 'local')
    final static browserType = System.getProperty('geb.local.browser', 'chrome')

    final static reportsDir = 'build/reports'
    final static platform = Util.getPlatform()
}
