package steps;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.ScreenshotUtil;


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

            ScreenshotUtil.captureScreenshot(
                    driver,
                    scenario.getName().replaceAll("[^a-zA-Z0-9]", "_")
                );
            }
        }
    }
