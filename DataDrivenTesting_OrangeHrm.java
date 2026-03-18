package OrangeHrm;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterTest;

public class DataDrivenTesting_OrangeHrm {
	String fpath="J:\\LoginData_OHRM (1).xlsx";
	File file;
	FileInputStream fis;//to write the data we use output to write
	XSSFWorkbook wb;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
  @Test
  public void ReadFromExcel() {
	  row=sheet.getRow(1);
	  cell=row.getCell(0);
	  System.out.println(cell.getStringCellValue());	  
  }
  @Test
	public void readAllData()
	{
		int rows = sheet.getPhysicalNumberOfRows();
		int cells = sheet.getRow(0).getPhysicalNumberOfCells();
		for(int i = 0; i < rows; i++)
		{
			row = sheet.getRow(i);
			for(int j = 0; j < cells; j++)
			{
				cell = row.getCell(j);
				System.out.println(cell.getStringCellValue());
			}
		}
	}
  @BeforeTest
  public void beforeTest() throws IOException {
	  file=new File(fpath);
	  fis=new FileInputStream(file);
	  wb=new XSSFWorkbook(fis);
	  sheet=wb.getSheet("Login Data");
  }

  @AfterTest
  public void afterTest() throws IOException {
	  wb.close();
	  fis.close();
  }

}
