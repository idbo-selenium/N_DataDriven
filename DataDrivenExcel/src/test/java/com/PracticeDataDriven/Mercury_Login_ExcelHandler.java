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

public class Mercury_Login_ExcelHandler {

	@Test
	public void ExcelHandler(){
		
		try {
			FileInputStream gmailFile = new FileInputStream("MercuryLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(gmailFile);
			XSSFSheet sheet = workbook.getSheet("MercuryLogin");
			
			int rows = sheet.getLastRowNum();
			System.out.println("rows : "+rows);
			for(int i = 1;i<=rows;i++){
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(username+" , "+password+" , "+criteria);
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				driver.get("http://newtours.demoaut.com/");
				//signOn click
				driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]")).click();
				Thread.sleep(2000);
				//enter username
				driver.findElement(By.name("userName")).sendKeys(username);
				Thread.sleep(2000);
				//enter password
				driver.findElement(By.name("password")).sendKeys(password);
				Thread.sleep(2000);
				//click submit
				driver.findElement(By.name("login")).click();Thread.sleep(2000);
				String currentURL = driver.getCurrentUrl();
				System.out.println("CurrentURL :"+currentURL);
				String homepageURL = "http://newtours.demoaut.com/mercuryreservation.php";
				if(homepageURL.equals(currentURL)){
					//signOff Click
					driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]")).click();
					Thread.sleep(2000);
					if(criteria.equals("valid")){
						cell.setCellValue("Success : Successfully Sign-Off");
						System.out.println("Success : Successfully Sign-Off");
					}
					else{
						cell.setCellValue("Failure : successfully Sign-Off but criteria invalid"); 
						System.out.println("Failure : successfully Sign-Off but criteria invalid");
					}
				}
				else{
					cell.setCellValue("Failure : Wrong Credentials");
					System.out.println("Failure : Wrong Credentials");
				}
				FileOutputStream fileout = new FileOutputStream("MercuryLogin.xlsx");
				workbook.write(fileout);
				fileout.close();
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