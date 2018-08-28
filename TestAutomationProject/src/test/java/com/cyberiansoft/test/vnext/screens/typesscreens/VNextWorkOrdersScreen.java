package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextWorkOrdersScreen extends VNextBaseTypeScreen {
	
	@FindBy(xpath="//div[contains(@class, 'page work-orders-list')]")
	private WebElement workordersscreen;
	
	@FindBy(xpath="//*[@data-autotests-id='work orders-list']")
	private WebElement workorderslist;
	
	@FindBy(xpath="//a[@handler='_createInvoice']")
	private WebElement createinvoicemenu;
	
	@FindBy(xpath="//*[@action='multiselect-actions-create-invoice']")
	private WebElement createinvoiceicon;
	
	public VNextWorkOrdersScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'page work-orders-list')]")));
		BaseUtils.waitABit(2000);
		if (elementExists("//div[@class='intercom-chat-dismiss-button-mobile']"))
			tap(appiumdriver.findElementByXPath("//div[@class='intercom-chat-dismiss-button-mobile']"));
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
		wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'page work-orders-list')]")));
		clearSearchField();
	}
	
	public VNextCustomersScreen clickAddWorkOrderButton() {
        clickAddButton();
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public String getFirstWorkOrderNumber() {
		return workorderslist.findElement(By.xpath(".//div[@action='select']/div[contains(@class, 'checkbox-item-title')]")).getText();
	}
	
	public VNextWorkOrdersMenuScreen clickOnWorkOrderByNumber(String wonumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='work orders-list']")));
		tap(workorderslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")));
		return new VNextWorkOrdersMenuScreen(appiumdriver);
	}
	
	public void selectWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
			tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
	}
	
	public void unselectWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") != null)
			tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
	}
	
	public boolean isWorkOrderSelected(String woNumber) {
		WebElement workordercell = getWorkOrderCell(woNumber);
		return workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked").equals("true");
	}
	
	public boolean isWorkOrderExists(String woNumber) {
		return workorderslist.findElements(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + woNumber + "']")).size() > 0;
	}
	
	public int getNumberOfSelectedWorkOrders() {
		return Integer.parseInt(workordersscreen.findElement(By.xpath(".//span[@class='selected-items-counter']")).getText());
	}
	
	public VNextHomeScreen clickBackButton() {
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
		clickScreenBackButton();
		return new VNextHomeScreen(appiumdriver);
	}
	
	public String getWorkOrderPriceValue(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		return workordercell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
	}

	public String getWorkOrderCustomerValue(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		return workordercell.findElement(By.xpath(".//div[@class='entity-item-title']")).getText();
	}
	
	public WebElement getWorkOrderCell(String wonumber) {
        return getListCell(workorderslist, wonumber);
	}
	
	public void clickCreateInvoiceFromWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		tap(workordercell.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")));
		clickCreateInvoiceMenuItem();
	}
	
	public void clickCreateInvoiceMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(createinvoicemenu));
		WaitUtils.click(createinvoicemenu);
		//tap(createinvoicemenu);
	}
	
	public void clickCreateInvoiceIcon() {
		tap(createinvoiceicon);
	}
	
	public void switchToTeamWorkordersView() {
		switchToTeamView();
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
	}

	public boolean isTeamWorkordersViewActive() {
		return isTeamViewActive();
	}
	
	public void switchToMyWorkordersView() {
		switchToMyView();

	}
	
	public boolean isMyWorkordersViewActive() {
		return isMyViewActive();
	}

	public VNextWorkOrdersScreen changeCustomerForWorkOrder(String workOrderNumber, AppCustomer newCustomer) {
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
		VNextCustomersScreen customersscreen = workOrdersMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.selectCustomer(newCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Order customer...']"));
		return this;
	}

	public VNextWorkOrdersScreen changeCustomerForWorkOrderViaSearch(String workOrderNumber, AppCustomer newCustomer) {
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
		VNextCustomersScreen customersscreen = workOrdersMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.switchToRetailMode();
		customersscreen.searchCustomerByName(newCustomer.getFullName());
		customersscreen.selectCustomer(newCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Order customer...']"));
		return this;
	}

	public VNextWorkOrdersScreen changeCustomerToWholesailForWorkOrder(String workOrderNumber, AppCustomer newWholesailCustomer) {
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
		VNextCustomersScreen customersscreen = workOrdersMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(newWholesailCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Order customer...']"));
		return this;
	}

	public void searchWorkOrderByFreeText(String searchtext) {
		searchByFreeText(searchtext);
	}
}
