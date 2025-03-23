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

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	private ForgotPasswordPage forgotPass;

	@BeforeMethod
	public void beforeTestMethod() throws IOException {
		loginPage = new LoginPage();
		homePage = new HomePage();
		forgotPass = new ForgotPasswordPage();
	}

	@Test(priority = 0, alwaysRun = true)
	public void invalidLoginTest() throws IOException {

		loginPage.negativeLoginTest("Invalid credentials");
		staticWait(5);
	}

	@Test(priority = 1, alwaysRun = true)
	public void validLoginTest() throws IOException {
		loginPage.login("Admin", "admin123");
		homePage.logout();
		staticWait(5);
	}

	@Test()
	public void homePageTest() throws IOException {
		loginPage.login("Admin", "admin123");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible when successful login");
		homePage.verifyOrangeHrmLogo();
		homePage.logout();
		staticWait(5);
	}

	@Test
	public void forgotSuccess() {
		forgotPass.resetPassword("Admin");
	}

	@Test
	public void forgotCancel() {
		String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
		forgotPass.cancelOperation(url, "Admin");
	}
}
