package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.PriceMatrixScreenData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.InspectionToolBar;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TechniciansPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import org.testng.Assert;

import java.util.List;

public class PriceMatrixScreenSteps {
    public static void goTopriceMatrixScreenAndSelectPriceMatrixData(PriceMatrixScreenData priceMatrixScreenData) {
        NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());


        if (priceMatrixScreenData.getVehiclePartsData() != null) {
            for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
                selectVehiclePartAndSetData(vehiclePartData);
            }
        } else if (priceMatrixScreenData.getVehiclePartData() != null)
            selectVehiclePartAndSetData(priceMatrixScreenData.getVehiclePartData());
        if (priceMatrixScreenData.getMatrixScreenPrice() != null) {
            InspectionToolBar inspectionToolBar = new InspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());
        }

        if (priceMatrixScreenData.getMatrixScreenTotalPrice() != null) {
            InspectionToolBar inspectionToolBar = new InspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), priceMatrixScreenData.getMatrixScreenTotalPrice());
        }

    }

    public static void selectVehiclePartAndSetData(VehiclePartData vehiclePartData) {
        selectVehiclePart(vehiclePartData);
        if (vehiclePartData.getVehiclePartPrice() != null)
            setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        if (vehiclePartData.getVehiclePartTime() != null)
            setVehiclePartTime(vehiclePartData.getVehiclePartTime());
        if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                selectVehiclepartAdditionalService(serviceData);
            }
        }
        if (vehiclePartData.getVehiclePartAdditionalService() != null) {
            selectVehiclepartAdditionalService(vehiclePartData.getVehiclePartAdditionalService());
        }
    }

    public static void setVehiclePartTime(String vehiclePartTime) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.setTime(vehiclePartTime);
    }

    public static void setVehiclePartPrice(String vehiclePartPrice) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.setPrice(vehiclePartPrice);
    }

    public static void selectVehiclePart(VehiclePartData vehiclePartData) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        if (vehiclePartData.getVehiclePartOption() != null)
            priceMatrixScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
        if (vehiclePartData.getVehiclePartSize() != null)
            priceMatrixScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
    }

    public static void selectVehiclepartAdditionalService(ServiceData serviceData) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        if ((serviceData.getServiceQuantity() != null) || (serviceData.getServicePrice() != null)) {
            openVehiclePartAdditionalServiceDetails(serviceData);
            ServiceDetailsScreenSteps.setServiceDetailsDataAndSave(serviceData);
        } else
            priceMatrixScreen.selectDiscaunt(serviceData.getServiceName());
    }

    public static void openVehiclePartAdditionalServiceDetails(ServiceData serviceData) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.clickDiscaunt(serviceData.getServiceName());
    }

    public static void verifyIfVehiclePartContainsPriceValue(VehiclePartData vehiclePartData) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartPriceValue(vehiclePartData.getVehiclePartName()), vehiclePartData.getVehiclePartTotalPrice());
    }

    public static void savePriceMatrixData() {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.clickSaveButton();
    }

    public static void verifyVehiclePartTechnicianValue(ServiceTechnician serviceTechnician) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        Assert.assertEquals(priceMatrixScreen.getTechniciansValue(), serviceTechnician.getTechnicianFullName());
    }

    public static void verifyVehiclePartTechniciansValue(List<ServiceTechnician> serviceTechnicians) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        for (ServiceTechnician serviceTechnician : serviceTechnicians)
            Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
    }

    public static void selectVehiclePartTechnician(ServiceTechnician serviceTechnician) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.clickOnTechnicians();
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        techniciansPopup.selecTechnician(serviceTechnician.getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
    }


}
