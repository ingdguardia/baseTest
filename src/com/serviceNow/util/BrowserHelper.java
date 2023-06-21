/**
 * 
 */
package com.serviceNow.util;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserHelper {

	String testRun = "local";
	public WebDriver driver;
	public List<WebDriver> driversList = new LinkedList<WebDriver>();

	// Open Browser
	public WebDriver startBrowser(ExtentTest logger, String browserName) {
		try {
			if (browserName.equalsIgnoreCase("chrome")) {
				// String browserVersion ="";
				if (testRun.equalsIgnoreCase("local")) {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
					System.setProperty("webdriver.chrome.whitelistedIps", "");
					System.setProperty("webdriver.http.factory", "jdk-http-client");
					System.setProperty("webdriver.chrome.args",
							"--disable-extensions --no-sandbox --disable-dev-shm-usage");
				}
				if (testRun.equalsIgnoreCase("Pipeline")) {
					WebDriverManager.chromedriver().setup();
				}
				ChromeOptions options = new ChromeOptions();
				options.setAcceptInsecureCerts(true);
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				options.getBrowserVersion();
				options.setExperimentalOption("detach", true);
				// options.setExperimentalOption("useAutomationExtension",false);
				options.setCapability(ChromeOptions.CAPABILITY, options);
				// options.setCapability(browserName, "chrome");
				// options.setCapability(browserVersion, "109.0.5414.120");
				driver = new ChromeDriver(options);
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				logger.log(Status.PASS, "<b>Chrome </b>browser is started ");
			} else if (browserName.equalsIgnoreCase("ie")) {
				driver = new InternetExplorerDriver();
				logger.log(Status.PASS, "<b>IE </b>browser is started ");
			}
			// ************************
			System.out.println("Browser Id :::" + driver);
			driversList.add(driver);
			// *******************************
			driver.manage().window().maximize();
			logger.log(Status.PASS, "Browser is maximized ");
			return driver;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to launch browser due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
			return null;
		}
	}

	/**
	 * This method will be used to Close Browser
	 * 
	 * @param driver
	 * 
	 */
	public void closeBrowser(ExtentTest logger, WebDriver driver) {
		try {
			driver.quit();
			logger.log(Status.PASS, "Browser closed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to close Browser due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	/**
	 * This method for Closing Current Tab
	 * 
	 * @param logger
	 * @param driver
	 */
	public void closeCurrentTab(ExtentTest logger, WebDriver driver) {
		try {
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
			logger.log(Status.PASS, "Current Tab is closed.");
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to close Browser due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	/**
	 * This method for Opening New Tab
	 * 
	 * @param logger
	 * @param driver
	 */
	public void openNewTab(ExtentTest logger, WebDriver driver) {
		try {
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			logger.log(Status.PASS, "Opening new tab .");
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to open new tab due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}
}