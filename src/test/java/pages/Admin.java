
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Admin {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardHeading = By.xpath(
            "//*[contains(normalize-space(.),'Admin Dashboard')]"
    );

    private final By usersMenu = By.xpath(
            "//*[self::a or self::button or self::div][normalize-space(.)='Users']"
    );

    private final By approvalsMenu = By.xpath(
            "//*[self::a or self::button or self::div][normalize-space(.)='Approvals']"
    );

    private final By logoutButton = By.xpath(
            "//*[self::a or self::button][contains(" +
                    "translate(normalize-space(.)," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz'),'logout')]"
    );

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

    public void openUsers() {
        wait.until(
                ExpectedConditions.elementToBeClickable(usersMenu)
        ).click();
    }

    public void openApprovals() {
        wait.until(
                ExpectedConditions.elementToBeClickable(approvalsMenu)
        ).click();
    }

    private WebElement findUserContainer(String userEmail) {

        String xpath = "//*[contains(normalize-space(.),'" +
                userEmail + "')]";

        By userLocator = By.xpath(xpath);

        WebElement userElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        userLocator
                )
        );

        List<WebElement> rows = userElement.findElements(
                By.xpath("./ancestor::tr[1]")
        );

        if (!rows.isEmpty()) {
            return rows.get(0);
        }

        List<WebElement> containers = userElement.findElements(
                By.xpath("./ancestor::div[.//*[contains(text(),'" +
                        userEmail + "')]][1]")
        );

        if (!containers.isEmpty()) {
            return containers.get(0);
        }

        return userElement;
    }

    public void approveUser(String userEmail) {

        openApprovals();

        WebElement userContainer = findUserContainer(userEmail);

        WebElement approveButton = userContainer.findElement(
                By.xpath(
                        ".//button[contains(translate(normalize-space(.)," +
                                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                                "'abcdefghijklmnopqrstuvwxyz'),'approve')]"
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(approveButton)
        ).click();
    }

    public void promoteUserToAdmin(String userEmail) {

        openUsers();

        WebElement userContainer = findUserContainer(userEmail);

        List<WebElement> dropdowns = userContainer.findElements(
                By.tagName("select")
        );

        if (dropdowns.isEmpty()) {
            throw new IllegalStateException(
                    "No role dropdown found for user: " + userEmail
            );
        }

        Select roleSelect = new Select(dropdowns.get(0));
        roleSelect.selectByVisibleText("Admin");
    }

    public void logout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(logoutButton)
        ).click();
    }

    public boolean isAdminRoleDisplayed() {

        By adminRole = By.xpath(
                "//*[normalize-space(.)='Admin' or " +
                        "contains(normalize-space(.),'Role: Admin')]"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(adminRole)
        ).isDisplayed();
    }

    public String getPageText() {

        return driver.findElement(
                By.tagName("body")
        ).getText();
    }
}
