package com.qa.appName.test;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.appName.base.BaseTest;
import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void regPageSetup()
	{
		regPage=loginPage.navigateToRegistrationPage();
		
	}
	
	public String getRandomEmail() {
		Random random = new Random();
		String email = "testAutomation"+random.nextInt(5000)+"@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegTestData()
	{
		Object regData[][]= ExcelUtil.getTestdata(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
/*	
	// ==========================
	// 1st registerUserTest Method
	// ==========================
	// This test method reads user details (first name, last name, email, phone number, password, and subscribe option) 
	// directly from the Excel sheet using the @DataProvider.
	// The email ID is also taken from the Excel sheet itself (static email).
	// It tries to register a new user using this provided data and asserts whether registration was successful.

	@Test(dataProvider = "getRegTestData")
	public void registerUserTest(String firstName,String lastName,String email,String telephone, String password,String subscribe) 	{
			
		boolean flag= regPage.registerAuser(firstName, lastName, email, telephone, password, subscribe);
		Assert.assertTrue(flag);
	}
*/
	
/*
	 ==========================
	 2nd registerUserTest Method
	 ==========================
	 This test method is almost the same as the first one but with a small important difference:
	 It reads the user details (first name, last name, phone number, password, subscribe option) from the Excel sheet 
	 but DOES NOT take the email ID from the sheet.
	 Instead, it generates a random email ID at runtime using the getRandomEmail() method above,
	 so that every test run uses a UNIQUE email address and avoids duplicate email registration errors.
	 It then tries to register the user and asserts registration success. */

	@Test(dataProvider = "getRegTestData")
	public void registerUserTest(String firstName,String lastName,String telephone, String password,String subscribe) 	{
			
		boolean flag= regPage.registerAuser(firstName, lastName, getRandomEmail(), telephone, password, subscribe);
		Assert.assertTrue(flag);
	}

	
	
	/*
	===================================================================================================================
	IMPORTANT NOTE ABOUT HANDLING PHONE NUMBERS FROM EXCEL:

	- Excel by default treats large numbers (like 10-digit phone numbers) as numeric values.
	- This can cause Excel to automatically format them in scientific notation (e.g., 9.23456789E9) 
	  or change them into inaccurate decimal numbers.

	TWO SOLUTIONS TO FIX THIS:
	1. Format the Excel cell or column as "Text" (Right-click → Format Cells → Select "Text" → OK → Re-enter phone numbers).
	2. OR, Simply put a single quote (') before entering the phone number in the Excel cell (e.g., '9876543210).

	- Both methods will ensure phone numbers are read correctly in Java without any format issues.

	===================================================================================================================
	*/


}
