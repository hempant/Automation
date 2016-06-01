package com.perficient.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//Page object class for Login screen
public class LoginScreen {
	
//	private static WebElement element = null;
	
	public WebDriver driver;
	
	public LoginScreen(WebDriver driver){
		this.driver = driver;
	}
	
	//Method to get the element for username
	public WebElement userName(){
		return driver.findElement(By.name("userName"));
	}
 
	
	//Method to get the element for Password
	public WebElement password(){
		return driver.findElement(By.name("password"));
	}
	
	//Method to get the element for login button
	public WebElement loginButton(){
		return driver.findElement(By.name("login"));
	}
}
