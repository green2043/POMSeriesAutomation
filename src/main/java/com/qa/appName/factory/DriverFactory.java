package com.qa.appName.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v133.page.model.Screenshot;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.tracing.Propagator;

public class DriverFactory {
	
	public Properties prop;

	public WebDriver driver; // because it will supply to everywhere
	public OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> threadLocalDriver=new ThreadLocal();
	
	/*
	 WHAT IS THREADLOCAL?
ThreadLocal is a Java class that provides thread-local variables. Each thread accessing a ThreadLocal variable has its own independent copy of that variable. This ensures that one thread doesn't interfere with another.

WHY USE THREADLOCAL IN SELENIUM?
In Selenium automation, multiple tests can run in parallel (for example, with frameworks like TestNG running tests concurrently). If you share a single WebDriver instance across multiple tests, they might interfere with each other. For instance:

One test might close the browser.
Another test might perform actions on a different browser instance.
This causes flaky tests and unreliable results.
ThreadLocal solves this problem by providing separate WebDriver instances for each thread (test) running in parallel.
	 */
	

	
	/*THIS STATIC VARIABLE IS USED TO CONTROL WHETHER ELEMENTS SHOULD BE HIGHLIGHTED (FLASHED) DURING THE TEST EXECUTION. BY MAKING IT STATIC, THE VALUE OF HIGHLIGHT IS SHARED ACROSS ALL INSTANCES OF THE DRIVERFACTORY CLASS. THIS ALLOWS THE OPTION TO BE SET GLOBALLY FOR THE ENTIRE TEST SUITE.*/
	public static String highlight;

/*
	public WebDriver initDriver(String browserName) {
		System.out.println("Broswer name is : " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();

		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}else {
			System.out.println("Please pass the right browser name..."+ browserName);
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
//		driver.get("https://www.amazon.com/gp/sign-in.html");
//		driver.get("https://timesofindia.indiatimes.com/");
		
		
		return driver;
	}
	*/
	
	/*
	
	public WebDriver initDriver(Properties prop) {
	    // Get the browser name from the properties file and remove extra spaces.
	    String browserName = prop.getProperty("browser").trim();
	    
	    // Print the browser name for debugging purposes.
	    System.out.println("Browser name is: " + browserName);
	    
	 // Set the 'highlight' flag from the properties file to control whether elements should be highlighted during the test.
	    highlight = prop.getProperty("highlight"); //Navigate to ElementUtil class
	    
	    optionsManager = new OptionsManager(prop);
	    
	    // Initialize the WebDriver based on the browser name provided.
	    if (browserName.equalsIgnoreCase("chrome")) {
	        // Set up Chrome browser.
	        driver = new ChromeDriver(optionsManager.getChromeOptions());
	        
	    } else if (browserName.equalsIgnoreCase("edge")) {
	        // Set up Edge browser.
	        driver = new EdgeDriver();
	        
	    } else if (browserName.equalsIgnoreCase("firefox")) {
	        // Set up Firefox browser.
	        driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
	        
	    } else {
	        // If an invalid browser name is provided, show an error message.
	        System.out.println("Please pass the right browser name..." + browserName);
	    }
	    
	    //SRP -- Single responsibility principle - each page should responsible for specific feature
	    
	    // Delete all cookies to ensure a clean session.
	    driver.manage().deleteAllCookies();
	    // Maximize the browser window for better visibility.
	    driver.manage().window().maximize();
	    // Open the URL specified in the properties file.
	    
	 // Uncomment one of the URLs below to hardcode a URL instead of using the properties file.
	   // driver.get(prop.getProperty("url"));
	    driver.get(prop.getProperty("urlForNaveenSApp"));
	    
	    // driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
	    // driver.get("https://www.amazon.com/gp/sign-in.html");
	    // driver.get("https://timesofindia.indiatimes.com/");
	    
	    // Return the initialized WebDriver.
	    return driver;
	}
	
	*/
	
	public WebDriver initDriver(Properties prop) {
		// Get the browser name from the properties file and remove extra spaces.
		String browserName = prop.getProperty("browser").trim();
		
		// Print the browser name for debugging purposes.
		System.out.println("Browser name is: " + browserName);
		
		// Set the 'highlight' flag from the properties file to control whether elements should be highlighted during the test.
		highlight = prop.getProperty("highlight"); //Navigate to ElementUtil class
		
		optionsManager = new OptionsManager(prop);
		
		// Initialize the WebDriver based on the browser name provided.
		if (browserName.equalsIgnoreCase("chrome")) {
			// Set up Chrome browser.
		//	driver = new ChromeDriver(optionsManager.getChromeOptions());
			threadLocalDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Set up Edge browser.
		//	driver = new EdgeDriver();
			threadLocalDriver.set(new EdgeDriver());	
			
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// Set up Firefox browser.
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			threadLocalDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			
		} else {
			// If an invalid browser name is provided, show an error message.
			System.out.println("Please pass the right browser name..." + browserName);
		}
		
		//SRP -- Single responsibility principle - each page should responsible for specific feature
		
		// Delete all cookies to ensure a clean session.
		getDriver().manage().deleteAllCookies();
		// Maximize the browser window for better visibility.
		getDriver().manage().window().maximize();
		// Open the URL specified in the properties file.
		
		// Uncomment one of the URLs below to hardcode a URL instead of using the properties file.
		// driver.get(prop.getProperty("url"));
		getDriver().get(prop.getProperty("url"));
		
		// getDriver().get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		// getDriver().get("https://www.amazon.com/gp/sign-in.html");
		// getDriver().get("https://timesofindia.indiatimes.com/");
		
		// Return the initialized WebDriver.
		return getDriver();
	}
	
	
	//GET THE LOCAL COPY OF THE DRIVER
	public static WebDriver getDriver() {
		return threadLocalDriver.get();
	}
	

	public Properties initializeProp() {
	    // Create a new Properties object to hold configuration properties.
	    prop = new Properties();
	    FileInputStream ip =null;
	    
	    //mvn clean install -Denv = "prod"
	    //mvn clean install
	    
	    String envName= System.getProperty("env");
	    System.out.println("------>Running test cases on environment-----> " + envName);
	    
	    if(envName==null) {
	    	System.out.println("No env is given...hence running it on the QA env....");
	    	try {
	    		ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
	    		
			} catch (FileNotFoundException e) 
	    	{
				e.printStackTrace();
			}
	
	    } else {
	    	
	    try {
	    	switch (envName.toLowerCase()) {
			case "qa": 
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev": 
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage": 
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "uat": 
				ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "prod": 
				ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
				break;
				
				default:
					System.out.println("Please print the right env name....." + envName);
					break;
			
	    	}
	    	
	    }
	    	catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    
	    
//	    try {
//	        // Create a FileInputStream object to connect to the properties file.
//	        FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
//	        // Load the properties from the file into the Properties object.
//	        prop.load(ip);
//	    } catch (FileNotFoundException e) {
//	        // Handle the case where the properties file is not found.
//	        e.printStackTrace();
//	    } catch (IOException e) {
//	        // Handle other input/output exceptions.
//	        e.printStackTrace();
//	    }
	    
	    //System.out.println("Loaded properties: " + prop);
	    
	    try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    
	    // Return the loaded Properties object.
	    return prop;
	}
	
	
	/*
	 * take screenshot
	 * */

	public static String getScreenshot() {

	    // 'File' is a CLASS from java.io — used to handle file objects
	    // 'TakesScreenshot' is an INTERFACE from org.openqa.selenium — used to capture screenshots
	    // 'getDriver()' is a USER-DEFINED METHOD — returns WebDriver instance (likely ThreadLocal)
	    // 'getScreenshotAs()' is a METHOD from the TakesScreenshot INTERFACE
	    // 'OutputType.FILE' is an ENUM CONSTANT from OutputType ENUM — tells Selenium to return screenshot as a File
	    File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

	    // 'System' is a FINAL CLASS in java.lang — used to access system properties
	    // 'getProperty()' is a STATIC METHOD in System CLASS — returns system property values
	    // 'currentTimeMillis()' is a STATIC METHOD in System CLASS — returns current time in milliseconds
	    // 'path' is a STRING VARIABLE that stores the full path where the screenshot will be saved
	    String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";

	    // 'new' is a JAVA KEYWORD — used to create new objects
	    // 'destination' is a VARIABLE of type File — it points to the file to which screenshot will be saved
	    File destination = new File(path);

	    try {
	        // 'FileHandler' is a CLASS from org.openqa.selenium.io — used to handle file operations
	        // 'copy()' is a STATIC METHOD in FileHandler CLASS — copies file from source to destination
	        FileHandler.copy(srcFile, destination);
	    } catch (IOException e) {
	        // 'IOException' is a CLASS from java.io — handles input/output exceptions
	        // 'e' is an OBJECT of IOException
	        // 'printStackTrace()' is a METHOD in Throwable CLASS — prints the exception stack trace
	        e.printStackTrace();
	    }

	    return path;
	}

	

	//THEN NAVIGATE TO BaseTest.java WHERE THIS METHOD WILL INITIALIZE AND RETURN PROPERTIES, WHICH ARE THEN STORED IN THE PROP VARIABLE.

}
