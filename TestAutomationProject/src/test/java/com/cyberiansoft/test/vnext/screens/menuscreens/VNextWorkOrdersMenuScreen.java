package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.customers.VNextChangeCustomerScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public VNextChangeCustomerScreen clickChangeCustomerMenuItem() {
        clickMenuItem(changecustomerbtn);
        return new VNextChangeCustomerScreen(appiumdriver);
    }

    public boolean isChangeCustomerMenuPresent() {
        return changecustomerbtn.isDisplayed();
    }

    public void clickDeleteWorkOrderMenuButton() {
        clickMenuItem(deleteorderbtn);
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

    public boolean isEditWorkOrderMenuButtonExists() {
        return appiumdriver.findElement(By.xpath("//a[@data-name='edit']")).isDisplayed();
    }

    public VNextWorkOrdersScreen clickCloseWorkOrdersMenuButton() {
        clickCloseMenuButton();
        return new VNextWorkOrdersScreen(appiumdriver);
    }

    public void clickEditWorkOrderMenuItem() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.visibilityOf(editinspectionbtn));
        clickMenuItem(editinspectionbtn);
    }

    public VNextInvoiceTypesList clickCreateInvoiceMenuItem(){
        clickMenuItem(createinvoicemenuitem);
        return new VNextInvoiceTypesList(appiumdriver);
    }
}
