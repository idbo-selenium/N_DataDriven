package com.Data.DataDrivenExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TrelloLogin_DataDriven {
	
	public static WebDriver driver;
	
	@Test
	public void TrelloLogin_Test(){
		try {
			FileInputStream trelloFile = new FileInputStream("TrelloLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(trelloFile);
			XSSFSheet loginSheet = workbook.getSheet("Trello_ Login");
			int trelloRows = loginSheet.getLastRowNum();
			System.out.println("Rows : "+trelloRows);
			//String trelloUsername = loginSheet.getR 
			for(int i=1;i<=trelloRows;i++){
				driver = new FirefoxDriver();
				driver.get("https://trello.com/login");
				String trelloUsername = String.valueOf(loginSheet.getRow(i).getCell(0));
				String trelloPassword = String.valueOf(loginSheet.getRow(i).getCell(1));
				System.out.println("username : "+trelloUsername+" , password : "+trelloPassword);
				driver.findElement(By.id("user")).sendKeys(trelloUsername);
				driver.findElement(By.id("password")).sendKeys(trelloPassword);
				driver.findElement(By.id("login")).click();Thread.sleep(5000);
				String currentURL = driver.getCurrentUrl();
				String homepageURL = "https://trello.com/";
				System.out.println("currentURL : "+currentURL);
				if(currentURL.equals(homepageURL)){
					//profile click
					driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[4]/a[2]/span[2]")).click();
					//settings click
					driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[1]/li[3]/a")).click();Thread.sleep(4000);
					//edit profile button click
					driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[1]/div/div/div/a")).click();
					//changing full name
					driver.findElement(By.name("fullName")).clear();
					driver.findElement(By.name("fullName")).sendKeys("Nirmala Santoshi Kumari");
					System.out.println("successfully changed full name");
					//cancel button click
					//driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[1]/div/div/form/input[5]")).click();
					//save button click
					driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[1]/div/div/form/input[4]")).click();
					//profile click
					driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[4]/a[2]/span[2]")).click();
					//logout click
					driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();
					System.out.println("sucessfully signout");
				}
				else{
					System.out.println("wrong credentials");
				}
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