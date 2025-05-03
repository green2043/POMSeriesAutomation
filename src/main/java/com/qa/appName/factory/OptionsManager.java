package com.qa.appName.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

    // Declare a Properties object to hold config settings (like headless, incognito)
    private Properties prop;
    
    // Declare ChromeOptions object to customize Chrome browser behavior
    private ChromeOptions co;
    
    // Declare FirefoxOptions object to customize Firefox browser behavior
    private FirefoxOptions fo;
    
    // Constructor of OptionsManager class to initialize 'prop' variable
    public OptionsManager(Properties prop) {
        this.prop = prop; // Assign the passed Properties object to the class variable
    }
    
    // Method to set Chrome browser options based on configuration
    public ChromeOptions getChromeOptions() {
        
        // Initialize ChromeOptions object
        co = new ChromeOptions();
        
        // Check if 'headless' property is set to true in config file
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
        	
        	System.out.println("Running the test in headlses mode......");
            // If yes, add '--headless' argument to Chrome (browser will run without GUI)
            co.addArguments("--headless");
        }
        
        // Check if 'incognito' property is set to true in config file
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
        	System.out.println("Running the test in incognito mode......");
            // If yes, add '--incognito' argument to Chrome (browser will run in incognito mode)
            co.addArguments("--incognito");
        }
        
        // Return the configured ChromeOptions object
        return co;
    }

    // Method to set Firefox browser options based on configuration
    public FirefoxOptions getFirefoxOptions() {
        
        // Initialize FirefoxOptions object
        fo = new FirefoxOptions();
        
        // Check if 'headless' property is set to true in config file
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            // If yes, add '--headless' argument to Firefox (browser will run without GUI)
            fo.addArguments("--headless");
        }
        
        // Check if 'incognito' property is set to true in config file
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            // If yes, add '--incognito' argument to Firefox (browser will run in private mode)
            fo.addArguments("--incognito");
        }
        
        // Return the configured FirefoxOptions object
        return fo;
    }
    
    //THEN NAVIGATE TO 
}
