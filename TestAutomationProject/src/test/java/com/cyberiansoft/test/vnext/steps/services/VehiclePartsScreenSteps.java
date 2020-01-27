package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePanelsList;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePartsList;

public class VehiclePartsScreenSteps {

    public static void selectVehiclePart(String vehiclePartName) {
        VNextVehiclePartsScreen vehiclePartsScreen  = new VNextVehiclePartsScreen();
        vehiclePartsScreen.selectVehiclePart(vehiclePartName);
    }

    public static void selectVehiclePartData(VehiclePartData vehiclePartData) {
        selectVehiclePart(vehiclePartData.getVehiclePartName());
        VNextVehiclePartInfoPage vehiclePartInfoScreen = new VNextVehiclePartInfoPage();
        selectVehiclePartSizeAndSeverity(vehiclePartData.getVehiclePartSize(),
                vehiclePartData.getVehiclePartSeverity());
        if (vehiclePartData.getVehiclePartAdditionalService() != null)
            vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        if (vehiclePartData.getLaborService() != null) {
            vehiclePartInfoScreen.openVehiclePartLaborServiceDetails(vehiclePartData.getLaborService().getServiceName());
            VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
            serviceDetailsScreen.clickSelectPanelsAndParts();
            VNextLaborServicePanelsList laborServicePanelsList = new VNextLaborServicePanelsList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
            VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(vehiclePartData.getLaborService().getLaborServicePanel());
            laborServicePartsList.selectServiceLaborPart(vehiclePartData.getLaborService().getLaborServicePart());
            laborServicePartsList.saveLaborServiceParts();
            vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        }
        vehiclePartInfoScreen.clickScreenBackButton();
    }

    public static void selectVehiclePartSizeAndSeverity(String vehiclePartSize, String vehiclePartSeverity) {
        VNextVehiclePartInfoPage vehiclePartInfoScreen = new VNextVehiclePartInfoPage();
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartSize);
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartSeverity);
    }

    public static void openAdditionalServiceDetails(String serviceName) {
        VNextVehiclePartInfoPage vehiclePartInfoScreen = new VNextVehiclePartInfoPage();
        vehiclePartInfoScreen.openServiceDetailsScreen(serviceName);
    }

    public static void saveVehicleParts() {
        VNextVehiclePartsScreen vehiclePartsScreen  = new VNextVehiclePartsScreen();
        vehiclePartsScreen.clickVehiclePartsSaveButton();
    }
}
