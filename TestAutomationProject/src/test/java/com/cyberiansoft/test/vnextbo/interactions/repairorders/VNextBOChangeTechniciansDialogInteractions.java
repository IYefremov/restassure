package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOChangeTechnicianDialog;
import org.openqa.selenium.WebElement;

public class VNextBOChangeTechniciansDialogInteractions {

    public static void setOrderServiceVendor(String vendor) {
        clickOrderServiceVendorBox();
        selectOrderServiceVendor(vendor);
    }

    private static void clickOrderServiceVendorBox() {
        Utils.clickElement(new VNextBOChangeTechnicianDialog().getChangeOrderServiceTechnicianListBoxes().get(0));
    }

    private static void selectOrderServiceVendor(String vendor) {
        Utils.selectOptionInDropDown(new VNextBOChangeTechnicianDialog().getVendorListBoxOptions().get(0),
                new VNextBOChangeTechnicianDialog().getVendorListBoxOptions(), vendor);
    }

    public static void setOrderServiceTechnician(String technician) {
        clickOrderServiceTechnicianBox();
        selectOrderServiceTechnician(technician);
    }

    private static void clickOrderServiceTechnicianBox() {
        Utils.clickElement(new VNextBOChangeTechnicianDialog().getChangeOrderServiceTechnicianListBoxes().get(1));
    }

    private static void selectOrderServiceTechnician(String technician) {
        final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog();
        Utils.selectOptionInDropDown(changeTechnicianDialog.getTechnicianListBoxOptions().get(0),
                changeTechnicianDialog.getTechnicianListBoxOptions(), technician);
    }

    public static void setVendor(String vendor) {
        clickVendorBox();
        selectVendor(vendor);
    }

    private static void clickVendorBox() {
        Utils.clickElement(new VNextBOChangeTechnicianDialog().getVendorListBox());
    }

    private static void selectVendor(String vendor) {
        final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog();
        Utils.selectOptionInDropDown(changeTechnicianDialog.getVendorDropDown(),
                changeTechnicianDialog.getVendorListBoxOptions(), vendor);
    }

    public static void setTechnician(String technician) {
        clickTechnicianBox();
        selectTechnician(technician);
    }

    public static String setTechnician() {
        clickTechnicianBox();
        return selectTechnician();
    }

    private static void clickTechnicianBox() {
        Utils.clickElement(new VNextBOChangeTechnicianDialog().getTechnicianArrow());
    }

    private static void selectTechnician(String technician) {
        final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog();
        Utils.selectOptionInDropDown(changeTechnicianDialog.getTechnicianDropDown(),
                changeTechnicianDialog.getTechnicianListBoxOptions(), technician);
    }

    private static String selectTechnician() {
        final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog();
        return Utils.selectOptionInDropDownWithJs(changeTechnicianDialog.getTechnicianDropDown(),
                changeTechnicianDialog.getTechnicianListBoxOptions());
    }

    public String getVendor() {
        return Utils.getText(new VNextBOChangeTechnicianDialog().getSelectedListBoxOptions().get(0));
    }

    public String getTechnician() {
        return Utils.getText(new VNextBOChangeTechnicianDialog().getSelectedListBoxOptions().get(1));
    }

    public static void clickOrderServiceOkButton() {
        clickChangeTechnicianButton(new VNextBOChangeTechnicianDialog().getChangeOrderServiceTechnicianOkButton());
    }

    public static void clickOrderServiceCancelButton() {
        clickChangeTechnicianButton(new VNextBOChangeTechnicianDialog().getChangeOrderServiceTechnicianCancelButton());
    }

    public static void clickOrderServiceXButton() {
        clickChangeTechnicianButton(new VNextBOChangeTechnicianDialog().getChangeOrderServiceTechnicianXButton());
    }

    public static void clickOkButton() {
        clickChangeTechnicianButton(new VNextBOChangeTechnicianDialog().getChangeTechnicianOkButton());
    }

    public static void clickCancelButton() {
        clickChangeTechnicianButton(new VNextBOChangeTechnicianDialog().getChangeTechnicianCancelButton());
    }

    public static void clickXButton() {
        clickChangeTechnicianButton(new VNextBOChangeTechnicianDialog().getChangeTechnicianXButton());
    }

    private static void clickChangeTechnicianButton(WebElement button) {
        Utils.clickElement(button);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}