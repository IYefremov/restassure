package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.WorkOrderListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
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

    public VNextWorkOrdersScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public WorkOrderListElement getWorkOrderElement(String workOrderId) {
        return workOrdersList.stream().filter(listElement -> listElement.getId().equals(workOrderId)).findFirst().orElseThrow(() -> new RuntimeException("work order not found " + workOrderId));
    }

    public List<WorkOrderListElement> getWorkOrdersList() {
        return workOrdersList;
    }

    public void clickAddWorkOrderButton() {
        clickAddButton();
    }

    public String getFirstWorkOrderNumber() {
        return workorderslist.findElement(By.xpath(".//*[@action='select']/div[contains(@class, 'checkbox-item-title')]")).getText();
    }

    public void selectWorkOrder(String workOrderNumber) {
        WebElement workordercell = getWorkOrderCell(workOrderNumber);
        if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
            tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public void unselectWorkOrder(String workOrderNumber) {
        WebElement workordercell = getWorkOrderCell(workOrderNumber);
        if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") != null)
            tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public boolean isWorkOrderExists(String workOrderNumber) {
        return workorderslist.findElements(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + workOrderNumber + "']")).size() > 0;
    }

    public String getWorkOrderPriceValue(String workOrderNumber) {
        WaitUtils.elementShouldBeVisible(workorderslist, true);
        WebElement workordercell = getWorkOrderCell(workOrderNumber);
        return workordercell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
    }

    public String getWorkOrderStatusValue(String workOrderNumber) {
        WaitUtils.elementShouldBeVisible(workorderslist, true);
        WebElement workordercell = getWorkOrderCell(workOrderNumber);
        return workordercell.findElement(By.xpath(".//span[contains(@class, 'entity-item-status-')]")).getText();
    }

    public String getWorkOrderCustomerValue(String workOrderNumber) {
        WaitUtils.elementShouldBeVisible(workorderslist, true);
        WebElement workordercell = getWorkOrderCell(workOrderNumber);
        return workordercell.findElement(By.xpath(".//div[@class='entity-item-title']")).getText();
    }

    public WebElement getWorkOrderCell(String workOrderNumber) {
        return getListCell(workorderslist, workOrderNumber);
    }

    public void clickCreateInvoiceIcon() {
        tap(createinvoiceicon);
    }

    public void switchToTeamWorkordersView() {
        switchToTeamView();
    }

    public void switchToMyWorkordersView() {
        switchToMyView();
    }


    public void waitForWorkOrderScreenInfoMessage(String infoMessage) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(
                appiumdriver.findElement(By.xpath("//*[text()='" + infoMessage + "']"))));
        wait = new WebDriverWait(appiumdriver, 40);
        wait.until(ExpectedConditions.invisibilityOf(
                appiumdriver.findElement(By.xpath("//*[text()='" + infoMessage + "']"))));

    }

    //todo make a step!!!
    public void createSeparateInvoice(String workOrderNumber) {
        selectWorkOrder(workOrderNumber);
        clickCreateInvoiceIcon();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickSeparateInvoicesButton();
        waitForWorkOrderScreenInfoMessage("Invoice creation");
    }


    //todo make a step!!!
    public void createSeparateInvoices(ArrayList<String> workOrders) {
        for (String workOrderNumber : workOrders)
            selectWorkOrder(workOrderNumber);
        clickCreateInvoiceIcon();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickSeparateInvoicesButton();
        waitForWorkOrderScreenInfoMessage("Invoices creation");
    }
}
