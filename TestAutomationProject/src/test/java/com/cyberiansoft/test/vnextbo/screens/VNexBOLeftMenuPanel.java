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
        if (!isMainMenuExpanded()) {
            expandMainMenu();
        }
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