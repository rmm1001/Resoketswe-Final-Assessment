package steps;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.Reports;
import utils.Screenshots;


public class Hooks {

    WebDriver driver = DriverFactory.getDriver();

    @After
    public void takeScreenshotAfterScenario(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();

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
            }
        }

    }
}