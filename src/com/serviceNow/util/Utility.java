package com.serviceNow.util;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Utility extends ReportHelper {

	String requestId = "";
	public Wait wait = new Wait();

	/**
	 * This method for wait or pause up to specified time
	 * 
	 * @param waitTime
	 * @param logger
	 */
	public void pause(ExtentTest logger, String waitTime) {
		try {
			long time = (long) Double.parseDouble(waitTime);
			Thread.sleep(time * 1000L);
			logger.log(Status.PASS, "wait <b>" + waitTime + "</b> Seconds");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to wait <b>" + waitTime + " Seconds</b> due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify the whether the element is displayed or not
	 * 
	 * @param logger
	 * @param object
	 * @return
	 */
	public boolean verifyElementByXpath(ExtentTest logger, WebElement object) {
		try {
			if (object.isDisplayed()) {
				// System.out.println("The Object is present ");
				logger.log(Status.PASS, "WebElement is displayed");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed: Element  <b>" + object + "</b> not displayed due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
			return false;
		}
		return false;
	}

	/**
	 * This method will verify the whether the element is displayed or not
	 * 
	 * @param logger
	 * @param object
	 * @return
	 */
	public boolean verifyElementNotDisplayed(ExtentTest logger, WebElement object) {
		try {
			if (!(object.isDisplayed())) {
				// System.out.println("The Object is present ");
				logger.log(Status.PASS, "WebElement is not displayed");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed: Element  <b>" + object + "</b> displayed due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
			return false;
		}
		return false;
	}

	// Method will verify whether alert is present or not
	public boolean isAlertPresent(ExtentTest logger, WebDriver driver) {
		boolean foundAlert = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
			logger.log(Status.INFO, "Alert is Present");
		} catch (Exception e) {
			logger.log(Status.INFO, "Alert is not Present");
		}
		return foundAlert;
	}

	/**
	 * This method will return specified element value
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public String getElementValue(ExtentTest logger, WebElement element) {
		try {
			requestId = element.getAttribute("value");
			logger.log(Status.PASS, "Element value taken :" + requestId + " <b>" + requestId + "</b");
			System.out.println(requestId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to take Element <b>" + element + "</b>Value due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
		return requestId;
	}

	/**
	 * This method will return specified drop down[Select Box] Value
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public String getSelectedValueFromDropdown(ExtentTest logger, WebElement element) {
		try {
			Select dropList = new Select(element);
			// System.out.println(dropList.getFirstSelectedOption());
			requestId = dropList.getFirstSelectedOption().getText();
			logger.log(Status.INFO, "Dropdown Value: <b>" + requestId + "</b");
			logger.log(Status.PASS, "Getting dropdown values is Success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to get Dropdown <b>" + element + "</b>value due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
		return requestId;
	}

	/**
	 * This method for Mouse hover on Specified element
	 * 
	 * @param logger
	 * @param driver
	 * @param element
	 */
	public void mouseHover(ExtentTest logger, WebDriver driver, WebElement element) {
		try {
			Actions builder = new Actions(driver);
			// Move cursor to the Main Menu Element
			builder.moveToElement(element).perform();
			logger.log(Status.PASS, "Mouse Hover to " + element + " is success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed:  Mouse hover on <b>" + element
					+ "</b> element due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * Verifying Drop down[Select box] options
	 * 
	 * @param object
	 * @param optionsList
	 * @return
	 */
	public void verifyDropdownValues(ExtentTest logger, WebElement object, String optionsList) {
		try {
			List<WebElement> dropListContents = object.findElements(By.tagName("option"));
			String valuesList[] = optionsList.split("\\|");
			logger.log(Status.INFO,
					"Application list size" + dropListContents.size() + "Expected List Size" + valuesList.length);
			if (valuesList.length != dropListContents.size()) {
				logger.log(Status.FAIL, "Dropdown list size and Options size are different");
			}
			for (int i = 0; i < dropListContents.size(); i++) {
				if (!dropListContents.get(i).getText().contains(valuesList[i])) {
					logger.log(Status.FAIL, "Failed to verify dropdown list values due to <b>" + valuesList[i]
							+ " option not available in Dropdown list</b>");
				} else {
					logger.log(Status.INFO, "Option" + valuesList[i] + "is available in dropdown");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed:  to verify dropdown list values due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	// Verify List values
	public void verifyListValues(ExtentTest logger, WebDriver driver, String object, String optionsList) {
		try {
			List<WebElement> dropListContents = driver.findElements(By.xpath(object));
			String valuesList[] = optionsList.split("\\|");
			logger.log(Status.INFO,
					"Application list size" + dropListContents.size() + "Expected List Size" + valuesList.length);
			System.out.println(dropListContents.size());
			for (int i = 0; i < valuesList.length; i++) {
				int flag = 0;
				for (int j = 0; j < dropListContents.size(); j++) {
					if (dropListContents.get(j).getText().contains(valuesList[i])) {
						logger.log(Status.INFO,
								"Expected value is present in application list" + dropListContents.get(j).getText());
						System.out.println(
								"Expected value is present in application list" + dropListContents.get(j).getText());
						break;
					} else {
						flag = flag + 1;
					}
					if (flag == dropListContents.size()) {
						logger.log(Status.FAIL, "Expected value is not present in application list"
								+ dropListContents.get(j).getText());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed:  to verify dropdown list values due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method for selecting a option in Drop down by Visible Text
	 * 
	 * @param logger
	 * @param we
	 * @param value
	 */
	public void selectBoxByValue(ExtentTest logger, WebElement element, String value) {
		try {
			Thread.sleep(3000);
			;
			Select dropList = new Select(element);
			dropList.selectByVisibleText(value);
			logger.log(Status.PASS, "<b>" + value + "</b> value is selected from Dropdown");
			System.out.println(value + " is getting selected from dropdown");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to select value from drop down due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify whether the webElement is disabled or not
	 * 
	 * @param webElement
	 * @return
	 */
	public void isDisabled(ExtentTest logger, WebElement webElement) {
		try {
			if (!webElement.isEnabled())
				logger.log(Status.PASS, "Element <b>" + webElement + "</b> is disabled");
			else
				logger.log(Status.FAIL, "Element <b>" + webElement + "</b> is enabled");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method will verify element is enabled
	public void isEnabled(ExtentTest logger, WebElement webElement) {
		try {
			if (webElement.isEnabled())
				logger.log(Status.PASS, "Element <b>" + webElement + "</b> is enabled");
			else
				logger.log(Status.FAIL, "Element <b>" + webElement + "</b> is disabled");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method verifies element is disabled
	public void verifyElementDisabled(ExtentTest logger, WebElement we) {
		try {
			String b = we.getAttribute("readonly");
			if (b.equals("true")) {
				logger.log(Status.PASS, "Element <b>" + we + "</b> is disabled");
			} else {
				logger.log(Status.FAIL, "Element <b>" + we + "</b> is enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method verifies element is disabled
	public void verifyElementReadonly(ExtentTest logger, WebElement we) {
		try {
			String b = we.getAttribute("readonly");
			System.out.println("attribute value" + b);
			if (b.equals("readonly")) {
				logger.log(Status.PASS, "Element <b>" + we + "</b> is disabled");
			} else {
				logger.log(Status.FAIL, "Element <b>" + we + "</b> is enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method verifies element is disabled based on some attribute
	public void verifyElementReadonlyBasedOnAttribute(ExtentTest logger, WebElement we, String attribute) {
		try {
			String b = we.getAttribute(attribute);
			if (b.equals("readonly")) {
				logger.log(Status.PASS, "Element <b>" + we + "</b> is disabled");
			} else {
				logger.log(Status.FAIL, "Element <b>" + we + "</b> is enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed to check whether the element is Disabled or not due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will compare two strings and return true only if both strings are
	 * same
	 * 
	 * @param logger
	 * @param actual
	 * @param expected
	 * 
	 */
	public void compareTwoStrings(ExtentTest logger, String actual, String expected) {
		try {
			if (actual.equalsIgnoreCase(expected)) {
				logger.log(Status.PASS, "Comparing " + actual + "and " + expected + " success");
			} else {
				logger.log(Status.FAIL, "Comparing " + actual + "and " + expected + " failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to compare strings due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	/**
	 * This method is useful for clicking Java Script enabled elements
	 * 
	 * @param driver
	 * @param element
	 * @return status[ boolean type]
	 */
	public void clickByJavascriptExecutor(ExtentTest logger, WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			logger.log(Status.PASS, "Clicking WebElement by using JavaScriptExecutor success");
		} catch (Exception e) {
			catchError(logger, driver, e, "Failed to click on web element due to <b style='color:red'>");
		}
	}

	/**
	 * This method will take print error Message and Screen Shot and also
	 * screenShot will attached to report
	 * 
	 * @param logger
	 * @param exception
	 * @param driver
	 * @param fileName
	 */
	// this method is to select option by giving wild card value instead of complete
	// text
	public void selectOptionFromList(ExtentTest logger, WebElement element, String value) {
		try {
			Select sel = new Select(element);
			List<WebElement> list = sel.getOptions();
			for (WebElement option : list) {
				if (option.getText().contains(value)) {
					sel.selectByVisibleText(option.getAttribute("value"));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to select value from drop down due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify Text based on value attribute
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public void verifyExactTextByValue(ExtentTest logger, WebElement we, String expected) {
		try {
			String actual = we.getAttribute("value");
			if (actual.trim().equalsIgnoreCase(expected.trim())) {
				logger.log(Status.PASS, "Strings Matched" + expected + "--" + actual);
			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(Status.FAIL, "Strings not Matched");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method will verify element's empty value
	public void verifyNullTextByValue(ExtentTest logger, WebElement we) {
		try {
			String actual = we.getAttribute("value");
			if (actual.isEmpty()) {
				System.out.println("strings are matching");
				logger.log(Status.PASS, "Strings Matched");
			} else {
				System.out.println("strings are not matching");
				logger.log(Status.FAIL, "Strings not Matched");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// /**
	// * This method will click on a particular webElement
	// *
	// * @param logger
	// * @param webElement
	// */
	// public void clickOn(ExtentTest logger, WebElement we) {
	// try {
	// we.click();
	// logger.log(Status.PASS, "Element Clicked");
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.log(Status.FAIL, "Failed to click on element due to <b
	// style='color:red'>" + e.getClass() + "<br>"
	// + e.getMessage() + "</b>");
	// }
	// }

	/**
	 * This method will send keys to a particular webElement
	 * 
	 * @param logger
	 * @param webElement
	 * @param string     needs to be enter
	 */
	public void setText(ExtentTest logger, WebElement we, String data) {
		try {
			we.sendKeys(data);
			logger.log(Status.PASS, "Keys sent");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method provides enter key
	public void provideEnterKey(ExtentTest logger, WebElement we) {
		try {
			we.sendKeys(Keys.ENTER);
			logger.log(Status.PASS, "Keys sent");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method provides delete key
	public void provideDeleteKey(ExtentTest logger, WebElement we, String data) {
		try {
			logger.log(Status.INFO, data);
			we.sendKeys(Keys.DELETE);
			logger.log(Status.PASS, "Keys sent and enter pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to send keys and enter due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method provides control key
	public void provideCtrlAKey(ExtentTest logger, WebElement we, String data) {
		try {
			logger.log(Status.INFO, data);
			we.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			logger.log(Status.PASS, "Keys sent and enter pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to send keys and enter due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will send keys to a particular WebElement and press Enter
	 * 
	 * @param logger
	 * @param webElement
	 * @param string     needs to be enter
	 */
	public void setTextWithEnter(ExtentTest logger, WebElement we, String data) {
		try {
			we.sendKeys(data + Keys.ENTER);
			logger.log(Status.PASS, "Keys sent and enter pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to send keys and enter due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method press right arrow key
	public void pressRightKey(ExtentTest logger, WebElement we) {
		try {
			we.sendKeys(Keys.ARROW_RIGHT);
			logger.log(Status.PASS, "Right Arrow Key is pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method press left arrow key
	public void pressLeftKey(ExtentTest logger, WebElement we) {
		try {
			we.sendKeys(Keys.ARROW_LEFT);
			logger.log(Status.PASS, "Left Arrow key is pressed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed to send keys <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will select multiple options from dropdown by pressing ctrl key
	 * 
	 * @param logger
	 * @param driver
	 * @param webElement
	 * @param option     value needs to be selected
	 */
	public void selectMultipleValuesFromDropdown(ExtentTest logger, WebDriver driver, WebElement we, String data) {
		try {
			String multipleSel[] = data.split(",");
			// System.out.println(multipleSel.length);
			Select dropdown = new Select(we);
			List<WebElement> options = dropdown.getOptions();
			Actions builder = new Actions(driver);
			boolean isMultiple = dropdown.isMultiple();
			if (isMultiple) {
				dropdown.deselectAll();
			}
			builder.keyDown(Keys.CONTROL);
			// for (WebElement webElement : options) {
			// // System.out.println(webElement.getText());
			// }
			// System.out.println("key pressed"+options.size());
			for (String textOption : multipleSel) {
				for (WebElement option : options) {
					String optionText = option.getText().trim();
					if (optionText.equalsIgnoreCase(textOption)) {
						if (isMultiple) {
							if (!option.isSelected()) {
								builder.click(option);
							}
						} else {
							option.click();
						}
						break;
					}
				}
			}
			builder.keyUp(Keys.CONTROL).build().perform();
			logger.log(Status.PASS, "Multiple values are selected from dropdown by pressing control key");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to select multiple values from dropdown due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will deselect all options of a dropdown
	 * 
	 * @param logger
	 * @param webElement
	 */
	public void deselectDropdown(ExtentTest logger, WebElement we) {
		try {
			Select dropdown = new Select(we);
			dropdown.getOptions();
			if (dropdown.isMultiple()) {
				dropdown.deselectAll();
			}
			logger.log(Status.PASS, "Dropdown is deselected completely");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Dropdown is not deselected due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will select option from a Model box one by one
	 * 
	 * @param logger
	 * @param webElement
	 * @param Left       or Right arrow button
	 * @param option     needs to be selected
	 */
	public void selectOptionsFromBox(ExtentTest logger, WebElement dropDown, WebElement button, String data) {
		try {
			String value[] = data.split(",");
			Select dropList = new Select(dropDown);
			for (int i = 0; i < value.length; i++) {
				dropList.selectByVisibleText(value[i]);
				button.click();
			}
			logger.log(Status.PASS, "Values are selected from dropdown one by one");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to select values from dropdown one by one due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify first selected option from a dropdown
	 * 
	 * @param logger
	 * @param webElement
	 * @param Expected   Value needs to be verified
	 */
	public void verifySelectedValueInDropdown(ExtentTest logger, WebElement element, String expectedValue) {
		try {
			Select dropList = new Select(element);
			// System.out.println(dropList.getFirstSelectedOption());
			String selectedValue = dropList.getFirstSelectedOption().getText().trim();
			if (selectedValue.trim().equalsIgnoreCase(expectedValue)) {
				logger.log(Status.PASS, "Expected and Application Selected Values is matched");
				logger.log(Status.PASS, "Expected Value: " + expectedValue);
				logger.log(Status.PASS, "Application Value: " + selectedValue);
			} else {
				logger.log(Status.FAIL, "Expected and Application Selected Values are not matched");
				logger.log(Status.FAIL, "Expected Value: " + expectedValue);
				logger.log(Status.FAIL, "Application Value: " + selectedValue);
			}
			logger.log(Status.PASS, "Fetching selected value from dropdown is success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to retrieve value from dropdown <b>" + element
					+ "</b>value due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will clear a particular webElement
	 * 
	 * @param logger
	 * @param webElement
	 */
	public void clearText(ExtentTest logger, WebElement we) {
		try {
			we.clear();
			logger.log(Status.PASS, "Element Text cleared");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to clear element text due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method select option from dropdown by index
	public void selectOptionFromListByIndex(ExtentTest logger, WebElement element, int index) {
		try {
			Select sel = new Select(element);
			List<WebElement> options = sel.getOptions();
			if (index < 0 || index >= options.size()) {
				logger.log(Status.FAIL, "Invalid index provided: " + index);
				return;
			}
			sel.selectByIndex(index);
		} catch (Exception e) {
			logger.log(Status.FAIL, "Failed to select option from dropdown due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify application value same as expected value
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public void verifyExactText(ExtentTest logger, WebElement we, String expected) {
		try {
			String actual = we.getText();
			if (actual.trim().equalsIgnoreCase(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(Status.PASS, "Actual Contains Expected");
				logger.log(Status.PASS, "strings are matching" + expected + "--" + actual);
			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(Status.FAIL, "Actual does not contain expected");
				logger.log(Status.FAIL, "strings are not matching" + expected + "--" + actual);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will accept/dismiss alert
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public void manageAlert(ExtentTest logger, WebDriver driver, String operation) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.alertIsPresent());
			if (operation.equalsIgnoreCase("accept")) {
				Thread.sleep(3000);
				driver.switchTo().alert().accept();
				logger.log(Status.PASS, "alert accepted successfully");
				System.out.println("alert accepted successfully");
			} else if (operation.equalsIgnoreCase("dismiss")) {
				Thread.sleep(3000);
				driver.switchTo().alert().dismiss();
				logger.log(Status.PASS, "alert dismissed successfully");
				System.out.println("alert dismissed successfully");
			} else {
				logger.log(Status.FAIL, "invalid string input");
				System.out.println("invalid string input");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to get <b>Alert<b> due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify alert's application value same as expected value
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public String verifyAlertText(ExtentTest logger, WebDriver driver, String expectedMessage) {
		String actualMessage = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.alertIsPresent());
			actualMessage = driver.switchTo().alert().getText();
			if (actualMessage.trim().contains(expectedMessage.trim())) {
				logger.log(Status.PASS,
						"Actual Message is verified: " + actualMessage + "Expected Message:" + expectedMessage);
			} else {
				logger.log(Status.FAIL,
						"Actual Message is not verified: " + actualMessage + "Expected Message: " + expectedMessage);
				System.out.println("alert accepted successfully but message is not matched");
			}
			return actualMessage;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to get <b>Alert Message<b> due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
			return actualMessage;
		}
	}

	/**
	 * This method will accept alert if there is any alert
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public void acceptAlertIfAny(ExtentTest logger, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
			logger.log(Status.PASS, "alert accepted successfully");
			System.out.println("alert is present");
		} catch (Exception e) {
			System.out.println("No Alert present");
		}
	}

	/**
	 * This method will return specified element text
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public String getElementText(ExtentTest logger, WebElement element) {
		String text = "";
		try {
			text = element.getText();
			logger.log(Status.PASS, "Element text taken :" + text + "</b");
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to take Element <b>" + element + "</b>text due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
		return text;
	}

	/**
	 * This method will verify expected text contains actual text
	 * 
	 * @param logger
	 * @param element
	 * @return
	 */
	public void verifyTextContains(ExtentTest logger, WebElement we, String expected) {
		try {
			String actual = we.getText();
			if (actual.trim().contains(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(Status.PASS, "Actual Contains Expected" + expected + "--" + actual);
			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(Status.FAIL, "Actual does not contain expected" + expected + "--" + actual);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// This method will verify expected text contains actual text by value attribute
	public void verifyTextContainsByValue(ExtentTest logger, WebElement we, String expected) {
		try {
			String actual = we.getAttribute("value");
			if (actual.trim().contains(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(Status.PASS, "Actual Contains Expected");
			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(Status.FAIL,
						"Actual does not contain expected, strings are not matching " + expected + "--" + actual);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	/**
	 * This method will verify the whether the element is displayed or not
	 * 
	 * @param logger
	 * @param object
	 * @return
	 */
	public int verifyElementNotPresentByXpath(ExtentTest logger, String xpath, WebDriver driver) {
		int flag = 0;
		try {
			if (driver.findElements(By.xpath(xpath)).size() == 0) {
				// System.out.println("The Object is present ");
				logger.log(Status.PASS, "WebElement" + xpath + " is not displayed");
				flag = 0;
			} else {
				logger.log(Status.FAIL, "WebElement" + xpath + " is displayed");
				flag = 1;
			}
			return flag;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed: Element  <b>" + xpath + "</b> displayed due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
			return flag;
		}
	}

	// Method returns integer value(more than zero) if element is present
	public int verifyElementPresentByXpath(ExtentTest logger, String xpath, WebDriver driver) {
		int flag = 0;
		try {
			if (driver.findElements(By.xpath(xpath)).size() == 0) {
				// System.out.println("The Object is present ");
				logger.log(Status.FAIL, "WebElement" + xpath + " is not displayed");
			} else {
				logger.log(Status.PASS, "WebElement" + xpath + " is displayed");
				flag = 1;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed: Element  <b>" + xpath + "</b> displayed due to <b style='color:red'>"
					+ e.getClass() + "<br>" + e.getMessage() + "</b>");
			return flag;
		}
	}

	/**
	 * This method will verify alert's application value should not same as expected
	 * value
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public String verifyAlertDoesNotContainsText(ExtentTest logger, WebDriver driver, String expectedMessage) {
		String actualMessage = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.alertIsPresent());
			actualMessage = driver.switchTo().alert().getText();
			if (!(actualMessage.trim().contains(expectedMessage.trim()))) {
				logger.log(Status.PASS,
						"alert Message Verified" + actualMessage + "does not contain ---" + expectedMessage);
			} else {
				logger.log(Status.FAIL,
						"alert Message not verified" + actualMessage + " should not contain ---" + expectedMessage);
				System.out.println("alert accepted successfully but message is not matched");
			}
			return actualMessage;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to get <b>Alert Message<b> due to <b style='color:red'>" + e.getClass()
					+ "<br>" + e.getMessage() + "</b>");
			return actualMessage;
		}
	}

	/**
	 * This method will generate any random number between 1-100
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public int generateRandomNumber(ExtentTest logger, WebDriver driver) {
		int randomInt = 0;
		try {
			// generate unique number using Random class
			Random randomGenerator = new Random();
			randomInt = randomGenerator.nextInt(100);
			logger.log(Status.PASS, "Random number is generated" + randomInt);
			return randomInt;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed to generate <b>random number<b> due to <b style='color:red'>" + e.getClass()
							+ "<br>" + e.getMessage() + "</b>");
			return randomInt;
		}
	}

	/**
	 * This method is useful for scrolling via Java script
	 * 
	 * @param driver
	 * @param element
	 * @return status[ boolean type]
	 */
	public void scrollDownByJavascriptExecutor(ExtentTest logger, WebDriver driver) {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			logger.log(Status.PASS, "Scrolling Down by using JavaScriptExecutor success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to scroll down due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method opens a new tab in browser
	public void openNewTab(ExtentTest logger, WebDriver driver) {
		try {
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to switch");
		}
	}

	// Method press right key through robot class
	public void keyPressRight(ExtentTest logger) {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.KEY_LOCATION_RIGHT);
			System.out.println("Enter Key pressed");
			logger.log(Status.PASS, "Key Press Right is success");
			r.keyRelease(KeyEvent.KEY_LOCATION_RIGHT);
		} catch (Exception e) {
			logger.log(Status.FAIL, "Failed to press Right key " + e.getMessage());
		}
	}

	// Method provides input in textfield
	public void setTextValue(ExtentTest logger, WebDriver driver, WebElement we, String value) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='" + value + "';", we);
			System.out.println("Value is set in textfield:" + value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method verifies text based on any attribute
	public void verifyTextByAttribute(ExtentTest logger, WebElement we, String expected, String attribute) {
		try {
			String actual = we.getAttribute(attribute);
			if (actual.trim().contains(expected.trim())) {
				System.out.println("strings are matching" + expected + "--" + actual);
				logger.log(Status.PASS, "Strings Matched");
			} else {
				System.out.println("strings are not matching" + expected + "--" + actual);
				logger.log(Status.FAIL, "Strings not Matched");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method verifies application text contains expected text
	public void checkForContainsText(ExtentTest logger, WebElement we, String data) {
		try {
			String textFromResults = we.getText().toLowerCase().trim();
			if (textFromResults.contains(data.toLowerCase().trim()))
				logger.log(Status.PASS,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			else {
				logger.log(Status.FAIL,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed </b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method verifies application text does not contain expected text
	public void checkForNotContainsText(ExtentTest logger, WebElement we, String data) {
		try {
			String textFromResults = we.getText().toLowerCase().trim();
			if (!(textFromResults.contains(data.toLowerCase().trim())))
				logger.log(Status.PASS,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			else {
				logger.log(Status.FAIL,
						"<b>Actual: </b>" + we.getText().toString() + "<br>" + "<b>Expected: </b>" + data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Failed </b> due to <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	// Method returns number of matching records present
	public int verifyNumberOfRecords(ExtentTest logger, WebElement we) {
		int size = 0;
		try {
			List<WebElement> tableTr = we.findElements(By.tagName("tr"));
			size = tableTr.size();
			System.out.println("Size" + size);
			return size;
		} catch (Exception e) {
			logger.log(Status.FAIL, "Failed to match string due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
			return size;
		}
	}

	// Method returns index of matching column
	public int returnColumnIndex(ExtentTest logger, String xp, String columnName, WebDriver driver) {
		int index = 0;
		try {
			List<WebElement> tableTr = driver.findElements(By.xpath(xp));
			int size = tableTr.size();
			// System.out.println("Size"+size);
			for (int i = 0; i < size; i++) {
				if (tableTr.get(i).getText().contains(columnName)) {
					index = i + 1;
					// System.out.println("Column Index"+index);
					break;
				}
			}
			if (index == 0) {
				logger.log(Status.FAIL, "Column is not found and index is " + index);
			}
			logger.log(Status.PASS, "COlumn is found and index is " + index);
			return index;
		} catch (Exception e) {
			logger.log(Status.FAIL,
					"Failed to get column Index'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return index;
		}
	}

	// Method returns current date& time in given format
	public String getCurrentDateTime(ExtentTest logger, String format) {
		String currentDate = "";
		try {
			DateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date();
			System.out.println(sdf.format(date));
			currentDate = sdf.format(date);
			System.out.println("currentDateTime: " + currentDate);
			logger.log(Status.PASS, "Current Date and Time is formatted");
			return currentDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to get current date time ");
			return currentDate;
		}
	}

	// format: yyyy-MM-dd
	// Method returns next date & time in given format
	public String getNextDateTime(ExtentTest logger, String format, int numberOfDays) {
		String nextDate = "";
		try {
			Date currentDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			int i;
			i = 24 * 3600000 * numberOfDays;
			Date myDate = DateUtils.addMilliseconds(currentDate, i);
			nextDate = sdf.format(myDate);
			System.out.print("Next Date and Time" + nextDate);
			return nextDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Unable to get next date <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return nextDate;
		}
	}

	// Method returns previous date and time in given format
	public String getPreviousDateTime(ExtentTest logger, String format) {
		String previousDate = "";
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			previousDate = sdf.format(cal.getTime());
			System.out.println("Previous Date and Time" + previousDate);
			return previousDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Unable to get next date <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return previousDate;
		}
	}

	// Method returns next month of your current month in given format
	public String getNextMonth(ExtentTest logger, String format) {
		String nextDate = "";
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			nextDate = sdf.format(cal.getTime());
			System.out.println("Next Date and Time" + nextDate);
			return nextDate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,
					"Unable to get next date <b style='color:red'>" + e.getClass() + "<br>" + e.getMessage() + "</b>");
			return nextDate;
		}
	}

	// Method scrolls till element si on centerPosition
	public void scrollByVisibleElement(ExtentTest logger, WebDriver driver, WebElement we) {
		try {
			// Get the height of the browser window
			int windowHeight = driver.manage().window().getSize().getHeight();

			// Calculate the position of the element relative to the top of the page
			int elementPosition = we.getLocation().getY();

			// Get the height of the element
			int elementHeight = we.getSize().getHeight();

			// Calculate the position of the element relative to the center of the screen
			int centerPosition = elementPosition - (windowHeight / 2) + (elementHeight / 2);
			if (centerPosition < 0) {
				centerPosition = 0;
			}

			// Scroll the page to the center position of the element
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,arguments[0]);", centerPosition);

			loggerMessage(logger, true, "Scroll bar is scrolled till element becomes visible");
		} catch (Exception e) {
			catchError(logger, driver, e, "Scroll to " + we);
		}
	}

	// Method focus on a particular element
	public void focusToElement(ExtentTest logger, WebDriver driver, WebElement ele) {
		try {
			new Actions(driver).moveToElement(ele).perform();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Focus to element failed");
		}
	}

	// goto end of page
	public void endOfPage(ExtentTest logger) {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_END);
			r.keyRelease(KeyEvent.VK_END);
			r.keyRelease(KeyEvent.VK_CONTROL);
		} catch (Exception e) {
			logger.log(Status.FAIL, "Failed to press Enter key " + e.getMessage());
		}
	}

	// go to top of page
	public void topOfPage(ExtentTest logger) {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_HOME);
			r.keyRelease(KeyEvent.VK_HOME);
			r.keyRelease(KeyEvent.VK_CONTROL);
		} catch (Exception e) {
			logger.log(Status.FAIL, "Failed to press Enter key " + e.getMessage());
		}
	}

	// copy paste
	public void copypaste(ExtentTest logger) {
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			StringSelection strSel = new StringSelection(System.getProperty("user.dir") + "\\Input.xlsx");
			clipboard.setContents(strSel, null);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			logger.log(Status.FAIL, "Failed to press  " + e.getMessage());
		}
	}

	/**
	 * This method will accept alert
	 * 
	 * @param logger
	 * @param webElement
	 * @param expected   string
	 */
	public void acceptAlert(ExtentTest logger, WebDriver driver) {
		try {
			driver.switchTo().alert().accept();
			logger.log(Status.PASS, "alert accepted successfully");
			System.out.println("alert is present");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, e.getMessage());
		}
	}

	// Provide input in Textfield by using Actions class. Specially used to type in
	// Table cell
	public void setTextUsingActions(ExtentTest logger, WebDriver driver, WebElement we, String data) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(we);
			actions.click();
			actions.sendKeys(data);
			actions.build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to send keys due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
	}

	// Method double clicks on element
	public void doubleClick(ExtentTest logger, WebDriver driver, WebElement we) {
		try {
			Actions actions = new Actions(driver);
			actions.doubleClick(we).perform();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to double click" + e.getClass() + "<br>" + e.getMessage() + "</b>");
		}
	}

	/*
	 * THIS SECTION CONTAINS MANY USEFUL METHODS DEVELOP BY ARGENTINA AUTOMATION
	 * TEAM
	 * CAN BE USED ON SERVICE NOW PROJECTS
	 * 
	 * feel free to add your code as you wish :D
	 */

	// TODO to be updated by Emi

	/**
	 * Verifies the visibility of a field on the backend.
	 * 
	 * @param logger    The logger to log the status of the verification.
	 * @param fieldName The name of the field to verify.
	 * 
	 * @return A boolean value indicating whether the field is visible (`true`) or
	 *         not (`false`).
	 */
	public boolean verifyFieldVisibility_Backend(ExtentTest logger, WebDriver driver, String fieldName) {
		String fieldXpath = "//label/span[contains(., '" + fieldName + "')]";
		if (driver.findElements(By.xpath(fieldXpath)).size() != 0) {
			if (driver.findElement(By.xpath(fieldXpath)).isDisplayed()) {
				loggerMessage(logger, true, "field is visible");
			} else {
				loggerMessage(logger, false, "is NOT visible");
			}
		} else {
			loggerMessage(logger, false, "is NOT visible");
		}
		return driver.findElements(By.xpath(fieldXpath)).size() != 0;
	}

	/**
	 * This method compare 2 List
	 * Get list<webElements>, and store it in a list<String>
	 * Compare this list<String> to another given List<String>
	 * 
	 * @param logger       {@code ExtentTest} log status for the reports
	 * @param listName     {@code String} name of the list
	 * @param wElementList {@code List<WebElement>} List of web elements
	 * @param expectedList {@code List<String>} Expected list
	 * 
	 * @throws Exception when fails, throws an error message on console and logger,
	 *                   with screenshot.
	 *
	 * @see #wait.visibilityOfList(List)
	 * @see #loggerMessage(ExtentTest, String, String)
	 * @see #catchError(ExtentTest, Exception, String)
	 * 
	 * @author Emiliano.molina
	 */
	public void compareListOfElements(ExtentTest logger, WebDriver driver, String listName,
			List<WebElement> wElementList,
			List<String> expectedList) {
		try {
			wait.visibilityOfList(logger, driver, listName, wElementList, 10);
			List<String> list = new ArrayList<String>();

			for (WebElement e : wElementList) {
				list.add(e.getText());
			}

			if (list.equals(expectedList)) {
				loggerMessage(logger, "pass", "List " + listName + " are correct");
			} else {
				loggerMessage(logger, "fail",
						"List " + listName + " are NOT correct. \n List site: " + list + "\n Expected list: "
								+ expectedList);
			}

		} catch (Exception e) {
			catchError(logger, driver, e, "verify list: " + listName);
		}
	}

	/**
	 * This method verify if activity is in the activity log section.
	 * <p>
	 * 
	 * This method iterates on activities on Activity Log Section
	 * and search the expectedField and his expectedValue for each one
	 * <p>
	 * 
	 * @param logger        {@code ExtentTest} log status for reports
	 * @param expectedField {@code String} Name of the expected Activity field
	 * @param expectedValue {@code String} Name of the expected Activity value
	 * 
	 * @see #loggerMessage(ExtentTest, String, String)
	 * @see #catchError(ExtentTest, Exception, String)
	 * 
	 * @Author Emiliano.molina
	 * 
	 */
	public void verifyIfActivityIsNotOnActivityLog(ExtentTest logger, WebDriver driver, String expectedField,
			String expectedValue) {
		try {
			String records = "//li[@class='h-card h-card_md h-card_comments']";
			List<WebElement> wElementList = driver.findElements(By.xpath(records));
			List<String> fieldsRecordsList = new ArrayList<String>();
			if (driver.findElements(By.xpath(records)).size() != 0) {
				for (WebElement e : wElementList) {
					fieldsRecordsList.add(e.getText().trim());
				}
				// System.out.println("RECORD LIST -> " + fieldsRecordsList);
				for (int i = 0; i < fieldsRecordsList.size(); i++) {
					if (!fieldsRecordsList.get(i).contains(expectedField)
							&& !fieldsRecordsList.get(i).contains(expectedValue)) {
						loggerMessage(logger, "pass", "Activity: <b>" + expectedField + "</b>: " + expectedValue
								+ " is NOT visible in activity log-> \n<br>" + fieldsRecordsList.get(i));
						break;
					} else {
						continue;
					}
				}
			} else {
				loggerMessage(logger, "fail", "Activities could not be found");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify if Activity: " + expectedField + " is in Activity Log section");
		}
	}

	/**
	 * This method verify if Attachments was saved and visible in Activity log
	 * 
	 * @param logger         {@code ExtentTest} log status for the reports
	 * @param attachmentName {@code String} Name of the attachment
	 * 
	 * @author Emiliano.molina
	 */
	public void verifyAttachmentsIsOnActivityLog(ExtentTest logger, WebDriver driver, String attachmentName) {
		try {
			wait.visibilityOf(logger, driver, attachmentName, "//a[@file-name='" + attachmentName + "']", 120);
			loggerMessage(logger, "pass", "Attachment: " + attachmentName + " is visible on Activity Log section");
		} catch (Exception e) {
			catchError(logger, driver, e,
					"verify if attachment " + attachmentName + " is visible on Activity Log section");
		}
	}

	/**
	 * This method verifies that the option received as a parameter
	 * is in the correct position.
	 * 
	 * @param logger         {@code ExtentTest} log status for the reports
	 * @param listLocator    {@code By} locator of the dropdown list
	 * @param optionName     {@code String} Name of the option
	 * @param optionPosition {@code Integer} Expected option´s position number
	 * 
	 * @author Emiliano.molina
	 */
	public void verifyDropDownOptionPosition(ExtentTest logger, WebDriver driver, By listLocator, String optionName,
			int optionPosition) {

		List<WebElement> wElementList = driver.findElements(listLocator);
		List<String> optionsList = new ArrayList<String>();

		for (WebElement e : wElementList) {
			optionsList.add(e.getText().trim());
			System.out.println("Option " + e.getText().trim());
		}

		if (optionsList.get(optionPosition).equals(optionName)) {
			loggerMessage(logger, "PASS",
					"Option: " + optionName + " is in the: " + optionPosition + " position of the list");
		} else {
			loggerMessage(logger, "FAIL",
					"Option: " + optionName + " is NOT in the expected position... actual position: " + optionPosition);
		}
	}

	public void verifyDropDownOptionPosition(ExtentTest logger, WebDriver driver, String listLocator, String optionName,
			int optionPosition) {
		By x = By.xpath(listLocator);
		verifyDropDownOptionPosition(logger, driver, x, optionName, optionPosition);
	}

	public String selectSpecificDate(String dateName) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime newDate = today;

		while (newDate.getDayOfWeek() != DayOfWeek.valueOf(dateName)) {

			newDate = newDate.plusDays(1);
		}

		String newDateString = newDate.format(formatter).toString();
		return newDateString;
	}

	/**
	 * <h4>This method remove attachment in backend
	 * <h4/>
	 * 
	 * @param logger
	 */
	public void removeAttachmentBackend(ExtentTest logger, WebDriver driver) {
		try {
			By inputCheckbox = By.xpath("//input[contains(@aria-label,'Select attachment')]"),
					attachmentFieldBackEnd = By.xpath("//button[@id='header_add_attachment']"),
					closeAttachmentModal = By.xpath("//button[@id='attachment_closemodal']");

			if (driver.findElements(attachmentFieldBackEnd).size() != 0) {

				driver.findElement(attachmentFieldBackEnd).click();
				pause(logger, "1");
				driver.switchTo().activeElement();
				System.out.println("Switching to modal window...");
				pause(logger, "1");

				if (driver.findElements(inputCheckbox).size() != 0) {

					driver.findElement(inputCheckbox).click();
					pause(logger, "2");
					driver.findElement(By.xpath("//input[@value='Remove']")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
					wait.until(ExpectedConditions.invisibilityOfElementLocated(inputCheckbox));
					clickOn(logger, driver, "Close modal window", closeAttachmentModal);
					pause(logger, "2");
					loggerMessage(logger, "pass", "File removed");
				}

			} else {
				loggerMessage(logger, "fail", "Manage attachment button could not be found");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "Remove attachment file");
		}
	}

	/**
	 * This method search record on filter navigator
	 * 
	 * @param logger
	 * @param searchBy   {@code String} Name of the option on drop down search
	 * @param recordName {@code String} Name of the record to be searched
	 * 
	 * @see #navigateTo(ExtentTest, String, String, String)
	 * @see #loggerMessage(ExtentTest, String, String)
	 * @see #catchError(ExtentTest, Exception, String)
	 * 
	 * @author Molina Emiliano
	 */
	public void searchOnNotificationTableBy(ExtentTest logger, WebDriver driver, String searchBy, String recordName) {

		String searchField = "//label[.='Search']/following-sibling::input[@placeholder='Search']";
		String filter = "//select[@aria-label='Search a specific field of the Notifications list']";
		String firstOccurrence = "(//a[@class='linked formlink' and contains(text(), '" + recordName + "')])[1]";

		try {
			driver.findElement(By.xpath(filter)).sendKeys(searchBy + Keys.ENTER);
			pause(logger, "2");
			driver.findElement(By.xpath(searchField)).sendKeys(recordName + Keys.ENTER);
			pause(logger, "2");
			driver.findElement(By.xpath(firstOccurrence)).click();
			pause(logger, "9");
			loggerMessage(logger, "pass", "Record: " + recordName + "is visible");

		} catch (Exception e) {
			catchError(logger, driver, e, "Search record: " + recordName);
		}
	}

	/**
	 * This method open a request on Preview Notification Table in Email
	 * Notification Preview
	 * 
	 * @param logger
	 * @param ticketN {@code String} ticket number
	 * 
	 * @author Emiliano.molina
	 */
	public void openRequestNotification(ExtentTest logger, WebDriver driver, String ticketN) {

		String searchField = "//label[.='Search']/following-sibling::input[@placeholder='Search']";
		String filter = "//select[@aria-label='Search a specific field of the SUS Requests list']";
		String firstOccurrence = "(//a[@class='glide_ref_item_link' and contains(text(), '" + ticketN + "')])[1]";

		try {
			if (driver.findElements(By.xpath(filter)).size() != 0) {
				driver.findElement(By.xpath(filter)).sendKeys("Number" + Keys.ENTER);
				pause(logger, "2");
				driver.findElement(By.xpath(searchField)).sendKeys(ticketN + Keys.ENTER);
				pause(logger, "5");
				driver.findElement(By.xpath(firstOccurrence)).click();
				pause(logger, "8");

				System.out.println("Request " + ticketN + " is opened on SUS Request Notification Table");
				logger.log(Status.PASS, "Request " + ticketN + " is opened on SUS Request Notification Table");

			} else {
				frameHelper.switchToFrame(logger, driver, nav.iframe_gsft);
				driver.findElement(By.xpath(filter)).sendKeys("Number" + Keys.ENTER);
				pause(logger, "2");
				driver.findElement(By.xpath(searchField)).sendKeys(ticketN + Keys.ENTER);
				pause(logger, "5");
				driver.findElement(By.xpath(firstOccurrence)).click();
				pause(logger, "8");

				System.out.println("Request " + ticketN + " is opened on SUS Request Notification Table");
				logger.log(Status.PASS, "Request " + ticketN + " is opened on SUS Request Notification Table");
			}

		} catch (Exception e) {
			logger.log(Status.FAIL, "Ticket number:" + ticketN + "could not be found");
			Assert.fail();
		}
	}

	/**
	 * This method verify 'Invalid reference' message when complete a field
	 * on forms in backend
	 * <p>
	 * 
	 * IMPORTANT: First, we must complete the field with invalid data,
	 * then this method validate the error message.
	 * <p>
	 * 
	 * @param logger
	 * @param fieldName {@code String} Field Name
	 * @param fieldPath {@code String} Locator field
	 * 
	 * @author Emiliano.molina
	 * 
	 */
	public void verifyInvalidReferenceMessage(ExtentTest logger, WebDriver driver, String fieldName, By fieldPath) {
		try {

			if (driver.findElements(By.xpath("//div[text()='Invalid reference']")).size() != 0) {
				loggerMessage(logger, "pass",
						fieldName + " error message: "
								+ driver.findElement(By.xpath("//div[text()='Invalid reference']")).getText()
								+ " is displayed -> CORRECT");
			} else {
				loggerMessage(logger, "fail", "Error message could not be found");
			}

		} catch (Exception e) {
			catchError(logger, driver, e, "verify invalid reference message");
		}
	}

	/**
	 * This method returns the created date of ticket
	 * <p>
	 * 
	 * IMPORTANT: 'createdDate' must be initialized globally.
	 * <p>
	 * 
	 * @param logger
	 * @return {@code String} Created date of the ticket in format: 'yyyy-MM-dd'
	 * 
	 * @author Emiliano.molina
	 */
	public String getCreatedDate(ExtentTest logger) {

		String createdDate = "";
		try {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime today = LocalDateTime.now();
			createdDate = today.format(formatter).toString();
			loggerMessage(logger, "info", "Created date -> " + createdDate);

		} catch (Exception e) {
			loggerMessage(logger, "fail", "get the created date of the ticket");
		}
		return createdDate;
	}

	/**
	 * <h3>This method get body innerText of email Templates</h3>
	 * <p>
	 * 
	 * Search 'sys_email_canned_message' in SN modules
	 * and for each email template, gets the text inside its body
	 * and store it in HashMap.
	 * <p>
	 * <p>
	 * 
	 * <b>MAP -> The KEY is the template name, and the VALUE is the body's text</b>
	 * <p>
	 * 
	 * @apiNote Show Application picker in header must be enabled and selected with
	 *          corresponding application. for example 'Responsible Company
	 *          Operations'.
	 *          For activate them, go to settings and developer module,
	 *          and active 'Show application picker in header'
	 * 
	 * @param logger {@code ExtentTest} log status for the reports
	 * 
	 * @author Emiliano.molina
	 * 
	 */
	public Map<String, String> getBodyTemplates(ExtentTest logger, WebDriver driver) {
		Map<String, String> quickMessageList = new HashMap<>();
		try {
			By filterSearch = By
					.xpath("//select[@aria-label='Search a specific field of the Email Client Canned Messages list']");
			By inputSearch = By.xpath("//label[.='Search']/following-sibling::input[@placeholder='Search']");
			By bodyTemplate = By.xpath("//body[@id='tinymce']");
			// searchOnFilterNavigator(logger, "sys_email_canned_message.filter");
			// driver.findElement(filterNavigator).sendKeys(Keys.ENTER);
			wait.iframeAndSwitch(logger, driver, nav.iframe_gsft);
			visibilityAndSendKeys(logger, driver, "Filter Search", filterSearch, "Custom type", 10);
			visibilityAndSendKeys(logger, driver, "Input Search field", inputSearch,
					"Responsible Company Operations", 10);
			wait.visibilityOfList(logger, driver, "", driver.findElements(By.xpath("//a[@class='linked formlink']")),
					10);
			List<WebElement> wElementList = driver.findElements(By.xpath("//a[@class='linked formlink']"));
			for (WebElement e : wElementList) {
				quickMessageList.put(e.getText(), "");
			}
			quickMessageList.forEach((template, body) -> {
				clickOn(logger, driver, "Template: " + template, driver
						.findElement(By.xpath("//a[@class='linked formlink' and contains(.,'" + template + "')]")));
				wait.iframeAndSwitch(logger, driver, nav.iframe_email_canned_message_body);
				wait.visibilityOf(logger, driver, "Body template", bodyTemplate, 10);
				quickMessageList.put(template, body = driver.findElement(By.xpath("//body[@id='tinymce']")).getText());
				// System.out.println(template + " -> " + body); DEBUG
				driver.switchTo().defaultContent();
				wait.iframeAndSwitch(logger, driver, nav.iframe_gsft);
				clickOn(logger, driver, "back button", By.xpath("//button[@aria-label='Back']"));
			});
			System.out.println("All bodies templates are obtained correctly: " + quickMessageList);
			logger.log(Status.PASS, "All bodies templates are obtained correctly: " + quickMessageList);
		} catch (Exception e) {
			catchError(logger, driver, e, "get all body templates");
		}
		return quickMessageList;
	}

	public void verifyFontSize(ExtentTest logger, WebDriver driver, String fontSizeNumber) {
		try {
			By emailTemplate = By.xpath("//body[@id='tinymce']");
			driver.findElement(emailTemplate).click();
			driver.switchTo().defaultContent();
			String fontSize = driver.findElement(By.xpath("//button[@aria-label='Font sizes']/span")).getText();
			if (fontSize.equalsIgnoreCase(fontSizeNumber)) {
				loggerMessage(logger, "pass", "Font size: " + fontSize + " is CORRECT");
			} else {
				loggerMessage(logger, "fail", "Font size: " + fontSize + " is NOT CORRECT");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify font size");
		}
	}

	/**
	 * This method verify that values and position on dropdown
	 * are correct.
	 * <p>
	 * 
	 * Search the locator of the dropdown and store the values in a list.
	 * Compares the List<String> received as parameter, with values on
	 * dropdown. If are equals, throws PASS message. Else throws FAIL message
	 * <p>
	 * 
	 * @param logger
	 * @param dropDownPath    {@code By} Locator of the dropdown
	 * @param dropDownOptions {@code List<String>} List of values on drop down
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void verifyDropDownValues(ExtentTest logger, WebDriver driver, By dropDownPath,
			List<String> dropDownOptions) {
		if (driver.findElements(dropDownPath).size() != 0) {
			List<String> optionList = new ArrayList<String>();
			List<WebElement> wElementList = driver.findElements(dropDownPath);
			for (WebElement e : wElementList) {
				Collections.addAll(optionList, e.getText().split("\n"));
			}
			System.out.println("DROPDOWN WEB OPTION LIST -----> " + optionList);
			System.out.println("DROPDOWN DATA OPTION LIST ----> " + dropDownOptions);
			if (optionList.equals(dropDownOptions)) {
				System.out.println("Drop down is in correct order -> " + optionList);
				logger.log(Status.PASS, "Drop down is in correct order -> " + optionList);
			} else {
				System.out.println("Drop down is NOT in correct order -> " + optionList);
				logger.log(Status.FAIL, "Drop down is NOT in correct order -> " + optionList);
			}
		} else {
			System.out.println("Drop down could not be found");
			logger.log(Status.FAIL, "Drop down could not be found");
		}
	}

	/**
	 * This method verify if activity is in the activity log section.
	 * <p>
	 * 
	 * This method iterates on activities on Activity Log Section
	 * and search the expectedField and his expectedValue for each one
	 * <p>
	 * 
	 * @param logger        {@code ExtentTest} log status for reports
	 * @param expectedField {@code String} Name of the expected Activity field
	 * @param expectedValue {@code String} Name of the expected Activity value
	 * 
	 * @see #loggerMessage(ExtentTest, String, String)
	 * @see #catchError(ExtentTest, Exception, String)
	 * 
	 * @Author Emiliano.molina
	 * 
	 */
	public void verifyIfActivityIsOnActivityLog(ExtentTest logger, WebDriver driver, String expectedField,
			String expectedValue) {
		try {
			String records = "//li[@class='h-card h-card_md h-card_comments']";
			List<WebElement> wElementList = driver.findElements(By.xpath(records));
			List<String> fieldsRecordsList = new ArrayList<String>();
			if (driver.findElements(By.xpath(records)).size() != 0) {
				for (WebElement e : wElementList) {
					fieldsRecordsList.add(e.getText().trim());
				}
				// System.out.println("RECORD LIST -> " + fieldsRecordsList);
				for (int i = 0; i < fieldsRecordsList.size(); i++) {
					if (fieldsRecordsList.get(i).contains(expectedField)
							&& fieldsRecordsList.get(i).contains(expectedValue)) {
						loggerMessage(logger, "pass", "Activity: <b>" + expectedField + "</b>: " + expectedValue
								+ " is visible in activity log-> \n<br>" + fieldsRecordsList.get(i));
						break;
					} else {
						continue;
						// loggerMessage(logger, "fail", "Activity: <b>" + expectedField + "</b> is NOT
						// visible on activity log");
					}
				}
			} else {
				loggerMessage(logger, "fail", "Activities could not be found");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify if Activity: " + expectedField + " is in Activity Log section");
		}
	}

	// This method complete input field with invalid EIDs
	public void completeFieldWhitInvalidData2(ExtentTest logger, WebDriver driver, String fieldName, String value) {

		String inputField = "//span[text()='" + fieldName + "']/following::input[1]";

		if (driver.findElements(By.xpath(inputField)).size() != 0) {

			driver.findElement(By.xpath(inputField)).clear();
			pause(logger, "3");
			driver.findElement(By.xpath(inputField)).click();
			pause(logger, "3");
			driver.findElement(By.xpath(inputField)).sendKeys(value);
			pause(logger, "3");
			System.out.println(fieldName + " is completed with: " + value);
			logger.log(Status.PASS, fieldName + " field is completed with: " + value);

		} else {
			System.out.println("Field '" + fieldName + "'could not be found");
			logger.log(Status.FAIL, "Field '" + fieldName + "' could not be found");
		}
	}

	// This method complete input field with invalidEIDs
	public void completeFieldWhitInvalidData(ExtentTest logger, WebDriver driver, String fieldName, String value) {

		String inputField = "//span[contains(.,'" + fieldName + "')]/following::b[1]";
		String dropdownSelect = "//div[@id='select2-drop']//input";

		if (driver.findElements(By.xpath(inputField)).size() != 0) {

			pause(logger, "2");
			driver.findElement(By.xpath(inputField)).click();
			pause(logger, "4");

			if (driver.findElements(By.xpath(dropdownSelect)).size() != 0) {

				driver.findElement(By.xpath(dropdownSelect)).clear();
				pause(logger, "3");
				driver.findElement(By.xpath(dropdownSelect)).sendKeys(value);
				pause(logger, "3");
				driver.findElement(By.xpath(dropdownSelect)).sendKeys(Keys.ENTER);
				pause(logger, "3");
				System.out.println(fieldName + " is completed with: " + value);
				logger.log(Status.PASS, fieldName + " field is completed with: " + value);

			} else {
				System.out.println(fieldName + " could not be completed");
				logger.log(Status.FAIL, fieldName + " could not be completed");
			}
		} else {
			System.out.println("Field '" + fieldName + "'could not be found");
			logger.log(Status.FAIL, "Field '" + fieldName + "' could not be found");
		}
	}

	// This method complete a field with invalidEID data and verify 'data NOT found'
	// error message
	// fieldName -> Field to be filled
	public void verifyDataNotFoundMessage2(ExtentTest logger, WebDriver driver, String fieldName) {
		By errorMessage_path = By.xpath("//ul[@aria-label='" + fieldName + "']/li");
		String errorMessage_data = driver.findElement(errorMessage_path).getText();
		String inputField = "//span[text()='" + fieldName + "']/following::input[1]";
		if (driver.findElements(errorMessage_path).size() != 0) {
			System.out.println(errorMessage_data + " is visible");
			logger.log(Status.PASS, errorMessage_data + " is visible");
		} else {
			System.out.println(errorMessage_data + " is NOT visible");
			logger.log(Status.FAIL, errorMessage_data + " is NOT visible");
		}
		if (driver.findElements(By.xpath(inputField)).size() != 0) {
			pause(logger, "5");
			driver.findElement(By.xpath(inputField)).clear();
			pause(logger, "2");
			driver.findElement(By.xpath(inputField)).sendKeys(Keys.ESCAPE);
		} else {
			System.out.println("Field could not be cleaned");
		}
	}

	// This method complete a field with invalidEID data and verify 'data NOT found'
	// error message
	// fieldName -> Field to be filled
	public void verifyDataNotFoundMessage(ExtentTest logger, WebDriver driver, String fieldName) {

		By errorMessage_path = By.xpath("//ul[@aria-label='" + fieldName + "']/li");
		String errorMessage_data = driver.findElement(errorMessage_path).getText();
		String dropdownSelect = "//div[@id='select2-drop']//input";
		String clickOut = "//div[@class='select2-drop-mask']";

		if (driver.findElements(errorMessage_path).size() != 0) {

			System.out.println(errorMessage_data + " is visible");
			logger.log(Status.PASS, errorMessage_data + " is visible");

		} else {

			System.out.println(errorMessage_data + " is NOT visible");
			logger.log(Status.FAIL, errorMessage_data + " is NOT visible");
		}

		if (driver.findElements(By.xpath(dropdownSelect)).size() != 0) {

			pause(logger, "5");
			driver.findElement(By.xpath(dropdownSelect)).sendKeys(Keys.ESCAPE);

		} else if (driver.findElements(By.xpath(clickOut)).size() != 0) {
			pause(logger, "5");
			driver.findElement(By.xpath(clickOut)).click();

		} else {
			System.out.println("DropList could not be closed");
		}
	}

	/**
	 * This method delete data from form field whit X button
	 * 
	 * @param logger
	 * @param fieldName {@code String} Name of the field
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void deleteFieldDataWhitXButton(ExtentTest logger, WebDriver driver, String fieldName) {
		By fieldPath = By.xpath("//span[text()='" + fieldName
				+ "']/ancestor::label/ancestor::div[1]/ancestor::div[1]/span/span/div/div/a/abbr");
		By fieldPath2 = By.xpath("//span[text()='" + fieldName + "']/following::a[1]");

		if (driver.findElements(fieldPath).size() != 0) {

			pause(logger, "2");
			driver.findElement(fieldPath).click();
			System.out.println("X button clicked!");
			logger.log(Status.PASS, "X button clicked!");

		} else if (driver.findElements(fieldPath2).size() != 0) {

			pause(logger, "2");
			driver.findElement(fieldPath2).click();
			System.out.println("X button clicked!");
			logger.log(Status.PASS, "X button clicked!");

		} else {

			System.out.println("X button can NOT be clicked!");
			logger.log(Status.FAIL, "X button can NOT be clicked!");
		}
	}

	/**
	 * This method delete option in form field whit x button
	 * 
	 * @param logger
	 * @param optionName {@code String} Name of option to be delete
	 * 
	 * @author Molina Emiliano
	 */
	public void deleteOptionWhitXBtn(ExtentTest logger, WebDriver driver, String optionName) {
		By optionPath = By.xpath("//div[text()='" + optionName + "']/following::a[1]");

		if (driver.findElements(optionPath).size() != 0) {

			pause(logger, "2");
			driver.findElement(optionPath).click();
			System.out.println("Option: " + optionName + " is deleted");
			logger.log(Status.PASS, "Option: " + optionName + " is deleted");
		} else {
			System.out.println("Option: " + optionName + " could not be deleted");
			logger.log(Status.FAIL, "Option: " + optionName + " could not be deleted");
		}
	}

	/**
	 * This method upload a new attachment file
	 * 
	 * @param logger
	 * @param fieldName {@code String} Name of the file
	 * @param file      {@code String} path to file
	 * 
	 * @author Molina Emiliano
	 */
	public void addNewAttachment(ExtentTest logger, WebDriver driver, String fieldName, String file) {

		File attachment2 = new File(file);
		try {
			scrollByVisibleElement(logger, driver, driver.findElement(By.xpath("//sp-attachment-button")));
			pause(logger, "1");
			WebElement uploadAttachment = driver.findElement(By.xpath("//span[@class='file-upload-input']/input"));
			pause(logger, "3");
			uploadAttachment.sendKeys(attachment2.getAbsolutePath());
			pause(logger, "5");
			System.out.println("Input: " + uploadAttachment.getAttribute("value"));

		} catch (Exception e) {
			logger.log(Status.FAIL, "Test Failed -> Verify '" + fieldName + "' text field in Backend");
			screenShotAndErrorMsg(logger, e, driver, "Screenshot");
			Assert.fail();
		}

	}

	/**
	 * This method drag and drop item in kanban board
	 * 
	 * @param logger
	 * @param ticketN    {@code String} ticket Number
	 * @param columnName {@code String} Column Name where element will be dragged
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void dragAndDropItem(ExtentTest logger, WebDriver driver, String ticketN, String columnName) {

		Actions builder = new Actions(driver);
		WebElement from = driver.findElement(By.xpath("//a[text()='" + ticketN + "']/ancestor::li[1]"));
		WebElement to = driver.findElement(By.xpath("//ul[@aria-label='Cards in lane: " + columnName + "']"));

		try {

			builder.dragAndDrop(from, to).perform();
			pause(logger, "9");
			System.out.println("Ticket: " + ticketN + " is dragged to column: " + columnName);
			logger.log(Status.PASS, "Ticket: " + ticketN + " is dragged to column: " + columnName);

		} catch (Exception e) {
			System.out.println("Ticket: " + ticketN + " could not be dragged to column: " + columnName);
			logger.log(Status.FAIL, "Ticket: " + ticketN + " could not be dragged to column: " + columnName);
			Assert.fail();
		}
	}

	/**
	 * THIS METHOD change display view in support portal
	 * 
	 * @param logger        {@code ExtentTest} log status for the reports
	 * @param expectedState {@code String} Expected display view: 'List view', 'Card
	 *                      view'
	 * 
	 */
	public void toggleDisplayView(ExtentTest logger, WebDriver driver, String expectedState) {
		try {
			By displayView = By.xpath("//span[@aria-label='" + expectedState + "']/a");

			if (expectedState.equals("") && !expectedState.equals("List View") && !expectedState.equals("Card View")) {
				loggerMessage(logger, "fail", "expectedState is not correct. Has to be 'Card View' or 'List View'");
			} else {
				wait.elementToBeClickable(logger, driver, "Display view", displayView, 10);
				// System.out.println("Color: " +
				// driver.findElement(displayView).getCssValue("color"));
				if (driver.findElement(displayView).getCssValue("color").equals("#333")) {
					loggerMessage(logger, "info", "Display View is already in: " + expectedState);
				} else {
					clickOn(logger, driver, expectedState + " button", displayView);
				}
			}

		} catch (Exception e) {
			catchError(logger, driver, e, "verify display view");
		}
	}

	/**
	 * This method verify Link on Web element
	 * <p>
	 * 
	 * Receipt as parameter the locator of the element,
	 * and search them in the Web Page. If exist compares
	 * the href attribute of the element whit the expectedURL
	 * receipt as parameter.
	 * <p>
	 * 
	 * @param logger
	 * @param fieldPath   {@code By} Locator of the web element.
	 * @param expectedURL {@code String} Link expected of the web element.
	 * 
	 * @throws Exception When failed, throws an error message on console and logger,
	 *                   with screenshot.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void verifyLink(ExtentTest logger, WebDriver driver, By fieldPath, String expectedURL) {
		try {
			if (driver.findElements(fieldPath).size() != 0) {
				String url = driver.findElement(fieldPath).getAttribute("href");
				if (url.equals(expectedURL)) {
					System.out.println("Link is correct -> " + url);
					logger.log(Status.PASS, "Link is correct -> " + url);
				} else {
					System.out.println("Link is NOT correct -> " + url);
					logger.log(Status.FAIL, "Link is NOT correct -> " + url);
				}
			} else {
				System.out.println("The field that contains the link could not be found");
				logger.log(Status.FAIL, "The field that contains the link could not be found");
			}
		} catch (Exception e) {
			System.out.println("Something went wrong... Link could not be verified");
			logger.log(Status.FAIL, "Something went wrong... Link could not be verified");
			screenShotAndErrorMsg(logger, e, driver, "Something went wrong... Link could not be verified");
			Assert.fail();
		}
	}

	/**
	 * This method verify value on Field's Request Table View.
	 * 
	 * <p>
	 * 
	 * This method receives as a parameter the position of the column in the request
	 * table.
	 * Gets the text of this element and compares it with the text received as a
	 * parameter.
	 * <p>
	 * 
	 * @param logger
	 * @param index          {@code int} position of the column in Request Table
	 *                       View.
	 * @param valueToCompare {@code String} The value to be compared
	 * 
	 * @see #getColumnPosition(ExtentTest, String)
	 * 
	 * @throws Exception When failed, throws an error message on console and logger,
	 *                   with screenshot.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void verifyValueInRequestTable(ExtentTest logger, WebDriver driver, int index, String valueToCompare) {
		By columnPath = By.xpath("//td[@class='vt'][" + index + "]");
		try {
			if (driver.findElements(columnPath).size() != 0) {
				String fieldContent = driver.findElement(columnPath).getText();
				if (fieldContent.isEmpty()) {
					loggerMessage(logger, "Field is empty");
				} else if (fieldContent.equals(valueToCompare)) {
					loggerMessage(logger, "pass", "field value is correct");
				} else {
					loggerMessage(logger, "fail", "Field value is NOT CORRECT -> " + fieldContent);
				}
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify value in request Table");
		}
	}

	/**
	 * This method get the position of the column in backend TABLE
	 * 
	 * <p>
	 * 
	 * This method obtain the name of the column, and search them in Request Table
	 * View,
	 * and return the position of this.
	 * <p>
	 * 
	 * @param logger
	 * @param columnName {@code String} Name of the column
	 * @return {@code int} Return position value of the column.
	 * 
	 * @throws Exception When failed, Throws an error message on console and logger,
	 *                   with screenshot.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public int getColumnPosition(ExtentTest logger, WebDriver driver, String columnName) {
		By columnPath = By.xpath("(//tr[@id='hdr_x_amspi_rco_request'])[1]/th[@glide_label]");
		int index = 0;
		if (driver.findElements(columnPath).size() != 0) {

			List<WebElement> wElementList = driver.findElements(columnPath);
			List<String> optionList = new ArrayList<String>();
			for (WebElement e : wElementList) {
				optionList.add(e.getAttribute("glide_label"));
			}
			System.out.println("OPTION LIST SIZE -->  " + optionList.size());
			System.out.println("OPTION LIST ------->  " + optionList);
			if (optionList.indexOf(columnName) != 0) {
				index = optionList.indexOf(columnName + 1);
				System.out.println("Index -> " + index);
				return index;
			} else {
				System.out.println("Could not get the position of the " + columnName + " column");
				logger.log(Status.FAIL, "Could not get the position of the " + columnName + " column");
			}
		} else {
			System.out.println("Column could not be found");
			logger.log(Status.FAIL, "Column could not be found");
		}
		return index;
	}

	/**
	 * This method verify the hover hover text in element.
	 * <p>
	 * 
	 * Get the text in element doing Hover over through Action
	 * {@code moveToElement},
	 * and compares the text whit other obtained form excel label.
	 * <p>
	 * 
	 * @param logger
	 * @param fieldName {@code String} Title name of the element
	 * @param text      {@code String} Text from excel label
	 * 
	 * @Variables descriptionText {@code String} Text appears after doing hover over
	 * 
	 * @see Actions moveToElement()
	 *
	 * @throws Exception When failed, throws an error message on console and logger.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void verifyTileHoverHoverText(ExtentTest logger, WebDriver driver, String fieldName, String text) {
		try {
			String object = "//a[@techlogs='" + fieldName + "']//div[@class='card__description ng-binding']";
			System.out.println(object);
			Actions actions = new Actions(driver);
			WebElement element = driver.findElement(By.xpath(object));
			actions.moveToElement(element).build().perform();
			pause(logger, "2");
			String descriptionText = driver.findElement(By.xpath(object)).getText();
			System.out.println(descriptionText);
			logger.log(Status.INFO, descriptionText);
			if (descriptionText.equalsIgnoreCase(text)) {
				System.out.println("Hover text is: " + descriptionText);
				logger.log(Status.PASS, "Hover text is: " + descriptionText);
			} else {
				System.out.println("Hover text is not correct");
				logger.log(Status.FAIL, "Hover text is not correct");
			}
		} catch (Exception e) {
			System.out.println("Text could not be found");
			logger.log(Status.FAIL, "Text could not be found");
		}
	}

	/**
	 * This method verify if the Page URL is correct
	 * <p>
	 * 
	 * Compares one URL whit the current URL, if is true get a
	 * console and logger PASS messages.
	 * If not true, get a console and logger FAIL messages
	 * <p>
	 * 
	 * @param logger
	 * @param expectedURL {@code String} This is the expected URL
	 * 
	 * @Variables page_url: This {@code String} variable is the current URL
	 * 
	 * @throws Exception When failed, throws an error message on console and logger.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void verifyPageURL(ExtentTest logger, WebDriver driver, String expectedURL) {
		String page_url = driver.getCurrentUrl();
		if (page_url.equals(expectedURL)) {
			System.out.println("The " + expectedURL + " page is displayed");
			logger.log(Status.PASS, "The " + expectedURL + " page is displayed");
		} else {
			System.out.println("The " + expectedURL + " page is not displayed");
			logger.log(Status.FAIL, "The " + expectedURL + " page is not displayed");
		}
	}

	/**
	 * This method verify if element is read only or not
	 * <p>
	 * 
	 * read only: cursor = "not-allowed"
	 * <p>
	 * 
	 * @param logger      {@code ExtentTest} log status for the reports
	 * @param driver      {@code WebDriver} driver
	 * @param elementName {@code String} Name of the element
	 * @param element     {@code WebElement} or {@code By} Locator of the element
	 * 
	 * @throws TimeoutException if timeout awaiting visibility of the element
	 */
	public void verifyElementIsReadOnly(ExtentTest logger, WebDriver driver, String elementName, WebElement element) {
		try {
			wait.visibilityOf(logger, driver, elementName, element, 5);
			String readOnly = element.getCssValue("cursor");
			if (readOnly.contains("not-allowed")) {
				loggerMessage(logger, "pass", elementName + " is read only");
			} else {
				loggerMessage(logger, "fail", " is not read only");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify if " + elementName + " is read only");
		}
	}

	public void verifyElementIsReadOnly(ExtentTest logger, WebDriver driver, String elementName, By element) {
		WebElement e = driver.findElement(element);
		verifyElementIsReadOnly(logger, driver, elementName, e);
	}

	public void verifyElementIsNOTReadOnly(ExtentTest logger, WebDriver driver, String elementName,
			WebElement element) {
		try {
			wait.visibilityOf(logger, driver, elementName, element, 5);
			String readOnly = element.getCssValue("cursor");
			if (!readOnly.contains("not-allowed")) {
				loggerMessage(logger, "pass", elementName + " is read only");
			} else {
				loggerMessage(logger, "fail", " is not read only");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify if " + elementName + " is read only");
		}
	}

	public void verifyElementIsNOTReadOnly(ExtentTest logger, WebDriver driver, String elementName, By element) {
		WebElement e = driver.findElement(element);
		verifyElementIsNOTReadOnly(logger, driver, elementName, e);
	}

	/**
	 * This method verify if the field is empty by Attribute (value)
	 * <p>
	 * 
	 * Get the Attribute {@code value} of the element, if {@code isEmpty()} then
	 * the field element is empty. Else is not.
	 * Do the same whit {@code getText()}
	 * <p>
	 * 
	 * @param logger      {@code ExtentTest} log status for the reports
	 * @param driver      {@code WebDriver} driver
	 * @param elementName {@code String} Label name of the element
	 * @param element     {@code WebElement} or {@code By} Locator of the element
	 * @throws @throws Exception When failed, throws an error message on console and
	 *                 logger.
	 * 
	 * @author Molina Emiliano
	 */
	public void verifyIfIsEmpty(ExtentTest logger, WebDriver driver, String elementName, WebElement element) {
		try {
			if (element.getText().isEmpty()) {
				loggerMessage(logger, "pass", elementName + " is empty");
			} else if (element.getAttribute("value").isEmpty()) {
				loggerMessage(logger, "pass", elementName + " is empty");
			} else {
				loggerMessage(logger, "fail", elementName + " is not empty");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify if " + elementName + " is empty");
		}
	}

	/**
	 * This method switch to current opened window
	 * 
	 * @throws @throws Exception When failed, throws an error message on console.
	 * 
	 * @author Molina Emiliano
	 */
	public void switchOpenedWindow(WebDriver driver) {

		try {
			String parentWindow = "0";
			parentWindow = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			for (String windowHandle : handles) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
				}
			}
			System.out.println("Switch window pass");
		} catch (Exception e) {
			System.out.println("Switch window fail");
		}
	}

	/**
	 * This method upload a file attachment on request on the backend
	 * <p>
	 * 
	 * Get the file from {@code Resources} folder
	 * <p>
	 * 
	 * @param logger
	 * 
	 * @throws Exception When failed, throws an error message on console and logger,
	 *                   whit screenshot.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void addNewAttachmentBackend(ExtentTest logger, WebDriver driver) {

		By attachmentFieldBackEnd = By.xpath("//button[@id='header_add_attachment']");
		By closeAttachmentModal = By.xpath("//button[@id='attachment_closemodal']");

		try {

			File file = new File("./Resources/Attachment.pdf");
			if (driver.findElements(attachmentFieldBackEnd).size() != 0) {
				driver.findElement(attachmentFieldBackEnd).click();
				pause(logger, "1");
				driver.switchTo().activeElement();
				System.out.println("Switching to modal window...");
				pause(logger, "1");
				WebElement uploadAtt = driver.findElement(By.xpath("//input[@id='attachFile']"));
				pause(logger, "1");
				uploadAtt.sendKeys(file.getAbsolutePath());
				pause(logger, "3");
				clickOn(logger, driver, "Close Attachment Modal", closeAttachmentModal);
				System.out.println("File Attached");
				logger.log(Status.PASS, "Attaching File");

			} else {
				System.out.println("File could NOT be attached");
				logger.log(Status.FAIL, "File NOT Attached");
				Assert.fail();
			}
		} catch (Exception e) {
			System.out.println("attachment field could not be find");
			logger.log(Status.FAIL, "Test Failed -> attachment field could not be find");
			screenShotAndErrorMsg(logger, e, driver, "Screenshot");
			Assert.fail();
		}
	}

	/**
	 * This method end impersonation in frontend and go back to site
	 * 
	 * @param logger
	 * 
	 * @throws Exception When failed, throws an error message on console and logger.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void endImpersonateFront(ExtentTest logger, WebDriver driver) {

		By menuBTN = By.xpath("//a[@alt='Menu']");
		By endImpersonateBTN = By.xpath("//li[@class='hidden-xs ng-scope']/a");

		try {
			driver.switchTo().defaultContent();
			clickOn(logger, driver, "Menu", menuBTN);
			pause(logger, "2");
			clickOn(logger, driver, "End Impersonation", endImpersonateBTN);
			pause(logger, "9");
			System.out.println("Impersonation finished");
			logger.log(Status.INFO, "Impersonation finished");
		} catch (Exception e) {
			System.out.println("Test Failed -> End impersonation");
			logger.log(Status.FAIL, "Test Failed -> End impersonation");
			Assert.fail();
		}
	}

	/**
	 * This method click on the element specified
	 * 
	 * @param logger      {@code ExtentTest} Log Status for the reports
	 * @param driver      {@code WebDriver} driver
	 * @param elementName {@code String} Name of the element to be clicked
	 * @param element     {@code WebElement} or {@code By} or {@code xpath} Locator
	 *                    of the element to be clicked
	 * 
	 * @throws Exception When failed, throws an error message on console and logger.
	 */
	public void clickOn(ExtentTest logger, WebDriver driver, String elementName, WebElement element)
			throws ElementClickInterceptedException {
		if (elementName == null || elementName.isEmpty()) {
			elementName = element.getTagName() + "_element";
		}
		try {
			wait.elementToBeClickable(logger, driver, elementName, element, 20);
			scrollByVisibleElement(logger, driver, element);
			element.click();
			// clickByJavascriptExecutor(logger, driver, element);
			loggerMessage(logger, "pass", elementName + " clicked!");

		} catch (Exception e) {
			catchError(logger, driver, e, "click on element " + elementName);
		}
	}

	public void clickOn(ExtentTest logger, WebDriver driver, String elementName, By element) {
		WebElement e = driver.findElement(element);
		clickOn(logger, driver, elementName, e);
	}

	public void clickOn(ExtentTest logger, WebDriver driver, String elementName, String element) {
		WebElement e = driver.findElement(By.xpath(element));
		clickOn(logger, driver, elementName, e);
	}

	/**
	 * This method wait until given seconds for visibility of given object
	 * and send given keys
	 * 
	 * @param logger     {@code ExtentTest} log status for the reports
	 * @param driver     {@code WebDriver} Driver
	 * @param objectName {@code String} Name of the object
	 * @param objects    {@code WebElement} or {@code By} or {@code xpath} Locator
	 *                   of the object
	 * @param keysToSend {@code String} keys to send
	 * @param maxTime    {@code int} Max time in seconds to wait
	 * 
	 * @throws TimeoutException - If the timeout expires.
	 * @author Emiliano.molina
	 */
	public void visibilityAndSendKeys(ExtentTest logger, WebDriver driver, String objectName, WebElement object,
			String keysToSend, int maxTime) {
		try {
			wait.elementToBeClickable(logger, driver, objectName, object, maxTime);
			scrollByVisibleElement(logger, driver, object);
			object.sendKeys(keysToSend);
			loggerMessage(logger, "pass", objectName + " was completed with: " + keysToSend);
		} catch (Exception e) {
			catchError(logger, driver, e, "complete " + objectName + " with " + keysToSend);
		}
	}

	public void visibilityAndSendKeys(ExtentTest logger, WebDriver driver, String objectName, By object,
			String keysToSend) {
		int maxTime = 15;
		WebElement e = driver.findElement(object);
		visibilityAndSendKeys(logger, driver, objectName, e, keysToSend, maxTime);
	}

	public void visibilityAndSendKeys(ExtentTest logger, WebDriver driver, String objectName, By object,
			String keysToSend, int maxTime) {
		WebElement e = driver.findElement(object);
		visibilityAndSendKeys(logger, driver, objectName, e, keysToSend, maxTime);
	}

	public void visibilityAndSendKeys(ExtentTest logger, WebDriver driver, String objectName, String object,
			String keysToSend, int maxTime) {
		WebElement e = driver.findElement(By.xpath(object));
		visibilityAndSendKeys(logger, driver, objectName, e, keysToSend, maxTime);
	}

	/**
	 * This method wait until given seconds for visibility of given object
	 * and send given keys + ENTER
	 * 
	 * @param logger     {@code ExtentTest} log status for the reports
	 * @param driver     {@code WebDriver} Driver
	 * @param objectName {@code String} Name of the object
	 * @param objects    {@code WebElement} or {@code By} or {@code xpath} Locator
	 *                   of the object
	 * @param keysToSend {@code String} keys to send
	 * @param maxTime    {@code int} Max time in seconds to wait
	 * 
	 * @throws TimeoutException - If the timeout expires.
	 * @author Emiliano.molina
	 */
	public void visibilityAndSendKeysEnter(ExtentTest logger, WebDriver driver, String objectName, WebElement object,
			String keysToSend, int maxTime) {
		try {
			wait.elementToBeClickable(logger, driver, objectName, object, maxTime);
			scrollByVisibleElement(logger, driver, object);
			object.sendKeys(keysToSend + Keys.ENTER);
			loggerMessage(logger, "pass", objectName + " was completed with: " + keysToSend + Keys.ENTER);
		} catch (Exception e) {
			catchError(logger, driver, e, "complete " + objectName + " with " + keysToSend);
		}
	}

	public void visibilityAndSendKeysEnter(ExtentTest logger, WebDriver driver, String objectName, By object,
			String keysToSend) {
		int maxTime = 15;
		WebElement e = driver.findElement(object);
		visibilityAndSendKeysEnter(logger, driver, objectName, e, keysToSend, maxTime);
	}

	public void visibilityAndSendKeysEnter(ExtentTest logger, WebDriver driver, String objectName, By object,
			String keysToSend, int maxTime) {
		WebElement e = driver.findElement(object);
		visibilityAndSendKeysEnter(logger, driver, objectName, e, keysToSend, maxTime);
	}

	public void visibilityAndSendKeysEnter(ExtentTest logger, WebDriver driver, String objectName, String object,
			String keysToSend, int maxTime) {
		WebElement e = driver.findElement(By.xpath(object));
		visibilityAndSendKeysEnter(logger, driver, objectName, e, keysToSend, maxTime);
	}

	/**
	 * This method verify compares data in element obtained with
	 * getText() or Attribute("value") with value received as parameter
	 * 
	 * @param logger       {@code ExtentTest} log status for the reports
	 * @param driver       {@code WebDriver} driver
	 * @param elementName  {@code String} Name of the element
	 * @param element      {@code WebElement} locator of the element
	 * @param expectedData {@code String} Expected data
	 */
	public void verifyDataMatch(ExtentTest logger, WebDriver driver, String elementName, WebElement element,
			String expectedData) {
		try {
			wait.visibilityOf(logger, driver, elementName, element, 5);
			if (element.getText().equals(expectedData)) {
				loggerMessage(logger, "pass", elementName + " data is correct!");
			} else if (element.getAttribute("value").equals(expectedData)) {
				loggerMessage(logger, "pass", elementName + " data is correct!");
			} else {
				loggerMessage(logger, "fail", elementName + " data is NOT correct");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify data match");
		}
	}

	/**
	 * This method verify option position in Topic List
	 * 
	 * @param logger
	 * @param option {@code String} Name of the topic option
	 * 
	 * @author Molina Emiliano
	 */
	public void verifyOptionPosition(ExtentTest logger, WebDriver driver, String option) {
		String topicListOptions = "//a[contains(.,'Topics')]/following-sibling::ul/li/a";
		List<WebElement> wElementList = driver.findElements(By.xpath(topicListOptions));
		List<String> optionsList = new ArrayList<String>();
		for (WebElement e : wElementList) {
			optionsList.add(e.getText().trim());
			System.out.println("Option " + e.getText().trim());
		}
		int position = optionsList.indexOf(option);

		if (optionsList.size() == position) {
			System.out.println("Option " + option + " is in the last position of the Topic list");
		} else {
			System.out.println("Option " + option + " is in the position N�" + position + " of the Topic list");
		}
	}

	/**
	 * This method verify if field type is correct
	 * 
	 * @param logger    {@code ExtentTest} log status for the reports
	 * @param driver    {@code WebDriver} driver
	 * @param fieldName {@code String} name of the field
	 * @param field     {@code WebElement} or {@code By} Locator of the field
	 */
	public void verifyFieldType(ExtentTest logger, WebDriver driver, String fieldName, WebElement field,
			String fieldType) {
		String fieldDropDwnPath = "//span[contains(.,'" + fieldName + "')]/following::b[1]";
		try {
			if (driver.findElement(By.xpath(fieldDropDwnPath)).isDisplayed()) {
				loggerMessage(logger, "pass", fieldName + " field type: dropdown");
			} else if (field.getAttribute("type").equalsIgnoreCase(fieldType)) {
				loggerMessage(logger, "Pass", fieldName + " field type: " + field.getAttribute("type"));
			} else {
				loggerMessage(logger, "fail",
						fieldName + " field type is not correct.\n Current fieldType: " + field.getAttribute("type") +
								"\n Expected fieldType: " + fieldType);
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify " + fieldName + " field type");
		}
	}

	public void verifyFieldType(ExtentTest logger, WebDriver driver, String fieldName, By field, String fieldType) {
		WebElement e = driver.findElement(field);
		verifyFieldType(logger, driver, fieldName, e, fieldType);
	}

	/**
	 * This method complete field in the backend
	 * 
	 * @param logger    {@code ExtentTest} log status for the reports
	 * @param driver    {@code WebDriver} driver
	 * @param fieldName {@code String} name of the field
	 * @param field     {@code WebElement} or {@code By} locator of the element
	 * @param value     {@code String} value to be completed
	 * 
	 * @author Emiliano.molina
	 */
	public void completeFieldBackend(ExtentTest logger, WebDriver driver, String fieldName, WebElement field,
			String value) {
		try {
			wait.visibilityOf(logger, driver, fieldName, field, 10);
			scrollByVisibleElement(logger, driver, field);
			field.clear();
			field.click();
			field.sendKeys(value + Keys.ENTER);
			loggerMessage(logger, "pass", "Field " + fieldName + " is completed with: " + value);
		} catch (Exception e) {
			catchError(logger, driver, e, "complete field " + fieldName);
		}
	}

	public void completeFieldBackend(ExtentTest logger, WebDriver driver, String fieldName, By field, String value) {
		WebElement e = driver.findElement(field);
		completeFieldBackend(logger, driver, fieldName, e, value);
	}

	/**
	 * This method completes all types of front-end fields
	 * <p>
	 * 
	 * @param logger
	 * @param fieldName {@code String} field name label
	 * @param value     {@code String} data to be filled in the field
	 * 
	 * @author b.marmol.maldonado
	 */
	public void completeFieldFrontend(ExtentTest logger, WebDriver driver, String fieldName, String value) {

		try {
			String fieldLabel = "//span[(text() = '" + fieldName + "' or text() = '" + fieldName
					+ " ') and (@class ='ng-binding' or @class='sn-tooltip-basic ')]";

			// String inputFieldB = fieldLabel + "/ancestor::div[2]//span/b[1]";
			String inputFieldB = fieldLabel + "/ancestor::div[2]//span[contains(@class, 'select')]//ancestor::div[1]";
			String dd_Select = "//div[@id='select2-drop']//input";
			String dd_SelectOption = dd_Select + "/ancestor::div[2]/ul/li//*[contains(text(),'" + value + "')]";
			String inputFieldDiv = fieldLabel + "/ancestor::div[2]/span/div";
			String dropdownInputDiv = fieldLabel + "/ancestor::div[2]/span/div//input";
			String FieldSelec = fieldLabel + "/ancestor::div[2]/div/select";
			String selecOption = fieldLabel + "/ancestor::div[2]/div/select/option[contains(text(),'" + value + "')]";
			String textAreaField = fieldLabel + "/ancestor::div[2]//span/textarea[1]";
			String inputInSpanField = fieldLabel + "/ancestor::div[2]//span/input[1]";
			String inputInDivField = fieldLabel + "/ancestor::div[2//div/input[not(@type = 'HIDDEN')]";
			String searchField = fieldLabel + "/ancestor::div[2]/div/div/input";

			// to debug
			System.out.println("\n----------");
			System.out.println("Field: " + fieldName);
			System.out.println("----------");

			System.out.println("inputFieldB: " + inputFieldB);
			System.out.println("dropdownSelectB: " + dd_Select);
			System.out.println("inputFieldDiv: " + inputFieldDiv);
			System.out.println("dropdownInputDiv: " + dropdownInputDiv);
			System.out.println("inputInSpanField: " + inputInSpanField);
			System.out.println("textAreaField: " + textAreaField);
			System.out.println("searchField: " + searchField);
			System.out.println("FieldSelec: " + FieldSelec);
			System.out.println("selecOption: " + selecOption);
			System.out.println("----------");

			wait.visibilityOf(logger, driver, "fieldLabel", fieldLabel, 10);

			if (driver.findElements(By.xpath("//div[@id='dummyfooter' and contains(text(), 'Hide')]")).size() != 0) {
				driver.findElement(By.xpath("//div[@id='dummyfooter']")).click();
				loggerMessage(logger, true, "Hide footer Button was clicked");
			}

			// if this type of field exist...
			if (driver.findElements(By.xpath(inputFieldB)).size() != 0) {

				System.out.println("Field Path: searchBox - input B");

				// open the field searchBox
				clickOn(logger, driver, "inputFieldB", By.xpath(inputFieldB));
				visibilityAndSendKeys(logger, driver, "dropdownSelect", By.xpath(dd_Select), value);
				clickOn(logger, driver, "inputFieldB", By.xpath(dd_SelectOption));

			} else if (driver.findElements(By.xpath(inputFieldDiv)).size() != 0) {

				// open the field searchBox
				clickOn(logger, driver, "inputFieldDiv", By.xpath(inputFieldDiv));
				visibilityAndSendKeysEnter(logger, driver, "dropdownInputDiv", By.xpath(dropdownInputDiv), value);

			} else if (driver.findElements(By.xpath(textAreaField)).size() != 0) {

				visibilityAndSendKeys(logger, driver, "textAreaField", By.xpath(textAreaField), value);

			} else if (driver.findElements(By.xpath(inputInSpanField)).size() != 0) {

				visibilityAndSendKeysEnter(logger, driver, "inputInSpanField", By.xpath(inputInSpanField), value);

			} else if (driver.findElements(By.xpath(inputInDivField)).size() != 0) {

				visibilityAndSendKeysEnter(logger, driver, "inputInDivField", By.xpath(inputInDivField), value);

			} else if (driver.findElements(By.xpath(searchField)).size() != 0) {

				visibilityAndSendKeysEnter(logger, driver, "searchField", By.xpath(searchField), value);

			} else if (driver.findElements(By.xpath(FieldSelec)).size() != 0) {

				clickOn(logger, driver, "FieldSelec", By.xpath(FieldSelec));

				if (driver.findElements(By.xpath(selecOption)).size() != 0) {
					clickOn(logger, driver, "selecOption", By.xpath(selecOption));
				} else {
					clickOn(logger, driver, "FieldSelec", By.xpath(FieldSelec));
					visibilityAndSendKeysEnter(logger, driver, "inputFieldB", By.xpath(inputFieldB), value);
				}

			} else {

				// same behavior that portal.button()
				List<String> xpathOptions = new ArrayList<>();
				xpathOptions.add("//button[ text() = 'Submit' or @id = 'Submit']");
				xpathOptions.add("//button[ text() = 'Next' or @id = 'Next']");

				for (String optionXpath : xpathOptions) {
					if (driver.findElements(By.xpath(optionXpath)).size() != 0) {
						WebElement element = driver.findElement(By.xpath(optionXpath));
						clickOn(logger, driver, "Continue button: '" + optionXpath + "'", element);
						completeFieldFrontend(logger, driver, fieldName, value);
						return;
					}
				}

				loggerMessage(logger, "Unknown kind of Field Path");
				loggerMessage(logger, false, "Input Field by:'" + fieldName + "' could not be find");
				return;
			}
			loggerMessage(logger, true, "Field '" + fieldName + "' is completed with '" + value + "'");

		} catch (ElementClickInterceptedException e) {

			clickOn(logger, driver, "Click on title to change the focus()",
					By.xpath("//div[@class='form-banner']//h2"));
			completeFieldFrontend(logger, driver, fieldName, value);

		} catch (Exception e) {

			catchError(logger, driver, e, "Field '" + fieldName + "' could not be find");
		}
	}

	// public void completeFieldFrontend(ExtentTest logger, WebDriver driver, String
	// fieldName, String value) {
	// WebElement fieldLabel = driver
	// .findElement(By.xpath("//span[(text() = '" + fieldName + "' or text() = '" +
	// fieldName
	// + " ') and (@class ='ng-binding' or @class='sn-tooltip-basic ')]"));
	// completeFieldFrontend(logger, driver, fieldName, fieldLabel, value);
	// }

	/**
	 * TODO Improve this method Emi
	 * This method captures Ticket Number from BACKEND
	 * <p>
	 * 
	 * When {@link #getTicketNumber(ExtentTest, String)} failed, try to find the
	 * ticketNumber
	 * from backend. So this method do that:
	 * First go to Request Table on backend. If actual user can go to backend, go
	 * on.
	 * Else, impersonate a user that can do it. Once in backend, get the current
	 * user EID.
	 * Switch Iframe, and if the Number column is sorted in ascending order then
	 * sort, else change on ascending. (newest ticket first)
	 * <p>
	 * <p>
	 * 
	 * IMPORTANT -> Success criteria field must be completed with unique ID, so that
	 * can identify tickets as unique. (This field must be completed from Input Data
	 * file).
	 * If Success Criteria field not exist in the form, change it for one that
	 * exist. Must be a text field that allows you complete whit free text.
	 * (All tables in Input Data File must be completed with unique ID in this
	 * field, for example -> AUTOMATED-TEST-01, AUTOMATED-TEST-02, etc).
	 * <p>
	 * 
	 * <p>
	 * 
	 * If 'Success criteria' column is visible then get the ticket number.
	 * ELSE add the 'Success criteria' Column then get the ticket number.
	 * 
	 * Once ticket number is obtained, get the current user name and if this is
	 * not the same username that created the ticket, impersonate them.
	 * <p>
	 * 
	 * <p>
	 * 
	 * Example of how implement this method: getTicketNumberFromBackend(logger,
	 * "Success criteria", "NASP");
	 * <p>
	 * 
	 * @param logger
	 * @param columnName          {@code String} Name of the column in Input Data
	 *                            file.
	 *                            (Must be completed with unique ID)
	 * @param ticketNumber_prefix {@code String} Prefix of the ticket Number
	 * 
	 * @author Molina Emiliano
	 * 
	 * @throws Exception When failed, throws an error message on console and logger,
	 *                   with screenshot.
	 * 
	 */
	// public void getTicketNumberFromBackend(ExtentTest logger, WebDriver driver,
	// String columnName, String ticketNumber_prefix) {

	// try {
	// By uniqueID_column = By.xpath("(//th[@glide_label='" + columnName +
	// "'])[1]");
	// String tktNumberPath = "(//td/preceding-sibling::td/a[contains(text(),'" +
	// ticketNumber_prefix + "')])[1]";
	// String sortedAscendingOrder = "(//a[@data-original-title='Sorted in ascending
	// order'])[1]";
	// String columnName2 = columnName.toLowerCase();
	// By inputSearch = By.xpath("(//input[@aria-label='Search column: " +
	// columnName2 + "'])[1]");
	// String columnNameValue = commonTest.getSpecificValue("DataSet1", columnName);
	// pause(logger, "9");
	// // redirects to NA SP Intake Tab - modify next line with the corresponding
	// table
	// // ->

	// verifyIfUserCanNavigateTo(logger, "Fulfiller View", "DataSet1",
	// "FulfillerURL");
	// pause(logger, "9");

	// // get user name
	// String actualUserEID = driver
	// .findElement(By.xpath("//span[@class='user-name hidden-xs hidden-sm
	// hidden-md']")).getText();
	// frameHelper.switchToFrame(logger, driver, nav.iframe_gsft);

	// // if Number column is sorted in ascending order then sort this Column in
	// // descending order(newest tickets first)
	// if (driver.findElements(By.xpath(sortedAscendingOrder)).size() != 0) {
	// commonTest.sortColumn(logger, "Number", "Sort (z to a)", "");
	// } else {
	// System.out.println("Can't find number column");
	// }

	// // If 'Success criteria' column is visible then get the ticket number ELSE
	// add
	// // the
	// // 'Success criteria' Column then get the ticket number
	// if (driver.findElements(uniqueID_column).size() != 0) {

	// // If input search exist, search for success criteria value
	// // successCrit must be unique id in excel table.
	// if (driver.findElements(inputSearch).size() != 0) {

	// driver.findElement(inputSearch).click();
	// pause(logger, "2");
	// driver.findElement(inputSearch).sendKeys(columnNameValue + Keys.ENTER);
	// pause(logger, "5");
	// ticketNumber = driver.findElement(By.xpath(tktNumberPath)).getText();
	// System.out.println("Ticket Number : " + ticketNumber);
	// logger.log(Status.PASS, "Ticket Number : " + ticketNumber);
	// } else {
	// System.out.println("Could not be search by 'Success Criteria'");
	// logger.log(Status.FAIL, "Could not search by 'Success Criteria'");
	// }
	// } else {
	// addColumn(logger, "Success criteria");
	// pause(logger, "2");
	// driver.findElement(inputSearch).click();
	// pause(logger, "2");
	// driver.findElement(inputSearch).sendKeys(columnNameValue + Keys.ENTER);
	// pause(logger, "5");
	// ticketNumber = driver.findElement(By.xpath(tktNumberPath)).getText();
	// System.out.println("Ticket Number : " + ticketNumber);
	// logger.log(Status.PASS, "Ticket Number : " + ticketNumber);
	// }

	// // Search the request by number and get the name of the user who created the
	// // request
	// searchRequestByNumber(logger, ticketNumber);
	// String createdBy = driver.findElement(By.xpath("//input[@aria-label='Created
	// by']")).getAttribute("value");

	// // If actual user is equals to the user who created request, go to support
	// // portal my cases.
	// // else, impersonate the user who created request and go to support portal my
	// // cases.
	// if (actualUserEID.equals(createdBy)) {
	// navigateTo(logger, "Support Portal My Cases", "DataSet1",
	// "SupportPortalMyCases");
	// pause(logger, "25");
	// System.out.println("Ticket number is: " + ticketNumber
	// + " and successful redirected to Support Portal My Cases as: " +
	// actualUserEID);
	// logger.log(Status.PASS, "Ticket number is: " + ticketNumber
	// + " and successful redirected to Support Portal My Cases as: " +
	// actualUserEID);
	// } else {
	// impersonateAndBack(logger, createdBy);
	// navigateTo(logger, "Support Portal My Cases", "DataSet1",
	// "SupportPortalMyCases");
	// pause(logger, "25");
	// System.out.println("Ticket number is: " + ticketNumber
	// + " and successful redirected to Support Portal My Cases as: " + createdBy);
	// logger.log(Status.PASS, "Ticket number is: " + ticketNumber
	// + " and successful redirected to Support Portal My Cases as: " + createdBy);
	// }

	// } catch (Exception e) {
	// System.out.println("Ticket Number could not be caught");
	// logger.log(Status.FAIL, "Ticket Number could not be caught");
	// Assert.fail();
	// }
	// }

	/**
	 * This method captures Ticket Number from the GREEN Message when a form
	 * is submitted in Portal Page.
	 * If it´s does not work, the method gonna try to get the ticket Number
	 * from My Cases on Support Portal.
	 * <p>
	 * 
	 * Type of ticket should be the first letters of the ticket such as:
	 * LOA, CTM, CAR, PR, HR, INS, INC, CHG, etc.
	 * <p>
	 * 
	 * @param logger
	 * @param typeOfTicket {@code String} First letters of the ticket
	 * 
	 */
	public String getTicketNumber(ExtentTest logger, WebDriver driver, String prefixOfTicket) {
		String ticketNumber = null;
		try {
			String ticketNotificationPath = "//*[@id='uiNotificationContainer']//*[contains(text(),'" + prefixOfTicket
					+ "')]";
			wait.visibilityOf(logger, driver, "Notification ticket number", ticketNotificationPath, 15);
			ticketNumber = extractFormattedTicketNumber(driver.findElement(By.xpath(ticketNotificationPath)).getText(),
					prefixOfTicket);

		} catch (TimeoutException e) {
			ticketNumber = getTicketNumberFromPortal(logger, driver);
		}

		// add another catch from get ticket number from backend

		catch (Exception e) {
			catchError(logger, driver, e, "get ticket number");
		}
		loggerMessage(logger, "pass", "ticket number: " + ticketNumber);
		return ticketNumber;
	}

	/**
	 * THIS METHOD get ticket number from Open Cases in Portal
	 * <p>
	 * 
	 * Search on My Cases Table view, filtered by Description and Opened
	 * date sorted in descending order.
	 * Get the ticket number of the first result of the searched list
	 * <p>
	 * 
	 * @param logger
	 * @return {@code String} return number of ticket
	 */
	public String getTicketNumberFromPortal(ExtentTest logger, WebDriver driver) {
		String ticketNumber = "";
		By shortDesc = By.xpath("//input[@placeholder='Short description']"),
				opened = By.xpath("//div[@role='button' and contains(.,'Opened')]"),
				casesBTN = By.xpath("//a[text()='Cases']"),
				firstRequest = By.xpath("(//td[@class='vertical-middle ng-binding ng-scope'])[1]");
		try {
			wait.elementToBeClickable(logger, driver, "Cases button", casesBTN, 60);
			driver.findElement(casesBTN).click();
			toggleDisplayView(logger, driver, "List View");
			clickOn(logger, driver, "Short Description", shortDesc);
			// driver.findElement(shortDesc).sendKeys(getSpecificValue("DataSet1", "Short
			// description") + Keys.ENTER); //TODO Emi?
			clickOn(logger, driver, "Sort by opened", opened);
			if (driver.findElement(By.xpath("//div[@role='button' and contains(.,'Opened')]/following::i[1]"))
					.getAttribute("className").contains("fa ng-scope fa-chevron-up")) {
				clickOn(logger, driver, "Sort by opened in descending order", opened);
			} else {
				loggerMessage(logger, "info", "Ordered by Opened in descending order");
			}
			ticketNumber = driver.findElement(firstRequest).getText();
			loggerMessage(logger, "pass", "Request created. Ticket number: " + ticketNumber);

		} catch (Exception e) {
			catchError(logger, driver, e, "get ticket number");
		}
		return ticketNumber;
	}

	/**
	 * This method gets the index of an element from a list of WebElements based on
	 * the provided parameter.
	 *
	 * @param elements  A list of WebElements to be searched through.
	 * @param parameter The parameter to be used for searching, currently only
	 *                  "getText" is supported.
	 * @param element   The element to search for.
	 *
	 * @return The index of the element in the list of WebElements if found, 0
	 *         otherwise.
	 *
	 * @author B.Marmol.Maldonado, ....
	 */
	public int getWebElementIndex(List<WebElement> elements, String parameter, String element) {

		int index = 0;

		if (parameter.equalsIgnoreCase("getText")) {
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getText().equals(element)) {
					index = i;
					break;
				}
			}
		} else {
			System.err.println("getWebElementIndex --> Error: Unsupported parameter: " + parameter);
		}
		return index;
	}

	/**
	 * This method checks if the input text is a number and returns a boolean value.
	 * 
	 * @param character the input text to be checked
	 * @param radix     the base to use for representing numeric values
	 * @return a boolean value indicating if the input text is a number
	 * 
	 * @author B.Marmol.Maldonado
	 */
	public static boolean isStringInteger(String character, int radix) {
		Scanner sc = new Scanner(character.trim());
		if (!sc.hasNextInt(radix)) {
			return false;
		}
		sc.nextInt(radix);
		return !sc.hasNext();
	}

	/**
	 * This method takes a text string and uses a given prefix to extract and return
	 * a formatted ticket number.
	 * 
	 * @param ritmTxt        - The text string to extract the ticket number from.
	 * @param prefixOfTicket - The prefix of the ticket number to use as a starting
	 *                       point for extraction.
	 * 
	 * @return - The extracted and formatted ticket number.
	 * 
	 */
	public String extractFormattedTicketNumber(String ritmTxt, String prefixOfTicket) {

		int startIndex = 0;
		int endIndex = 0;
		int index = 0;

		// Search for the starting index of the ticket number by finding the prefix in
		// the text.
		startIndex = ritmTxt.indexOf(prefixOfTicket);

		// Move the index past the prefix.
		index = startIndex + prefixOfTicket.length();

		// Find the end index of the ticket number by searching for the first
		// non-numeric character.
		while (index <= ritmTxt.length()) {
			if (!isStringInteger(ritmTxt.substring(index, index + 1), 10)) {
				endIndex = index;
				break;
			}
			index++;
		}

		// Return the extracted and formatted ticket number.
		return ritmTxt.substring(startIndex, endIndex);
	}

	/**
	 * This method get the current work day
	 * <p>
	 * 
	 * work day -> is 2 work days after today
	 * Can not be weekend
	 * <p>
	 * 
	 * @return {@code String} work day in format yyyy-MM-dd hh:mm:ss
	 * 
	 * @author Molina Emiliano
	 */
	public String getWorkDay() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime newDate = today;

		if (newDate.getDayOfWeek() == DayOfWeek.THURSDAY) {

			newDate = newDate.plusDays(4);

		} else if (newDate.getDayOfWeek() == DayOfWeek.FRIDAY) {

			newDate = newDate.plusDays(4);

		} else if (newDate.getDayOfWeek() == DayOfWeek.SATURDAY) {

			newDate = newDate.plusDays(3);

		} else if (newDate.getDayOfWeek() == DayOfWeek.SUNDAY) {

			newDate = newDate.plusDays(2);

		} else {

			newDate = newDate.plusDays(2);

		}
		String newDateString = newDate.format(formatter).toString();
		return newDateString;
	}

	/**
	 * This method calculate new date.
	 * <p>
	 * This method gets the actual date, adds or subtracts days and returns the new
	 * date.
	 * <p>
	 * 
	 * @param add          {@code boolean} If `true`, the date will be added, if
	 *                     `false` the date will be subtracted
	 * @param numberOfDays {@code Integer} Amount of days to be added or subtracted
	 * @return {@code String} Returns a string with the new date. Formatted as
	 *         yyyy-MM-dd hh:mm:ss
	 * 
	 * @author BM
	 */
	public String calculateNewDate(boolean add, int numberOfDays) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime newDate = null;

			if (add) {
				newDate = today.plusDays(numberOfDays);
			} else {
				newDate = today.minusDays(numberOfDays);
			}

			String newDateString = newDate.format(formatter).toString();

			System.out.println("Today's Date is:     " + today);
			System.out.println("New Date is:     " + newDateString);

			return newDateString;
		} catch (Exception e) {
			System.out.println("New Date could not be calculated");
			Assert.fail();
			return "";
		}
	}

	// this method search a specific Group in the fulfiller view -> User
	// Administration table
	public void verifyGroupComanager(ExtentTest logger, WebDriver driver, List<String> coManagersExpected) {
		try {
			if (nav.coManagerField.getText().isEmpty()) {
				loggerMessage(logger, "info", "There are not co-managers");
			} else {
				String membersNames = "";
				String coManagers = nav.coManagerField.getText();
				System.out.print("Co-Manager Members are: *");
				for (int i = 0; i < coManagersExpected.size(); i++) {

					if (coManagers.contains(coManagersExpected.get(i))) {

						System.out.print(coManagersExpected.get(i) + " * ");
						membersNames = membersNames + coManagersExpected.get(i) + "; ";

					} else {
						System.out.print(coManagersExpected.get(i) + " is not a member of the Comanager Group ");
						logger.log(Status.FAIL,
								coManagersExpected.get(i) + " is not a member of the Comanager Group ");
					}
				}
				loggerMessage(logger, "info", "Co-Managers are: " + membersNames);
				loggerMessage(logger, "pass", "Co-managers checked");
			}
		} catch (Exception e) {
			catchError(logger, driver, e, "verify co-managers group members");
		}
	}

	// TODO to be improved by Emi
	// This method verifies if a User is Part of a Group in User Administration >
	// Groups Table
	// public void verifyUserInAGroup(ExtentTest logger, String groupName, String
	// user) {

	// String groupResult = "(//a[contains(text(), '" + groupName + "')])[1]";
	// String groupMember =
	// "//tr[@record_class='sys_user_grmember']//a[@class='linked' and text()='" +
	// user + "']";

	// try {

	// pause(logger, "2");
	// driver.navigate().to(getSpecificValue("DataSet1", "URL"));
	// pause(logger, "8");
	// frameHelper.switchToFrame(logger, driver, nav.iframe_gsft);
	// if
	// (driver.findElement(By.xpath(columnSearch)).getAttribute("aria-expanded").equals("false"))
	// {
	// driver.findElement(By.xpath(columnSearch)).click();
	// pause(logger, "1");
	// }
	// driver.findElement(By.xpath(nameSearch)).click();
	// pause(logger, "1");
	// driver.findElement(By.xpath(nameSearch)).clear();
	// pause(logger, "1");
	// driver.findElement(By.xpath(nameSearch)).sendKeys(groupName + Keys.ENTER);
	// pause(logger, "5");
	// driver.findElement(By.xpath(groupResult)).click();
	// pause(logger, "7");
	// util.scrollByVisibleElement(logger, driver,
	// driver.findElement(By.xpath(groupMembersSection)));
	// pause(logger, "2");
	// if (driver.findElements(By.xpath(groupMember)).size() != 0) {
	// System.out.println(user + " is a member of " + groupName + " group");
	// logger.log(Status.PASS, user + " is a member of " + groupName + " group");
	// } else {
	// System.out.println(user + " is NOT a member of " + groupName + " group");
	// logger.log(Status.PASS, user + " is NOT a member of " + groupName + "
	// group");
	// }

	// pause(logger, "2");

	// } catch (Exception e) {
	// logger.log(Status.FAIL, "Group: " + groupName + " could not be found");
	// Assert.fail();
	// }
	// }

	// // This method removes a User to a Group in User Administration > Groups
	// Table
	// public void removeUserFromAGroup(ExtentTest logger, String groupName, String
	// user) {

	// String groupResult = "(//a[contains(text(), '" + groupName + "')])[1]";
	// String groupMember =
	// "//tr[@record_class='sys_user_grmember']//a[@class='linked' and text()='" +
	// user + "']";
	// String removeFromCollection = "//select[@id='select_1']/option[contains
	// (text(), '" + user + "')]";

	// try {
	// driver.navigate().to(getSpecificValue("DataSet1", "URL"));

	// pause(logger, "9");
	// frameHelper.switchToFrame(logger, driver, nav.iframe_gsft);
	// if
	// (driver.findElement(By.xpath(columnSearch)).getAttribute("aria-expanded").equals("false"))
	// {
	// driver.findElement(By.xpath(columnSearch)).click();
	// pause(logger, "1");
	// }
	// driver.findElement(By.xpath(nameSearch)).click();
	// pause(logger, "1");
	// driver.findElement(By.xpath(nameSearch)).clear();
	// pause(logger, "1");
	// driver.findElement(By.xpath(nameSearch)).sendKeys(groupName + Keys.ENTER);
	// pause(logger, "5");
	// driver.findElement(By.xpath(groupResult)).click();
	// pause(logger, "7");
	// util.scrollByVisibleElement(logger, driver,
	// driver.findElement(By.xpath(groupMembersSection)));
	// pause(logger, "2");

	// if (driver.findElements(By.xpath(groupMember)).size() != 0) {

	// driver.findElement(By.xpath(editButton)).click();
	// pause(logger, "9");
	// driver.findElement(By.xpath(removeFromCollection)).click();
	// pause(logger, "4");
	// driver.findElement(By.xpath(removeButton)).click();
	// pause(logger, "4");
	// driver.findElement(By.xpath(saveButton)).click();
	// pause(logger, "4");
	// System.out.println(user + " removed from " + groupName + " group");
	// logger.log(Status.PASS, user + " removed from " + groupName + " group");
	// } else {
	// System.out.println(user + " is NOT a member of " + groupName + " group");
	// logger.log(Status.PASS, user + " is NOT a member of " + groupName + "
	// group");
	// }

	// } catch (Exception e) {
	// logger.log(Status.FAIL, "Group: " + groupName + "could not be found");
	// Assert.fail();
	// }
	// }

	// // this method verifies group members -> User Administration table
	// public void verifyGroupMembers(ExtentTest logger) {
	// try {

	// String membersNames = "";
	// util.scrollByVisibleElement(logger, driver,
	// driver.findElement(By.xpath(groupMembersSection)));
	// List<WebElement> group = driver.findElements(By.xpath(groupMembersPath));
	// aux = approverGroup.split("; ");
	// esgApproversGroup = Arrays.asList(aux);
	// group.forEach((member) -> actualGroupMembers = actualGroupMembers +
	// member.getText() + "; ");

	// System.out.print("Group Members are: ");
	// for (int i = 0; i < group.size(); i++) {

	// System.out.print(group.get(i).getText() + " * ");
	// membersNames = membersNames + group.get(i).getText() + "; ";

	// }
	// System.out.println(" ");
	// pause(logger, "9");
	// logger.log(Status.INFO, "Group Members are: " + membersNames);
	// System.out.println("Group members checked");
	// logger.log(Status.PASS, "Group members checked");

	// } catch (Exception e) {
	// logger.log(Status.FAIL, "Group members could not be checked");
	// Assert.fail();
	// }
	// }

	// public void verifyGroup(ExtentTest logger, String groupName) {

	// String groupResult = "(//a[contains(text(), '" + groupName + "')])[1]";
	// pause(logger, "8");
	// frameHelper.switchToFrame(logger, driver, nav.iframe_gsft);
	// if
	// (driver.findElement(By.xpath(columnSearch)).getAttribute("aria-expanded").equals("false"))
	// {
	// driver.findElement(By.xpath(columnSearch)).click();
	// pause(logger, "1");
	// }
	// driver.findElement(By.xpath(nameSearch)).click();
	// pause(logger, "1");
	// driver.findElement(By.xpath(nameSearch)).clear();
	// pause(logger, "1");
	// driver.findElement(By.xpath(nameSearch)).sendKeys(groupName + Keys.ENTER);
	// pause(logger, "5");
	// driver.findElement(By.xpath(groupResult)).click();
	// pause(logger, "7");
	// }

	// // This method verify if members exist in group
	// // if not, add them
	// public void addMemberToGroup(ExtentTest logger, String memberName) {

	// By member = By.xpath("//select/option[text()='" + memberName + "']");
	// By inputSearch =
	// By.xpath("//label[text()='Collection']/following::input[@type='search']");
	// By result = By.xpath("//select[@aria-label='Collection']/option[text()='" +
	// memberName + "']");
	// By addBTN = By.xpath("//a[@aria-label='Add selected options to the Group
	// Members List listbox']");

	// try {

	// if (driver.findElements(member).size() == 0) {

	// driver.findElement(inputSearch).click();
	// pause(logger, "2");
	// driver.findElement(inputSearch).sendKeys(memberName);
	// pause(logger, "9");
	// driver.findElement(result).click();
	// pause(logger, "2");
	// driver.findElement(addBTN).click();
	// pause(logger, "5");
	// System.out.println("Member: " + memberName + " is added to group");
	// logger.log(Status.PASS, "Member: " + memberName + " is added to group");

	// } else if (driver.findElements(member).size() != 0) {

	// System.out.println("Member: " + memberName + " is already on group");
	// logger.log(Status.INFO, "Member: " + memberName + " is already on group");

	// }

	// } catch (Exception e) {
	// System.out.println("Something went wrong... could not verify members on
	// group");
	// logger.log(Status.INFO, "Something went wrong... could not verify members
	// on group");
	// util.screenShotAndErrorMsg(logger, e, driver, "Something went wrong... could
	// not verify members on group");
	// Assert.fail();
	// }
	// }

}