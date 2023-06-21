/**
 * This class is responsible for setting up the browser config and other common page elements 
 * that are shared across different pages.
 */
package com.serviceNow.testcases;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.serviceNow.util.BrowserHelper;
import com.serviceNow.util.FrameHelper;
import com.serviceNow.util.Utility;
import com.serviceNow.util.Wait;
import com.serviceNow.xls.Xls_Reader;

public class BaseTest {

	public WebDriver driver;
	// useful classes
	public BrowserHelper browserHelper;
	public FrameHelper frameHelper;
	public Utility util;
	public Wait wait;
	// Variables for taking Data from Excel
	// private static volatile Xls_Reader xl = null;
	public String sheetName = "Testdata"; // could be overwrite
	// Variable for reports
	public DateFormat dateFormat;
	public Date date;
	static String suiteName;
	static String currentPath = "";
	static String suiteFolderPath = "";
	static String browserName = "Chrome";
	// Variable to file manage
	String filepath;

	// ExtendReport Management
	private static ThreadLocal<ExtentReports> extent_Report = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> logger = new ThreadLocal<>();
	// Excel TC data Management it will contain all TC data
	private static ThreadLocal<Map<String, Map<String, String>>> threadLocalExcelData = new ThreadLocal<Map<String, Map<String, String>>>() {
		@Override
		protected Map<String, Map<String, String>> initialValue() {
			return new LinkedHashMap<String, Map<String, String>>();
		}
	};
	/**
	 * A thread-safe variable to hold a single instance of the Excel class, which is
	 * shared and reused by all threads.
	 */
	private static AtomicReference<Xls_Reader> xl = new AtomicReference<>();

	// Constructor
	public BaseTest() throws IOException {
		date = new Date();
		dateFormat = new SimpleDateFormat("ddMMyyyy-HH_mm_ss");
		// Current Project Path
		currentPath = System.getProperty("user.dir");
		// Creating Map object for storing the Excel data
		browserHelper = new BrowserHelper();
		util = new Utility();
		wait = new Wait();
		frameHelper = new FrameHelper();
	}

	/**
	 * This method for creating a new folder with Suite name
	 * 
	 * @param suiteName
	 */
	public void suiteConfig(String suiteName) {
		suiteFolderPath = currentPath + "//Reports//" + suiteName + "_" + dateFormat.format(date);
		// Create new Directory for Reports with SuiteName
		new File(suiteFolderPath).mkdir();
	}

	/**
	 * This method for creating reports file and reports related configurations
	 * 
	 * @param testName
	 */
	public synchronized ExtentReports createExtentReports(String testCaseName) {
		ExtentReports extent = extent_Report.get();
		if (extent == null) {
			extent = new ExtentReports();
			extent_Report.set(extent);
		}
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(suiteFolderPath + "//" + testCaseName + ".html");

		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName(suiteName + " Test Results");

		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Project Name", suiteName);
		// This line get the Sbox Number from URL
		// String Sbox = getSpecificValue("navigate",
		// "URL").split("\\.")[0].split("-")[1];
		String Sbox = getSpecificValue("navigate", "URL");

		extent.setSystemInfo("Environment", Sbox);
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Browser", browserName);
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Automation Developer", "@b.marmol.maldonado");
		return extent;
	}

	/**
	 * This method for creating ExtentTest rows on their report
	 * 
	 * @param testName
	 */
	public static synchronized ExtentTest createTest(String testName) {
		logger.set(extent_Report.get().createTest(testName));
		return logger.get();
	}

	// Method to get the ExtentTest object
	public static synchronized ExtentTest getLogger() {
		return logger.get();
	}

	// Method to terminate ExtentReports
	public static synchronized void closeExtentReports() {
		if (extent_Report.get() != null) {
			extent_Report.get().flush();
			extent_Report.remove();
		}
		if (logger.get() != null) {
			logger.remove();
		}
	}

	// Method to set ExtentReports in current thread
	public static synchronized void setExtentReports(ExtentReports extentReports) {
		extent_Report.set(extentReports);
	}

	/**
	 * This method is to initialize the excel class and allow reading of TCs from
	 * this sheet
	 * 
	 * @param inputExcel
	 */
	public void getInputSheetName(String inputExcel) {
		try {
			if (xl.get() == null) {
				xl.set(new Xls_Reader(currentPath + File.separator + inputExcel, sheetName));
			}
		} catch (Exception e) {
			util.catchError(getLogger(), driver, e, "getInputSheetName() '" + sheetName + "'");
		}
	}

	/**
	 * This method is to initialize the excel class and allow reading of TCs from
	 * the sheet provided by parameter
	 * 
	 * @param inputSheet
	 * @param newSheetName
	 */
	public void getInputSheetName(String inputSheet, String newSheetName) {
		sheetName = newSheetName;
		getInputSheetName(inputSheet);
	}

	/**
	 * Fetch TC data from excel intro threadLocalExcelData
	 * 
	 * @param testcaseName
	 */
	public synchronized void getInputDataFromExcel(String testcaseName) {

		/**
		 * The synchronized block ensures that only one thread at a time can execute the
		 * code inside the block.
		 * It uses xl as a lock object to control access to the shared
		 * resource in a multi-threaded environment.
		 */
		synchronized (xl) {
			try {
				// Thread currentThread = Thread.currentThread();
				// util.loggerMessage(getLogger(), "Thread Number: " + currentThread.getId());

				if (xl.get() == null) {
					util.loggerMessage(getLogger(), false, "xl is Null");
					Assert.assertTrue(false, "xl is Null at ");
				}

				threadLocalExcelData.set(xl.get().getFullTC_Data(testcaseName));

				printMap(testcaseName);
			} catch (IllegalArgumentException e) {
				util.catchError(null, driver, e, "find Test Name: " + testcaseName + " on Excel sheet.");
			} catch (Exception x) {
				util.catchError(null, driver, x, "run getInputDataFromExcel() --> " + testcaseName);
			}
		}
	}

	/**
	 * Method for securely obtaining Excel data for each thread
	 * 
	 */
	public Map<String, Map<String, String>> getExcelData() {
		return threadLocalExcelData.get();
	}

	/**
	 * This method for displaying threadLocalExcelData.get() map values
	 * 
	 * @param testcaseName
	 */
	public void printMap(String testcaseName) {
		int totalLength = 140;
		int starLength = (totalLength - testcaseName.length() - 19) / 2;
		String stars = String.join("", Collections.nCopies(starLength, "*"));
		System.out.println(stars + " TC: " + testcaseName + " INPUT_DATA " + stars);

		for (Map.Entry<String, Map<String, String>> entry : getExcelData().entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
			System.out.println();
		}

		System.out.println(String.join("", Collections.nCopies(totalLength, "*")));
	}

	public List<String> getColumnValues(String columnName) {
		List<String> columnValues = new ArrayList<>();
		for (Map.Entry<String, Map<String, String>> row : getExcelData().entrySet()) {
			Map<String, String> rowData = row.getValue();
			if (rowData.containsKey(columnName)) {
				columnValues.add(rowData.get(columnName));
			}
		}
		return columnValues;
	}

	public String getSpecificValue(String row, String col) {
		return getSpecificValue(null, row, col);
	}

	public String getSpecificValue(ExtentTest logger, String row, String col) {
		String data = null;
		try {
			data = getExcelData().get(row).get(col).toString();
			util.loggerMessage(logger, "read [" + row + "," + col + "] = " + data);
			return data;

		} catch (NullPointerException e) {
			util.catchError(logger, driver, e,
					"read [" + row + "," + col + "].Data is null. Either Row or column in Excel sheet.");
		} catch (Exception e) {
			util.catchError(logger, driver, e, "read [" + row + "," + col + ".Return an ERROR");
		}
		return data;
	}

	/*
	 * Opening New Browser and redirecting to Specified URL
	 * 
	 */
	public void openBrowserOnUrl(ExtentTest logger, String url) {
		browserName = getExcelData().get("navigate").get("Browser").toString();
		driver = browserHelper.startBrowser(logger, browserName);
		navigate(logger, url);
	}

	/*
	 * Redirect to Specified URL
	 * 
	 */
	public void navigate(ExtentTest logger, String url) {
		try {
			driver.navigate().to(url);
			util.loggerMessage(logger, true, "Navigating to <b>" + url + "</b>");
		} catch (Exception e) {
			util.catchError(logger, driver, e, "Opening: " + url);
		}
	}
}
