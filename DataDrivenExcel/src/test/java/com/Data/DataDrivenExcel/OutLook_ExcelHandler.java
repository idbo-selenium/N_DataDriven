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

public class OutLook_ExcelHandler {

	@Test
	public void OutLook_Login(){
		
		try {
			FileInputStream	outlookFile = new FileInputStream("OutLookLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(outlookFile);
			XSSFSheet sheet = workbook.getSheet("OutLookLogin");
			int rows = sheet.getLastRowNum();
			System.out.println("rows : "+rows);
			for(int i =1;i<=rows;i++){
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(username +" , "+password+" , "+criteria);
				
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(cell.CELL_TYPE_STRING);
				
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				driver.get("https://email.i-dbo.com/owa/#path=/mail");
				driver.findElement(By.id("username")).sendKeys(username);Thread.sleep(4000);
				driver.findElement(By.id("password")).sendKeys(password);Thread.sleep(4000);
				driver.findElement(By.xpath("//*[@id='lgnDiv']/div[9]/div/span")).click();Thread.sleep(5000);
				String currentURL = driver.getCurrentUrl();
				System.out.println("currentURL : "+currentURL);
				if(currentURL.equals("https://email.i-dbo.com/owa/#path=/mail")){					
					//new mail link click
					driver.findElement(By.xpath("//*[@id='_ariaId_37']")).click();
					Thread.sleep(20000);
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[3]/div[2]/div[1]/div[3]/div[1]/div/div/div/span/span[1]/form/input")).sendKeys("Nirmala@ProConstructor.com");
					Thread.sleep(2000);
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[3]/div[2]/div[1]/div[7]/div/div/input")).sendKeys("Sample Mail");
					Thread.sleep(2000);
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[2]/div[1]/span[1]/div[1]/button[1]/span[2]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[1]/div[2]/div[2]/div/div[1]/div/table/tbody/tr/td[1]/button/div[2]")).click();
					//driver.findElement(By.xpath("//*[@id='_ariaId_475']/div/div[2]/div[3]/div/div[4]/div")).click();
					if(criteria.equals("valid")){
						cell.setCellValue("Success : Successfully Login");
						System.out.println("Success : Successfully Login");
					}
					else{
						cell.setCellValue("Failure : Successfully Login but criteria Invalid");
						System.out.println("Failure : Successfully Login but criteria Invalid");
					}
				}
				else{
					cell.setCellValue("Failure : Wrong Credentials");
					System.out.println("Failure : Wrong Credentials");
				}
				FileOutputStream fileOut = new FileOutputStream("OutLookLogin.xlsx");
				workbook.write(fileOut);
				fileOut.close();
				Thread.sleep(2000);
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