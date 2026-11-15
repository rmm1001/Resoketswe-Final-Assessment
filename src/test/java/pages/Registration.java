
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Registration {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Actual website locators
    private final By signUpHere = By.xpath(
            "//*[self::a or self::button]" +
                    "[contains(translate(normalize-space(.)," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz'),'sign up here')]"
    );

    private final By firstName =
            By.id("register-firstName");

    private final By lastName =
            By.id("register-lastName");

    private final By email =
            By.id("register-email");

    private final By password =
            By.id("register-password");

    private final By confirmPassword =
            By.id("register-confirmPassword");

    private final By groupDropdown =
            By.id("register-group");

    private final By createAccountButton =
            By.id("register-submit");

    private String registrationMessage;

    public Registration(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
        this.registrationMessage = registrationMessage;
    }

    // Open registration form
    public void openSignUpForm() {

        // If the form is already visible, do not click again
        if (!driver.findElements(firstName).isEmpty()
                && driver.findElement(firstName).isDisplayed()) {
            return;
        }

        wait.until(
                ExpectedConditions.elementToBeClickable(signUpHere)
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        );

        System.out.println("Registration form opened.");
    }

    private void enterText(By locator, String value) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        element.clear();
        element.sendKeys(value);
    }

    public void enterFirstName(String value) {
        enterText(firstName, value);
    }

    public void enterLastName(String value) {
        enterText(lastName, value);
    }

    public void enterEmail(String value) {
        enterText(email, value);
    }

    public void enterPassword(String value) {
        enterText(password, value);
    }

    public void enterConfirmPassword(String value) {
        enterText(confirmPassword, value);
    }

    // Select group
    public void selectGroup(String groupName) {

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        groupDropdown
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                dropdown
        );

        Select select = new Select(dropdown);

        select.selectByVisibleText(groupName);

        String selectedGroup = select
                .getFirstSelectedOption()
                .getText()
                .trim();

        if (!selectedGroup.equals(groupName)) {
            throw new AssertionError(
                    "Expected group: " + groupName +
                            ", but selected: " + selectedGroup
            );
        }

        System.out.println("Selected group: " + selectedGroup);
    }

    // Submit registration

    public String clickCreateAccount() {

        wait.until(
                ExpectedConditions.elementToBeClickable(createAccountButton)
        ).click();

        // Wait for the website's JavaScript confirmation
        wait.until(
                ExpectedConditions.alertIsPresent()
        );

        // Capture the alert message
        org.openqa.selenium.Alert alert = driver.switchTo().alert();

        String confirmationMessage = alert.getText();

        System.out.println(
                "Registration alert: " + confirmationMessage
        );

        // Accept the confirmation
        alert.accept();

        return confirmationMessage;
    }


    // Complete registration

    public String registerUser(
            String first,
            String last,
            String emailAddress,
            String pass
    ) {

        openSignUpForm();

        enterFirstName(first);
        enterLastName(last);
        enterEmail(emailAddress);
        enterPassword(pass);
        enterConfirmPassword(pass);

        selectGroup("Do Not Use (2026)");

        String confirmationMessage = clickCreateAccount();

        System.out.println(
                "Registration submitted for: " + emailAddress
        );

        return confirmationMessage;
    }


    public String getPageText() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.tagName("body")
                )
        ).getText();
    }
}
