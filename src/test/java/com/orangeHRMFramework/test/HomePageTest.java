package com.orangeHRMFramework.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void beforeTestMethodHome() throws IOException {

		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}

	@Test
	public void homepage() throws InterruptedException {
		ExtentManager.startTest("Homepage Test");
		loginPage.login("Admin");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible when successful login");
		ExtentManager.logStep("Login successfull");
		homePage.verifyOrangeHrmLogo();
		ExtentManager.logStep("Logo is visible");
		homePage.logout();
		staticWait(5);
	}
}
