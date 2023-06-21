package com.serviceNow.testcases;

import java.io.IOException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.serviceNow.util.Wait;

//@author @molina.emiliano @b.marmol.maldonado

public class ProjectName extends usefulMethods {

	String testRun = "local"; // pipeline
	public CommonTest common;
	public ProjectName_Map mapping;
	public Wait wait;

	public ProjectName() throws IOException {
		super();
		// Auto-generated constructor stub
	}

	// Admin login
	@BeforeTest
	public void UserLogin() {
		createTest("URL"); // user login

		try {
			String url = "";
			String username = "";
			String password = "";
			if (testRun.equals("pipeline")) {
				url = System.getProperty("url");
				// username = System.getProperty("username");
				// password = System.getProperty("password");
			}
			if (testRun.equals("local")) {
				url = getSpecificValue("navigate", "URL");
				// username = getSpecificValue("validLogin", "AdsUsername");
				// password = getSpecificValue("validLogin", "Password");

			}

			openBrowserOnUrl(getLogger(), url);
			initializePage();
			// login(getLogger(), username, password);

		} catch (Exception e) {
			util.catchError(getLogger(), driver, e, "Unable to Login");
		}
	}

	@Test
	public void scriptMethod() {
		createTest("PM Test");
		System.out.println("Test 1");
		// navigateTo(getLogger(), "form PMR", "navigate", "Form URL");
		// submitForm_Frontend(getLogger(), "PM");
	}

	@Test
	public void scriptMethod2() {
		String url;
		url = getSpecificValue("navigate", "URL");
		createTest("HR Test");
		openBrowserOnUrl(getLogger(), url);
		initializePage();
		// navigateTo(getLogger(), "form HRR", "navigate", "Form URL");
		// submitForm_Frontend(getLogger(), "HR");
	}

	@Test
	public void scriptMethod3() {
		createTest("INC Test");
		navigateTo(getLogger(), "form INC", "navigate", "Form URL");
		submitForm_Frontend(getLogger(), "INC");
	}

	@Test
	public void scriptMethod4() {
		createTest("CHR Test");
		navigateTo(getLogger(), "form CHR", "navigate", "Form URL");
		submitForm_Frontend(getLogger(), "CHR");
	}

	// This method closes the browser when a test method is finished
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
