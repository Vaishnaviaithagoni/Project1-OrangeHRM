package OrangeHRM_TestCase2;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AdminPage2 {
	WebDriver driver;
	 @FindBy(xpath = "//a[@class='oxd-main-menu-item']")
	    List<WebElement> menuList;

	    @FindBy(xpath = "//span[text()='Admin']")
	    WebElement adminMenu;

	    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input")
	    WebElement usernameSearchBox;

	    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div/div[1]")
	    WebElement userRoleDropdown;

	    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div[1]/div[1]")
	    WebElement userStatusDropdown;

	    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input")
	    WebElement searchButton;

	  //  @FindBy(xpath = "//button[normalize-space()='Reset']")
	    //WebElement resetButton;

	    @FindBy(xpath = "//div[@class='oxd-table-body']/div")
	    List<WebElement> resultRows;
	    
   

    public AdminPage2(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    // Print and click Admin from the menu
    public void verifyLeftMenuAndClickAdmin() {
        int count = menuList.size();
        System.out.println("Total menu options: " + count);
        for (WebElement option : menuList) {
            System.out.println(option.getText());
        }
        adminMenu.click();
    }


    public void searchByUsername(String username) throws InterruptedException {
        usernameSearchBox.clear();
        usernameSearchBox.sendKeys(username);
        searchButton.click();
        Thread.sleep(2000);
        System.out.println("Records found (by username): " + resultRows.size());
    }

    public void searchByUserRole(String role) throws InterruptedException {
        // Handle custom dropdown, not <select>
        userRoleDropdown.click();
        Thread.sleep(1000);
        WebElement option = driver.findElement(By.xpath("//div[@role='option']//span[text()='" + role + "']"));
        option.click();

        searchButton.click();
        Thread.sleep(2000);
        System.out.println("Records found (by role): " + resultRows.size());
    }

    public void searchByUserStatus(String status) throws InterruptedException {
        userStatusDropdown.click();
        Thread.sleep(1000);
        WebElement option = driver.findElement(By.xpath("//div[@role='option']//span[text()='" + status + "']"));
        option.click();

        searchButton.click();
        Thread.sleep(2000);
        System.out.println("Records found (by status): " + resultRows.size());
    }
}
   