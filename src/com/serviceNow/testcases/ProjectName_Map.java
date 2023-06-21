package com.serviceNow.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProjectName_Map extends CommonTest {

	public ProjectName_Map() throws IOException {
		super();
	}

	String ticketNumber = "",
			parentWindow = "0";

	public WebElement wElement = null;
	public WebElement wElement2 = null;
	public List<WebElement> wElementList = null;
	public List<String> list = new ArrayList<String>();
	public List<String> list2 = new ArrayList<String>();
	int count_error = 0;
	String coManagerField = "//span[@id='sys_user_group.u_co_managers_edit']/following-sibling::p";

	// BACKEND LOCATORS
	By filterNavigator = By.xpath("//input[@placeholder='Filter navigator']"),
			attachmentPath = By.xpath("//sp-attachment-button");

	// FRONTEND LOCATORS
	By submitBTN2 = By.cssSelector(
			"#catItemTop > div > div.inline-cart.ng-scope > div > div.text-right.ng-scope > div > div > button"),
			submitButtonFront = By.xpath("//button[.='Submit']"),
			backBTN = By.xpath("//button[@aria-label='Back']");
	String requiredInfoPath = "//legend[@id='required_information_bottom']",
			incompleteFields = "//legend[@id='required_information_bottom']/parent::fieldset/descendant::label";

	// BACKEND VARS
	String attZip = "./Resources/Zip.zip",
			attWord = "./Resources/Word.docx",
			attPdf = "./Resources/Attachment.pdf",
			attPng = "./Resources/Screenshot.png",
			attJpg = "./Resources/Screenshot2.jpg",
			attTxt = "./Resources/Attachment.txt";

	// FRONTEND VARS
	List<String> esgApproversGroup = new ArrayList<String>();
	List<String> esgCoManagerGroup = new ArrayList<String>();
	By load_myCases = By.xpath(
			"//div[@sn-atf-area='SP ACN4.0 MyCases Cards - NonApproval'] | //div[@sn-atf-area='SP ACN 4.0 Data Table Actionable']");

	String aux[], actualGroupMembers, coManagerMembers, approverGroup, checkbox, eidInput;

	// User administration table paths
	String groupMembersPath = "//tr[@record_class='sys_user_grmember']//a[@class='linked']",
			groupMembersSection = "(//a[contains(text(), 'Group Members')])[1]",
			columnSearch = "(//button[@aria-controls='sys_user_group_table_header_search_control'])[1]",
			nameSearch = "(//input[@aria-label='Search column: name'])[1]",
			editButton = "(//button[@id='sysverb_edit_m2m'])[2]",
			addButton = "//a[@id='add_to_collection_button']",
			saveButton = "//button[@id='select_0_sysverb_save']",
			removeButton = "//a[@id='remove_from_collection_button']";

	By groupsModule = By.xpath("//span[.='User Administration']/following::div[contains(text(),'Groups')]/ancestor::a"),
			modifyGroupModule = By.xpath(
					"(//span[.='Self-Service']/following::div[contains(text(),'Modify Group Membership')]/ancestor::a)[1]"),
			coManagersLabel = By.xpath("//label[contains(.,'Co-managers:')]"),
			addMembersCheckbox = By.xpath("//input[@name='add_members']"),
			removeMembersCheckbox = By.xpath("//input[@name='remove_members']"),
			enterpriseIDsInput = By.xpath("//div[@id='s2id_sp_formfield_add_enterpriseid']//input");

	// Example locator
	By locationField = By.xpath("//div[@id='s2id_sp_formfield_u_location']"),
			categoryField = By.xpath("//div[@id='s2id_sp_formfield_u_category']"),
			clientCategoryField = By.xpath("//div[@id='s2id_sp_formfield_u_client_category']"),
			clientField = By.xpath("//input[@id='sp_formfield_u_client']"),
			deadlineField = By.xpath("//input[@id='sp_formfield_due_date']"),
			titleField = By.xpath("//input[@id='sp_formfield_short_description']"),
			descriptionField = By.xpath("//textarea[@id='sp_formfield_description']"),
			attField = By.xpath("//span[text()='Add attachments']/preceding::button[1]");

	// Used to verify Mandatory Fields
	String dummyFooter = "//div[@id='dummyfooter']";

	// to be used on VerifyEmailBody()
	String emailBodyTemplate, emailBodyText;

	// to be used on VerifyEmailBody()
	String requestName;

	/**
	 * This method get the Email Body Template from excel input file
	 * and complete them with the specific ticket data that should appear
	 */
	public void initializeEmailTemplateVars() {

		emailBodyTemplate = getSpecificValue("DataSet1", "FullText");
		requestName = getSpecificValue("DataSet1", "<Requester name>");

		emailBodyTemplate = emailBodyTemplate.replaceAll("<Requester name>", requestName);
		emailBodyTemplate = emailBodyTemplate.replaceAll("<Request Number>", ticketNumber);

	}

	// to get the ticket number from my cases view.
	String ritm_number_path;

	public void initializeTicketLabel(String prefixOfTicket) {

		ritm_number_path = "//p[contains(text(),'" + prefixOfTicket + "')][1]";

	}

	// Email Notifications
	By previewNotification = By.xpath("(//button[text()='Preview Notification'])[1]"),
			searchRecordBTN = By.xpath("//button[@id='lookup.record_picker']"),
			emailNotificationSubModule = By.xpath("//a[@id='57c7122e0a00008200e0f94513f2f7ee']"),
			emailsSubModule = By.xpath("//a[@id='8d620b63c611227b008368697b0b8d7b']"),
			caseNumber = By.xpath("//input[@id='sp_formfield_number']"),
			requestNumberField = By.xpath("//input[@id='sys_readonly.x_amspi_rco_request.number']");
	String addComments_requester = "SUS_additional_comments_added_requester",
			addComments_fulfiller = "SUS_additional_comments_added_fulfiller",
			created_onBehalf = "SUS ticket created on behalf",
			requester_replyFulfiller = "SUS requester reply fulfiller",
			requestResolved = "SUS Request Resolved",
			requestClosedSkipped = "SUS Request Closed Skipped",
			newRecord = "SUS New Record Notification",
			requestCancelled = "SUS Ticket Cancelled Notification";

	// Emails Templates
	By emailTemplateBTN = By.xpath("//button[contains(.,'Email')]"),
			moreOptions = By.xpath("//button[@id='toggleMoreOptions']");
	Map<String, String> quickMessageList = new HashMap<String, String>();

}
