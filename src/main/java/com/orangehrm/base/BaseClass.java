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
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
//	protected static WebDriver driver;
//	private static ActionDriver actionDriver;

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	public static final Logger logger = (Logger) LoggerManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {

		// load the configuration file
		prop = new Properties();
		FileInputStream file = new FileInputStream("src/main/resources/config.properties");
		prop.load(file);
		logger.info("config.properties file loaded");

		// start the extent report
//		ExtentManager.getReporter();//this has been implemented in TestListener class
	}

	@BeforeMethod
	public synchronized void setup() throws Exception {

		// printing method name
		System.out.println("Setting the WebDriver for : " + this.getClass().getSimpleName());

		// Calling method for browser launch and browser setup
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("WebDriver initialozed and browser maximized");
		logger.trace("This is a Trace Message");
		logger.error("This is a Error Message");
		logger.debug("This is a Debug Message");
		logger.fatal("This is a Fatal Message");
		logger.warn("This is a Warn Message");

		/*
		 * // initialize action driver only once if (actionDriver == null) {
		 * actionDriver = new ActionDriver(driver);
		 * logger.info("ActionDriver instance is created "+Thread.currentThread().getId(
		 * )); }
		 */

		// initialize action driver for the current thread
		actionDriver.set(new ActionDriver(driver.get()));
		logger.info("ActionDrive initialized for current thread :" + Thread.currentThread().getId());
		// initialize the browser based on the browser provided into confi.properties
		// file
	}

	private synchronized void launchBrowser() {

		String browser = prop.getProperty("browser");

		switch (browser.toLowerCase()) {
		case "chrome":
			// driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("ChromeDriver initiated is created");
			break;

		case "edge":
//			driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("EdgeDriver initiated is created");
			break;

		case "firefox":
//			driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver(getDriver());
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
			driver.get().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the url :" + e.getMessage());
		}

		// maximizing window
		driver.get().manage().window().maximize();

		// implicit wait of 10 seconds
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));// driver.get() or getDriver()
																							// both are same

	}

	@AfterMethod
	public synchronized void tearDown() {
		try {
			driver.get().quit();
		} catch (Exception e) {
			System.out.println("Unable to quit the driver :" + e.getMessage());
		}

//		driver = null;
//		actionDriver = null;

		driver.remove();
		actionDriver.remove();
		logger.info("ActionDriver instance is closed");
		logger.info("WebDriver instance is closed");

//		ExtentManager.endTest();//implemented in test listener class
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
		if (driver.get() == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();
	}

//code to set the driver
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}

	public static ActionDriver getActionDriver() {
		if (actionDriver.get() == null) {
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver.get();
	}

	// static wait for pause
	public void staticWait(int seconds) throws InterruptedException {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
//		 TimeUnit.SECONDS.sleep(seconds);
	}
}
