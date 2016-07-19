package com.Data.DataDrivenExcel.Trello;

public class UserAccountPage {

	public static String IsAbleToLoginAs(){
		System.out.println("CurrentURL : "+Browser.driver.getCurrentUrl());
		return Browser.driver.getCurrentUrl();
	}
	
}
