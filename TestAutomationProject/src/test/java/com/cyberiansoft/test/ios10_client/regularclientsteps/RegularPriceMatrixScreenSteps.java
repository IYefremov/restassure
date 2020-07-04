package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.PriceMatrixScreenData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;

public class RegularPriceMatrixScreenSteps {

    public static void selectPriceMatrixData(PriceMatrixScreenData priceMatrixScreenData) {
        if (priceMatrixScreenData.getMatrixService() != null) {
            RegularServicesScreenSteps.selectMatrixServiceData(priceMatrixScreenData.getMatrixService());
        } else if (priceMatrixScreenData.getVehiclePartsData() != null) {
            for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
                RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
                RegularVehiclePartsScreenSteps.saveVehiclePart();
            }
        } else if (priceMatrixScreenData.getVehiclePartData() != null)
            RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(priceMatrixScreenData.getVehiclePartData());
    }

    public static void savePriceMatrix() {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.waitPriceMatrixScreenLoad();
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickSave();
    }



}
