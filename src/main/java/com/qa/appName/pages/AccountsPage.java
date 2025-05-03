package com.qa.appName.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.ElementUtil;
import com.qa.appName.utils.TimeUtil;



//1-LoginPage-->2-AccountsPage-->3-ResultsPage

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("button[class='btn btn-default btn-lg']");
	private By amazonSearchIcon = By.id("nav-search-submit-button");
	private By logoutLink = By.linkText("Logout");
	private By amazonLogoutLink = By.linkText("Sign Out");
	private By accountSectionHeaders = By.cssSelector("div#content h2"); 
	private By amazonAccountAndLists = By.xpath("//span[@class='nav-line-2 ']") ;
	private By navigateToAccList = By.xpath("//a[@id='nav-link-accountList']");
	private By navigateToAccListItems = By.xpath("(//div[@id='nav-al-container']//ul)[2]//li"); //("//div[@id='nav-al-container']//ul//li")
	
	public AccountsPage(WebDriver driver)
	{
		this.driver=driver;
		elementUtil=new ElementUtil(driver);
		
	}
	
	public String getAccPageTitle() {
		return elementUtil.waitForTitleIs(AppConstants.ACCOUNT_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
//		return elementUtil.waitForTitleIs(AppConstants.AMAZON_ACCOUNT_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public String getAccPageURL() {
		return elementUtil.waitForUrlContains(AppConstants.ACCOUNT_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
//		return elementUtil.waitForUrlContains(AppConstants.AMAZON_ACCOUNT_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public boolean isSearchExist() {
		return elementUtil.waitForElementVisible(searchIcon,TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
//		return elementUtil.waitForElementVisible(amazonSearchIcon,TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	}
	public boolean isLogoutExist() {
		//elementUtil.moveToelementByActionClass(navigateToAccList);
		return elementUtil.waitForElementVisible(logoutLink,TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountPageSectionHeaders() {
		
		List<WebElement> secHeadersList= elementUtil.waitForElementsVisibile(accountSectionHeaders,TimeUtil.DEFAULT_TIME_OUT);
		List<String> secHeaderValList= new ArrayList<String>();
		
//		List<WebElement> secHeadersList= elementUtil.waitForElementsVisibile(accountSectionHeaders,TimeUtil.DEFAULT_TIME_OUT);
//		List<String> secHeaderValList= new ArrayList<String>();
		
		for(WebElement e: secHeadersList)
		{
			String text = e.getText();
			secHeaderValList.add(text);
			
		}
		
		return secHeaderValList;
	}

	public ResultsPage performSearch (String productName) {
		
		System.out.println("product search for : " + productName);
		
		if(isSearchExist()) {
			elementUtil.doClearTextBox(search);
			elementUtil.doSendKeys(search, productName);
			elementUtil.doClick(searchIcon);		
			return new ResultsPage(driver);
		}
		return null;
	
		
	}

}
