package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AccountInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AddressData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.EmailOptionsData;
import com.cyberiansoft.test.vnextbo.interactions.clients.*;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOClientAddressFieldsVerifications;

public class VNextBOClientDetailsViewAccordionSteps {

    public static void setClientInfoData(Employee employee) {

        VNextBOClientAddressFieldsVerifications.verifyClientInfoBlockIsExpanded();
        final VNextBOClientInfoBlockInteractions clientInfoBlockInteractions = new VNextBOClientInfoBlockInteractions();
        if (employee.getClientType().toLowerCase().equals("retail")) {
            clientInfoBlockInteractions.setRetailCompanyType();
        } else if (employee.getClientType().toLowerCase().equals("wholesale")) {
            clientInfoBlockInteractions.setRetailCompanyType();
        }
        clientInfoBlockInteractions.setCompanyName(employee.getCompanyName());
        clientInfoBlockInteractions.setFirstName(employee.getEmployeeFirstName());
        clientInfoBlockInteractions.setLastName(employee.getEmployeeLastName());
        clientInfoBlockInteractions.setEmail(employee.getEmployeeEmail());
        clientInfoBlockInteractions.setPhone(employee.getPhoneNumber());
    }

    public static void setAccountInfoData(AccountInfoData accountInfoData) {

        VNextBOClientAddressFieldsVerifications.verifyAccountInfoBlockIsExpanded();

        final VNextBOAccountInfoBlockInteractions accountInfoBlockInteractions = new VNextBOAccountInfoBlockInteractions();
        accountInfoBlockInteractions.setAccountingId(accountInfoData.getAccountingId());
        accountInfoBlockInteractions.setAccountingId2(accountInfoData.getAccountingId2());
        accountInfoBlockInteractions.setExportAs(accountInfoData.getExportAs());
        accountInfoBlockInteractions.setClass(accountInfoData.getClassOption());
        accountInfoBlockInteractions.setQbAccount(accountInfoData.getQbAccount());
        accountInfoBlockInteractions.clickPoNumberRequiredCheckbox();
    }

    public static void setAddressData(AddressData addressData) {

        VNextBOClientAddressFieldsVerifications.verifyAddressBlockIsExpanded();
        setAddressShipToData(addressData);
        new VNextBOAddressBlockInteractions().checkSameAsShipToCheckBox();
        setAddressBillToData(addressData);
    }

    public static void setEmailOptionsData(EmailOptionsData emailOptionsData) {

        VNextBOClientAddressFieldsVerifications.verifyEmailOptionsBlockIsExpanded();
        final VNextBOEmailOptionsBlockInteractions emailOptionsBlockInteractions = new VNextBOEmailOptionsBlockInteractions();
        emailOptionsBlockInteractions.setDefaultRecipient(emailOptionsData.getDefaultRecipient());
        emailOptionsBlockInteractions.setCc(emailOptionsData.getCc());
        emailOptionsBlockInteractions.setBcc(emailOptionsData.getBcc());
    }

    public static void setPreferencesData(String defaultArea) {

        VNextBOClientAddressFieldsVerifications.verifyPreferencesBlockIsExpanded();
        final VNextBOPreferencesBlockInteractions preferencesBlockInteractions = new VNextBOPreferencesBlockInteractions();
        preferencesBlockInteractions.clickUseSingleWoTypeCheckbox();
        preferencesBlockInteractions.setDefaultArea(defaultArea);
    }

    public static void setMiscellaneousData(String notes) {

        VNextBOClientAddressFieldsVerifications.verifyMiscellaneousBlockIsExpanded();
        new VNextBOMiscellaneousBlockInteractions().setNotes(notes);
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