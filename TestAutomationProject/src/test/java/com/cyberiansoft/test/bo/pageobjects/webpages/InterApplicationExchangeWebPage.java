package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class InterApplicationExchangeWebPage extends WebPageWithPagination {

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_comboDocType_Input")
	WebElement documentTypeDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_comboEntityType_Input")
	WebElement entityTypeDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10__0:0_0")
	WebElement firstEntry;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_tbName")
	WebElement profileDetailsName;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl05_EditFormControl_tbName")
	WebElement profileDetailsNameEdit;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10")
	WebElement entriesTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl02_EditFormControl_tbName")
	WebElement addRuleNameField;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl02_EditFormControl_comboEntityType_Input")
	WebElement addRuleEntityTypeDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl02_EditFormControl_comboIncludeType")
	WebElement addRuleFilterTypeDropDOwn;

	@FindBy(xpath = "//select[@name='ctl00$ctl00$Content$Main$gvSharing$ctl00$ctl06$Detail10$ctl06$Detail10$ctl02$ctl02$EditFormControl$lbItems_helper1']")
	WebElement addRuleUsersList;

	@FindBy(xpath = "//select[@name='ctl00$ctl00$Content$Main$gvSharing$ctl00$ctl06$Detail10$ctl06$Detail10$ctl02$ctl02$EditFormControl$lbItems_helper2']")
	WebElement addRuleSelectedUsersList;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10")
	WebElement rulesTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl07_EditFormControl_tbName")
	WebElement ruleNameEdit1;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl05_EditFormControl_tbName")
	WebElement ruleNameEdit2;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_popupButton")
	WebElement sendFromCalendarBTN;
	
	@FindBy(id ="ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_popupButton")
	WebElement calendsrIcon;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_dpCopyOrderDate_calendar_Top")
	WebElement calendarPage;

	public InterApplicationExchangeWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickTab(String tabName) throws InterruptedException {
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

	public void fillProfileDetails(String name, String documentType, String entityType) throws InterruptedException {
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
		
		sendFromCalendarBTN.click();
		driver.findElement(By.className("rcRow")).findElement(By.className("rcOtherMonth")).click();
		Thread.sleep(2000);
	}
	
	public void fillProfileDetails(String name, String entityType) throws InterruptedException {
		profileDetailsName.clear();
		profileDetailsName.sendKeys(name);

		entityTypeDropDown.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals(entityType)).findFirst()
				.get().click();
		
		calendsrIcon.click();
		calendarPage.findElements(By.className("rcOtherMonth")).stream().findAny().get().click();
		Thread.sleep(2000);
	}

	public void fillProfileDetailsEdit(String name) {
		profileDetailsNameEdit.clear();
		profileDetailsNameEdit.sendKeys(name);
	}

	public void clickProfileDetailsBox(String button) throws InterruptedException {
		if (button.equals("Cancel")){
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_btnCancel"))).click();
			}catch(Exception e){
				Thread.sleep(2000);
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_btnCancel"))).click();
			}
		}
		else if (button.equals("Insert")){	
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_btnUpdate"))).click();
			}catch(Exception e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl02_ctl02_EditFormControl_btnUpdate"))).click();
				Thread.sleep(2000);
			}
		}
		else
			Assert.assertTrue(false, "Wrong button");
		waitForLoading();
	}

	public void clickProfileEditBox(String button) {
		if (button.equals("Cancel"))
			driver.findElement(
					By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl05_EditFormControl_btnCancel"))
					.click();
		else if (button.equals("Update"))
			driver.findElement(
					By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl05_EditFormControl_btnUpdate"))
					.click();
		else
			Assert.assertTrue(false, "Wrong button");
		waitForLoading();
	}

	public void clickEditFirstEntry() {
		driver.findElement(By.className("btn-edit")).click();
		waitForLoading();
	}

	public String getFirstEntryText() {
		return firstEntry.findElements(By.tagName("td")).get(3).getText();
	}

	public boolean checkEntryByName(String name) throws InterruptedException {
		try {
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), '" + name + "')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public int countEntries() {
		return entriesTable.findElements(By.tagName("tbody")).get(1).findElements(By.tagName("tr")).size();
	}

	public void deleteEnty(String entryName) {
		entriesTable.findElements(By.tagName("tbody")).get(1).findElements(By.tagName("tr")).stream()
				.filter(e -> e.findElements(By.tagName("td")).get(3).getText().equals(entryName)).findFirst().get()
				.findElement(By.linkText("Delete")).click();

		driver.switchTo().alert().accept();
		waitForLoading();
	}

	public void expandFirstCompanyProfile() {
		entriesTable.findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr"))
				.findElement(By.className("rgExpandCol")).click();
		waitForLoading();
	}

	public void clickAddRuleToFirstProfile() {
		driver.findElement(
				By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl00_AddNewRecordButton"))
				.click();
		waitForLoading();
	}

	public void fillFilterRuleBox(String name, String entityType, String filterType) {
		addRuleNameField.clear();
		addRuleNameField.sendKeys(name);

		addRuleEntityTypeDropDown.click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(entityType)).findFirst()
				.get().click();
	

		waitForLoading();

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

	public void clickAddRuleBox(String button) {
		if (button.equals("Cancel"))
			driver.findElement(
					By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl02_EditFormControl_btnCancel"))
					.click();
		else if (button.equals("Insert"))
			driver.findElement(
					By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl02_ctl02_EditFormControl_btnUpdate"))
					.click();
		else
			Assert.assertTrue(false, "Wrong button");
		waitForLoading();
	}

	public boolean checkRuleByName(String name) throws InterruptedException {
		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + name + "')]")));
			Thread.sleep(2000);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void clickEditRuleBox(String button) {
		if (button.equals("Cancel")) {
			try {
				driver.findElement(
						By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl05_EditFormControl_btnCancel"))
						.click();
			} catch (Exception e) {
				driver.findElement(
						By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl07_EditFormControl_btnCancel"))
						.click();
			}
		} else if (button.equals("Update")) {
			try {
				driver.findElement(
						By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl05_EditFormControl_btnUpdate"))
						.click();
			} catch (Exception e) {
				driver.findElement(
						By.id("ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl07_EditFormControl_btnUpdate"))
						.click();
			}
		} else
			Assert.assertTrue(false, "Wrong button");
		waitForLoading();
	}

	public void fillRuleBoxEdit(String name) throws InterruptedException {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(
					"ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl07_EditFormControl_tbName")));
			ruleNameEdit1.clear();
			ruleNameEdit1.sendKeys(name);
		} catch (TimeoutException e) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(
					"ctl00_ctl00_Content_Main_gvSharing_ctl00_ctl06_Detail10_ctl06_Detail10_ctl05_EditFormControl_tbName")));
			ruleNameEdit2.clear();
			ruleNameEdit2.sendKeys(name);
		}
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
