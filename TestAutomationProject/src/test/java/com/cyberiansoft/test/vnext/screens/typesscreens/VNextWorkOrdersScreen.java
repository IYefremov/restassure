package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.customers.VNextChangeCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.WorkOrderListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VNextWorkOrdersScreen extends VNextBaseTypeScreen {

    @FindBy(xpath = "//div[contains(@class, 'page work-orders-list')]")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@data-autotests-id='work orders-list']")
    private WebElement workorderslist;

    @FindBy(xpath = "//*[@data-name='invoice']")
    private WebElement createinvoicemenu;

    @FindBy(xpath = "//*[@action='multiselect-actions-create-invoice']")
    private WebElement createinvoiceicon;

    @FindBy(xpath = "//*[@data-autotests-id='work orders-list']/div")
    private List<WorkOrderListElement> workOrdersList;

    public VNextWorkOrdersScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextWorkOrdersScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public WorkOrderListElement getWorkOrderElement(String workOrderId) {
        return workOrdersList.stream().filter(listElement -> listElement.getId().equals(workOrderId)).findFirst().orElseThrow(() -> new RuntimeException("work order not found " + workOrderId));
    }

    public List<WorkOrderListElement> getWorkOrdersList() {
        return workOrdersList;
    }

    public VNextCustomersScreen clickAddWorkOrderButton() {
        clickAddButton();
        return new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
    }

    public VNextWorkOrderTypesList clickAddWorkOrdernWithPreselectedCustomerButton() {
        clickAddButton();
        return new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
    }

    public String getFirstWorkOrderNumber() {
        return workorderslist.findElement(By.xpath(".//*[@action='select']/div[contains(@class, 'checkbox-item-title')]")).getText();
    }

    public VNextWorkOrdersMenuScreen clickOnWorkOrderByNumber(String wonumber) {
        if (isTeamViewActive()) {
            if (!WaitUtils.isElementPresent(By.xpath("//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")))
                searchWorkOrderByFreeText(wonumber);
        } else {
            if (!WaitUtils.isElementPresent(By.xpath(("//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']"))))
            clearSearchField();
        }
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='work orders-list']")));
        WaitUtils.getGeneralFluentWait().until(driver -> {
            workorderslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")).click();
            return true;
        });
        return new VNextWorkOrdersMenuScreen(appiumdriver);
    }

    public void selectWorkOrder(String wonumber) {
        WebElement workordercell = getWorkOrderCell(wonumber);
        if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
            tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public void unselectWorkOrder(String wonumber) {
        WebElement workordercell = getWorkOrderCell(wonumber);
        if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") != null)
            tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public boolean isWorkOrderSelected(String woNumber) {
        WebElement workordercell = getWorkOrderCell(woNumber);
        return workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked").equals("true");
    }

    public boolean isWorkOrderExists(String woNumber) {
        //clearSearchField();
        return workorderslist.findElements(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + woNumber + "']")).size() > 0;
    }

    public int getNumberOfSelectedWorkOrders() {
        return Integer.parseInt(rootElement.findElement(By.xpath(".//span[@class='selected-items-counter']")).getText());
    }

    public VNextHomeScreen clickBackButton() {
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
        clickScreenBackButton();
        return new VNextHomeScreen(appiumdriver);
    }

    public String getWorkOrderPriceValue(String wonumber) {
        WaitUtils.elementShouldBeVisible(workorderslist, true);
        WebElement workordercell = getWorkOrderCell(wonumber);
        return workordercell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
    }

    public String getWorkOrderStatusValue(String wonumber) {
        WaitUtils.elementShouldBeVisible(workorderslist, true);
        WebElement workordercell = getWorkOrderCell(wonumber);
        return workordercell.findElement(By.xpath(".//span[contains(@class, 'entity-item-status-')]")).getText();
    }

    public String getWorkOrderCustomerValue(String wonumber) {
        WaitUtils.elementShouldBeVisible(workorderslist, true);
        WebElement workordercell = getWorkOrderCell(wonumber);
        return workordercell.findElement(By.xpath(".//div[@class='entity-item-title']")).getText();
    }

    public WebElement getWorkOrderCell(String wonumber) {
        return getListCell(workorderslist, wonumber);
    }

    public void clickCreateInvoiceFromWorkOrder(String wonumber) {
        WaitUtils.elementShouldBeVisible(workorderslist, true);
        WebElement workordercell = getWorkOrderCell(wonumber);
        tap(workordercell.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + wonumber + "']")));
        clickCreateInvoiceMenuItem();
    }

    public void clickCreateInvoiceMenuItem() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(createinvoicemenu));
        tap(createinvoicemenu);
    }

    public void clickCreateInvoiceIcon() {
        tap(createinvoiceicon);
    }

    public void switchToTeamWorkordersView() {
        switchToTeamView();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
    }

    public boolean isTeamWorkordersViewActive() {
        return isTeamViewActive();
    }

    public void switchToMyWorkordersView() {
        switchToMyView();

    }

    public void changeCustomerForWorkOrderViaSearch(String workOrderNumber, AppCustomer newCustomer) {
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
        VNextChangeCustomerScreen changeCustomerScreen = workOrdersMenuScreen.clickChangeCustomerMenuItem();
        changeCustomerScreen.switchToRetailMode();
        changeCustomerScreen.searchCustomerByName(newCustomer.getFullName());
        changeCustomerScreen.selectCustomer(newCustomer);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogYesButton();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Order customer...']"));
    }

    public void changeCustomerToWholesailForWorkOrder(String workOrderNumber, AppCustomer newWholesailCustomer) {
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = clickOnWorkOrderByNumber(workOrderNumber);
        VNextChangeCustomerScreen changeCustomerScreen = workOrdersMenuScreen.clickChangeCustomerMenuItem();
        changeCustomerScreen.switchToWholesaleMode();
        changeCustomerScreen.selectCustomer(newWholesailCustomer);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogYesButton();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Order customer...']"));
    }

    public void searchWorkOrderByFreeText(String searchtext) {
        SearchSteps.searchByText(searchtext);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading work orders']"));
    }

    public void waitForWorkOrderScreenInfoMessage(String infoMessage) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(
                appiumdriver.findElement(By.xpath("//*[text()='" + infoMessage + "']"))));
        wait = new WebDriverWait(appiumdriver, 40);
        wait.until(ExpectedConditions.invisibilityOf(
                appiumdriver.findElement(By.xpath("//*[text()='" + infoMessage + "']"))));

    }

    public void createSeparateInvoice(String workOrderNumber) {
        selectWorkOrder(workOrderNumber);
        clickCreateInvoiceIcon();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickSeparateInvoicesButton();
        waitForWorkOrderScreenInfoMessage("Invoice creation");
    }

    public void createSeparateInvoices(ArrayList<String> workOrders) {
        for (String workOrderNumber : workOrders)
            selectWorkOrder(workOrderNumber);
        clickCreateInvoiceIcon();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickSeparateInvoicesButton();
        waitForWorkOrderScreenInfoMessage("Invoices creation");
    }

    public void cancelCreatingSeparateInvoice() {
        BaseUtils.waitABit(2000);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        WebElement modalDlg = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal modal-loading modal-in']")));

        wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(modalDlg.findElement(By.xpath(".//span[text()='Cancel loading']"))));
        //BaseUtils.waitABit(1000);
        tap(modalDlg.findElement(By.xpath(".//span[text()='Cancel loading']")));

        wait.until(ExpectedConditions.visibilityOf(
                appiumdriver.findElement(By.xpath("//*[text()='Invoice has been created']"))));
        wait = new WebDriverWait(appiumdriver, 120);
        wait.until(ExpectedConditions.invisibilityOf(
                appiumdriver.findElement(By.xpath("//*[text()='Invoice has been created']"))));

    }
}
