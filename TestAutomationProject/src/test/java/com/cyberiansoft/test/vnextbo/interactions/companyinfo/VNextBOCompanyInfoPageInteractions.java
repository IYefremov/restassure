package com.cyberiansoft.test.vnextbo.interactions.companyinfo;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOCompanyInfoPage;

public class VNextBOCompanyInfoPageInteractions {

    public static void setCompanyName(String companyName) {
        Utils.clearAndType(new VNextBOCompanyInfoPage().getCompanyNameInputField(), companyName);
    }

    public static void setAddressLine1(String addressLine1) {
        Utils.clearAndType(new VNextBOCompanyInfoPage().getAddress1InputField(), addressLine1);
    }

    public static void setAddressLine2(String addressLine2) {
        Utils.clearAndType(new VNextBOCompanyInfoPage().getAddress2InputField(), addressLine2);
    }

    public static void setCity(String city) {
        Utils.clearAndType(new VNextBOCompanyInfoPage().getCityInputField(), city);
    }

    public static void setZip(String zip) {
        Utils.clearAndType(new VNextBOCompanyInfoPage().getZipInputField(), zip);
    }

    public static void setPhoneCode(String phoneCode) {
        final VNextBOCompanyInfoPage companyInfoPage = new VNextBOCompanyInfoPage();
        Utils.clickElement(companyInfoPage.getPhoneCodeArrow());
        Utils.selectOptionInDropDown(companyInfoPage.getPhoneCodeDropDown(), companyInfoPage.getPhoneCodeListBoxOptions(), phoneCode, true);
    }

    public static void setPhone(String phone) {
        Utils.clearAndType(new VNextBOCompanyInfoPage().getPhoneInputField(), phone);
    }

    public static void setEmail(String email) {
        Utils.clearAndType(new VNextBOCompanyInfoPage().getEmailInputField(), email);
    }

    public static void setCountry(String country) {
        final VNextBOCompanyInfoPage companyInfoPage = new VNextBOCompanyInfoPage();
        Utils.clickElement(companyInfoPage.getCountryArrow());
        Utils.selectOptionInDropDownWithJs(companyInfoPage.getCountryDropDown(), companyInfoPage.getCountryListBoxOptions(), country);
    }

    public static void setStateProvince(String stateProvince) {
        final VNextBOCompanyInfoPage companyInfoPage = new VNextBOCompanyInfoPage();
        Utils.clickElement(companyInfoPage.getStateProvinceArrow());
        Utils.selectOptionInDropDownWithJs(companyInfoPage.getStateProvinceDropDown(), companyInfoPage.getStateProvinceListBoxOptions(), stateProvince);
    }

    public static void clickSaveButton() {
        Utils.clickElement(new VNextBOCompanyInfoPage().getSaveButton());
    }

    public static String getCompanyValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getCompanyNameInputField());
    }

    public static String getAddressLine1Value() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getAddress1InputField());
    }

    public static String getAddressLine2Value() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getAddress2InputField());
    }

    public static String getCityValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getCityInputField());
    }

    public static String getCountryValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getCountryInputField());
    }

    public static String getStateProvinceValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getStateProvinceInputField());
    }

    public static String getZipValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getZipInputField());
    }

    public static String getPhoneCodeValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getPhoneCodeInputField());
    }

    public static String getPhoneValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getPhoneInputField());
    }

    public static String getEmailValue() {
        return Utils.getInputFieldValue(new VNextBOCompanyInfoPage().getEmailInputField());
    }
}
