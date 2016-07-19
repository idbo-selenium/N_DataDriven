package com.Data.DataDrivenExcel.GitHub;

import org.openqa.selenium.By;

public class SignInCommands {

	public String githubUsername;
	public String githubPassword;
	
	public SignInCommands(String githubUsername){
		this.githubUsername = githubUsername;
	}
	public SignInCommands WithPassword(String githubPassword){
		this.githubPassword = githubPassword;
		return this;
	}
	public void Login() {
		HomePage.driver.findElement(By.xpath("//*[@id='login_field']")).sendKeys(githubUsername);
		HomePage.driver.findElement(By.xpath("//*[@id='password']")).sendKeys(githubPassword);
		HomePage.driver.findElement(By.xpath("//*[@id='login']/form/div[4]/input[3]")).click();
	}	
}