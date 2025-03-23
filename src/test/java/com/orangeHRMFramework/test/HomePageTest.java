package com.orangeHRMFramework.test;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;

public class HomePageTest extends BaseClass{

	
	private HomePage hp;
	
	@BeforeMethod
	public void setup() throws IOException {
		
//		hp = new HomePage(getDriver());
	}
	
	@Test
	public void homepage() {
//		hp.isAdminTabVisible(driver);
	}
}
