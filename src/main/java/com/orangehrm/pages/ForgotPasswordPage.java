package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class ForgotPasswordPage {

	private ActionDriver actionDriver;

	@FindBy (xpath="//p[contains(.,'Forgot your password')]")
	WebElement forgotPass;
	
	@FindBy (xpath="//input[@name='username']")
	WebElement userName;
	
	@FindBy (xpath="//button[text()=' Cancel ']")
	WebElement cancelBtn;
	
	@FindBy (xpath="//button[text()=' Reset Password ']")
	WebElement rstPassBtn;
	
	@FindBy (xpath="//h6[contains(.,'Reset Password link')]")
	WebElement rstSuccessMsg;
	
//	private By forgotPass = By.xpath("//p[contains(.,'Forgot your password')]");
//	private By userName = By.xpath("//input[@name='username']");
//	private By cancelBtn = By.xpath("//button[text()=' Cancel ']");
//	private By rstPassBtn = By.xpath("//button[text()=' Reset Password ']");
//	private By rstSuccessMsg = By.xpath("//h6[contains(.,'Reset Password link')]");

	public ForgotPasswordPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
		PageFactory.initElements(driver, this);
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