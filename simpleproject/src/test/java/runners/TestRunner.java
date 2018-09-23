package runners;


import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import dataProviders.ConfigFileReader;
import managers.FileReaderManager;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;

//@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", //Default {}
        glue = {"stepDefinitions"},
        format = {"pretty", "html:target/cucumber",
                            "json:target/cucumber/Cucumber.json",
                            "junit:target/cucumber/Cucumber.xml",
                            "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber/extent_report.html"},
        //~ is used to skip tags
        //tags = {"@E2ETest,@SmokeTest,@RegressionTest"},
        monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
    }
}
