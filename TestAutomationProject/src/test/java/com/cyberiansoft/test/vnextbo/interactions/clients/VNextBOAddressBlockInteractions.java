package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOAddressBlock;
import org.openqa.selenium.support.PageFactory;

public class VNextBOAddressBlockInteractions {

    private VNextBOAddressBlock addressBlock;

    public VNextBOAddressBlockInteractions() {
        addressBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOAddressBlock.class);
    }


    //ship to
    public void setAddress1ShipTo(String address1ShipTo) {
        Utils.clearAndType(addressBlock.getAddress1ShipToInputField(), address1ShipTo);
    }

    public void setAddress2ShipTo(String address2ShipTo) {
        Utils.clearAndType(addressBlock.getAddress2ShipToInputField(), address2ShipTo);
    }

    public void setCityShipTo(String cityShipTo) {
        Utils.clearAndType(addressBlock.getCityShipToInputField(), cityShipTo);
    }

    public void setCountryShipTo(String country) {
        Utils.clearAndType(addressBlock.getCountryShipToInputField(), country);
        Utils.selectOptionInDropDownWithJs(addressBlock.getCountryShipToDropDown(),
                addressBlock.getCountryShipToListBoxOptionByText(country));
    }

    public void setStateProvinceShipTo(String stateProvince) {
        Utils.clearAndType(addressBlock.getStateProvinceShipToInputField(), stateProvince);
        Utils.selectOptionInDropDownWithJs(addressBlock.getStateProvinceShipToDropDown(),
                addressBlock.getStateProvinceShipToListBoxOptionByText(stateProvince));
    }

    public void setZipShipTo(String zipShipTo) {
        Utils.clearAndType(addressBlock.getZipShipToInputField(), zipShipTo);
    }


    //bill to
    public void checkSameAsShipToCheckBox() {
        Utils.clickElement(addressBlock.getSameAsShipToCheckbox());
    }

    public void setAddress1BillTo(String address1BillTo) {
        Utils.clearAndType(addressBlock.getAddress1BillToInputField(), address1BillTo);
    }

    public void setAddress2BillTo(String address2BillTo) {
        Utils.clearAndType(addressBlock.getAddress2BillToInputField(), address2BillTo);
    }

    public void setCityBillTo(String cityBillTo) {
        Utils.clearAndType(addressBlock.getCityBillToInputField(), cityBillTo);
    }

    public void setCountryBillTo(String country) {
        Utils.clearAndType(addressBlock.getCountryBillToInputField(), country);
        Utils.selectOptionInDropDownWithJs(addressBlock.getCountryBillToDropDown(),
                addressBlock.getCountryBillToListBoxOptionByText(country));
    }

    public void setStateProvinceBillTo(String stateProvince) {
        Utils.clearAndType(addressBlock.getStateProvinceBillToInputField(), stateProvince);
        Utils.selectOptionInDropDownWithJs(addressBlock.getStateProvinceBillToDropDown(),
                addressBlock.getStateProvinceBillToListBoxOptionByText(stateProvince));
    }

    public void setZipBillTo(String zipBillTo) {
        Utils.clearAndType(addressBlock.getZipBillToInputField(), zipBillTo);
    }
}