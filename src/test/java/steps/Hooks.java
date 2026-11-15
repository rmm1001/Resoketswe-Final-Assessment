
package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.Screenshots;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setUp() {

        driver = DriverFactory.getDriver();

        driver.manage().window().maximize();

        driver.get("https://ndosisimplifiedautomation.vercel.app/#overview");
    }

    @After
    public void takeScreenshotAfterScenario(Scenario scenario) {

        if (driver != null) {

            try {
                // Capture screenshot
                byte[] screenshot =
                        ((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.BYTES);

                // Attach screenshot to Cucumber HTML report
                scenario.attach(
                        screenshot,
                        "image/png",
                        scenario.getName()
                );

                // Save screenshot to screenshots folder
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
