package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNexBOLeftMenuPanel extends VNextBOBaseWebPage {
	
	@FindBy(id = "mainMenu")
	private WebElement mainmenu;
	
	@FindBy(xpath = "//span[@data-id='inspections']")
	private WebElement inspectionsmenu;
	
	@FindBy(xpath = "//span[@data-id='invoices']")
	private WebElement invoicesmenu;
	
	@FindBy(xpath = "//span[@data-id='services']")
	private WebElement servicesmenu;
	
	@FindBy(xpath = "//span[@data-id='company-info']")
	private WebElement companyinfomenu;
	
	@FindBy(xpath = "//span[@data-id='users']")
	private WebElement usersmenu;
	
	private static String OPERATIONS_MAINMENU_ITEM = "Operations";
	private static String SETTINGS_MAINMENU_ITEM = "Settings";
	
	public VNexBOLeftMenuPanel(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(mainmenu));
	}
	
	public VNextBOInspectionsWebPage selectInspectionsMenu() {
		selectMenuItem(inspectionsmenu, OPERATIONS_MAINMENU_ITEM);
		return PageFactory.initElements(
				driver, VNextBOInspectionsWebPage.class);
	}
	
	public VNextBOInvoicesWebPage selectInvoicesMenu() {
		selectMenuItem(invoicesmenu, OPERATIONS_MAINMENU_ITEM);
		waitABit(4000);
		return PageFactory.initElements(
				driver, VNextBOInvoicesWebPage.class);
	}
	
	public VNexBOUsersWebPage selectUsersMenu() {
		selectMenuItem(usersmenu, SETTINGS_MAINMENU_ITEM);
		return PageFactory.initElements(
				driver, VNexBOUsersWebPage.class);
	}
	
	public boolean isUsersMenuItemExists() {
		if (!isMainMenuExpanded(SETTINGS_MAINMENU_ITEM))
			expandMainMenu(SETTINGS_MAINMENU_ITEM);
		return driver.findElement(By.xpath("//*[@data-automation-id='users']")).isDisplayed();
	}
	
	public VNexBOServicesWebPage selectServicesMenu() {
		selectMenuItem(servicesmenu, SETTINGS_MAINMENU_ITEM);
		return PageFactory.initElements(
				driver, VNexBOServicesWebPage.class);
	}
	
	public boolean isMainMenuExpanded(String meinmenu) {
		return getMainMenuItem(meinmenu).getAttribute("aria-expanded") != null;
	}
	
	public void expandMainMenu(String meinmenu) {
		getMainMenuItem(meinmenu).click();
		/*waitABit(1000);
		if (!isMainMenuExpanded(meinmenu)) {
			getMainMenuItem(meinmenu).click();
		}*/
	}
	
	public WebElement getMainMenuItem(String meinmenu) {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(mainmenu));
		waitABit(1000);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable((WebElement) mainmenu.findElement(By.xpath("./li/div[contains(text(), '" + meinmenu + "')]"))));
		return mainmenu.findElement(By.xpath(".//div[contains(text(), '" + meinmenu + "')]"));
	}
	
	public void selectMenuItem(WebElement menuitem, String mainmenuitem) {
		if (!isMainMenuExpanded(mainmenuitem))
			expandMainMenu(mainmenuitem);
		waitABit(1000);
		if (!menuitem.isDisplayed())
			expandMainMenu(mainmenuitem);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(menuitem)).click();		
	}

}
