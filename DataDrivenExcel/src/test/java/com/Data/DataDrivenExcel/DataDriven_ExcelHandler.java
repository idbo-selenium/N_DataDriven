package com.Data.DataDrivenExcel;

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
import org.openqa.selenium.firefox.FirefoxDriver;

public class DataDriven_ExcelHandler {

	public WebDriver driver;
	@Test
	public void DataDriven_ExcelHandle()throws Exception{	
		
		try { 
			FileInputStream file = new FileInputStream("TrelloLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet loginsheet = workbook.getSheet("Trello_Login");
			int loginsheetRows = loginsheet.getLastRowNum();
			System.out.println("loginsheetRows : "+loginsheetRows);
			for(int i=1;i<=loginsheetRows;i++){
				String loginSheetUsername = String.valueOf(loginsheet.getRow(i).getCell(0));
				String loginSheetPassword = String.valueOf(loginsheet.getRow(i).getCell(1));
				String loginSheetCriteria = String.valueOf(loginsheet.getRow(i).getCell(2));
//				System.setProperty("webdriver.chrome.driver","F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
//				driver= new ChromeDriver();
				driver = new FirefoxDriver();
				driver.get("https://trello.com/login");
				driver.findElement(By.xpath("//*[@id='user']")).sendKeys(loginSheetUsername);
				driver.findElement(By.xpath("//*[@id='password']")).sendKeys(loginSheetPassword);
				driver.findElement(By.xpath("//*[@id='login']")).click();
				Thread.sleep(3000);
				System.out.println("loginSheetUsername : "+loginSheetUsername);
				System.out.println("loginSheetPassword : "+loginSheetPassword);
				String currentURL = driver.getCurrentUrl();
				String actualURL = "https://trello.com/";				
				System.out.println("currentURL : "+currentURL);
				Cell cell = loginsheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(currentURL.equals(actualURL)){
					if(loginSheetCriteria.equals("valid")){									
					    cell.setCellValue("Success : Successfully Signedout");
					    System.out.println("Success : Successfully Signedout");		
					}
					else{
						cell.setCellValue("Failure : Successfully Signedout but criteria invalid");
					    System.out.println("Failure : Successfully Signedout but criteria invalid");
					}						
					driver.findElement(By.xpath("//*[@id='header']/div[4]/a[2]/span[2]")).click();
				    Thread.sleep(3000);
				    driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();
				    Thread.sleep(3000);	
				}
				else{
					System.out.println("Failure : Invalid Credentials");
					cell.setCellValue("Failure : Invalid Credentials");
					Thread.sleep(3000);					
				}
				FileOutputStream fileOut = new FileOutputStream("TrelloLogin.xlsx");
			    workbook.write(fileOut);
			    fileOut.close();
			    driver.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}