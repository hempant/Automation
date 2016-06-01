package com.perficient.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginFactory {
	
	WebDriver driver;
	@FindBy(name="userName")
	WebElement username;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(name="login")
	WebElement login;
	
	public LoginFactory(WebDriver driver){
//		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement username(){
		return username;
	}
	
	public WebElement password(){
		return password;
	}
	
	public WebElement submit(){
		return login;
	}

}
