package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
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
    private WebElement addRuleNameField;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboEntityType_Input')]")
    private WebElement addRuleEntityTypeDropDown;

	@FindBy(xpath = "//*[contains(@id, 'EditFormControl_comboIncludeType')]")
    private WebElement addRuleFilterTypeDropDOwn;

	@FindBy(xpath = "//select[@name='ctl00$ctl00$Content$Main$gvSharing$ctl00$ctl06$Detail10$ctl06$Detail10$ctl02$ctl02$EditFormControl$lbItems_helper1']")
    private WebElement addRuleUsersList;

	@FindBy(xpath = "//select[@name='ctl00$ctl00$Content$Main$gvSharing$ctl00$ctl06$Detail10$ctl06$Detail10$ctl02$ctl02$EditFormControl$lbItems_helper2']")
    private WebElement addRuleSelectedUsersList;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10")
    private WebElement rulesTable;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_tbName')]")
    private WebElement ruleNameEdit1;

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
    private WebElement addTitleButton;

	@FindBy(xpath = "//div[@class='rgEditForm  RadGrid_Default rgEditPopup']" +
            "//td[contains(text(), 'Active')]/following-sibling::td/input")
    private WebElement activeCheckbox;

    public InterApplicationExchangeWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickTab(String tabName) {
		driver.findElement(By.linkText(tabName)).click();
		waitForLoading();
	}

	public InterApplicationExchangeWebPage expandFirstCreatedCompany() {
		driver.findElement(By.className("rgExpand")).click();
		waitForLoading();
		return this;
	}

	public InterApplicationExchangeWebPage clickAddProfileButton() {
		wait.until(ExpectedConditions.elementToBeClickable(addTitleButton)).click();
		waitForLoading();
		wait.until(ExpectedConditions.visibilityOf(profileDetails));
        return this;
    }

	public InterApplicationExchangeWebPage fillProfileDetails(String name, String documentType, String entityType) {
        fillInProfileDetailsName(name);
        selectProfileDetailsDocumentType(documentType);
        selectProfileDetailsEntityType(entityType);
        selectProfileDetailsDateForToday();
        return this;
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

	public InterApplicationExchangeWebPage fillProfileDetailsEdit(String name) {
		wait.until(ExpectedConditions.visibilityOf(profileDetailsNameEdit)).clear();
		wait.until(ExpectedConditions.visibilityOf(profileDetailsNameEdit)).sendKeys(name);
        return this;
    }

	public void clickProfileDetailsBox(String button) {
		if (button.equals("Cancel")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnCancel')]"))).click();
		} else if (button.equals("Insert")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnUpdate')]"))).click();
		}
		waitForLoading();
	}

	public InterApplicationExchangeWebPage clickCancelButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
		waitForLoading();
		return this;
	}

	public InterApplicationExchangeWebPage clickInsertButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(insertButton)).click();
		waitForLoading();
		return this;
	}

	public InterApplicationExchangeWebPage clickUpdateButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();
		waitForLoading();
		return this;
	}

	public void clickProfileEditBox(String button) {
		if (button.equals("Cancel"))
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnCancel')]"))).click();
		else if (button.equals("Update"))
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnUpdate')]"))).click();
		waitForLoading();
	}

	public void clickEditFirstEntry() {
		driver.findElement(By.className("btn-edit")).click();
		waitForLoading();
	}

	public InterApplicationExchangeWebPage clickEditEntry(String entry) {
		wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//td[contains(text(), '" + entry + "')]/../td[@class='btn-edit']")))).click();
		waitForLoading();
        return this;
    }

	public boolean isEntryActive(String entry) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(driver
                    .findElement(By.xpath("//td[contains(text(), '" + entry + "')]/../td[text()='Active']")))).isDisplayed();
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

    public InterApplicationExchangeWebPage verifyCompanyDoesNotExist(String company) {
        while (isCompanyDisplayed(company)) {
            deleteEntry(company);
        }
        return this;
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

	public void clickAddRuleToFirstProfile() throws InterruptedException {
		Thread.sleep(2000);
		entriesTable.findElement(By.xpath(".//table[@class='rgDetailTable detail-table']")).findElement(By.xpath(".//input[contains(@id, 'AddNewRecordButton')]")).click();
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'AddNewRecordButton')]"))).click();
		waitForLoading();
	}

	public void fillFilterRuleBox(String name, String entityType, String filterType) {
		addRuleNameField.clear();
		addRuleNameField.sendKeys(name);

		addRuleEntityTypeDropDown.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@id, 'EditFormControl_comboEntityType_DropDown')]/div/ul"))).findElements(By.tagName("li"))
				.stream().filter(e -> e.getText().equals(entityType)).findFirst().get().click();

		waitForLoading();
		waitABit(2000);
		if (filterType.equals("Include Selected"))
			new Select(driver.findElement(
					By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl02_EditFormControl_comboIncludeType")))
							.selectByIndex(1);
	}

	public void fillFilterRuleBox(String name, String filterType) {
		addRuleNameField.clear();
		addRuleNameField.sendKeys(name);

		waitForLoading();

		if (filterType.equals("Include Selected"))
			new Select(driver.findElement(
					By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl02_EditFormControl_comboIncludeType")))
							.selectByIndex(1);
		;
	}

	public void selectUsersWhileCreatingRule(int usersToAdd) throws InterruptedException {
		List<WebElement> allUsers = addRuleUsersList.findElements(By.tagName("option"));
		int allUsersCount = allUsers.size();
		if (allUsersCount >= usersToAdd) {
			for (int i = 0; i < usersToAdd; i++) {
				addRuleUsersList.findElements(By.tagName("option")).get(0).click();
				for (int j = 0; j < 5; j++) {
					Thread.sleep(100);
					try {
						if (addRuleSelectedUsersList.findElements(By.tagName("options")).size() == i + 1)
							break;
					} catch (Exception e) {
					}
				}
			}
		}
	}

	public void clickAddRuleBox(String button) throws InterruptedException {
		if (button.equals("Cancel"))
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnCancel')]"))).click();
		else if (button.equals("Insert"))
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnUpdate')]"))).click();
		Thread.sleep(5000);
		waitForLoading();
	}

	public boolean checkRuleByName(String name) throws InterruptedException {
		waitABit(4000);
		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + name + "')]")));
			Thread.sleep(2000);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getRuleNameByNumber(int number) {
		waitABit(2000);
		if (number > 0 && number  <= rulesTableRows.size()) {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElements(rulesTableRows));
                return driver
                        .findElement(By.xpath("//table[@id='ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_" +
                                "ctl06_Detail10']/tbody/tr[" + number + "]/td[3]")).getText();
            } catch (TimeoutException ignored) {}
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

	public void fillRuleBoxEdit(String name) {
		wait.until(ExpectedConditions.elementToBeClickable(ruleNameEdit1)).clear();
		ruleNameEdit1.sendKeys(name);
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

	public InterApplicationExchangeWebPage clickSendingTab() {
	    wait.until(ExpectedConditions.elementToBeClickable(sendingTab)).click();
	    waitForLoading();
	    return this;
    }

	public InterApplicationExchangeWebPage clickActiveCheckBox() {
	    wait.until(ExpectedConditions.elementToBeClickable(activeCheckbox)).click();
	    waitABit(500);
	    return this;
    }
}
