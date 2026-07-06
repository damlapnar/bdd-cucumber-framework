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
- **CI/CD** — GitHub Actions pipeline

## Project Structure

```
bdd-cucumber-framework/
├── src/
│   ├── main/java/com/automation/
│   │   ├── steps/       # Step definitions
│   │   ├── pages/       # Page Object classes
│   │   ├── hooks/       # Before/After hooks
│   │   └── runner/      # JUnit test runner
│   └── test/resources/
│       └── features/    # Gherkin .feature files
```

## Running Tests

```bash
# All scenarios
mvn test

# Specific tag
mvn test -Dcucumber.filter.tags="@smoke"

# Headless
mvn test -Dheadless=true
```
