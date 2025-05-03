package com.qa.appName.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.appName.base.BaseTest;
import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.AppErrors;

public class AccountsPageTest extends BaseTest {

	// page chaining :login page->Accounts page->perform product search->ResultsPage->products page->Add to cart->cart page->order page->payment page
	
//login()>AccountsPage>PerformSearch>resultsPage>call getSearchPageTitle>Assertion for title 	

	@BeforeClass
	public void accountSetUp() throws InterruptedException {

//		accPage = loginPage.doLogin("satyasahoo2043@gmail.com");
		accPage = loginPage.doLogin(prop.getProperty("username").trim(),"");
		Thread.sleep(2000);

	}

	@Test
	public void accPageTitleTest() throws InterruptedException {
		String actTitle = accPage.getAccPageTitle();
		System.out.println("acc page Title:" + actTitle);
		Thread.sleep(2000);

		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE, AppErrors.NO_TITLE_MATCHED);
//		Assert.assertEquals(actTitle, AppConstants.AMAZON_ACCOUNT_PAGE_TITLE, AppErrors.NO_TITLE_MATCHED);
	}

	@Test
	public void accountPageUrlTest() throws InterruptedException {
		Thread.sleep(2000);
		String actUrl = accPage.getAccPageURL();
		System.out.println("Page url: " + actUrl);

		Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNT_PAGE_FRACTION_URL), AppErrors.NO_URL_MATCHED);
	}

	@Test
	public void searchExistTest() throws InterruptedException {
		Thread.sleep(2000);

		Assert.assertTrue(accPage.isSearchExist());
	}

	@Test
	public void logoutExistTest() {
		Assert.assertTrue(accPage.isLogoutExist());
	}

	@Test
	public void accountsPageHeaderTest() throws InterruptedException {
		List<String> accountHeadersList = accPage.getAccountPageSectionHeaders();
		Thread.sleep(2000);

		Assert.assertEquals(accountHeadersList, AppConstants.EXPECTED_ACCOUT_HEADER_LIST);
	}

	@DataProvider
	public Object[][] getProductName()  //return type is 2 dimension object array 
	{
		return new Object[][]
				{
			{"MacBook"},
			{"iMac"},
			{"Samsung"},
		};
	}

	// TDD
	@Test(dataProvider = "getProductName")// getProductName is above methods
	public void productSearchTest(String productName) //productName is holding parameter
	{
		resultsPage = accPage.performSearch(productName);
		String actTitle = resultsPage.geSearchPageTitle(productName);
		System.out.println("Search page title : " + actTitle);
//		Assert.assertEquals(actTitle, AppConstants.SEARCH_PAGE_TITLE + " - " + productName);
		softAssert.assertEquals(actTitle, AppConstants.SEARCH_PAGE_TITLE + " - " + productName); //for softAssert- need to create object
		Assert.assertTrue(resultsPage.getSerchProductCount()>0); // for hard Assert -directly use 'Assert' class
	}
	
	/*
	 In terms of testing, Test-Driven Development (TDD) is a process where tests are written first to define the expected behavior of a feature or function. These tests guide the development process by ensuring that the code fulfills the requirements and works correctly. Here's the definition focused on testing:

TDD is a software development approach where automated tests are written before the actual code. These tests act as a blueprint for the functionality being developed. Developers then write code to pass the tests, ensuring correctness, reliability, and meeting the specified requirements. After passing, the code is refined for optimization while keeping the tests successful.
In simple terms: You test first, then write code to pass the test, and keep improving while ensuring your tests always work

	 **/
	

}
