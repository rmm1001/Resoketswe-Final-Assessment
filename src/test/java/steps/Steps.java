
package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.*;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pages.Registration;
import pages.Login;
import pages.Admin;
import utils.DriverFactory;

import java.util.UUID;

public class Steps {

    private WebDriver driver;

    private Registration registrationPage;
    private Login loginPage;
    private Admin adminPage;

    // Newly registered user details
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userPassword;

    // Registration confirmation
    private String registrationMessage;

    // Administrator credentials
    private final String adminEmail = "mashabela96@gmail.com";
    private final String adminPassword = "Test@1234";

    // Initialize one browser and all page objects
    private void initializePages() {

        if (driver == null) {
            driver = DriverFactory.getDriver();

            registrationPage = new Registration(driver);
            loginPage = new Login(driver);
            adminPage = new Admin(driver);
        }
    }

    // STEP 1: Open registration page
    @Given("I am on the registration page")
    public void iAmOnRegistrationPage() {

        initializePages();

        driver.get("https://ndosisimplifiedautomation.vercel.app/#practice");

        // Wait for the practice page to load
        new org.openqa.selenium.support.ui.WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(30)
        ).until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .urlContains("#practice")
        );

        // Wait for the page body
        new org.openqa.selenium.support.ui.WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(30)
        ).until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfElementLocated(
                                org.openqa.selenium.By.tagName("body")
                        )
        );

        System.out.println("Registration page opened.");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    // STEP 2: Register a new user
    @When("I register a new user selecting the Do Not Use {int} group")
    public void registerNewUser(Integer year) {

        Assert.assertEquals(
                year.intValue(),
                2026,
                "Unexpected group year."
        );

        String unique = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        firstName = "Test";
        lastName = "User";
        userEmail = "testuser" + unique + "@example.com";
        userPassword = "Test@12345";

        System.out.println("\n========== NEW USER ==========");
        System.out.println("First name: " + firstName);
        System.out.println("Last name: " + lastName);
        System.out.println("Email: " + userEmail);
        System.out.println("==============================");

        registrationMessage = registrationPage.registerUser(
                firstName,
                lastName,
                userEmail,
                userPassword
        );
    }

    // STEP 3: Verify registration
    @Then("the user registration should be submitted")
    public void verifyRegistration() {

        Assert.assertNotNull(
                registrationMessage,
                "Registration confirmation was not received."
        );

        Assert.assertTrue(
                registrationMessage.contains(
                        "Registration submitted successfully"
                ),
                "Unexpected registration message: " + registrationMessage
        );

        Assert.assertTrue(
                registrationMessage.contains(
                        "pending admin approval"
                ),
                "Account was not confirmed as pending admin approval."
        );

        System.out.println("Registration successfully verified.");
    }

    // STEP 4: Admin login
    @Given("an existing admin logs into the application")
    public void adminLogsIn() {

        initializePages();

        System.out.println("Logging in with admin account: " + adminEmail);

        loginPage.login(adminEmail, adminPassword);

        System.out.println("Admin login submitted.");
    }

    // STEP 5: Open Users
    @When("the admin opens user management")
    public void adminOpensUserManagement() {

        adminPage.openUsers();

        System.out.println("Admin opened Users.");
    }

    // STEP 6: Approve new user
    @When("the admin approves the new user")
    public void adminApprovesUser() {

        Assert.assertNotNull(
                userEmail,
                "New user email is missing."
        );

        adminPage.approveUser(userEmail);

        System.out.println("Approved user: " + userEmail);
    }

    // STEP 7: Promote user
    @When("the admin promotes the new user to Admin")
    public void adminPromotesUser() {

        adminPage.promoteUserToAdmin(userEmail);

    }

    // STEP 8: Logout admin
    @Given("I have logged out of the admin account")
    public void logoutAdmin() {

        adminPage.logout();

        System.out.println("Administrator logged out.");
    }

    // STEP 9: Login as new user
    @When("I log in with the newly registered user")
    public void loginNewUser() {

        Assert.assertNotNull(
                userEmail,
                "New user email is missing."
        );

        Assert.assertNotNull(
                userPassword,
                "New user password is missing."
        );

        loginPage.openLogin();

        loginPage.login(
                userEmail,
                userPassword
        );

        System.out.println("New user login: " + userEmail);
    }

    // STEP 10: Verify Admin role
    @Then("I should have the {string} role")
    public void verifyRole(String expectedRole) {

        String actualPageText = adminPage.getPageText();

        Assert.assertTrue(
                actualPageText.toLowerCase().contains(
                        expectedRole.toLowerCase()
                ),
                "Expected role was not displayed: " + expectedRole
        );

        System.out.println("Verified role: " + expectedRole);
    }

    // Close browser after the scenario finishes
    @After
    public void closeBrowser() {

        DriverFactory.quitDriver();

        driver = null;

        System.out.println("Browser session closed.");
    }
}
