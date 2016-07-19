package com.Data.DataDrivenExcel.Trello;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser {
	
	int maxSecond;
	public static WebDriver driver;
	public static void OpenBrowser(){
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	public static void CloseBrowser(){
		driver.close();
	}	
	
	public static WebElement waitForPageUntilElementIsVisible(By locator, int maxSecond){
		return (new WebDriverWait(Browser.driver, maxSecond))
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}