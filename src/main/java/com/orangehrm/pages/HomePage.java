package com.orangehrm.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {

	@FindBy (xpath="//span[text()='Admin']")
	WebElement admin;
	
	@FindBy (xpath="//span[@class='oxd-userdropdown-tab']")
	WebElement user;

	@FindBy (xpath="//a[text()='Logout']")
	WebElement logoutBtn;
	
	@FindBy (xpath="//div[@class='oxd-brand-banner']/img")
	WebElement orangeHrmLogo;
	
//	private By admin = By.xpath("//span[text()='Admin']");
//	private By user = By.xpath("//span[@class='oxd-userdropdown-tab']");
//	private By logoutBtn = By.xpath("//a[text()='Logout']");
//	private By orangeHrmLogo = By.xpath("//div[@class='oxd-brand-banner']/img");

	private ActionDriver actionDriver;

//	public HomePage(WebDriver driver) throws IOException {
//		this.actionDriver = new ActionDriver(driver);
//	}

	// new implementation of singleton pattern
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
		PageFactory.initElements(driver, this);
	}

	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(admin);

	}

	public boolean verifyOrangeHrmLogo() {
		return actionDriver.isDisplayed(orangeHrmLogo);
	}

	public void logout() {
		actionDriver.click(user);
		actionDriver.click(logoutBtn);
	}
}
