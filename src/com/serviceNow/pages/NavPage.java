package com.serviceNow.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class NavPage extends BasePage {
	public String tabName = "";
	public String name;
	ArrayList<String> nameList = new ArrayList<>();

	public NavPage(WebDriver driver) {
		super(driver);
	}

	public WebElement element = null;

	@FindBy(xpath = "//iframe[@title='Input Summary']")
	public WebElement iframe_input;
	@FindBy(xpath = "//div[@id='select2-drop']//input")
	public WebElement txt_DropList;
	@FindBy(xpath = "//iframe[contains(@id,'bulk')]")
	public WebElement iframe_bulk;
	@FindBy(id = "gsft_main")
	public WebElement iframe_gsft;
	@FindBy(id = "email_preview_iframe")
	public WebElement iframe_emailPreview;
	@FindBy(xpath = "//iframe[@class='card']")
	public WebElement iframe_card;
	@FindBy(xpath = "//*[@id='activity-stream-textarea']")
	public WebElement txt_workNotes;
	@FindBy(xpath = "//i[contains(@data-original-title,'List')][not(@aria-hidden='true')]")
	public WebElement lnk_personalizedIcon;
	@FindBy(xpath = "//button[contains(@id,'closemodal')]")
	public WebElement btn_closeModal;
	@FindBy(xpath = "//a[@role='textbox']")
	public WebElement lnk_imageName;
	@FindBy(xpath = "//div[@id='attachment_dialog_list']//a[contains(text(),'[rename]')]")
	public WebElement btn_rename;
	@FindBy(xpath = "//a[@class='glide_ref_item_link']")
	public WebElement lnk_lookUpFirstSearchResult;
	@FindBy(xpath = "//textarea[@id='activity-stream-comments-textarea']")
	public WebElement txt_comments;
	@FindBy(xpath = "//button[@id='close-messages-btn']")
	public WebElement btn_CloseErrorMessage;
	@FindBy(xpath = "//input[@id='attachFile']")
	public WebElement txt_attachFile;
	@FindBy(xpath = "//div[contains(@class,'outputmsg outputmsg_error')]//*[@class='outputmsg_text']")
	public WebElement lbl_errorMessage;
	@FindBy(id = "message.text_ifr")
	public WebElement iframe_bodyEmail;
	@FindBy(id = "simulated_html_iframe")
	public WebElement iframe_bodyEmailNotification;
	@FindBy(id = "sys_email_canned_message.body_ifr")
	public WebElement iframe_email_canned_message_body;

	// Emi
	@FindBy(xpath = "//span[@id='sys_user_group.u_co_managers_edit']/following-sibling::p")
	public WebElement coManagerField;

	// returns iframe webElement based on title
	// @SuppressWarnings("unchecked")
	public WebElement inputIframe(WebDriver driver, String title) {
		try {
			if (driver.findElements(By.xpath("//iframe[@title='" + title + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//iframe[@title='" + title + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns application webElement
	// main parent- string which is displayed as main parent in left navigation
	// view, Parent shouldn't have dropdown arrow
	// child- exact application item(table name)
	// @SuppressWarnings("unchecked")
	public WebElement applicationLink(WebDriver driver, String mainParent, String child) {
		try {
			if (driver
					.findElements(By.xpath("//*[normalize-space(text())='" + mainParent
							+ "']/parent::a/parent::li/ul/li//*[normalize-space(text())='" + child + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[normalize-space(text())='" + mainParent
						+ "']/parent::a/parent::li/ul/li//*[normalize-space(text())='" + child + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns application webElement
	// parent- string which is displayed as parent in left navigation view, parent
	// should have dropdown arrow
	// child- exact application item(table name)
	// @SuppressWarnings("unchecked")
	public WebElement applicationLinkWithParent(WebDriver driver, String parent, String child) {
		try {
			if (driver
					.findElements(By.xpath(
							".//*[@aria-label='Submodules for Module: " + parent + "']//*[text()='" + child + "']"))
					.size() != 0) {
				element = driver.findElement(By
						.xpath(".//*[@aria-label='Submodules for Module: " + parent + "']//*[text()='" + child + "']"));
			} else if (driver.findElements(By.xpath(".//*[contains(@aria-label,'Submodules for Module: " + parent
					+ "')]//*[contains(text(),'" + child + "')]")).size() != 0) {
				element = driver.findElement(By.xpath(".//*[contains(@aria-label,'Submodules for Module: " + parent
						+ "')]//*[contains(text(),'" + child + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns textfield/textarea webElement
	// @SuppressWarnings("unchecked")
	public WebElement text(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//input[@role='combobox'][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//input[@role='combobox'][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div/div/input[@aria-required='true'][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div/div/input[@aria-required='true'][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div/div/input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div/div/input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//input[@type='text'][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//input[@type='text'][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following-sibling::div//input[@type='text'][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following-sibling::div//input[@type='text'][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')][not(@tabindex='-1')]"));
			} else if (driver
					.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
							+ "']/parent::label/parent::div/following-sibling::div/span//input[not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div/span//input[not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[text()='" + fieldName
					+ "']/parent::label/parent::div/following::div/textarea[@placeholder='" + fieldName
					+ "'][not(@tabindex='-1')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[text()='" + fieldName
						+ "']/parent::label/parent::div/following::div/textarea[@placeholder='" + fieldName
						+ "'][not(@tabindex='-1')]"));
			} else if (driver
					.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
							+ "']/parent::label/parent::div/following-sibling::div/textarea[not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div/textarea[not(@tabindex='-1')]"));
			} else if (driver
					.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
							+ "')]/parent::label/parent::div/following-sibling::div/textarea[not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following-sibling::div/textarea[not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/div//textarea[not(@tabindex='-1')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/div//textarea[not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div/input[not(@type='hidden')][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div/input[not(@type='hidden')][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//input[not(@type='HIDDEN')][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//input[not(@type='HIDDEN')][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following-sibling::div//input[not(@type='HIDDEN')][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following-sibling::div//input[not(@type='HIDDEN')][not(@tabindex='-1')]"));
			} else if (driver
					.findElements(By
							.xpath(tabName + "//input[@data-original-title='" + fieldName + "'][not(@tabindex='-1')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//input[@data-original-title='" + fieldName + "'][not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::div//input[not(@tabindex='-1')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::div//input[not(@tabindex='-1')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/following-sibling::textarea[not(@tabindex='-1')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/following-sibling::textarea[not(@tabindex='-1')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns textfield webElement
	// @SuppressWarnings("unchecked")
	public WebElement navPageTextFieldOnPortal(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "(//*[normalize-space(text())='" + fieldName
					+ "'])[2]/parent::label/parent::div/following-sibling::div//input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "(//*[normalize-space(text())='" + fieldName
						+ "'])[2]/parent::label/parent::div/following-sibling::div//input[not(translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'hidden')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns field validation message webElement
	// @SuppressWarnings("unchecked")
	public WebElement fieldValidationMsg(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//div[contains(@class,'fieldmsg notification')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//div[contains(@class,'fieldmsg notification')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following-sibling::div//div[contains(@class,'fieldmsg notification')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following-sibling::div//div[contains(@class,'fieldmsg notification')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns checkbox webElement
	// @SuppressWarnings("unchecked")
	public WebElement checkbox(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//label[contains(@class,'checkbox-label')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//label[contains(@class,'checkbox-label')]"));
			} else if (driver
					.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
							+ "']/parent::label/parent::div/following-sibling::div//input[not(@type='hidden')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//input[not(@type='hidden')]"));
			} else if (driver.findElements(By.xpath(".//*[contains(text(),'" + fieldName
					+ "')]/parent::span/parent::div/parent::div//input[@data-type='checkbox']")).size() != 0) {
				element = driver.findElement(By.xpath(".//*[contains(text(),'" + fieldName
						+ "')]/parent::span/parent::div/parent::div//input[@data-type='checkbox']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns dropdown webElement
	// tabName
	// @SuppressWarnings("unchecked")
	public WebElement dropdown(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//select")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//select"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following-sibling::div//select")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following-sibling::div//select"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div/span/select")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div/span/select"));
			} else if (driver.findElements(By.xpath(tabName + "//label[normalize-space(text())='" + fieldName
					+ "']/preceding-sibling::select[contains(@class,'form-control')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//label[normalize-space(text())='" + fieldName
						+ "']/preceding-sibling::select[contains(@class,'form-control')]"));
			} else if (driver.findElements(By.xpath(tabName + "//label[contains(text(),'" + fieldName
					+ "')]/preceding-sibling::select[contains(@class,'form-control')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//label[contains(text(),'" + fieldName
						+ "')]/preceding-sibling::select[contains(@class,'form-control')]"));
			} else if (driver.findElements(By.xpath(
					tabName + "//*[normalize-space(text())='" + fieldName + "']/parent::div/parent::div//select"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						tabName + "//*[normalize-space(text())='" + fieldName + "']/parent::div/parent::div//select"));
			} else if (driver
					.findElements(By.xpath(
							tabName + "//*[contains(text(),'" + fieldName + "')]/parent::div/parent::div//select"))
					.size() != 0) {
				element = driver.findElement(By
						.xpath(tabName + "//*[contains(text(),'" + fieldName + "')]/parent::div/parent::div//select"));
			} else if (driver.findElements(By.xpath(tabName + "//select[@aria-label='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//select[@aria-label='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/ancestor::label/parent::div/following-sibling::div//select")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/ancestor::label/parent::div/following-sibling::div//select"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
					+ "')]/ancestor::label/parent::div/following-sibling::div//select")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/ancestor::label/parent::div/following-sibling::div//select"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns filter dropdown based on section name. Eg. Available, Selected
	// @SuppressWarnings("unchecked")
	public WebElement filterDropdown(WebDriver driver, String fieldName, String sectionName) {
		try {
			if (sectionName.isEmpty()) {
				if (driver.findElements(By.xpath("//select[@aria-label='" + fieldName + "']")).size() != 0) {
					element = driver.findElement(By.xpath("//select[@aria-label='" + fieldName + "']"));
				}
			} else if (driver.findElements(By.xpath("//*[normalize-space(text())='" + sectionName
					+ "']/parent::label/parent::div/following-sibling::div//select[@aria-label='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + sectionName
						+ "']/parent::label/parent::div/following-sibling::div//select[@aria-label='" + fieldName
						+ "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns button webElement
	// @SuppressWarnings("unchecked")
	public WebElement button(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName
					+ "//div[@class='inline-cart ng-scope']//button[normalize-space(text())='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName
						+ "//div[@class='inline-cart ng-scope']//button[normalize-space(text())='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath(tabName + "//button[@aria-label='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//button[@aria-label='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath("//button[normalize-space(text())='" + fieldName + "']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath(tabName + "//button[normalize-space(text())='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath(tabName + "//button[contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//button[contains(text(),'" + fieldName + "')]"));
			} else if (driver
					.findElements(
							By.xpath(tabName + "//button[normalize-space(text())='" + fieldName + "'][@role='button']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//button[normalize-space(text())='" + fieldName + "'][@role='button']"));
			} else if (driver
					.findElements(By.xpath(tabName + "//button[contains(text(),'" + fieldName + "')][@role='button']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//button[contains(text(),'" + fieldName + "')][@role='button']"));
			} else if (driver.findElements(By.xpath(tabName + "//*[@value='" + fieldName + "'][@type='button']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[@value='" + fieldName + "'][@type='button']"));
			} else if (driver.findElements(By.xpath(tabName + "//*[@value='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[@value='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns button webElement on popup
	// @SuppressWarnings("unchecked")
	public WebElement popupButton(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("//div[contains(@class,'modal')]//button[text()='" + fieldName + "']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//div[contains(@class,'modal')]//button[text()='" + fieldName + "']"));
			} else if (driver
					.findElements(
							By.xpath("//div[contains(@class,'modal')]//button[contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//div[contains(@class,'modal')]//button[contains(text(),'" + fieldName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns link webElement
	// @SuppressWarnings("unchecked")
	public WebElement link(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//a[text()='" + fieldName + "'][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath(tabName + "//a[text()='" + fieldName + "'][not(@aria-hidden='true')]"));
			} else if (driver
					.findElements(
							By.xpath(tabName + "//a[contains(text(),'" + fieldName + "')][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//a[contains(text(),'" + fieldName + "')][not(@aria-hidden='true')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns label webElement
	// @SuppressWarnings("unchecked")
	public WebElement label(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//div[normalize-space(text())='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//div[normalize-space(text())='" + fieldName + "']"));
			} else if (driver
					.findElements(By.xpath(
							tabName + "//*[normalize-space(text())='" + fieldName + "'][not(aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath(tabName + "//span[contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//span[contains(text(),'" + fieldName + "')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns condition filters webElement Eg. All, Greater than 400
	// @SuppressWarnings("unchecked")
	public WebElement conditionLink(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//a/b[text()='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//a/b[text()='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath(tabName + "//a/b[contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//a/b[contains(text(),'" + fieldName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// @SuppressWarnings("unchecked")
	public WebElement blockText(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("//body[@id='tinymce'][@aria-label='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//body[@id='tinymce'][@aria-label='" + fieldName + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns icon webElement
	// @SuppressWarnings("unchecked")
	public WebElement editIcon(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//a[contains(@data-original-title,'"
					+ fieldName + "')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//a[contains(@data-original-title,'"
						+ fieldName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns search icon webElement
	// @SuppressWarnings("unchecked")
	public WebElement lookupImage(WebDriver driver, String fieldName, String toolTipName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//a[contains(@data-original-title,'"
					+ toolTipName + "')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//a[contains(@data-original-title,'"
						+ toolTipName + "')]"));
			} else if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div//*[contains(@data-original-title,'"
					+ toolTipName + "')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//*[contains(@data-original-title,'"
						+ toolTipName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns lock button webElement
	// @SuppressWarnings("unchecked")
	public WebElement lockButton(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
					+ "']/parent::label/parent::div/following-sibling::div/button")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[normalize-space(text())='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div/button"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following-sibling::div/button")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following-sibling::div/button"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns webElement of column's search textfield in list view
	// @SuppressWarnings("unchecked")
	public WebElement searchColumnInTable(ExtentTest logger, WebDriver driver, String fieldName, String tabName)
			throws InterruptedException {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		addColumn(logger, driver, fieldName, tabName);
		fieldName = fieldName.toLowerCase();
		name = fieldName.replaceAll("\\s", "_");
		if (driver
				.findElements(By.xpath(
						tabName + "//button[@data-original-title='Show column search row'][@aria-expanded='false']"))
				.size() != 0) {
			element = driver.findElement(By.xpath(
					tabName + "//button[@data-original-title='Show column search row'][@aria-expanded='false']"));
			element.click();
		} else {
			element = null;
		}
		try {
			System.out.println(tabName + "//td[@name='" + name + "']//input");
			if (driver.findElement(By.xpath(tabName + "//td[@name='" + name + "']//input")).isDisplayed()) {
				element = driver.findElement(By.xpath(tabName + "//td[@name='" + name + "']//input"));
				element.clear();
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// add column
	public void addColumn(ExtentTest logger, WebDriver driver, String columnName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		util.pause(logger, "5");
		WebElement personalizedIcon = driver.findElement(
				By.xpath(tabName + "//i[contains(@data-original-title,'List')][not(@aria-hidden='true')]"));
		wait.elementIsClickable(logger, driver, personalizedIcon);
		util.clickOn(logger, driver, "personalizedIcon", personalizedIcon);
		util.pause(logger, "5");
		// util.waitTillElementIsVisible(logger, driver, dropdown(driver,
		// "Selected",""));
		Select rightDropdown = new Select(dropdown(driver, "Selected", ""));
		List<WebElement> rightList = rightDropdown.getOptions();
		int flag = 0;
		for (int k = 0; k < rightList.size(); k++) {
			if (rightList.get(k).getText().equals(columnName)) {
				rightDropdown.selectByVisibleText(columnName);
				logger.log(Status.INFO, "Column is  already present in goto dropdown: " + columnName);
				System.out.println("Column is  already present in goto dropdown: " + columnName);
				break;
			} else {
				flag = flag + 1;
			}
		}
		if (flag == rightList.size()) {
			util.clickOn(logger, driver, "Column_" + columnName,
					driver.findElement(By.xpath(tabName + "//option[text()='" + columnName + "']")));
			util.clickOn(logger, driver, "add", tooltip(driver, "Add", "", ""));
			logger.log(Status.INFO, "Added all items from available to selected list");
			System.out.println("Column Added: " + columnName);
		}
		util.clickOn(logger, driver, "Move up", tooltip(driver, "Move up", "", ""));
		util.clickOn(logger, driver, "Move up", tooltip(driver, "Move up", "", ""));
		util.clickOn(logger, driver, "Move up", tooltip(driver, "Move up", "", ""));
		util.clickOn(logger, driver, "Move up", tooltip(driver, "Move up", "", ""));
		util.clickOn(logger, driver, "Move up", tooltip(driver, "Move up", "", ""));
		util.clickOn(logger, driver, "Move up", tooltip(driver, "Move up", "", ""));
		util.clickOn(logger, driver, "Move up", button(driver, "OK", ""));
		util.pause(logger, "5");
	}

	/*
	 * public WebElement searchInSection(WebDriver driver, String
	 * sectionName, String searchfield) throws InterruptedException { //clicking on
	 * magnifier icon if(driver.findElements(By.
	 * xpath("//h2[normalize-space(text())='Tasks']/parent::a/parent::div/parent::div/parent::div/following-sibling::div[@class='custom-form-group ']//button[@class='list_header_search_toggle icon-search btn btn-icon table-btn-lg'][@aria-expanded='false']"
	 * )).size()!=0) { driver.findElement(By.
	 * xpath("//h2[normalize-space(text())='Tasks']/parent::a/parent::div/parent::div/parent::div/following-sibling::div[@class='custom-form-group ']//button[@class='list_header_search_toggle icon-search btn btn-icon table-btn-lg'][@aria-expanded='false']"
	 * )).click(); Thread.sleep(5000); } searchfield = searchfield.toLowerCase();
	 * 
	 * if(driver.findElements(By.xpath(tabName+"//h2[normalize-space(text())='"+
	 * sectionName+"']/parent::a/parent::div/parent::div/parent::div/following-sibling::div[@class='custom-form-group ']//input[@aria-label='Search column: "
	 * +searchfield+"']")).size()!=0) {
	 * driver.findElements(By.xpath(tabName+"//h2[normalize-space(text())='"+
	 * sectionName+"']/parent::a/parent::div/parent::div/parent::div/following-sibling::div[@class='custom-form-group ']//input[@aria-label='Search column: "
	 * +searchfield+"']")); } return element; }
	 */

	// returns header webElement
	// @SuppressWarnings("unchecked")
	public WebElement formDisplayValue(WebDriver driver) {
		try {
			if (driver.findElements(By.xpath("//span[@class='form_display_value']")).size() != 0) {
				element = driver.findElement(By.xpath("//span[@class='form_display_value']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns table header webElement
	// @SuppressWarnings("unchecked")
	public WebElement tableHeader(WebDriver driver) {
		try {
			if (driver.findElements(By.xpath("//*[contains(@class,'navbar-title')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@class,'navbar-title')]"));
			} else if (driver.findElements(By.xpath("//*[contains(@class,'navbar-title')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@class,'navbar-title')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns webElement which is used for right clicking on column name. Eg.
	// Sorting
	// @SuppressWarnings("unchecked")
	public WebElement tableHeaderActions(WebDriver driver, String columnName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[@aria-label='" + columnName + " column options']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath(tabName + "//*[@aria-label='" + columnName + " column options']"));
			} else if (driver
					.findElements(By.xpath(tabName + "//*[contains(@aria-label,'" + columnName + " column options')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//*[contains(@aria-label,'" + columnName + " column options')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns webElement based on background text
	// @SuppressWarnings("unchecked")
	public WebElement backgroundText(WebDriver driver, String value, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + "//*[@placeholder='" + value + "']")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[@placeholder='" + value + "']"));
			} else if (driver.findElements(By.xpath(tabName + "//*[contains(@placeholder,'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(@placeholder,'" + value + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns webElement based on Activity Log First Update
	// @SuppressWarnings("unchecked")
	public WebElement activityLogText(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + ".//span[contains(text(),'" + fieldName
					+ "')]/following-sibling::span[contains(@class,'table-cell')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + ".//span[contains(text(),'" + fieldName
						+ "')]/following-sibling::span[contains(@class,'table-cell')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns webElement based on Entry Number In Activity Log
	// @SuppressWarnings("unchecked")
	public WebElement activityLogTextBasedOnMultipleEntries(WebDriver driver, String fieldName, String tabName,
			String entryNumber) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver
					.findElements(By.xpath("(" + tabName + ".//span[contains(text(),'" + fieldName
							+ "')]/following-sibling::span[contains(@class,'table-cell')])[" + entryNumber + "]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("(" + tabName + ".//span[contains(text(),'" + fieldName
						+ "')]/following-sibling::span[contains(@class,'table-cell')])[" + entryNumber + "]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns webElement based on tooltip value
	public WebElement tooltip(WebDriver driver, String value, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}

		if (driver.findElements(By.xpath(tabName + "//a[@title='" + value + "'][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver.findElement(By.xpath(tabName + "//a[@title='" + value + "'][not(@aria-hidden='true')]"));
		} else if (driver
				.findElements(By.xpath(tabName + "//a[contains(@title,'" + value + "')][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver
					.findElement(By.xpath(tabName + "//a[contains(@title,'" + value + "')][not(@aria-hidden='true')]"));
		} else if (driver
				.findElements(By.xpath(tabName + "//*[@data-original-title='" + value + "'][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver.findElement(
					By.xpath(tabName + "//*[@data-original-title='" + value + "'][not(@aria-hidden='true')]"));
		} else if (driver
				.findElements(By.xpath(
						tabName + "//*[contains(@data-original-title,'" + value + "')][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver.findElement(By
					.xpath(tabName + "//*[contains(@data-original-title,'" + value + "')][not(@aria-hidden='true')]"));
		} else if (driver.findElements(By.xpath(tabName + "//*[@title='" + value + "'][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver.findElement(By.xpath(tabName + "//*[@title='" + value + "'][not(@aria-hidden='true')]"));
		} else if (driver
				.findElements(By.xpath(tabName + "//*[contains(@title,'" + value + "')][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver
					.findElement(By.xpath(tabName + "//*[contains(@title,'" + value + "')][not(@aria-hidden='true')]"));
		} else if (driver.findElements(By.xpath(tabName + "//*[@aria-label='" + value + "'][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver
					.findElement(By.xpath(tabName + "//*[@aria-label='" + value + "'][not(@aria-hidden='true')]"));
		} else if (driver
				.findElements(
						By.xpath(tabName + "//*[contains(@aria-label,'" + value + "')][not(@aria-hidden='true')]"))
				.size() != 0) {
			element = driver.findElement(
					By.xpath(tabName + "//*[contains(@aria-label,'" + value + "')][not(@aria-hidden='true')]"));
		} else {
			element = null;
		}

		System.out.println(element);
		return element;

	}

	// select from dropdown list
	// @SuppressWarnings("unchecked")
	public void selectFromDropList(ExtentTest logger, WebDriver driver, String fieldName, String value,
			String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(
					tabName + "//span[text()='" + fieldName + "']/parent::label/parent::div/following-sibling::div//a"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//span[text()='" + fieldName
						+ "']/parent::label/parent::div/following-sibling::div//a"));
			} else if (driver
					.findElements(
							By.xpath(tabName + "//*[contains(text(),'" + fieldName + "')]/parent::span/parent::div//a"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//*[contains(text(),'" + fieldName + "')]/parent::span/parent::div//a"));
			} else if (driver
					.findElements(By.xpath(tabName + "//*[contains(text(),'" + fieldName
							+ "')]/parent::div/parent::div//a[contains(@class,'select2-choice select2-default')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//*[contains(text(),'" + fieldName
						+ "')]/parent::div/parent::div//a[contains(@class,'select2-choice select2-default')]"));
			} else {
				element = null;
			}
			System.out.println(element);
		} catch (Exception e) {
		}
		element.click();
		// util.waitTillElementIsVisible(logger, driver, txt_DropList);
		util.setText(logger, txt_DropList, value);
		util.pause(logger, "10");
		try {
			if (value.equals("No")) {
				util.clickOn(logger, driver, "", driver.findElement(
						By.xpath("//li[not(contains(@class,'highlighted'))]/div/span[text()='" + value + "']")));
			} else if (driver
					.findElements(
							By.xpath(tabName + "//li//div[not(text())]/span[text()='" + value + "']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//li//div[not(text())]/span[text()='" + value + "']"));
			} else if (driver
					.findElements(
							By.xpath(tabName + "//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value
									+ "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value
								+ "')]"));
			} else if (driver
					.findElements(By.xpath(
							tabName + "//li[contains(@class,'highlighted')]/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//li[contains(@class,'highlighted')]/div[contains(text(),'" + value
								+ "')]"));
			} else if (driver
					.findElements(By.xpath(
							tabName + "//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value
								+ "')]"));
			} else {
				element = null;
			}
			System.out.println(element);
			element.click();
		} catch (Exception e) {
		}
	}

	// Validates a file used for bulk upload of records
	public String bulkUploadValidation(ExtentTest logger, WebDriver driver) {
		try {
			frameHelper.switchToFrame(logger, driver, iframe_bulk);
			if (driver.findElements(By.xpath(
					"//button[@class='list_header_search_toggle icon-search btn btn-icon table-btn-lg'][@aria-expanded='false']"))
					.size() != 0) {
				driver.findElement(By.xpath(
						"//button[@class='list_header_search_toggle icon-search btn btn-icon table-btn-lg'][@aria-expanded='false']"))
						.click();
				util.pause(logger, "5");
			}
			if (driver.findElements(By.xpath("//input[@type='search'][@aria-label='Search column: error comments']"))
					.size() != 0) {
				util.setTextWithEnter(logger,
						driver.findElement(
								By.xpath("//input[@type='search'][@aria-label='Search column: error comments']")),
						"!=null");
				util.pause(logger, "10");
			}
			frameHelper.switchToDefaultFrame(logger, driver);
			logger.log(Status.PASS, "bulk upload verification is successful");
			if (driver.findElements(By.xpath("//td[normalize-space(text())='No records to display']")).size() != 0) {
				return "no error";
			} else {
				return "error in attachment";
			}
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to verify bulk upload file");
			return "error in attachment";
		}
	}

	// search in lookup table
	// @SuppressWarnings("unchecked")
	public WebElement searchInLookUp(ExtentTest logger, WebDriver driver, String fieldName)
			throws InterruptedException {
		name = fieldName.toLowerCase();
		try {
			if (driver
					.findElements(
							By.xpath("//button[@data-original-title='Show column search row'][@aria-expanded='false']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//button[@data-original-title='Show column search row'][@aria-expanded='false']"));
				element.click();
			}
			// System.out.println(driver.findElement(By.xpath("(//input[@aria-label='Search
			// column: "+fieldName+"'])[1]")));
			if (driver.findElements(By.xpath("//input[@aria-label='Search column: " + name + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//input[@aria-label='Search column: " + name + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns button webElement based on section names
	// sectionName-Eg. Task, Approvals
	// buttonName-Eg. New,Edit
	// @SuppressWarnings("unchecked")
	public WebElement taskButton(WebDriver driver, String sectionName, String buttonName) {
		try {
			if (driver.findElements(By.xpath("//*[normalize-space(text())='" + sectionName
					+ "']/parent::a/following-sibling::button[text()='" + buttonName + "']")).size() != 0) {
				driver.findElement(By.xpath("//*[normalize-space(text())='" + sectionName
						+ "']/parent::a/following-sibling::button[text()='" + buttonName + "']"));
			} else if (driver.findElements(By.xpath("//*[normalize-space(text())='" + sectionName
					+ "']//parent::div//parent::div/following-sibling::div//button[text()='" + buttonName + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[normalize-space(text())='" + sectionName
						+ "']//parent::div//parent::div/following-sibling::div//button[text()='" + buttonName + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// @SuppressWarnings("unchecked")
	public WebElement tooltip(WebDriver driver, String value, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		if (fieldName.isEmpty()) {
			name = "";
		} else {
			name = "//*[contains(text(),'" + fieldName + "')]/parent::label/parent::div/following-sibling::div";
		}
		try {
			if (driver
					.findElements(
							By.xpath(tabName + "" + name + "//a[@title='" + value + "'][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "" + name + "//a[@title='" + value + "'][not(@aria-hidden='true')]"));
			} else if (driver
					.findElements(By.xpath(
							tabName + "" + name + "//a[contains(@title,'" + value + "')][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						tabName + "" + name + "//a[contains(@title,'" + value + "')][not(@aria-hidden='true')]"));
			} else if (driver.findElements(By
					.xpath(tabName + "" + name + "//*[@data-original-title='" + value + "'][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						tabName + "" + name + "//*[@data-original-title='" + value + "'][not(@aria-hidden='true')]"));
			} else if (driver.findElements(By.xpath(tabName + "" + name + "//*[contains(@data-original-title,'" + value
					+ "')][not(@aria-hidden='true')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "" + name + "//*[contains(@data-original-title,'"
						+ value + "')][not(@aria-hidden='true')]"));
			} else if (driver
					.findElements(
							By.xpath(tabName + "" + name + "//*[@title='" + value + "'][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "" + name + "//*[@title='" + value + "'][not(@aria-hidden='true')]"));
			} else if (driver
					.findElements(By.xpath(
							tabName + "" + name + "//*[contains(@title,'" + value + "')][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						tabName + "" + name + "//*[contains(@title,'" + value + "')][not(@aria-hidden='true')]"));
			} else if (driver
					.findElements(By
							.xpath(tabName + "" + name + "//*[@aria-label='" + value + "'][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "" + name + "//*[@aria-label='" + value + "'][not(@aria-hidden='true')]"));
			} else if (driver.findElements(By.xpath(
					tabName + "" + name + "//*[contains(@aria-label,'" + value + "')][not(@aria-hidden='true')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						tabName + "" + name + "//*[contains(@aria-label,'" + value + "')][not(@aria-hidden='true')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// return table cell webElement, table number is 1,2,3..if there is only one
	// table, then it should be 1
	// provide row name and column name
	// @SuppressWarnings("unchecked")
	public WebElement modifyTableCellText(ExtentTest logger, int tableNumber, String rowName, String columnName) {
		util.pause(logger, "5");
		int columnIndex = util.returnColumnIndex(logger, "(//tbody)[" + tableNumber + "]//tr[1]//td", columnName,
				driver);
		System.out.print("column index" + columnIndex);
		try {
			if (driver.findElements(By.xpath("(//tbody)[" + tableNumber + "]//*[contains(text(),'" + rowName
					+ "')]/parent::tr//td[" + columnIndex + "]")).size() != 0) {
				element = driver.findElement(By.xpath("(//tbody)[" + tableNumber + "]//*[contains(text(),'" + rowName
						+ "')]/parent::tr//td[" + columnIndex + "]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// return table cell webElement
	// provide column name
	// @SuppressWarnings("unchecked")
	public WebElement tableCellText(ExtentTest logger, String columnName, String tabName) {
		util.pause(logger, "5");
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		String xpath = tabName + "//table[not(contains(@id,'clone'))]//th";
		System.out.println(xpath);
		int columnIndex = util.returnColumnIndex(logger, tabName + "//table[not(contains(@id,'clone'))]//th",
				columnName, driver);
		System.out.print("column index" + columnIndex);
		try {
			if (driver.findElements(
					By.xpath(tabName + "//table[not(contains(@id,'clone'))]//tbody[@class='list2_body']//tr[1]//td["
							+ columnIndex + "]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//table[not(contains(@id,'clone'))]//tbody[@class='list2_body']//tr[1]//td["
								+ columnIndex + "]"));
			} else if (driver.findElements(By.xpath(
					tabName + "//table[not(contains(@id,'clone'))]//tbody//tr[1]//td[" + columnIndex + "]//input"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						tabName + "//table[not(contains(@id,'clone'))]//tbody//tr[1]//td[" + columnIndex + "]//input"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// return table cell webElement based on row Number
	// provide column name and row number
	// @SuppressWarnings("unchecked")
	public WebElement tableCellTextBasedOnRowNumber(ExtentTest logger, int rowNumber, String columnName,
			String tabName) {
		util.pause(logger, "5");
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[contains(@tab_caption,'" + tabName + "')]";
		}
		String xpath = tabName + "//table[not(contains(@id,'clone'))]//th";
		System.out.println(xpath);
		int columnIndex = util.returnColumnIndex(logger, tabName + "//table[not(contains(@id,'clone'))]//th",
				columnName, driver);
		System.out.print("column index" + columnIndex);
		try {
			if (driver.findElements(
					By.xpath(tabName + "//table[not(contains(@id,'clone'))]//tbody[@class='list2_body']//tr["
							+ rowNumber + "]//td[" + columnIndex + "]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(tabName + "//table[not(contains(@id,'clone'))]//tbody[@class='list2_body']//tr["
								+ rowNumber + "]//td[" + columnIndex + "]"));
			} else if (driver.findElements(By.xpath(tabName + "//table[not(contains(@id,'clone'))]//tbody//tr["
					+ rowNumber + "]//td[" + columnIndex + "]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + "//table[not(contains(@id,'clone'))]//tbody//tr["
						+ rowNumber + "]//td[" + columnIndex + "]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// return arraylist of column values
	// provide column name to be searched
	public ArrayList<String> getColumnValuesInTable(ExtentTest logger, WebDriver driver, String columnName,
			String tabName) {
		nameList.clear();
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "'][not(@aria-hidden='true')]";
			System.out.println(tabName);
		}
		int i = util.returnColumnIndex(logger, tabName + "//table//thead//tr/th", columnName, driver);
		String str;
		int size = driver.findElements(By.xpath(tabName + "//table//thead//following-sibling::tbody//tr")).size();
		for (int j = 1; j <= size; j++) {
			str = driver
					.findElement(
							By.xpath(tabName + "//table//thead//following-sibling::tbody//tr[" + j + "]/td[" + i + "]"))
					.getAttribute("innerText").trim();
			System.out.println("Value is " + str);
			nameList.add(str);
		}
		return nameList;
	}

	// returns table cell value based on column name
	// provide column name to be searched
	public String getTableCellText(ExtentTest logger, WebDriver driver, String columnName, String tabName) {
		String text;
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		int i = util.returnColumnIndex(logger, tabName + "//table//thead//tr/th", columnName, driver);
		text = driver.findElement(By.xpath(tabName + "//table//tbody//tr[1]/td[" + i + "]")).getAttribute("innerText");
		return (text);
	}

	// returns column2 value based on matching criteria of column1 value
	// provide column 1 and column2 and string to be matched in column1
	public String getMatchingRowText(ExtentTest logger, WebDriver driver, String columnName, String secondColumnName,
			String stringToMatch, String tabName) {
		int rowIndex = 0;
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		int i = util.returnColumnIndex(logger, tabName + "//table//thead//tr/th", columnName, driver);
		List<WebElement> we = driver.findElements(By.xpath(tabName + "//table//tbody//tr//td[" + i + "]//a"));
		for (int j = 0; j < we.size(); j++) {
			if (we.get(j).getText().contains(stringToMatch)) {
				rowIndex = j + 1;
				System.out.println("Matching Row Index" + rowIndex);
				break;
			}
		}
		if (rowIndex == 0) {
			logger.log(Status.FAIL, "String is not found and index is " + rowIndex);
		}
		i = util.returnColumnIndex(logger, tabName + "//table//thead//tr/th", secondColumnName, driver);
		String text = driver.findElement(By.xpath(tabName + "//table//tbody//tr[" + rowIndex + "]//td[" + i + "]//a"))
				.getText();
		return (text);
	}

	// Returns mandatory fields
	// Pass the name of label
	// @SuppressWarnings("unchecked")
	public WebElement mandatoryField(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + ".//*[contains(text(),'" + fieldName
					+ "')]/preceding-sibling::span[contains(@mandatory,'true')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + ".//*[contains(text(),'" + fieldName
						+ "')]/preceding-sibling::span[contains(@mandatory,'true')]"));
				System.out.println("Element is mandatory");
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		return element;
	}

	// Returns Non mandatory fields
	// Pass the name of label
	// @SuppressWarnings("unchecked")
	public WebElement nonMandatoryField(WebDriver driver, String fieldName, String tabName) {
		if (tabName.isEmpty()) {
			tabName = "";
		} else {
			tabName = "//*[@tab_caption='" + tabName + "']";
		}
		try {
			if (driver.findElements(By.xpath(tabName + ".//*[contains(text(),'" + fieldName
					+ "')]/preceding-sibling::span[contains(@mandatory,'false')]")).size() != 0) {
				element = driver.findElement(By.xpath(tabName + ".//*[contains(text(),'" + fieldName
						+ "')]/preceding-sibling::span[contains(@mandatory,'false')]"));
				System.out.println("Element is non mandatory");
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		return element;
	}

	// Verify Column Editable / Non Editable from list view
	// columnName-name of column to be verified
	// type- WebElement Type
	public void verifyTableElements(ExtentTest logger, String columnName, String type, String tabName) {
		try {
			if (type.equalsIgnoreCase("link")) {
				WebElement element = tableCellText(logger, columnName, tabName);
				if (element.findElements(By.tagName("a")).size() != 0) {
					System.out.println("Element is Non editable");
					logger.log(Status.INFO, "Element is Non Editable");
				} else {
					System.out.println("Element is editable");
					logger.log(Status.INFO, "Element is Editable");
				}
			} else if (type.equalsIgnoreCase("calendar")) {
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", tableCellText(logger, columnName, tabName));
				util.pause(logger, "5");
				util.doubleClick(logger, driver, tableCellText(logger, columnName, tabName));
				util.pause(logger, "10");
				if (driver.findElements(By.xpath("//div[text()='Security prevents writing to this field']"))
						.size() != 0) {
					util.pause(logger, "5");
					System.out.println("Element is Non Editable");
					logger.log(Status.INFO, "Element is Non Editable");
					util.clickOn(logger, driver, "", tooltip(driver, "Cancel (ESC)", "", ""));
				} else {
					System.out.println("Element is editable");
					logger.log(Status.INFO, "Element is Editable");
					util.clickOn(logger, driver, "", label(driver, "Go to Today", ""));
				}
			} else if (type.equals("others")) {
				util.pause(logger, "5");
				util.clickOn(logger, driver, "", tableCellText(logger, columnName, tabName));
				util.pause(logger, "5");
				util.doubleClick(logger, driver, tableCellText(logger, columnName, tabName));
				util.pause(logger, "10");
				if (driver.findElements(By.xpath("//div[text()='Security prevents writing to this field']"))
						.size() != 0) {
					System.out.println("Element is Non Editable");
					logger.log(Status.INFO, "Element is Non Editable");
					util.clickOn(logger, driver, "", tooltip(driver, "Cancel (ESC)", "", ""));
				} else {
					System.out.println("Element is editable");
					logger.log(Status.INFO, "Element is Editable");
					util.clickOn(logger, driver, "", tooltip(driver, "Cancel (ESC)", "", ""));
				}
			}
			util.pause(logger, "5");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to verify if the field can be edited");
		}
	}

	// returns tab name webElement
	// @SuppressWarnings("unchecked")
	public WebElement tabName(WebDriver driver, String tabName) {
		try {
			if (driver.findElements(By.xpath("//*[@class='tab_caption_text'][text()='" + tabName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[@class='tab_caption_text'][text()='" + tabName + "']"));
			} else if (driver
					.findElements(By.xpath("//*[@class='tab_caption_text'][contains(text(),'" + tabName + "')]"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//*[@class='tab_caption_text'][contains(text(),'" + tabName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// Quebec release
	// Remove column
	// columnName-name of column to be added
	public void removeColumn(ExtentTest logger, String columnName, String tabName) {
		try {
			if (tabName.isEmpty()) {
				tabName = "";
			} else {
				tabName = "//*[@tab_caption='" + tabName + "']";
			}
			util.pause(logger, "5");
			WebElement personalizedIcon = driver.findElement(
					By.xpath(tabName + "//i[contains(@data-original-title,'List')][not(@aria-hidden='true')]"));
			wait.elementIsClickable(logger, driver, personalizedIcon);
			util.clickOn(logger, driver, "", personalizedIcon);
			Select rightDropdown = new Select(dropdown(driver, "Selected", ""));
			List<WebElement> rightList = rightDropdown.getOptions();
			int flag = 0;
			for (int k = 0; k < rightList.size(); k++) {
				if (rightList.get(k).getText().equals(columnName)) {
					rightDropdown.selectByVisibleText(columnName);
					util.clickOn(logger, driver, "", tooltip(driver, "Remove", "", ""));
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
			util.clickOn(logger, driver, "", button(driver, "OK", ""));
			util.pause(logger, "5");
			logger.log(Status.PASS, "Column is removed");
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "Unable to remove columns in nav page");
		}
	}
}
