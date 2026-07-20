package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
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
    // with no exception raised (confirmed by reading the field's live DOM
    // property immediately after typing - not just visually from a
    // screenshot, which can catch a field that "caught up" later). Verifying
    // the value actually landed - and retrying if not - is what makes this
    // deterministic instead of occasionally flaky.
    private void typeReliably(By locator, String text) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            WebElement element = driver.findElement(locator);
            element.click();
            element.clear();
            element.sendKeys(text);
            try {
                new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(d -> text.equals(driver.findElement(locator).getDomProperty("value")));
                return;
            } catch (TimeoutException e) {
                if (attempt == 3) {
                    throw new IllegalStateException(
                            "Could not type '" + text + "' into " + locator + " after 3 attempts", e);
                }
            }
        }
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
