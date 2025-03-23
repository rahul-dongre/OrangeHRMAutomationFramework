package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	protected static WebDriver driver;
	private static ActionDriver actionDriver;
	public static final Logger logger = (Logger) LoggerManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {

		// load the configuration file
		prop = new Properties();
		FileInputStream file = new FileInputStream("src/main/resources/config.properties");
		prop.load(file);
		logger.info("config.properties file loaded");
	}

	@BeforeMethod
	public void setup() throws Exception {

		// printing method name
		System.out.println(this.getClass().getSimpleName());

		// Calling method for browser launch and browser setup
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("WebDriver initialozed and browser maximized");
		// initialize action driver only once
		if (actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			logger.info("ActionDriver instance is created");
			logger.trace("This is a Trace Message");
			logger.error("This is a Error Message");
			logger.debug("This is a Debug Message");
			logger.warn("This is a Warn Message");
		}
	}

	// initialize the browser based on the browser provided into confi.properties
	// file
	private void launchBrowser() {

		String browser = prop.getProperty("browser");

		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			logger.info("ChromeDriver initiated is created");
			break;

		case "edge":
			driver = new EdgeDriver();
			logger.info("EdgeDriver initiated is created");
			break;

		case "firefox":
			driver = new FirefoxDriver();
			logger.info("FirefoxDriver initiated is created");
			break;

		default:
			throw new IllegalArgumentException("Browser is not supported " + browser);
		}

	}

	// configure browser settings
	private void configureBrowser() {

		// Navigate to the url
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the url :" + e.getMessage());
		}

		// maximizing window
		driver.manage().window().maximize();

		// implicit wait of 10 seconds
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

	}

	@AfterMethod
	public void tearDown() {
		try {
			driver.quit();
		} catch (Exception e) {
			System.out.println("Unable to quit the driver :" + e.getMessage());
		}

		driver = null;
		actionDriver = null;
		logger.info("ActionDriver instance is closed");
		logger.info("WebDriver instance is closed");
	}

	public static Properties getProp() {
		return prop;
	}

////code to access driver outside of this class because driver is declared as protected
//	public WebDriver getDriver() {
//		return driver;
//	}

	// getter method - new implementation of Singleton design pattern (no need to
	// create ActionDriver instance multiple times

	public static WebDriver getDriver() {
		if (driver == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver;
	}

//code to set the driver
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public static ActionDriver getActionDriver() {
		if (actionDriver == null) {
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver;
	}

	// static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}
}
