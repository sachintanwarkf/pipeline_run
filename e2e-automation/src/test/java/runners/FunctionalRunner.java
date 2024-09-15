package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = "src/test/resources/features/Functional",  // Path to your feature files
        glue = "stepdefinitions.functional",  // Package where step definitions are located
        plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json"}  // Configure Cucumber plugins
)
public class FunctionalRunner{
}