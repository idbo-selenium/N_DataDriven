package com.Data.DataDrivenExcel.Trello;

import org.openqa.selenium.By;

public class LoginInCommands {

	public String trelloUsername;
	public String trelloPassword;
	
	public LoginInCommands(String trelloUsername,String trelloPassword){
		this.trelloUsername = trelloUsername;
		this.trelloPassword = trelloPassword;
	}
	
	public void Login(){
		Browser.waitForPageUntilElementIsVisible(By.id("user"), 4000).sendKeys(trelloUsername);
		Browser.waitForPageUntilElementIsVisible(By.id("password"), 4000).sendKeys(trelloPassword);
		Browser.waitForPageUntilElementIsVisible(By.id("login"), 4000).click();
	}	
}
