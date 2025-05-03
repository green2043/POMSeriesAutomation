package com.qa.appName.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.appName.factory.DriverFactory;

//DON'T TOUCH THIS, DON'T CHANGE ANYHTING HERE
/*IT WON"T GIVE HTML REPORT DIRECTLY, IT GIVES WEB REPORT
FOR THAT GO TO THIS LINK AND INSTALL ALLURE IN YOUR WINDOWWS SYSTEM ACCORDINGLY:
https://allurereport.org/docs/install-for-windows/

NOTE: USE POWERSHELL, NOT CMD PROMPT, IF SCOOP NOT INSTALLED INSTALL SCOOP FIRST
EXAMPLE:
PS C:\Users\satya> Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
PS C:\Users\satya> irm get.scoop.sh | iex
Initializing...
Downloading...
Extracting...
Creating shim...
Adding ~\scoop\shims to your path.
Scoop was installed successfully!
Type 'scoop help' for instructions.
PS C:\Users\satya>scoop install allure
Installing 'allure' (2.34.0) [64bit] from 'main' bucket
allure-2.34.0.zip (23.7 MB) [==========================================================================================================================================================================] 100%
Checking hash of allure-2.34.0.zip ... ok.
Extracting allure-2.34.0.zip ... done.
Linking ~\scoop\apps\allure\current => ~\scoop\apps\allure\2.34.0
Creating shim for 'allure'.
'allure' (2.34.0) was installed successfully!
PS C:\Users\satya> allure --version
2.34.0
PS C:\Users\satya> scoop update allure
allure: 2.34.0 (latest version)
Latest versions for all apps are installed! For more information try 'scoop status'
PS C:\Users\satya>
*/
//============================================================================================================================

//'TestAllureListener' is the NAME of the class — custom listener for Allure reporting
//'implements' is a JAVA KEYWORD — used for implementing interfaces
//'ITestListener' is an INTERFACE from TestNG — provides listener methods for test events
public class TestAllureListener implements ITestListener {

 // 'private' is an ACCESS MODIFIER — this method is accessible only within this class
 // 'static' means this method belongs to the class, not to any specific object
 // 'String' is the RETURN TYPE (class from java.lang) — this method returns the name of the test method
 // 'getTestMethodName' is the METHOD NAME
 // '(ITestResult iTestResult)' is the METHOD PARAMETER — 'ITestResult' is an INTERFACE from TestNG holding test method info
 private static String getTestMethodName(ITestResult iTestResult) {
     // 'getMethod()' returns the method object of the test
     // 'getConstructorOrMethod()' gives access to the reflective method
     // 'getName()' returns the name of the method
     return iTestResult.getMethod().getConstructorOrMethod().getName();
 }

 // '@Attachment' is an ALURE ANNOTATION to attach screenshot in the report
 // 'value = "Page screenshot"' is the ATTACHMENT NAME shown in the report
 // 'type = "image/png"' specifies the MIME type of the attachment
 @Attachment(value = "Page screenshot", type = "image/png")
 // 'public' allows this method to be called from other classes
 // 'byte[]' is the RETURN TYPE — screenshot is returned as a byte array
 // 'saveScreenshotPNG' is the METHOD NAME
 // '(WebDriver driver)' is the PARAMETER — Selenium WebDriver
 public byte[] saveScreenshotPNG(WebDriver driver) {
     // Cast the driver to 'TakesScreenshot' INTERFACE to capture screenshot
     // 'getScreenshotAs(OutputType.BYTES)' returns screenshot as bytes
     return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
 }

 // Attaching plain text to Allure report
 // '{0}' is a PLACEHOLDER for the method argument
 @Attachment(value = "{0}", type = "text/plain")
 // 'public static' — accessible without an object
 // 'String' — method returns a string
 // 'saveTextLog' — method name
 public static String saveTextLog(String message) {
     return message; // returning the text message
 }

 // Attaching HTML content to Allure report
 @Attachment(value = "{0}", type = "text/html")
 public static String attachHtml(String html) {
     return html; // returning HTML string
 }

 // This method runs when a test suite starts
 @Override // annotation to override interface method
 public void onStart(ITestContext iTestContext) {
     // 'iTestContext.getName()' returns the name of the current test context (suite)
     System.out.println("I am in onStart method " + iTestContext.getName());
     // iTestContext.setAttribute(...) — can be used to share WebDriver object
 }

 // This method runs when a test suite finishes
 @Override
 public void onFinish(ITestContext iTestContext) {
     System.out.println("I am in onFinish method " + iTestContext.getName());
 }

 // This method runs just before each test method starts
 @Override
 public void onTestStart(ITestResult iTestResult) {
     System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
 }

 // This method runs when a test passes
 @Override
 public void onTestSuccess(ITestResult iTestResult) {
     System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
 }

 // This method runs when a test fails
 @Override
 public void onTestFailure(ITestResult iTestResult) {
     System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");

     // 'getInstance()' returns the instance of the test class
     Object testClass = iTestResult.getInstance();

     // if current thread's WebDriver instance is of type WebDriver
     if (DriverFactory.getDriver() instanceof WebDriver) {
         System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
         // Call the method to capture and attach screenshot to Allure
         saveScreenshotPNG(DriverFactory.getDriver());
     }

     // Attach a text log to Allure report
     saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
 }

 // This method runs when a test is skipped
 @Override
 public void onTestSkipped(ITestResult iTestResult) {
     System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
 }

 // This method runs when test fails but within allowed success ratio
 @Override
 public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
     System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
 }

}
