package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNexBOLeftMenuPanel extends VNextBOBaseWebPage {

    @FindBy(id = "mainMenu")
    private WebElement mainmenu;

    @FindBy(xpath = "//*[@data-automation-id='inspections']")
    private WebElement inspectionsmenu;

    @FindBy(xpath = "//*[@data-automation-id='invoices']")
    private WebElement invoicesmenu;

    @FindBy(xpath = "//*[@data-automation-id='services']")
    private WebElement servicesmenu;

    @FindBy(xpath = "//*[@data-automation-id='quick-notes']")
    private WebElement quickNotesMenu;

    @FindBy(xpath = "//*[@data-automation-id='company-info']")
    private WebElement companyinfomenu;

    @FindBy(xpath = "//*[@data-automation-id='users']")
    private WebElement usersmenu;

    @FindBy(xpath = "//li[@data-automation-id='orders']")
    private WebElement repairordersmenu;

    @FindBy(xpath = "//div[@class='left-menu-btn closed']")
    private WebElement closedMenuButton;

    @FindBy(xpath = "//div[@class='left-menu-btn']")
    private WebElement openedMenuButton;

    @FindBy(id = "menuBtn")
    private WebElement menuButton;

    @FindBy(xpath = "//div[@class='WFSTACB']//button[text()='SKIP']")
    private List<WebElement> welcomePopup;

    private static String MONITOR_MAINMENU_ITEM = "Monitor";
    private static String OPERATIONS_MAINMENU_ITEM = "Operations";
    private static String SETTINGS_MAINMENU_ITEM = "Settings";

    public VNexBOLeftMenuPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

    public VNextBORepairOrdersWebPage selectRepairOrdersMenu() {
        selectMenuItem(repairordersmenu, MONITOR_MAINMENU_ITEM);
        return PageFactory.initElements(
                driver, VNextBORepairOrdersWebPage.class);
    }

    public boolean isUsersMenuItemExists() {
        if (!isMainMenuExpanded(SETTINGS_MAINMENU_ITEM))
            expandMainMenu(SETTINGS_MAINMENU_ITEM);
        return driver.findElement(By.xpath("//*[@data-automation-id='users']")).isDisplayed();
    }

    public VNextBOServicesWebPage selectServicesMenu() {
        selectMenuItem(servicesmenu, SETTINGS_MAINMENU_ITEM);
        waitForLoading();
        return PageFactory.initElements(
                driver, VNextBOServicesWebPage.class);
    }

    public VNextBOQuickNotesWebPage selectQuickNotesMenu() {
        selectMenuItem(quickNotesMenu, SETTINGS_MAINMENU_ITEM);
        waitForLoading();
        return PageFactory.initElements(
                driver, VNextBOQuickNotesWebPage.class);
    }

    public boolean isMainMenuExpanded(String meinmenu) {
        return getMainMenuItem(meinmenu).getAttribute("aria-expanded") != null;
    }

    public void expandMainMenu(String meinmenu) {
        getMainMenuItem(meinmenu).click();
        waitABit(1000);
		/*if (!isMainMenuExpanded(meinmenu)) {
			getMainMenuItem(meinmenu).click();
		}*/
    }

    public WebElement getMainMenuItem(String meinmenu) {
        waitLong.until(ExpectedConditions.visibilityOf(mainmenu));
        waitABit(1000);
        try {
            waitLong
                    .until(ExpectedConditions.elementToBeClickable(mainmenu
                            .findElement(By.xpath("./li/div[contains(text(), '" + meinmenu + "')]"))));
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(closedMenuButton)).click();
            wait.until(ExpectedConditions.visibilityOf(openedMenuButton));
        }
        return mainmenu.findElement(By.xpath(".//div[contains(text(), '" + meinmenu + "')]"));
    }

    private void selectMenuItem(WebElement menuitem, String mainmenuitem) {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(menuitem)).click();
        if (!menuitem.isDisplayed()) {
            expandMainMenu(mainmenuitem);
        }
//        if (!isMainMenuExpanded(mainmenuitem))
//            expandMainMenu(mainmenuitem);
//        waitABit(1000);
//        if (!menuitem.isDisplayed())
//            expandMainMenu(mainmenuitem);
//        wait.until(ExpectedConditions.elementToBeClickable(menuitem)).click();
    }
}
