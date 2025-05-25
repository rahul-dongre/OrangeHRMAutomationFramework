package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class TestListener implements ITestListener {

	// triggered when test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// start logging in extent report
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started : " + testName);
	}

	// triggered when a test is passed
	@Override
	public void onTestSuccess(ITestResult result) {

		String testName = result.getMethod().getMethodName();
		// start logging in extent report
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test passed successfully",
				"Test End: " + testName + " - Test Passed");
	}

	// triggered when a test is failed
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMsg = result.getThrowable().getMessage();
		ExtentManager.logStep(failureMsg);
		// start logging in extent report
		ExtentManager.logFailure(BaseClass.getDriver(), "Test failed", "Test End: " + testName + " - Test Failed");
	}

	// triggered when test skips
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped : " + testName);
	}

	// it will be triggered when suite starts
	@Override
	public void onStart(ITestContext context) {
		// Initialize the extent reports
		ExtentManager.getReporter();
	}

	// it will be triggered when suite ends
	@Override
	public void onFinish(ITestContext context) {
		// Flush the extent reports
		ExtentManager.endTest();

	}

}
