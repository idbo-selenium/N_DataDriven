package com.Data.DataDrivenExcel.GitHub;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {

	public static WebDriver driver;
	
	public static void OpenBrowser(){
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();		
	}
	
	public static void CloseBrowser(){
		driver.close();
	}
	
	public static WebDriver Driver() {
		return driver;
	}	
}
