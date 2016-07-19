package com.Data.DataDrivenExcel.Trello;

public class TrelloLoginPage {

	public static void GoTo(){
		Browser.OpenBrowser();
		Browser.driver.navigate().to("https://trello.com/login");
	}
	
}
