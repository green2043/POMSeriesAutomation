package com.qa.appName.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.appName.base.BaseTest;
import com.qa.appName.utils.AppConstants;
import com.qa.appName.utils.AppErrors;

public class TOIPageTest extends BaseTest {

	@Test(priority = 1)
	public void toiPageTitleTest() {

		String actTitle = toiPage.getTOIPageTitle();
		System.out.println("TOI Page title:" + actTitle);
		Assert.assertTrue(actTitle.contains(AppConstants.TOI_FRACTION_PAGE_TITLE), AppErrors.NO_TITLE_MATCHED);
	}
	
	@Test(priority = 3)
	public void toiPageURLTest() {
		
		String actURL = toiPage.getTOIPageURL();
		System.out.println("TOI Page title:" + actURL);
		Assert.assertTrue(actURL.equalsIgnoreCase(AppConstants.TOI_PAGE_URL), AppErrors.NO_TITLE_MATCHED);
	}

	@Test(priority = 4)
	public void toiPageLogoTest() {
		Assert.assertTrue(toiPage.IsTOILogoExist(), AppErrors.LOGO_IS_NOT_PRESENT);
	}
	
	@Test(priority = 5)
	public void toiTechPageURLTest() {
		
		String actURL = toiPage.getTOITechPageURL();
		System.out.println("TOI tech Page title:" + actURL);
		Assert.assertTrue(actURL.contains(AppConstants.TOI_TECH_PAGE_FRACTION_URL), AppErrors.NO_TITLE_MATCHED);
	}

	
	@Test(priority = 6)
	public void toiTechPageTest() {
		Assert.assertTrue(toiPage.isTechLogoExist(),AppErrors.LOGO_IS_NOT_PRESENT);
	
		
	}

}
