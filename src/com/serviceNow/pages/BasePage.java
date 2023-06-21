package com.serviceNow.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import com.serviceNow.util.BrowserHelper;
import com.serviceNow.util.FrameHelper;
import com.serviceNow.util.Utility;
import com.serviceNow.util.Wait;

public class BasePage {

	// properties file
	public WebDriver driver;
	Utility util;
	Wait wait;
	FrameHelper frameHelper;
	BrowserHelper browserHelper;

	// Date related Variables
	public DateFormat dateFormat;
	public Date date;
	boolean status = false;

	// Screen Shot
	public String screenShotPath;
	public String screenShotFileName;

	// Constructor
	public BasePage(WebDriver driver) {
		this.driver = driver;
		date = new Date();
		dateFormat = new SimpleDateFormat("ddMMyyyy-HH_mm_ss");
		util = new Utility();
		frameHelper = new FrameHelper();
		browserHelper = new BrowserHelper();
		screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\";
	}
}
