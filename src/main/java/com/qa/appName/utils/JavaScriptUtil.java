package com.qa.appName.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	
	public JavaScriptUtil (WebDriver driver)
	{
		this.driver =driver;
	}
	
	
//==============================================================================================================================	
	// Method to flash (highlight) a web element by changing its background color repeatedly
	public void flash(WebElement element) {
	    
	    // Step 1: Store the current (original) background color of the element
	    String bgcolor = element.getCssValue("backgroundColor");
	    
	 // Step 2:This is the flash logic, which will flash the element the number of times specified by the loop's repetition count (i < number)
	    for (int i = 0; i < 5; i++) {
	        
	        // Step 3a: Change the element's background color to a highlight color
	        changeColor("rgb(0,200,0)", element); // Green Flash (You can choose other colors listed below)
	        
	        // Step 3b: Change the background color back to the original color
	        changeColor(bgcolor, element); // Restore original background
	    }
	}

	/*
	 * 🎨 Available Color Codes for Flashing:
	 *
	 * Green        -> "rgb(0, 200, 0)"       // Bright Green (default)
	 * Red          -> "rgb(255, 0, 0)"        // Bright Red
	 * Blue         -> "rgb(0, 0, 255)"        // Bright Blue
	 * Yellow       -> "rgb(255, 255, 0)"      // Bright Yellow
	 * Orange       -> "rgb(255, 165, 0)"      // Bright Orange
	 * Purple       -> "rgb(128, 0, 128)"      // Deep Purple
	 * Pink         -> "rgb(255, 192, 203)"    // Soft Pink
	 * Light Blue   -> "rgb(173, 216, 230)"    // Light Sky Blue
	 * Cyan         -> "rgb(0, 255, 255)"      // Aqua Blue
	 * Black        -> "rgb(0, 0, 0)"          // Black
	 * White        -> "rgb(255, 255, 255)"    // White
	 *
	 */
//==========================================================================================================================
	
	

	// Method to change the background color of a web element temporarily
	private void changeColor(String color, WebElement element) {
	    
	    // Step 1: Create a JavaScriptExecutor from the driver
	    JavascriptExecutor js = ((JavascriptExecutor) driver);
	    
	    // Step 2: Use JavaScript to set the background color of the given web element
	    js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

	    // Step 3: Small pause to make the color change visible
	    try {
	        Thread.sleep(20); // Pause for 20 milliseconds
	    } catch (InterruptedException e) {
	        // InterruptedException handling (usually happens if thread is interrupted during sleep)
	        // Here, simply ignoring the exception
	    }
	}
//===============================================================================================================================

	public String getTitleByJS() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return document.title;").toString();
	}

	public void goBackByJS() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("history.go(-1)");
	}
	
	public void goForwardByJS() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("history.go(1)");
	}
	
	public void refreshBrowserByJS() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("history.go(0)");
	}

	public void generateAlert(String message) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert('" + message + "')");
	}
	
	public void generateConfirmPopUp(String message) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("confirm('" + message + "')");
	}

	public String getPageInnerText() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	public void clickElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void sendKeysUsingWithId(String id, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
						  //document.getElementById('input-email').value ='tom@gmail.com'
	}

	public void scrollPageDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollPageDown(String height) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}

	public void scrollPageUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void drawBorder(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

}
