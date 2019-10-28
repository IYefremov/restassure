package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOAddNewUserDialogSteps extends VNextBOBaseWebPageSteps {

    public static void clickSaveButton() {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        Utils.clickElement(vNexBOAddNewUserDialog.saveBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void closeDialog() {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        Utils.clickElement(vNexBOAddNewUserDialog.closeDialogBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setUserFirstName(String firstName) {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        Utils.clearAndType(vNexBOAddNewUserDialog.firstNameFld, firstName);
    }

    public static void setUserLastName(String lastName) {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        Utils.clearAndType(vNexBOAddNewUserDialog.lastNameFld, lastName);
    }

    public static void setUserEmail(String userMail) {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        Utils.clearAndType(vNexBOAddNewUserDialog.userMailFld, userMail);
    }

    public static void setUserPhone(String userPhone) {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        Utils.clearAndType(vNexBOAddNewUserDialog.userPhoneFld, userPhone);
    }

    public static void clickWebAccessCheckbox() {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        Utils.clickElement(vNexBOAddNewUserDialog.webAccessCheckbox);
    }

    public static void createNewUser(String firstName, String lastName, String userMail, String phoneNumber, boolean webAccess) {
        setUserFirstName(firstName);
        setUserLastName(lastName);
        setUserEmail(userMail);
        setUserPhone(phoneNumber);
        if (webAccess)
            clickWebAccessCheckbox();
        clickSaveButton();
    }

    public static void editUserData(String firstName, String lastName, String phoneNumber, boolean webAccess) {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        setUserFirstName(firstName);
        setUserLastName(lastName);
        setUserPhone(phoneNumber);
        if (!webAccess & (vNexBOAddNewUserDialog.webAccessCheckbox.getAttribute("checked") != null))
            clickWebAccessCheckbox();
        clickSaveButton();
    }

    public static List<String> getErrorMessages() {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        return  vNexBOAddNewUserDialog.errorMessagesList.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}