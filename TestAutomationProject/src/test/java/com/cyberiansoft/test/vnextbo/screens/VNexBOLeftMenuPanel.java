package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNexBOLeftMenuPanel extends BaseWebPage {
	
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
	
	public void selectInvoicesMenu() {
		selectMenuItem(invoicesmenu, OPERATIONS_MAINMENU_ITEM);
	}
	
	public VNexBOUsersWebPage selectUsersMenu() {
		selectMenuItem(usersmenu, SETTINGS_MAINMENU_ITEM);
		return PageFactory.initElements(
				driver, VNexBOUsersWebPage.class);
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
	}
	
	public WebElement getMainMenuItem(String meinmenu) {
		return mainmenu.findElement(By.xpath(".//div[contains(text(), '" + meinmenu + "')]"));
	}
	
	public void selectMenuItem(WebElement menuitem, String mainmenuitem) {
		if (!isMainMenuExpanded(mainmenuitem))
			expandMainMenu(mainmenuitem);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(menuitem)).click();		
	}

}
