package com.automation.steps;

import com.automation.pages.LoginPage;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    private final WebDriver driver;
    private final LoginPage loginPage;

    public LoginSteps(WebDriver driver) {
        this.driver = driver;
        this.loginPage = new LoginPage(driver);
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.navigate();
    }

    @When("I enter username {string} and password {string}")
    public void iEnterCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void iClickLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the inventory page")
    public void iShouldBeOnInventoryPage() {
        assert driver.getCurrentUrl().contains("inventory") :
            "Expected inventory page but got: " + driver.getCurrentUrl();
    }

    @Then("I should see an error message containing {string}")
    public void iShouldSeeErrorMessage(String expectedText) {
        String actual = loginPage.getErrorMessage();
        assert actual.contains(expectedText) :
            "Expected error containing '" + expectedText + "' but got: " + actual;
    }
}
