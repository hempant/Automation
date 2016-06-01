package com.perficient.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//Page object class for landing page
public class FlightFinderScreen {
	
	//Method to get the element for Passenger count
	public static WebElement passengerCountByName(WebDriver driver){
		return driver.findElement(By.name("passCount"));
	}
 	
	//Method to get the element for From Place
	public static WebElement departFromByName(WebDriver driver){
		return driver.findElement(By.name("fromPort"));
	}
	
	
	//Method to get the element for from month
	public static WebElement departMonthByName(WebDriver driver){
		return driver.findElement(By.name("fromMonth"));
	}

	//Method to get the element for form day
	public static WebElement departDateByName(WebDriver driver){
		return driver.findElement(By.name("fromDay"));
	}
	
	//Method to get the element for to Place
	public static WebElement arrivingInByName(WebDriver driver){
		return driver.findElement(By.name("toPort"));
	}
	
	//Method to get the element for to Month
	public static WebElement arriveMonthByName(WebDriver driver){
		return driver.findElement(By.name("toMonth"));
	}

	//Method to get the element for to Date
	public static WebElement arriveDateByName(WebDriver driver){
		return driver.findElement(By.name("toDay"));
	}
}
