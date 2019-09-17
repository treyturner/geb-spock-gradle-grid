package search_engine_exercises

class Globals {

    final static browser = System.getProperty('geb.browser', 'chrome')
    final static location = System.getProperty('geb.location', 'local')

    final static baseUrl = ""
    final static reportsDir = "build/reports"
}
