package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextWorkOrdersScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[contains(@class, 'page work-orders-list')]")
	private WebElement workordersscreen;
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addwobtn;
	
	@FindBy(xpath="//*[@data-autotests-id='work orders-list']")
	private WebElement workorderslist;
	
	@FindBy(xpath="//a[@handler='_createInvoice']")
	private WebElement createinvoicemenu;
	
	@FindBy(xpath="//*[@action='multiselect-actions-create-invoice']")
	private WebElement createinvoiceicon;
	
	@FindBy(xpath="//*[@action='my']")
	private WebElement myworkorderstab;
	
	@FindBy(xpath="//*[@action='team']")
	private WebElement teamworkorderstab;
	
	public VNextWorkOrdersScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 35);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'page work-orders-list')]")));
		BaseUtils.waitABit(2000);
		if (elementExists("//div[@class='intercom-chat-dismiss-button-mobile']"))
			tap(appiumdriver.findElementByXPath("//div[@class='intercom-chat-dismiss-button-mobile']"));
	}
	
	public VNextCustomersScreen clickAddWorkOrderButton() {
		tap(addwobtn);
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public String getFirstWorkOrderNumber() {
		return workorderslist.findElement(By.xpath(".//div[@action='select']/div[contains(@class, 'checkbox-item-title')]")).getText();
	}
	
	public VNextInspectionsMenuScreen clickOnWorkOrderByNumber(String wonumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='work orders-list']")));
		tap(workorderslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")));
		return new VNextInspectionsMenuScreen(appiumdriver);
	}
	
	public void selectWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null)
			if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
				tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
	}
	
	public void unselectWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null)
			if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") != null)
				tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
	}
	
	public boolean isWorkOrderSelected(String woNumber) {
		boolean selected = false;
		WebElement workordercell = getWorkOrderCell(woNumber);
		if (workordercell != null)
			selected = workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked").equals("true");
		else
			Assert.assertTrue(false, "Can't find work order: " + woNumber);
		return selected;
	}
	
	public boolean isWorkOrderExists(String woNumber) {
		return workorderslist.findElements(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + woNumber + "']")).size() > 0;
	}
	
	public int getNumberOfSelectedWorkOrders() {
		//if (StringUtils.isNumeric(String str))
		return Integer.parseInt(workordersscreen.findElement(By.xpath(".//span[@class='selected-items-counter']")).getText());
	}
	
	public VNextHomeScreen clickBackButton() {
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
		//new WebDriverWait(appiumdriver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[text()='Loading work orders']")));
		clickScreenBackButton();
		return new VNextHomeScreen(appiumdriver);
	}
	
	public String getWorkOrderPriceValue(String wonumber) {
		String woprice = null;
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null)
			woprice = workordercell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
		return woprice;		
	}

	public String getWorkOrderCustomerValue(String wonumber) {
		String woprice = null;
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null)
			woprice = workordercell.findElement(By.xpath(".//div[@class='entity-item-title']")).getText();
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
		return woprice;
	}
	
	public WebElement getWorkOrderCell(String wonumber) {
		WebElement wocell = null;
		List<WebElement> workorders = workorderslist.findElements(By.xpath(".//*[@class='entity-item accordion-item']"));
		for (WebElement workordercell : workorders)
			if (workordercell.findElements(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")).size() > 0) {
				wocell = workordercell;
				break;
			}
		return wocell;
	}
	
	public void clickCreateInvoiceFromWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null) {
			tap(workordercell.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")));
		}
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
		clickCreateInvoiceMenuItem();
		
	}
	
	public void clickCreateInvoiceMenuItem() {
		tap(createinvoicemenu);
	}
	
	public void clickCreateInvoiceIcon() {
		tap(createinvoiceicon);
	}
	
	public void switchToTeamWorkordersView() {
		tap(WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@action='team']")));
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
	}

	public boolean isTeamWorkordersViewActive() {
		return teamworkorderstab.getAttribute("class").contains("active");
	}
	
	public void switchToMyWorkordersView() {
		tap(myworkorderstab);
	}
	
	public boolean isMyWorkordersViewActive() {
		return myworkorderstab.getAttribute("class").contains("active");
	}

	public VNextWorkOrdersScreen changeCustomerForWorkOrder(String workOrderNumber, AppCustomer newCustomer) {
		VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
		VNextCustomersScreen customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.selectCustomer(newCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		return this;
	}

	public VNextWorkOrdersScreen changeCustomerForWorkOrderViaSearch(String workOrderNumber, AppCustomer newCustomer) {
		VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
		VNextCustomersScreen customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.switchToRetailMode();
		customersscreen.searchCustomerByName(newCustomer.getFullName());
		customersscreen.selectCustomer(newCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		return this;
	}

	public VNextWorkOrdersScreen changeCustomerToWholesailForWorkOrder(String workOrderNumber, AppCustomer newWholesailCustomer) {
		VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
		VNextCustomersScreen customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(newWholesailCustomer);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
		return this;
	}
}
