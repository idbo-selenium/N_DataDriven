package com.Data.DataDrivenExcel.Gmail;

import org.openqa.selenium.By;

public class HomePage {

	public static void GoTo(){
		Browser.driver.get("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1");
	}

	public static void LoginAs(String username) {
		// TODO Auto-generated method stub
		Browser.driver.findElement(By.id("Email")).sendKeys(username);
		Browser.driver.findElement(By.id("next")).click();
	}

	public static void WithPassword(String password) {
		// TODO Auto-generated method stub
		Browser.driver.findElement(By.id("Passwd")).sendKeys(password);
		Browser.driver.findElement(By.id("signIn")).click();
	}

	public static void Signout() {
		// TODO Auto-generated method stub		
		Browser.driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/a/span")).click();
		Browser.driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[4]/div[2]/div[3]/div[2]/a")).click();
		
	}	
}
