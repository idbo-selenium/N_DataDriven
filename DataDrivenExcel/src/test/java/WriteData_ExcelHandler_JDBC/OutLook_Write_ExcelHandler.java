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
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OutLook_Write_ExcelHandler {

	public WebDriver driver ;
	public static String baseURL = "https://email.i-dbo.com/owa/#path=/mail"; 
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public static String username = "root";
	public static String password = "";
	
	@Test
	public void OutLook_Login(){
		Connection conn;
		Statement stmt;
		
		try {
			FileInputStream outlookFile = new FileInputStream("OutLookLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(outlookFile);
			XSSFSheet sheet = workbook.getSheet("JDBC");
			Class.forName(JDBC_Driver);
			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();
			String sql = "select * from outlook";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<OutLook> outlookArray = new ArrayList<OutLook>();
			OutLook outlook = null;
			while(rs.next()){
				outlook = new OutLook();
				outlook.id = rs.getInt("id");
				outlook.username = rs.getString("username");
				outlook.password = rs.getString("password");
				outlookArray.add(outlook);				
			}
			
			Row usernameRow = sheet.createRow(0);
			Cell usernameCell = usernameRow.createCell(0);
			usernameCell.setCellValue("Username");
			
			Row passwordRow = sheet.getRow(0);
			Cell passwordCell = passwordRow.createCell(1);
			passwordCell.setCellValue("Password");
			
			int i=0;
			for(OutLook item : outlookArray){
				i++;
				Row rowUsername = sheet.createRow(i);
				Cell cellUsername = rowUsername.createCell(0);
				cellUsername.setCellType(Cell.CELL_TYPE_STRING);
				cellUsername.setCellValue(item.username);
				
				Row rowPassword = sheet.getRow(i);
				Cell cellPassword = rowPassword.createCell(1);
				cellPassword.setCellType(Cell.CELL_TYPE_STRING);
				cellPassword.setCellValue(item.password);
				
				String excelUsername = String.valueOf(sheet.getRow(i).getCell(0));
				String excelPassword = String.valueOf(sheet.getRow(i).getCell(1));
				
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.get("https://email.i-dbo.com/owa/#path=/mail");
				driver.findElement(By.id("username")).sendKeys(excelUsername);
				driver.findElement(By.id("password")).sendKeys(excelPassword);
				driver.findElement(By.xpath("//*[@id='lgnDiv']/div[9]/div/span")).click();
				
				Thread.sleep(10000);
				
				String currentURL = driver.getCurrentUrl();
				System.out.println("currentURL : "+currentURL);
				if(currentURL.equals("https://email.i-dbo.com/owa/#path=/mail")){
					//new mail button click
					driver.findElement(By.xpath("//*[@id='_ariaId_37']")).click();
					Thread.sleep(20000);
					//to address clidk
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[3]/div[2]/div[1]/div[3]/div[1]/div/div/div/span/span[1]/form/input")).sendKeys("Nirmala@ProConstructor.com");
					Thread.sleep(2000);
					//subject
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[3]/div[2]/div[1]/div[7]/div/div/input")).sendKeys("Example mail");
					Thread.sleep(2000);
					//sent mail
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[2]/div[1]/span[1]/div[1]/button[1]/span[2]")).click();
					System.out.println("Success");
				}
				else{
					System.out.println("Failure");
				}
			}
			
			FileOutputStream fileOut = new FileOutputStream("OutLookLogin.xlsx");
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