package com.qa.appName.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.ElementUtil;
import com.qa.appName.utils.TimeUtil;

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@value='1' and @type='radio']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@value='0' and @type='radio']");

	private By registerSuccessMesg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public boolean registerAuser(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {
		
		eleUtil.waitForElementVisible(this.firstName, TimeUtil.DEFAULT_TIME_OUT).clear();
		eleUtil.waitForElementVisible(this.firstName, TimeUtil.DEFAULT_TIME_OUT).sendKeys(firstName);
		
		eleUtil.doClearTextBox(this.lastName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doClearTextBox(this.email);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doClearTextBox(this.telephone);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doClearTextBox(this.password);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doClearTextBox(this.confirmpassword);
		eleUtil.doSendKeys(this.confirmpassword, password);
		
		if(subscribe.equalsIgnoreCase("yes"))
		{
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String SuccessMessage= eleUtil.waitForElementVisible(registerSuccessMesg, TimeUtil.DEFAULT_TIME_OUT).getText();
		System.out.println(SuccessMessage);
		
		if(SuccessMessage.contains(AppConstants.ACCOUNT_REGISTER_SUCCESS_MESSAGE))
		{
			eleUtil.doClick(logoutLink);
			eleUtil.waitForElementVisible(registerLink,TimeUtil.DEFAULT_TIME_OUT).click();
			return true;
		}
		else {
			
			eleUtil.doClearTextBox(registerLink);
		}
			return false;
		
		
		

	}

}
