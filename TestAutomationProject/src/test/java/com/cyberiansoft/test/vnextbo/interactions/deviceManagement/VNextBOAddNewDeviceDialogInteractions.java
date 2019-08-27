package com.cyberiansoft.test.vnextbo.interactions.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBOAddNewDeviceDialog;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class VNextBOAddNewDeviceDialogInteractions extends VNextBODeviceDialogInteractions {

    private VNextBOAddNewDeviceDialog addNewDeviceDialog;

    public VNextBOAddNewDeviceDialogInteractions() {
        super();
        addNewDeviceDialog = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBOAddNewDeviceDialog.class);
    }

    public void setExpiresInHours(String hours) {
        Utils.clickElement(addNewDeviceDialog.getHoursInputField());
        Utils.sendKeysWithJS(addNewDeviceDialog.getHoursInputFieldToBeEdited(), hours);
        Utils.clickElement(addNewDeviceDialog.getExpiresInLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewDeviceDialog.getHoursInputField(), hours);
        Assert.assertEquals(Utils.getInputFieldValue(addNewDeviceDialog.getHoursInputField()), hours,
                "The hours value hasn't been set");
    }

    public void setExpiresInMinutes(String minutes) {
        Utils.clickElement(addNewDeviceDialog.getMinutesInputField());
        Utils.sendKeysWithJS(addNewDeviceDialog.getMinutesInputFieldToBeEdited(), minutes);
        Utils.clickElement(addNewDeviceDialog.getExpiresInLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewDeviceDialog.getMinutesInputField(), minutes);
        Assert.assertEquals(Utils.getInputFieldValue(addNewDeviceDialog.getMinutesInputField()), minutes,
                "The minutes value hasn't been set");
    }

    @Override
    public void setNickName(String nickName) {
        Utils.clearAndType(addNewDeviceDialog.getNicknameInputField(), nickName);
    }

    public void setPhoneNumber(String phoneNumber) {
        Utils.clearAndType(addNewDeviceDialog.getPhoneNumberInputField(), phoneNumber);
    }

    public void setRandomPhoneNumber() {
        final String randomPhoneNumber = RandomStringUtils.randomNumeric(9);
        Utils.clearAndType(addNewDeviceDialog.getPhoneNumberInputField(), randomPhoneNumber);
        Utils.clickElement(addNewDeviceDialog.getPhoneNumberLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(
                addNewDeviceDialog.getPhoneNumberInputField(), randomPhoneNumber);
        Assert.assertEquals(Utils.getInputFieldValue(addNewDeviceDialog.getPhoneNumberInputField()), randomPhoneNumber,
                "The phone number hasn't been set");
    }

    @Override
    public void setTeam(String team) {
        Utils.clickElement(addNewDeviceDialog.getTeamArrow());
        Utils.selectOptionInDropDown(addNewDeviceDialog.getTeamDropDown(),
                addNewDeviceDialog.getTeamListBoxOptions(), team, true);
    }

    public void setLicense(String license) {
        Utils.clickElement(addNewDeviceDialog.getLicenseArrow());
        Utils.selectOptionInDropDown(addNewDeviceDialog.getLicenseDropDown(),
                addNewDeviceDialog.getLicenseListBoxOptions(), license, true);
    }

    public void setRandomLicense() {
        Utils.clickElement(addNewDeviceDialog.getLicenseArrow());
        final int randomLicenseNumber = RandomUtils.nextInt(0, addNewDeviceDialog.getLicenseListBoxOptions().size());
        final String license = addNewDeviceDialog.getLicenseListBoxOptions().get(randomLicenseNumber).getText();
        Utils.selectOptionInDropDown(addNewDeviceDialog.getLicenseDropDown(),
                addNewDeviceDialog.getLicenseListBoxOptions(), license, true);
    }

    @Override
    public void setTimeZone(String timeZone) {
        Utils.clickElement(addNewDeviceDialog.getTimeZoneArrow());
        Utils.selectOptionInDropDown(addNewDeviceDialog.getTimeZoneDropDown(),
                addNewDeviceDialog.getTimeZoneListBoxOptions(), timeZone, true);
    }

    public void setTechnician(String technician) {
        Utils.clickElement(addNewDeviceDialog.getTechnicianArrow());
        Utils.selectOptionInDropDown(addNewDeviceDialog.getTechnicianDropDown(),
                addNewDeviceDialog.getTechnicianListBoxOptions(), technician, true);
    }

    @Override
    public void clickSubmitButton() {
        Utils.clickElement(addNewDeviceDialog.getSubmitButton());
    }

    @Override
    public void clickCancelButton() {
        Utils.clickElement(addNewDeviceDialog.getCancelButton());
    }
}