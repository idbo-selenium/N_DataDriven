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
import org.openqa.selenium.firefox.FirefoxDriver;

public class GitHub_Write_ExcelHandler {

	public WebDriver driver;
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost:3306/selenium";	
	public static String username = "root";
	public static String password = "";
	public static String baseURL = "https://github.com/login";
	
	@Before
	public void Init(){
		//driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@After
	public void CleanUp(){
		driver.close();
	}
	
	@Test
	public void JDBC_ExcelHandler(){
		Connection con;
		Statement stmt;
		try {
			FileInputStream githubFile = new FileInputStream("GitHub.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(githubFile);
			XSSFSheet newSheet = workbook.getSheet("JDBC");
			Class.forName(JDBC_Driver);
			System.out.println("Connecting to DataBase....");
			con = DriverManager.getConnection(DB_URL, username, password);
			stmt = con.createStatement();
			String query = "Select * from github";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<GitHub> githubArray = new ArrayList<GitHub>();
			GitHub github = null;
			while(rs.next()){
				github = new GitHub();
				github.id = rs.getInt("id");
				github.username = rs.getString("username");
				github.password = rs.getString("password");
				githubArray.add(github);
			}
			
			Row rowUsername = newSheet.createRow(0);
			Cell cellUsername = rowUsername.createCell(0);
			cellUsername.setCellValue("Username");
			
			Row rowPassword = newSheet.getRow(0);
			Cell cellPassword = rowPassword.createCell(1);
			cellPassword.setCellValue("Password");
			
			int i=0;
			for(GitHub item : githubArray){
				i++;
				Row rowUsernameCredentials = newSheet.createRow(i);
				Cell cellUsernameCredentials = rowUsernameCredentials.createCell(0);
				cellUsernameCredentials.setCellType(Cell.CELL_TYPE_STRING);
				Row rowPasswordCredentials = newSheet.getRow(i);
				Cell cellPasswordCredentials = rowPasswordCredentials.createCell(1);
				cellPasswordCredentials.setCellType(Cell.CELL_TYPE_STRING);
				//System.out.println(item.id+" , "+item.username+" , "+item.password);
				cellUsernameCredentials.setCellValue(item.username);
				cellPasswordCredentials.setCellValue(item.password);
				driver.get(baseURL); 
				String excelUsername = String.valueOf(newSheet.getRow(i).getCell(0));
				String excelPassword = String.valueOf(newSheet.getRow(i).getCell(1));
				System.out.println(excelUsername+" , "+excelPassword);
				driver.findElement(By.id("login_field")).sendKeys(excelUsername);
				Thread.sleep(2000);
				driver.findElement(By.id("password")).sendKeys(excelPassword);
				Thread.sleep(2000);
				driver.findElement(By.name("commit")).click();Thread.sleep(4000);
				String currentURL = driver.getCurrentUrl();
				if("https://github.com/session".equals(currentURL)){
					System.out.println("Wrong Credentials");
				}
				else{
					System.out.println("Correct Credentials");
					driver.findElement(By.xpath("//*[@id='user-links']/li[3]/a/img")).click();
					driver.findElement(By.xpath("//*[@id='user-links']/li[3]/div/div/form/button")).click();
				}				
			}
			FileOutputStream githubFileOut = new FileOutputStream("GitHub.xlsx");
			workbook.write(githubFileOut);
			githubFileOut.close();			
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}