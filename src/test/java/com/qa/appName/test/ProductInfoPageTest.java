package com.qa.appName.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.appName.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {

	// Page chaining:
	// loginpage>dologin>accPage>performSearch>ResultPage>selectProduct>prodInfoPage>checkHeader>assert=Header

	// BfeoreTest>BeforeClass

	@BeforeClass
	public void productInfoSetup() {
//		loginPage.elementUtil.readProp();
//		String encryptedPassword = loginPage.elementUtil.prop.getProperty("password");
//		String decryptedPassword = loginPage.decrypt(encryptedPassword);

		accPage = loginPage.doLogin("satyasahoo2043@gmail.com","");
	}
	
	@DataProvider
	public Object [][] getProductTestData(){
		
		return new Object[][]
				{
			{"MacBook","MacBook Air"},
			{"MacBook","MacBook Pro"},
			{"iMac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"},
			{"Apple","Apple Cinema 30\""}
			
				};
	}

	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey, String mainProduct) {
		resultsPage = accPage.performSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(mainProduct);
		String actualHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actualHeader, mainProduct);
	}
	
	@DataProvider
	public Object [][] getProductImagesTestData(){
		
		return new Object[][]
				{
			{"MacBook","MacBook Air",4},
			{"MacBook","MacBook Pro",4},
			{"iMac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"Samsung","Samsung Galaxy Tab 10.1",7},
			{"Apple","Apple Cinema 30\"",6}
			
				};
	}
	
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImageTest(String searchKey, String mainProduct,int imageCount) {
		resultsPage = accPage.performSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(mainProduct);
		int actualImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actualImagesCount, imageCount);
	}
	
	@Test
	public void productmetaDataTest() {
		resultsPage = accPage.performSearch("Macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> actualProductInfoMap= productInfoPage.getProductInformation(); 
		
//		Assert.assertEquals(actualProductInfoMap.get("Brand"), "Apple");
//		Assert.assertEquals(actualProductInfoMap.get("Availability"), "In Stock");
//		Assert.assertEquals(actualProductInfoMap.get("actualPrice"), "$2,000.00");
//		Assert.assertEquals(actualProductInfoMap.get("Reward Points"), "800");
		
		// In hard assertion if one assertion fail it doesn't give chance to other assertion to verify, but is soft assertion it happens 
		
//		softAssert.assertEquals(actualProductInfoMap.get("Brand"), "Apple11"); //this assertion will fail
//		softAssert.assertEquals(actualProductInfoMap.get("Availability"), "In Stock");
//		softAssert.assertEquals(actualProductInfoMap.get("actualPrice"), "$2,000.00");
//		softAssert.assertEquals(actualProductInfoMap.get("Reward Points"), "800");
//		softAssert.assertAll(); // this line is important to write, it will tell which assertion failed from all assertion
		
		
		softAssert.assertEquals(actualProductInfoMap.get("Brand"), "Apple"); 
		softAssert.assertEquals(actualProductInfoMap.get("Availability"), "Out Of Stock"); // Actual: "Out Of Stock"



		softAssert.assertEquals(actualProductInfoMap.get("actualPrice"), "$2,000.00");
		softAssert.assertEquals(actualProductInfoMap.get("Reward Points"), "800");
		softAssert.assertAll(); // this line is important to write, it will tell which assertion failed from all assertion
		
	}

}
