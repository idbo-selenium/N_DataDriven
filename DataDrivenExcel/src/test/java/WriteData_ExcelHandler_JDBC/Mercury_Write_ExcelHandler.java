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

public class Mercury_Write_ExcelHandler {

	public WebDriver driver;
	public static String JDBCDriver = "com.mysql.jdbc.Driver";
	public static String DBURL ="jdbc:mysql://localhost:3306/selenium";
	public static String username = "root";
	public static String password = "";
	
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
	public void ExcelHandler(){
		Connection conn;
		Statement stmt;
		try {
			FileInputStream mercuryFile = new FileInputStream("MercuryLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(mercuryFile);
			XSSFSheet sheet = workbook.createSheet("JDBC");
			Class.forName(JDBCDriver);
			System.out.println("Connecting to DataBase...");
			conn = DriverManager.getConnection(DBURL, username, password);
			stmt = conn.createStatement();
			String query = "Select * from mercury";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Mercury> mercuryArray = new ArrayList<Mercury>();
			Mercury mercury = null;
			while(rs.next()){
				mercury = new Mercury();
				mercury.id = rs.getInt("ID");
				mercury.username = rs.getString("username");
				mercury.password = rs.getString("password");
				mercuryArray.add(mercury);
				//System.out.println(mercury.id+" , "+mercury.username+" , "+mercury.password);
			}			
			
			Row rowUsername = sheet.createRow(0);
			Cell cellUsername = rowUsername.createCell(0);
			cellUsername.setCellValue("Username");
			
			Row rowPassword = sheet.getRow(0);
			Cell cellPassword = rowPassword.createCell(1);
			cellPassword.setCellValue("Password");			
			
			int i=0;
			for(Mercury item : mercuryArray){
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
				
				driver.get("http://newtours.demoaut.com/");
				driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]")).click();
				driver.findElement(By.name("userName")).sendKeys(excelUsername);
				driver.findElement(By.name("password")).sendKeys(excelPassword);
				driver.findElement(By.name("login")).click();
				Thread.sleep(4000);
				String currentUrl = driver.getCurrentUrl();
				if(!currentUrl.equals("http://newtours.demoaut.com/mercurysignon.php")){
					driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a")).click();
					System.out.println("Correct Details");
				}
				else{
					System.out.println("Wrong details");
				}
			}
			FileOutputStream fileOut = new FileOutputStream("MercuryLogin.xlsx");
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}