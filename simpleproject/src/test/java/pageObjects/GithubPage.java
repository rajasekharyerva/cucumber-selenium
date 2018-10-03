package pageObjects;

import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import org.openqa.selenium.support.PageFactory;

public class GithubPage {
    public GithubPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Elements
    @FindBy(xpath = "//a[text()='Sign in']")
    private WebElement signInLink;

    @FindBy(xpath = "//*[@id= 'login_field']")
    private WebElement usernameInput;

    @FindBy(xpath = "//*[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@value='Sign in']")
    private WebElement signInButton;

    //Methods
    public void clickSignInLink() {
        signInLink.click();
    }

    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}
