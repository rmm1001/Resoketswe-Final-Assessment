
package steps;

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

    // Registration confirmation message
    private String registrationMessage;

    // Administrator credentials
    private final String adminEmail = System.getenv("ADMIN_EMAIL");
    private final String adminPassword = System.getenv("ADMIN_PASSWORD");

    // Initialize Page Objects
    private void initializePages() {

        driver = DriverFactory.getDriver();

        registrationPage = new Registration(driver);
        loginPage = new Login(driver);
        adminPage = new Admin(driver);
    }

    // STEP 1: Open registration page
    @Given("I am on the registration page")
    public void iAmOnRegistrationPage() {

        initializePages();

        driver.get(
                "https://ndosisimplifiedautomation.vercel.app/#practice"
        );
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

        System.out.println(
                "Registration successfully verified."
        );
    }

    // STEP 4: Admin login
    @Given("an existing admin logs into the application")
    public void adminLogsIn() {

        initializePages();

        Assert.assertNotNull(
                adminEmail,
                "ADMIN_EMAIL environment variable is missing."
        );

        Assert.assertNotNull(
                adminPassword,
                "ADMIN_PASSWORD environment variable is missing."
        );

        loginPage.openLogin();

        loginPage.login(
                adminEmail,
                adminPassword
        );

        Assert.assertTrue(
                adminPage.isDashboardDisplayed(),
                "Admin dashboard was not displayed."
        );

        System.out.println(
                "Administrator login successful."
        );
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

        System.out.println(
                "Approved user: " + userEmail
        );
    }

    // STEP 7: Promote user
    @When("the admin promotes the new user to Admin")
    public void adminPromotesUser() {

        Assert.assertNotNull(
                userEmail,
                "New user email is missing."
        );

        adminPage.promoteUserToAdmin(userEmail);

        System.out.println(
                "Promoted user to Admin: " + userEmail
        );
    }

    // STEP 8: Logout admin
    @Given("I have logged out of the admin account")
    public void logoutAdmin() {

        adminPage.logout();

        System.out.println(
                "Administrator logged out."
        );
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

        System.out.println(
                "New user login: " + userEmail
        );
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

        System.out.println(
                "Verified role: " + expectedRole
        );
    }
}
