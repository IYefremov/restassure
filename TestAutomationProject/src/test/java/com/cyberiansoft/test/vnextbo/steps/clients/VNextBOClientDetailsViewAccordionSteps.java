package com.cyberiansoft.test.vnextbo.steps.clients;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOClientDetailsViewAccordionSteps {

    public static void clickClientsInfoTab(String shouldBeExpanded) {
        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getClientsInfo());
        waitUntilPanelIsExpanded(new VNextBOClientsDetailsViewAccordion().getClientsInfoPanel(), shouldBeExpanded);
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickAccountInfoTab(String shouldBeExpanded) {
        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getAccountInfo());
        waitUntilPanelIsExpanded(new VNextBOClientsDetailsViewAccordion().getAccountInfoPanel(), shouldBeExpanded);
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickAddressTab(String shouldBeExpanded) {
        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getAddress());
        waitUntilPanelIsExpanded(new VNextBOClientsDetailsViewAccordion().getAddressPanel(), shouldBeExpanded);
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickEmailOptionsTab(String shouldBeExpanded) {
        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getEmailOptions());
        waitUntilPanelIsExpanded(new VNextBOClientsDetailsViewAccordion().getEmailOptionsPanel(), shouldBeExpanded);
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickPreferencesTab(String shouldBeExpanded) {
        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getPreferences());
        waitUntilPanelIsExpanded(new VNextBOClientsDetailsViewAccordion().getPreferencesPanel(), shouldBeExpanded);
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickMiscellaneousTab(String shouldBeExpanded) {
        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getMiscellaneous());
        waitUntilPanelIsExpanded(new VNextBOClientsDetailsViewAccordion().getMiscellaneousPanel(), shouldBeExpanded);
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickServicesTab(String shouldBeExpanded) {
        Utils.clickElement(new VNextBOClientsDetailsViewAccordion().getServices());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickCancelButton() {
        WebElement cancelButton = new VNextBOClientsDetailsViewAccordion().getCancelButton();
        Utils.clickElement(cancelButton);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clickOkButton() {
        WebElement okButton = new VNextBOClientsDetailsViewAccordion().getOkButton();
        Utils.clickElement(okButton);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void setClientInfoData(Employee employee) {
        WaitUtilsWebDriver.waitABit(2000);
        WaitUtilsWebDriver.waitForElementToBeClickable(new VNextBOClientsDetailsViewAccordion().getClientsInfo(), 2);
        if (!VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded()) clickClientsInfoTab("true");
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

        if (!VNextBOClientDetailsValidations.verifyAccountInfoPanelIsExpanded()) clickAccountInfoTab("true");
        final VNextBOAccountInfoBlockInteractions accountInfoBlockInteractions = new VNextBOAccountInfoBlockInteractions();
        accountInfoBlockInteractions.setAccountingId(accountInfoData.getAccountingId());
        accountInfoBlockInteractions.setAccountingId2(accountInfoData.getAccountingId2());
        accountInfoBlockInteractions.setExportAs(accountInfoData.getExportAs());
        accountInfoBlockInteractions.setClass(accountInfoData.getClassOption());
        accountInfoBlockInteractions.setQbAccount(accountInfoData.getQbAccount());
        accountInfoBlockInteractions.clickPoNumberRequiredCheckbox();
    }

    public static void setAddressData(AddressData addressData) {

        if (!VNextBOClientDetailsValidations.verifyAddressPanelIsExpanded()) clickAddressTab("true");
        setAddressShipToData(addressData);
        setAddressBillToData(addressData);
    }

    public static void setEmailOptionsData(EmailOptionsData emailOptionsData, boolean wholesale) {

        if (!VNextBOClientDetailsValidations.verifyEmailOptionsBlockIsExpanded()) clickEmailOptionsTab("true");
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

        if (!VNextBOClientDetailsValidations.verifyPreferencesBlockIsExpanded()) clickPreferencesTab("true");
        final VNextBOPreferencesBlockInteractions preferencesBlockInteractions = new VNextBOPreferencesBlockInteractions();
        preferencesBlockInteractions.clickUseSingleWoTypeCheckbox();
        preferencesBlockInteractions.clickVehicleHistoryEnforcedCheckbox();
        preferencesBlockInteractions.setDefaultArea(defaultArea);
    }

    public static void setMiscellaneousData(String notes) {

        if (!VNextBOClientDetailsValidations.verifyMiscellaneousBlockIsExpanded()) clickMiscellaneousTab("true");
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

    private static void waitUntilPanelIsExpanded(WebElement infoPanel, String shouldBeExpanded) {
        WaitUtilsWebDriver.getWebDriverWait(2).
                until(ExpectedConditions.attributeToBe(infoPanel, "aria-expanded", shouldBeExpanded));
    }
}