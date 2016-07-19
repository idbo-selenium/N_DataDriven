package com.Data.DataDrivenExcel.Mercury;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {

	public static WebDriver driver;
	public static void OpenBrowser(){
		driver = new FirefoxDriver();		
	}
	public static void CloseBrowser(){
		driver.close();
	}
}
