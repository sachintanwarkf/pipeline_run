//package runners;
//
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import net.masterthought.cucumber.Configuration;
//import net.masterthought.cucumber.ReportBuilder;
//import org.testng.annotations.AfterSuite;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class BaseRunner extends AbstractTestNGCucumberTests {
//
//    private final String BUILD_NUMBER = "1.0";
//
//    abstract String getRunnerName();
//
//    @AfterSuite
//    public void generateReport() {
//        File reportOutputDirectory = new File("target/reports"); //
//        List<String> jsonFiles = new ArrayList<String>();
//
//        jsonFiles.add("target/cucumber-reports/" + getRunnerName() + "_report.json");
//
//        Configuration configuration = new Configuration(reportOutputDirectory, getRunnerName());
//
//        configuration.setBuildNumber(BUILD_NUMBER);
//
//        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
//        reportBuilder.generateReports();
//    }
//}