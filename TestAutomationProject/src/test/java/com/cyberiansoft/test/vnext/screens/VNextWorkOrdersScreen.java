package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

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
	
	public VNextWorkOrdersScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'page work-orders-list')]")));
		waitABit(1000);
	}
	
	public VNextCustomersScreen clickAddWorkOrderButton() {
		waitABit(2000);		
		tap(addwobtn);
		log(LogStatus.INFO, "Tap Add work order button");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public String getFirstWorkOrderNumber() {
		return workorderslist.findElement(By.xpath(".//div[contains(@class, 'entity-item-name')]")).getText();
	}
	
	public VNextInspectionsMenuScreen clickOnWorkOrderByNumber(String wonumber) {
		tap(workorderslist.findElement(By.xpath(".//div[contains(@class, 'entity-item-name') and text()='" + wonumber + "']")));
		log(LogStatus.INFO, "Tap on Work order: " + wonumber);
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
		return workorderslist.findElements(By.xpath(".//div[contains(@class, 'entity-item-name') and text()='" + woNumber + "']")).size() > 0;
	}
	
	public int getNumberOfSelectedWorkOrders() {
		//if (StringUtils.isNumeric(String str))
		return Integer.parseInt(workordersscreen.findElement(By.xpath(".//span[@class='selected-items-counter']")).getText());
	}
	
	public VNextHomeScreen clickBackButton() {
		clickScreenBackButton();
		log(LogStatus.INFO, "Tap Work Orders screen Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public String getWorkOrderPriceValue(String wonumber) {
		String woprice = null;
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null)
			woprice = workordercell.findElement(By.xpath(".//div[@class='entity-item-currency']")).getText();
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
		return woprice;		
	}
	
	public WebElement getWorkOrderCell(String wonumber) {
		WebElement wocell = null;
		List<WebElement> workorders = workorderslist.findElements(By.xpath(".//*[@class='entity-item accordion-item']"));
		for (WebElement workordercell : workorders)
			if (workordercell.findElements(By.xpath(".//div[contains(@class, 'entity-item-name') and text()='" + wonumber + "']")).size() > 0) {
				wocell = workordercell;
				break;
			}
		return wocell;
	}
	
	public void clickCreateInvoiceFromWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null) {
			tap(workordercell.findElement(By.xpath(".//div[contains(@class, 'entity-item-name') and text()='" + wonumber + "']")));
			log(LogStatus.INFO, "Click on Work Order: " + wonumber);
		}
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
		clickCreateInvoiceMenuItem();
		
	}
	
	public void clickCreateInvoiceMenuItem() {
		tap(createinvoicemenu);
		log(LogStatus.INFO, "Click Create Invoice menu item");
	}
	
	public void clickCreateInvoiceIcon() {
		tap(createinvoiceicon);
		log(LogStatus.INFO, "Click Create Invoice icon");
	}
	
	public void switchToTeamWorkordersView() {
		tap(teamworkorderstab);
		if (appiumdriver.findElements(By.xpath("//*[text()='Loading work orders']")).size() > 0) {
			try {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElement(By.xpath("//*[text()='Loading work orders']"))));
			} catch (NoSuchElementException e) {
				//do nothing
			}
		}
		log(LogStatus.INFO, "Switch to Team Work Orders view");
	}
	
	public boolean isTeamWorkordersViewActive() {
		return teamworkorderstab.getAttribute("class").contains("active");
	}
	
	public void switchToMyWorkordersView() {
		tap(myworkorderstab);
		log(LogStatus.INFO, "Switch to My Work Orders view");
	}
	
	public boolean isMyWorkordersViewActive() {
		return myworkorderstab.getAttribute("class").contains("active");
	}

}
