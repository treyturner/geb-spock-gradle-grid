# geb-spock-gradle-grid

This multi-module [Gradle] project integrates [Geb] and [Spock] to create a web automation framework. The executable BDD specifications run [Selenium] sessions locally or (optionally) against a scalable [grid].

Multi-level logging is supported via [slf4j] and [logback]. A shared utility package is utilized to store common code. Integration with []IntelliJ IDEA] is seamless for easy step debugging and testing pipeline automation entrypoints.

The project is intended for educational and demonstration purposes, and can serve as a starting point to rapidly develop functional test suites or automation scripts across multiple web applications. Additional examples and design patterns will be added over time and an effort will be made to keep the included technologies up to date with modern browser standards.

## Currently Implemented Technology Versions

Software       |Version
|---	       |---	
[Geb] 	       |1.0   	
[Spock]	       |1.1   	
[Gradle]       |3.2	
[Selenium]     |3.0.1   	
[ChromeDriver] |2.25
[GeckoDriver]  |0.11.1
[slf4j]        |1.7.21
[logback]      |1.1.7

[Geb]: http://gebish.org
[Spock]: http://spockframework.org
[Gradle]: https://gradle.org/
[Selenium]: http://docs.seleniumhq.org/
[Grid]: https://github.com/SeleniumHQ/docker-selenium
[ChromeDriver]: https://sites.google.com/a/chromium.org/chromedriver/
[GeckoDriver]: https://github.com/mozilla/geckodriver
[slf4j]: http://www.slf4j.org/
[logback]: http://logback.qos.ch/

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
  - Firefox
  - Chrome
- Browser Location
  - Local
  - Remote (Headless)
    - For continuous integration. Docker is ideal for setting up your own [grid].
    - Once you have your grid setup, edit the URI as appropriate in [WebDriver.groovy]

[WebDriver.groovy]: util/src/main/groovy/info/treyturner/qa/demo/util/WebDriver.groovy#L22

Based on your choices to the above, edit the [Globals.groovy] file for the module you intend to configure.
[Globals.groovy]: search-engine-exercises/src/main/groovy/info/treyturner/qa/demo/search_engine_exercises/Globals.groovy

## IntelliJ IDEA
IDEA works great with Gradle; just 'Import project from existing sources' and select the root `build.gradle` script.
- It helps if you check 'auto-import' so your changes to Gradle files are immediately refreshed
- I also check 'automatically create content roots' for when I add a new modules to `settings.gradle`.

## Demos

### Google Search Demo
There is currently only a very basic Google search in the [Search Engine Exercises] module. It illustrates:

[Search Engine Exercises]: search-engine-exercises

1. How to write a basic spec
2. How to write a basic page object
3. How to use modules to model table rows

## Adding new demos
It's easy to add a new module to start testing a new application.

1. Edit [settings.gradle] and add a line to `import 'your-module'`
2. Copy a [build.gradle]  into the newly created `your-module/`

[settings.gradle]: settings.gradle
[build.gradle]: search-engine-exercises/build.gradle
