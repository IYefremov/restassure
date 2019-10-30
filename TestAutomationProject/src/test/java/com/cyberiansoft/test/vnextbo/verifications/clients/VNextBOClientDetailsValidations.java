package com.cyberiansoft.test.vnextbo.verifications.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AccountInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AddressData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.EmailOptionsData;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.*;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import org.testng.Assert;

public class VNextBOClientDetailsValidations {

    public static boolean isClientInfoPanelExpanded() {

        return Utils.isElementDisplayed(new VNextBOClientInfoBlock().getClientInfoPanel());
    }

    public static boolean isAccountInfoPanelExpanded() {

        return Utils.isElementDisplayed(new VNextBOAccountInfoBlock().getAccountInfoPanel());
    }

    public static boolean isAddressPanelExpanded() {

        return Utils.isElementDisplayed(new VNextBOAddressBlock().getAddressInfoPanel());
    }

    public static boolean isEmailOptionsBlockExpanded() {

        return Utils.isElementDisplayed(new VNextBOEmailOptionsBlock().getEmailOptionsPanel());
    }

    public static boolean isPreferencesBlockExpanded() {

        return Utils.isElementDisplayed(new VNextBOPreferencesBlock().getPreferencesPanel());
    }

    public static boolean isMiscellaneousBlockExpanded() {

        return Utils.isElementDisplayed(new VNextBOMiscellaneousBlock().getMiscellaneousPanel());
    }

    public static void verifyClientInfoFieldsContainCorrectData(Employee employee) {

        VNextBOClientInfoBlock clientInfoBlock = new VNextBOClientInfoBlock();
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
            Assert.assertEquals(clientInfoBlock.getWholesaleRadioButton().getAttribute("checked"), null,
                    "\"Wholesale\" radiobutton hasn't been checked");
        }
        else if (employee.getClientType().equals("Wholesale")) {
            Assert.assertEquals(clientInfoBlock.getRetailRadioButton().getAttribute("checked"), null,
                    "\"Retail\" radiobutton hasn't been checked");
            Assert.assertEquals(clientInfoBlock.getWholesaleRadioButton().getAttribute("checked"), "true",
                    "\"Wholesale\" radiobutton hasn't been checked");
        }

    }

    public static void verifyAccountInfoFieldsContainCorrectData(AccountInfoData accountInfoData, boolean poNumberRequiredCheckbox) {

        VNextBOAccountInfoBlock accountInfoBlock = new VNextBOAccountInfoBlock();
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
            Assert.assertEquals(accountInfoBlock.getPoNumberRequiredCheckbox().getAttribute("checked"), null,
                    "\"PO# required\" checkbox hasn't been checked");
        }
        Assert.assertEquals(accountInfoBlock.getPoNumberUpfrontRequiredCheckbox().getAttribute("checked"), null,
                "\"PO# Upfront required\" checkbox hasn't been checked");
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
            Assert.assertEquals(emailOptionsBlock.getInvoicesCheckbox().getAttribute("checked"), null,
                    "\"Invoices\" checkbox has been checked");
            Assert.assertEquals(emailOptionsBlock.getInspectionsCheckbox().getAttribute("checked"), null,
                    "\"Inspections\" checkbox has been checked");
            Assert.assertEquals(emailOptionsBlock.getIncludeInspectionCheckbox().getAttribute("checked"), null,
                    "\"Include Inspection\" checkbox has been checked");
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
            Assert.assertEquals(preferencesBlock.getUseSingleWoTypeCheckbox().getAttribute("checked"), null,
                    "\"Use Single WO type\" checkbox has been checked");
            Assert.assertEquals(preferencesBlock.getVehicleHistoryEnforcedCheckbox().getAttribute("checked"), null,
                    "\"Vehicle History Enforced\" checkbox has been checked");
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

        if (!VNextBOClientDetailsValidations.isClientInfoPanelExpanded()) VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        VNextBOClientDetailsValidations.verifyClientInfoFieldsContainCorrectData(clientsData.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        VNextBOClientDetailsValidations.verifyAccountInfoFieldsContainCorrectData(clientsData.getAccountInfoData(), selectedCheckboxes);
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        VNextBOClientDetailsValidations.verifyAddressFieldsContainCorrectData(clientsData.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        VNextBOClientDetailsValidations.verifyEmailOptionsFieldsContainCorrectData(clientsData.getEmailOptionsData(), wholesale, selectedCheckboxes);
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        VNextBOClientDetailsValidations.verifyPreferencesFieldsContainCorrectData(clientsData.getDefaultArea(), selectedCheckboxes);
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        VNextBOClientDetailsValidations.verifyMiscellaneousFieldsContainCorrectData(clientsData.getNotes(), wholesale);
    }
}