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

public class Trello_Login_ExcelHandler {

	@Test
	public void Excel_Handler(){
		try {
			FileInputStream trelloFile = new FileInputStream("TrelloLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(trelloFile);
			XSSFSheet sheet = workbook.getSheet("Trello_Login");
			
			int rows = sheet.getLastRowNum();
			System.out.println("rows : "+rows);
			for(int i=1;i<=rows;i++){
				String username = String.valueOf(sheet.getRow(i).getCell(0));
				String password = String.valueOf(sheet.getRow(i).getCell(1));
				String criteria = String.valueOf(sheet.getRow(i).getCell(2));
				Cell cell = sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				System.out.println(username +" , "+password+" , "+criteria);
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				driver.get("https://trello.com/login");
				//enter username
				driver.findElement(By.id("user")).sendKeys(username);
				//enter password
				driver.findElement(By.id("password")).sendKeys(password);
				//login click
				driver.findElement(By.id("login")).click();
				Thread.sleep(4000);
				String currentURL = driver.getCurrentUrl();
				System.out.println("CurrentURL : " +currentURL);
				if(currentURL.equals("https://trello.com/")){
					//new board click
					driver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div/div[2]/ul/li/a")).click();Thread.sleep(2000);
					//board title
					driver.findElement(By.xpath("//*[@id='boardNewTitle']")).sendKeys("JAVA");Thread.sleep(2000);
					//create board click
					driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/form/input[3]")).click();Thread.sleep(2000);
					//list title
					driver.findElement(By.xpath("//*[@id='board']/div/form/input")).sendKeys("JavaList1");Thread.sleep(2000);
					//save button click
					driver.findElement(By.xpath("//*[@id='board']/div/form/div/input")).click();Thread.sleep(2000);
					//"add a card" click
					driver.findElement(By.xpath("//*[@id='board']/div[1]/div/a")).click();Thread.sleep(2000);
					//enter first card name
					driver.findElement(By.xpath("//*[@id='board']/div[1]/div/div[2]/div/div[1]/div/textarea")).sendKeys("Card11");Thread.sleep(2000);
					//Add button click
					driver.findElement(By.xpath("//*[@id='board']/div[1]/div/div[2]/div/div[2]/div[1]/input")).click();Thread.sleep(2000);
					//enter second card name
					driver.findElement(By.xpath("//*[@id='board']/div[1]/div/div[2]/div[2]/div[1]/div/textarea")).sendKeys("Card12");Thread.sleep(2000);
					//Add button click
					driver.findElement(By.xpath("//*[@id='board']/div[1]/div/div[2]/div[2]/div[2]/div[1]/input")).click();Thread.sleep(2000);
					//"X" button click
					driver.findElement(By.xpath("//*[@id='board']/div[1]/div/div[2]/div[3]/div[2]/div[1]/a")).click();Thread.sleep(2000);
					//"add a list" click
					driver.findElement(By.xpath("//*[@id='board']/div[2]/form/span")).click();Thread.sleep(2000);
					//enter 2nd list name
					driver.findElement(By.xpath("//*[@id='board']/div[2]/form/input")).sendKeys("JavaList2");Thread.sleep(2000);
					//save button click
					driver.findElement(By.xpath("//*[@id='board']/div[2]/form/div/input")).click();Thread.sleep(2000);
					//"add a card" click
					driver.findElement(By.xpath("//*[@id='board']/div[2]/div/a")).click();Thread.sleep(2000);
					//enter first card name
					driver.findElement(By.xpath("//*[@id='board']/div[2]/div/div[2]/div/div[1]/div/textarea")).sendKeys("Card21");Thread.sleep(2000);
					//add button click
					driver.findElement(By.xpath("//*[@id='board']/div[2]/div/div[2]/div/div[2]/div[1]/input")).click();Thread.sleep(2000);
					//enter second card name
					driver.findElement(By.xpath("//*[@id='board']/div[2]/div/div[2]/div[2]/div[1]/div/textarea")).sendKeys("Card22");Thread.sleep(2000);
					//add button click
					driver.findElement(By.xpath("//*[@id='board']/div[2]/div/div[2]/div[2]/div[2]/div[1]/input")).click();Thread.sleep(2000);
					//"X" button click
					driver.findElement(By.xpath("//*[@id='board']/div[2]/div/div[2]/div[3]/div[2]/div[1]/a")).click();Thread.sleep(2000);
					//more button click
					driver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div[2]/div/ul/li[5]/a")).click();Thread.sleep(2000);
					//close board click
					driver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div[2]/div/ul[3]/li/a")).click();Thread.sleep(2000);
					//close button click
					driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/input")).click();Thread.sleep(2000);
					//profile click
					driver.findElement(By.xpath("//*[@id='header']/div[4]/a[2]/span[2]")).click();Thread.sleep(2000);
					//logout click
					driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();Thread.sleep(2000);
					
					if(criteria.equals("valid")){
						cell.setCellValue("Success : Successfully logout");
						System.out.println("Success : Successfully logout");
					}
					else{
						cell.setCellValue("Failure : Successfully logout but criteria invalid");
						System.out.println("Failure : Successfully logout but criteria invalid");
					}					
				}
				else{
					cell.setCellValue("Failure : Invalid Credentials");
					System.out.println("Failure : Invalid Credentials");					
				}
				FileOutputStream fileOut = new FileOutputStream("TrelloLogin.xlsx");
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