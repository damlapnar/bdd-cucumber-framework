package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        // The URL changes to checkout-step-one before the form has actually
        // mounted, so typing right after the navigation wait can land on
        // fields that get replaced by React's render a moment later. Wait
        // for the first field to be visible before touching any of them.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='firstName']")));
        typeReliably(By.cssSelector("[data-test='firstName']"), firstName);
        typeReliably(By.cssSelector("[data-test='lastName']"), lastName);
        typeReliably(By.cssSelector("[data-test='postalCode']"), postalCode);
    }

    // sendKeys() on these React-controlled inputs is flaky under headless
    // Chrome: click()+clear()+sendKeys() sometimes leaves the field empty
    // with no exception raised, confirmed by reading the field's live DOM
    // property immediately after typing (not just visually from a
    // screenshot, which can catch a value that "caught up" later). A
    // click()+clear()+sendKeys()+retry loop still isn't fully reliable -
    // even 3 attempts occasionally all miss. Setting the value through
    // the input's native property setter and dispatching a real 'input'
    // event bypasses whatever timing sendKeys is racing against entirely,
    // since it updates React's state directly rather than simulating
    // keystrokes for it to (maybe) pick up.
    private static final String SET_REACT_INPUT_VALUE =
            "const el = arguments[0];"
            + "const text = arguments[1];"
            + "const setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
            + "setter.call(el, text);"
            + "el.dispatchEvent(new Event('input', { bubbles: true }));";

    private void typeReliably(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.click();
        ((JavascriptExecutor) driver).executeScript(SET_REACT_INPUT_VALUE, element, text);
        wait.until(d -> text.equals(element.getDomProperty("value")));
    }

    public void clickContinue() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='continue']")));
        jsClick(button);
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void clickFinish() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='finish']")));
        jsClick(button);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }

    public String getOrderCompleteText() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".complete-header")));
        return header.getText();
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
