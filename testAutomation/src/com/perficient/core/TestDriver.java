/**
 * TestNG class which gets called for every test case. 
 * It iterates through the worksheet of test data, runs the services and writes a corresponding output sheet.
 */

package com.perficient.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.perficient.util.BrowserUtilities;
import com.perficient.util.CommonUtilities;
import com.perficient.util.ExcelUtil;

public class TestDriver extends CommonUtilities{

	ExcelUtil excelUtil = new ExcelUtil();
	public static LinkedHashMap<String, String> parameters;
	public static String outputFileName;
	public static LinkedList<LinkedHashMap<String, String>> results = new LinkedList<LinkedHashMap<String, String>>();
	public static WebDriver driver;
	
	@BeforeSuite
	public void suiteSetup() {
		outputFileName = excelUtil.setupOutput();
	}

	@BeforeClass(alwaysRun = true)
	@Parameters({"tc_name","test_method"})
	public void classSetup(String TCName, String TestMethod) {
		String TestClass = TestMethod.substring(0, TestMethod.lastIndexOf("."));
		TestMethod = TestMethod.substring(TestMethod.lastIndexOf(".")+1);
		parameters = excelUtil.getInputData(TCName);
		parameters.put("tc_name", TCName);
		parameters.put("test_class", TestClass);
		parameters.put("test_method", TestMethod);		
	}

	@Test(dataProvider = "data-provider")
	public void testMethod(String browser) throws Exception {
	    try {
	    	driver = BrowserUtilities.getBrowser(browser);
			Capabilities cap = ((RemoteWebDriver) TestDriver.driver).getCapabilities();
			parameters.put("browser_version", cap.getBrowserName().toString() + " " + cap.getVersion().toString());
			
			Class<?> objClass = Class.forName(parameters.get("test_class"));
			Object insClass = objClass.newInstance(); 
		    Method objMethod = insClass.getClass().getMethod(parameters.get("test_method"), WebDriver.class, LinkedHashMap.class);
		    objMethod.invoke(insClass, driver, parameters);
	    } catch (InvocationTargetException e) {
	    	recover(e.toString());
	    } catch (Exception e) {
	        recover(e.toString());
	    }
	}
	
	@AfterMethod
	public void tearDown(){
	    if (driver != null)
	        driver.quit();
	}

	@DataProvider(name = "data-provider")
	public Object[][] dataProvider(ITestContext context) {
		String[] browsersArray = context.getCurrentXmlTest().getParameter("browsers").split(",");
		Object[][] obj = new Object[browsersArray.length][1];
		for (int i = 0; i < browsersArray.length; i++) {
			obj[i][0] = browsersArray[i];
		}
		return obj;
	}

	@AfterClass
	public void wrapup(){
		excelUtil.writeOutput(outputFileName, parameters, results);
		results.clear();
		parameters.clear();
	}

}
