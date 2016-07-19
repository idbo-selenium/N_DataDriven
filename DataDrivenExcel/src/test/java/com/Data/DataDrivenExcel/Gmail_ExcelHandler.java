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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class Gmail_ExcelHandler {

	public WebDriver driver;
	@Test
	public void Gmail_Handler()throws Exception{
		//Pattern compose_button = new Pattern("C:\\Users\\sravan\\Desktop\\important\\Sikuli_Snippets\\gmail\\compose_button.png");
		Pattern mailsending_to = new Pattern("C:\\Users\\sravan\\Desktop\\important\\Sikuli_Snippets\\gmail\\mailsending_to.png");
		Pattern subject = new Pattern("C:\\Users\\sravan\\Desktop\\important\\Sikuli_Snippets\\gmail\\subject.png");
		Pattern attachfile_click = new Pattern("C:\\Users\\sravan\\Desktop\\important\\Sikuli_Snippets\\gmail\\attachfile_click.png");
		Pattern mailsend_button = new Pattern("C:\\Users\\sravan\\Desktop\\important\\Sikuli_Snippets\\gmail\\mailsend_button.png");
		try {
			FileInputStream file = new FileInputStream("GmailLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet gmail_Loginsheet = workbook.getSheet("GmailLogin");
			int gmail_LoginsheetRows = gmail_Loginsheet.getLastRowNum();
			System.out.println("gmail_LoginsheetRows : "+gmail_LoginsheetRows);
			for(int i = 1; i<=gmail_LoginsheetRows;i++){
				String gmail_Login_Username = String.valueOf(gmail_Loginsheet.getRow(i).getCell(0));
				String gmail_Login_Password = String.valueOf(gmail_Loginsheet.getRow(i).getCell(1));
				String gmail_Login_Criteria = String.valueOf(gmail_Loginsheet.getRow(i).getCell(2));
				System.out.println(gmail_Login_Username + " , "+gmail_Login_Password + " , "+gmail_Login_Criteria);
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				//driver = new FirefoxDriver();
				driver.get("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1");
				Thread.sleep(4000);
				String usernamePageURL = driver.getCurrentUrl();
				String passwordPageURL = "https://accounts.google.com/ServiceLoginAuth#password";
				String inboxPageURL = "https://mail.google.com/mail/u/0/#inbox";
				//System.out.println("usernamePageURL : "+usernamePageURL);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(gmail_Login_Username);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='next']")).click();
				Thread.sleep(2000);
				String afterclickNextButton = driver.getCurrentUrl();
				Cell cell = gmail_Loginsheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				
				if(usernamePageURL.equals(afterclickNextButton)){
					cell.setCellValue("Failure : Invalid Username");
					System.out.println("Failure : Invalid Username");
				}
				if(!(usernamePageURL.equals(afterclickNextButton))){
					driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(gmail_Login_Password);
					Thread.sleep(2000);
					driver.findElement(By.xpath("//*[@id='signIn']")).click();
					Thread.sleep(4000);
					String signInClick = driver.getCurrentUrl();
					Thread.sleep(2000);
					//System.out.println("signInClick : "+signInClick);
					if(passwordPageURL.equals(signInClick)){
						cell.setCellValue("Failure : Wrong credentials");
						System.out.println("Failure : Wrong credentials");
					}
					else if(signInClick.equals(inboxPageURL)){
						Screen src = new Screen();
						//src.click(compose_button);
						driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[1]/div[1]/div[2]/div/div/div[1]/div/div")).click();
						Thread.sleep(2000);						
						src.type(mailsending_to, "knskumari@gmail.com");
						Thread.sleep(2000);
						Actions action = new Actions(driver);
						action.sendKeys(Keys.TAB).build().perform();
						Thread.sleep(2000);
						src.type(subject, "Autoit");
						Thread.sleep(2000);
						src.click(attachfile_click);
						Thread.sleep(2000);
						Runtime.getRuntime().exec("C:\\Users\\sravan\\Desktop\\important\\Sikuli_Snippets\\gmail\\Attachfile.exe");
						Thread.sleep(4000);
						src.click(mailsend_button);
						Thread.sleep(2000);
						
						driver.findElement(By.xpath("//*[@id='gb']/div[1]/div[1]/div[2]/div[4]/div[1]/a/span")).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//*[@id='gb_71']")).click();
						Thread.sleep(2000);
						if(gmail_Login_Criteria.equals("valid")){
							cell.setCellValue("Success : SuccessFully signout");
							System.out.println("Success : SuccessFully signout");
						}
						else{
							cell.setCellValue("Failure : SuccessFully signout but Criteria invalid");
							System.out.println("Failure : SuccessFully signout but Criteria invalid");
						}
					}
				}
				FileOutputStream fileOut = new FileOutputStream("GmailLogin.xlsx");
				workbook.write(fileOut);
				fileOut.close();
				driver.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}