package managers;

import org.openqa.selenium.WebDriver;
import pageObjects.GithubPage;

public class PageObjectManager {
    private WebDriver driver;
    private GithubPage githubPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public GithubPage getGithubPage(){
        return (githubPage == null) ? githubPage = new GithubPage(driver) : githubPage;
    }
}
