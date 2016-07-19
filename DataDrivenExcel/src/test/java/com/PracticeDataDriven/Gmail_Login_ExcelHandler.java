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

public class Gmail_Login_ExcelHandler {
	
	@Test
	public void ExcelHandler(){
		try {
			FileInputStream gmailFile = new FileInputStream("GmailLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(gmailFile);
			XSSFSheet sheet = workbook.getSheet("GmailLogin");
			
			int rows = sheet.getLastRowNum();
			System.out.println("rows : "+rows);
			
			for(int i=1;i<=rows;i++){
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(username +" , "+password+" , "+criteria);
				
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				driver.navigate().to("https://accounts.google.com/ServiceLogin?sacu=1&scc=1&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&osid=1&service=mail&ss=1&ltmpl=default&rm=false#identifier");
				//enter username
				driver.findElement(By.id("Email")).sendKeys(username);Thread.sleep(2000);
				//next button click
				driver.findElement(By.id("next")).click();Thread.sleep(2000);
				String nextButtonClickURL = driver.getCurrentUrl();
				System.out.println("nextButtonClickURL : "+nextButtonClickURL);
				String passwordPageURL = "https://accounts.google.com/ServiceLogin?sacu=1&scc=1&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&osid=1&service=mail&ss=1&ltmpl=default&rm=false#password";
				if(nextButtonClickURL.equals(passwordPageURL)){
					//enter password
					driver.findElement(By.id("Passwd")).sendKeys(password);Thread.sleep(2000);
					//click signin
					driver.findElement(By.id("signIn")).click();
					
					Thread.sleep(4000);
					String signInClickURL = driver.getCurrentUrl();
					System.out.println("signInClickURL : "+signInClickURL);
					
					String inboxPageURL = "https://mail.google.com/mail/u/0/#inbox";
					if(signInClickURL.equals(inboxPageURL)){
						driver.findElement(By.xpath("//*[@id='gb']/div[1]/div[1]/div[2]/div[4]/div[1]/a/span")).click();Thread.sleep(2000);
						driver.findElement(By.xpath("//*[@id='gb_71']")).click();Thread.sleep(2000);
						if(criteria.equals("valid")){
							cell.setCellValue("Success : Successfully signout");
							System.out.println("Success : Successfully signout");							
						}
						else{
							cell.setCellValue("Failure : Successfully signout but criteria invalid");
							System.out.println("Failure : Successfully signout but criteria invalid");
						}
					}
					else{
						cell.setCellValue("Failure : Wrong Credentials");
						System.out.println("Failure : Wrong Credentials");
					}						
				}
				else{
					cell.setCellValue("Failure : Invalid User");
					System.out.println("Failure : Invalid User");
				}
				FileOutputStream fileOut = new FileOutputStream("GmailLogin.xlsx");
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