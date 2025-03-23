package com.orangeHRMFramework.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class DummyClass extends BaseClass {

	@Test
	public void test() {

		Assert.assertEquals(driver.getTitle(), "OrangeHRM",  "Title is different");
	}
}
