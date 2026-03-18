

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Scenario1{
	String fPath = "J:\\LoginData_OHRM.xlsx";
	File file;
	FileInputStream fis;
	FileOutputStream fos;
	XSSFWorkbook wb;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell, cellMsg;
	int index = 1;

	WebDriver driver;

	@Test(dataProvider = "getLoginData")
	public void loginToOHRM(String un, String ps) {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(un);
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(ps);
		driver.findElement(By.xpath("//button[@type='submit']")).submit();
	}

	@AfterMethod
	public void logout() {
		row = sheet.getRow(index);
		cell = row.getCell(2);
		cellMsg = row.getCell(3);
		String msg;
		
		if (driver.getCurrentUrl().contains("dashboard")) {
			msg = driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).getText();
			//cellMsg.setCellValue(msg);
			driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")).click();
			driver.findElement(By.partialLinkText("Log")).click();
			System.out.println("Test case pass");
			
			cell.setCellValue("Pass");
		}
		else
		{
			msg = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
			//cellMsg.setCellValue(msg);
			System.out.println("Invalid credentials\nTest Case fail");
			
			cell.setCellValue("Fail");
		}
		cellMsg.setCellValue(msg);
		index++;
	}

	@DataProvider
	public Object[][] getLoginData() {
		int rows = sheet.getPhysicalNumberOfRows();
		String[][]loginData = new String[rows-1][2];

		/*for(int i = 0; i < rows-1; i++)
		{
			row = sheet.getRow(i+1);
			for(int j = 0; j < 2; j++)
			{
				cell = row.getCell(j);
				loginData[i][j] = cell.getStringCellValue();
			}
		}*/
		for(int i = 0; i < rows-1; i++)
		{
			for(int j = 0; j < 2; j++)
			{
				loginData[i][j] = sheet.getRow(i+1).getCell(j).getStringCellValue();
			}
		}
		return loginData;
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		file = new File(fPath);
		fis = new FileInputStream(file);
		wb = new XSSFWorkbook(fis);	
		sheet = wb.getSheet("Login Data");
		fos = new FileOutputStream(file);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		/*driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));*/

		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	}


	@AfterTest
	public void afterTest() throws IOException {
		wb.write(fos);
		wb.close();
		fis.close();
		
		driver.close();
	}

}
