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

public class GitHub_Login_ExcelHandler {
	
	@Test
	public void GitHub_Login(){
		try {
			FileInputStream githubFile = new FileInputStream("GitHub.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(githubFile);
			XSSFSheet sheet = workbook.getSheet("GitHubLogin");
			int rows = sheet.getLastRowNum();
			for(int i = 1;i<=rows;i++){
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(username+" , "+password+" , "+criteria);
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				//navigate to github site
				driver.get("https://github.com/login");
				//Enter username
				driver.findElement(By.id("login_field")).sendKeys(username);
				//enter password
				driver.findElement(By.id("password")).sendKeys(password);
				//click signIn
				driver.findElement(By.xpath("//*[@id='login']/form/div[4]/input[3]")).click();Thread.sleep(4000);
				String currentURL = driver.getCurrentUrl();
				System.out.println("CurrentURL : "+currentURL);
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(currentURL.equals("https://github.com/")){
					//click profile
					driver.findElement(By.xpath("//*[@id='user-links']/li[3]/a/img")).click();
					//click signOut
					driver.findElement(By.xpath("//*[@id='user-links']/li[3]/div/div/form/button")).click();
					if(criteria.equals("valid")){
						cell.setCellValue("Success : Successfully SignOut");
						System.out.println("Success : Successfully SignOut");
					}
					else{
						cell.setCellValue("Failure : Successfully SignOut but criteria invalid");
						System.out.println("Failure : Successfully SignOut but criteria invalid");
					}
				}
				else{
					cell.setCellValue("Failure : Wrong Credentials");
					System.out.println("Failure : Wrong Credentials");
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