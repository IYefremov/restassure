package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AccountInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AddressData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.EmailOptionsData;
import com.cyberiansoft.test.vnextbo.interactions.*;

public class VNextBOClientDetailsViewAccordionSteps {

    private VNextBOAccountInfoBlockInteractions accountInfoInteractions;
    private VNextBOAddressBlockInteractions addressBlockInteractions;
    private VNextBOClientInfoBlockInteractions clientInfoBlockInteractions;
    private VNextBOEmailOptionsBlockInteractions emailOptionsBlockInteractions;
    private VNextBOPreferencesBlockInteractions preferencesBlockInteractions;
    private VNextBOMiscellaneousBlockInteractions miscellaneousBlockInteractions;

    public VNextBOClientDetailsViewAccordionSteps() {
        accountInfoInteractions = new VNextBOAccountInfoBlockInteractions();
        addressBlockInteractions = new VNextBOAddressBlockInteractions();
        clientInfoBlockInteractions = new VNextBOClientInfoBlockInteractions();
        emailOptionsBlockInteractions = new VNextBOEmailOptionsBlockInteractions();
        preferencesBlockInteractions = new VNextBOPreferencesBlockInteractions();
        miscellaneousBlockInteractions = new VNextBOMiscellaneousBlockInteractions();
    }

    public void setClientInfoData(Employee employee) {
        clientInfoBlockInteractions.verifyClientInfoBlockIsExpanded();

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

    public void setAccountInfoData(AccountInfoData accountInfoData) {
        accountInfoInteractions.verifyAccountInfoBlockIsExpanded();

        accountInfoInteractions.setAccountingId(accountInfoData.getAccountingId());
        accountInfoInteractions.setAccountingId2(accountInfoData.getAccountingId2());
        accountInfoInteractions.setExportAs(accountInfoData.getExportAs());
        accountInfoInteractions.setClass(accountInfoData.getClassOption());
        accountInfoInteractions.setQbAccount(accountInfoData.getQbAccount());
        accountInfoInteractions.clickPoNumberRequiredCheckbox();
    }

    public void setAddressData(AddressData addressData) {
        addressBlockInteractions.verifyAddressBlockIsExpanded();
        setAddressShipToData(addressData);
        addressBlockInteractions.checkSameAsShipToCheckBox();
        setAddressBillToData(addressData);
    }

    public void setEmailOptionsData(EmailOptionsData emailOptionsData) {
        emailOptionsBlockInteractions.verifyEmailOptionsBlockIsExpanded();

        emailOptionsBlockInteractions.setDefaultRecipient(emailOptionsData.getDefaultRecipient());
        emailOptionsBlockInteractions.setCc(emailOptionsData.getCc());
        emailOptionsBlockInteractions.setBcc(emailOptionsData.getBcc());
    }

    public void setPreferencesData(String defaultArea) {
        preferencesBlockInteractions.verifyPreferencesBlockIsExpanded();

        preferencesBlockInteractions.clickUseSingleWoTypeCheckbox();
        preferencesBlockInteractions.setDefaultArea(defaultArea);
    }

    public void setMiscellaneousData(String notes) {
        miscellaneousBlockInteractions.verifyMiscellaneousBlockIsExpanded();

        miscellaneousBlockInteractions.setNotes(notes);
    }

    private void setAddressShipToData(AddressData addressData) {
        addressBlockInteractions.setAddress1ShipTo(addressData.getAddress1());
        addressBlockInteractions.setAddress2ShipTo(addressData.getAddress2());
        addressBlockInteractions.setCityShipTo(addressData.getCity());
        addressBlockInteractions.setCountryShipTo(addressData.getCountry());
        addressBlockInteractions.setStateProvinceShipTo(addressData.getStateProvince());
        addressBlockInteractions.setZipShipTo(addressData.getZip());
    }

    private void setAddressBillToData(AddressData addressData) {
        addressBlockInteractions.setAddress1BillTo(addressData.getAddress11());
        addressBlockInteractions.setAddress2BillTo(addressData.getAddress22());
        addressBlockInteractions.setCityBillTo(addressData.getCity2());
        addressBlockInteractions.setCountryBillTo(addressData.getCountry2());
        addressBlockInteractions.setStateProvinceBillTo(addressData.getStateProvince2());
        addressBlockInteractions.setZipBillTo(addressData.getZip2());
    }
}