package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOChangeTechnicianDialog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOChangeTechniciansDialogInteractions {

    private VNextBOChangeTechnicianDialog changeTechnicianDialog;

    public VNextBOChangeTechniciansDialogInteractions() {
        changeTechnicianDialog = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBOChangeTechnicianDialog.class);
    }

    public void setOrderServiceVendor(String vendor) {
        clickOrderServiceVendorBox();
        selectOrderServiceVendor(vendor);
    }

    private void clickOrderServiceVendorBox() {
        Utils.clickElement(changeTechnicianDialog.getChangeOrderServiceTechnicianListBoxes().get(0));
    }

    private void selectOrderServiceVendor(String vendor) {
        Utils.selectOptionInDropDown(changeTechnicianDialog.getVendorListBoxOptions().get(0),
                changeTechnicianDialog.getVendorListBoxOptions(), vendor);
    }

    public void setOrderServiceTechnician(String technician) {
        clickOrderServiceTechnicianBox();
        selectOrderServiceTechnician(technician);
    }

    private void clickOrderServiceTechnicianBox() {
        Utils.clickElement(changeTechnicianDialog.getChangeOrderServiceTechnicianListBoxes().get(1));
    }

    private void selectOrderServiceTechnician(String technician) {
        Utils.selectOptionInDropDown(changeTechnicianDialog.getTechnicianListBoxOptions().get(0),
                changeTechnicianDialog.getTechnicianListBoxOptions(), technician);
    }

    public void setVendor(String vendor) {
        clickVendorBox();
        selectVendor(vendor);
    }

    private void clickVendorBox() {
        Utils.clickElement(changeTechnicianDialog.getChangeTechnicianListBoxes().get(0));
    }

    private void selectVendor(String vendor) {
        Utils.selectOptionInDropDown(changeTechnicianDialog.getVendorListBoxOptions().get(0),
                changeTechnicianDialog.getVendorListBoxOptions(), vendor);
    }

    public void setTechnician(String technician) {
        clickTechnicianBox();
        selectTechnician(technician);
    }

    public String setTechnician() {
        clickTechnicianBox();
        return selectTechnician();
    }

    private void clickTechnicianBox() {
        Utils.clickElement(changeTechnicianDialog.getChangeTechnicianListBoxes().get(1));
    }

    private void selectTechnician(String technician) {
        Utils.selectOptionInDropDown(changeTechnicianDialog.getTechnicianListBoxOptions().get(0),
                changeTechnicianDialog.getTechnicianListBoxOptions(), technician);
    }

    private String selectTechnician() {
        return Utils.selectOptionInDropDown(changeTechnicianDialog.getTechnicianListBoxOptions().get(0),
                changeTechnicianDialog.getTechnicianListBoxOptions());
    }

    public String getVendor() {
        return Utils.getText(changeTechnicianDialog.getSelectedListBoxOptions().get(0));
    }

    public String getTechnician() {
        return Utils.getText(changeTechnicianDialog.getSelectedListBoxOptions().get(1));
    }

    public void clickOrderServiceOkButton() {
        clickChangeTechnicianButton(changeTechnicianDialog.getChangeOrderServiceTechnicianOkButton());
    }

    public void clickOrderServiceCancelButton() {
        clickChangeTechnicianButton(changeTechnicianDialog.getChangeOrderServiceTechnicianCancelButton());
    }

    public void clickOrderServiceXButton() {
        clickChangeTechnicianButton(changeTechnicianDialog.getChangeOrderServiceTechnicianXButton());
    }

    public void clickOkButton() {
        clickChangeTechnicianButton(changeTechnicianDialog.getChangeTechnicianOkButton());
    }

    public void clickCancelButton() {
        clickChangeTechnicianButton(changeTechnicianDialog.getChangeTechnicianCancelButton());
    }

    public void clickXButton() {
        clickChangeTechnicianButton(changeTechnicianDialog.getChangeTechnicianXButton());
    }

    private void clickChangeTechnicianButton(WebElement button) {
        Utils.clickElement(button);
        WaitUtilsWebDriver.waitForLoading();
    }

    public boolean isChangeTechnicianDialogDisplayed() {
        WaitUtilsWebDriver.waitForLoading();
        return Utils.isElementDisplayed(changeTechnicianDialog.getChangeOrderTechnicianDialog(), 7);
    }
}