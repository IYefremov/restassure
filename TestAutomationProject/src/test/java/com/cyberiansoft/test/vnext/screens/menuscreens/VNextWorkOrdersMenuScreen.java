package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.customers.VNextChangeCustomerScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextWorkOrdersMenuScreen extends VNextBasicMenuScreen {

    @FindBy(xpath = "//*[@data-name='changeCustomer']")
    private WebElement changecustomerbtn;

    @FindBy(xpath = "//*[@data-name='delete']")
    private WebElement deleteorderbtn;

    @FindBy(xpath = "//*[@data-name='edit']")
    private WebElement editinspectionbtn;

    @FindBy(xpath = "//*[@data-name='invoice']")
    private WebElement createinvoicemenuitem;

    public VNextWorkOrdersMenuScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
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
        return appiumdriver.findElement(By.xpath("//*[@data-name='edit']")).isDisplayed();
    }

    public VNextWorkOrdersScreen clickCloseWorkOrdersMenuButton() {
        clickCloseMenuButton();
        return new VNextWorkOrdersScreen(appiumdriver);
    }

    public VNextVehicleInfoScreen clickEditWorkOrderMenuItem() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.visibilityOf(editinspectionbtn));
        clickMenuItem(editinspectionbtn);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        return vehicleInfoScreen;
    }

    public VNextInvoiceTypesList clickCreateInvoiceMenuItem(){
        clickMenuItem(createinvoicemenuitem);
        return new VNextInvoiceTypesList(appiumdriver);
    }
}
