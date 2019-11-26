package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class VNextBOChangeTechnicianDialog extends VNextBOBaseWebPage {

    @FindBy(id = "orderServices_PhaseVendorTechnician_ModalTemplate")
    private WebElement changeOrderServicesTechnicianDialog;

    @FindBy(id = "order_VendorTechnician_ModalTemplate")
    private WebElement changeOrderTechnicianDialog;

    @FindBy(xpath = "//div[@id='orderServices_PhaseVendorTechnician_ModalTemplate']//button[@data-automation-id='modalConfirmButton']")
    private WebElement changeOrderServiceTechnicianOkButton;

    @FindBy(xpath = "//div[@id='orderServices_PhaseVendorTechnician_ModalTemplate']//button[@data-automation-id='modalCancelButton']")
    private WebElement changeOrderServiceTechnicianCancelButton;

    @FindBy(xpath = "//div[@id='orderServices_PhaseVendorTechnician_ModalTemplate']//button[@data-automation-id='modalCloseButton']")
    private WebElement changeOrderServiceTechnicianXButton;

    @FindBy(xpath = "//div[@id='order_VendorTechnician_ModalTemplate']//button[@data-automation-id='modalConfirmButton']")
    private WebElement changeTechnicianOkButton;

    @FindBy(xpath = "//div[@id='order_VendorTechnician_ModalTemplate']//button[@data-automation-id='modalCancelButton']")
    private WebElement changeTechnicianCancelButton;

    @FindBy(xpath = "//div[@id='order_VendorTechnician_ModalTemplate']//button[@data-automation-id='modalCloseButton']")
    private WebElement changeTechnicianXButton;

    @FindBy(xpath = "//div[@id='orderServices_PhaseVendorTechnician_ModalTemplate']//span[contains(@class, 'dropdown k-header')]")
    private List<WebElement> changeOrderServiceTechnicianListBoxes;

    @FindBy(xpath = "//div[@id='order_VendorTechnician_ModalTemplate']//span[contains(@class, 'dropdown k-header')]")
    private List<WebElement> changeTechnicianListBoxes;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> vendorListBoxOptions;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> technicianListBoxOptions;

    @FindBy(xpath = "//div[@id='orderServices_PhaseVendorTechnician_ModalTemplate']//span[contains(@class, 'dropdown k-header')]//span[@class='k-input']")
    private List<WebElement> selectedListBoxOptions;

    public VNextBOChangeTechnicianDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean isChangeTechnicianDialogDisplayed() {
        return Utils.isElementDisplayed(changeOrderServicesTechnicianDialog);
    }
}
