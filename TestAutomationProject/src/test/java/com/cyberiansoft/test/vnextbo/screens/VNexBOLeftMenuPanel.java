package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.enums.MainMenuItems;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
@Getter
public class VNexBOLeftMenuPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@id='mainMenu']")
    private WebElement mainMenu;

    @FindBy(id = "menuBtn")
    private WebElement menuButton;

    @FindBy(xpath = "//body[@class='body-mobile--scroll-hidden']")
    private WebElement closedMenu;

    @FindBy(xpath = "//nav[@class='left-menu__nav']/ul")
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

    public WebElement mainMenuItemByName(String menuItemName) {
        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@data-parent='#mainMenu' and contains(text(),'" + menuItemName + "')]"));
    }

    public WebElement subMenuItemByName(String subMenuItemName) {
        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//ul[@data-template='submenu-item-template']//span[text()='" + subMenuItemName + "']/parent::a"));
    }

    public VNextBOInspectionsWebPage selectInspectionsMenu() {
        selectMenuItem(inspectionsMenu, MainMenuItems.OPERATIONS.getMenu());
        return PageFactory.initElements(driver, VNextBOInspectionsWebPage.class);
    }

    public VNextBOQuickNotesWebPage selectQuickNotesMenu() {
        selectMenuItem(quickNotesMenu, MainMenuItems.SETTINGS.getMenu());
        return PageFactory.initElements(driver, VNextBOQuickNotesWebPage.class);
    }

    public boolean isUsersMenuItemExists() {
        if (!VNextBOLeftMenuValidations.isMainMenuExpanded()) {
            VNextBOLeftMenuInteractions.expandMainMenu();
        }
        return driver.findElement(By.xpath("//*[@data-automation-id='users']")).isDisplayed();
    }

    private void clickMainMenuItem(String mainMenu) {
        try {
            wait.until(ExpectedConditions
                    .elementToBeClickable(this.mainMenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]"))))
                    .click();
        } catch (Exception e) {
            Utils.scrollToElement(this.mainMenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]")));
            Utils.clickWithJS(this.mainMenu.findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]")));
        }
        WaitUtilsWebDriver.waitABit(1000);
    }

    private void selectMenuItem(WebElement menuitem, String mainMenuItem) {
        try {
            driver.switchTo().frame(tutorialFrame);
            WaitUtilsWebDriver.waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(tutorialSkipButton)).click();
        } catch (Exception ignored) {
        }

        driver.switchTo().defaultContent();
        VNextBOLeftMenuInteractions.expandMainMenu();
        clickMainMenuItem(mainMenuItem);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menuitem)).click();
        } catch (TimeoutException e) {
            Utils.scrollToElement(menuitem);
            Utils.clickWithJS(menuitem);
        }
        WaitUtilsWebDriver.waitForLoading();
    }
}