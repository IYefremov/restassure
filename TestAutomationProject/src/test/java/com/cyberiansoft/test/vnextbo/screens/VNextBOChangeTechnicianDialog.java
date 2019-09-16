package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
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

    public VNextBOChangeTechnicianDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public VNextBOChangeTechnicianDialog setVendor(String vendor) {
        clickVendorBox();
        selectVendor(vendor);
        return this;
    }

    private VNextBOChangeTechnicianDialog clickVendorBox() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(changeOrderServiceTechnicianListBoxes.get(0))).click();
        return this;
    }

    private VNextBOChangeTechnicianDialog selectVendor(String vendor) {
        selectOptionInDropDown(vendorListBoxOptions.get(0), vendorListBoxOptions, vendor);
        return this;
    }

    public VNextBOChangeTechnicianDialog setTechnician(String technician) {
        clickTechnicianBox();
        selectTechnician(technician);
        return this;
    }

    private VNextBOChangeTechnicianDialog clickTechnicianBox() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(changeOrderServiceTechnicianListBoxes.get(1))).click();
        return this;
    }

    private VNextBOChangeTechnicianDialog selectTechnician(String technician) {
        selectOptionInDropDown(technicianListBoxOptions.get(0), technicianListBoxOptions, technician);
        return this;
    }

    public String getVendor() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(selectedListBoxOptions.get(0))).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public String getTechnician() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(selectedListBoxOptions.get(1))).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public VNextBORODetailsPage clickOkButton() {
        return clickChangeTechnicianButton(changeOrderServiceTechnicianOkButton);
    }

    public VNextBORODetailsPage clickCancelButton() {
        return clickChangeTechnicianButton(changeOrderServiceTechnicianCancelButton);
    }

    public VNextBORODetailsPage clickXButton() {
        return clickChangeTechnicianButton(changeOrderServiceTechnicianXButton);
    }

    private VNextBORODetailsPage clickChangeTechnicianButton(WebElement button) {
        Utils.clickElement(button);
        WaitUtilsWebDriver.waitForLoading();
        return PageFactory.initElements(driver, VNextBORODetailsPage.class);
    }

    public boolean isChangeTechnicianDialogDisplayed() {
        return Utils.isElementDisplayed(changeOrderServicesTechnicianDialog);
    }
}
