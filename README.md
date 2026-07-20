# BDD Cucumber Framework

![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?style=flat-square&logo=cucumber&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=flat-square&logo=selenium&logoColor=white)
[![CI](https://github.com/damlapnar/bdd-cucumber-framework/actions/workflows/bdd-tests.yml/badge.svg)](https://github.com/damlapnar/bdd-cucumber-framework/actions/workflows/bdd-tests.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

BDD test framework using Cucumber 7, Selenium, and Gherkin. Human-readable test scenarios bridge the gap between business stakeholders and engineering teams.

## Features

- **Gherkin Scenarios** — plain English test cases readable by non-technical stakeholders
- **Scenario Outline** — data-driven tests with Examples tables
- **Hooks** — `@Before`/`@After` with screenshot capture on failure
- **Allure Reports** — rich reporting with step-by-step breakdown
- **Static Analysis** — curated Checkstyle ruleset (`config/checkstyle.xml`), run in the Maven `verify` phase
- **CI/CD** — GitHub Actions pipeline

## Project Structure

```
bdd-cucumber-framework/
├── src/test/
│   ├── java/com/automation/
│   │   ├── steps/       # Step definitions (LoginSteps, ShoppingSteps)
│   │   ├── pages/       # Page Object classes
│   │   ├── hooks/       # Before/After hooks
│   │   └── runner/      # JUnit test runner
│   └── resources/
│       └── features/    # Gherkin .feature files (login, shopping)
```

All of the above live under `src/test/java`, not `src/main/java` — Maven Surefire's default test discovery only scans compiled classes under `target/test-classes`, so the JUnit `TestRunner` (and everything it depends on) has to be a test source, not a main one.

## Running Tests

```bash
# All scenarios
mvn test

# Specific tag
mvn test -Dcucumber.filter.tags="not @skip"

# Headless
mvn test -Dheadless=true
```
