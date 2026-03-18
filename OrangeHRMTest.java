package OrangeHRM_TestCase2;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class OrangeHRMTest<searchByUserStatus> {
	
	 WebDriver driver;
	    LoginPage1 loginPage;
	    AdminPage2 admin;
	   //AdminPage adminPage;

	    @BeforeClass
	    public void setup() {
	    	 driver = new ChromeDriver();   // ✅ Assign to class driver

	    	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    	    driver.manage().window().maximize();

	    	   
		
	        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

	        loginPage = new LoginPage1(driver);
	        admin=new AdminPage2(driver);
	       // adminPage = new AdminPage(driver);  // ✅ FIXED
	    }

	    @Test(priority = 1)
	    public void loginTestcase() {
	        loginPage.loginValidUser("Admin", "admin123");
	        System.out.println("✅ Logged in successfully");
	    }

	   @Test(priority = 2)
	    public void verifyMenuAndClickAdmin() {
	        admin.verifyLeftMenuAndClickAdmin();
	    }

	   @Test(priority = 3)
	    public void searchByUsernameTest() throws InterruptedException {
	        admin.searchByUsername("Admin");
	    }

	    @Test(priority = 4)
	    public void searchByUserRoleTest() throws InterruptedException {
	        admin.searchByUserRole("Admin");
	    }

	    @Test(priority = 5)
	    public void searchByStatusTest() throws InterruptedException {
	        admin.searchByUserStatus("Enabled");
	        admin.searchButton.click();
	    }
	    @AfterClass
	    public void logout() {
	        driver.quit();
	    }

	    

}