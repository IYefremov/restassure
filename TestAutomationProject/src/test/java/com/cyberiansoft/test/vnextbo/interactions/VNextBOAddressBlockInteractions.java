package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOAddressBlock;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

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
        Utils.selectOptionInDropDown(addressBlock.getCountryShipToDropDown(),
                addressBlock.getCountryShipToListBoxOptions(), country);
    }

    public void setStateProvinceShipTo(String stateProvince) {
        Utils.clearAndType(addressBlock.getStateProvinceShipToInputField(), stateProvince);
        Utils.selectOptionInDropDown(addressBlock.getStateProvinceShipToDropDown(),
                addressBlock.getCountryShipToListBoxOptions(), stateProvince);
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
        Utils.selectOptionInDropDown(addressBlock.getCountryBillToDropDown(),
                addressBlock.getCountryBillToListBoxOptions(), country);
    }

    public void setStateProvinceBillTo(String stateProvince) {
        Utils.clearAndType(addressBlock.getStateProvinceBillToInputField(), stateProvince);
        Utils.selectOptionInDropDown(addressBlock.getStateProvinceBillToDropDown(),
                addressBlock.getCountryBillToListBoxOptions(), stateProvince);
    }

    public void setZipBillTo(String zipBillTo) {
        Utils.clearAndType(addressBlock.getZipBillToInputField(), zipBillTo);
    }

    public void verifyAddressBlockIsExpanded() {
        final List<WebElement> addressBlockElements = Arrays.asList(
                addressBlock.getAddress1ShipToInputField(),
                addressBlock.getAddress2ShipToInputField(),
                addressBlock.getCityShipToInputField(),
                addressBlock.getCountryShipToInputField(),
                addressBlock.getStateProvinceShipToInputField(),
                addressBlock.getZipShipToInputField());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> addressBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickAddressTab();
        }
    }
}