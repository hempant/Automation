package com.perficient.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.perficient.core.TestDriver;

public class CommonUtilities {
	
	//create screen shots
	public static void getScreenshot(WebDriver driver, String Status) throws Exception {
		File dir = new File("");
		 if(!dir.exists()){
			 dir.mkdir();
		 }
		 File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 FileUtils.copyFile(srcfile,new File(new SimpleDateFormat("'resources/screenshots/" + TestDriver.parameters.get("tc_name") + "_" + Status + "_Screenshot_'yyyyMMdd_hhmmss'.png'").format(new Date())));
	}
	
	//type in input field
	//accepts element, text to type, clearing the field before typing true/false 
	public static void sendKeys(WebElement element, String text, boolean clear){
		if(clear){
			element.clear();
		}
			element.sendKeys(text);
	}
	
	//clear input field
	public static void clear(WebElement element){
		element.clear();
	}
	
	//get element text
	public static String getText(WebElement element){
		return element.getText();
	}
	
	
	//click on element
	public static void click(WebElement element){
		element.click();
	}
	
	//check if element is present by size
	public static boolean isElementPresent(WebElement element){
		try {
			if(element.getSize() != null )
				return true;
			else
				return false;
		} catch (NoSuchElementException e){
			return false;
		}
	}
	
	//check Element present by By
	public static boolean isElementPresent(WebDriver driver,By by){
		try {
			driver.findElement(by); 
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	//wait for page to load
	public static void pageLoadTimeout(WebDriver driver,int seconds){
		driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
	}
	
	public void report(String Status, String Description, String ExpectedResult, String ActualResult, String Exception, boolean isScreenshotNeeded) throws Exception{
		LinkedHashMap<String, String> resultStep = new LinkedHashMap<String,String>();
		resultStep.put("Status", Status.toUpperCase());
		resultStep.put("Description", Description);
		resultStep.put("ExpectedResult", ExpectedResult);
		resultStep.put("ActualResult", ActualResult);
		resultStep.put("Exception", Exception);
		resultStep.put("Browser", TestDriver.parameters.get("browser_version"));
		TestDriver.results.add(resultStep);
		if (isScreenshotNeeded) {
			getScreenshot(TestDriver.driver, Status.toUpperCase());
		}

	}
	
	public void recover(String Exception) throws Exception{
		LinkedHashMap<String, String> resultStep = new LinkedHashMap<String,String>();
		resultStep.put("Status", "ERROR");
		resultStep.put("Description", "An error occured during the execution");
		resultStep.put("Exception", Exception);
		TestDriver.results.add(resultStep);
		getScreenshot(TestDriver.driver, "ERROR");
	}
}
