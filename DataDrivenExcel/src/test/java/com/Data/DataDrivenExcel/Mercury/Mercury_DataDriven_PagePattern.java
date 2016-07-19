package com.Data.DataDrivenExcel.Mercury;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.apache.poi.ss.usermodel.Cell;

public class Mercury_DataDriven_PagePattern {
	
	@Test
	public void PagePattern_Mercury(){
		
		try {
			FileInputStream file = new FileInputStream("MercuryLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet loginSheet = workbook.getSheet("MercuryLogin");
			int loginSheetRows = loginSheet.getLastRowNum();
			System.out.println("No. of Rows : "+loginSheetRows);
			for(int i=1;i<=loginSheetRows;i++){
				String username = String.valueOf(loginSheet.getRow(i).getCell(0));
				String password = String.valueOf(loginSheet.getRow(i).getCell(1));
				String criteria = String.valueOf(loginSheet.getRow(i).getCell(2));
				System.out.println(username+" , "+password+" , "+criteria);
				Browser.OpenBrowser();
				Homepage.GoTo();
				SignInPage.LoginAs(username).WithPassword(password).Login();
				String currentURL = Browser.driver.getTitle();
				String homepageURL = "Find a Flight: Mercury Tours:";
				System.out.println("currentURL : "+currentURL);
				Cell cell = loginSheet.getRow(i).createCell(3); 
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(currentURL.equals(homepageURL)){
					Homepage.Signout();
					if(criteria.equals("valid")){
						cell.setCellValue("Success : Successfully logout");
						System.out.println("---------Success : Successfully logout-----------");
					}
					else{
						cell.setCellValue("Failure : Successfully logout but Criteria invalid");
						System.out.println("----------Failure : Successfully logout but Criteria invalid------------");
					}
				}
				else{
					cell.setCellValue("Failure : invalid details");
					System.out.println("-----------Failure : invalid details-------------");
				}
				FileOutputStream fileout = new FileOutputStream("MercuryLogin.xlsx");
				workbook.write(fileout);
				fileout.close();
				Browser.CloseBrowser();
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}