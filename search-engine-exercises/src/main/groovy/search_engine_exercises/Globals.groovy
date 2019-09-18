package search_engine_exercises

class Globals {

    // This is where you can set the browser for IDEA execution; Gradle tasks override this default
    final static browser = System.getProperty('geb.browser', 'chrome')

    // Set the browser location: 'local' or 'grid'. Applies to both Gradle and IDEA executions
    final static location = System.getProperty('geb.location', 'local')

    // You can set a base URL here as needed/appropriate
    final static baseUrl = ""

    // Choose where reports go
    final static reportsDir = "build/reports"
}
