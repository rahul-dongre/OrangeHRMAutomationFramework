package com.orangehrm.pages;

import org.openqa.selenium.By;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class ForgotPasswordPage {

	private ActionDriver actionDriver;

	private By forgotPass = By.xpath("//p[contains(.,'Forgot your password')]");
	private By userName = By.xpath("//input[@name='username']");
	private By cancelBtn = By.xpath("//button[text()=' Cancel ']");
	private By rstPassBtn = By.xpath("//button[text()=' Reset Password ']");
	private By rstSuccessMsg = By.xpath("//h6[contains(.,'Reset Password link')]");

	public ForgotPasswordPage() {
		this.actionDriver = BaseClass.getActionDriver();
	}

	public void cancelOperation(String expectedUrl, String userName) {

		actionDriver.click(forgotPass);
		actionDriver.enterText(this.userName, userName);
		actionDriver.click(cancelBtn);
		actionDriver.hardAssert(actionDriver.getPageUrl(), expectedUrl, "Url doen't match, stopping execution");
	}

	public void resetPassword(String userName) {

		actionDriver.click(forgotPass);
		actionDriver.enterText(this.userName, userName);
		actionDriver.click(rstPassBtn);
		System.out.println("<--" + actionDriver.getText(rstSuccessMsg) + "-->");

	}

}