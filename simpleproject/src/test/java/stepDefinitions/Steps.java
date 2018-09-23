package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataProviders.ConfigFileReader;
import managers.FileReaderManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pageObjects.GithubPage;
import testDataTypes.User;

import java.util.concurrent.TimeUnit;

public class Steps {
    WebDriver webDriver = null;
    GithubPage githubPage = null;
    ConfigFileReader configFileReader;

    @Given("^Navigate to Application URL$")
    public void navigate_to_Application_URL() throws Throwable {
        String userDir = System.getProperty("user.dir");
        configFileReader = new ConfigFileReader();
        System.setProperty("webdriver.chrome.driver", userDir+configFileReader.getDriverPath()+"/chromedriver.exe");
        webDriver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.navigate().to("https://github.com/");
    }

    @When("^Click Sign In$")
    public void click_Sign_In() throws Throwable {
        githubPage = new GithubPage(webDriver);
        System.out.println("Click Sign In");
        githubPage.clickSignInLink();
    }

    @When("^Enters \"([^\"]*)\" in Username$")
    public void enters_in_Username(String userName) throws Throwable {
        User user = FileReaderManager.getInstance().getJsonReader().getUserByName(userName);
        System.out.println("Enters Username");
        System.out.println(user.password);
        githubPage.enterUsername("xxxxxxx@gmail.com");
    }

    @When("^Enters Password$")
    public void enters_Password() throws Throwable {
        System.out.println("Enters Password");
        githubPage.enterPassword("***********");
    }


    @When("^Clicks SignIn$")
    public void clicks_SignIn() throws Throwable {
        System.out.println("Clicks SignIn");
        githubPage.clickSignInButton();
    }

    @Then("^Should land on HomePage$")
    public void should_land_on_HomePage() throws Throwable {
        System.out.println("Should land on HomePage");
        //Verification & close browser
        webDriver.quit();
        Assert.fail("Verification failed");
    }

    @Before
    public void beforeScenario(Scenario scenario){
        System.out.println("This will run before the Scenario");
        System.out.println(scenario);
    }

    @After
    public void afterScenario(Scenario scenario){
        System.out.println("This will run after the Scenario");
        System.out.println(scenario.getName());
    }
}