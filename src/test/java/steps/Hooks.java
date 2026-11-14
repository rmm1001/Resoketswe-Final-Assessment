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

        if (driver != null) {

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            scenario.attach(
                    screenshot,
                    "image/png",
                    scenario.getName()
            );

            Screenshots.captureScreenshot(
                    driver,
                    scenario.getName().replaceAll("[^a-zA-Z0-9]", "_")
                );
            }
        }

    @AfterAll
    public static void generateAssessmentReport() {

        // Replace these example values with
        // actual results from your Cucumber JSON report.
        int totalTests = 7;
        int passedTests = 7;
        int failedTests = 0;

        Reports.generateReport(
                totalTests,
                passedTests,
                failedTests
        );
    }
}

