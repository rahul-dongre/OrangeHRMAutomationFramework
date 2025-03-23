package com.orangehrm.actiondriver;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.orangehrm.base.BaseClass;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) throws IOException {

		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		logger.info("WebDriver instance is created");
	}

	// Method to click an element
	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			logger.info("Clicked an element");
		} catch (Exception e) {
			logger.error("Unable to click element :- " + e.getMessage());
//			logger.error("Unable to click element");
		}
	}
	
	public void clearField(By by) {
		try {
			waitForElementToBeVisible(by);
			driver.findElement(by).clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to clear text fields" + e.getMessage());
		}
	}

	// method to enter text into a input text field --> to avoid code duplicacy and
	// fixed the multiple call
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			logger.info("Entered text :" + value);
//			driver.findElement(by).clear();
//			driver.findElement(by).sendKeys(value);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			logger.error("Unable to enter the value :- " + value + " " + e.getMessage());
			
		}
	}

	// method to get text from an input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {

			logger.error("Element is not clickable :- " + e.getMessage());
			return "";
		}
	}

	// method to compare two text
	public void compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();

			if (expectedText.equals(actualText)) {
				logger.info("Texts are matching : " + actualText + " equals " + expectedText);
			} else {
				logger.info("Texts are not matching : " + actualText + " not equals " + expectedText);
			}
		} catch (Exception e) {
			logger.error("Unable to compare text :- " + e.getMessage());
		}
	}

	// get text from an input field
	public String getInputText(By by) {
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String value = (String) js.executeScript("return arguments[0].value;", element);
			return value;

		} catch (Exception e) {
			return "Unable to compare text " + e.getMessage();
		}
	}

	// hard assert

	public void hardAssert(String actual, String expected, String yourTxt) {
		Assert.assertEquals(actual, expected, yourTxt);
		logger.info("Assert Passed");
	}

	// dropdown - select class
	public void dropdownSelect(By by, String options) {
		WebElement element = driver.findElement(by);
		Select sel = new Select(element);
		sel.selectByValue(options);
	}

	// method to check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);

			return driver.findElement(by).isDisplayed();

		} catch (Exception e) {
			logger.error("Element is not displayed :- " + e.getMessage());
			return false;
		}
	}

	// wait for elements to be clickable
	public void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not clickable :- " + e.getMessage());
		}
	}

	// click using javascript executer
	public void jsClick(By by) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(by);
		js.executeScript("argument[0].click();", element);
	}

	// scroll to an element
	public void scrollToAnElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("argument[0],scrollIntoView(true)", element);
		} catch (Exception e) {
			logger.error("Unable to locate element :- " + e.getMessage());
		}

	}

	// Method for the page load
//	public void waitForPageLoad(int timeOutInSecs) {
//		wait.withTimeout(Duration.ofSeconds(timeOutInSecs).until(WebDriver -> ((JavascriptExecutor) WebDriver).executeScript("return document.readyState").equals("complete")));
//	}

	// wait for element to be visible
	public void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Element is not visible :- " + e.getMessage());
		}
	}

	public String getPageUrl() {
		String crntUrl = driver.getCurrentUrl();
		return crntUrl;
	}

}
