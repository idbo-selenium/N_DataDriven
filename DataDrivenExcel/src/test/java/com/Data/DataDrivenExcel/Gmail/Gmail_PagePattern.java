package com.Data.DataDrivenExcel.Gmail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.By;

public class Gmail_PagePattern {

	@Test
	public void PagePattern_Gmail()throws Exception{
		try {
			FileInputStream file = new FileInputStream("GmailLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet loginSheet = workbook.getSheet("GmailLogin");
			int loginRows = loginSheet.getLastRowNum();
			System.out.println("Rows : "+loginRows);
			for(int i=1;i<=loginRows;i++){				
				String username = String.valueOf(loginSheet.getRow(i).getCell(0));
				String password = String.valueOf(loginSheet.getRow(i).getCell(1));
				String criteria = String.valueOf(loginSheet.getRow(i).getCell(2));
				System.out.println(username +" , "+password+" , "+criteria);
				Browser.OpenBrowser();
				Browser.driver.get("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1");
				String usernamepageURL = Browser.driver.getCurrentUrl();
				
				String passwordpageURL = "https://accounts.google.com/ServiceLoginAuth#password";
				String inboxPageURL = "https://mail.google.com/mail/u/0/#inbox";
				Browser.driver.findElement(By.id("Email")).sendKeys(username);
				Thread.sleep(3000);
				Browser.driver.findElement(By.id("next")).click();
				Thread.sleep(3000);
				String afterNextClick = Browser.driver.getCurrentUrl();
				//System.out.println("afterNextClick : "+afterNextClick);
				Cell cell = loginSheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(afterNextClick.equals(usernamepageURL)){
					cell.setCellValue("Failure : invalid user");
					System.out.println("Failure : invalid user");
				}
				if(!afterNextClick.equals(usernamepageURL)) {
					Browser.driver.findElement(By.id("Passwd")).sendKeys(password);
					Thread.sleep(3000);
					Browser.driver.findElement(By.id("signIn")).click();
					Thread.sleep(3000);
					String afterSigninClick = Browser.driver.getCurrentUrl();
					//System.out.println("afterSigninClick : "+afterSigninClick);
					if(afterSigninClick.equals(passwordpageURL)){
						cell.setCellValue("Failure : Wrong Credentials");
						System.out.println("Failure : Wrong Credentials");
					}
					else if(afterSigninClick.equals(inboxPageURL)){
						Browser.driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/a/span")).click();
						Thread.sleep(2000);
						Browser.driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[4]/div[2]/div[3]/div[2]/a")).click();
						Thread.sleep(2000);	
						if(criteria.equals("valid")){
							cell.setCellValue("Success : Successfully Signout");
							System.out.println("Success : Successfully Signout");
						}
						else{
							cell.setCellValue("Failure : Successfully Signout but criteria invalid");
							System.out.println("Failure : Successfully Signout but criteria invalid");
						}
					}
				}
				FileOutputStream fileout = new FileOutputStream("GmailLogin.xlsx");
				workbook.write(fileout);
				fileout.close();
				Browser.CloseBrowser();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}