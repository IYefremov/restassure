package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AccountInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AddressData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.EmailOptionsData;
import com.cyberiansoft.test.vnextbo.interactions.clients.*;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOClientsDetailsViewAccordion;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientDetailsValidations;

public class VNextBOClientDetailsViewAccordionSteps {

    public static void clickClientsInfoTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getClientsInfo());
        BaseUtils.waitABit(1000);
    }

    public static void clickAccountInfoTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getAccountInfo());
        BaseUtils.waitABit(1000);
    }

    public static void clickAddressTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getAddress());
        BaseUtils.waitABit(1000);
    }

    public static void clickEmailOptionsTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getEmailOptions());
        BaseUtils.waitABit(1000);
    }

    public static void clickPreferencesTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getPreferences());
        BaseUtils.waitABit(1000);
    }

    public static void clickMiscellaneousTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getMiscellaneous());
        BaseUtils.waitABit(1000);
    }

    public static void clickServicesTab() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getServices());
        BaseUtils.waitABit(1000);
    }

    public static void clickCancelButton() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getCancelButton());
        //WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickOkButton() {

        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getOkButton());
        //WaitUtilsWebDriver.waitForLoading();
    }

    public static void setClientInfoData(Employee employee) {

        WaitUtilsWebDriver.waitForElementToBeClickable(new VNextBOClientsDetailsViewAccordion().getClientsInfo());
        if (!VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded()) clickClientsInfoTab();
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

        if (!VNextBOClientDetailsValidations.verifyAccountInfoPanelIsExpanded()) clickAccountInfoTab();
        final VNextBOAccountInfoBlockInteractions accountInfoBlockInteractions = new VNextBOAccountInfoBlockInteractions();
        accountInfoBlockInteractions.setAccountingId(accountInfoData.getAccountingId());
        accountInfoBlockInteractions.setAccountingId2(accountInfoData.getAccountingId2());
        accountInfoBlockInteractions.setExportAs(accountInfoData.getExportAs());
        accountInfoBlockInteractions.setClass(accountInfoData.getClassOption());
        accountInfoBlockInteractions.setQbAccount(accountInfoData.getQbAccount());
        accountInfoBlockInteractions.clickPoNumberRequiredCheckbox();
    }

    public static void setAddressData(AddressData addressData) {

        if (!VNextBOClientDetailsValidations.verifyAddressPanelIsExpanded()) clickAddressTab();
        setAddressShipToData(addressData);
        setAddressBillToData(addressData);
    }

    public static void setEmailOptionsData(EmailOptionsData emailOptionsData, boolean wholesale) {

        if (!VNextBOClientDetailsValidations.verifyEmailOptionsBlockIsExpanded()) clickEmailOptionsTab();
        VNextBOEmailOptionsBlockInteractions.setDefaultRecipient(emailOptionsData.getDefaultRecipient());
        VNextBOEmailOptionsBlockInteractions.setCc(emailOptionsData.getCc());
        VNextBOEmailOptionsBlockInteractions.setBcc(emailOptionsData.getBcc());
        if (wholesale) {
            VNextBOEmailOptionsBlockInteractions.clickInvoicesCheckbox();
            VNextBOEmailOptionsBlockInteractions.clickIInspectionsCheckbox();
            VNextBOEmailOptionsBlockInteractions.clickIncludeInspectionCheckbox();
        }
    }

    public static void setPreferencesData(String defaultArea) {

        if (!VNextBOClientDetailsValidations.verifyPreferencesBlockIsExpanded()) clickPreferencesTab();
        final VNextBOPreferencesBlockInteractions preferencesBlockInteractions = new VNextBOPreferencesBlockInteractions();
        preferencesBlockInteractions.clickUseSingleWoTypeCheckbox();
        preferencesBlockInteractions.clickVehicleHistoryEnforcedCheckbox();
        preferencesBlockInteractions.setDefaultArea(defaultArea);
    }

    public static void setMiscellaneousData(String notes) {

        if (!VNextBOClientDetailsValidations.verifyMiscellaneousBlockIsExpanded()) clickMiscellaneousTab();
        new VNextBOMiscellaneousBlockInteractions().setNotes(notes);
    }

    public static void setAllClientsData(VNextBOClientsData clientsData, boolean wholesale){

        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(clientsData.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(clientsData.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.setAddressData(clientsData.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(clientsData.getEmailOptionsData(), wholesale);
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(clientsData.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(clientsData.getNotes());
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