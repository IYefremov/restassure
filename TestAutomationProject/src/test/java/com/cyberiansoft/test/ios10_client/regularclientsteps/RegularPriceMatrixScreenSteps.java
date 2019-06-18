package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.PriceMatrixScreenData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import org.testng.Assert;

public class RegularPriceMatrixScreenSteps {

    public static void goTopriceMatrixScreenAndSelectPriceMatrixData(PriceMatrixScreenData priceMatrixScreenData) {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
        if (priceMatrixScreenData.getMatrixService() != null) {
            RegularServicesScreen servicesScreen = new RegularServicesScreen();
            servicesScreen.selectSubService(priceMatrixScreenData.getMatrixService().getMatrixServiceName());
            RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
            if (priceMatrixScreenData.getMatrixService().getHailMatrixName() != null)
                priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getMatrixService().getHailMatrixName());
            if (priceMatrixScreenData.getMatrixService().getVehiclePartsData() != null) {
                for (VehiclePartData vehiclePartData : priceMatrixScreenData.getMatrixService().getVehiclePartsData()) {
                    selectPriceMatrixServiceAndSetData(vehiclePartData);
                }
            }
        } else if (priceMatrixScreenData.getVehiclePartsData() != null) {
            for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
                selectPriceMatrixServiceAndSetData(vehiclePartData);
            }
        }

    }

    public static void selectPriceMatrixServiceAndSetData(VehiclePartData vehiclePartData) {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        if (vehiclePartData.getVehiclePartOption() != null)
            vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
        if (vehiclePartData.getVehiclePartSize() != null)
            vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
        if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                selectVehiclepartAdditionalService(serviceData);
            }
        }
        if (vehiclePartData.getVehiclePartAdditionalService() != null) {
            selectVehiclepartAdditionalService(vehiclePartData.getVehiclePartAdditionalService());
        }
        if (vehiclePartData.getVehiclePartTime() != null)
            vehiclePartScreen.setTime(vehiclePartData.getVehiclePartTime());
        vehiclePartScreen.saveVehiclePart();
    }

    public static void selectVehiclepartAdditionalService(ServiceData serviceData) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            if ((serviceData.getServiceQuantity() != null) || (serviceData.getServicePrice() != null)) {
                vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.serServiceDetailsData(serviceData);
            } else
                vehiclePartScreen.selectDiscaunt(serviceData.getServiceName());
    }

    public static void verifyIfVehiclePartContainsPriceValue(VehiclePartData vehiclePartData) {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        Assert.assertTrue(priceMatrixScreen.isPriceMatrixContainsPriceValue(vehiclePartData.getVehiclePartName(), vehiclePartData.getVehiclePartTotalPrice()));
    }

    public static void savePriceMatrixData() {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.clickSave();
    }

}
