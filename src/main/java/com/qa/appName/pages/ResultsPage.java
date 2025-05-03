package com.qa.appName.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.appName.utils.ElementUtil;
import com.qa.appName.utils.TimeUtil;


//1-LoginPage-->2-AccountsPage-->3-ResultsPage-->4-ProductInfoPage(LAST PAGE)
//[PAGE CHAINING]

public class ResultsPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	private By searchProducts = By.cssSelector("div.product-layout");


	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	public String geSearchPageTitle(String productName) {
		return elementUtil.waitForTitleContains(productName, TimeUtil.DEFAULT_TIME_OUT);
	}

	public int getSerchProductCount() {

		int productCount= elementUtil.waitForElementsVisibile(searchProducts, TimeUtil.DEFAULT_TIME_OUT).size();
		System.out.println("product search count : "+ productCount);
		return productCount;
	}
	
	public ProductInfoPage selectProduct(String desiredOroductName) {
		System.out.println("main product name :"+ desiredOroductName );
		elementUtil.doClick(By.linkText(desiredOroductName));
		
		return new ProductInfoPage(driver);
		
	}
	

}
