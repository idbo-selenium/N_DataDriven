package com.Data.DataDrivenExcel.Trello;

public class LoginPage {

	public static LoginInCommands LoginAs(String trelloUsername,String trelloPassword){
		return new LoginInCommands(trelloUsername,trelloPassword);
	}
	
}
