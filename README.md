# geb-spock-gradle-grid

This multi-module [Gradle] project leverages [Geb] and [Spock] to create a behavior-driven development ([BDD]) web automation framework. The executable specifications, written in [Groovy] or [Java], read like plain English and drive automated [Selenium] browser sessions locally or (optionally) against a scalable remote [grid]. The use of [Page Object modeling] ensures the maintainability and re-usability of code.

Multi-level logging is supported via [slf4j] and [logback]. A shared utility package is utilized to store common code. There is seamless Integration with IntelliJ [IDEA], enabling easy step-by-step debugging and the testing of pipeline automation entry-points.

The project is intended for educational and demonstration purposes, and can serve as a starting point to rapidly develop functional web application test suites or automation scripts across a variety of web applications. Additional examples and design patterns will be added over time and an effort will be made to keep the integrated technologies up to date with modern browser standards.

[Page Object modeling]: https://github.com/SeleniumHQ/selenium/wiki/PageObjects

## Technologies Integrated

Software       |Version |Description
|---	       |---	    |---
[Groovy]       |2.4.7   |A powerful and expressive JVM ([Java]) scripting language
[Geb] 	       |1.0   	|High level WebDriver API
[Spock]	       |1.1   	|[BDD] test/specification runner
[Gradle]       |3.2	    |Build tool & dependency management
[Selenium]     |3.0.1   |Browser automation API
[ChromeDriver] |2.25    |Selenium driver for Chrome
[GeckoDriver]  |0.11.1  |Selenium driver for Mozilla
[slf4j]        |1.7.21  |Logging API
[logback]      |1.1.7   |Logging implementation

[Groovy]: http://groovy-lang.org/
[Geb]: http://gebish.org
[Spock]: http://spockframework.org
[BDD]: https://en.wikipedia.org/wiki/Behavior-driven_development
[Gradle]: https://gradle.org
[Selenium]: http://docs.seleniumhq.org
[Grid]: https://github.com/SeleniumHQ/docker-selenium
[ChromeDriver]: https://sites.google.com/a/chromium.org/chromedriver
[GeckoDriver]: https://github.com/mozilla/geckodriver
[slf4j]: http://www.slf4j.org
[logback]: http://logback.qos.ch
[IDEA]: https://www.jetbrains.com/idea

## Requirements
- [Git]
- [Java] Development Kit 7+
- A browser. Tested against [Firefox] v50 and [Chrome] v54

[Git]: https://git-scm.com/
[Java]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[Firefox]: https://www.mozilla.org/en-US/firefox/new
[Chrome]: https://www.google.com/chrome/browser/desktop

## Setup & Run
1. Clone the repo.
  - `git clone git@github.com:treyturner/geb-spock-gradle-grid.git`
2. Run specs.
  - On Windows: `gradlew.bat test`
  - On Mac / Linux: `./gradlew test`
3. View the results.
  - HTML reports are written to `build/reports/tests`
  - JUnit XML results (for CI/[Jenkins] consumption) are written to `build/test-results/tests`
  - Console text logs are written to `build/logs`

[Jenkins]: https://jenkins.io

## Configuration
You have a few choices on how to run tests.
- Browser Type
  - Firefox
  - Chrome
- Browser Location
  - Local
  - Remote (Headless)
    - For continuous integration. Docker is ideal for setting up your own [grid]
    - Once you have your grid setup, edit the URI as appropriate in [WebDriver.groovy]

[WebDriver.groovy]: util/src/main/groovy/info/treyturner/qa/demo/util/WebDriver.groovy#L22

Based on your choices to the above, edit the [Globals.groovy] file for the module you intend to configure.
[Globals.groovy]: search-engine-exercises/src/main/groovy/info/treyturner/qa/demo/search_engine_exercises/Globals.groovy#L8

## IntelliJ IDEA
[IDEA] works great with Gradle; just 'Import project from existing sources' and select the root `build.gradle` script.
- It helps if you check 'auto-import' so your changes to Gradle files are immediately refreshed
- I also check 'automatically create content roots' for when I add a new modules to `settings.gradle`

## Demos

### Search Engine Exercises Module

#### Google Search Demo
There is currently only a very basic Google search in the [Search Engine Exercises] module. It illustrates:

[Search Engine Exercises]: search-engine-exercises

1. How to write a basic [spec]
2. How to write basic [page objects]
3. How to use [modules] to model table rows

[spec]: search-engine-exercises/src/test/groovy/info/treyturner/qa/demo/search_engine_exercises/spec/google/GoogleSearchSpec.groovy
[page objects]: search-engine-exercises/src/test/groovy/info/treyturner/qa/demo/search_engine_exercises/page/google
[modules]: search-engine-exercises/src/test/groovy/info/treyturner/qa/demo/search_engine_exercises/module/google/GoogleSearchResult.groovy


## Adding new modules
It's easy to add a new module to start testing a new application.

1. Edit [settings.gradle] and add a line to `import 'your-module'`
2. Copy a [build.gradle]  into the newly created `your-module/`
3. Copy a [GebConfig.groovy] into `your-module/src/test/resources`

[settings.gradle]: settings.gradle
[build.gradle]: search-engine-exercises/build.gradle
[GebConfig.groovy]: search-engine-exercises/src/test/resources/GebConfig.groovy
