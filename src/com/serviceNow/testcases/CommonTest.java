/**
 * This class contains common methods that are used across different test cases
 */
package com.serviceNow.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.serviceNow.pages.NavPage;
import com.serviceNow.pages.PortalPage;

public class CommonTest extends BaseTest {

	public WebElement element = null;
	public WebElement shadowElement = null;

	public CommonTest() throws IOException {
		super();
	}

	@BeforeSuite
	public void setUpProject(ITestContext context) {
		suiteName = context.getCurrentXmlTest().getSuite().getName();
		// Calling suiteConfig() in BaseTest class
		suiteConfig(suiteName);
		// Print suite Name
		System.out.println("@@@ Suite Name : " + suiteName);

		try {
			String inputDataName = context.getCurrentXmlTest().getSuite().getParameter("inputdatafile");
			// String sheetNameA =
			// context.getCurrentXmlTest().getSuite().getParameter("sheetName");
			System.out.println("@@@ Test data Field Name : " + inputDataName);
			System.out.println("@@@ Test data Sheet Name : " + sheetName);
			getInputSheetName(inputDataName);
			// getInputSheetName(inputDataName, sheetNameA);

		} catch (Exception e) {
			System.out.println("Error at setInputSheetName(): " + e.getMessage());
		}
	}

	@Parameters({ "testcaseName" })
	@BeforeTest
	public void beforeTest(@Optional("TestCase1") String testcaseName) {
		// Fetch TC data from excel and print
		getInputDataFromExcel(testcaseName);
		createExtentReports(testcaseName);
	}

	@AfterMethod
	public void flush() {
		closeExtentReports();
	}

	PortalPage portal;
	NavPage nav;

	// Initialize all web elements of pages
	public void initializePage() {

		portal = PageFactory.initElements(driver, PortalPage.class);
		System.out.println("@@@ initializePage : PortalPage.class");
		nav = PageFactory.initElements(driver, NavPage.class);
		System.out.println("@@@ initializePage : NavPage.class");

	}

	public void login(ExtentTest logger, String username, String password) {
		try {
			util.setText(logger, portal.text(driver, "User name"), username);
			util.setText(logger, portal.text(driver, "Password"), password);
			util.clickOn(logger, driver, "Login button", portal.button(driver, "Log in"));

			validateView_byLink(driver.getCurrentUrl());

			util.loggerMessage(logger, true, "Login successful");
		} catch (Exception e) {
			util.catchError(logger, driver, e, "Unable to Login");
			// util.screenShotAndErrorMsg(logger, e, driver, "Unable to Login");
		}
	}

	// Generally Used for Sorting in Ascending/Descending Order
	// columnName-name of column on which action needs to be performed
	// action- action to be performed(list of actions appears on right click on
	// column)
	@Test
	public void sortColumn(ExtentTest logger, String columnName, String action, String tabName) {
		try {
			// Added if-else for Quebec Release
			if (tabName.isEmpty()) {
				tabName = "";
			} else {
				tabName = "//*[@tab_caption='" + tabName + "']";
			}
			if (driver.findElements(By.xpath(tabName + "//a[text()='" + columnName + "'][not(@aria-hidden='true')]"))
					.size() == 0) {
				nav.addColumn(logger, driver, columnName, tabName);
				util.pause(logger, "5");
			}
			util.clickOn(logger, driver, "", nav.tableHeaderActions(driver, columnName, ""));
			util.pause(logger, "5");
			util.clickOn(logger, driver, "", nav.label(driver, action, ""));
			util.pause(logger, "5");
			logger.log(Status.PASS, "Action on column is performed");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to perform action on column");
		}
	}

	// Impersonate as user
	// impersonated Username--user id needs to be impersonated
	@Test
	public void impersonateAsUser(ExtentTest logger, String impersonatedUsername) {
		try {
			driver.switchTo().defaultContent();
			// Click avatar
			WebElement shadowHost = driver.findElement(By.cssSelector("macroponent-f51912f4c700201072b211d4d8c26010"));
			WebElement root1 = getShadowElement(driver, shadowHost, "sn-polaris-layout");
			WebElement root2 = getShadowElement(driver, root1, "sn-polaris-header");
			WebElement root3 = getShadowElement(driver, root2, "now-avatar");
			util.clickOn(logger, driver, "root3", root3);
			util.pause(logger, "5");

			// Click on impersonate user
			WebElement root4 = getShadowElement(driver, root2,
					"sn-contextual-menu#userMenu > span > span > div > div.user-menu-controls > button.user-menu-button.impersonateUser.keyboard-navigatable.polaris-enabled");
			util.clickOn(logger, driver, "root4", root4);
			util.pause(logger, "15");

			// enter user id
			WebElement root5 = getShadowElement(driver, root1, "sn-impersonation");
			WebElement root6 = getShadowElement(driver, root5, "now-typeahead");
			// WebElement root6a = root6.findElement(By.cssSelector("now-popover >
			// div.now-typeahead-field now-form-field > input.now-typeahead-native-input"));
			// util.clickOn(logger, root6);
			util.clickByJavascriptExecutor(logger, driver, root6);
			util.pause(logger, "2");
			util.setText(logger, root6, impersonatedUsername);
			util.pause(logger, "8");

			// click the user id in dropdown
			// WebElement shadowHost2 =
			// driver.findElement(By.cssSelector("now-popover-panel"));
			// WebElement root7 = getShadowElement(driver,shadowHost2,"seismic-hoist >
			// div.now-dropdown-list");

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement dropDownList = (WebElement) jse.executeScript(
					"return document.querySelector('now-popover-panel > seismic-hoist').shadowRoot.querySelector('div.now-dropdown-list > div > div > div > div.now-dropdown-list-sublabel')");
			System.out.println("Impersonate using " + dropDownList.getText());
			util.clickOn(logger, driver, "drop list", dropDownList);
			util.pause(logger, "3");

			// Click on impersonate button
			WebElement impersonateButton = (WebElement) jse.executeScript(
					"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-impersonation').shadowRoot.querySelector('now-modal').shadowRoot.querySelector('div > div > div > div.now-modal-footer > now-button:nth-child(2)').shadowRoot.querySelector('button > slot > span')");
			util.clickOn(logger, driver, "impersonate button", impersonateButton);
			System.out.println("Name of the button clicked: " + impersonateButton.getText());
			util.pause(logger, "10");
			logger.log(Status.PASS, "Impersonated as User");
			System.out.println("Impersonated as User");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to impersonate");
		}
	}

	// Validates email notification is triggered with correct recipient and subject
	// reviewerMailID, requestorMailID-To and Cc sections email ids
	// target-target field value of email notification
	// subject-subject of email notification
	@Test
	public void verifyEmailNotification(ExtentTest logger, String reviewerMailID, String requestorMailID, String target,
			String subject) {
		try {
			//// util.waitTillElementIsClickable(logger, driver, nav.conditionLink(driver,
			//// "All",""));
			util.clickOn(logger, driver, "", nav.conditionLink(driver, "All", ""));
			util.pause(logger, "10");
			util.setTextWithEnter(logger, nav.searchColumnInTable(logger, driver, "Subject", ""), subject);
			// util.pause(logger, "5");
			//// util.waitTillElementIsClickable(logger, driver, nav.tooltip(driver,
			// "Preview","",""));
			sortColumn(logger, "Created", "Sort (z to a)", "");
			//// util.waitTillElementIsClickable(logger, driver, nav.tooltip(driver,
			//// "Preview","",""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Preview", "", ""));
			// util.pause(logger, "5");
			// util.waitTillElementIsClickable(logger, driver, nav.link(driver, "Open
			// Record",""));
			util.clickOn(logger, driver, "", nav.link(driver, "Open Record", ""));
			// util.pause(logger, "5");
			//// util.waitTillElementIsVisible(logger, driver,nav.text(driver,
			// "Subject",""));
			util.checkForContainsText(logger, nav.text(driver, "Subject", ""), subject);
			// util.checkForNotContainsText(logger,nav.text(driver, "Recipients"),
			// requestorMailID);
			util.checkForContainsText(logger, nav.text(driver, "Recipients", ""), reviewerMailID);
			// util.checkForContainsText(logger, nav.text(driver, "Copied"),
			// reviewerMailID);
			util.verifyTextContainsByValue(logger, nav.text(driver, "Target", ""), target);
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Back", "", ""));
			logger.log(Status.PASS, "Email Notification is verified");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to verify Email Notification");
		}
	}

	// Open a Record in Table
	// requestNumber-requestNumber to search
	// columnName-name of column to be selected in Search dropdown of table
	public void openRecord(ExtentTest logger, String requestNumber, String columnName) {
		try {
			addColumn(logger, columnName);
			// util.waitTillElementIsClickable(logger, driver, nav.dropdown(driver,
			// "Search",""));
			util.selectBoxByValue(logger, nav.dropdown(driver, "Search", ""), columnName);
			// util.waitTillElementIsClickable(logger, driver, nav.backgroundText(driver,
			// "Search",""));
			util.setTextWithEnter(logger, nav.backgroundText(driver, "Search", ""), requestNumber);
			// Wait 3 sec
			util.pause(logger, "3");
			// util.waitTillElementIsClickable(logger, driver, nav.tooltip(driver,
			// "Preview","",""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Preview", "", ""));
			// util.pause(logger, "5");
			util.clickOn(logger, driver, "", nav.link(driver, "Open Record", ""));
			// util.pause(logger, "5");
			logger.log(Status.PASS, "Record is opened");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to open record");
		}
	}

	// Add column in list view
	// columnName-name of column to be added
	public void addColumn(ExtentTest logger, String columnName) {
		try {
			util.pause(logger, "5");
			// util.waitTillElementIsClickable(logger, driver, nav.lnk_personalizedIcon);
			util.clickOn(logger, driver, "", nav.lnk_personalizedIcon);
			util.pause(logger, "5");
			// util.waitTillElementIsVisible(logger, driver, nav.dropdown(driver,
			// "Selected",""));
			Select rightDropdown = new Select(nav.dropdown(driver, "Selected", ""));
			List<WebElement> rightList = rightDropdown.getOptions();
			int flag = 0;
			for (int k = 0; k < rightList.size(); k++) {
				if (rightList.get(k).getText().equals(columnName)) {
					rightDropdown.selectByVisibleText(columnName);
					logger.log(Status.INFO, "Column is  already present in selected dropdown: " + columnName);
					System.out.println("Column is  already present in selected dropdown: " + columnName);
					break;
				} else {
					flag = flag + 1;
				}
			}
			if (flag == rightList.size()) {
				util.clickOn(logger, driver, "",
						driver.findElement(By.xpath("(.//option[text()='" + columnName + "'])")));
				util.clickOn(logger, driver, "", nav.tooltip(driver, "Add", "", ""));
				logger.log(Status.INFO, "Added all items from available to selected list");
				System.out.println("Column Added: " + columnName);
			}
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Move up", "", ""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Move up", "", ""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Move up", "", ""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Move up", "", ""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Move up", "", ""));
			util.clickOn(logger, driver, "", nav.button(driver, "OK", ""));
			util.pause(logger, "5");
			logger.log(Status.PASS, "Column is added");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to add columns in nav page");
		}
	}

	// Remove column
	// columnName-name of column to be added
	public void removeColumn(ExtentTest logger, String columnName) {
		try {
			util.pause(logger, "5");
			util.clickOn(logger, driver, "", nav.lnk_personalizedIcon);
			util.pause(logger, "5");
			Select rightDropdown = new Select(nav.dropdown(driver, "Selected", ""));
			List<WebElement> rightList = rightDropdown.getOptions();
			int flag = 0;
			for (int k = 0; k < rightList.size(); k++) {
				if (rightList.get(k).getText().equals(columnName)) {
					rightDropdown.selectByVisibleText(columnName);
					util.clickOn(logger, driver, "", nav.tooltip(driver, "Remove", "", ""));
					logger.log(Status.INFO, "Column Removed from Right list");
					System.out.println("Column Removed: " + columnName);
					break;
				} else {
					flag = flag + 1;
				}
			}
			if (flag == rightList.size()) {
				System.out.println("Column is not present in the Right list");
				logger.log(Status.INFO, "Column is not present in the Right list");
			}
			util.clickOn(logger, driver, "", nav.button(driver, "OK", ""));
			util.pause(logger, "5");
			logger.log(Status.PASS, "Column is removed");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to remove columns in nav page");
		}
	}

	// Verify list of columns to be available in Personalized list view
	// expectedColumns-list of columns, should be available in Personalized list
	// view
	public void verifyColumns(ExtentTest logger, String expectedColumns) {
		try {
			// util.waitTillElementIsClickable(logger, driver, nav.lnk_personalizedIcon);
			util.clickOn(logger, driver, "", nav.lnk_personalizedIcon);
			util.pause(logger, "5");
			// util.waitTillElementIsVisible(logger, driver, nav.dropdown(driver,
			// "Available",""));
			String columns[] = expectedColumns.split(",");
			Select leftDropdown = new Select(nav.dropdown(driver, "Available", ""));
			Select rightDropdown = new Select(nav.dropdown(driver, "Selected", ""));
			List<WebElement> leftList = leftDropdown.getOptions();
			List<WebElement> rightList = rightDropdown.getOptions();
			for (int i = 0; i < columns.length; i++) {
				for (int j = 0; j < leftList.size(); j++) {
					if (columns[i].equals(leftList.get(j).getText().trim())) {
						logger.log(Status.PASS, "Column is  present " + columns[i]);
						break;
					} else if (j == (leftList.size() - 1)) {
						for (int k = 0; k < rightList.size(); k++) {
							if (columns[i].equals(rightList.get(k).getText().trim())) {
								logger.log(Status.PASS, "Column is  present " + columns[i]);
								break;
							} else if (k == (rightList.size() - 1)) {
								logger.log(Status.FAIL, "Column is not present " + columns[i]);
							}
						}
					}
				}
			}
			util.clickOn(logger, driver, "", nav.button(driver, "OK", ""));
			logger.log(Status.PASS, "Columns Verification is successful");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to verify columns");
		}
	}

	// Attach file on Nav Page
	// data-name of file to attach
	public void uploadAttachmentOnNavPage(ExtentTest logger, String fileName) {
		try {
			// util.waitTillElementIsClickable(logger, driver, nav.tooltip(driver,"Manage
			// Attachments","",""));
			util.clickByJavascriptExecutor(logger, driver, nav.tooltip(driver, "Manage Attachments", "", ""));
			util.pause(logger, "5");
			String filePath = System.getProperty("user.dir") + "\\" + fileName;
			System.out.println("FilePath:" + filePath);
			util.setText(logger, nav.txt_attachFile, filePath);
			util.pause(logger, "10");
			// util.waitTillElementIsVisible(logger, driver, nav.link(driver,"rename",""));
			// util.waitTillElementIsClickable(logger, driver, nav.btn_closeModal);
			util.clickOn(logger, driver, "", nav.btn_closeModal);
			logger.log(Status.PASS, "Attachment is successful");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to attach");
		}
	}

	// Delete Attachment on Nav Page
	public void deleteAttachmentOnNavPage(ExtentTest logger, String fileName) {
		try {
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Manage Attachments", "", ""));
			util.pause(logger, "5");
			util.clickByJavascriptExecutor(logger, driver,
					nav.tooltip(driver, "Select attachment " + fileName + " for deletion", "", ""));
			// util.waitTillElementIsClickable(logger, driver,
			// nav.button(driver,"Remove",""));
			util.clickOn(logger, driver, "", nav.button(driver, "Remove", ""));
			util.pause(logger, "10");
			int flag = util.verifyElementNotPresentByXpath(logger,
					".//div[@id='attachment_dialog_list']//a[contains(text(),'[rename]')]", driver);
			if (flag == 0) {
				logger.log(Status.PASS, "Attachment is deleted");
			} else {
				logger.log(Status.FAIL, "Attachment is not deleted");
			}
			// util.waitTillElementIsClickable(logger, driver, nav.btn_closeModal);
			util.clickOn(logger, driver, "", nav.btn_closeModal);
			logger.log(Status.PASS, "Attachment is deleted");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to rename");
		}
	}

	// Rename attachment on Nav Page
	public void renameAttachmentOnNavPage(ExtentTest logger) {
		try {
			// util.waitTillElementIsClickable(logger, driver, nav.tooltip(driver,"Manage
			// Attachments","",""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Manage Attachments", "", ""));
			util.pause(logger, "5");
			util.clickOn(logger, driver, "", nav.btn_rename);
			util.pause(logger, "3");
			Random r = new Random(1000);
			util.setTextWithEnter(logger, nav.lnk_imageName, r.nextInt(1000) + ".jpg");
			// util.waitTillElementIsClickable(logger, driver, nav.btn_closeModal);
			util.clickOn(logger, driver, "", nav.btn_closeModal);
			logger.log(Status.PASS, "Attachment is renamed");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to rename");
		}
	}

	// Edit Column values from list view
	// columnName-name of column to be edited
	// value-value for edit
	public void editTableText(ExtentTest logger, String columnName, String value) {
		try {
			int i = util.returnColumnIndex(logger,
					"//table[not(contains(@id,'clone'))]//th/span/i[not(@aria-hidden='true')]/parent::span", columnName,
					driver);
			System.out.println(i);
			util.clickOn(logger, driver, "", driver.findElement(
					By.xpath("//table[not(contains(@id,'clone'))]//tr//td[contains(@class,'vt')][" + i + "]")));
			driver.findElement(
					By.xpath("//table[not(contains(@id,'clone'))]//tr//td[contains(@class,'vt')][" + i + "]"))
					.sendKeys("" + Keys.ENTER);
			driver.findElement(By.xpath(
					"//td[@id='cell_edit_window_header']//input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')]"))
					.sendKeys(value);
			util.pause(logger, "2");
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Save (Enter)", "", ""));
			util.pause(logger, "2");
			logger.log(Status.PASS, "Table Item is edited");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Edit Table Items issue");
		}
	}

	// Filter Entity and move from left dropdown to right dropdown
	// entityName-entity to be filtered
	// rightListName-name of right side dropdown
	// section Name-either null or main section name which is displayed above
	// filters and buttons
	public void filterEntity(ExtentTest logger, String entityName, String rightListName, String sectionName) {
		try {
			if (sectionName.isEmpty()) {
				wait.elementIsClickable(logger, driver, nav.tooltip(driver, "Search Available List", "", ""));
			} else {
				wait.elementIsClickable(logger, driver, driver.findElement(By.xpath("//*[text()='" + sectionName
						+ "']/parent::label/parent::div/following-sibling::div//*[@aria-label='Search Available List'][not(@aria-hidden='true')]")));
			}
			Select rightDropdown = new Select(nav.filterDropdown(driver, rightListName, sectionName));
			List<WebElement> rightList = rightDropdown.getOptions();
			int flag = 0;
			for (int k = 0; k < rightList.size(); k++) {
				if (rightList.get(k).getText().equals(entityName)) {
					rightDropdown.selectByVisibleText(entityName);
					logger.log(Status.INFO, "Entity is  already present in Right dropdown: " + entityName);
					System.out.println("Entity is  already present in selected dropdown: " + entityName);
					break;
				} else {
					flag = flag + 1;
				}
			}
			if (flag == rightList.size()) {
				if (sectionName.isEmpty()) {
					util.setTextWithEnter(logger, nav.tooltip(driver, "Search Available List", "", ""), entityName);
					util.pause(logger, "10");
					util.clickOn(logger, driver, "",
							driver.findElement(By.xpath("(.//option[text()='" + entityName + "'])")));
					util.clickOn(logger, driver, "", nav.tooltip(driver, "Add", "", ""));
				} else {
					util.setTextWithEnter(logger, driver.findElement(By.xpath("//*[text()='" + sectionName
							+ "']/parent::label/parent::div/following-sibling::div//*[contains(@aria-label,'Search Available List')][not(@aria-hidden='true')]")),
							entityName);
					util.pause(logger, "10");
					util.clickOn(logger, driver, "",
							driver.findElement(By.xpath("(.//option[text()='" + entityName + "'])")));
					util.clickOn(logger, driver, "", driver.findElement(By.xpath("//*[normalize-space(text())='"
							+ sectionName
							+ "']/parent::label/parent::div/following-sibling::div//a[@data-original-title='Add']")));
				}
				System.out.println("Entity Added: " + entityName);
			}
			logger.log(Status.PASS, "Entity is successfully filtered out and added to right list");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to filter entity");
		}
	}

	// Move Entity from right dropdown to left dropdown
	// entityName-entity to be filtered
	// rightListName-name of right side dropdown
	// section Name-either null or main section name which is displayed above
	// filters and buttons
	public void removeEntity(ExtentTest logger, String entityName, String rightListName, String sectionName) {
		try {
			if (sectionName.isEmpty()) {
				wait.elementIsClickable(logger, driver, nav.tooltip(driver, "Search Available List", "", ""));
			} else {
				wait.elementIsClickable(logger, driver, driver.findElement(By.xpath("//*[text()='" + sectionName
						+ "']/parent::label/parent::div/following-sibling::div//*[@aria-label='Search Available List'][not(@aria-hidden='true')]")));
			}
			Select rightDropdown = new Select(nav.filterDropdown(driver, rightListName, sectionName));
			List<WebElement> rightList = rightDropdown.getOptions();
			int flag = 0;
			for (int k = 0; k < rightList.size(); k++) {
				if (rightList.get(k).getText().equals(entityName)) {
					rightDropdown.selectByVisibleText(entityName);
					logger.log(Status.INFO, "Entity is present in Right dropdown: " + entityName);
					System.out.println("Entity is   present in selected dropdown: " + entityName);
					break;
				} else {
					flag = flag + 1;
				}
			}
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Remove", "", ""));
			logger.log(Status.PASS, "Entity is successfully removed from right list");
			if (flag == rightList.size()) {
				logger.log(Status.FAIL, "Entity is  not present in Right dropdown: " + entityName);
			}
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to remove entity");
		}
	}

	// View a Record in Table
	// columnName-name of column to be provided in Search dropdown
	// item name-name of item to be provided in Search textfield
	@Test
	public void viewRecord(String columnName, String itemName) {
		ExtentTest logger = createTest("View Record");
		try {
			addColumn(logger, columnName);
			// util.waitTillElementIsClickable(logger, driver, nav.dropdown(driver,
			// "Search",""));
			util.selectBoxByValue(logger, nav.dropdown(driver, "Search", ""), columnName);
			// util.waitTillElementIsClickable(logger, driver, nav.backgroundText(driver,
			// "Search",""));
			util.setTextWithEnter(logger, nav.backgroundText(driver, "Search", ""), itemName);
			util.pause(logger, "5");
			// util.waitTillElementIsClickable(logger, driver,
			// nav.tooltip(driver,"Preview","",""));
			util.clickOn(logger, driver, "", nav.tooltip(driver, "Preview", "", ""));
			util.pause(logger, "3");
			// util.waitTillElementIsClickable(logger, driver, nav.link(driver, "Open
			// Record",""));
			util.clickOn(logger, driver, "", nav.link(driver, "Open Record", ""));
			util.pause(logger, "5");
			logger.log(Status.PASS, "item is viewed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to view item");
		}
	}

	// Delete a Record after opening it from list view
	@Test
	public void deleteRecord(ExtentTest logger) {
		try {
			// delete Record
			// util.waitTillElementIsClickable(logger, driver, nav.button(driver,
			// "Delete",""));
			util.clickOn(logger, driver, "", nav.button(driver, "Delete", ""));
			util.pause(logger, "10");
			// util.waitTillElementIsClickable(logger, driver, nav.popupButton(driver,
			// "Delete"));
			util.clickOn(logger, driver, "", nav.popupButton(driver, "Delete"));
			util.pause(logger, "60");
			// util.waitTillElementIsClickable(logger, driver, nav.dropdown(driver,
			// "Search",""));
			logger.log(Status.PASS, "Record is deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to delete record");
		}
	}

	// verify column values in a table
	// columnName-column where value is present
	// value-value to verify
	public void verifyColumnValuesInTable(ExtentTest logger, WebDriver driver, String columnName, String value) {
		try {
			int i = util.returnColumnIndex(logger, "//th", columnName, driver);
			String str;
			int size = driver.findElements(By.xpath("//tbody[contains(@class,'body')]//tr")).size();
			System.out.println("Row Size is " + size);
			System.out.println("Column Size is " + i);
			for (int j = 1; j <= size; j++) {
				str = driver.findElement(By.xpath("//tbody[contains(@class,'body')]//tr[" + j + "]//td[" + i + "]"))
						.getAttribute("innerText");
				System.out.println(str);
				if (str.contains(value)) {
					System.out.println("Row Index" + j);
					logger.log(Status.PASS, "Value matching, expected is " + value + " and displaying is " + str);
				} else {
					logger.log(Status.FAIL,
							"Value not matching, expected is " + value + " but displaying is " + str);
				}
			}
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to verify Column values in table");
		}
	}

	// Set Filters for filtering records in list view
	// field-name of field on which filter to be applied
	// operation-condition to be used for filter
	// value-value for filter
	// conditionNumber-number of rows currently present for filters
	public void setFilterConditions(ExtentTest logger, String field, String operation, String value,
			int conditionNumber) {
		try {
			util.clickOn(logger, driver, "", driver.findElement(By.xpath(
					"(//table//tr[@class='filter_row_condition']/td[@id='field']//a)[" + conditionNumber + "]")));
			util.pause(logger, "2");
			util.setText(logger, nav.txt_DropList, field);
			util.pause(logger, "10");
			if (driver
					.findElements(
							By.xpath(
									".//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value + "')]"))
					.size() != 0) {
				util.clickOn(logger, driver, "", driver.findElement(
						By.xpath(
								"(.//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value + "')])")));
			} else if (driver
					.findElements(
							By.xpath(".//li[contains(@class,'highlighted')]/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				util.clickOn(logger, driver, "", driver.findElement(
						By.xpath(".//li[contains(@class,'highlighted')]/div[contains(text(),'" + value + "')]")));
			} else if (driver
					.findElements(
							By.xpath(".//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				util.clickOn(logger, driver, "", driver.findElement(
						By.xpath(".//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value + "')]")));
			} else if (driver
					.findElements(By.xpath(".//li[contains(@class,'highlighted')]/div/span"))
					.size() != 0) {
				util.clickOn(logger, driver, "", driver.findElement(
						By.xpath(".//li[contains(@class,'highlighted')]/div/span")));
			}
			util.selectBoxByValue(logger, driver.findElement(By.xpath(
					"(//table//tr[@class='filter_row_condition']/td[@id='oper']//select)[" + conditionNumber + "]")),
					operation);
			if (value.equalsIgnoreCase("")) {
			} else {
				if (driver.findElements(By.xpath("(//table//tr[@class='filter_row_condition']/td[@id='value'])["
						+ conditionNumber + "]//input[not(@type='hidden')]")).size() != 0) {
					util.setText(logger,
							driver.findElement(By.xpath("(//table//tr[@class='filter_row_condition']/td[@id='value'])["
									+ conditionNumber + "]//input[not(@type='hidden')]")),
							value);
				}
				if (driver.findElements(By.xpath("(//table//tr[@class='filter_row_condition']/td[@id='value'])["
						+ conditionNumber + "]//select")).size() != 0) {
					util.selectBoxByValue(logger,
							driver.findElement(By.xpath("(//table//tr[@class='filter_row_condition']/td[@id='value'])["
									+ conditionNumber + "]//select")),
							value);
				}
			}
			logger.log(Status.PASS, "Filter is set successfully");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to set filters");
		}
	}

	// upload file on portal page
	public void uploadAttachmentOnPortalPage(ExtentTest logger, String fileName) {
		try {
			// util.waitTillElementIsClickable(logger, driver, nav.tooltip(driver,"Manage
			// Attachments","",""));
			util.clickByJavascriptExecutor(logger, driver, nav.tooltip(driver, "Manage Attachments", "", ""));
			util.pause(logger, "5");
			String filePath = System.getProperty("user.dir") + "\\" + fileName;
			System.out.println("FilePath:" + filePath);
			util.setText(logger, nav.text(driver, "Attach", ""), filePath);
			util.pause(logger, "10");
			// util.waitTillElementIsVisible(logger, driver, nav.link(driver,"rename",""));
			// util.waitTillElementIsClickable(logger, driver, nav.btn_closeModal);
			util.clickOn(logger, driver, "", nav.btn_closeModal);
			logger.log(Status.PASS, "Attachment is successful");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Attachment Issue");
		}
	}

	// upload file on portal page by attach button present in bottom of page
	public void uploadAttachmentAtBottomOnPortalPage(ExtentTest logger, String fileName) {
		try {
			portal.txt_attachment.sendKeys(System.getProperty("user.dir") + "\\" + fileName);
			util.pause(logger, "10");
			// util.waitTillElementIsVisible(logger, driver, portal.btn_deleteAttachment);
			logger.log(Status.PASS, "File is uploaded");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].value = '';", portal.txt_attachment);
			util.pause(logger, "5");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Attachment Issue");
		}
	}

	// Verify Popup message
	// msg- popup message
	public void verifyPopupValidationMsgOnPortal(ExtentTest logger, WebDriver driver, String msg) {
		try {
			util.verifyExactText(logger, portal.lbl_popUpValidationMsg, msg);
			logger.log(Status.PASS, "Popup message is verified");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to verify popup message");
		}
	}

	// close all browsers which are opened
	// @AfterTest
	public void closeAllBrowsers() {
		ExtentTest logger = createTest(" Close Browsers");
		try {
			for (int i = 0; i < browserHelper.driversList.size(); i++) {
				browserHelper.driversList.get(i).quit();
				logger.log(Status.PASS, "<b>" + browserHelper.driversList.get(i) + "</b> browser is closed");
			}
			browserHelper.driversList.clear();
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Browser is unable to close");
		}
	}

	// search and select value from look up window
	// select first value if no search value is passed
	public void selectFromLookUp(ExtentTest logger, String searchColumn, String searchValue) {
		try {
			String parentHandle = frameHelper.getParentWindowHandle(logger, driver);
			util.pause(logger, "5");
			frameHelper.switchToChildWindow(logger, driver, parentHandle);
			if (searchValue.isEmpty()) {
				util.clickOn(logger, driver, "", nav.lnk_lookUpFirstSearchResult);
			} else {
				util.setTextWithEnter(logger, nav.searchInLookUp(logger, driver, searchColumn), searchValue);
				util.pause(logger, "10");
				util.clickOn(logger, driver, "", driver.findElement(
						By.xpath("//a[@class='glide_ref_item_link'][normalize-space(text())='" + searchValue + "']")));
			}
			frameHelper.switchToParentWindowHandle(logger, driver, parentHandle);
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to filter entity");
		}
	}

	// Verify which text is selected in dropList on portal page
	// fieldName-dropList label name
	// name-selected text to be verified
	public void verifyTextInDropListOnPortal(ExtentTest logger, String fieldName, String name) {
		try {
			util.verifyExactText(logger,
					driver.findElement(
							By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div/a//span")),
					name);
			logger.log(Status.PASS, "Text is verified in drop list");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to verify text in drop list");
		}
	}

	// Edit Column values from list view
	// columnName-name of column to be edited
	// value-value for edit
	// type- WebElement Type
	public void editTableItems(ExtentTest logger, int rowNumber, String columnName, String type, String value,
			String tabName) {
		try {
			util.clickOn(logger, driver, "", nav.tableCellTextBasedOnRowNumber(logger, rowNumber, columnName, ""));
			util.pause(logger, "3");
			util.doubleClick(logger, driver, nav.tableCellText(logger, columnName, ""));
			util.pause(logger, "10");
			if (type.equals("dropdown")) {
				util.selectBoxByValue(logger,
						driver.findElement(
								By.xpath(".//span[contains(text(),'" + columnName + "')]/parent::label//select")),
						value);
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", nav.tooltip(driver, "Save (Enter)", "", ""));
			} else if (type.equals("textfield")) {
				driver.findElement(By.xpath(
						"//td[@id='cell_edit_window_header']//input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')]"))
						.sendKeys(value);
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", nav.tooltip(driver, "Save (Enter)", "", ""));
			} else if (type.equals("calendar")) {
				util.clickOn(logger, driver, "", nav.label(driver, "Go to Today", ""));
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", nav.tooltip(driver, "Save (Enter)", "", ""));
			}
			util.pause(logger, "10");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to edit");
		}
	}

	// get error message and close
	public String getErrorMessageAndCloseInNavPage(ExtentTest logger) {
		String str = "";
		try {
			System.out.println(util.getElementText(logger, nav.lbl_errorMessage));
			logger.log(Status.INFO, "Error Message - " + util.getElementText(logger, nav.lbl_errorMessage));
			str = util.getElementText(logger, nav.lbl_errorMessage);
			if (driver.findElements(By.xpath("//button[@id='close-messages-btn']")).size() != 0) {
				util.clickOn(logger, driver, "", nav.btn_CloseErrorMessage);
			}
			return str;
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to edit");
		}
		return str;
	}

	// verify DropList values
	// Pass list of options to validate separated by '|'
	public void verifyDropListElements(WebDriver driver, ExtentTest logger, String fieldName, String optionsList) {
		util.verifyListValues(getLogger(), driver, portal.dropListElements(driver, fieldName), optionsList);
	}

	// search from Tree look up structure
	// Pass "" if you don't have any child or sub child
	public void selectFromTreeLookUp(ExtentTest logger, String parent, String child, String subChild) {
		try {
			String parentHandle = frameHelper.getParentWindowHandle(logger, driver);
			frameHelper.switchToChildWindow(logger, driver, parentHandle);
			wait.forPageToLoadCompletely(logger, driver);
			if (child.isEmpty() && subChild.isEmpty()) {
				util.clickOn(logger, driver, "", nav.link(driver, parent, ""));
				util.pause(logger, "5");
			} else if (!(subChild.isEmpty())) {
				util.clickOn(logger, driver, "", driver.findElement(By.xpath("//a[text()='" + parent
						+ "']/parent::td/preceding-sibling::td//img[contains(@src,'expand')]")));
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", driver.findElement(By.xpath(
						"//a[text()='" + child + "']/parent::td/preceding-sibling::td//img[contains(@src,'expand')]")));
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", nav.link(driver, subChild, ""));
			} else {
				util.clickOn(logger, driver, "", driver.findElement(By.xpath("//a[text()='" + parent
						+ "']/parent::td/preceding-sibling::td//img[contains(@src,'expand')]")));
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", nav.link(driver, child, ""));
			}
			frameHelper.switchToParentWindowHandle(logger, driver, parentHandle);
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to select the data from lookup tree");
		}
	}

	// Pass the fieldName
	// Verify whether field is mandatory or not
	public void verifyMandatoryField(ExtentTest logger, String fieldName) {
		try {
			WebElement element = portal.mandatoryField(driver, fieldName);
			if (element != null) {
				logger.log(Status.PASS, "Element is Mandatory Field");
				System.out.println("Element is Mandatory Field");
			} else {
				logger.log(Status.FAIL, "Element is Not Mandatory Field / Field is not available");
				System.out.println("Element is Not Mandatory Field / Field is not available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to find the Mandatory Field");
		}
	}

	// Pass the fieldName
	// Verify whether field is mandatory or not
	public void verifyNonMandatoryField(ExtentTest logger, String fieldName) {
		try {
			WebElement element = portal.mandatoryField(driver, fieldName);
			if (element == null) {
				logger.log(Status.PASS, "Element is Not Mandatory Field / Field is not available");
				System.out.println("Element is Not Mandatory Field / Field is not available");
			} else {
				logger.log(Status.FAIL, "Element is Mandatory Field");
				System.out.println("Element is Mandatory Field");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to find the Mandatory Field");
		}
	}

	// Pass number of Checkboxes need to be clicked
	// Click on Checkboxes in list view
	public void clickCheckboxInListView(ExtentTest logger, String numberOfCheckboxes) {
		try {
			int n = Integer.parseInt(numberOfCheckboxes);
			List<WebElement> checkboxes = driver
					.findElements(By.xpath("//input[@title='Mark record for List Action']/parent::span"));
			for (int i = 0; i < n; i++) {
				util.clickOn(logger, driver, "", checkboxes.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Unable to click on checkboxes");
		}
	}

	// QUEBEC RELEASE

	/*
	 * Function need to be called as shown below arguments to the function are
	 * logger, driver and js path of the shadow element Example to get the text from
	 * shadow element: ---------------------------------------- WebElement contents
	 * = getShadowRootWebElement(logger, driver, "return
	 * document.querySelector('.sn-widget-textblock-body_formatted').
	 * shadowRoot.querySelector('div')"); contents.getText();
	 * -----------------------------------------
	 */
	public WebElement getShadowRootWebElement(ExtentTest logger, WebDriver driver, String jsPath) {
		try {
			util.pause(logger, "15");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			shadowElement = (WebElement) executor.executeScript(jsPath);
			util.pause(logger, "5");
			System.out.println("Shadow element identification by using JavaScriptExecutor success");
			logger.log(Status.PASS, "Shadow element identification by using JavaScriptExecutor success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, "Failed to identify element due to <b style='color:red'>" + e.getClass() + "<br>"
					+ e.getMessage() + "</b>");
		}
		return shadowElement;
	}

	// Method to find shadow Root - geetha.ganesan
	private SearchContext getShadowRoot(WebDriver driver, WebElement shadowHost) {
		// Provide access to Javascript
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		return (SearchContext) jse.executeScript("return arguments[0].shadowRoot", shadowHost);

	}

	// Method to find shadow Element- geetha.ganesan
	private WebElement getShadowElement(WebDriver driver, WebElement shadowHost, String cssOfShadowElement) {
		SearchContext shadowRoot = getShadowRoot(driver, shadowHost);
		if (shadowRoot != null) {
			return shadowRoot.findElement(By.cssSelector(cssOfShadowElement));
		} else {
			return null;
		}
	}

	// Method to find shadow Element -Give multi elements- geetha.ganesan
	private List<WebElement> getShadowElements(WebDriver driver, WebElement shadowHost, String cssOfShadowElement) {
		SearchContext shadowRoot = getShadowRoot(driver, shadowHost);
		if (shadowRoot != null) {
			return shadowRoot.findElements(By.cssSelector(cssOfShadowElement));
		} else {
			return null;
		}
	}

	// Function to open the form through filter navigator - geetha.ganesan
	// searchTable
	public void shadowDomFilterSearch(ExtentTest logger, String filterData, List<String> parentList, String child) {

		try {
			util.pause(logger, "20");
			System.out.println("inside backend");
			// Provide access to Javascript
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			// Code to check whether the filter is pinned or not
			WebElement linkAllShadowRoot = (WebElement) jse.executeScript(
					"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header')");
			WebElement linkAll = getShadowElement(driver, linkAllShadowRoot, "div>div[aria-label='All']");
			if (linkAll.isDisplayed()) {
				System.out.println("link All is not pinned");
				util.clickOn(logger, driver, "", linkAll);
				util.pause(logger, "2");
				System.out.println("link All clicked");
				util.pause(logger, "2");
				WebElement btnPinRoot = (WebElement) jse.executeScript(
						"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header').shadowRoot.querySelector('sn-polaris-menu:nth-child(1)')");
				WebElement btnPinAll = getShadowElement(driver, btnPinRoot, "button[aria-label='Pin All menu']");
				util.clickOn(logger, driver, "", btnPinAll);
			} else {
				System.out.println("link All is already pinned");
			}

			// Find out the web element to perform action
			WebElement filter = (WebElement) jse.executeScript(
					"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header').shadowRoot.querySelector('sn-polaris-menu.is-main-menu.is-pinned.can-animate').shadowRoot.querySelector('#filter')");
			util.pause(logger, "2");
			System.out.println(filterData);
			util.clickOn(logger, driver, "", filter);
			util.setText(logger, filter, filterData);
			util.pause(logger, "3");

			WebElement childWebElement = getCollapsibleElement(driver, parentList, child);
			if (childWebElement != null) {
				System.out.println("Child matched");
				jse.executeScript("arguments[0].scrollIntoView()", childWebElement);
				wait.elementIsClickable(logger, driver, childWebElement);
				childWebElement.click();
				util.pause(logger, "3");

			} else {
				System.out.println("Child not matched");
				return;
			}

			System.out.println("Form is Opened");
			util.pause(logger, "8");

			// Switch to the frame
			WebElement frame = (WebElement) jse.executeScript(
					"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('iframe#gsft_main')");
			util.pause(logger, "3");
			System.out.println("Switch frame done " + driver.switchTo().frame(frame));
			util.pause(logger, "3");
			logger.log(Status.PASS, "Table is opened");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to open table");

		}

	}

	private WebElement getCollapsibleElement(WebDriver driver, List<String> parentList, String childName)
			throws IOException {
		WebElement shadowHost = driver.findElement(By.cssSelector("macroponent-f51912f4c700201072b211d4d8c26010"));
		WebElement root1 = getShadowElement(driver, shadowHost, "sn-polaris-layout");
		WebElement root2 = getShadowElement(driver, root1, "sn-polaris-header");
		WebElement root3 = getShadowElement(driver, root2, "sn-polaris-menu");
		List<WebElement> root4 = getShadowElements(driver, root3, "sn-collapsible-list");

		ArrayList<String> parentItem = new ArrayList<String>();
		parentItem.clear();
		for (int i = 0; i < parentList.size(); i++) {
			parentItem.add(parentList.get(i)); // Take a copy of parent list and Store in parent item
		}
		if (root4 != null) {
			while (!parentItem.isEmpty()) {
				String parentName = parentItem.remove(0); // save the first parent name alone in parentName
				WebElement matchParent = getMatchParent(driver, root4, parentName);
				if (matchParent != null) {
					System.out.println("matchParent matched");
					WebElement child = getMatchChild(driver, matchParent, childName);
					if (child != null) {
						return child;
					} else {
						List<WebElement> subParent = getShadowElements(driver, matchParent,
								"div.snf-collapsible-list > div > ul > li > sn-collapsible-list.nested-item");
						if (subParent != null && !subParent.isEmpty()) {
							System.out.println("subParent  " + subParent.size());
							root4 = subParent;
						}
					}
				}
			}
		} else {
			throw new IOException("Modules are not founded");
		}
		return null;
	}

	// Check whether child matches with our input- geetha.ganesan
	private WebElement getMatchChild(WebDriver driver, WebElement matchParent, String child) {
		List<WebElement> childList = getShadowElements(driver, matchParent, "div.snf-collapsible-list > div > ul > li");
		for (int j = 0; j < childList.size(); j++) {
			String childModule = childList.get(j).getText().trim();
			System.out.println("Child " + j + "    " + childModule);
			if (childModule.equalsIgnoreCase(child.trim())) {
				WebElement root6 = childList.get(j);
				return root6;
			}
		}
		return null;
	}

	// Check whether Parent and sub parent matches with our input- geetha.ganesan
	private WebElement getMatchParent(WebDriver driver, List<WebElement> root4, String parentName) {
		for (int i = 0; i < root4.size(); i++) {
			WebElement root5 = getShadowElement(driver, root4.get(i), "div.snf-collapsible-list > button");
			System.out.println("Parent " + i + "    " + root5.getText() + "      " + parentName);
			// Comparing with input
			if (root5.getText().trim().equalsIgnoreCase(parentName.trim())) {
				System.out.println("matched " + i + "    " + root5.getText() + "      " + parentName);
				return root4.get(i);
			}
		}
		return null;
	}

	// Navigation to nav page from portal
	@Test
	public void navigateToNavPageFromPortal() {
		ExtentTest logger = createTest("Navigate to Nav page");
		try {
			String pageURL = driver.getCurrentUrl();
			if (pageURL.contains("portal")) {
				//// util.waitTillElementIsClickable(logger, driver, portal.tooltip(driver,
				//// "User Profile"));
				util.clickOn(logger, driver, "Menu", portal.tooltip(driver, "Menu"));
				util.clickByJavascriptExecutor(logger, driver, portal.link(driver, "Go to fulfill.accenture.com"));
				// util.pause(logger, "10");
				//// util.waitTillElementIsClickable(logger, driver, nav.backgroundText(driver,
				// "Filter navigator",""));
			}
			logger.log(Status.PASS, "Navigated to Nav page from portal");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to navigate to Nav Page");
		}
	}

	// End Impersonate
	@Test
	public void endImpersonation(ExtentTest logger) {

		try {
			driver.switchTo().defaultContent();
			wait.nowPageIsLoaded(logger, driver);
			// Ensure we are staying at nav page
			navigateToNavPageFromPortal();
			util.pause(logger, "10");
			// Click avatar
			WebElement shadowHost = driver.findElement(By.cssSelector("macroponent-f51912f4c700201072b211d4d8c26010"));
			WebElement root1 = getShadowElement(driver, shadowHost, "sn-polaris-layout");
			WebElement root2 = getShadowElement(driver, root1, "sn-polaris-header");
			WebElement root3 = getShadowElement(driver, root2, "now-avatar");
			util.clickOn(logger, driver, "Avatar icon", root3);
			util.pause(logger, "5");

			// Click on end impersonation
			WebElement root4 = getShadowElement(driver, root2,
					"sn-contextual-menu#userMenu > span > span > div > div.user-menu-controls > button.user-menu-button.unimpersonate.keyboard-navigatable.polaris-enabled");
			// util.clickOn(logger, root4);
			util.clickByJavascriptExecutor(logger, driver, root4);
			util.pause(logger, "15");

			/*
			 * JavascriptExecutor jse = (JavascriptExecutor)driver;
			 * WebElement avatar = (WebElement) jse.
			 * executeScript("return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header').shadowRoot.querySelector('div > now-avatar').shadowRoot.querySelector('span > span > span > span.now-line-height-crop')"
			 * );
			 * util.clickOn(logger, avatar);
			 * util.pause(logger, "2");
			 * WebElement endImpersonate = (WebElement) jse.
			 * executeScript("return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header').shadowRoot.querySelector('#userMenu > span > span:nth-child(2) > div > div.user-menu-controls > button.user-menu-button.unimpersonate.keyboard-navigatable.polaris-enabled > div')"
			 * );
			 * util.clickByJavascriptExecutor(logger, driver, endImpersonate);
			 */
			util.pause(logger, "8");
			util.acceptAlertIfAny(logger, driver);
			util.pause(logger, "2");
			util.acceptAlertIfAny(logger, driver);
			util.pause(logger, "2");
			logger.log(Status.PASS, "End Impersonation is done");
			System.out.println("End Impersonation is done");

		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to End impersonate");
		}
	}

	// Global Search
	@Test
	public void globalSearch(ExtentTest logger, String reqNumber) {
		try {
			util.pause(logger, "10");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement search = (WebElement) jse.executeScript(
					"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header').shadowRoot.querySelector('nav > div > div.ending-header-zone > div.polaris-header-controls > div.polaris-search.polaris-enabled > sn-search-input-wrapper').shadowRoot.querySelector('sn-component-workspace-global-search-typeahead').shadowRoot.querySelector('#sncwsgs-typeahead-input')");
			util.pause(logger, "3");
			util.clickOn(logger, driver, "Search", search);
			util.pause(logger, "3");
			util.setText(logger, search, reqNumber);
			util.pause(logger, "5");
			WebElement searchResult = (WebElement) jse.executeScript(
					"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header').shadowRoot.querySelector('nav > div > div.ending-header-zone > div.polaris-header-controls > div.polaris-search.polaris-enabled > sn-search-input-wrapper').shadowRoot.querySelector('sn-component-workspace-global-search-typeahead').shadowRoot.querySelector('div.sn-global-typeahead > #sncwsgs-typeahead-sections > ul > ul.sn-list-ul-group > li#sncwsgs-typeahead-record-0-0')");
			util.pause(logger, "3");
			wait.elementIsClickable(logger, driver, searchResult);
			util.clickOn(logger, driver, "", searchResult);
			util.pause(logger, "15");
			util.acceptAlertIfAny(logger, driver);
			util.pause(logger, "5");

		}

		catch (Exception exception) {
			util.screenShotAndErrorMsg(logger, exception, driver, "Unable to find global search");
		}
		Assert.assertEquals(logger.getStatus(), Status.PASS);
	}

	/*
	 * Argentina Methods
	 */

	/**
	 * This method redirects you to any link set on Input Data
	 * 
	 * @param logger   {@code ExtentTest} log status for the reports
	 * @param siteName {@code String} Title name of the site
	 * @param rowName  {@code String} Row name in excel table
	 * @param colName  {@code String} Column name in excel table
	 * 
	 * @throws Exception        When failed, throws an error message on console and
	 *                          logger.
	 * @throws TimeoutException if the maximum waiting time to find the element is
	 *                          exceeded
	 * 
	 */
	public void navigateTo(ExtentTest logger, String siteName, String rowName, String colName) {
		try {
			String siteLink = getSpecificValue(rowName, colName);
			navigate(logger, siteLink);
			validateView_byLink(driver.getCurrentUrl(), siteLink);
			util.loggerMessage(logger, true, "Successfully redirected to '" + siteName + "'");
		} catch (Exception e) {
			util.catchError(logger, driver, e, "access to '" + siteName + "'");
		}
	}

	/**
	 * This method should verify the current url and wait util the Page Is
	 * completely Loaded
	 * 
	 * @param siteLink
	 * @throws Exception
	 */
	public void validateView_byLink(String currentSiteLink, String expectedSiteLink) throws Exception {

		if (currentSiteLink.equals(expectedSiteLink)) {

			util.loggerMessage(getLogger(), "Link: " + currentSiteLink);
		} else {

			util.loggerMessage(getLogger(), "There are differences between...\n" +
					"ExpectedSiteLink: " + expectedSiteLink + "\n" +
					"CurrentSiteLink : " + currentSiteLink);
		}
		validateView_byLink(currentSiteLink);

	}

	public void validateView_byLink(String currentSiteLink) throws Exception {

		if (currentSiteLink.contains("accenture.com/now/nav")) {

			wait.nowPageIsLoaded(getLogger(), driver);
			util.loggerMessage(getLogger(), "View: Backend view");

		} else if (currentSiteLink.contains("accenture.com/support_portal")) {

			wait.visibilityOf(getLogger(), driver, "support_portal", portal.formContainers(driver), 60);
			util.loggerMessage(getLogger(), "View: support_portal");

		} else {

			util.loggerMessage(getLogger(), "Unexpected link: " + currentSiteLink);
			throw new Exception();
		}
	}

	/**
	 * This method impersonates a specific user and moves back to fulfiller view
	 * 
	 * @param logger  {@code ExtentTest} log status for the reports
	 * @param persona {@code String} user EID for impersonate
	 * 
	 * @see #impersonateAsUser(ExtentTest, String)
	 * 
	 * @throws Exception When failed, throws an error message on console and logger.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void impersonateAndBack(ExtentTest logger, String persona) {
		try {
			impersonateAsUser(logger, persona);
			navigateTo(logger, "Support Portal Fulfiller view", "navigate", "FulfillerURL");
		} catch (Exception e) {
			util.catchError(logger, driver, e, "Impersonation failed for: " + persona);
		}
	}

	/**
	 * This method should be able to submit every frontend form
	 * 
	 * just need to follow an excel template to get the input data
	 * 
	 * form url must be on (navigate, "Form")
	 * The field names must be listed on ("DataSet" + i, "Field Name")
	 * The field data must be listed on ("DataSet" + i, "Field Data")
	 * 
	 * @param logger
	 */
	@Test
	public void submitForm_Frontend(ExtentTest logger, String prefixOfTicket) {

		List<String> fieldName_List = getColumnValues("Field Name");
		List<String> fieldData_List = getColumnValues("Field Data");

		if (fieldData_List.size() != fieldName_List.size()) {
			System.err.println("ERROR: missing information to populate all fields.");
			return;
		}

		// debug
		util.loggerMessage(getLogger(), "fieldName_List: " + fieldName_List);
		util.loggerMessage(getLogger(), "fieldData_List: " + fieldData_List);
		System.out.println("The data to complete the fields has been provided.");

		for (int x = 0; x < fieldName_List.size(); x++) {

			util.completeFieldFrontend(getLogger(), driver, fieldName_List.get(x), fieldData_List.get(x));
		}

		verifyMandatoryFieldsAndSubmit(prefixOfTicket);
	}

	String incompleteFields_path = "//legend[@id='required_information_bottom']/parent::fieldset/descendant::label";

	public boolean verifyMandatoryFields() {
		return portal.incompleteFields != null;
	}

	/**
	 * <h4>This method verify that all mandatory fields in form was completed</h4>
	 * 
	 * @param prefixOfTicket
	 *                       Firsts letters of the expected ticket number such as :
	 *                       RITM, LOA, HR, INC, CHG, etc.
	 * 
	 * @author // b.marmol.maldonado
	 */
	public void verifyMandatoryFieldsAndSubmit(String prefixOfTicket) {
		try {
			// if (driver.findElements(By.xpath(incompleteFields_path)).size() != 0) {
			util.loggerMessage(getLogger(), "incompleteFields var: " + portal.incompleteFields);
			if (portal.incompleteFields != null) {
				WebElement submit_btn = portal.button(driver, "Submit");
				try {
					if (submit_btn == null) {
						submit_btn = portal.button(driver, "Next");
					}
					util.scrollByVisibleElement(getLogger(), driver, submit_btn);
					util.clickOn(getLogger(), driver, "Submit button", submit_btn);

				} catch (Exception e) {

					if (driver.findElements(By.xpath("//div[@id='dummyfooter' and contains(text(), 'Hide')]"))
							.size() != 0) {
						driver.findElement(By.xpath("//div[@id='dummyfooter']")).click();
						util.loggerMessage(getLogger(), true,
								"Hide footer Button was clicked on verifyMandatoryFieldsAndSubmit()");
					}

					util.clickOn(getLogger(), driver, "Submit button", submit_btn);
				}

				util.getTicketNumber(getLogger(), driver, prefixOfTicket);
				util.loggerMessage(getLogger(), true, "Form completed and submitted");

			} else {

				util.loggerMessage(getLogger(), true, "Form could not be submitted, some fields are incomplete");
				portal.incompleteFields.forEach(
						field -> util.loggerMessage(getLogger(), false,
								"'" + field.getText() + "' field is incomplete"));
			}
		} catch (Exception e) {
			util.catchError(getLogger(), driver, e, "submit the form");
		}
	}

	/**
	 * This method fill each form field of the given Map
	 * 
	 * @param logger        {@code ExtentTest} log status for the reports
	 * @param formName      {@code String} Name of the form
	 * @param formFields    {@code Map} Form field list with type and value
	 * @param fulfillerView {@code Boolean} true = "fulfiller view" | false =
	 *                      "Portal view"
	 * 
	 * @author Emiliano.molina
	 */
	public void completeForm(ExtentTest logger, String formName, Map<String, Map<String, String>> formFields,
			Boolean fulfillerView) {
		try {
			formFields.forEach((fieldName, type_value) -> {
				for (Map.Entry<String, String> entry : type_value.entrySet()) {
					// System.out.println("Field -> " + fieldName + " has value: " +
					// entry.getValue());
					if (entry.getKey().equals("dropdown")) {
						if (fulfillerView) {
							// completeFieldBackend(logger, fieldName, inputFieldPortal, formName);
						} else {
							// util.completeField(logger, fieldName, entry.getValue());
						}
						break;
					} else if (entry.getKey().equals("input") || entry.getKey().equals("textArea")) {
						if (fulfillerView) {
							// completeFieldBackend(logger, formName, nav.input(logger, fieldName),
							// entry.getValue());
						} else {
							// util.completeTextField(logger, fieldName, entry.getValue());
						}
						break;
					}
				}
			});
		} catch (Exception e) {
			util.catchError(logger, driver, e, "broken");
		}
	}

	/**
	 * THIS METHOD Open a request in the Support Portal
	 * (frontend) by ticket number
	 * 
	 * @param logger       {@code ExtentTest} log status for the reports
	 * @param ticketN      {@code String} ticket number
	 * @param openOrClosed {@code String} Switch to search open or closed request
	 */
	public void openRequestInFrontByNumber(ExtentTest logger, String ticketN, Boolean openCase) {

		By inputSearch = By.xpath("//input[@title='Number']"),
				closedCasesBTN = By.xpath("//li[@id='closedCases-list']/a"),
				openCases = By.xpath("//a[contains(.,'Open Cases')]"),
				caseActivityBTN = By.xpath("//a[text()='Case Activity']");

		String firstOccurrence = "//td[text()='" + ticketN + "']";

		try {
			navigateTo(logger, "My Cases View", "DataSet1", "MyCases");
			if (openCase) {
				wait.elementToBeClickable(logger, driver, "Open Cases", openCases, 60);
			} else {
				wait.elementToBeClickable(logger, driver, "Closed Cases", closedCasesBTN, 120);
			}
			util.pause(logger, "5");
			util.toggleDisplayView(logger, driver, "List View");
			util.pause(logger, "8");
			wait.elementToBeClickable(logger, driver, "Search by name field", inputSearch, 10);
			util.pause(logger, "2");
			driver.findElement(inputSearch).click();
			driver.findElement(inputSearch).sendKeys(ticketN + Keys.ENTER);
			wait.elementToBeClickable(logger, driver, "Ticket Number: " + ticketN, firstOccurrence, 15);
			wait.visibilityOf(logger, driver, "Portal View form", caseActivityBTN, 60);
			util.loggerMessage(logger, "pass", "Request: " + ticketN + " is opened");

		} catch (Exception e) {
			util.catchError(logger, driver, e, "open request in Portal");
		}
	}

	// This method searches a Ticket By its number in RCO Requests Table
	public void openRequestByNumber(ExtentTest logger, String ticketN) {

		By searchField = By.xpath("//label[.='Search']/following-sibling::input[@placeholder='Search']");
		By filter = By.xpath("//select[]");
		By firstOccurrence = By.xpath("(//a[@class='linked formlink' and contains(text(), '" + ticketN + "')])[1]");
		try {
			navigateTo(logger, "Requests Table", "navigate", "requestTable");
			if (driver.findElements(filter).size() == 0) {
				wait.iframeAndSwitch(logger, driver, nav.iframe_gsft);
			}
			util.visibilityAndSendKeys(logger, driver, "Filter", filter, "Number", 10);
			util.visibilityAndSendKeys(logger, driver, "Search by", searchField, ticketN, 10);
			wait.elementToBeClickable(logger, driver, "Ticket Number", firstOccurrence, 20);
			driver.findElement(firstOccurrence).click();
			// WebElement specificWebElementOnTicket =
			// driver.findElement(By.xpath("//div[]"));
			// wait.elementIsVisible(logger, driver, specificWebElementOnTicket);
			util.loggerMessage(logger, "pass", "Request " + ticketN + " is visible in the fulfiller view");
		} catch (Exception e) {
			util.catchError(logger, driver, e, "open the ticket number: " + ticketN);
		}
	}

	/**
	 * This method end impersonation in backend and go back to site
	 * 
	 * @param logger
	 * 
	 * @throws Exception When failed, throws an error message on console and logger.
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void endImpersonationAndBack(ExtentTest logger) {
		try {
			endImpersonation(logger);
			navigateTo(logger, "Fulfiller view", "navigate", "FulfillerURL");
			util.loggerMessage(logger, "pass", "Impersonation finished");
		} catch (Exception e) {
			util.catchError(logger, driver, e, "end impersonation");
		}
	}

	/**
	 * This method verifies that email notification NOT exist
	 * <p>
	 * 
	 * Navigate to 'Email' Notification sub module, search the record notification
	 * received as parameter on Notification Table.
	 * Search by ticket Number in preview notification, and validate that the
	 * notification
	 * NOT exist.
	 * <p>
	 * 
	 * @param logger
	 * @param recordName   {@code String} Name of the record to be searched on
	 *                     notification Table
	 * @param ticketNumber {@code String} ticket number
	 * 
	 * @author Molina Emiliano
	 * 
	 */
	public void verifyEmailNotificationNotExist(ExtentTest logger, String recipient, String ticketN) {
		try {

			// TODO go to emails submodule
			// navigate to emails submodule
			// searchOnFilterNavigator(logger, "Emails");
			// clickOn(logger, "Emails Notifications Sub Module", emailsSubModule);
			// Create this locator
			util.pause(logger, "2");
			frameHelper.switchToFrame(logger, driver, nav.iframe_gsft);
			System.out.println("Switching iframe...");
			util.pause(logger, "1");

			// search by recipient in Emails Table
			String searchField = "//label[.='Search']/following-sibling::input[@placeholder='Search']",
					filter = "//select[@aria-label='Search a specific field of the Emails list']",
					subject = "(//input[@aria-label='Search column: subject'])[1]",
					firstOccurrence = "(//a[@class='linked formlink'])[1]",
					subject_inputData = getSpecificValue("DataSet1", "Subject2");
			subject_inputData = subject_inputData.replace("<ticketNumber>", ticketN);

			driver.findElement(By.xpath(filter)).sendKeys("Recipients" + Keys.ENTER);
			util.pause(logger, "2");
			driver.findElement(By.xpath(searchField)).sendKeys(recipient + Keys.ENTER);
			util.pause(logger, "2");
			driver.findElement(By.xpath(subject)).sendKeys(subject_inputData + Keys.ENTER);
			util.pause(logger, "2");

			if (driver.findElements(By.xpath(firstOccurrence)).size() == 0) {

				util.loggerMessage(logger, "pass", "Notification not exist -> CORRECT");

			} else {

				util.loggerMessage(logger, "fail", "notification exist -> NOT CORRECT");

			}
		} catch (Exception e) {
			util.catchError(logger, driver, e, "verify that notification not exist");
		}
	}

	// This method verify if column exist in fulfiller table view and if not exist,
	// added it
	public void verifyColumnExist(ExtentTest logger, String fieldName) {
		By column = By.xpath("(//th[@glide_label='" + fieldName + "'])[1]");
		try {
			if (driver.findElements(column).size() != 0) {
				System.out.println(fieldName + " column exist!");
				logger.log(Status.PASS, fieldName + " column exist!");
			} else {
				addColumn(logger, fieldName);
				System.out.println(fieldName + " column added");
				logger.log(Status.PASS, fieldName + " column added");
			}
		} catch (Exception e) {
			System.out.println(fieldName + " column could not be verify");
			logger.log(Status.FAIL, fieldName + " column could not be verify");
			Assert.fail();
		}
	}

	/**
	 * This method get data from Input Data file and complete field with them.
	 * 
	 * @see #CompleteFieldFrontend(ExtentTest, String, String)
	 * @see #completeFieldBackend(ExtentTest, String, By, String)
	 * 
	 * @param logger
	 * @param fieldsName {@code String} Column name where the fields name are listed
	 *                   on Input Data file (xlsx)
	 * @param fieldsData {@code String} Column name where the fields Data are listed
	 *                   on Input Data file (xlsx)
	 * @param locator    {@code By} Reference of the back-end field.
	 * 
	 * @author b.marmol.maldonado
	 */
	public void getFieldsAndCompleteForm(ExtentTest logger, String fieldsName, String fieldsData, String frontOrBack) {

		int i = 1;
		while (true) {

			String field = getSpecificValue("DataSet" + i, fieldsName);
			String data = getSpecificValue("DataSet" + i, fieldsData);

			if (field.equals(null) || field.equals("FAILED TO READ") || field.equals("null")) {
				System.out.println("Data entry has been stopped, Number of fields loaded: " + (i - 1));
				logger.log(Status.INFO, "Data entry has been stopped, Number of fields loaded: " + (i - 1));
				break;
			}
			if (data.equals(null) || data.equals("FAILED TO READ") || data.equals("null")) {
				System.out
						.println("Data entry has been stopped (cause data colum), Number of fields loaded: " + (i - 1));
				logger.log(Status.INFO,
						"Data entry has been stopped (cause data colum), Number of fields loaded: " + (i - 1));
				break;
			}

			System.out.println("Field name: '" + field + "'");
			logger.log(Status.INFO, "Field name: '" + field + "'");
			System.out.println("Field Data: '" + data + "'");
			logger.log(Status.INFO, "Field Data: " + data + "'");
			System.out.println("---------------------------------------");

			if (frontOrBack.equalsIgnoreCase("back")) {

				// completeFieldBackend(logger, field, locator, data);

			} else if (frontOrBack.equalsIgnoreCase("front")) {

				util.completeFieldFrontend(logger, driver, field, data);
			}
			i++;
		}
	}

	/**
	 * TODO improve this method Emi
	 * This method set Tabbed form View on Related List
	 * 
	 * @param logger
	 * 
	 * @author Molina Emiliano
	 */
	// public void activeRelatedListTabbedFormView(ExtentTest logger, WebDriver
	// driver) {
	// try {
	// By settingsBTN = By.xpath("//button[@id='nav-settings-button']");
	// By form = By.xpath("//span[@data-tab-name='form']");
	// By switchTabbedForm = By.xpath("//div[@class='input-switch']/parent::div");
	// By label = By.xpath("//div[@class='input-switch']/label");
	// By closeModalBTB = By.xpath("//div[@class='modal-header-left']/button");

	// if (driver.findElements(settingsBTN).size() == 0) {
	// driver.switchTo().defaultContent();
	// System.out.println("Switching iframe...");
	// pause(logger, "1");
	// }

	// if (driver.findElements(settingsBTN).size() != 0) {
	// driver.findElement(settingsBTN).click();
	// pause(logger, "2");
	// driver.switchTo().activeElement();
	// System.out.println("Switching modal window");
	// pause(logger, "1");

	// if (driver.findElements(form).size() != 0) {
	// driver.findElement(form).click();
	// pause(logger, "2");

	// if (driver.findElements(switchTabbedForm).size() != 0) {

	// String switch_ = driver.findElement(label).getCssValue("background-color");
	// System.out.println("Label Color -> " + switch_);

	// if (switch_.equals("rgba(46, 46, 46, 1)")) {

	// driver.findElement(label).click();
	// pause(logger, "2");
	// loggerMessage(logger, "info", "Tabbed form Activated");

	// } else if (switch_.equals("rgba(22, 92, 83, 1)")) {
	// loggerMessage(logger, "info", "Tabbed form is already Active");

	// } else {
	// loggerMessage(logger, "fail", "Tabbed form could not be Activated");
	// }
	// } else {
	// loggerMessage(logger, "fail", "Tabbed form could not be clicked");
	// }

	// } else {
	// loggerMessage(logger, "fail", "Form option could not be found");
	// }
	// } else {
	// loggerMessage(logger, "fail", "Settings button could not be found");
	// }

	// if (driver.findElements(closeModalBTB).size() != 0) {
	// driver.findElement(closeModalBTB).click();
	// pause(logger, "2");
	// frameHelper.switchToFrame(logger, driver, nav.iframe_gsft);
	// System.out.println("Switching iframe...");
	// pause(logger, "1");
	// } else {
	// loggerMessage(logger, "fail", "Close modal button could not be found");
	// }

	// } catch (Exception e) {
	// catchError(logger, e, "Active Tabbed from view");
	// }
	// }

	/**
	 * 
	 * 
	 * MAP (FIELD_NAME, MAP(VALUE,FIELD_TYPE))
	 * FOR EACH -> {
	 * 
	 * Switch
	 * case "dropdown":
	 * completeField(logger, portal.dropDown(driver, fieldName) , value)
	 * case "inputField":
	 * completeField(logger, portal.inputField(driver, fieldName) , value)
	 * case "textArea":
	 * completeField(logger, portal.textArea(driver, fieldName) , value)
	 * 
	 * }
	 */
}
