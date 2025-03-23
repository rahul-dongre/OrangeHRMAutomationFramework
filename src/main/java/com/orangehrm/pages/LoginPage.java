package com.orangehrm.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {

	private ActionDriver actionDriver;

	private By username = By.xpath("//input[@name='username']");
	private By password = By.xpath("//input[@name='password']");
	private By loginBtn = By.xpath("//button[@type='submit']");
	private By errorMsg = By.xpath("//p[text() = 'Invalid credentials']");

//	public LoginPage(WebDriver driver) throws IOException {
//		this.actionDriver = new ActionDriver(driver);
//	}

	// new implementation of singleton pattern
	public LoginPage() {
		this.actionDriver = BaseClass.getActionDriver();
	}

	// login method
	public void login(String username, String password) {
		
		actionDriver.clearField(this.username);
		actionDriver.enterText(this.username, username);
		actionDriver.enterText(this.password, password);
		actionDriver.click(loginBtn);
		
	}

	// method to check invalid creds error msg

	public boolean isMsgDisplayed() {
		return actionDriver.isDisplayed(errorMsg);
	}

	// method to get the text from error msg
	public String getErrorMsg() {
		return actionDriver.getText(errorMsg);
	}

	// verify if error msg is correct
	public void verifyErrorMsg(String expectedError) {
		actionDriver.compareText(errorMsg, expectedError);
	}

	// negative login
	public void negativeLoginTest(String expectedError) {
		login("Holi", "Holika");
		isMsgDisplayed();
		getErrorMsg();
		Assert.assertEquals(getErrorMsg(), expectedError, "Assert fails as error message is not same");
		verifyErrorMsg(expectedError);
	}
}