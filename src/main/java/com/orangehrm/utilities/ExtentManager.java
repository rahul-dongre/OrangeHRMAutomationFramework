package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();// to store ThreadID and WebDriver instance

	// initialize the extent report
	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/src/main/resources/ExtentReport/ExtentReports.html";
			System.err.println(reportPath);
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath); // generates contents in extent reports
			spark.config().setReportName("Automation Report");
			spark.config().setDocumentTitle("OrangeHRM Report");
			spark.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(spark); 
			// adding system information
			extent.setSystemInfo("Operating System", System.getProperty("os,name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
		}
		return extent;
	}

	// start the test
	public synchronized static ExtentTest startTest(String testName) {
		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}

	// end the test
	public synchronized static void endTest() {
		getReporter().flush();
	}

	// get current Thread's test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}

	// Method to get the actual name of the current test
	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if (currentTest != null) {
			return currentTest.getModel().getName();
		} else {
			return "No test is currently active for this thread";
		}
	}

	// Method to log the steps
	public static void logStep(String logMsg) {
		getTest().info(logMsg);
	}

	// Log a step validation with screenshot
	public static void logStepWithScreenshot(WebDriver driver, String logMsg, String screenshotMsg) {
		getTest().pass(logMsg);

		// screenshot method
		attachScreenshot(driver, screenshotMsg);

	}

	// Log a failure
	public static void logFailure(WebDriver driver, String logMsg, String screenshotMsg) {
		
		String coloredMsg = String.format("<span style = 'color:red;'>%s</span>", logMsg);
		getTest().fail(coloredMsg);
		// screenshot method
		attachScreenshot(driver, screenshotMsg);
	}

	// Log skipped test
	public static void logSkip(String logMsg) {
		String coloredMsg = String.format("<span style = 'color:orange;'>%s</span>", logMsg);
		getTest().skip(coloredMsg);
	}

	// take screenshot with date and time in the file
	public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);

		// Format date and time for file name
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

		// Saving the screenshot to a file
		String destinationPath = System.getProperty("user.dir") + "/src/main/resources/screenshots/Screenshots_"
				+ screenshotName + "_" + timeStamp + ".png";
		System.err.println(destinationPath);
		File finalPath = new File(destinationPath);
		try {
			FileUtils.copyFile(src, finalPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Convert screenshot to Base64 for embedding in the report
		String base64Format = convertToBase64File(src);
		return base64Format;
	}

	// Convert screenshot to Base64 format
	public static String convertToBase64File(File screenshotFile) {
		String base64Format = "";
		// read the file content into a byte array

		try {
			byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
			base64Format = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// convert byte array to Base64 String
		return base64Format;
	}

	// Attach screenshot to report using Base64
	public synchronized static void attachScreenshot(WebDriver driver, String msg) {
		try {
			String screenshotBase64 = takeScreenshot(driver, getTestName());
			getTest().info(msg, com.aventstack.extentreports.MediaEntityBuilder
					.createScreenCaptureFromBase64String(screenshotBase64).build());
		} catch (Exception e) {
			getTest().fail("Failed to attach screenshot: " + msg);
			e.printStackTrace();
		}
	}

	// register driver for current thread
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}
}
