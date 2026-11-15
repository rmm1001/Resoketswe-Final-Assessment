
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

    private WebDriver driver;
    private WebDriverWait wait;

    // Registration form locators
    private By firstName =
            By.cssSelector("input[placeholder='First Name']");

    private By lastName =
            By.cssSelector("input[placeholder='Last Name']");

    private By email =
            By.cssSelector("input[placeholder='Email']");

    private By password =
            By.cssSelector("input[placeholder='Password']");

    private By confirmPassword =
            By.cssSelector("input[placeholder='Confirm Password']");

    private By groupDropdown =
            By.tagName("select");

    private By createAccountButton =
            By.xpath("//*[@id=\"signup-toggle\"]");

    // Constructor
    public Registration(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    // Enter first name
    public void enterFirstName(String value) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        );

        element.sendKeys(value);
    }

    // Enter last name
    public void enterLastName(String value) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastName)
        ).sendKeys(value);
    }

    // Enter email
    public void enterEmail(String value) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(email)
        ).sendKeys(value);
    }

    // Enter password
    public void enterPassword(String value) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        ).sendKeys(value);
    }

    // Confirm password
    public void enterConfirmPassword(String value) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmPassword)
        ).sendKeys(value);
    }

    // Scroll and select group
    public void selectGroup(String groupName) {

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(groupDropdown)
        );

        // Scroll to dropdown
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                dropdown
        );

        // Select group
        Select select = new Select(dropdown);

        select.selectByVisibleText(groupName);
    }

    // Click Create Account
    public void clickCreateAccount() {

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(
                        createAccountButton
                )
        );

        button.click();
    }

    // Complete registration
    public void registerUser(
            String first,
            String last,
            String emailAddress,
            String pass
    ) {

        enterFirstName(first);

        enterLastName(last);

        enterEmail(emailAddress);

        enterPassword(pass);

        enterConfirmPassword(pass);

        selectGroup("Do Not Use");

        clickCreateAccount();
    }

}
