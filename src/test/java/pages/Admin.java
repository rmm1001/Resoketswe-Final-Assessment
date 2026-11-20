
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Admin {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardHeading = By.xpath(
            "//*[contains(normalize-space(.),'Admin Dashboard')]"
    );

    private final By logoutButton = By.xpath(
            "//*[self::a or self::button][contains(" +
                    "translate(normalize-space(.)," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz'),'logout')]"
    );

    // Admin login details
    // Move credentials to environment variables before publishing.
    private final String adminEmail = "mashabela96@gmail.com";

    public Admin(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    public boolean isDashboardDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        dashboardHeading
                )
        ).isDisplayed();
    }

    // ==========================================
    // OPEN USERS PAGE
    // ==========================================


    public void openUsers() {

        System.out.println("\n========== OPENING USER MANAGEMENT ==========");

        WebDriverWait freshWait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL: " + currentUrl);

        if (!currentUrl.contains("#dashboard")) {

            By accountMenu = By.cssSelector("button.user-pill");

            freshWait.until(
                    ExpectedConditions.elementToBeClickable(accountMenu)
            ).click();

            By adminPanelButton = By.xpath(
                    "//button[contains(@class,'nav-dropdown-item') " +
                            "and contains(.,'Admin Panel')]"
            );

            freshWait.until(
                    ExpectedConditions.elementToBeClickable(adminPanelButton)
            ).click();

            freshWait.until(
                    ExpectedConditions.urlContains("#dashboard")
            );
        }

        System.out.println("Admin Dashboard is open.");

        By usersButton = By.xpath(
                "//button[contains(.,'Users') " +
                        "and not(contains(.,'Manage Users'))]"
        );

        freshWait.ignoring(StaleElementReferenceException.class)
                .until(
                        ExpectedConditions.elementToBeClickable(usersButton)
                ).click();

        freshWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.tagName("body")
                )
        );

        System.out.println("Users page opened successfully.");

        // Wait until the Users page finishes loading its records
        freshWait.until(driver -> {

            String pageText = driver.findElement(
                    By.tagName("body")
            ).getText();

            return !pageText.contains("Loading users...");
        });

        System.out.println("Users data has finished loading.");

        // Print all buttons
        System.out.println("\n========== USERS PAGE BUTTON HTML ==========");

        for (WebElement element : driver.findElements(By.tagName("button"))) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        // Print all dropdowns
        System.out.println("\n========== USERS PAGE SELECT HTML ==========");

        for (WebElement element : driver.findElements(By.tagName("select"))) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        // Print all inputs
        System.out.println("\n========== USERS PAGE INPUT HTML ==========");

        for (WebElement element : driver.findElements(By.tagName("input"))) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        // Print all table rows
        System.out.println("\n========== USERS PAGE TABLE HTML ==========");

        for (WebElement element : driver.findElements(By.tagName("tr"))) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        // Print other interactive elements
        System.out.println("\n========== USERS PAGE INTERACTIVE ELEMENTS ==========");

        for (WebElement element : driver.findElements(
                By.cssSelector("[role='button'], [role='combobox'], [contenteditable='true']")
        )) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        System.out.println("\n========== END USERS PAGE HTML ==========");
    }

    // ==========================================
    // OPEN APPROVALS PAGE
    // ==========================================

    public void openApprovals() {

        System.out.println("\n========== OPENING APPROVALS ==========");

        WebDriverWait freshWait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        By approvalsButton = By.xpath(
                "//button[contains(.,'Approvals')]"
        );

        WebElement button = freshWait
                .ignoring(StaleElementReferenceException.class)
                .until(
                        ExpectedConditions.elementToBeClickable(
                                approvalsButton
                        )
                );

        button.click();

        freshWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.tagName("body")
                )
        );

        freshWait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(.,'User Approvals')]")
                )
        );

        System.out.println("Approvals page opened successfully.");

        System.out.println(
                driver.findElement(By.tagName("body")).getText()
        );
    }

    // ==========================================
    // APPROVE NEW USER
    // ==========================================

    public void approveUser(String userEmail) {

        System.out.println("\n========== APPROVING NEW USER ==========");

        openApprovals();

        WebDriverWait freshWait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        By searchField = By.cssSelector(
                "input[placeholder='Search by name or email...']"
        );

        WebElement search = freshWait.until(
                ExpectedConditions.visibilityOfElementLocated(searchField)
        );

        search.clear();
        search.sendKeys(userEmail);

        System.out.println("Searching for: " + userEmail);

        By userRow = By.xpath(
                "//tr[.//td[normalize-space()='" + userEmail + "']]"
        );

        WebElement row = freshWait.until(
                ExpectedConditions.visibilityOfElementLocated(userRow)
        );

        System.out.println("New user found: " + userEmail);
        System.out.println("User details: " + row.getText());

        By approveButton = By.xpath(
                ".//button[contains(normalize-space(.),'Approve')]"
        );

        WebElement approve = row.findElement(approveButton);

        freshWait.until(
                ExpectedConditions.elementToBeClickable(approve)
        );

        approve.click();

        System.out.println("Approve button clicked.");

        try {

            WebDriverWait alertWait =
                    new WebDriverWait(driver, Duration.ofSeconds(3));

            alertWait.until(
                    ExpectedConditions.alertIsPresent()
            );

            System.out.println(
                    "Approval alert: " +
                            driver.switchTo().alert().getText()
            );

            driver.switchTo().alert().accept();

        } catch (org.openqa.selenium.TimeoutException e) {

            System.out.println("No confirmation alert displayed.");
        }

        freshWait.until(
                ExpectedConditions.invisibilityOfElementLocated(userRow)
        );

        System.out.println(
                "SUCCESS: User approval verified for " + userEmail
        );
    }

    // ==========================================
    // INSPECT USERS PAGE CONTROLS
    // ==========================================


    public void inspectUsersPage() {

        System.out.println("\n========== USERS PAGE INSPECTION ==========");

        // Wait until the page has finished loading
        wait.until(driver ->
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Allow the application to render its user data
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.tagName("body")
        ));

        System.out.println("\nCURRENT URL:");
        System.out.println(driver.getCurrentUrl());

        System.out.println("\n========== PAGE TEXT ==========");
        System.out.println(
                driver.findElement(By.tagName("body")).getText()
        );

        System.out.println("\n========== BUTTONS ==========");

        for (WebElement element :
                driver.findElements(By.tagName("button"))) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        System.out.println("\n========== INPUTS ==========");

        for (WebElement element :
                driver.findElements(By.tagName("input"))) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        System.out.println("\n========== SELECT ELEMENTS ==========");

        for (WebElement element :
                driver.findElements(By.tagName("select"))) {
            System.out.println(element.getAttribute("outerHTML"));
        }

        System.out.println("\n========== TABLE ROWS ==========");

        for (WebElement element :
                driver.findElements(By.tagName("tr"))) {
            System.out.println(element.getText());
            System.out.println(element.getAttribute("outerHTML"));
        }

        System.out.println("\n========== END USERS PAGE INSPECTION ==========");
    }


    // ==========================================
    // PROMOTE USER TO ADMIN
    // TEMPORARY DIAGNOSTIC VERSION
    // ==========================================

    public void promoteUserToAdmin(String userEmail) {

        System.out.println("\n========== PROMOTING USER TO ADMIN ==========");

        if (userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException(
                    "User email is missing."
            );
        }

        // Open the Users page
        openUsers();

        WebDriverWait freshWait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        System.out.println("\nCurrent URL: " + driver.getCurrentUrl());

        // Wait for the target user's email to appear
        try {

            freshWait.until(driver ->
                    driver.findElement(By.tagName("body"))
                            .getText()
                            .toLowerCase()
                            .contains(userEmail.toLowerCase())
            );

            System.out.println(
                    "SUCCESS: Target user found: " + userEmail
            );

        } catch (org.openqa.selenium.TimeoutException e) {

            System.out.println(
                    "WARNING: User email was not found after loading."
            );

            System.out.println(
                    "Target email: " + userEmail
            );

            System.out.println(
                    "Current page content:"
            );

            System.out.println(
                    driver.findElement(By.tagName("body")).getText()
            );

            throw new AssertionError(
                    "Could not find the approved user on the Users page."
            );
        }

        // Print the complete page content
        System.out.println("\n========== LOADED USERS PAGE ==========");

        System.out.println(
                driver.findElement(By.tagName("body")).getText()
        );

        // Print table rows
        System.out.println("\n========== USER TABLE ROWS ==========");

        for (WebElement row : driver.findElements(By.tagName("tr"))) {

            System.out.println(
                    "ROW: " + row.getText()
            );

            System.out.println(
                    "HTML: " + row.getAttribute("outerHTML")
            );
        }

        // Print buttons
        System.out.println("\n========== BUTTONS ==========");

        for (WebElement button : driver.findElements(By.tagName("button"))) {

            System.out.println(
                    "TEXT: " + button.getText()
                            + " | HTML: "
                            + button.getAttribute("outerHTML")
            );
        }

        // Print dropdowns
        System.out.println("\n========== DROPDOWNS ==========");

        for (WebElement select : driver.findElements(By.tagName("select"))) {

            System.out.println(
                    select.getAttribute("outerHTML")
            );
        }

        // Print inputs
        System.out.println("\n========== INPUTS ==========");

        for (WebElement input : driver.findElements(By.tagName("input"))) {

            System.out.println(
                    input.getAttribute("outerHTML")
            );
        }

        System.out.println(
                "\n========== END PROMOTION INSPECTION =========="
        );
    }

    // ==========================================
    // INSPECT PAGE AFTER LOGOUT
    // ==========================================

    public void inspectPageAfterLogout() {

        System.out.println("\n========== PAGE CONTENT AFTER LOGOUT ==========");

        System.out.println("Current URL: " + driver.getCurrentUrl());

        System.out.println("\n========== PAGE TEXT ==========");

        System.out.println(
                driver.findElement(By.tagName("body")).getText()
        );

        System.out.println("\n========== BUTTON DETAILS ==========");

        for (WebElement button : driver.findElements(By.tagName("button"))) {

            System.out.println(
                    "Button text: " + button.getText()
                            + " | HTML: " + button.getAttribute("outerHTML")
            );
        }

        System.out.println("\n========== INPUT DETAILS ==========");

        for (WebElement input : driver.findElements(By.tagName("input"))) {

            System.out.println(
                    "HTML: " + input.getAttribute("outerHTML")
            );
        }

        System.out.println("\n========== LINKS ==========");

        for (WebElement link : driver.findElements(By.tagName("a"))) {

            System.out.println(
                    "Text: " + link.getText()
                            + " | HREF: " + link.getAttribute("href")
            );
        }

        System.out.println("\n========== END LOGOUT INSPECTION ==========");
    }

// ==========================================
// LOGOUT
// ==========================================

    public void logout() {

        System.out.println("\n========== LOGGING OUT ==========");

        WebDriverWait freshWait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        By backToWebsite = By.xpath(
                "//button[contains(.,'Back to Website')]"
        );

        // Return to the main website if currently in Admin Dashboard
        if (!driver.findElements(backToWebsite).isEmpty()) {

            freshWait.until(
                    ExpectedConditions.elementToBeClickable(backToWebsite)
            ).click();

            System.out.println("Returned to the main website.");
        }

        By accountMenu = By.cssSelector("button.user-pill");

        // Wait for account menu
        WebElement accountButton = freshWait.until(
                ExpectedConditions.elementToBeClickable(accountMenu)
        );

        accountButton.click();

        System.out.println("Account menu opened.");

        // Click Logout
        freshWait.until(
                ExpectedConditions.elementToBeClickable(logoutButton)
        ).click();

        System.out.println("Logout clicked.");

        // Accept JavaScript confirmation if displayed
        try {

            WebDriverWait alertWait =
                    new WebDriverWait(driver, Duration.ofSeconds(5));

            alertWait.until(
                    ExpectedConditions.alertIsPresent()
            );

            String alertMessage = driver.switchTo().alert().getText();

            System.out.println("Logout confirmation: " + alertMessage);

            driver.switchTo().alert().accept();

            System.out.println("Logout confirmation accepted.");

        } catch (org.openqa.selenium.TimeoutException e) {

            System.out.println("No logout confirmation alert appeared.");
        }

        // IMPORTANT:
        // The button remains visible after logout.
        // Its text changes from the logged-in user's name to Login.
        freshWait.until(driver -> {

            try {

                WebElement button = driver.findElement(accountMenu);

                return button.isDisplayed()
                        && button.getText().trim().contains("Login");

            } catch (StaleElementReferenceException |
                     org.openqa.selenium.NoSuchElementException e) {

                return false;
            }
        });

        System.out.println("Administrator successfully logged out.");

        // Inspect the public website after logout
        inspectPageAfterLogout();

        System.out.println("========== END LOGOUT ==========");
    }


    // ==========================================
    // VERIFY ADMIN ROLE
    // ==========================================

    public boolean isAdminRoleDisplayed() {

        By adminRole = By.xpath(
                "//*[normalize-space(.)='Admin' or " +
                        "contains(normalize-space(.),'Role: Admin')]"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(adminRole)
        ).isDisplayed();
    }

    // ==========================================
    // GET PAGE TEXT
    // ==========================================

    public String getPageText() {

        return driver.findElement(
                By.tagName("body")
        ).getText();
    }
}
