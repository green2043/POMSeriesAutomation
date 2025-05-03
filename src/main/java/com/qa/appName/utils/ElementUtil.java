package com.qa.appName.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.appName.factory.DriverFactory;

public class ElementUtil {
	private WebDriver driver;
	private JavaScriptUtil javaScriptUtil;


	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		/*CONSTRUCTOR: IN THE 'ElementUtil' CLASS, THIS 'JavascriptUtil' OBJECT IS CREATED USING THE DRIVER. THE 'JavascriptUtil' CLASS HAS THE flash() METHOD, ALONG WITH OTHER METHODS, THAT ARE RESPONSIBLE FOR HIGHLIGHTING AN ELEMENT BY CHANGING ITS BACKGROUND COLOR AND PERFORMING OTHER ACTIONS AS WELL. */
		javaScriptUtil = new JavaScriptUtil(driver);
	}

	public WebElement getElement(By locator) {
		WebElement ele = driver.findElement(locator);
		
		// If the 'highlight' flag is set to "true", highlight the element by flashing it.
	    // This applies to all types of web elements in the page (input fields, buttons, links, etc.).
	    if (Boolean.parseBoolean(DriverFactory.highlight)) {
	        // Use the JavaScriptUtil class to flash (highlight) the element.
	        javaScriptUtil.flash(ele);
	    }
		
		return ele;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);

	}

	public void doSendKeys(By locator, String value) {
		WebElement element= getElement(locator);
		element.clear();
		getElement(locator).sendKeys(value);
	}

	public void doClearTextBox(By locator) {
		getElement(locator).clear();
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetDomAttributeValue(By locator, String attributeName) {
		return getElement(locator).getDomAttribute(attributeName);
	}

	public String doGetDomPropertyValue(By locator, String attributeName) {
		return getElement(locator).getDomProperty(attributeName);
	}

	public void doClickOnElements(By locator, String linkText) {

		List<WebElement> languageLinks = driver.findElements(locator);

		System.out.println("total language links:" + languageLinks.size());

		for (WebElement e : languageLinks) {
			String text = e.getText();
			System.out.println(text);

			if (text.contains(linkText)) {
				e.click();
				break;
			}
		}

	}

	public boolean IsSingleElementexist(By locator) {
		int actCount = getElements(locator).size();
		System.out.println("actual count of element ===" + actCount);
		if (actCount == 1) {
			return true;

		} else {
			return false;
		}

	}

	public boolean IsTwoElementexist(By locator) {
		int actCount = getElements(locator).size();
		System.out.println("actual count of element ===" + actCount);
		if (actCount > 1) {
			return true;

		} else {
			return false;
		}

	}

	public boolean IsMultipleElementexist(By locator, int expectedElementCount) {
		int actCount = getElements(locator).size();
		System.out.println("actual count of element ===" + actCount);
		if (actCount == expectedElementCount) {
			return true;

		} else {
			return false;
		}

	}

	public boolean doIsElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public int totalElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> elementList = getElements(locator);
		List<String> elementTextList = new ArrayList<String>();

		for (WebElement e : elementList) {
			String text = e.getText();
			elementTextList.add(text);
		}
		return elementTextList;

	}

	// ************* drop down utils -- select based drop downs
	// ***********************

	public void doSelectDropdownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropdownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectDropdownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDpwnBy(By locator, String value) {
		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = select.getOptions();

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);

			if (text.contains(value)) {
				e.click();
				break;

			}
		}

	}

	// ************ Action class ***************************************************
	public void moveToelementByActionClass(By locator)
	{
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
	}
	
	
	public void selectSubMenuByVisibleText(String htmlTag, String parentMenu, String chilDmenu)
			throws InterruptedException {
		By parentMenuLocator = By.xpath("//" + htmlTag + "[text()='" + parentMenu + "']");
		By childMenuLocator = By.xpath("//" + htmlTag + "[text()='" + chilDmenu + "']");
		WebElement parentMenuelement = driver.findElement(parentMenuLocator);
		// WebElement childMenuelement = driver.findElement(childMenuLocatorBy);

		Actions act = new Actions(driver);
		act.moveToElement(parentMenuelement).build().perform();
		Thread.sleep(2000);

		doClick(childMenuLocator);

	}

	public void doActionSendKeys(By locator, String value) {

		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();

	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();

	}

//***************************** WAIT UTILS **********************************************************************************************
	/**
	 * presenceOfElementLocated: An expectation for checking that an element is
	 * present on the DOM of a page. This does not necessarily mean that the element
	 * is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		/*
		 * presenceOfElementLocated: An expectation for checking that an element is
		 * present on the DOM of a page. This does not necessarily mean that the element
		 * is visible.
		 */
	}

	/**
	 * visibilityOfElementLocated: An expectation for checking that an element is
	 * present on the DOM of a page and visible. Visibility means that the element
	 * is not only displayed but also has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		/*
		 * visibilityOfElementLocated: An expectation for checking that an element is
		 * present on the DOM of a page and visible. Visibility means that the element
		 * is not only displayed but also has a height and width that is greater than 0.
		 */
	}

	public String waitForTitleContains(String titleFractionValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleFractionValue))) {
			return driver.getTitle();
		} else {
			System.out.println("expected value is not visible...");
			return null;
		}
	}

	public String waitForTitleIs(String titleCompleteValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleIs(titleCompleteValue))) {
			return driver.getTitle();
		} else {
			System.out.println("expected value is not visible...");
			return null;
		}
	}

	public String waitForUrlContains(String urlFractionValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlContains(urlFractionValue))) {
			return driver.getCurrentUrl();
		} else {
			System.out.println("expected value is not visible...");
			return null;
		}
	}

	public String waitForUrlIs(String urlCompleteValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlToBe(urlCompleteValue))) {
			return driver.getCurrentUrl();
		} else {
			System.out.println("expected value is not visible...");
			return null;
		}
	}

	// FluentWait
	public Alert waitForAlertPresentAndSwitchWithFluentWait(int timeout, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoAlertPresentException.class)
				.withMessage("Alert not fpound in this page...");
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	// WebDriverWait
	public Alert waitForAlertPresentAndSwitch(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForAlertPresentAndSwitch(timeOut).getText();

	}

	public void acceptAlert(int timeOut) {
		waitForAlertPresentAndSwitch(timeOut).accept();

	}

	public void dismissAlert(int timeOut) {
		waitForAlertPresentAndSwitch(timeOut).dismiss();

	}

	public void alertSendKeys(int timeOut, String value) {
		waitForAlertPresentAndSwitch(timeOut).sendKeys(value);

	}

	public void waitForFramePresentAndSwitch(int frameInndex, int timeOut)

	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameInndex));

	}

	public void waitForFramePresentAndSwitch(By frameLocator, int timeOut)

	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	public void waitForFramePresentAndSwitch(WebElement frameElement, int timeOut)

	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}

	public void waitForFramePresentAndSwitch(String nameOrID, int timeOut)

	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 *
	 * @param locator
	 * @param timeOut
	 * @return the list of WebElements once they are located
	 */

	public List<WebElement> waitForElementsVisibile(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	public List<WebElement> waitForElementsVisibile(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 *
	 * @param locator
	 * @param timeOut
	 * @return the list of WebElements once they are located
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 *
	 * @param locator
	 * @param timeOut
	 */

	public void WaitForElementtoBeClicked(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	public WebElement waitForElementtoBeVisible(By locator, int timeOut, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
		wait.ignoring(NoSuchElementException.class).ignoring(ElementNotInteractableException.class)
				.ignoring(TimeoutException.class).ignoring(StaleElementReferenceException.class)
				.withMessage("Element Not found on the page...");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

//	**********************Custom Wait********************************

	public WebElement retryingElement(By locator, int timeOut)

	{
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {

				element = getElement(locator);
				System.out.println("Element is found in attempt: " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("Element is not found in attempt: " + attempts + "  for  " + locator);
				TimeUtil.applyWait(500);
			}

			attempts++;

		}

		if (element == null) {

			System.out.println(
					"element is not found.....tried for : " + timeOut + " secs " + "with the interval of 1 sec");
			throw new FrameworkException("TimeOutException");

		}

		return element;

	}

	public WebElement retryingElement(By locator, int timeOut, int intervalTime)

	{
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {

				element = getElement(locator);
				System.out.println("Element is found in attempt: " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("Element is not found in attempt: " + attempts + "  for  " + locator);
				TimeUtil.applyWait(intervalTime);
			}

			attempts++;

		}

		if (element == null) {

			System.out.println(
					"element is not found.....tried for : " + timeOut + " secs " + "with the interval of 1 sec");
			throw new FrameworkException("TimeOutException");

		}

		return element;

	}
	//********************************ENCRYPT/DECRYPT*****************************************
	
	
	public  Properties prop;
	
	public  void readProp() {
		prop= new Properties();
		try {
			FileInputStream ip= new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(ip);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getEncryptedPassword() {
	    return prop.getProperty("password"); // Assuming your config file has the encrypted password under the "password" key
	}

	

}
