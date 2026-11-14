package utils;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Screenshots {

        public static void captureScreenshot(WebDriver driver, String screenshotName) {

            try {
                // Create screenshots folder if it does not exist
                Path folder = Paths.get("screenshots");
                Files.createDirectories(folder);

                // Capture screenshot
                File source = ((TakesScreenshot) driver)
                        .getScreenshotAs(org.openqa.selenium.OutputType.FILE);

                // Save screenshot
                Path destination = folder.resolve(screenshotName + ".png");

                Files.copy(source.toPath(), destination);

                System.out.println("Screenshot saved: " + destination);

            } catch (IOException e) {
                System.out.println("Unable to save screenshot: " + e.getMessage());
            }
        }
    }

