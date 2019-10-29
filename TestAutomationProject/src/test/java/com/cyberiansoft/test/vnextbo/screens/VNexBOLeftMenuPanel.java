package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsWebPage;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.cyberiansoft.test.vnextbo.enums.MainMenuItems;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
@Getter
public class VNexBOLeftMenuPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@id='mainMenu']")
    private WebElement mainMenu;

    @FindBy(id = "menuBtn")
    private WebElement menuButton;

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

    @FindBy(xpath = "//li[@data-automation-id='quick-notes']")
    private WebElement quickNotesMenu;

    @FindBy(xpath = "//li[@data-automation-id='company-info']")
    private WebElement companyInfoMenu;

    @FindBy(xpath = "//li[@data-automation-id='clients']")
    private WebElement clientsMenu;

    @FindBy(xpath = "//*[@data-automation-id='users']")
    private WebElement usersMenu;

    @FindBy(xpath = "//li[@data-automation-id='devices']")
    private WebElement deviceManagementMenu;

    @FindBy(xpath = "//li[@data-automation-id='orders']")
    private WebElement repairOrdersMenu;

    @FindBy(xpath = "//iframe[@id='embed']/following-sibling::div[5]//iframe")
    private WebElement tutorialFrame;

    @FindBy(xpath = "//button[text()='SKIP']")
    private WebElement tutorialSkipButton;

    @FindBy(xpath = "//ul[@id='mainMenu']/li[contains(., 'Settings')]")
    private WebElement settingsMenuTab;

    public VNexBOLeftMenuPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public VNextBOInspectionsWebPage selectInspectionsMenu() {
        selectMenuItem(inspectionsMenu, MainMenuItems.OPERATIONS.getMenu());
        return PageFactory.initElements(driver, VNextBOInspectionsWebPage.class);
    }

    public VNextBOInvoicesWebPage selectInvoicesMenu() {
        selectMenuItem(invoicesMenu, MainMenuItems.OPERATIONS.getMenu());
        return PageFactory.initElements(driver, VNextBOInvoicesWebPage.class);
    }

    public VNextBOPartsManagementWebPage selectPartsManagementMenu() {
        selectMenuItem(partsManagementMenu, MainMenuItems.OPERATIONS.getMenu());
        return PageFactory.initElements(driver, VNextBOPartsManagementWebPage.class);
    }

    public VNexBOUsersWebPage selectUsersMenu() {
        selectMenuItem(usersMenu, MainMenuItems.SETTINGS.getMenu());
        return PageFactory.initElements(driver, VNexBOUsersWebPage.class);
    }

    public VNextBOROWebPage selectRepairOrdersMenu() {
        selectMenuItem(repairOrdersMenu, MainMenuItems.MONITOR.getMenu());
        return PageFactory.initElements(driver, VNextBOROWebPage.class);
    }

    public VNextBOServicesWebPage selectServicesMenu() {
        selectMenuItem(servicesMenu, MainMenuItems.SETTINGS.getMenu());
        return PageFactory.initElements(driver, VNextBOServicesWebPage.class);
    }

    public VNextBOQuickNotesWebPage selectQuickNotesMenu() {
        selectMenuItem(quickNotesMenu, MainMenuItems.SETTINGS.getMenu());
        return PageFactory.initElements(driver, VNextBOQuickNotesWebPage.class);
    }

    public VNextBOCompanyInfoWebPage selectCompanyInfoMenu() {
        selectMenuItem(companyInfoMenu, MainMenuItems.SETTINGS.getMenu());
        return PageFactory.initElements(driver, VNextBOCompanyInfoWebPage.class);
    }

    public VNextBOClientsWebPage selectClientsMenu() {
        selectMenuItem(clientsMenu, MainMenuItems.SETTINGS.getMenu());
        return PageFactory.initElements(driver, VNextBOClientsWebPage.class);
    }

    public VNextBODeviceManagementWebPage selectDeviceManagementMenu() {
        selectMenuItem(deviceManagementMenu, MainMenuItems.SETTINGS.getMenu());
        final VNextBODeviceManagementWebPage deviceManagementWebPage =
                PageFactory.initElements(driver, VNextBODeviceManagementWebPage.class);
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(deviceManagementWebPage.getDeviceManagementBreadCrumb(), 5);
        return deviceManagementWebPage;
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
                    .elementToBeClickable(this.mainMenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]"))))
                    .click();
        } catch (Exception e) {
            scrollToElement(this.mainMenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]")));
            clickWithJS(this.mainMenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]")));
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
        waitForLoading();
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