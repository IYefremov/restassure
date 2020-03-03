package com.cyberiansoft.test.vnextbo.validations.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AccountInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AddressData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.EmailOptionsData;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.*;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOClientDetailsValidations {

    public static boolean verifyClientInfoPanelIsExpanded() {
        return isPanelExpanded(new VNextBOClientInfoBlock().getClientInfoPanel());
    }

    public static boolean verifyAccountInfoPanelIsExpanded() {
        return isPanelExpanded(new VNextBOAccountInfoBlock().getAccountInfoPanel());
    }

    public static boolean verifyAddressPanelIsExpanded() {
        return isPanelExpanded(new VNextBOAddressBlock().getAddressInfoPanel());
    }

    public static boolean verifyEmailOptionsBlockIsExpanded() {
        return isPanelExpanded(new VNextBOEmailOptionsBlock().getEmailOptionsPanel());
    }

    public static boolean verifyPreferencesBlockIsExpanded() {
        return isPanelExpanded(new VNextBOPreferencesBlock().getPreferencesPanel());
    }

    public static boolean verifyMiscellaneousBlockIsExpanded() {
        return isPanelExpanded(new VNextBOMiscellaneousBlock().getMiscellaneousPanel());
    }

    public static boolean isPanelExpanded(WebElement element) {
        if (element.getAttribute("aria-expanded") == null)
            return false;
        else
            return element.getAttribute("aria-expanded").equals("true");
    }

    public static void verifyClientInfoFieldsContainCorrectData(Employee employee) {


        VNextBOClientInfoBlock clientInfoBlock = new VNextBOClientInfoBlock();
        WaitUtilsWebDriver.waitForElementToBeClickable(clientInfoBlock.getCompanyInputField());
        Assert.assertEquals(Utils.getInputFieldValue(clientInfoBlock.getCompanyInputField()), employee.getCompanyName(),
                "\"Company\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(clientInfoBlock.getFirstNameInputField()), employee.getEmployeeFirstName(),
                "\"First name\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(clientInfoBlock.getLastNameInputField()), employee.getEmployeeLastName(),
                "\"Last name\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(clientInfoBlock.getEmailInputField()), employee.getEmployeeEmail(),
                "\"Email\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(clientInfoBlock.getPhoneInputField()), employee.getPhoneNumber(),
                "\"Phone\" field has contained incorrect value");
        if (employee.getClientType().equals("Retail")) {
            Assert.assertEquals(clientInfoBlock.getRetailRadioButton().getAttribute("checked"), "true",
                    "\"Retail\" radiobutton hasn't been checked");
            Assert.assertNull(clientInfoBlock.getWholesaleRadioButton().getAttribute("checked"), "\"Wholesale\" radiobutton hasn't been checked");
        }
        else if (employee.getClientType().equals("Wholesale")) {
            Assert.assertNull(clientInfoBlock.getRetailRadioButton().getAttribute("checked"), "\"Retail\" radiobutton hasn't been checked");
            Assert.assertEquals(clientInfoBlock.getWholesaleRadioButton().getAttribute("checked"), "true",
                    "\"Wholesale\" radiobutton hasn't been checked");
        }

    }

    public static void verifyAccountInfoFieldsContainCorrectData(AccountInfoData accountInfoData, boolean poNumberRequiredCheckbox) {

        VNextBOAccountInfoBlock accountInfoBlock = new VNextBOAccountInfoBlock();
        WaitUtilsWebDriver.waitForElementToBeClickable(accountInfoBlock.getAccountingId());
        Assert.assertEquals(Utils.getInputFieldValue(accountInfoBlock.getAccountingId()), accountInfoData.getAccountingId(),
                "\"Accounting ID\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(accountInfoBlock.getAccountingId2()), accountInfoData.getAccountingId2(),
                "\"Accounting ID2\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(accountInfoBlock.getExportAsInputField()), accountInfoData.getExportAs(),
                "\"Export As\" field has contained incorrect value");
        Assert.assertEquals(Utils.getText(accountInfoBlock.getClassField()), accountInfoData.getClassOption(),
                "\"Class\" field has contained incorrect value");
        Assert.assertEquals(Utils.getText(accountInfoBlock.getQbAccountField()), accountInfoData.getQbAccount(),
                "\"QB Account\" field has contained incorrect value");
        if (poNumberRequiredCheckbox) {
            Assert.assertEquals(accountInfoBlock.getPoNumberRequiredCheckbox().getAttribute("checked"), "true",
                    "\"PO# required\" checkbox hasn't been checked");
        } else {
            Assert.assertNull(accountInfoBlock.getPoNumberRequiredCheckbox().getAttribute("checked"), "\"PO# required\" checkbox hasn't been checked");
        }
        Assert.assertNull(accountInfoBlock.getPoNumberUpfrontRequiredCheckbox().getAttribute("checked"), "\"PO# Upfront required\" checkbox hasn't been checked");
    }

    public static void verifyAddressFieldsContainCorrectData(AddressData addressData) {

        VNextBOAddressBlock addressBlock = new VNextBOAddressBlock();
        //Ship To
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getAddress1ShipToInputField()), addressData.getAddress1(),
                "\"Ship To Address 1\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getAddress2ShipToInputField()), addressData.getAddress2(),
                "\"Ship To Address 2\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getCityShipToInputField()), addressData.getCity(),
                "\"Ship To City\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getCountryShipToInputField()), addressData.getCountry(),
                "\"Ship To Country\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getStateProvinceShipToInputField()), addressData.getStateProvince(),
                "\"Ship To State/Province\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getZipShipToInputField()), addressData.getZip(),
                "\"Ship To Zip/Postal Code\" field has contained incorrect value");
        //Bill To
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getAddress1BillToInputField()), addressData.getAddress11(),
                "\"Bill To Address 1\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getAddress2BillToInputField()), addressData.getAddress22(),
                "\"Bill To Address 2\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getCityBillToInputField()), addressData.getCity2(),
                "\"Bill To City\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getCountryBillToInputField()), addressData.getCountry2(),
                "\"Bill To Country\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getStateProvinceBillToInputField()), addressData.getStateProvince2(),
                "\"Bill To State/Province\" field has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(addressBlock.getZipBillToInputField()), addressData.getZip2(),
                "\"Bill To Zip/Postal Code\" field has contained incorrect value");
    }

    public static void verifyEmailOptionsFieldsContainCorrectData(EmailOptionsData emailOptionsData, boolean wholesale, boolean selectedCheckboxes) {

        VNextBOEmailOptionsBlock emailOptionsBlock = new VNextBOEmailOptionsBlock();
        Assert.assertEquals(Utils.getInputFieldValue(emailOptionsBlock.getDefaultRecipientInputField()), emailOptionsData.getDefaultRecipient(),
                "\"Default recipient\" textarea has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(emailOptionsBlock.getCcInputField()), emailOptionsData.getCc(),
                "\"CC\" textarea has contained incorrect value");
        Assert.assertEquals(Utils.getInputFieldValue(emailOptionsBlock.getBccInputField()), emailOptionsData.getBcc(),
                "\"BCC\" textarea has contained incorrect value");
        if (wholesale & selectedCheckboxes) {
            Assert.assertEquals(emailOptionsBlock.getInvoicesCheckbox().getAttribute("checked"), "true",
                    "\"Invoices\" checkbox hasn't been checked");
            Assert.assertEquals(emailOptionsBlock.getInspectionsCheckbox().getAttribute("checked"), "true",
                    "\"Inspections\" checkbox hasn't been checked");
            Assert.assertEquals(emailOptionsBlock.getIncludeInspectionCheckbox().getAttribute("checked"), "true",
                    "\"Include Inspection\" checkbox hasn't been checked");
        } else {
            Assert.assertNull(emailOptionsBlock.getInvoicesCheckbox().getAttribute("checked"), "\"Invoices\" checkbox has been checked");
            Assert.assertNull(emailOptionsBlock.getInspectionsCheckbox().getAttribute("checked"), "\"Inspections\" checkbox has been checked");
            Assert.assertNull(emailOptionsBlock.getIncludeInspectionCheckbox().getAttribute("checked"), "\"Include Inspection\" checkbox has been checked");
        }
    }

    public static void verifyPreferencesFieldsContainCorrectData(String defaultArea, boolean selectedCheckboxes) {

        VNextBOPreferencesBlock preferencesBlock = new VNextBOPreferencesBlock();
        Assert.assertEquals(Utils.getText(preferencesBlock.getDefaultAreaField()), defaultArea,
                "\"Default Area\" field has contained incorrect value");
        if (selectedCheckboxes)
        {
            Assert.assertEquals(preferencesBlock.getUseSingleWoTypeCheckbox().getAttribute("checked"), "true",
                    "\"Use Single WO type\" checkbox hasn't been checked");
            Assert.assertEquals(preferencesBlock.getVehicleHistoryEnforcedCheckbox().getAttribute("checked"), "true",
                    "\"Vehicle History Enforced\" checkbox hasn't been checked");
        } else {
            Assert.assertNull(preferencesBlock.getUseSingleWoTypeCheckbox().getAttribute("checked"), "\"Use Single WO type\" checkbox has been checked");
            Assert.assertNull(preferencesBlock.getVehicleHistoryEnforcedCheckbox().getAttribute("checked"), "\"Vehicle History Enforced\" checkbox has been checked");
        }

    }

    public static void verifyMiscellaneousFieldsContainCorrectData(String notes, boolean wholesale) {

        VNextBOMiscellaneousBlock miscellaneousBlock = new VNextBOMiscellaneousBlock();
        Assert.assertEquals(Utils.getInputFieldValue(miscellaneousBlock.getNotesField()), notes,
                "\"Notes\" textarea has contained incorrect value");
        if (wholesale) {
            Assert.assertTrue(Utils.isElementDisplayed(miscellaneousBlock.getUploadButton()),
                    "\"Upload\" button hasn't been displayed");
            Assert.assertTrue(Utils.isElementDisplayed(miscellaneousBlock.getClearButton()),
                    "\"Clear\" button hasn't been displayed");
        }
    }

    public static void verifyAllClientDetailsBlocksData(VNextBOClientsData clientsData, boolean wholesale, boolean selectedCheckboxes) {

        if (!VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded()) VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab("true");
        VNextBOClientDetailsValidations.verifyClientInfoFieldsContainCorrectData(clientsData.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab("true");
        VNextBOClientDetailsValidations.verifyAccountInfoFieldsContainCorrectData(clientsData.getAccountInfoData(), selectedCheckboxes);
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab("true");
        VNextBOClientDetailsValidations.verifyAddressFieldsContainCorrectData(clientsData.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab("true");
        VNextBOClientDetailsValidations.verifyEmailOptionsFieldsContainCorrectData(clientsData.getEmailOptionsData(), wholesale, selectedCheckboxes);
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab("true");
        VNextBOClientDetailsValidations.verifyPreferencesFieldsContainCorrectData(clientsData.getDefaultArea(), selectedCheckboxes);
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab("true");
        VNextBOClientDetailsValidations.verifyMiscellaneousFieldsContainCorrectData(clientsData.getNotes(), wholesale);
    }
}