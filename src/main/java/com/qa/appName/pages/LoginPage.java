package com.qa.appName.pages;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.ElementUtil;
import com.qa.appName.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	//Page class shouldn't have any kid of assertion, only behavior of login page
	//MARTIN FLOWER- invented POM - "https://martinfowler.com/bliki/PageObject.html"
	//Simon Stewart:invented selenium
	
	//1-LoginPage-->2-AccountsPage
	
	private WebDriver driver; //because every page has it's own driver
	public ElementUtil elementUtil;
	
	//1. private By locators:
	private By emailId = By.id("input-email");
	private By amazonUid = By.id("ap_email");
	private By continueBtnInUid=By.id("continue");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By amazonignInBtn = By.id("signInSubmit");
	private By password = By.id("input-password");
	private By amazonPassword = By.id("ap_password");
	private By forgotPasswordLink = By.linkText("Forgotten Password");
	private By amazonForgotPasswordLink = By.linkText("Forgot password?");
	private By amazonSignInText = By.xpath("//h1[contains(text(),'Sign in')]");
	private static final String SECRET_KEY = "FWautomtion12345";
	//private static final String SECRET_KEY_FOR_AMAZON = "AmazonTest123456";
	private String  demo = "demo for git";
	
	private By resterLink = By.linkText("Register");
	
	
	//2. Page constructor:
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		elementUtil=new ElementUtil(driver);
	}
	
	//3. page actions:
	@Step("getting login page titel...")
	public String getLoginPageTitle()
	{
		return elementUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
//		return elementUtil.waitForTitleIs(AppConstants.AMAZON_LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	@Step("getting login page url")
	public String getLoginPageUrl()
	{
		return elementUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
//		return elementUtil.waitForUrlContains(AppConstants.AMAZON_LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	@Step("cecking forgot pwd link exist")
	public boolean isForgotPwdLinkExist() 
	{
		return elementUtil.doIsElementDisplayed(forgotPasswordLink);
//		return elementUtil.doIsElementDisplayed(amazonForgotPasswordLink);
	}
	
	
	public boolean isAmazonSignInTextExist() 
	{
		return elementUtil.doIsElementDisplayed(amazonSignInText);
	}
	
	
	
	
	public String encrypt(String stringToEncrypt) {

		try {
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String decrypt(String stringToDecrypt) {
		
		try {
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(stringToDecrypt)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@Step("login with encrypted username: {0} and password: {1}")
	public AccountsPage doLogin(String uname, String pwd) 
	{
		//System.out.println("Creds are : " + uname + ":" + pwd);
		 elementUtil.readProp();
		
		 String encryptedPassword = elementUtil.getEncryptedPassword();  // Get the encrypted password
	     String decryptedPassword = decrypt(encryptedPassword); // Decrypt the password
	     pwd = decryptedPassword;

		
		elementUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_TIME_OUT).clear();
		elementUtil.doSendKeys(emailId, uname);
		elementUtil.doClearTextBox(password);
	    elementUtil.doSendKeys(password, pwd);
		//elementUtil.doSendKeys(password, decryptedPassword);
		elementUtil.doClick(loginBtn);
		
//		elementUtil.readProp();
//		
//		elementUtil.waitForElementVisible(amazonUid, TimeUtil.DEFAULT_TIME_OUT).clear();
//		elementUtil.doSendKeys(amazonUid, uname);
//		elementUtil.doClick(continueBtnInUid);
//		elementUtil.doClearTextBox(amazonPassword);
//		elementUtil.doSendKeys(amazonPassword, pwd);
//		//elementUtil.doSendKeys(password, elementUtil.decrypt(pwd));
//		elementUtil.doClick(amazonignInBtn);
		
		return new AccountsPage(driver);
	}
	
	/*
	  Here we maintaining 3 things:
	  1. private variable : own webdriver, by locators, 
	  2. one constructor which will help me to initialize driver
	  3. all public methods: here inside we using all private variables
	  
	  Public methods using private variables: called encapsulation.
	 */
	
	public RegistrationPage navigateToRegistrationPage()
	{
		elementUtil.doClick(resterLink);
		return new RegistrationPage(driver);
		
	}
	
}
