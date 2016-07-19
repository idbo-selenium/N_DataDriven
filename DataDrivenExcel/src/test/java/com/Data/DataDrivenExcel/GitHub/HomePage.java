package com.Data.DataDrivenExcel.GitHub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomePage {
	
	public static WebDriver driver;
	public static void GoTo() {
		//ChromeBrowser.Driver().navigate().to("https://github.com/login/");
		//driver = new FirefoxDriver();
		OpenBrowser();
		driver.get("https://github.com/login/");
	}
	
	public static void OpenBrowser(){
		//driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	public static void CloseBrowser(){
		driver.close();
	}
	
	public static WebDriver Driver() {
		return driver;
	}

	public static void Signout() {
		// TODO Auto-generated method stub
		driver.findElement(By.xpath("//*[@id='user-links']/li[3]/a/img")).click();
		driver.findElement(By.xpath("//*[@id='user-links']/li[3]/div/div/form/button")).click();
	}
}