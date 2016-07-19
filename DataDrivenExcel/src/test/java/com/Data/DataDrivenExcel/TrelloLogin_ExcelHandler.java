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
import org.openqa.selenium.firefox.FirefoxDriver;

public class TrelloLogin_ExcelHandler {

	@Test
	public void Trello_Login(){
		
		FileInputStream file;
		WebDriver driver = null;
		try {
			file = new FileInputStream("TrelloLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("Trello_Login");
			int rows = sheet.getLastRowNum();
			System.out.println("rows : "+rows);
			for(int i = 1;i<=rows;i++){
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(username +" , "+password+" , "+criteria);
				driver = new FirefoxDriver();
				Thread.sleep(2000);
				driver.navigate().to("https://trello.com/login");
				driver.findElement(By.xpath("//*[@class='login-password-container-email']/div[1]/input[1]")).sendKeys(username);
				driver.findElement(By.xpath("//*[@class='login-password-container-email']/div[1]/input[2]")).sendKeys(password);
				driver.findElement(By.xpath("//*[@class='login-password-container-email']/input")).click();
				Thread.sleep(8000);
				String URL = driver.getCurrentUrl();
				System.out.println("CurrentURL : "+URL);
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(URL.equals("https://trello.com/")){
					driver.findElement(By.xpath("//*[@class='header-user']/a[2]")).click();
					driver.findElement(By.xpath("//*[@class='pop-over is-shown']/div/div[2]/div/div/div/ul[3]/li/a")).click();
					if(criteria.equals("valid")){
						cell.setCellValue("Success : Successfully logout");
					}
					else{
						cell.setCellValue("Failure : Successfully logout but criteria invalid");
					}
				}
				else{
					cell.setCellValue("Failure : Invalid Credentials");
				}
				FileOutputStream fileout = new FileOutputStream("TrelloLogin.xlsx");
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