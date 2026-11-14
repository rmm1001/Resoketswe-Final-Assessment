package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources",
        glue = {"steps"},
        plugin = {
                "pretty",
                "html:reports/cucumber-report.html",
                "json:reports/cucumber-report.json",
                "junit:reports/cucumber-report.xml"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
