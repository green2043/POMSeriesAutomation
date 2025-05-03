package com.qa.appName.pages;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.ElementUtil;
import com.qa.appName.utils.TimeUtil;

public class TOIPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By toiLogo= By.xpath("//div[@class='contentwrapper']//a[@aria-label='TOI Logo']");
	private By tech= By.xpath("//a[@aria-label='Tech']");
	private By technologyLogo= By.xpath("//a[normalize-space()='Technology']");
	
	public TOIPage (WebDriver driver)
	{
		this.driver=driver;
		elementUtil=new ElementUtil(driver);
	}
	
	public String getTOIPageTitle()
	{
		return elementUtil.waitForTitleContains(AppConstants.TOI_FRACTION_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}
	public String getTOIPageURL()
	{
		return elementUtil.waitForUrlIs(AppConstants.TOI_PAGE_URL, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public boolean IsTOILogoExist() {
		
	return elementUtil.waitForElementVisible(toiLogo, TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
		
	}
	
	public String getTOITechPageURL()
	{
		elementUtil.doClick(tech);
		return elementUtil.waitForUrlContains(AppConstants.TOI_TECH_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
		
	
	}

	
	public boolean isTechLogoExist() 
	{
		
		return elementUtil.waitForElementVisible(technologyLogo,TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	
		
	}
	
	

	 
	
	
}
