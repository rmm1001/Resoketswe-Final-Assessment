User Registration and Admin Verification
Project Description

This project uses Java, Selenium WebDriver and Cucumber to automate user registration, admin approval, admin role assignment and login verification.

Technologies Used
Java
Selenium WebDriver
Cucumber
JUnit
Maven
IntelliJ IDEA
GitHub
Test Scenarios
Register a new user with unique details.
Select the group "Do Not Use".
Approve the new user using an admin account.
Assign the new user an Admin role.
Log in using the new user's credentials.
Verify successful login.
Verify that the user has an Admin role.
Project Structure
text
Project/
├── pages/
├── steps/
├── utils/
├── reports/
├── screenshots/
├── README.md
└── pom.xml

How to Run the Tests

Run the following Maven command:

bash
mvn clean test

Reports

Test execution reports are saved in the reports folder.

Screenshots

Screenshots of the automated test scenarios are saved in the screenshots folder.

CI/CD Pipeline
Every Git push triggers the pipeline.
The pipeline runs daily at midnight (00:00).
Automated tests are executed through the pipeline.

Author
Your Name
Resoketswe Mashabela
Project Status
In Progress