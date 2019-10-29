package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AccountInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AddressData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.EmailOptionsData;
import com.cyberiansoft.test.vnextbo.interactions.clients.*;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOClientsDetailsViewAccordion;
import com.cyberiansoft.test.vnextbo.verifications.clients.VNextBOClientDetailsValidations;

public class VNextBOClientDetailsViewAccordionSteps {

    public static void clickClientsInfoTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getClientsInfo());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickAccountInfoTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getAccountInfo());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickAddressTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getAddress());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickEmailOptionsTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getEmailOptions());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickPreferencesTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getPreferences());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickMiscellaneousTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getMiscellaneous());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickCancelButton() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getCancelButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickOkButton() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getOkButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setClientInfoData(Employee employee) {

        if (!VNextBOClientDetailsValidations.isClientInfoPanelExpanded()) clickClientsInfoTab();
        final VNextBOClientInfoBlockInteractions clientInfoBlockInteractions = new VNextBOClientInfoBlockInteractions();
        if (employee.getClientType().toLowerCase().equals("retail")) {
            clientInfoBlockInteractions.setRetailCompanyType();
        } else if (employee.getClientType().toLowerCase().equals("wholesale")) {
            clientInfoBlockInteractions.setWholesaleCompanyType();
        }
        clientInfoBlockInteractions.setCompanyName(employee.getCompanyName());
        clientInfoBlockInteractions.setFirstName(employee.getEmployeeFirstName());
        clientInfoBlockInteractions.setLastName(employee.getEmployeeLastName());
        clientInfoBlockInteractions.setEmail(employee.getEmployeeEmail());
        clientInfoBlockInteractions.setPhone(employee.getPhoneNumber());
    }

    public static void setAccountInfoData(AccountInfoData accountInfoData) {

        if (!VNextBOClientDetailsValidations.isAccountInfoPanelExpanded()) clickAccountInfoTab();
        final VNextBOAccountInfoBlockInteractions accountInfoBlockInteractions = new VNextBOAccountInfoBlockInteractions();
        accountInfoBlockInteractions.setAccountingId(accountInfoData.getAccountingId());
        accountInfoBlockInteractions.setAccountingId2(accountInfoData.getAccountingId2());
        accountInfoBlockInteractions.setExportAs(accountInfoData.getExportAs());
        accountInfoBlockInteractions.setClass(accountInfoData.getClassOption());
        accountInfoBlockInteractions.setQbAccount(accountInfoData.getQbAccount());
        accountInfoBlockInteractions.clickPoNumberRequiredCheckbox();
    }

    public static void setAddressData(AddressData addressData) {

        if (!VNextBOClientDetailsValidations.isAddressPanelExpanded()) clickAddressTab();
        setAddressShipToData(addressData);
        setAddressBillToData(addressData);
    }

    public static void setEmailOptionsData(EmailOptionsData emailOptionsData, boolean wholesale) {

        if (!VNextBOClientDetailsValidations.isEmailOptionsBlockExpanded()) clickEmailOptionsTab();
        final VNextBOEmailOptionsBlockInteractions emailOptionsBlockInteractions = new VNextBOEmailOptionsBlockInteractions();
        emailOptionsBlockInteractions.setDefaultRecipient(emailOptionsData.getDefaultRecipient());
        emailOptionsBlockInteractions.setCc(emailOptionsData.getCc());
        emailOptionsBlockInteractions.setBcc(emailOptionsData.getBcc());
        if (wholesale) {
            emailOptionsBlockInteractions.clickInvoicesCheckbox();
            emailOptionsBlockInteractions.clickIInspectionsCheckbox();
            emailOptionsBlockInteractions.clickIncludeInspectionCheckbox();
        }
    }

    public static void setPreferencesData(String defaultArea) {

        if (!VNextBOClientDetailsValidations.isPreferencesBlockExpanded()) clickPreferencesTab();
        final VNextBOPreferencesBlockInteractions preferencesBlockInteractions = new VNextBOPreferencesBlockInteractions();
        preferencesBlockInteractions.clickUseSingleWoTypeCheckbox();
        preferencesBlockInteractions.clickVehicleHistoryEnforcedCheckbox();
        preferencesBlockInteractions.setDefaultArea(defaultArea);
    }

    public static void setMiscellaneousData(String notes) {

        if (!VNextBOClientDetailsValidations.isMiscellaneousBlockExpanded()) clickMiscellaneousTab();
        new VNextBOMiscellaneousBlockInteractions().setNotes(notes);
    }

    public static void setAllClientsData(Employee employee, AccountInfoData accountInfoData, AddressData addressData,
                                               EmailOptionsData emailOptionsData, String defaultArea, String notes, boolean wholesale){

        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(employee);
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(accountInfoData);
        VNextBOClientDetailsViewAccordionSteps.setAddressData(addressData);
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(emailOptionsData, wholesale);
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(defaultArea);
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(notes);
    }

    private static void setAddressShipToData(AddressData addressData) {

        final VNextBOAddressBlockInteractions addressBlockInteractions = new VNextBOAddressBlockInteractions();
        addressBlockInteractions.setAddress1ShipTo(addressData.getAddress1());
        addressBlockInteractions.setAddress2ShipTo(addressData.getAddress2());
        addressBlockInteractions.setCityShipTo(addressData.getCity());
        addressBlockInteractions.setCountryShipTo(addressData.getCountry());
        addressBlockInteractions.setStateProvinceShipTo(addressData.getStateProvince());
        addressBlockInteractions.setZipShipTo(addressData.getZip());
    }

    private static void setAddressBillToData(AddressData addressData) {

        final VNextBOAddressBlockInteractions addressBlockInteractions = new VNextBOAddressBlockInteractions();
        addressBlockInteractions.setAddress1BillTo(addressData.getAddress11());
        addressBlockInteractions.setAddress2BillTo(addressData.getAddress22());
        addressBlockInteractions.setCityBillTo(addressData.getCity2());
        addressBlockInteractions.setCountryBillTo(addressData.getCountry2());
        addressBlockInteractions.setStateProvinceBillTo(addressData.getStateProvince2());
        addressBlockInteractions.setZipBillTo(addressData.getZip2());
    }
}