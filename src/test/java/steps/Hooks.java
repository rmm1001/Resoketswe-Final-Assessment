
package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;
import utils.Screenshots;

import java.time.Duration;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setUp() {

        driver = DriverFactory.getDriver();

        driver.manage().window().maximize();

        driver.get("https://ndosisimplifiedautomation.vercel.app/#overview");

        // Wait for the page to finish loading
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete")
                );

        // Print page information in GitHub Actions logs
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
    }

    @After
    public void takeScreenshotAfterScenario(Scenario scenario) {

        if (driver != null) {

            try {

                byte[] screenshot =
                        ((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.BYTES);

                scenario.attach(
                        screenshot,
                        "image/png",
                        scenario.getName()
                );

                Screenshots.captureScreenshot(
                        driver,
                        scenario.getName().replaceAll(
                                "[^a-zA-Z0-9]",
                                "_"
                        ) + "_" + System.currentTimeMillis()
                );

                System.out.println(
                        "Screenshot captured: " + scenario.getName()
                );

            } catch (Exception e) {

                System.out.println(
                        "Screenshot error: " + e.getMessage()
                );

            } finally {

                driver.quit();
                driver = null;
            }
        }
    }
}
