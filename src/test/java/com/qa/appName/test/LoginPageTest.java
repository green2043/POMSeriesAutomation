package com.qa.appName.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.appName.base.BaseTest;
import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.AppErrors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;




/*
 Allure provides several annotations to enhance test reporting by grouping, categorizing, and detailing test cases. Here's a concise list:
Core Allure Annotations
✅ @Epic – Groups tests under a high-level feature (e.g., "User Authentication").
✅ @Feature – Defines a specific functionality within an epic (e.g., "Login Functionality").
✅ @Story – Represents an individual user story (e.g., "User can log in using email").
Test Documentation & Metadata
✅ @Description – Adds a detailed description for a test.
✅ @Link – Attaches an external reference (e.g., issue tracker, documentation).
✅ @Issue – Links a test to a bug tracking system (e.g., Jira issue ID).
✅ @TmsLink – Connects a test to a Test Management System like TestRail.
Severity & Categorization
✅ @Severity(SeverityLevel.CRITICAL) – Defines priority (BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL).
✅ @Tag – Adds custom labels for filtering tests.
Test Steps & Attachments
✅ @Step – Marks a method as a test step, making logs structured in reports.
✅ @Attachment – Attaches screenshots, logs, or text files to reports.
Would you like an example of how to integrate these in your tests? 🚀

 */


@Epic("Epis - 100: Design Login page for open cart shopping application")
@Story("USR-101: Create login page functionality for open cart login page")
public class LoginPageTest extends BaseTest {
	

	
	@Description("login page title test")
	@Severity(SeverityLevel.TRIVIAL) 
	/* Severity Levels in Allure
Allure provides different severity levels to help categorize test cases:
- BLOCKER – Critical, must be fixed immediately.
- CRITICAL – Important feature broken, high priority.
- NORMAL – Standard functionality test.
- MINOR – Slightly lower impact test.
- TRIVIAL – Least important, low priority. */
	@Test (priority = 1,enabled = true)
	public void loginPageTitleTest() {

		String acttitle = loginPage.getLoginPageTitle();
		System.out.println("Page title: " + acttitle);
		Assert.assertEquals(acttitle, AppConstants.LOGIN_PAGE_TITLE,AppErrors.NO_TITLE_MATCHED);

	}
	
	
	/*
	=======================================================================================
	🧪 LoginPageTest – Controlled Execution via testng.xml
	=======================================================================================

	This test class contains 4 test methods:
	----------------------------------------
	1. loginPageTitleTest
	2. loginPageUrlTest
	3. forgotPwdLinkTest
	4. dologinTest

	✔ All tests are annotated with @Test(enabled = true), so by default all are runnable.

	BUT — execution is filtered through the TestNG XML file using <include> and <exclude> tags:
	------------------------------------------------------------------------------------------
	<include name="loginPageTitleTest" />
	<include name="loginPageUrlTest" />
	<exclude name="forgotPwdLinkTest" />
	<include name="dologinTest" />

	✅ EXECUTED METHODS:
	---------------------
	- loginPageTitleTest
	- loginPageUrlTest
	- dologinTest

	❌ SKIPPED METHOD:
	-------------------
	- forgotPwdLinkTest → Explicitly excluded in testng.xml, so will not run.

	🧠 Reminder:
	-------------
	Even if a method is marked enabled = true in @Test, it will be skipped 
	if excluded in the XML file. Always refer to testng.xml to verify test execution.

	=======================================================================================
	*/
	@Description("login page url test")
	@Severity(SeverityLevel.NORMAL) 
	@Test (priority = 2,enabled = true)
	public void loginPageUrlTest() {
		String actUrl = loginPage.getLoginPageUrl();
		System.out.println("Page url: " + actUrl);
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_FRACTION_URL),AppErrors.NO_URL_MATCHED);
	}
	
	@Description("forgot password lnk on login page test")
	@Severity(SeverityLevel.CRITICAL)
	@Test (priority = 3,enabled = true)
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Description("user is able to login on login page test")
	@Severity(SeverityLevel.BLOCKER)
	@Test (priority = 4,enabled = true)
	public void dologinTest() {
//		loginPage.elementUtil.readProp(); 
//
//		String encryptedPassword = loginPage.elementUtil.prop.getProperty("password");
//	    String decryptedPassword = loginPage.decrypt(encryptedPassword);  
//	    accPage = loginPage.doLogin("satyasahoo2043@gmail.com", decryptedPassword);  // Pass the decrypted password
//		accPage = loginPage.doLogin("satyasahoo2043@gmail.com");  
	    accPage = loginPage.doLogin(prop.getProperty("username").trim(),"");  
		
		Assert.assertTrue(accPage.isLogoutExist(),AppErrors.LOGIN_UNSUCCESSFUL);
	}
}
