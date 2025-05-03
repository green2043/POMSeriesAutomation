package com.qa.appName.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.plaf.metal.MetalTextFieldUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import com.qa.appName.utils.ElementUtil;
import com.qa.appName.utils.TimeUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	
	private By productHeader= By.cssSelector("div#content h1");
	private By productImages= By.cssSelector("a.thumbnail");
	private By productMetadata= By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPricedata= By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	
	//private Map<String, String> productMap;
	//private LinkedHashMap<String, String> productMap; //It will provide data in insertion order, i.e.,-order wise
	private TreeMap<String, String> productMap; //It will provide in alphabetical order

	
	public ProductInfoPage(WebDriver driver)
	{
		this.driver=driver;
		elementUtil = new ElementUtil(driver);
	}

	
	public String getProductHeader() {
		return elementUtil.doGetElementText(productHeader);
	}
	
	public int getProductImagesCount()
	{
	 int imagesCount= elementUtil.waitForElementsVisibile(productImages, TimeUtil.DEFAULT_TIME_OUT).size();
	 System.out.println("Product Images count --->"+imagesCount);
	 return imagesCount;
	}
	
	public Map<String, String> getProductInformation() //calling a private method from the public method is called encapsulation
	{
//		productMap =new HashMap<String, String>();  //1-hashMap will initialize
//		productMap =new LinkedHashMap<String, String>();  //1-LinkedHashhMap will initialize
		productMap =new TreeMap<String, String>();  //1-LinkedHashhMap will initialize
		getProductMetaData(); //2-fetch the meta data from this private method from the list on the basis of 0 and 1
		getProductPricingData(); //3-fetch the pricing data from this private method from the list on the basis of 0 and 1
		System.out.println(productMap);
		return productMap;
	}
	
	
//	Brand: Apple
//	Product Code: Product 16
//	Reward Points: 600
//	Availability: In Stock
	
	private void getProductMetaData() {
	    // Fetch all elements matching the product metadata locator.
	    List<WebElement> metaDataList = elementUtil.getElements(productMetadata);
	    
	    // Print the count of metadata elements retrieved for debugging.
	    System.out.println("product meta data count--->" + metaDataList.size());
	    
	    // Loop through each metadata element.
	    for (WebElement e : metaDataList) {
	        // Get the text content of the metadata element.
	        String meta = e.getText();
	        
	        // Split the metadata text into key and value using ":".
	        String metaData[] = meta.split(":");
	        
	        // Remove extra spaces before and after the key text.
	        String metaKey = metaData[0].trim();
	        
	        // Remove extra spaces before and after the value text.
	        String metaValue = metaData[1].trim();
	        
	        // Add the cleaned key-value pair to the map.
	        productMap.put(metaKey, metaValue);
	    }
	    
/*	    Metadata:
	    	Metadata is essentially "data about data." It provides extra information that describes or summarizes something, making it easier to organize, find, or use. For example:
	    	- A book's metadata includes its title, author, publication date, and genre.
	    	- A photo's metadata includes its resolution, file size, and date taken.

	    	Product Metadata:
	    	Product metadata refers to the descriptive information related to a product. It includes key details that define or characterize the product, such as:
	    	- Brand: The manufacturer or company behind the product (e.g., Apple, Samsung).
	    	- Availability: Whether the product is in stock or out of stock.
	    	- Price: The cost of the product.
	    	- Reward Points: Loyalty or bonus points customers can earn by purchasing the product.
*/


	    
	}
	
	
	
	
	//	$602.00 --0th postn
	//	Ex Tax: $500.00   --1st postn
	private void getProductPricingData() {
		
		List<WebElement> metaPriceList= elementUtil.getElements(productPricedata);
		System.out.println("product meta data count--->" + metaPriceList.size());
		
		
		String price = metaPriceList.get(0).getText().trim();
		String ExTaxPrice=metaPriceList.get(1).getText().trim();
		
		productMap.put("actualPrice",price);
		productMap.put("actualTaxPrice",price);
		
		
	}

}
