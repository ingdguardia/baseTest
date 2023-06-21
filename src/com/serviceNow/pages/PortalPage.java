package com.serviceNow.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class PortalPage extends BasePage {

	public PortalPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "gsft_main")
	public WebElement iframe_gsft;
	public WebElement iFrame1;
	@FindBy(id = "iFrame1")
	public WebElement element = null;
	public String parentFieldName = "";
	@FindBy(xpath = "//div[@id='select2-drop']//input")
	public WebElement txt_DropList;
	@FindBy(xpath = "//*[@id='casetitle']")
	public WebElement lbl_titleCase;
	@FindBy(xpath = " //textarea[@id='comments']")
	public WebElement txt_comments;
	@FindBy(xpath = "//*[@class='ng-binding ng-scope']")
	public WebElement lbl_attachmentMsg;
	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement btn_deleteAttachment;
	@FindBy(xpath = ".//input[@class='sp-attachments-input']")
	public WebElement txt_attachment;
	// @FindBy(xpath=".//span[@class='ng-binding']")public WebElement
	// lbl_popUpValidationMsg;
	// Quebec
	@FindBy(xpath = ".//span[contains(@aria-label,'Error')]/following-sibling::span")
	public WebElement lbl_popUpValidationMsg;
	@FindBy(xpath = ".//button[contains(@aria-label,'Close')]")
	public WebElement btn_closeNotification;
	@FindBy(xpath = ".//div[contains(@ng-show,'role')]//div[@aria-hidden='false']//button[contains(text(),'Remove')]")
	public WebElement btn_removeRoleAccess;
	@FindBy(xpath = ".//div[contains(@class,'modal-header')]//*[contains(@class,'ng-binding')]")
	public WebElement lbl_alertHeading;
	@FindBy(xpath = ".//div[contains(@class,'modal-body')]//*[contains(@class,'ng-binding')]")
	public WebElement lbl_alertMessage;

	// My Cases
	@FindBy(xpath = "//div[@sn-atf-area='SP ACN4.0 MyCases Cards - NonApproval'] | //div[@sn-atf-area='SP ACN 4.0 Data Table Actionable']")
	public WebElement load_myCases;

	// Portal
	@FindBy(xpath = "//h2[@id='BrowseSupport Topics']")
	public WebElement load_supportPortal;
	@FindBy(xpath = "//*[@id='acn-footer']")
	public WebElement footerPortal;
	@FindBy(xpath = "// *[@class='container']")
	public WebElement containerPortal;

	// From view
	@FindBy(xpath = "//div[@id='s2id_sp_formfield_u_md_sponsor']")
	public WebElement load_portalRequest;

	// Creation Form
	@FindBy(xpath = "//legend[@id='required_information_bottom']/parent::fieldset/descendant::label")
	public List<WebElement> incompleteFields;

	public WebElement formContainers(WebDriver driver) {

		List<String> xpathOptions = new ArrayList<>();
		xpathOptions.add("//div[@id='sc_cat_item']//*[@class='container']");
		xpathOptions.add("//div[@id='sc_cat_item']//*[@class='panel panel-default']");
		xpathOptions.add("//div[@id='sc_cat_item']//*[contains(@class,'panel panel-default')]");

		for (String optionXpath : xpathOptions) {
			if (driver.findElements(By.xpath(optionXpath)).size() != 0) {
				WebElement element = driver.findElement(By.xpath(optionXpath));
				System.out.println(element);
				return element;
			}
		}
		return null;
	}

	public WebElement portalSite(WebDriver driver, String siteName) {
		try {
			if (siteName.equalsIgnoreCase("portal")) {
				element = driver.findElement(By.xpath("//h2[@id='BrowseSupport Topics']"));
			} else if (siteName.equalsIgnoreCase("create new request")) {
				element = driver.findElement(By.xpath("//div[@id='s2id_sp_formfield_u_md_sponsor']"));
			} else if (siteName.equalsIgnoreCase("my cases")) {
				element = driver.findElement(By.xpath(
						"//div[@sn-atf-area='SP ACN4.0 MyCases Cards - NonApproval'] | //div[@sn-atf-area='SP ACN 4.0 Data Table Actionable']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println("Portal site: " + element);
		return element;
	}

	// returns webElement based on background text
	// @SuppressWarnings("unchecked")
	public WebElement backgroundText(WebDriver driver, String value) {
		try {
			if (driver.findElements(By.xpath("//*[@placeholder='" + value + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[@placeholder='" + value + "']"));
			} else if (driver.findElements(By.xpath("//*[contains(@placeholder,'" + value + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@placeholder,'" + value + "')]"));
			} else if (driver.findElements(By.xpath("//*[@aria-label='" + value + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[@aria-label='" + value + "']"));
			} else if (driver.findElements(By.xpath("//*[contains(@aria-label,'" + value + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@aria-label,'" + value + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns dropList webElement
	// @SuppressWarnings("unchecked")
	public WebElement dropList(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("//*[text()='" + fieldName + "']/parent::div//b")).size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName + "']/parent::div//b"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[text()='" + fieldName + "']/parent::label/parent::div//b"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName + "']/parent::label/parent::div//b"));
			} else if (driver
					.findElements(
							By.xpath("//label[text()='" + fieldName + "']/parent::div//div[contains(@id,'s2id')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//label[text()='" + fieldName + "']/parent::div//div[contains(@id,'s2id')]"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath(
							"//span[text()='" + fieldName + "']/parent::label/parent::div//div[contains(@id,'s2id')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//span[text()='" + fieldName + "']/parent::label/parent::div//div[contains(@id,'s2id')]"));
			} else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div/a"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div/a"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath(
							"//*[contains(text(),'" + fieldName + "')]/parent::label/following-sibling::span//div/a"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//*[contains(text(),'" + fieldName + "')]/parent::label/following-sibling::span//div/a"));
			} else if (driver
					.findElements(By
							.xpath("//*[contains(text(),'" + fieldName + "')]/parent::div//div[contains(@id,'s2id')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/parent::div//div[contains(@id,'s2id')]"));
			}
			// Quebec
			else if (driver.findElements(By.xpath(
					"//*[contains(text(),'" + fieldName + "')]/parent::label/parent::div//div[contains(@id,'s2id')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div//div[contains(@id,'s2id')]"));
			} else if (driver
					.findElements(By
							.xpath("//*[contains(text(),'" + fieldName + "')]/ancestor::div/following-sibling::div//b"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/ancestor::div/following-sibling::div//b"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns list of multiple values
	// @SuppressWarnings("unchecked")
	public String dropListElements(WebDriver driver, String fieldName) {
		String xpath = "";
		;
		try {
			if (driver
					.findElements(
							By.xpath("//label[text()='" + fieldName + "']/parent::div/following-sibling::ul//div"))
					.size() != 0) {
				xpath = "//label[text()='" + fieldName + "']/parent::div/following-sibling::ul//div";
			} else if (driver
					.findElements(By.xpath(
							"//label[contains(text(),'" + fieldName + "')]/parent::div/following-sibling::ul//div"))
					.size() != 0) {
				xpath = "//label[contains(text(),'" + fieldName + "')]/parent::div/following-sibling::ul//div";
			}
			if (driver
					.findElements(By.xpath("//label[text()='" + fieldName + "']/parent::div/following-sibling::ul//li"))
					.size() != 0) {
				xpath = "//label[text()='" + fieldName + "']/parent::div/following-sibling::ul//li";
			}
			// quebec and paris
			else if (driver
					.findElements(By.xpath(
							"//label[contains(text(),'" + fieldName + "')]/parent::div/following-sibling::ul//li"))
					.size() != 0) {
				xpath = "//label[contains(text(),'" + fieldName + "')]/parent::div/following-sibling::ul//li";
			} else if (driver
					.findElements(By.xpath("//input[@placeholder='" + fieldName + "']/following-sibling::ul//li//a"))
					.size() != 0) {
				xpath = "//input[@placeholder='" + fieldName + "']/following-sibling::ul//li//a";
			} else if (driver
					.findElements(By
							.xpath("//input[contains(@placeholder,'" + fieldName + "')]/following-sibling::ul//li//a"))
					.size() != 0) {
				xpath = "//input[contains(@placeholder,'" + fieldName + "')]/following-sibling::ul//li//a";
			} else {
				xpath = null;
			}
		} catch (Exception e) {
		}
		System.out.println(xpath);
		return xpath;
	}

	// returns dropdown webElement
	// @SuppressWarnings("unchecked")
	public WebElement dropdown(WebDriver driver, String fieldName) {
		try {
			if (driver
					.findElements(
							By.xpath("//*[text()='" + fieldName + "']/parent::label/parent::div/following::div/select"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[text()='" + fieldName + "']/parent::label/parent::div/following::div/select"));
			} else if (driver
					.findElements(By.xpath(
							"//*[text()='" + fieldName + "']/parent::label/parent::div/following-sibling::div/select"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//*[text()='" + fieldName + "']/parent::label/parent::div/following-sibling::div/select"));
			} else if (driver.findElements(By.xpath("//*[text()='" + fieldName + "']/parent::div//select"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName + "']/parent::div//select"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns button dropdown webElement
	// @SuppressWarnings("unchecked")
	public WebElement buttonDropdown(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath(".//*[text()='" + fieldName + "']/parent::button")).size() != 0) {
				element = driver.findElement(By.xpath(".//*[text()='" + fieldName + "']/parent::button"));
			} else if (driver.findElements(By.xpath(".//*[contains(text(),'" + fieldName + "')]/parent::button"))
					.size() != 0) {
				element = driver.findElement(By.xpath(".//*[contains(text(),'" + fieldName + "')]/parent::button"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// Select From dropList
	// fieldName-dropList name to be clicked on
	// value-value to be selected from dropList
	// @SuppressWarnings("unchecked")
	public void selectFromDropList(ExtentTest logger, WebDriver driver, String fieldName, String value) {
		try {
			if (driver.findElements(By.xpath("//span[@aria-hidden='false']/parent::span/parent::label[text()='"
					+ fieldName + "']//parent::div//b")).size() != 0) {
				element = driver.findElement(By.xpath("//span[@aria-hidden='false']/parent::span/parent::label[text()='"
						+ fieldName + "']//parent::div//b"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath("//span[@aria-hidden='false']/parent::span/parent::label/span[text()='"
							+ fieldName + "']/parent::label//parent::div//b"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//span[@aria-hidden='false']/parent::span/parent::label/span[text()='"
								+ fieldName + "']/parent::label//parent::div//b"));
			} else if (driver
					.findElements(
							By.xpath("//label[text()='" + fieldName + "']/parent::div//div[contains(@id,'s2id')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//label[text()='" + fieldName + "']/parent::div//div[contains(@id,'s2id')]"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath(
							"//span[text()='" + fieldName + "']/parent::label/parent::div//div[contains(@id,'s2id')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//span[text()='" + fieldName + "']/parent::label/parent::div//div[contains(@id,'s2id')]"));
			} else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div/a"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div/a"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath(
							"//*[contains(text(),'" + fieldName + "')]//parent::label/following-sibling::span//div/a"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//*[contains(text(),'" + fieldName + "')]//parent::label/following-sibling::span//div/a"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		util.pause(logger, "5");
		util.mouseHover(logger, driver, element);
		System.out.println(element);
		util.clickOn(logger, driver, "", element);
		util.pause(logger, "3");
		// util.waitTillElementIsVisible(logger, driver, txt_DropList);
		util.setText(logger, txt_DropList, value);
		try {
			if (value.equals("No")) {
				element = driver.findElement(
						By.xpath("//li[not(contains(@class,'highlighted'))]/div/span[text()='" + value + "']"));
			} else if (driver
					.findElements(
							By.xpath("//li//div[not(text())]/span[text()='" + value + "']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//li//div[not(text())]/span[text()='" + value + "']"));
			} else if (driver
					.findElements(
							By.xpath(
									".//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"(.//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value + "')])[1]"));
			} else if (driver
					.findElements(
							By.xpath(".//li[contains(@class,'highlighted')]/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(".//li[contains(@class,'highlighted')]/div[contains(text(),'" + value + "')]"));
			} else if (driver
					.findElements(By.xpath(".//li[contains(@class,'highlighted')]/div/div[text()='" + value + "']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath(".//li[contains(@class,'highlighted')]/div/div[text()='" + value + "']"));
			} else if (driver
					.findElements(
							By.xpath(".//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(".//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value + "')]"));
			} else {
				element = null;
			}
			System.out.println(element);
			element.click();
		} catch (Exception e) {
		}
	}

	// returns label webElement
	// @SuppressWarnings("unchecked")
	public WebElement label(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("//*[text()='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName + "')]"));
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
	public WebElement text(WebDriver driver, String fieldName) {
		try {
			if (driver
					.findElements(By.xpath("//*[text()='" + fieldName
							+ "']/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::label/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			} else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName
							+ "')]/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div[@aria-hidden='false']//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			} else if (driver.findElements(By.xpath(
					"//*[text()='" + fieldName + "']/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath("//*[text()='" + fieldName
							+ "']/parent::label/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			} else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName
							+ "')]/parent::label/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			} else if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::label/parent::div/following::div/input[not(@type='HIDDEN')][not(@type='hidden')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/parent::div/following::div/input[not(@type='HIDDEN')][not(@type='hidden')]"));
			} else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following::div/input[not(@type='HIDDEN')][not(@type='hidden')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following::div/input[not(@type='HIDDEN')][not(@type='hidden')]"));
			} else if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::label/parent::div/following::div//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/parent::div/following::div//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			} else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following::div//input[not(@type='hidden')][not(@type='HIDDEN')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following::div//input[not(@type='hidden')][not(@type='HIDDEN')]"));
			} else if (driver.findElements(By.xpath("//input[@name='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//input[@name='" + fieldName + "']"));
			} else if (driver
					.findElements(
							By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div//input"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div//input"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns requestNumber textfield webElement
	// @SuppressWarnings("unchecked")
	public WebElement requestText(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::label/parent::div/following::div/input[not(@type='hidden')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/parent::div/following::div/input[not(@type='hidden')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns textfield webElement which is having calendar
	// @SuppressWarnings("unchecked")
	public WebElement calendarText(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("//input[@aria-label='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//input[@aria-label='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath("//input[contains(@aria-label,'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//input[contains(@aria-label,'" + fieldName + "')]"));
			} else if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::label/parent::div/following::div//input[not(@type='HIDDEN')][not(@type='hidden')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/parent::div/following::div//input[not(@type='HIDDEN')][not(@type='hidden')]"));
			} else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div/following::div//input[not(@type='HIDDEN')][not(@type='hidden')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div/following::div//input[not(@type='HIDDEN')][not(@type='hidden')]"));
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
	public WebElement checkbox(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("//*[text()='" + fieldName + "']/parent::label/input[@type='checkbox']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//*[text()='" + fieldName + "']/parent::label/input[@type='checkbox']"));
			} else if (driver
					.findElements(By.xpath("//*[text()='" + fieldName + "']/parent::*//input[@type='checkbox']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//*[text()='" + fieldName + "']/parent::*//input[@type='checkbox']"));
			} else if (driver
					.findElements(
							By.xpath("//*[contains(text(),'" + fieldName + "')]/parent::*//input[@type='checkbox']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/parent::*//input[@type='checkbox']"));
			} else if (driver
					.findElements(By.xpath(".//*[text()='" + fieldName + "']/parent::*//input[@role='checkbox']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath(".//*[text()='" + fieldName + "']/parent::*//input[@role='checkbox']"));
			} else if (driver
					.findElements(
							By.xpath(".//*[contains(text(),'" + fieldName + "')]/parent::*//input[@role='checkbox']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(".//*[contains(text(),'" + fieldName + "')]/parent::*//input[@role='checkbox']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns checkbox webElement Inside A Tree Structure
	// @SuppressWarnings("unchecked")
	public WebElement innerCheckbox(WebDriver driver, String fieldName, String parentFieldName) {
		try {
			if (driver.findElements(By.xpath(".//*[contains(text(),'" + parentFieldName
					+ "')]//parent::span//parent::div//following-sibling::div//label[contains(text(),'" + fieldName
					+ "')]/parent::div//input")).size() != 0) {
				element = driver.findElement(By.xpath(".//*[contains(text(),'" + parentFieldName
						+ "')]//parent::span//parent::div//following-sibling::div//label[contains(text(),'" + fieldName
						+ "')]/parent::div//input"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns expand arrow button as WebElement
	// @SuppressWarnings("unchecked")
	public WebElement expandButton(WebDriver driver, String fieldName, String mainParent) {
		try {
			if (mainParent.isEmpty()) {
				parentFieldName = "";
			} else {
				parentFieldName = ".//*[contains(text(),'" + parentFieldName
						+ "')]//parent::span//parent::div//following-sibling::div";
			}
			if (driver
					.findElements(By.xpath(parentFieldName + "//label[contains(text(),'" + fieldName
							+ "')]/parent::div/preceding-sibling::button[contains(@aria-label,'Expand')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(parentFieldName + "//label[contains(text(),'" + fieldName
						+ "')]/parent::div/preceding-sibling::button[contains(@aria-label,'Expand')]"));
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
	public WebElement button(WebDriver driver, String fieldName) {
		List<String> xpathOptions = new ArrayList<>();
		xpathOptions.add("//div[@class='inline-cart ng-scope']//button[text()='" + fieldName + "']");
		xpathOptions.add("//div[@aria-hidden='false']//button[contains(text(),'" + fieldName + "')]");
		xpathOptions.add("//button[ text() = '" + fieldName + "']");
		xpathOptions.add("//button[contains(text(),'" + fieldName + "')]");
		xpathOptions.add("//button[normalize-space(text())='" + fieldName + "']");
		xpathOptions.add("(//input[@value='" + fieldName + "'][@type='button'])");
		xpathOptions.add("//button[@aria-label='" + fieldName + "']");
		xpathOptions.add("(//input[@value='" + fieldName + "'])");

		for (String optionXpath : xpathOptions) {
			if (driver.findElements(By.xpath(optionXpath)).size() != 0) {
				WebElement element = driver.findElement(By.xpath(optionXpath));
				System.out.println(element);
				return element;
			}
		}
		return null;
	}

	// returns link webElement
	// @SuppressWarnings("unchecked")
	public WebElement link(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath("(//a[text()='" + fieldName + "'])")).size() != 0) {
				element = driver.findElement(By.xpath("(//a[text()='" + fieldName + "'])"));
			} else if (driver.findElements(By.xpath("(//a[contains(text(),'" + fieldName + "')])")).size() != 0) {
				element = driver.findElement(By.xpath("(//a[contains(text(),'" + fieldName + "')])"));
			} else if (driver.findElements(By.xpath("(//*[text()='" + fieldName + "'])")).size() != 0) {
				element = driver.findElement(By.xpath("(//*[text()='" + fieldName + "'])"));
			} else if (driver.findElements(By.xpath("(//*[contains(text(),'" + fieldName + "')])")).size() != 0) {
				element = driver.findElement(By.xpath("(//*[contains(text(),'" + fieldName + "')])"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns catalog card webElement
	// @SuppressWarnings("unchecked")
	public WebElement catalogLink(WebDriver driver, String fieldName) {
		try {
			if (driver
					.findElements(
							By.xpath("//div[@class='card__caption']/div[normalize-space(text())='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//div[@class='card__caption']/div[normalize-space(text())='" + fieldName + "']"));
			} else if (driver
					.findElements(By.xpath(
							"//div[contains(@class,'caption')]/div[normalize-space(text())='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//div[contains(@class,'caption')]/div[normalize-space(text())='" + fieldName + "']"));
			} else if (driver
					.findElements(
							By.xpath("//div[contains(@class,'caption')][normalize-space(text())='" + fieldName + "']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//div[contains(@class,'caption')][normalize-space(text())='" + fieldName + "']"));
			} else if (driver
					.findElements(By.xpath("//div[contains(@class,'caption')][contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//div[contains(@class,'caption')][contains(text(),'" + fieldName + "')]"));
			} else if (driver
					.findElements(By.xpath("//*[contains(@class,'caption')][contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//*[contains(@class,'caption')][contains(text(),'" + fieldName + "')]"));
			} else if (driver.findElements(By.xpath("//*[@title='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[@title='" + fieldName + "']"));
			} else if (driver.findElements(By.xpath("//img[@alt='" + fieldName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//img[@alt='" + fieldName + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// value-tooltip value which is displayed on mouse hover
	// @SuppressWarnings("unchecked")
	public WebElement tooltip(WebDriver driver, String value) {
		try {
			if (driver.findElements(By.xpath("//*[@data-original-title='" + value + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[@data-original-title='" + value + "']"));
			} else if (driver.findElements(By.xpath("//*[contains(@data-original-title,'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@data-original-title,'" + value + "')]"));
			} else if (driver.findElements(By.xpath("//*[@title='" + value + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[@title='" + value + "']"));
			} else if (driver.findElements(By.xpath("//*[contains(@title,'" + value + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@title,'" + value + "')]"));
			} else if (driver.findElements(By.xpath("//*[@aria-label='" + value + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[@aria-label='" + value + "']"));
			} else if (driver.findElements(By.xpath("//*[contains(@aria-label,'" + value + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@aria-label,'" + value + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns red color error webElement for a field
	// @SuppressWarnings("unchecked")
	public WebElement validationMsg(WebDriver driver, String fieldName, String msg) {
		try {
			// Quebec
			if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][text()='" + msg
					+ "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][text()='" + msg
						+ "']"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][contains(text(),'"
					+ msg + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][contains(text(),'"
						+ msg + "'])"));
			} else if (driver
					.findElements(By.xpath("//*[text()='" + fieldName
							+ "']/parent::div//div[contains(@class,'ng-binding')][text()='" + msg + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::div//div[contains(@class,'ng-binding')][text()='" + msg + "']"));
			} else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName
							+ "')]/parent::div//div[contains(@class,'ng-binding')][contains(text(),'" + msg + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::div//div[contains(@class,'ng-binding')][contains(text(),'" + msg + "')]"));
			} else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::div//span/following::div[contains(@class,'ng-binding')][contains(text(),'" + msg
					+ "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::div//span/following::div[contains(@class,'ng-binding')][contains(text(),'" + msg
						+ "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns text in blue color Box
	// @SuppressWarnings("unchecked")
	public WebElement fieldMsg(WebDriver driver, String fieldName, String msg) {
		try {
			// Quebec
			if (driver
					.findElements(By.xpath("//*[text()='" + fieldName
							+ "']/parent::label/following-sibling::div//div[contains(@class,'ng-binding')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/following-sibling::div//div[contains(@class,'ng-binding')]"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName
							+ "')]/parent::label/following-sibling::div//div[contains(@class,'ng-binding')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/following-sibling::div//div[contains(@class,'ng-binding')]"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][text()='" + msg
					+ "']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][text()='" + msg
						+ "']"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][contains(text(),'"
					+ msg + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/following-sibling::div//div[contains(@class,'ng-binding')][contains(text(),'"
						+ msg + "'])"));
			} else if (driver
					.findElements(By.xpath("//*[text()='" + fieldName
							+ "']/parent::div//*[contains(@class,'ng-binding')]/*[text()='" + msg + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::div//*[contains(@class,'ng-binding')]/*[text()='" + msg + "']"));
			} else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName
							+ "')]/parent::div//*[contains(@class,'ng-binding')]/*[contains(text(),'" + msg + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::div//*[contains(@class,'ng-binding')]/*[contains(text(),'" + msg + "')]"));
			} else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName
							+ "')]/parent::div//*[contains(@class,'ng-binding')]/*[contains(text(),'" + msg + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::div//*[contains(@class,'ng-binding')]/*[contains(text(),'" + msg + "')]"));
			} else if (driver
					.findElements(By.xpath("//*[text()='" + fieldName + "']/parent::div//span[@class='ng-binding']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[text(),'" + fieldName + "']/parent::div//span[@class='ng-binding']"));
			} else if (driver
					.findElements(By
							.xpath("//*[contains(text(),'" + fieldName + "')]/parent::div//span[@class='ng-binding']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/parent::div//span[@class='ng-binding']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// select from textfield type dropdown
	// fieldName-textfield name
	// value-value to be selected
	// @SuppressWarnings("unchecked")
	public void selectFromTextfield(ExtentTest logger, WebDriver driver, String fieldName, String value) {
		try {
			if (driver.findElements(By.xpath("//label[text()='" + fieldName
					+ "']/parent::div[@aria-hidden='false']/span//input[@role='combobox']")).size() != 0) {
				element = driver.findElement(By.xpath("//label[text()='" + fieldName
						+ "']/parent::div[@aria-hidden='false']/span//input[@role='combobox']"));
			}
			// Quebec
			if (driver
					.findElements(By.xpath("//span[text()='" + fieldName
							+ "']/parent::label/parent::div[@aria-hidden='false']/span//input[@role='combobox']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//span[text()='" + fieldName
						+ "']/parent::label/parent::div[@aria-hidden='false']/span//input[@role='combobox']"));
			} else if (driver.findElements(By.xpath("//label[contains(text(),'" + fieldName
					+ "')]/parent::div[@aria-hidden='false']/span//input[@role='combobox']")).size() != 0) {
				element = driver.findElement(By.xpath("//label[contains(text(),'" + fieldName
						+ "')]/parent::div[@aria-hidden='false']/span//input[@role='combobox']"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath("//span[contains(text(),'" + fieldName
							+ "')]/parent::label/parent::div[@aria-hidden='false']/span//input[@role='combobox']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//span[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div[@aria-hidden='false']/span//input[@role='combobox']"));
			} else if (driver
					.findElements(By.xpath(
							"//label[contains(text(),'" + fieldName + "')]/parent::div//div[contains(@id,'s2id')]"))
					.size() != 0) {
				element = driver.findElement(By
						.xpath("//label[contains(text(),'" + fieldName + "')]/parent::div//div[contains(@id,'s2id')]"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//span[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div//div[contains(@id,'s2id')]")).size() != 0) {
				element = driver.findElement(By.xpath("//span[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div//div[contains(@id,'s2id')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		element.click();
		util.setText(logger, element, value);
		try {
			if (driver
					.findElements(
							By.xpath(
									".//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("(.//li[contains(@class,'highlighted')]/div/span[contains(text(),'" + value
								+ "')])[1]"));
			} else if (driver
					.findElements(
							By.xpath(".//li[contains(@class,'highlighted')]/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(".//li[contains(@class,'highlighted')]/div[contains(text(),'" + value + "')]"));
			} else if (driver
					.findElements(
							By.xpath(".//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(".//li[contains(@class,'highlighted')]/div/div[contains(text(),'" + value + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		element.click();
	}

	// save newly generated request number
	// catalogRequestInitials-prefix of requestNumber. Eg. RITM, CTM, PIP
	public String saveRequestNumber(ExtentTest logger, String catalogRequestInitials) {
		try {
			// util.pause(logger, "20");
			//// util.waitTillElementIsVisible(logger, driver,
			// label(driver,catalogRequestInitials));
			util.clickOn(logger, driver, "", label(driver, catalogRequestInitials));
			// util.waitTillElementIsVisible(logger, driver,lbl_titleCase );
			util.pause(logger, "30");
			String number = util.getElementText(logger, lbl_titleCase);
			String Number[] = number.split("-");
			String requestNumber = Number[0].trim();
			System.out.println(requestNumber);
			return (requestNumber);
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "unable to search open ticket");
			System.out.println("No request is saved");
			return ("CTM0000");
		}
	}

	// return table column header webElement
	// @SuppressWarnings("unchecked")
	public WebElement tableColumnHeader(WebDriver driver, String columnName) {
		try {
			if (driver.findElements(By.xpath("//th//*[normalize-space(text())='" + columnName + "']")).size() != 0) {
				element = driver.findElement(By.xpath("//th//*[normalize-space(text())='" + columnName + "']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// return table data webElement, table number is 1,2,3..if there is only one
	// table, then it should be 1
	// @SuppressWarnings("unchecked")
	public WebElement tableData(WebDriver driver, int tableNumber, int rowNumber, int columnNumber) {
		try {
			if (driver.findElements(By.xpath("(//thead)[" + tableNumber + "]/following-sibling::tbody//tr[" + rowNumber
					+ "]//td[" + columnNumber + "]")).size() != 0) {
				element = driver.findElement(By.xpath("(//thead)[" + tableNumber + "]/following-sibling::tbody//tr["
						+ rowNumber + "]//td[" + columnNumber + "]"));
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
	public WebElement tableCellText(WebDriver driver, ExtentTest logger, int tableNumber, String rowName,
			String columnName) {
		try {
			int columnIndex = util.returnColumnIndex(logger, "(//thead)[" + tableNumber + "]//th", columnName, driver);
			// System.out.print("column index"+columnIndex);
			if (driver
					.findElements(
							By.xpath("(//thead)[" + tableNumber + "]/following-sibling::tbody//*[contains(text(),'"
									+ rowName + "')]/parent::td/parent::tr//td[" + columnIndex + "]//input"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("(//thead)[" + tableNumber + "]/following-sibling::tbody//*[contains(text(),'"
								+ rowName + "')]/parent::td/parent::tr//td[" + columnIndex + "]//input"));
			} else if (driver
					.findElements(
							By.xpath("(//thead)[" + tableNumber + "]/following-sibling::tbody//*[contains(text(),'"
									+ rowName + "')]/parent::td/parent::tr//td[" + columnIndex + "]//textarea"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("(//thead)[" + tableNumber + "]/following-sibling::tbody//*[contains(text(),'"
								+ rowName + "')]/parent::td/parent::tr//td[" + columnIndex + "]//textarea"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// Returns By default selected text webElement in dropList
	// @SuppressWarnings("unchecked")
	public WebElement selectedTextInDropList(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath(
					"//*[text()='" + fieldName + "']/following-sibling::span//div/a//span[@class='select2-chosen']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/following-sibling::span//div/a//span[@class='select2-chosen']"));
			}
			// Quebec
			if (driver
					.findElements(By.xpath("//*[text()='" + fieldName
							+ "']/parent::label/following-sibling::span//div/a//span[@class='select2-chosen']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/following-sibling::span//div/a//span[@class='select2-chosen']"));
			} else if (driver
					.findElements(
							By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div/a//span"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[contains(text(),'" + fieldName + "')]/following-sibling::span//div/a//span"));
			}
			// Quebec
			else if (driver.findElements(By.xpath(
					"//*[contains(text(),'" + fieldName + "')]/parent::label/following-sibling::span//div/a//span"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/following-sibling::span//div/a//span"));
			} else if (driver
					.findElements(By.xpath("//*[text()='" + fieldName + "']/following-sibling::span//div/a//span"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//*[text()='" + fieldName + "']/following-sibling::span//div/a//span"));
			}
			// Quebec
			else if (driver
					.findElements(By.xpath(
							"//*[text()='" + fieldName + "']/parent::label/following-sibling::span//div/a//span"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[text()='" + fieldName + "']/parent::label/following-sibling::span//div/a//span"));
			} else if (driver.findElements(By.xpath("//*[text()='" + fieldName
					+ "']/parent::div/following-sibling::div//a//span[@class='select2-chosen']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::div/following-sibling::div//a//span[@class='select2-chosen']"));
			}
			// Quebec
			else if (driver.findElements(By.xpath(
					"//*[text()='" + fieldName + "']/parent::label/parent::div//a//span[@class='select2-chosen']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//*[text()='" + fieldName + "']/parent::label/parent::div//a//span[@class='select2-chosen']"));
			} else if (driver
					.findElements(By.xpath("//*[contains(text(),'" + fieldName
							+ "')]/parent::div/following-sibling::div//a//span[@class='select2-chosen']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::div/following-sibling::div//a//span[@class='select2-chosen']"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div//a//span[@class='select2-chosen']")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div//a//span[@class='select2-chosen']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// returns radio button webElement
	// @SuppressWarnings("unchecked")
	public WebElement radioButton(WebDriver driver, String question, String option) {
		try {
			if (driver.findElements(By.xpath(".//*[text()='" + question + "']/following-sibling::span//*[text()='"
					+ option + "']/preceding-sibling::input[@type='radio']")).size() != 0) {
				element = driver
						.findElement(By.xpath(".//*[text()='" + question + "']/following-sibling::span//*[text()='"
								+ option + "']/preceding-sibling::input[@type='radio']"));
			}
			// Quebec
			else if (driver.findElements(
					By.xpath(".//*[text()='" + question + "']/parent::label/following-sibling::span//*[text()='"
							+ option + "']/preceding-sibling::input[@type='radio']"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(".//*[text()='" + question + "']/parent::label/following-sibling::span//*[text()='"
								+ option + "']/preceding-sibling::input[@type='radio']"));
			} else if (driver.findElements(
					By.xpath(".//*[contains(text(),'" + question + "')]/following-sibling::span//*[contains(text(),'"
							+ option + "')]/preceding-sibling::input[@type='radio']"))
					.size() != 0) {
				element = driver.findElement(By
						.xpath(".//*[contains(text(),'" + question + "')]/following-sibling::span//*[contains(text(),'"
								+ option + "')]/preceding-sibling::input[@type='radio']"));
			}
			// Quebec
			else if (driver.findElements(By.xpath(".//*[contains(text(),'" + question
					+ "')]/parent::label/following-sibling::span//*[contains(text(),'" + option
					+ "')]/preceding-sibling::input[@type='radio']")).size() != 0) {
				element = driver.findElement(By.xpath(".//*[contains(text(),'" + question
						+ "')]/parent::label/following-sibling::span//*[contains(text(),'" + option
						+ "')]/preceding-sibling::input[@type='radio']"));
			} else if (driver
					.findElements(By.xpath(
							"//*[@aria-label='" + question + "']//following-sibling::label/*[text()='" + option + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//*[@aria-label='" + question + "']//following-sibling::label/*[text()='" + option + "']"));
			} else if (driver.findElements(By.xpath("//*[contains(@aria-label,'" + question
					+ "')]//following-sibling::label/*[contains(text(),'" + option + "')]")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(@aria-label,'" + question
						+ "')]//following-sibling::label/*[contains(text(),'" + option + "')]"));
			} else if (driver
					.findElements(By.xpath(
							"//*[@aria-label='" + question + "']//following-sibling::label[text()='" + option + "']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//*[@aria-label='" + question + "']//following-sibling::label[text()='" + option + "']"));
			} else if (driver.findElements(By.xpath("//input[@aria-label='" + option + "'][@type='radio']"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//input[@aria-label='" + option + "'][@type='radio']"));
			} else if (driver.findElements(By.xpath("//input[contains(@aria-label,'" + option + "')][@type='radio']"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath("//input[contains(@aria-label,'" + option + "')][@type='radio']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// save newly generated request number from open cases
	// catalogRequestInitials-prefix of requestNumber. Eg. RITM, CTM, PIP
	public String saveRequestNumberFromCases(ExtentTest logger, String catalogRequestInitials) {
		try {
			util.pause(logger, "20");
			util.clickOn(logger, driver, "", tooltip(driver, "Card View"));
			// util.waitTillElementIsClickable(logger, driver,
			// label(driver,catalogRequestInitials));
			String requestNumber = driver
					.findElement(By.xpath("(//div[contains(@class,'ticketCard ng-scope')][contains(@aria-label,'"
							+ catalogRequestInitials + "')])[1]"))
					.getAttribute("id").trim();
			System.out.println("Request number is " + requestNumber);
			logger.log(Status.INFO, "Request Number is " + requestNumber);
			return (requestNumber);
		} catch (Exception e) {
			util.screenShotAndErrorMsg(logger, e, driver, "unable to get ticket number");
			System.out.println("No request is saved");
			return ("CTM0000");
		}
	}

	// return table cell webElement
	// provide column name, rowNumber, webElementType, incrementValue
	// incrementValue-"0" if no increment is needed
	// label-Pass Text in case of button
	// @SuppressWarnings("unchecked")
	public WebElement updateTableCellText(ExtentTest logger, String columnName, int rowNumber, String webElementType,
			int incrementValue, String label) {
		util.pause(logger, "5");
		String xpath = "//table//th";
		System.out.println(xpath);
		int cIndex = util.returnColumnIndex(logger, "//table//th", columnName, driver);
		int columnIndex = cIndex + incrementValue;
		System.out.print("column index" + columnIndex);
		try {
			if (webElementType.equals("textfield")) {
				if (driver
						.findElements(By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//input"))
						.size() != 0) {
					element = driver.findElement(
							By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//input"));
				} else {
					element = null;
				}
			} else if (webElementType.equals("dropdown")) {
				if (driver
						.findElements(
								By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//select"))
						.size() != 0) {
					element = driver.findElement(
							By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//select"));
				} else {
					element = null;
				}
			} else if (webElementType.equals("textarea")) {
				if (driver
						.findElements(
								By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//textarea"))
						.size() != 0) {
					element = driver.findElement(
							By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//textarea"));
				} else {
					element = null;
				}
			} else if (webElementType.equals("calendarButton")) {
				if (driver
						.findElements(
								By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//button"))
						.size() != 0) {
					element = driver.findElement(
							By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//button"));
				} else {
					element = null;
				}
			} else if (webElementType.equalsIgnoreCase("dropList")) {
				if (driver
						.findElements(
								By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//div//a"))
						.size() != 0) {
					element = driver.findElement(
							By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//div//a"));
				} else {
					element = null;
				}
			} else if (webElementType.equals("button")) {
				if (driver.findElements(By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex
						+ "]//button[text()='" + label + "']")).size() != 0) {
					element = driver.findElement(By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex
							+ "]//button[text()='" + label + "']"));
				} else {
					element = null;
				}
			} else if (webElementType.equals("link")) {
				if (driver.findElements(By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//a"))
						.size() != 0) {
					element = driver
							.findElement(By.xpath("//table//tbody//tr[" + rowNumber + "]//td[" + columnIndex + "]//a"));
				} else {
					element = null;
				}
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// Returns Label webElement to check value corresponding to a label
	// @SuppressWarnings("unchecked")
	public WebElement labelValue(WebDriver driver, String fieldName) {
		try {
			if (driver.findElements(By.xpath(".//*[text()='" + fieldName + "']/following-sibling::span")).size() != 0) {
				element = driver.findElement(By.xpath(".//*[text()='" + fieldName + "']/following-sibling::span"));
			} else if (driver
					.findElements(By.xpath(".//*[contains(text(),'" + fieldName + "')]/following-sibling::span"))
					.size() != 0) {
				element = driver
						.findElement(By.xpath(".//*[contains(text(),'" + fieldName + "')]/following-sibling::span"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// Returns SubsectionHeading webElement
	// Pass SectionName
	// Pass Subsection ID in small letters
	// @SuppressWarnings("unchecked")
	public WebElement subsectionHeading(WebDriver driver, String sectionName, String subsectionID) {
		try {
			if (driver
					.findElements(By.xpath(".//span[text()='" + sectionName
							+ "']/parent::div//following-sibling::div//span[contains(@id,'" + subsectionID + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(".//span[text()='" + sectionName
						+ "']/parent::div//following-sibling::div//span[contains(@id,'" + subsectionID + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// Returns SectionHeading Arrow webElement
	// Pass SectionName
	// Pass arrow name in small letters either up or down
	// @SuppressWarnings("unchecked")
	public WebElement sectionUpAndDownArrowButton(WebDriver driver, String sectionName, String arrowName) {
		try {
			if (driver
					.findElements(
							By.xpath(".//*[text()='" + sectionName + "']/i[contains(@class,'" + arrowName + "')]"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath(".//*[text()='" + sectionName + "']/i[contains(@class,'" + arrowName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// Returns mandatory fields
	// Pass the name of label
	// @SuppressWarnings("unchecked")
	public WebElement mandatoryField(WebDriver driver, String fieldName) {
		try {
			// Quebec
			if (driver
					.findElements(By.xpath(".//span[contains(text(),'" + fieldName
							+ "')]/parent::label//span[contains(@class,'mandatory') and @aria-hidden='false']"))
					.size() != 0) {
				element = driver.findElement(By.xpath(".//span[contains(text(),'" + fieldName
						+ "')]/parent::label//span[contains(@class,'mandatory') and @aria-hidden='false']"));
			} else if (driver.findElements(By.xpath(".//label[contains(text(),'" + fieldName
					+ "')]//span[contains(@class,'mandatory') and @aria-hidden='false']")).size() != 0) {
				element = driver.findElement(By.xpath(".//label[contains(text(),'" + fieldName
						+ "')]//span[contains(@class,'mandatory') and @aria-hidden='false']"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		return element;
	}

	// This method performs edit operations on the Table in portal page
	// WebElementType = checkbox. To check and uncheck the checkbox in table
	// pass the row number, column number, table number, webElement type
	public void editTableItemsOnPortalPage(ExtentTest logger, WebDriver driver, int tableNumber, int rowNumber,
			int columnNumber, String webElementType) {
		try {
			if (webElementType.equals("checkbox")) {
				util.clickOn(logger, driver, "", driver.findElement(By.xpath("(//thead)[" + tableNumber
						+ "]/following-sibling::tbody//tr[" + rowNumber + "]//td[" + columnNumber + "]//input")));
			}
		} catch (Exception e) {
		}
	}

	// returns button webElement On ModalBox
	// @SuppressWarnings("unchecked")
	public WebElement buttonOnModalBox(WebDriver driver, String fieldName, String modalHeader) {
		try {
			if (driver
					.findElements(By.xpath(".//*[text()='" + modalHeader
							+ "']/parent::div/following-sibling::div/button[contains(text(),'" + fieldName + "')]"))
					.size() != 0) {
				element = driver.findElement(By.xpath(".//*[text()='" + modalHeader
						+ "']/parent::div/following-sibling::div/button[contains(text(),'" + fieldName + "')]"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}

	// For typing in block
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

	// returns textarea webElement
	// @SuppressWarnings("unchecked")
	public WebElement textArea(WebDriver driver, String fieldName) {
		try {
			// Quebec
			if (driver.findElements(By.xpath(
					"//*[text()='" + fieldName + "']/parent::label/parent::div[not(@aria-hidden='true')]//textarea"))
					.size() != 0) {
				element = driver.findElement(By.xpath("//*[text()='" + fieldName
						+ "']/parent::label/parent::div[not(@aria-hidden='true')]//textarea"));
			}
			// Quebec
			else if (driver.findElements(By.xpath("//*[contains(text(),'" + fieldName
					+ "')]/parent::label/parent::div[not(@aria-hidden='true')]//textarea")).size() != 0) {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + fieldName
						+ "')]/parent::label/parent::div[not(@aria-hidden='true')]//textarea"));
			} else if (driver
					.findElements(
							By.xpath("//*[text()='" + fieldName + "']/parent::div[not(@aria-hidden='true')]//textarea"))
					.size() != 0) {
				element = driver.findElement(
						By.xpath("//*[text()='" + fieldName + "']/parent::div[not(@aria-hidden='true')]//textarea"));
			} else if (driver.findElements(By
					.xpath("//*[contains(text(),'" + fieldName + "')]/parent::div[not(@aria-hidden='true')]//textarea"))
					.size() != 0) {
				element = driver.findElement(By.xpath(
						"//*[contains(text(),'" + fieldName + "')]/parent::div[not(@aria-hidden='true')]//textarea"));
			} else {
				element = null;
			}
		} catch (Exception e) {
		}
		System.out.println(element);
		return element;
	}
}
