
package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class Steps {

    private WebDriver driver;
    private WebDriverWait wait;

    private String registeredEmail;
    private final String registeredPassword = "Test@1234";

    private final String baseUrl =
            "https://ndosisimplifiedautomation.vercel.app/#overview";

    private void initialize() {
        driver = getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement find(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    private void click(By locator) {
        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    private void type(By locator, String value) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(value);
    }

    // ==========================================
    // HOME PAGE
    // ==========================================

    @Given("I open the application home page")
    public void openHomePage() {
        initialize();
        driver.get(baseUrl);

        wait.until(ExpectedConditions.titleContains(
                "Ndosi Test Automation"
        ));
    }

    @Given("I am on home page")
    public void i_am_on_home_page() {
        initialize();
        driver.get(baseUrl);
    }

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        initialize();
        driver.get(baseUrl);
    }

    // ==========================================
    // LOGIN NAVIGATION
    // ==========================================

    @When("I click on Login")
    public void clickLogin() {
        initialize();

        click(By.xpath(
                "//*[normalize-space()='Login']"
        ));
    }

    @Given("I click login button")
    public void i_click_login_button() {
        clickLogin();
    }

    @When("I click on Sign up here")
    public void clickSignUp() {
        initialize();

        click(By.xpath(
                "//*[contains(translate(normalize-space(.), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                        "'abcdefghijklmnopqrstuvwxyz'), 'sign up')]"
        ));
    }

    @Given("I click on the registration link")
    public void i_click_on_the_registration_link() {
        clickSignUp();
    }

    @Given("I navigate to the registration page")
    public void i_navigate_to_the_registration_page() {
        clickSignUp();
    }

    // ==========================================
    // REGISTRATION
    // ==========================================

    @When("I enter unique user details")
    public void enterUniqueUserDetails() {

        initialize();

        registeredEmail = "testuser"
                + UUID.randomUUID().toString().substring(0, 8)
                + "@gmail.com";

        System.out.println(
                "Generated registration email: " + registeredEmail
        );

        // TODO:
        // Add the actual first name, last name, email,
        // password and confirm password field locators.
        //
        // These locators must be confirmed against
        // the registration page HTML.
    }

    @Given("I fill in the registration form with valid details")
    public void i_fill_in_the_registration_form_with_valid_details() {
        enterUniqueUserDetails();
    }

    @When("I register a new user with unique details")
    public void i_register_a_new_user_with_unique_details() {
        enterUniqueUserDetails();
    }

    @When("I select the {string} group")
    public void i_select_the_group(String groupName) {

        initialize();

        // Replace this locator if the group field
        // uses a custom dropdown rather than a select.
        WebElement dropdown = find(
                By.xpath("//select[contains(@name,'group') or " +
                        "contains(@id,'group')]")
        );

        new Select(dropdown).selectByVisibleText(groupName);
    }

    @When("I submit the registration form")
    public void i_submit_the_registration_form() {

        initialize();

        click(By.xpath(
                "//button[normalize-space()='Register' or " +
                        "normalize-space()='Sign Up' or " +
                        "normalize-space()='Sign up']"
        ));
    }

    @Then("my registration should be successful")
    public void my_registration_should_be_successful() {

        initialize();

        // Replace with the application's actual success message.
        WebElement confirmation = find(
                By.xpath(
                        "//*[contains(translate(normalize-space(.), " +
                                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                                "'abcdefghijklmnopqrstuvwxyz'), 'success')]"
                )
        );

        assertTrue(
                confirmation.isDisplayed(),
                "Registration confirmation was not displayed"
        );
    }

    @Then("the user registration should be successful")
    public void the_user_registration_should_be_successful() {
        my_registration_should_be_successful();
    }

    @Then("I should see a confirmation message indicating successful registration")
    public void i_should_see_a_confirmation_message_indicating_successful_registration() {
        my_registration_should_be_successful();
    }

    // ==========================================
    // LOGIN
    // ==========================================

    @Given("I enter username mashabela96@gmail.com")
    public void i_enter_username_mashabela96_gmail_com() {

        initialize();

        type(
                By.xpath("//input[@type='email' or " +
                        "contains(@name,'email') or " +
                        "contains(@id,'email')]"),
                "mashabela96@gmail.com"
        );
    }

    @Given("I enter password Test@1234")
    public void i_enter_password_test() {

        initialize();

        type(
                By.xpath("//input[@type='password']"),
                registeredPassword
        );
    }

    @When("I click submit button")
    public void i_click_submit_button() {

        initialize();

        click(By.xpath(
                "//button[@type='submit' or " +
                        "normalize-space()='Login' or " +
                        "normalize-space()='Sign in']"
        ));
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {

        initialize();

        assertTrue(
                !driver.getCurrentUrl().contains("login"),
                "Login may not have completed successfully"
        );
    }

    // ==========================================
    // ADMINISTRATION
    // ==========================================

    @Given("an existing admin logs into the application")
    public void an_existing_admin_logs_into_the_application() {

        initialize();

        driver.get(baseUrl);

        // TODO: Enter actual administrator credentials
        // and submit the login form.
        throw new UnsupportedOperationException(
                "Configure administrator login credentials and locators"
        );
    }

    @When("the admin navigates to user management")
    public void the_admin_navigates_to_user_management() {
        throw new UnsupportedOperationException(
                "Configure the actual User Management navigation locator"
        );
    }

    @When("the admin searches for the newly registered user")
    public void the_admin_searches_for_the_newly_registered_user() {

        initialize();

        if (registeredEmail == null) {
            throw new IllegalStateException(
                    "No registered email is available"
            );
        }

        throw new UnsupportedOperationException(
                "Configure the user search field locator"
        );
    }

    @When("the admin approves the newly registered user")
    public void the_admin_approves_the_newly_registered_user() {
        throw new UnsupportedOperationException(
                "Configure the actual approval button locator"
        );
    }

    @Then("the user account status should be {string}")
    public void the_user_account_status_should_be(String status) {
        throw new UnsupportedOperationException(
                "Configure the actual account status verification"
        );
    }

    @When("the admin updates the newly registered user's role")
    public void the_admin_updates_the_newly_registered_user_s_role() {
        throw new UnsupportedOperationException(
                "Configure the actual role editing controls"
        );
    }

    @When("the admin assigns the {string} role")
    public void the_admin_assigns_the_role(String role) {
        throw new UnsupportedOperationException(
                "Configure the actual role dropdown"
        );
    }

    @When("the admin saves the user changes")
    public void the_admin_saves_the_user_changes() {
        throw new UnsupportedOperationException(
                "Configure the actual save button"
        );
    }

    @Then("the newly registered user should have the {string} role")
    public void the_newly_registered_user_should_have_the_role(String role) {
        throw new UnsupportedOperationException(
                "Configure the actual role verification"
        );
    }

    // ==========================================
    // REGISTERED USER LOGIN
    // ==========================================

    @Given("I have logged out of the admin account")
    public void i_have_logged_out_of_the_admin_account() {
        throw new UnsupportedOperationException(
                "Configure the actual logout button"
        );
    }

    @When("I log in using the newly registered user credentials")
    public void i_log_in_using_the_newly_registered_user_credentials() {

        initialize();

        if (registeredEmail == null) {
            throw new IllegalStateException(
                    "Registered user email is missing"
            );
        }

        throw new UnsupportedOperationException(
                "Configure the registered user login form"
        );
    }

    @Then("I should be successfully logged in")
    public void i_should_be_successfully_logged_in() {
        i_should_be_logged_in_successfully();
    }

    @Then("I should see the newly registered user's details")
    public void i_should_see_the_newly_registered_user_s_details() {
        throw new UnsupportedOperationException(
                "Configure the actual user details verification"
        );
    }

    @And("I should have the {string} role")
    public void iShouldHaveTheRole(String role) {
        throw new UnsupportedOperationException(
                "Configure the actual logged-in role verification"
        );
    }

    @And("I should have access to administrator functionality")
    public void iShouldHaveAccessToAdministratorFunctionality() {
        throw new UnsupportedOperationException(
                "Configure administrator functionality verification"
        );
    }
}
