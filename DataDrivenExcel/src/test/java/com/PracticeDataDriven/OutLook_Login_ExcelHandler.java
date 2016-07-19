package com.PracticeDataDriven;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OutLook_Login_ExcelHandler {

	@Test
	public void ExcelHandler(){
		try {
			FileInputStream outLookFile = new FileInputStream("OutLookLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(outLookFile);
			XSSFSheet sheet = workbook.getSheet("OutLookLogin");
			
			int rows = sheet.getLastRowNum();
			System.out.println("Rows : "+rows);
			
			for(int i=1;i<=rows;i++){
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(username+" , "+password+" , "+criteria);
				
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				driver.get("https://email.i-dbo.com/owa/#path=/mail");
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				
				driver.findElement(By.id("username")).sendKeys(username);
				driver.findElement(By.id("password")).sendKeys(password);
				driver.findElement(By.xpath("//*[@id='lgnDiv']/div[9]/div/span")).click();
				Thread.sleep(4000);
				String currentURL = driver.getCurrentUrl();
				System.out.println("CurrentURL : "+currentURL);
				if(currentURL.equals("https://email.i-dbo.com/owa/#path=/mail")){
					if(criteria.equals("valid")){
						cell.setCellValue("Success : Successfully SignIn");
						System.out.println("Success : Successfully SignIn");
					}
					else{
						cell.setCellValue("Failure : Successfully SignIn but criteria invalid");
						System.out.println("Failure : Successfully SignIn but criteria invalid");
					}
				}
				else{
					cell.setCellValue("Failure : Invalid Credentials");
					System.out.println("Failure : Invalid Credentials");
				}
				FileOutputStream fileOut = new FileOutputStream("OutLookLogin.xlsx");
				workbook.write(fileOut);
				fileOut.close();
				driver.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}