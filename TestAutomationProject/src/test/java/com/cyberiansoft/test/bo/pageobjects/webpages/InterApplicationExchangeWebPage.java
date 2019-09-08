package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class InterApplicationExchangeWebPage extends WebPageWithPagination {

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboDocType_Input')]")
	private ComboBox documentTypeCombobox;

	@FindBy(xpath = "//div[contains(@id, 'EditFormControl_comboDocType_DropDown')]")
	private DropDown documentTypeDropDown;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboEntityType_Input')]")
	private ComboBox entityTypeCombobox;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboEntityType_Input') and @disabled='disabled']")
	private WebElement entityTypeDisabled;

	@FindBy(xpath = "//div[contains(@id, 'EditFormControl_comboEntityType_DropDown')]")
	private DropDown entityTypeDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10__0:0_0")
	private WebElement firstEntry;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_tbName')]")
	private WebElement profileDetailsName;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_tbName')]")
	private WebElement profileDetailsNameEdit;

	@FindBy(xpath = "//table[@class='rgDetailTable detail-table']")
	private WebElement entriesTable;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_tbName')]")
	private WebElement ruleNameField;

	@FindBy(xpath = "//select[contains(@name, 'EditFormControl$lbItems_helper1')]")
	private WebElement addRuleUnselectedUsersList;

	@FindBy(xpath = "//select[contains(@name, 'EditFormControl$lbItems_helper2')]")
	private WebElement addRuleSelectedUsersList;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10")
	private WebElement rulesTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_popupButton")
	private WebElement sendFromCalendarBTN;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_popupButton")
	private WebElement calendsrIcon;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_calendar_Top")
	private WebElement calendarPage;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_dateInput")
	private WebElement addProfileDateField;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_dateInput_ClientState")
	private WebElement addProfileDateFieldAttr;

	@FindBy(xpath = "//table[@id='ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10']/tbody/tr")
	private List<WebElement> rulesTableRows;

	@FindBy(xpath = "//span[text()='Sending']")
	private WebElement sendingTab;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_ctl00")
	private WebElement profileDetails;

	@FindBy(className = "rcbItem")
	private List<WebElement> itemsList;

	@FindBy(xpath = "//input[@value='Cancel']")
	private WebElement cancelButton;

	@FindBy(xpath = "//input[@value='Insert']")
	private WebElement insertButton;

	@FindBy(xpath = "//input[@value='Update']")
	private WebElement updateButton;

	@FindBy(xpath = "//input[@title='Add Profile']")
	private WebElement addProfileButton;

	@FindBy(xpath = "//div[@class='rgEditForm  RadGrid_Default rgEditPopup']" +
			"//td[contains(text(), 'Active')]/following-sibling::td/input")
	private WebElement activeCheckbox;

	@FindBy(xpath = "//input[@title='Add Rule']")
	private WebElement addRuleButton;

	@FindBy(xpath = "//div[@class='rgEditForm  RadGrid_Default rgEditPopup']")
	private WebElement ruleDialog;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboEntityType_Input')]")
	private WebElement ruleEntityType;

	@FindBy(xpath = "//div[contains(@id, 'EditFormControl_comboEntityType_DropDown')]/..")
	private WebElement ruleEntityTypeDropDown;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboEntityType_Input')]")
	private WebElement ruleEntityTypeInput;

	@FindBy(xpath = "//select[contains(@id, 'EditFormControl_comboIncludeType')]")
	private WebElement ruleFilterType;

	public InterApplicationExchangeWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickTab(String tabName) {
		driver.findElement(By.linkText(tabName)).click();
		waitForLoading();
	}

	public void expandFirstCreatedCompany() {
		driver.findElement(By.className("rgExpand")).click();
		waitForLoading();
	}

	public void clickAddProfileButton() {
		wait.until(ExpectedConditions.elementToBeClickable(addProfileButton)).click();
		waitForLoading();
		wait.until(ExpectedConditions.visibilityOf(profileDetails));
	}

	public void fillProfileDetails(String name, String documentType, String entityType) {
		fillInProfileDetailsName(name);
		selectProfileDetailsDocumentType(documentType);
		selectProfileDetailsEntityType(entityType);
		selectProfileDetailsDateForToday();
	}

	private void fillInProfileDetailsName(String name) {
		wait.until(ExpectedConditions.visibilityOf(profileDetailsName)).clear();
		wait.until(ExpectedConditions.elementToBeClickable(profileDetailsName)).sendKeys(name);
	}

	private void selectProfileDetailsDocumentType(String documentType) {
		selectComboboxValue(documentTypeCombobox, documentTypeDropDown, documentType);
		waitForLoading();
	}

	private void selectProfileDetailsEntityType(String entityType) {
		selectComboboxValue(entityTypeCombobox, entityTypeDropDown, entityType);
	}

	private void selectProfileDetailsDateForToday() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		wait.until(ExpectedConditions.visibilityOf(addProfileDateField)).sendKeys(formatter.format(date));
	}

	public void fillProfileDetails(String name, String entityType) {
		fillInProfileDetailsName(name);
		selectProfileDetailsEntityType(entityType);
		selectProfileDetailsDateForToday();
	}

	public void fillProfileDetailsEdit(String name) {
		wait.until(ExpectedConditions.visibilityOf(profileDetailsNameEdit)).clear();
		wait.until(ExpectedConditions.visibilityOf(profileDetailsNameEdit)).sendKeys(name);
	}

	public void clickProfileDetailsBox(String button) {
		if (button.equals("Cancel")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnCancel')]"))).click();
		} else if (button.equals("Insert")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnUpdate')]"))).click();
		}
		waitForLoading();
	}

	public void clickCancelButton() {
		wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
		waitForLoading();
	}

	public void clickInsertButton() {
		wait.until(ExpectedConditions.elementToBeClickable(insertButton)).click();
		waitForLoading();
	}

	public void clickUpdateButton() {
		wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();
		waitForLoading();
	}

	public void clickProfileEditBox(String button) {
		if (button.equals("Cancel"))
			wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
		else if (button.equals("Update"))
			wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();
		waitForLoading();
	}

	public void clickEditFirstEntry() {
		driver.findElement(By.className("btn-edit")).click();
		waitForLoading();
	}

	public void clickEditEntry(String entry) {
		wait.until(ExpectedConditions.elementToBeClickable(driver
				.findElement(By.xpath("//td[contains(text(), '" + entry + "')]/../td[@class='btn-edit']")))).click();
		waitForLoading();
	}

	public boolean isEntryActive(String entry) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(driver
					.findElement(By.xpath("//td[contains(text(), '" + entry + "')]/../td[text()='Active']")))).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isRuleDisplayed(String ruleName) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(driver
					.findElement(By.xpath("//td[contains(text(), '" + ruleName + "')]")))).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getFirstEntryText() {
		return firstEntry.findElements(By.tagName("td")).get(3).getText();
	}

	public boolean isCompanyDisplayed(String name) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), '" + name + "')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void verifyCompanyDoesNotExist(String company) {
		while (isCompanyDisplayed(company)) {
			deleteEntry(company);
		}
	}

	public void deleteEntry(String entryName) {
		entriesTable.findElements(By.tagName("tbody"))
				.get(1).findElements(By.tagName("tr"))
				.stream()
				.filter(e -> e.findElements(By.tagName("td"))
						.get(3)
						.getText()
						.contains(entryName))
				.findFirst()
				.ifPresent(e -> e.findElement(By.linkText("Delete")).click());
		driver.switchTo().alert().accept();
		waitForLoading();
	}

	public void expandFirstCompanyProfile() {
		entriesTable.findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr"))
				.findElement(By.className("rgExpandCol")).click();
		waitForLoading();
	}

	public void clickAddRuleToFirstProfile() {
		waitABit(2000);
		wait.until(ExpectedConditions.elementToBeClickable(entriesTable))
				.findElement(By.xpath(".//table[@class='rgDetailTable detail-table']")).findElement(By
				.xpath(".//input[contains(@id, 'AddNewRecordButton')]")).click();
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'AddNewRecordButton')]"))).click();
		waitForLoading();
	}

	public void fillRuleBox(String name, String entityType, String filterType) {
		fillAddRuleName(name);
		selectRuleEntityType(entityType);
		selectRuleFilterType(filterType);
	}

	public void fillRuleBox(String name, String filterType) {
		fillAddRuleName(name);
		selectRuleFilterType(filterType);
	}

	private void fillAddRuleName(String name) {
		wait.until(ExpectedConditions.visibilityOf(ruleNameField)).clear();
		wait.until(ExpectedConditions.visibilityOf(ruleNameField)).sendKeys(name);
	}

	private void selectRuleEntityType(String entityType) {
		wait.until(ExpectedConditions.elementToBeClickable(ruleEntityType)).click();
		wait.until(ExpectedConditions.attributeContains(
				ruleEntityTypeDropDown, "style", "display: block;"));
		ruleEntityTypeDropDown.findElements(By.xpath(".//li"))
				.stream()
				.filter(e -> e.getText().equals(entityType))
				.findFirst()
				.ifPresent(WebElement::click);
		waitForLoading();
	}

	private void selectRuleFilterType(String filterType) {
		wait.until(ExpectedConditions.elementToBeClickable(ruleFilterType));
		new Select(ruleFilterType).selectByVisibleText(filterType);
	}

	public void selectUsersWhileCreatingRule(int usersToAdd) {
		Select unselectedUsersSelection = new Select(addRuleUnselectedUsersList);
		Select selectedUsersSelection = new Select(addRuleSelectedUsersList);
		while (unselectedUsersSelection.getOptions().size() > 0 && usersToAdd != 0) {
			int selectedSize = selectedUsersSelection.getOptions().size();
			unselectedUsersSelection.selectByIndex(RandomUtils.nextInt(0, unselectedUsersSelection.getOptions().size()));
			if (!getBrowserType().equalsIgnoreCase("Edge")) {
				try {
					wait.until(driver -> selectedUsersSelection.getOptions().size() != selectedSize);
				} catch (Exception ignored) {
				}
			} else {
				waitABit(2000);
			}
			Assert.assertEquals(selectedSize + 1, selectedUsersSelection.getOptions().size(),
					"The user has not been selected");
			usersToAdd--;
		}
	}

	public int getNumberOfUnselectedUsers() {
		return new Select(addRuleUnselectedUsersList).getOptions().size();
	}

	public void clickAddRuleBox(String button) {
		if (button.equals("Cancel"))
			wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
		else if (button.equals("Insert"))
			wait.until(ExpectedConditions.elementToBeClickable(insertButton)).click();
		waitABit(5000);
		waitForLoading();
	}

	public boolean checkRuleByName(String name) {
		waitABit(4000);
		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + name + "')]")));
			waitABit(2000);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getRuleNameByNumber(int number) {
		waitABit(2000);
		if (number > 0 && number <= rulesTableRows.size()) {
			try {
				wait.until(ExpectedConditions.visibilityOfAllElements(rulesTableRows));
				return driver
						.findElement(By.xpath("//table[@id='ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_" +
								"ctl06_Detail10']/tbody/tr[" + number + "]/td[3]")).getText();
			} catch (TimeoutException ignored) {
			}
		} else {
			Assert.fail("The ordinary number of the rule should be within the bounds of the rules table rows!");
		}
		return null;
	}

	public void clickEditRuleBox(String button) {
		if (button.equals("Cancel"))
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnCancel')]"))).click();
		else if (button.equals("Update"))
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnUpdate')]"))).click();
		waitForLoading();
	}

	public void editRuleBoxName(String newRuleName) {
		wait.until(ExpectedConditions.elementToBeClickable(ruleNameField)).clear();
		wait.until(ExpectedConditions.elementToBeClickable(ruleNameField)).sendKeys(newRuleName);
	}

	public void deleteRule(String ruleName) {
		rulesTable.findElements(By.tagName("tbody")).get(1).findElements(By.tagName("tr")).stream()
				.filter(e -> e.findElements(By.tagName("td")).get(2).getText().equals(ruleName)).findFirst().get()
				.findElement(By.linkText("Delete")).click();

		driver.switchTo().alert().accept();
		waitForLoading();
	}

	public void editRule(String ruleName) {
		rulesTable.findElements(By.tagName("tbody")).get(1).findElements(By.tagName("tr")).stream()
				.filter(e -> e.findElements(By.tagName("td")).get(2).getText().equals(ruleName)).findFirst().get()
				.findElement(By.linkText("Edit")).click();
		waitForLoading();
	}

	public void expandCompanyProfile(String companyName) {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[contains(text(), '" +
				companyName + "')]/..//input[@title='Expand']")))).click();
		waitForLoading();
	}

	public void clickAddRule() {
		wait.until(ExpectedConditions.elementToBeClickable(addRuleButton)).click();
		waitForLoading();
		try {
			wait.until(ExpectedConditions.visibilityOf(ruleDialog));
		} catch (Exception e) {
			Assert.fail("The rule dialog has not been displayed!");
		}
	}

	public void clickEditRule(String ruleName) {
		wait.until(ExpectedConditions.elementToBeClickable(driver
				.findElement(By.xpath("//td[contains(text(), '" + ruleName + "')]/../td[@class='btn-edit']"))))
				.click();
		waitForLoading();
		Assert.assertTrue(isEditDialogDisplayed(),
				"The rule dialog has not been displayed after clicking the \"Edit Rule\" button");
	}

	private boolean isEditDialogDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(ruleDialog));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickSendingTab() {
		wait.until(ExpectedConditions.elementToBeClickable(sendingTab)).click();
		waitForLoading();
	}

	public void clickActiveCheckBox() {
		wait.until(ExpectedConditions.elementToBeClickable(activeCheckbox)).click();
		waitABit(500);
	}

	public boolean isEntityTypeDisabled() {
		try {
			wait.until(ExpectedConditions.visibilityOf(entityTypeDisabled));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
