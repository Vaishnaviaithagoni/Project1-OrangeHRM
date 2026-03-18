package OrangeHRM;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Scenario1 {
	String fPath = "C:\\Users\\91891\\Downloads\\LoginData_OHRM.xlsx";
	FileInputStream fis;
	XSSFWorkbook wb;
	XSSFSheet sheet;
	WebDriver driver;

	ExtentReports report;
	ExtentTest test;

	@BeforeClass
	public void beforeTest() throws IOException {

		// ---------------------------
		// Extent Report Setup
		// ---------------------------
		ExtentSparkReporter spark = new ExtentSparkReporter("./Reports/OrangeHRM_Login_Report.html");
		report = new ExtentReports();
		report.attachReporter(spark);

		// Read Excel
		fis = new FileInputStream(new File(fPath));
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet("Login Data");

		// Launch Chrome
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}

	// Screenshot Function
	public String captureScreenshot(String testName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);

		String timestamp = String.valueOf(new Date(0, 0, 0).getTime());
		String path = "./Screenshots/" + testName + "_" + timestamp + ".png";

		FileUtils.copyFile(src, new File(path));
		return path;
	}

	@DataProvider(name = "loginData")
	public Object[][] getData() {

		int rows = sheet.getPhysicalNumberOfRows();
		int cols = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] data = new Object[rows - 1][cols];

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i - 1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
			}
		}
		return data;
	}

	@Test(dataProvider = "loginData")
	public void loginTest(String username, String password) throws Exception {

		test = report.createTest("Login Test - Username: " + username);

		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys(username);

		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);

		driver.findElement(By.xpath("//button[@type='submit']")).click();

		Thread.sleep(2000);

		try {
			WebElement dashboard = driver.findElement(By.xpath("//h6[text()='Dashboard']"));
			if (dashboard.isDisplayed()) {

				test.pass("Login Successful");
				test.pass("Dashboard visible");

				// Screenshot for success
				String path = captureScreenshot("LoginSuccess_" + username);
				test.addScreenCaptureFromPath(path);

				// Logout
				driver.findElement(By.cssSelector(".oxd-userdropdown")).click();
				driver.findElement(By.xpath("//a[text()='Logout']")).click();
			}
		} catch (Exception e) {

			WebElement error = driver.findElement(By.xpath("//p[contains(@class,'oxd-alert-content-text')]"));
			test.fail("Login Failed: " + error.getText());

			// Screenshot for failure
			String path = captureScreenshot("LoginFail_" + username);
			test.addScreenCaptureFromPath(path);
		}
	}

	@AfterTest
	public void afterTest() throws IOException {
		wb.close();
		fis.close();

		report.flush(); // Save extent report

		driver.quit();
	}
}