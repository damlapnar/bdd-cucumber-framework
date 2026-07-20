package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Native click() doesn't reliably register on saucedemo's React-controlled
    // buttons in headless Chrome; dispatching via JavascriptExecutor does.
    public void addItemToCart(String itemName) {
        int prevCount = currentCartCount();
        String xpath = "//div[contains(@class,'inventory_item')]"
                + "[.//div[contains(@class,'inventory_item_name') and text()='" + itemName + "']]"
                + "//button[contains(text(),'Add to cart')]";
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        jsClick(button);
        int expected = prevCount + 1;
        wait.until(d -> currentCartCount() == expected);
    }

    public void removeItemFromCart(String itemName) {
        int prevCount = currentCartCount();
        String xpath = "//div[contains(@class,'inventory_item')]"
                + "[.//div[contains(@class,'inventory_item_name') and text()='" + itemName + "']]"
                + "//button[contains(text(),'Remove')]";
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        jsClick(button);
        int expected = prevCount - 1;
        wait.until(d -> currentCartCount() == expected);
    }

    public String getCartBadgeCount() {
        return String.valueOf(currentCartCount());
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(By.cssSelector(".shopping_cart_badge")).isEmpty();
    }

    public void goToCart() {
        // Direct navigation avoids the same headless-click reliability issue
        // for the cart icon itself.
        String origin = driver.getCurrentUrl().replaceAll("(https?://[^/]+).*", "$1");
        driver.navigate().to(origin + "/cart.html");
    }

    private int currentCartCount() {
        List<WebElement> badges = driver.findElements(By.cssSelector(".shopping_cart_badge"));
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
