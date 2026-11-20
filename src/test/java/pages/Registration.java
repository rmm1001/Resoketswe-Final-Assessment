
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Registration {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Actual website locators
    private final By signUpButton = By.id("signup-toggle");
    private final By firstName = By.id("register-firstName");
    private final By lastName = By.id("register-lastName");
    private final By email = By.id("register-email");
    private final By password = By.id("register-password");
    private final By confirmPassword = By.id("register-confirmPassword");
    private final By groupDropdown = By.id("register-group");
    private final By createAccountButton = By.id("register-submit");

    public Registration(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // Open registration form

    public void openSignUpForm() {
        System.out.println("\n========== OPENING REGISTRATION FORM ==========");

        WebDriverWait freshWait = new WebDriverWait(
                driver, Duration.ofSeconds(30)
        );

        // If the registration form is already open, do not click again.
        if (!driver.findElements(firstName).isEmpty()
                && driver.findElement(firstName).isDisplayed()) {
            System.out.println("Registration form is already open.");
            return;
        }

        // Check whether the signup toggle is already available.
        if (driver.findElements(signUpButton).isEmpty()) {

            System.out.println("Signup toggle not available. Opening Login form...");

            By mainLoginButton = By.xpath(
                    "//button[contains(normalize-space(.),'Login') " +
                            "and not(contains(normalize-space(.),'Login Here'))]"
            );

            freshWait.until(
                    ExpectedConditions.elementToBeClickable(mainLoginButton)
            ).click();

            // Wait until the login form appears.
            freshWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("login-email")
                    )
            );

            System.out.println("Login form opened.");
        }

        // Switch from Login to Sign Up.
        freshWait.until(
                ExpectedConditions.elementToBeClickable(signUpButton)
        ).click();

        // Confirm that the registration form is visible.
        freshWait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        );

        System.out.println("Registration form opened successfully.");
    }


    public void enterFirstName(String first) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        ).clear();

        driver.findElement(firstName).sendKeys(first);
    }

    public void enterLastName(String last) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastName)
        ).clear();

        driver.findElement(lastName).sendKeys(last);
    }

    public void enterEmail(String emailAddress) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(email)
        ).clear();

        driver.findElement(email).sendKeys(emailAddress);
    }

    public void enterPassword(String pass) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        ).clear();

        driver.findElement(password).sendKeys(pass);
    }

    public void enterConfirmPassword(String pass) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmPassword)
        ).clear();

        driver.findElement(confirmPassword).sendKeys(pass);
    }

    public void selectGroup(String groupName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(groupDropdown)
        );

        Select select = new Select(
                driver.findElement(groupDropdown)
        );

        select.selectByVisibleText(groupName);

        System.out.println("Selected group: " + groupName);
    }

    // Submit registration and capture confirmation

    public String clickCreateAccount() {

        wait.until(
                ExpectedConditions.elementToBeClickable(createAccountButton)
        ).click();

        // Wait for the confirmation alert
        wait.until(
                ExpectedConditions.alertIsPresent()
        );

        // Capture the confirmation message
        org.openqa.selenium.Alert alert = driver.switchTo().alert();

        String confirmationMessage = alert.getText();

        System.out.println(
                "Registration alert: " + confirmationMessage
        );

        // Keep confirmation visible for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        // Close confirmation after 5 seconds
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
