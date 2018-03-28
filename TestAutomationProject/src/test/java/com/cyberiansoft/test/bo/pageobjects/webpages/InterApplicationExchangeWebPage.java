package com.cyberiansoft.test.bo.pageobjects.webpages;

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

public class InterApplicationExchangeWebPage extends WebPageWithPagination {

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboDocType_Input')]")
	private WebElement documentTypeDropDown;

	@FindBy(xpath = "//input[contains(@id, 'EditFormControl_comboEntityType_Input')]")
	private WebElement entityTypeDropDown;

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

	@FindBy(xpath = "//table[@id='ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10']/tbody/tr")
    private List<WebElement> rulesTableRows;

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
		driver.findElement(By.linkText("Add Profile")).click();
		waitForLoading();
	}

	public void fillProfileDetails(String name, String documentType, String entityType) {
		profileDetailsName.clear();
		profileDetailsName.sendKeys(name);
		documentTypeDropDown.click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals(documentType))
				.findFirst().get().click();

		waitForLoading();

		entityTypeDropDown.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals(entityType)).findFirst()
				.get().click();
		waitABit(500);
		wait.until(ExpectedConditions.elementToBeClickable(sendFromCalendarBTN)).click();
		driver.findElement(By.className("rcRow")).findElement(By.className("rcOtherMonth")).click();
		waitABit(2000);
	}

	public void fillProfileDetails(String name, String entityType) {
		profileDetailsName.clear();
		profileDetailsName.sendKeys(name);

		entityTypeDropDown.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals(entityType)).findFirst()
				.get().click();

		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		addProfileDateField.sendKeys(formatter.format(date));

		// calendsrIcon.click();
		// calendarPage.findElements(By.className("rcOtherMonth")).stream().findAny().get().click();
		waitABit(2000);
	}

	public void fillProfileDetailsEdit(String name) {
		profileDetailsNameEdit.clear();
		profileDetailsNameEdit.sendKeys(name);
	}

	public void clickProfileDetailsBox(String button) throws InterruptedException {
		if (button.equals("Cancel")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnCancel')]"))).click();
		} else if (button.equals("Insert")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'EditFormControl_btnUpdate')]"))).click();
		}
		waitForLoading();
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

	public String getFirstEntryText() {
		return firstEntry.findElements(By.tagName("td")).get(3).getText();
	}

	public boolean checkEntryByName(String name) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), '" + name + "')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public int countEntries() {
		return entriesTable.findElements(By.tagName("tbody")).get(1).findElements(By.tagName("tr")).size();
	}

	public void deleteEntry(String entryName) {
		entriesTable.findElements(By.tagName("tbody"))
                .get(1).findElements(By.tagName("tr"))
                .stream()
                .filter(e -> e.findElements(By.tagName("td"))
                        .get(3)
                        .getText()
                        .equals(entryName))
                .findFirst()
                .get()
                .findElement(By.linkText("Delete"))
                .click();

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
		;
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

}
