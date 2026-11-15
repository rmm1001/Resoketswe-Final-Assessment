package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.CompoundName;
import java.time.Duration;

import static   utils.DriverFactory.getDriver;
import static utils.DriverFactory.getDriver;

public class Steps {

    @Given("I open the application home page")
    public void openHomePage() {
        WebDriver driver = getDriver();
        driver.get("https://ndosisimplifiedautomation.vercel.app/#overview");
    }

    @When("I click on Login")
    public void clickLogin() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[normalize-space()='Login']")
                )
        );

        loginButton.click();
    }

    @When("I click on Sign up here")
    public void clickSignUp() {

        WebDriver driver = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement signUp = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(translate(normalize-space(.), " +
                                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                                "'abcdefghijklmnopqrstuvwxyz'), 'sign up')]")
                )
        );

        signUp.click();
    }

    @Given("I am on home page")
    public void i_am_on_home_page() {
    }

    @Given("I click login button")
    public void i_click_login_button() {
    }

    @Given("I enter username mashabela96@gmail.com")
    public void i_enter_username_mashabela96_gmail_com() {

    }
    @Given("I enter password Test@1234")
    public void i_enter_password_test() {
    }

    @When("I click submit button")
    public void i_click_submit_button() {

    }
    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {

    }
    @Given("I am on the home page")
    public void i_am_on_the_home_page() {

    }
    @Given("I click on the registration link")
    public void i_click_on_the_registration_link() {

    }
    @Given("I fill in the registration form with valid details")
    public void i_fill_in_the_registration_form_with_valid_details() {

    }
    @When("I submit the registration form")
    public void i_submit_the_registration_form() {

    }
    @Then("I should see a confirmation message indicating successful registration")
    public void i_should_see_a_confirmation_message_indicating_successful_registration() {

    }
    @Given("I navigate to the registration page")
    public void i_navigate_to_the_registration_page() {

    }
    @When("I register a new user with unique details")
    public void i_register_a_new_user_with_unique_details() {

    }
    @When("I select the {string} group")
    public void i_select_the_group(String string) {

    }
    @Then("the user registration should be successful")
    public void the_user_registration_should_be_successful() {

    }

    @Given("an existing admin logs into the application")
    public void an_existing_admin_logs_into_the_application() {

    }
    @When("the admin navigates to user management")
    public void the_admin_navigates_to_user_management() {

    }
    @When("the admin searches for the newly registered user")
    public void the_admin_searches_for_the_newly_registered_user() {

    }
    @When("the admin approves the newly registered user")
    public void the_admin_approves_the_newly_registered_user() {

    }
    @Then("the user account status should be {string}")
    public void the_user_account_status_should_be(String string) {

    }
    @When("the admin updates the newly registered user's role")
    public void the_admin_updates_the_newly_registered_user_s_role() {

    }
    @When("the admin assigns the {string} role")
    public void the_admin_assigns_the_role(String string) {

    }
    @When("the admin saves the user changes")
    public void the_admin_saves_the_user_changes() {

    }
    @Then("the newly registered user should have the {string} role")
    public void the_newly_registered_user_should_have_the_role(String string) {

    }
    @Then("I should be successfully logged in")
    public void i_should_be_successfully_logged_in() {

    }
    @Then("I should see the newly registered user's details")
    public void i_should_see_the_newly_registered_user_s_details() {

    }

    @Given("I have logged out of the admin account")
    public void i_have_logged_out_of_the_admin_account() {

    }
    @When("I log in using the newly registered user credentials")
    public void i_log_in_using_the_newly_registered_user_credentials() {

    }

    @And("I should have the {string} role")
    public void iShouldHaveTheRole(String arg0) {

    }

    @And("I should have access to administrator functionality")
    public void iShouldHaveAccessToAdministratorFunctionality() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
