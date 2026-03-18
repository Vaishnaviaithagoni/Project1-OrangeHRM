package OrangeHRM_TestCase2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage1 {
	WebDriver driver;

    @FindBy(xpath = "//input[@placeholder='Username']")
    WebElement enterUserName;

    @FindBy(xpath = "//input[@placeholder='Password']")
    WebElement enterPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement login;

    public LoginPage1(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setUsername(String username) {
        enterUserName.sendKeys(username);
    }

    public void setPassword(String password) {
        enterPassword.sendKeys(password);
    }

    public void clickLogin() {
        login.click();
    }

    public void loginValidUser(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLogin();
    }
}


