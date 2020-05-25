package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextHomeScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='home']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='navigate-to-customers']")
    private WebElement customersList;

    @FindBy(xpath = "//span[@class='client-mode']")
    private WebElement clientMode;

    @FindBy(xpath = "//*[@action='navigate-to-inspections']")
    private WebElement inspectionsList;

    @FindBy(xpath = "//*[@action='navigate-to-orders']")
    private WebElement workOrdersList;

    @FindBy(xpath = "//*[@action='navigate-to-invoices']")
    private WebElement invoicesList;

    @FindBy(xpath = "//*[@action='work-queue']")
    private WebElement workQueue;

    @FindBy(xpath = "//*[@action='monitor-update-work']")
    private WebElement monitorUpdateWork;

    @FindBy(xpath = "//*[@action='navigate-to-monitor']")
    private WebElement monitor;

    @FindBy(xpath = "//*[@action='navigate-to-service-requests']")
    private WebElement serviceRequests;

    @FindBy(xpath = "//a[@class='tile-link tile-item more-tile']")
    private WebElement moreList;

    @FindBy(xpath = "//*[@action='navigate-to-settings']")
    private WebElement settingsList;

    @FindBy(xpath = "//*[@action='navigate-to-status']")
    private WebElement statusList;

    @FindBy(xpath = "//*[@action='messager-send']/span[@class='messager-counter']")
    private WebElement queueMessage;

    @FindBy(xpath = "//*[@action='messager-send']")
    private WebElement queueMessageIcon;

    @FindBy(xpath = "//*[@action='logout']")
    private WebElement logoutBtn;

    @FindBy(xpath = "//div[@class='speed-dial']/a[@class='floating-button color-red']")
    private WebElement addBtn;

    @FindBy(xpath = "//*[@action='new_order']")
    private WebElement newWorkOrderBtn;

    @FindBy(xpath = "//*[@action='new_inspection']")
    private WebElement newInspectionBtn;

    @FindBy(xpath = "//*[@action='new_invoice']")
    private WebElement newInvoiceBtn;

    public VNextHomeScreen() {
    }

    public void clickCustomersMenuItem() {
        tap(customersList);
    }

    public void clickWorkOrdersMenuItem() {
        WaitUtils.elementShouldBeVisible(workOrdersList,true);
        tap(workOrdersList);
    }

    @Step
    public void clickInspectionsMenuItem() {
        WebDriver webDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            JavascriptExecutor executor = (JavascriptExecutor) webDriver;
            executor.executeScript("arguments[0].click();", inspectionsList);
            return true;
        });
    }

    public void clickInvoicesMenuItem() {
        tap(invoicesList);
        BaseUtils.waitABit(2000);
    }

    public void clickSettingsMenuItem() {
        if (!settingsList.isDisplayed())
            tap(moreList);
        tap(settingsList);
    }

    public void clickStatusMenuItem() {
        WebDriver webDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", statusList);
    }

    public void clickQueueMessageIcon() {
        tap(queueMessageIcon);
        BaseUtils.waitABit(500);
    }

    public String getQueueMessageValue() {
        WaitUtils.elementShouldBeVisible(queueMessage, true);
        return queueMessage.getText();
    }

    public boolean isQueueMessageVisible() {
        return WaitUtils.isElementPresent(By.xpath("//*[@action='messager-send']"));
    }

    public void waitUntilQueueMessageInvisible() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 240);
        wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElement(By.xpath("//*[@action='messager-send']"))));
    }

    public VNextLoginScreen clickLogoutButton() {
        tap(logoutBtn);
        return new VNextLoginScreen(appiumdriver);
    }

    //todo: make it as Step
    public VNextVehicleInfoScreen openCreateWOWizard(AppCustomer testcustomer) {
        WorkOrderSteps.clickAddWorkOrderButton();
        CustomersScreenSteps.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        return vehicleInfoScreen;
    }

    public boolean isUpgrateToProBannerVisible() {
        return appiumdriver.findElement(By.xpath("//div[@class='upgrade-image' and @action='ad']")).isDisplayed();
    }

    public void clickAddButton() {
        tap(addBtn);
    }

    public void clickNewWorkOrderPopupMenu() {
        clickAddButton();
        tap(newWorkOrderBtn);
    }
}
