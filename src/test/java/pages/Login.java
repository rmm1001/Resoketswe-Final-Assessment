
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Login {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Navigation Login control
    private final By loginNavigation =
            By.xpath(
                    "//nav//*[self::a or self::button]"
                            + "[normalize-space()='Login' "
                            + "or normalize-space()='Login Here']"
            );

    // Login form
    private final By emailField =
            By.cssSelector("input[type='email']");

    private final By passwordField =
            By.cssSelector("input[type='password']");

    private final By loginButton =
            By.xpath(
                    "//button[contains("
                            + "translate(normalize-space(.),"
                            + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                            + "'abcdefghijklmnopqrstuvwxyz'),"
                            + "'login')]"
            );

    public Login(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // Open Login page
    public void openLogin() {

        List<WebElement> buttons = driver.findElements(
                loginNavigation
        );

        if (!buttons.isEmpty()) {

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            loginNavigation
                    )
            ).click();

        } else {

            // Fallback: find Login Here anywhere on the page
            By loginHere = By.xpath(
                    "//*[self::a or self::button]"
                            + "[normalize-space()='Login Here']"
            );

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            loginHere
                    )
            ).click();
        }

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        emailField
                )
        );
    }

    // Enter email
    public void enterEmail(String username) {

        WebElement email = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        emailField
                )
        );

        email.clear();
        email.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String pass) {

        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        passwordField
                )
        );

        password.clear();
        password.sendKeys(pass);
    }

    // Click Login
    public void clickLogin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        loginButton
                )
        ).click();
    }

    // Complete login
    public void login(String username, String pass) {

        enterEmail(username);
        enterPassword(pass);
        clickLogin();
    }

    public String getPageText() {

        return driver.findElement(
                By.tagName("body")
        ).getText();
    }

}
