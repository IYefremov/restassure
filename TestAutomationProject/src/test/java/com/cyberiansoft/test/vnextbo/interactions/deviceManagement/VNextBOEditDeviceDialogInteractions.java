package com.cyberiansoft.test.vnextbo.interactions.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOEditDeviceDialog;
import org.openqa.selenium.support.PageFactory;

public class VNextBOEditDeviceDialogInteractions extends VNextBODeviceDialogInteractions {

    private VNextBOEditDeviceDialog editDeviceDialog;

    public VNextBOEditDeviceDialogInteractions() {
        super();
        editDeviceDialog = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBOEditDeviceDialog.class);
    }

    @Override
    public void setNickName(String nickName) {
        Utils.clearAndType(editDeviceDialog.getNicknameInputField(), nickName);
    }

    @Override
    public void setTeam(String team) {
        Utils.clickElement(editDeviceDialog.getTeamArrow());
        Utils.selectOptionInDropDown(editDeviceDialog.getTeamDropDown(),
                editDeviceDialog.getTeamListBoxOptions(), team, true);
    }

    @Override
    public void setTimeZone(String timeZone) {
        Utils.clickElement(editDeviceDialog.getTimeZoneArrow());
        Utils.selectOptionInDropDown(editDeviceDialog.getTimeZoneDropDown(),
                editDeviceDialog.getTimeZoneListBoxOptions(), timeZone, true);
    }

    @Override
    public void clickSubmitButton() {
        Utils.clickElement(editDeviceDialog.getSubmitButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    @Override
    public void clickCancelButton() {
        Utils.clickElement(editDeviceDialog.getCancelButton());
    }

    public String getNickNameValue() {
        return Utils.getInputFieldValue(editDeviceDialog.getNicknameInputField(), 5);
    }

    public String getTeamValue() {
        return WaitUtilsWebDriver
                .waitForVisibility(editDeviceDialog.getTeamInput(), 5)
                .getText();
    }

    public String getTimeZoneValue() {
        return WaitUtilsWebDriver
                .waitForVisibility(editDeviceDialog.getTimeZoneInput(), 5)
                .getText();
    }

    public boolean isEditDeviceDialogDisplayed() {
        return Utils.isElementDisplayed(editDeviceDialog.getEditDeviceDialog());
    }

    public boolean isEditDeviceDialogClosed() {
        return Utils.isElementNotDisplayed(editDeviceDialog.getEditDeviceDialog());
    }
}