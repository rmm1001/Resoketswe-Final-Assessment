
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginNavigationButton =
            By.cssSelector("button.user-pill");

    private final By emailField =
            By.id("login-email");

    private final By passwordField =
            By.id("login-password");

    private final By loginButton =
            By.id("login-submit");

    public Login(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openLogin() {

        System.out.println("\n========== OPENING LOGIN ==========");

        // Check whether the login form is already displayed
        if (!driver.findElements(emailField).isEmpty()
                && driver.findElement(emailField).isDisplayed()) {

            System.out.println("Login form is already visible.");
            return;
        }

        // Click the actual Login button found on the website
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        loginNavigationButton
                )
        );

        loginButton.click();

        System.out.println("Clicked the Login navigation button.");

        // Wait for the login form
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        );

        System.out.println("Login form is visible.");
        System.out.println("========== END OPENING LOGIN ==========");
    }

    public void enterEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Login email is null or empty. Check Steps.java."
            );
        }

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        );

        field.clear();
        field.sendKeys(email);
    }

    public void enterPassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Login password is null or empty. Check Steps.java."
            );
        }

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        );

        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();

        System.out.println("Login button clicked.");
    }

    public void login(String email, String password) {

        openLogin();
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
