# geb-spock-gradle-grid

Functional web testing using Geb, Spock, and Gradle. Browser tests execute locally or (optionally) against a Selenium Grid.

## Brief
This is a multi-module Gradle 'archetype' project that has served as a viable structure for me to develop functional test suites across multiple web applications. A shared utility package is available for convenience.

## Requirements
- Git
- Java 7+
- A browser (Firefox or Chrome)

## Setup
1. Clone the repo.
  - `git clone git@github.com:treyturner/geb-spock-gradle-grid.git`
2. Run tests.
  - On Windows: `gradlew.bat test`
  - On Mac / Linux: `./gradlew test`
3. View the results.
  - HTML reports are written to `build/reports/tests`
  - Logs are written to `build/logs`

You have a few choices on how to run tests.
- Browser Type
  - Firefox (Usually easiest)
  - Chrome
- Browser Location
  - Local
  - Remote (Headless)
    - For continuous integration. Grid setup is out of scope for this document, see [docker-selenium]
    - Once you have your grid setup, edit the URI as appropriate in [WebDriver.groovy]

Based on your choice to the above, edit the [Globals.groovy] file for the module you want to configure.

## Demos

### Google Search Demo
The first very basic example project, the [Google Search Demo], is merged to master. It illustrates:

  1. How to write a basic spec
  2. How to write a basic page object
  3. How to model repeating data structures, like table rows

[docker-selenium]: https://github.com/SeleniumHQ/docker-selenium
[Google Search Demo]: google-search
[Globals.groovy]: google-search/src/main/groovy/info/treyturner/qa/demo/google_search/Globals.groovy
[WebDriver.groovy]: util/src/main/groovy/info/treyturner/qa/demo/util/WebDriver.groovy
