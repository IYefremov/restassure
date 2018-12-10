package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    @FindBy(xpath = "//div[@tabindex=0 and @class='left-menu__wrapper']")
    private WebElement menuButton;

    @FindBy(xpath = "//iframe[@id='embed']/following-sibling::div[5]//iframe")
    private WebElement tutorialFrame;

    @FindBy(xpath = "//button[text()='SKIP']")
    private WebElement tutorialSkipButton;

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

    public boolean isMainMenuExpanded(String mainMenu) {
        return getMainMenuItem(mainMenu).getAttribute("aria-expanded") != null;
    }

    public void expandMainMenu(String mainMenu) {
        getMainMenuItem(mainMenu).click();
        waitABit(1000);
    }

    private WebElement getMainMenuItem(String mainMenu) {
        waitLong.until(ExpectedConditions.visibilityOf(this.mainmenu));
        waitABit(1000);
        return this.mainmenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]"));
    }

    private void selectMenuItem(WebElement menuitem, String mainmenuitem) {
        try {
            driver.switchTo().frame(tutorialFrame);
            waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(tutorialSkipButton)).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        expandMainMenu(mainmenuitem);
        wait.until(ExpectedConditions.elementToBeClickable(menuitem)).click();

//        if (!menuitem.isDisplayed()) {
//            expandMainMenu(mainmenuitem);
//        }
//
//        if (!isMainMenuExpanded(mainmenuitem))
//            expandMainMenu(mainmenuitem);
//        waitABit(1000);
//        if (!menuitem.isDisplayed())
//            expandMainMenu(mainmenuitem);
    }
}