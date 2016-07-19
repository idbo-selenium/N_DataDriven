package com.Data.DataDrivenExcel.GitHub;

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

public class GitHub_ExcelHandler {
	
	public WebDriver driver;
	@Test
	public void ExcelHandler(){
		try {
			FileInputStream file = new FileInputStream("GitHub.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("GitHubLogin");
			int githubsheetRows = sheet.getLastRowNum();
			System.out.println("githubsheetRows : "+githubsheetRows);
			for(int i = 1;i<=githubsheetRows;i++){
				//System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				String githubUsername = String.valueOf(sheet.getRow(i).getCell(0));
				String githubPassword = String.valueOf(sheet.getRow(i).getCell(1));
				String githubCriteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println("githubUsername : "+githubUsername +" , githubPassword : "+githubPassword);				
				
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				
				driver.get("https://github.com/login");				
				driver.findElement(By.id("login_field")).sendKeys(githubUsername);Thread.sleep(4000);
				//String sample = driver.findElement(By.id("login_field")).getText();System.out.println("Sample : "+sample);
				driver.findElement(By.id("password")).sendKeys(githubPassword);
				driver.findElement(By.xpath("//*[@id='login']/form/div[4]/input[3]")).click();
				Thread.sleep(3000);
				
				Cell cell =  sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(driver.getCurrentUrl().equals("https://github.com/")){
					driver.findElement(By.xpath("//*[@id='user-links']/li[3]/a/img")).click();
					driver.findElement(By.xpath("//*[@id='user-links']/li[3]/div/div/form/button")).click();
					if(githubCriteria.equals("valid")){
						cell.setCellValue("Success : Successfully SignedOut");
						System.out.println("Success : Successfully SignedOut");
					}
					else{
						cell.setCellValue("Failure: successfully SignedOut bt criteria 'invalid'");
						System.out.println("Failure: successfully SignedOut bt criteria 'invalid'");
					}
				}
				else{
					cell.setCellValue("Failure: Wrong credentials");
					System.out.println("Failure: Wrong credentials");
				}

				FileOutputStream fileOut = new FileOutputStream("GitHub.xlsx");
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