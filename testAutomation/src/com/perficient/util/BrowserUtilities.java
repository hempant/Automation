package com.perficient.util;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserUtilities {
	
	private static WebDriver driver;
//	private static String browserName;
	private static File path = new File("resources/drivers");
	
	//derive the browser needed
	public static WebDriver getBrowser(String browserName){
		if(browserName.equalsIgnoreCase("FF")){
			driver = getFireoxBrowser();
		} else if(browserName.equalsIgnoreCase("IE")){
			driver = getInternetExplorerDriver();
		} else if(browserName.equalsIgnoreCase("Chrome")){
			driver = getChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	//load FF browser
	public static WebDriver getFireoxBrowser(){
		driver = new FirefoxDriver();
		return driver;
	}
	
	//load IE browser
	public static WebDriver getInternetExplorerDriver(){
		new DesiredCapabilities();
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setJavascriptEnabled(true);
		System.setProperty("webdriver.ie.driver", path.getAbsolutePath() + "\\IEDriverServer.exe");
		driver = new InternetExplorerDriver(capabilities);
		return driver;
	}
	
	//load Chrome browser
	public static WebDriver getChromeDriver(){
		new DesiredCapabilities();
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("--no-sandbox");
		capabilities.setCapability(ChromeOptions.CAPABILITY,options);
		System.setProperty("webdriver.chrome.driver", path.getAbsolutePath() + "\\chromedriver.exe");
		driver = new ChromeDriver(capabilities);
		return driver;
	}

}
