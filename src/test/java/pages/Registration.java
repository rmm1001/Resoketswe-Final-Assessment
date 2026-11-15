package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Registration {

    public class RegistrationPage {

        private WebDriver driver;
        private WebDriverWait wait;

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

        private By groupDropdown = By.tagName("select");

        private By createAccountButton =
                By.xpath("//button[normalize-space()='Create Account']");

        public RegistrationPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(
                    driver, Duration.ofSeconds(15));
        }

        public void enterFirstName(String value) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstName))
                    .sendKeys(value);
        }

        public void enterLastName(String value) {
            driver.findElement(lastName).sendKeys(value);
        }

        public void enterEmail(String value) {
            driver.findElement(email).sendKeys(value);
        }

        public void enterPassword(String value) {
            driver.findElement(password).sendKeys(value);
        }

        public void enterConfirmPassword(String value) {
            driver.findElement(confirmPassword).sendKeys(value);
        }

        public void leaveGroupUnselected() {
            WebElement group = driver.findElement(groupDropdown);
            // Keep the default "-- Select Your Group --" option.
        }

        public void clickCreateAccount() {
            wait.until(ExpectedConditions.elementToBeClickable(
                    createAccountButton)).click();
        }

        public void registerUser(String first, String last,
                                 String emailAddress, String pass) {

            enterFirstName(first);
            enterLastName(last);
            enterEmail(emailAddress);
            enterPassword(pass);
            enterConfirmPassword(pass);
            leaveGroupUnselected();
            clickCreateAccount();
        }
    }

}
