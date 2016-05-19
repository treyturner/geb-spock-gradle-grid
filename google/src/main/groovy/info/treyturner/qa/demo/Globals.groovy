package info.treyturner.qa.demo

import info.treyturner.qa.util.Util

class Globals {

    //Might need to change these for local IDEA runs
    final static browserLocation = System.getProperty('browserLocation', 'local')
    final static browserType = System.getProperty('geb.local.browser', 'chrome')

    final static reportsDir = 'build/reports'
    final static platform = Util.getPlatform()
}
