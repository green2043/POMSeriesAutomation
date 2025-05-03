package com.qa.appName.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.appName.factory.DriverFactory;
import com.qa.appName.pages.AccountsPage;
import com.qa.appName.pages.LoginPage;
import com.qa.appName.pages.ProductInfoPage;
import com.qa.appName.pages.RegistrationPage;
import com.qa.appName.pages.ResultsPage;
import com.qa.appName.pages.TOIPage;

//@Listeners(ChainTestListener.class)
public class BaseTest {

	DriverFactory df;
	WebDriver driver;
	protected LoginPage loginPage;
	public AccountsPage accPage;
	protected TOIPage toiPage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegistrationPage regPage;
	
	
	protected SoftAssert softAssert; 
	
	protected Properties prop;
		
	

	@BeforeTest
	public void setUp() {
		df = new DriverFactory();
		prop = df.initializeProp();
		driver = df.initDriver(prop); //THIS IS THREADLOCAL DRIVER NOW
//		driver = df.initDriver("chrome");
		loginPage = new LoginPage(driver);
		toiPage= new TOIPage(driver);
		softAssert=new SoftAssert();
		
		//THEN NAVIGATE TO OptionsManager CLASS, WHICH WILL CONFIGURES AND MANAGES BROWSER OPTIONS (LIKE HEADLESS AND INCOGNITO MODES)BASED ON SETTINGS FROM A PROVIDED PROPERTIES OBJECT.
		
	}
	
	@AfterTest
	public void tearDown()
	{ driver.quit();
	
	}
}
