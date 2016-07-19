package com.Data.DataDrivenExcel.Mercury;

import org.openqa.selenium.By;

public class Homepage {

	public static void GoTo(){
		Browser.driver.get("http://newtours.demoaut.com/");
	}

	public static void Signout() {
		// TODO Auto-generated method stub
		Browser.driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a")).click();
	}
	
}
