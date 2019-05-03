package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class VNexBOLeftMenuPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@id='mainMenu']")
    private WebElement mainmenu;

    @FindBy(id = "menuBtn")
    private WebElement menuButton;

//    @FindBy(xpath = "//body[contains(@class, 'body-mobile--scroll-hidden')]")
//    private WebElement body;

//    @FindBy(xpath = "//body[contains(@class, 'body')]")
    @FindBy(xpath = "//div[@id='page-wrapper']/parent::body")
    private WebElement body;

    @FindBy(xpath = "//*[@data-automation-id='inspections']")
    private WebElement inspectionsMenu;

    @FindBy(xpath = "//*[@data-automation-id='invoices']")
    private WebElement invoicesMenu;

    @FindBy(xpath = "//li[@data-automation-id='parts']")
    private WebElement partsManagementMenu;

    @FindBy(xpath = "//*[@data-automation-id='services']")
    private WebElement servicesMenu;

    @FindBy(xpath = "//*[@data-automation-id='quick-notes']")
    private WebElement quickNotesMenu;

    @FindBy(xpath = "//*[@data-automation-id='company-info']")
    private WebElement companyInfoMenu;

    @FindBy(xpath = "//*[@data-automation-id='users']")
    private WebElement usersMenu;

    @FindBy(xpath = "//li[@data-automation-id='orders']")
    private WebElement repairOrdersMenu;

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
        selectMenuItem(inspectionsMenu, OPERATIONS_MAINMENU_ITEM);
        return PageFactory.initElements(
                driver, VNextBOInspectionsWebPage.class);
    }

    public VNextBOInvoicesWebPage selectInvoicesMenu() {
        selectMenuItem(invoicesMenu, OPERATIONS_MAINMENU_ITEM);
        waitABit(4000);
        return PageFactory.initElements(
                driver, VNextBOInvoicesWebPage.class);
    }

    public VNextBOPartsManagementWebPage selectPartsManagementMenu() {
        selectMenuItem(partsManagementMenu, OPERATIONS_MAINMENU_ITEM);
        waitABit(4000);
        return PageFactory.initElements(
                driver, VNextBOPartsManagementWebPage.class);
    }

    public VNexBOUsersWebPage selectUsersMenu() {
        selectMenuItem(usersMenu, SETTINGS_MAINMENU_ITEM);
        return PageFactory.initElements(
                driver, VNexBOUsersWebPage.class);
    }

    public VNextBORepairOrdersWebPage selectRepairOrdersMenu() {
        selectMenuItem(repairOrdersMenu, MONITOR_MAINMENU_ITEM);
        return PageFactory.initElements(
                driver, VNextBORepairOrdersWebPage.class);
    }

    public VNextBOServicesWebPage selectServicesMenu() {
        selectMenuItem(servicesMenu, SETTINGS_MAINMENU_ITEM);
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

    public boolean isUsersMenuItemExists() {
        if (!isMainMenuExpanded()) {
            expandMainMenu();
        }
        return driver.findElement(By.xpath("//*[@data-automation-id='users']")).isDisplayed();
    }

    public boolean isMainMenuExpanded() {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.visibilityOf(body));
        try {
            return wait.until(ExpectedConditions.attributeContains(body, "class", "left-menu--open"));
        } catch (Exception ignored) {
            return false;
        }
    }

    public void expandMainMenu() {
        if (!isMainMenuExpanded()) {
            wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
            wait.until(ExpectedConditions.attributeContains(body, "class", "left-menu--open"));
        }
    }

    public void collapseMainMenu() {
        if (isMainMenuExpanded()) {
            wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
            wait.until(ExpectedConditions.attributeToBe(body, "class", "body-mobile--scroll-hidden"));
        }
    }

    private void clickMainMenuItem(String mainMenu) {
        try {
            wait.until(ExpectedConditions
                    .elementToBeClickable(mainmenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]"))))
                    .click();
        } catch (Exception e) {
            scrollToElement(mainmenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]")));
            clickWithJS(mainmenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]")));
        }
        waitABit(1000);
    }

    private void selectMenuItem(WebElement menuitem, String mainMenuItem) {
        try {
            driver.switchTo().frame(tutorialFrame);
            waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(tutorialSkipButton)).click();
        } catch (Exception ignored) {
        }

        driver.switchTo().defaultContent();
        expandMainMenu();
        clickMainMenuItem(mainMenuItem);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menuitem)).click();
        } catch (TimeoutException e) {
            scrollToElement(menuitem);
            clickWithJS(menuitem);
        }
    }

    public boolean isMenuButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(menuButton));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}