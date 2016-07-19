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

public class Mercury_DataDriven {

	WebDriver driver;
	@Test
	public void DataDriven_Mercury()throws Exception{
		
		try {
			FileInputStream file = new FileInputStream("MercuryLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("MercuryLogin");
			int sheetRows = sheet.getLastRowNum();
			//System.out.println("sheetRows : "+sheetRows);
			for(int i = 1;i<=sheetRows;i++){
				String mercuryUsername = String.valueOf(sheet.getRow(i).getCell(0));
				String mercuryPassword = String.valueOf(sheet.getRow(i).getCell(1));
				String mercuryCriteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(mercuryUsername +" , "+mercuryPassword+" , "+mercuryCriteria);
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.get("http://newtours.demoaut.com/");
				//String mercuryTitle = driver.getTitle();
				//System.out.println("mercuryTitle : "+mercuryTitle);				  
				driver.findElement(By.name("userName")).sendKeys(mercuryUsername);
				driver.findElement(By.name("password")).sendKeys(mercuryPassword);
				driver.findElement(By.name("login")).click();
				Thread.sleep(4000);
				String profileTitle = "Find a Flight: Mercury Tours:";
				String loginTitle = driver.getTitle();
				//System.out.println(profileTitle + ",  "+loginTitle+",");
				Cell cell =sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);				
				if(profileTitle.equals(loginTitle)){
					driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a")).click();
					Thread.sleep(4000);
					if(mercuryCriteria.equals("valid")){
						cell.setCellValue("Success:successfully signout");
						System.out.println("Success:successfully signout");		
					}
					else{
						cell.setCellValue("Failure:successfully signout but criteria invalid");
						System.out.println("Failure:criteria invalid");
					}
				}
				else{
					cell.setCellValue("Failure:invalid details");
					System.out.println("Failure:invalid details");
				}
				FileOutputStream fileOut = new FileOutputStream("MercuryLogin.xlsx");
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