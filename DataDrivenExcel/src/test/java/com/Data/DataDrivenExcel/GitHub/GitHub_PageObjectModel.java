package com.Data.DataDrivenExcel.GitHub;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class GitHub_PageObjectModel {

	@Test
	public void ExcelHandler(){
		try {
			FileInputStream file = new FileInputStream("GitHub.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("GitHubLogin");
			int githubsheetRows = sheet.getLastRowNum();
			System.out.println("githubsheetRows : "+githubsheetRows);
			for(int i = 1;i<=githubsheetRows;i++){
				//System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				String githubUsername = String.valueOf(sheet.getRow(i).getCell(0));
				String githubPassword = String.valueOf(sheet.getRow(i).getCell(1));
				String githubCriteria = String.valueOf(sheet.getRow(i).getCell(2));
				System.out.println("githubUsername : "+githubUsername +" , githubPassword : "+githubPassword);				
				
				HomePage.GoTo();
				SignInPage.LoginAs(githubUsername).WithPassword(githubPassword).Login();				
				
				Thread.sleep(3000);
				//Assert.assertEquals("https://github.com/", UserAccountPage.IsAbleToLoginAs());
				Cell cell =  sheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(UserAccountPage.IsAbleToLoginAs().equals("https://github.com/")){
					HomePage.Signout();
					if(githubCriteria.equals("valid")){
						System.out.println("Successfully Signout");
						cell.setCellValue("Successfully Signout");
					}
					else{
						System.out.println("Failure : Successfully Signout but criteria 'invalid'");
						cell.setCellValue("Failure : Successfully Signout but criteria 'invalid'");
					}
				}
				else{
					System.out.println("Failure");
					cell.setCellValue("Failure : Invalid Credentials");
				}
				FileOutputStream fileOut = new FileOutputStream("GitHub.xlsx");
				workbook.write(fileOut);
				fileOut.close();
				HomePage.CloseBrowser();
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