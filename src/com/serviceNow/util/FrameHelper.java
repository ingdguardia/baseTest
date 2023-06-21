/**
 * 
 */
package com.serviceNow.util;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class FrameHelper {

	public static String parentHandle;
	static ArrayList<String> windowHandleListPrimary = new ArrayList<String>();
	Utility util = new Utility();

	/**
	 * This method will Switch to Specified frame
	 */
	public void switchToFrame(ExtentTest logger, WebDriver driver, WebElement iFrame) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
			logger.log(Status.PASS, "Switch to <b>" + iFrame + "</b> Success");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "switchToFrame");
		}
	}

	/**
	 * This method will return Parent window handler
	 * 
	 * @param logger
	 * @param driver
	 * @return
	 */
	public String getParentWindowHandle(ExtentTest logger, WebDriver driver) {
		try {
			parentHandle = driver.getWindowHandle();
			System.out.println(parentHandle);
			logger.log(Status.PASS, "The parent window handler is <b>" + parentHandle + "</b> Success");
			return parentHandle;
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "getParentWindowHandle");
		}
		return "";
	}

	/**
	 * This method will useful switching to child window
	 * 
	 * @param logger
	 * @param driver
	 * @param parentHandle
	 */
	public void switchToChildWindow(ExtentTest logger, WebDriver driver, String parentHandle) {
		try {
			Thread.sleep(5000);
			for (String winHandle : driver.getWindowHandles()) {
				if (!winHandle.equals(parentHandle)) {
					driver.switchTo().window(winHandle);
					logger.log(Status.PASS, "Switching to child window is  Success");
				}
			}
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "switchToChildWindow");
		}
	}

	/**
	 * 
	 * This method for switch back to parent window
	 * 
	 * @param logger
	 * @param driver
	 * @param parentHandle
	 */
	public void switchToParentWindowHandle(ExtentTest logger, WebDriver driver, String parentHandle) {
		try {
			System.out.println("Switching back to parent Window-----------" + parentHandle);
			driver.switchTo().window(parentHandle);
			logger.log(Status.PASS, "Switching to parent window <b>" + parentHandle + "</b>is  Success");
			System.out.println("Switching back to parent Window");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "switchToParentWindowHandle");
		}
	}

	/**
	 * This method for Switch back to Default Frame
	 * 
	 * @param logger
	 * @param driver
	 */
	public void switchToDefaultFrame(ExtentTest logger, WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
			logger.log(Status.PASS, "Switching to Default frame is  Success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed: Switch to default frame due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method clears an arraylist--- windowHandleListPrimary
	public void clearWindowHandleArrayList(ExtentTest logger) {
		try {
			System.out.println("EMPTYING_ARRAYLIST");
			windowHandleListPrimary.clear();
			logger.log(Status.PASS, "Clearing ArrayList is success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to clear arraylist due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Close Child window, popups
	public void closeChildWindow(ExtentTest logger, WebDriver driver, String handle) {
		try {
			driver.close();
			switchToParentWindowHandle(logger, driver, handle);
			logger.log(Status.PASS, "Close Child window");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to close child window");
		}
	}

	// The method waits till number of windows increases by one than the current
	// @param-number of browser windows
	public boolean waitTillNoOfWindows(ExtentTest logger, WebDriver driver, final int numberOfWindows) {
		System.out.println("Waiting till number of windows increases");
		try {
			new WebDriverWait(driver, Duration.ofSeconds(60)) {
			}.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					System.out.println(driver.getWindowHandles().size() + "---" + numberOfWindows);
					return (driver.getWindowHandles().size() == numberOfWindows);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// The method switches to a window after clicking on an element
	public void SwitchToMultipleWindows(ExtentTest logger, WebDriver driver) {
		System.out.println("main window " + parentHandle);
		System.out.println("SWITCHING_TO_NEW WINDOW");
		System.out.println("primary size" + windowHandleListPrimary.size());
		try {
			System.out.println("primary size" + windowHandleListPrimary.size());
			if (!(windowHandleListPrimary.contains(parentHandle))) {
				windowHandleListPrimary.add(parentHandle);
			}
			int currentNoOfHandle = windowHandleListPrimary.size();
			System.out.println("currentHandle" + currentNoOfHandle);
			waitTillNoOfWindows(logger, driver, currentNoOfHandle + 1);
			ArrayList<String> windowHandleListSecondary = new ArrayList<String>(driver.getWindowHandles());
			String handleToSwitch = null;
			for (int secondaryListIndex = 0; secondaryListIndex < windowHandleListSecondary
					.size(); secondaryListIndex++) {
				if (!(windowHandleListPrimary.contains(windowHandleListSecondary.get(secondaryListIndex)))) {
					handleToSwitch = windowHandleListSecondary.get(secondaryListIndex);
					windowHandleListPrimary.add(handleToSwitch);
					break;
				}
			}
			driver.switchTo().window(handleToSwitch);
			System.out.println("switched handle" + handleToSwitch);
			logger.log(Status.PASS, "Switching to new window is success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed: Switch to new window due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// The method switches back to a particular window by providing window number
	// (e.g. 2k) in data sheet
	public void SwitchBackToMultipleWindows(ExtentTest logger, WebDriver driver, String data) {
		System.out.println("Switching back to previous window");
		try {
			int windowNumToSwitch = Integer.parseInt(data.split("k")[0]);
			driver.switchTo().window(windowHandleListPrimary.get(windowNumToSwitch - 1));
			System.out.println("back handle" + windowHandleListPrimary.get(windowNumToSwitch - 1));
			logger.log(Status.PASS, "Switching back to particular window is  Success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed: Switching back to particular window due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	// The method removes window handle from array list
	public void removeHandle(ExtentTest logger, String data) {
		System.out.println("Removing window Handle");
		try {
			int handleToRemove = Integer.parseInt(data.split("k")[0]);
			System.out.println("Removed Handle" + windowHandleListPrimary.get(handleToRemove - 1));
			windowHandleListPrimary.remove(handleToRemove - 1);
			logger.log(Status.PASS, "Removing window Handle is  Success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed: Removing window Handle due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}
}