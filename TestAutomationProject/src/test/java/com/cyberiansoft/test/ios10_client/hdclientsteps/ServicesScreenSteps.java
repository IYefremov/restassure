package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import org.testng.Assert;

import java.util.List;

public class ServicesScreenSteps {

    public static void waitServicesScreenLoad() {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.waitServicesScreenLoaded();
    }

    public static void verifyServicesAreSelected(List<ServiceData> servicesData) {
        waitServicesScreenLoad();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : servicesData) {
            if (serviceData.isSelected())
                Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
            else
                Assert.assertFalse(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        }
    }

    public static void selectServiceWithServiceData(ServiceData serviceData) {
        openCustomServiceDetails(serviceData.getServiceName());
        ServiceDetailsScreenSteps.setServiceDetailsDataAndSave(serviceData);
    }

    public static void openCustomServiceDetails(String serviceName) {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.openCustomServiceDetails(serviceName);
    }

    public static void selectBundleService(BundleServiceData bundleServiceData) {
        openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if ((serviceData.getServiceQuantity() != null) || (serviceData.getServicePrice() != null)) {
                selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
                if (serviceData.getServiceQuantity() != null)
                    ServiceDetailsScreenSteps.setServiceQuantityValue(serviceData.getServiceQuantity());
                if (serviceData.getServicePrice() != null)
                    ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                if (serviceData.getVehiclePart() != null) {
                    ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
                }
                if (serviceData.getVehicleParts() != null) {
                    for (VehiclePartData vehiclePartData : serviceData.getVehicleParts())
                        ServiceDetailsScreenSteps.slectServiceVehiclePart(vehiclePartData);
                }
                ServiceDetailsScreenSteps.saveServiceDetails();
            } else
                selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
        }
        if (bundleServiceData.getBundleServiceAmount() != null)
            selectedservicebundlescreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
        ServiceDetailsScreenSteps.saveServiceDetails();
    }

    public static void selectService(String sericeName) {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(sericeName);
    }

    public static void selectMatrixServiceDataAndSave(MatrixServiceData matrixServiceData) {
        selectMatrixService(matrixServiceData);
        selectMatrixServiceData(matrixServiceData);
        PriceMatrixScreenSteps.savePriceMatrixData();
    }

    public static void selectMatrixServiceData(MatrixServiceData matrixServiceData) {
        selectMatrixService(matrixServiceData);
        if (matrixServiceData.getVehiclePartsData() != null) {
            for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
                PriceMatrixScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
            }
        }
        if (matrixServiceData.getVehiclePartData() != null) {
            PriceMatrixScreenSteps.selectVehiclePartAndSetData(matrixServiceData.getVehiclePartData());
        }
    }

    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        if (matrixServiceData.getHailMatrixName() != null) {
            servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
        }
    }

    public static void selectPanelServiceData(DamageData damageData) {
        selectGroupServiceItem(damageData);
        if (damageData.getMoneyService() != null) {
            selectServiceWithServiceData(damageData.getMoneyService());
        }
        if (damageData.getMoneyServices() != null) {
            for (ServiceData serviceData : damageData.getMoneyServices())
                selectServiceWithServiceData(serviceData);
        }
        if (damageData.getBundleService() != null) {
            selectBundleService(damageData.getBundleService());
        }
        if (damageData.getMatrixService() != null) {
            selectMatrixServiceDataAndSave(damageData.getMatrixService());
        }

    }

    public static void selectGroupServiceItem(DamageData damageData) {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectGroupServiceItem(damageData.getDamageGroupName());

    }

    public static void selectLaborServiceAndSetData(LaborServiceData laborServiceData) {
        openCustomServiceDetails(laborServiceData.getServiceName());
        ServiceDetailsScreenSteps.setLaborServiceData(laborServiceData);
    }

    public static void clickServiceTypesButton() {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.clickServiceTypesButton();
    }
}
