package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOAddNewUserDialogSteps extends VNextBOBaseWebPageSteps {

    public static void clickSaveButton()
    {
        Utils.clickElement(new VNexBOAddNewUserDialog().saveBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void closeDialog()
    {
        Utils.clickElement(new VNexBOAddNewUserDialog().closeDialogBtn);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setUserFirstName(String firstName)
    {
        Utils.clearAndType(new VNexBOAddNewUserDialog().firstNameFld, firstName);
    }

    public static void setUserLastName(String lastName)
    {
        Utils.clearAndType(new VNexBOAddNewUserDialog().lastNameFld, lastName);
    }

    public static void setUserEmail(String userMail)
    {
        Utils.clearAndType(new VNexBOAddNewUserDialog().userMailFld, userMail);
    }

    public static void setUserPhone(String userPhone)
    {
        Utils.clearAndType(new VNexBOAddNewUserDialog().userPhoneFld, userPhone);
    }

    public static void clickWebAccessCheckbox()
    {
        Utils.clickElement(new VNexBOAddNewUserDialog().webAccessCheckbox);
    }

    public static void createNewUser(String firstName, String lastName, String userMail, String phoneNumber, boolean webAccess)
    {
        setUserFirstName(firstName);
        setUserLastName(lastName);
        setUserEmail(userMail);
        setUserPhone(phoneNumber);
        if (webAccess)
            clickWebAccessCheckbox();
        clickSaveButton();
    }

    public static void editUserData(String firstName, String lastName, String phoneNumber, boolean webAccess)
    {
        setUserFirstName(firstName);
        setUserLastName(lastName);
        setUserPhone(phoneNumber);
        if (!webAccess & (new VNexBOAddNewUserDialog().webAccessCheckbox.getAttribute("checked") != null))
            clickWebAccessCheckbox();
        clickSaveButton();
    }

    public static List<String> getErrorMessages()
    {
        return  new VNexBOAddNewUserDialog().errorMessagesList.stream().
                map(WebElement::getText).collect(Collectors.toList());
    }
}