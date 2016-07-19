package com.Data.DataDrivenExcel.Mercury;

import org.openqa.selenium.By;

public class SignInCommands {

	public String username;
	public String password;
	public SignInCommands(String username) {
		// TODO Auto-generated constructor stub
		this.username = username;
	}
	public SignInCommands WithPassword(String password) {
		// TODO Auto-generated method stub
		this.password = password;
		return this;
	}
	public void Login() {
		// TODO Auto-generated method stub
		Browser.driver.findElement(By.name("userName")).sendKeys(username);
		Browser.driver.findElement(By.name("password")).sendKeys(password);
		Browser.driver.findElement(By.name("login")).click();
	}
}
