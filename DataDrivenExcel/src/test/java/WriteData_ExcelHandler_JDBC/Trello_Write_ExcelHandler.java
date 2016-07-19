package WriteData_ExcelHandler_JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Trello_Write_ExcelHandler {

	public WebDriver driver;
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	public static String DB_URL ="jdbc:mysql://localhost:3306/selenium";
	public static String username = "root";
	public static String password = "";
	public static String baseURL = "https://trello.com/login";
	@Before
	public void Init(){
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver= new ChromeDriver();
	}
	
	@After
	public void Cleanup(){
		//driver.close();
	}
	
	@Test
	public void Write_ExcelHandler(){
		Connection conn;
		Statement stmt;
		try {
			FileInputStream trelloFile = new FileInputStream("TrelloLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(trelloFile);
			XSSFSheet sheet = workbook.createSheet("JDBC");
			Class.forName(JDBC_Driver);
			System.out.println("Connecting to DataBase....");
			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();
			String query ="select * from trellologin";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Trello> trelloArray = new ArrayList<Trello>();
			Trello trello = null;
			while(rs.next()){
				trello = new Trello();
				trello.id = rs.getInt("ID");
				trello.username = rs.getString("username");
				trello.password = rs.getString("password");
				trelloArray.add(trello);
			}
			
			Row rowUsername = sheet.createRow(0);
			Cell cellUsername = rowUsername.createCell(0);
			cellUsername.setCellValue("Username");
			
			Row rowPassword = sheet.getRow(0);
			Cell cellPassword = rowPassword.createCell(1);
			cellPassword.setCellValue("Password");
			
			int i = 0;
			for(Trello item : trelloArray){
				i++;
				System.out.println(item.id+" , "+item.username+" , "+item.password);
				
				Row rowUsernameCredentials = sheet.createRow(i);
				Cell cellUsernameCredentials = rowUsernameCredentials.createCell(0);
				cellUsernameCredentials.setCellValue(item.username);
				
				Row rowPasswordCredentials = sheet.getRow(i);
				Cell cellPasswordCredentials = rowPasswordCredentials.createCell(1);
				cellPasswordCredentials.setCellValue(item.password);
				
				String excelUsername = String.valueOf(sheet.getRow(i).getCell(0));
				String excelPassword = String.valueOf(sheet.getRow(i).getCell(1));
				System.out.println(excelUsername +" , "+excelPassword);	
				driver.get(baseURL);
				driver.findElement(By.id("user")).sendKeys(excelUsername);Thread.sleep(2000);
				driver.findElement(By.id("password")).sendKeys(excelPassword);Thread.sleep(2000);
				driver.findElement(By.id("login")).click();Thread.sleep(4000);
				String currentURL = driver.getCurrentUrl();
				if(currentURL.equals(baseURL)){
					System.out.println("Wrong Credentials");
				}
				else{
					System.out.println("Correct Credentials");
					driver.findElement(By.xpath("//*[@id='header']/div[4]/a[2]/span[2]")).click();
					driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();
				}
			}
			FileOutputStream fileOut = new FileOutputStream("TrelloLogin.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
}