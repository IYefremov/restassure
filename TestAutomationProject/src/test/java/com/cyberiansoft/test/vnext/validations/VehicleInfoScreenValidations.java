package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.ControlUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

public class VehicleInfoScreenValidations {
    public static void validateVehicleInfo(VehicleInfoData vehicleInfoDto) {
        if (vehicleInfoDto.getVINNumber() != null)
            dataFieldShouldHaveValue(VehicleDataField.VIN, vehicleInfoDto.getVINNumber());
        if (vehicleInfoDto.getVehicleMake() != null)
            dataFieldShouldHaveValue(VehicleDataField.MAKE, vehicleInfoDto.getVehicleMake());
        if (vehicleInfoDto.getVehicleModel() != null)
            dataFieldShouldHaveValue(VehicleDataField.MODEL, vehicleInfoDto.getVehicleModel());
        if (vehicleInfoDto.getVehicleYear() != null)
            dataFieldShouldHaveValue(VehicleDataField.YEAR, vehicleInfoDto.getVehicleYear());
        if (vehicleInfoDto.getMileage() != null)
            dataFieldShouldHaveValue(VehicleDataField.MILAGE, vehicleInfoDto.getMileage());
        if (vehicleInfoDto.getStockNumber() != null)
            dataFieldShouldHaveValue(VehicleDataField.STOCK_NO, vehicleInfoDto.getStockNumber());
        if (vehicleInfoDto.getRoNumber() != null)
            dataFieldShouldHaveValue(VehicleDataField.RO_NO, vehicleInfoDto.getRoNumber());
        if (vehicleInfoDto.getPoNumber() != null)
            dataFieldShouldHaveValue(VehicleDataField.PO_NO, vehicleInfoDto.getPoNumber());
        if (vehicleInfoDto.getVehicleLicensePlate() != null)
            dataFieldShouldHaveValue(VehicleDataField.LIC_PLATE, vehicleInfoDto.getVehicleLicensePlate());
        if (vehicleInfoDto.getVehicleColor() != null)
            dataFieldShouldHaveValue(VehicleDataField.COLOR, vehicleInfoDto.getVehicleColor());
    }

    public static void dataFiledShouldBeVisible(VehicleDataField dataField, Boolean shouldBeVisible) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(
                vehicleInfoScreen
                        .getDataFieldList()
                        .stream()
                        .filter(element -> element.getAttribute("name").contains(dataField.getValue()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Vehicle info data not found " + dataField.getValue())),
                shouldBeVisible);
    }

    public static void dataFieldShouldHaveValue(VehicleDataField dataField, String expectedValue) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.collectionSizeIsGreaterThan(vehicleInfoScreen.getDataFieldList(), 0);
        WaitUtils.getGeneralFluentWait().until(driver -> {
                    Assert.assertEquals(
                            ControlUtils.getElementValue(vehicleInfoScreen
                                    .getDataFieldList()
                                    .stream()
                                    .filter(element -> element.getAttribute("name").contains(dataField.getValue()))
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Vehicle info data not found " + dataField.getValue()))), expectedValue
                    );
                    return true;
                }
        );
    }

    public static void vinValidationMessageShouldExist(Boolean shouldExist) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getVehicleFieldsList().findElement(By.xpath(".//div[@class='input-with-validation-message']")), true);
    }

    public static void vinValidationColorShouldBeEqual(String expectedColorString) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(vehicleInfoScreen.getVehicleFieldsList().findElement(By.xpath(".//div[@class='input-with-validation-message']")).getCssValue("background-color"), expectedColorString);
            return true;
        });
    }

    public static void cutomerContextShouldBe(String expectedCustomerContext) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(VehicleInfoScreenInteractions.getCustomerContextValue(), expectedCustomerContext);
            return true;
        });
    }

    public static void ownerShouldBe(String expectedOwner) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(VehicleInfoScreenInteractions.getOwnerValue(), expectedOwner);
            return true;
        });
    }

}
