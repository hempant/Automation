package com.perficient.test;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.perficient.pageObjects.LoginFactory;
import com.perficient.pageObjects.LoginScreen;
import com.perficient.util.CommonUtilities;

public class SampleTC extends CommonUtilities{

	public void SampleTestMethod(WebDriver driver, LinkedHashMap<String, String> parameters) throws Exception{
		
		LoginScreen lg = new LoginScreen(driver);
		LoginFactory lf = new LoginFactory(driver);
		try {
			driver.get("http://newtours.demoaut.com");
			sendKeys(lf.username(), "test",true);
			sendKeys(lf.password(), "test",true);
			click(lf.submit());
			pageLoadTimeout(driver, 3);
			driver.findElement(By.linkText("SIGN-OFF")).click();
			report("Pass", "Sample Checkpoint", "The checkpoint should be passed", "The checkpoint is successfully passed", "", true);
		} catch(Exception e){
			recover(e.toString());
		}
		
	}

}
