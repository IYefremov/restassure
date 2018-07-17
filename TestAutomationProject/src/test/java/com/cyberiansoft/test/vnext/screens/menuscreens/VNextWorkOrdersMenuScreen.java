package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextWorkOrdersMenuScreen extends VNextBasicMenuScreen {

    @FindBy(xpath="//a[@data-name='changeCustomer']")
    private WebElement changecustomerbtn;

    @FindBy(xpath="//a[@handler='_deleteWorkOrder']")
    private WebElement deleteorderbtn;

    @FindBy(xpath="//a[@data-name='edit']")
    private WebElement editinspectionbtn;

    @FindBy(xpath="//a[@data-name='invoice']")
    private WebElement createinvoicemenuitem;

    public VNextWorkOrdersMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
    }

    public VNextCustomersScreen clickChangeCustomerMenuItem() {
        clickMenuItem(changecustomerbtn);
        return new VNextCustomersScreen(appiumdriver);
    }

    public boolean isChangeCustomerMenuPresent() {
        return changecustomerbtn.isDisplayed();
    }

    public void clickDeleteWorkOrderMenuButton() {
        tap(deleteorderbtn);
    }

    public VNextWorkOrdersScreen deleteWorkOrder() {
        clickDeleteWorkOrderMenuButton();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        informationdlg.clickInformationDialogDeleteButton();
        return new VNextWorkOrdersScreen(appiumdriver);
    }

    public boolean isDeleteWorkOrderMenuButtonExists() {
        return appiumdriver.findElement(By.xpath("//a[@handler='_deleteWorkOrder']")).isDisplayed();
    }

    public VNextWorkOrdersScreen clickCloseWorkOrdersMenuButton() {
        clickCloseMenuButton();
        return new VNextWorkOrdersScreen(appiumdriver);
    }

    public VNextVehicleInfoScreen clickEditWorkOrderMenuItem() {
        clickMenuItem(editinspectionbtn);
        return new VNextVehicleInfoScreen(appiumdriver);
    }

    public VNextInspectionTypesList clickCreateInvoiceMenuItem(){
        clickMenuItem(createinvoicemenuitem);
        return new VNextInspectionTypesList(appiumdriver);
    }
}
