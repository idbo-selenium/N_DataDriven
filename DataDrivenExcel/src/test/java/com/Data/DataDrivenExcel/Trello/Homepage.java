package com.Data.DataDrivenExcel.Trello;

import org.openqa.selenium.By;

public class Homepage {

	public static void Logout() {
		// TODO Auto-generated method stub
		Browser.waitForPageUntilElementIsVisible(By.xpath("//*[@id='header']/div[4]/a[2]/span[2]"), 3000).click();
		Browser.waitForPageUntilElementIsVisible(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a"), 3000).click();
	}
}
