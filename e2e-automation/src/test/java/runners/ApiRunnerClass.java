package runners;

import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)  // This tells JUnit to run Cucumber tests
@CucumberOptions(
        strict = true,
        features = "src/test/resources/features/API/api.feature",  // Path to your feature files
        glue = "stepdefinitions.api",  // Package where step definitions are located
        plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json"}  // Cucumber plugins for reporting
)
public class ApiRunnerClass {
}
