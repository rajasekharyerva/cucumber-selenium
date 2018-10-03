package stepDefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cucumber.listener.Reporter;
import com.google.common.io.Files;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javax.imageio.ImageIO;
import java.io.File;
import dataProviders.ConfigFileReader;
import managers.FileReaderManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import pageObjects.GithubPage;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import testDataTypes.User;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Steps{
    WebDriver webDriver = null;
    GithubPage githubPage = null;
    ConfigFileReader configFileReader;
    ExtentHtmlReporter htmlReporter;
    ExtentTest test;
    ExtentReports extent;

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
        System.out.println(userName);
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
    public void beforeScenario(Scenario scenario) {
        System.out.println("This will run before the Scenario");
        System.out.println(scenario.getName());

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "//" + "src//test//resources//testDataResources//testData.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row currentRow = sheet.getRow(i);
            for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
                Cell currentCell = currentRow.getCell(j);
                switch (currentCell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(currentCell.getStringCellValue() + "\t");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(currentCell.getNumericCellValue() + "\t");
                        break;
                }
            }
            System.out.println("\n");
        }
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            System.out.println("..................");
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            try {
                //This takes a screenshot from the driver at save it to the specified location
                File sourcePath = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

                //Building up the destination path for the screenshot to save
                //Also make sure to create a folder 'screenshots' with in the cucumber-report folder
                String folder = System.getProperty("user.dir")+"/target/cucumber/";
                File newFolder = new File(folder);
                newFolder.mkdir();

                File destinationPath = new File(folder + screenshotName + ".png");
                System.out.println(destinationPath);
                //Copy taken screenshot from source location to destination location
                Files.copy(sourcePath, destinationPath);

                //This attach the specified screenshot to the test
                Reporter.addScreenCaptureFromPath(destinationPath.toString());
            } catch (IOException e) {
            }
        }
    }

    @After(order = 0)
    public void AfterSteps() {
        webDriver.quit();
    }


}