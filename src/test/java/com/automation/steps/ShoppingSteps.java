package com.automation.steps;

import com.automation.hooks.Hooks;
import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShoppingSteps {

    private InventoryPage inventoryPage() {
        return new InventoryPage(Hooks.getDriver());
    }

    private CartPage cartPage() {
        return new CartPage(Hooks.getDriver());
    }

    private CheckoutPage checkoutPage() {
        return new CheckoutPage(Hooks.getDriver());
    }

    @Given("I am logged in as {string}")
    public void iAmLoggedInAs(String username) {
        LoginPage loginPage = new LoginPage(Hooks.getDriver());
        loginPage.navigate();
        loginPage.login(username, "secret_sauce");
    }

    @Given("I am on the inventory page")
    public void iAmOnTheInventoryPage() {
        String url = Hooks.getDriver().getCurrentUrl();
        assertTrue("Expected inventory page but got: " + url, url.contains("inventory"));
    }

    @Given("I have {string} in my cart")
    public void iHaveInMyCart(String itemName) {
        inventoryPage().addItemToCart(itemName);
    }

    @When("I add {string} to the cart")
    public void iAddToTheCart(String itemName) {
        inventoryPage().addItemToCart(itemName);
    }

    @When("I remove {string} from the cart")
    public void iRemoveFromTheCart(String itemName) {
        inventoryPage().removeItemFromCart(itemName);
    }

    @Then("the cart badge should show {string}")
    public void theCartBadgeShouldShow(String expectedCount) {
        assertEquals(expectedCount, inventoryPage().getCartBadgeCount());
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        assertFalse("Cart badge should not be visible when empty", inventoryPage().isCartBadgeVisible());
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        inventoryPage().goToCart();
        cartPage().proceedToCheckout();
    }

    @When("I fill in first name {string} last name {string} postal code {string}")
    public void iFillInShippingInfo(String firstName, String lastName, String postalCode) {
        checkoutPage().fillShippingInfo(firstName, lastName, postalCode);
        checkoutPage().clickContinue();
    }

    @When("I complete the purchase")
    public void iCompleteThePurchase() {
        checkoutPage().clickFinish();
    }

    @Then("I should see the order confirmation message")
    public void iShouldSeeTheOrderConfirmationMessage() {
        String text = checkoutPage().getOrderCompleteText();
        assertTrue("Expected order confirmation but got: " + text, text.contains("Thank you"));
    }
}
