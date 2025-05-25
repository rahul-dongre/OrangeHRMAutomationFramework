package com.orangeHRMFramework.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.ForgotPasswordPage;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	private ForgotPasswordPage forgotPass;

	@BeforeMethod
	public void beforeTestMethodLogin() throws IOException {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		forgotPass = new ForgotPasswordPage(getDriver());
	}

	@Test(priority = 0, alwaysRun = true)
	public void invalidLoginTest() throws IOException, InterruptedException {
//		ExtentManager.startTest("Invalid login test");
		ExtentManager.logStep("Navigating to login page entering wrong username and password");
		loginPage.negativeLoginTest("Invalid credentials");
//		ExtentManager.endTest();
		staticWait(5);
	}

	@Test(priority = 1, alwaysRun = true)
	public void validLoginTest() throws IOException, InterruptedException {
//		ExtentManager.startTest("Valid login test");
		ExtentManager.logStep("Navigating to login page entering username and password");
		loginPage.login("Admin");
		ExtentManager.logStep("Login successfully");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
//		ExtentManager.endTest();
		staticWait(5);
		
	}

	@Test(enabled = false)
	public void homePageTest() throws InterruptedException {
		loginPage.login("Admin");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible when successful login");
		homePage.verifyOrangeHrmLogo();
		homePage.logout();
		staticWait(5);
	}

	@Test
	public void forgotSuccess() {
//		ExtentManager.startTest("Password Reset");
		forgotPass.resetPassword("Admin");
		ExtentManager.logStep("Password Reset suceess");
	}

	@Test
	public void forgotCancel() {
//		ExtentManager.startTest("Password Reset Cancel button");
		String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
		forgotPass.cancelOperation(url, "Admin");
		
		ExtentManager.logStep("Password Reset cancel operation");
	}
}
