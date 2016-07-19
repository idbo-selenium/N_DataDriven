package com.Data.DataDrivenExcel.Trello;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoginTest {
	
	public static void ExcelHandler(){
		try {
			FileInputStream file = new FileInputStream("TrelloLogin.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet trelloSheet = workbook.getSheet("Trello_Login");
			int trelloSheetRows = trelloSheet.getLastRowNum();
			System.out.println("trelloSheetRows : "+trelloSheetRows);
			for(int i=1;i<=trelloSheetRows;i++){
				String trelloUsername = String.valueOf(trelloSheet.getRow(i).getCell(0)); 
				String trelloPassword = String.valueOf(trelloSheet.getRow(i).getCell(1));
				String trelloCriteria = String.valueOf(trelloSheet.getRow(i).getCell(2));
				System.out.println(trelloUsername + " , "+trelloPassword);
				TrelloLoginPage.GoTo();
				LoginPage.LoginAs(trelloUsername,trelloPassword).Login();
				Thread.sleep(3000);
				
				Cell cell = trelloSheet.getRow(i).createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(UserAccountPage.IsAbleToLoginAs().equals("https://trello.com/")){
					if(trelloCriteria.equals("valid")){
						cell.setCellValue("Success: Successfully SignedOut");						
						Homepage.Logout();
						System.out.println("Successfully SignedOut");
					}
					else{
						cell.setCellValue("Failure: successfully SignedOut bt criteria 'invalid'");
						System.out.println("Failure: successfully SignedOut bt criteria 'invalid'");
					}
				}
				else{
					cell.setCellValue("Failure: invalid credentials");
					System.out.println("Failure: invalid credentials");
				}
				FileOutputStream fileOut = new FileOutputStream("TrelloLogin.xlsx");
				workbook.write(fileOut);
				fileOut.close();
				Browser.CloseBrowser();
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