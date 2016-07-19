package com.Data.DataDrivenExcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GitHub_ExcelHandler {

	@Test
	public void ExcelHandler(){
		//driver.navigate().to("https://github.com/login");
		try {
			FileInputStream file = new FileInputStream("GitHub.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("GitHubLogin");
			int rows = sheet.getLastRowNum();
			System.out.println("rows : "+rows);			
			for(int i = 1;i<=rows;i++){
				WebDriver driver = new FirefoxDriver();
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println(username+" , "+password+" , "+criteria);
				driver.navigate().to("https://github.com/login");
				driver.findElement(By.id("login_field")).sendKeys(username);
				driver.findElement(By.id("password")).sendKeys(password);
				driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form/div[4]/input[3]")).click();
				Thread.sleep(4000);
				String URL = driver.getCurrentUrl();
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				System.out.println("CurrentURL : "+URL);
				if(URL.equals("https://github.com/")){
					driver.findElement(By.className("avatar")).click();
					driver.findElement(By.xpath("/html/body/div[2]/div/ul[2]/li[3]/div/div/form/button")).click();
					if(criteria.equals("valid")){
						cell.setCellValue("Success : SuccessFully SignOut");
						System.out.println("Success : SuccessFully SignOut");
					}
					else{
						cell.setCellValue("Failure : successfully SignOut but criteria invalid");
						System.out.println("Failure : successfully SignOut but criteria invalid");
					}
				}
				else{
					cell.setCellValue("Failure : Wrong Credentials");
					System.out.println("Failure : Wrong Credentials");
					
				}
				driver.close();
				FileOutputStream fileOut = new FileOutputStream("GitHub.xlsx");
				workbook.write(fileOut);
				fileOut.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}