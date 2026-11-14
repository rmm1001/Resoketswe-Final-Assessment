package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reports {

    public static void generateReport(
            int totalTests,
            int passedTests,
            int failedTests) {

        try {
            java.io.File folder = new java.io.File("reports");
            folder.mkdirs();

            FileWriter writer = new FileWriter(
                    "reports/assessment-report.txt");

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            writer.write("USER REGISTRATION ASSESSMENT REPORT\n");
            writer.write("====================================\n\n");

            writer.write("Date: " +
                    LocalDateTime.now().format(formatter) + "\n\n");

            writer.write("PROJECT DETAILS\n");
            writer.write("------------------------------\n");
            writer.write("Language: Java\n");
            writer.write("Automation: Selenium WebDriver\n");
            writer.write("Framework: Cucumber\n");
            writer.write("Build Tool: Maven\n\n");

            writer.write("TEST EXECUTION SUMMARY\n");
            writer.write("------------------------------\n");
            writer.write("Total Tests: " + totalTests + "\n");
            writer.write("Passed Tests: " + passedTests + "\n");
            writer.write("Failed Tests: " + failedTests + "\n\n");

            writer.write("ASSESSMENT TEST SCENARIOS\n");
            writer.write("------------------------------\n");
            writer.write("1. Register a new user\n");
            writer.write("2. Select group Do Not Use\n");
            writer.write("3. Approve the new user\n");
            writer.write("4. Assign Admin role\n");
            writer.write("5. Login using the new user\n");
            writer.write("6. Verify successful login\n");
            writer.write("7. Verify Admin role\n\n");

            writer.write("CONCLUSION\n");
            writer.write("------------------------------\n");

            if (failedTests == 0 && totalTests > 0) {
                writer.write("All tests passed successfully.\n");
            } else {
                writer.write("Some tests failed or remain incomplete.\n");
            }

            writer.close();

            System.out.println("Assessment report generated.");

        } catch (IOException e) {
            System.out.println("Report generation failed: "
                    + e.getMessage());
        }
    }
}
