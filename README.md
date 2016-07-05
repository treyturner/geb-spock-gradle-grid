# geb-spock-gradle-grid

Functional web testing using Geb, Spock, and Gradle. Browser tests execute locally or (optionally) against a Selenium Grid.

## Brief
This is a multi-module Gradle "archetype" project that can serve as a viable structure to develop functional test suites across multiple web applications. A shared utility package is available for convenience.

## Requirements
- Git
- Java 7+
- A browser (Firefox or Chrome)

## Setup & Run
1. Clone the repo.
  - `git clone git@github.com:treyturner/geb-spock-gradle-grid.git`
2. Run tests.
  - On Windows: `gradlew.bat test`
  - On Mac / Linux: `./gradlew test`
3. View the results.
  - HTML reports are written to `build/reports/tests`
  - Logs are written to `build/logs`

## Configuration
You have a few choices on how to run tests.
- Browser Type
  - Firefox (Usually easiest)
  - Chrome
- Browser Location
  - Local
  - Remote (Headless)
    - For continuous integration. Grid setup is out of scope for this document, see [docker-selenium]
    - Once you have your grid setup, edit the URI as appropriate in [WebDriver.groovy]

[docker-selenium]: https://github.com/SeleniumHQ/docker-selenium
[WebDriver.groovy]: util/src/main/groovy/info/treyturner/qa/demo/util/WebDriver.groovy#L17

Based on your choices to the above, edit the [Globals.groovy] file for the module you intend to configure.
[Globals.groovy]: google-search/src/main/groovy/info/treyturner/qa/demo/google_search/Globals.groovy

## IntelliJ IDEA
IDEA works great with Gradle; just 'Import project from existing sources' and select the root `build.gradle` script.
- It helps if you check 'auto-import' so your changes to Gradle files are immediately refreshed
- I also like to check 'automatically create content roots'

## Demos

### Google Search Demo
There is currently only a very basic example project, the [Google Search Demo]. It illustrates:

[Google Search Demo]: google-search

1. How to write a basic spec
2. How to write a basic page object
3. How to use modules to model table rows

## Adding new demos
It's easy to add a new module to start testing a new application.

1. Edit [settings.gradle] and add a line to `import 'your-module'`
2. Copy a [build.gradle]  into the newly created `your-module/`

[settings.gradle]: settings.gradle
[build.gradle]: google-search/build.gradle
