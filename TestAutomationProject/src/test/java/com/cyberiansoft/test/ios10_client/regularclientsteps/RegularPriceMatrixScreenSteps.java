package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.PriceMatrixScreenData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularInspectionToolBar;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularPriceMatricesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import org.testng.Assert;

public class RegularPriceMatrixScreenSteps {

    public static void selectPriceMatrixData(PriceMatrixScreenData priceMatrixScreenData) {
        if (priceMatrixScreenData.getMatrixService() != null) {
            RegularServicesScreenSteps.selectMatrixServiceData(priceMatrixScreenData.getMatrixService());
        } else if (priceMatrixScreenData.getVehiclePartsData() != null) {
            for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
                RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
            }
        } else if (priceMatrixScreenData.getVehiclePartData() != null)
            RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(priceMatrixScreenData.getVehiclePartData());
    }



}
