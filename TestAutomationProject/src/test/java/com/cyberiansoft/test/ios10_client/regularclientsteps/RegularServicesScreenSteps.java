package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularPriceMatricesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSubServicesScreen;

public class RegularServicesScreenSteps {

    public static void selectServiceWithServiceData(ServiceData serviceData) {
        openCustomServiceDetails(serviceData.getServiceName());
        RegularServiceDetailsScreenSteps.serServiceDetailsData(serviceData);
    }

    public static void selectService(String serviceName) {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(serviceName);
    }

    public static void selectSubService(String subServiceName) {
        RegularSubServicesScreen subServicesScreen = new RegularSubServicesScreen();
        subServicesScreen.selectServiceSubSrvice(subServiceName);
    }

    public static void openCustomServiceDetails(String serviceName) {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.openCustomServiceDetails(serviceName);
    }

    public static void selectMatrixServiceData(MatrixServiceData matrixServiceData) {
        selectMatrixService(matrixServiceData);
        if (matrixServiceData.getVehiclePartsData() != null) {
            for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
                RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
            }
        }
        if (matrixServiceData.getVehiclePartData() != null) {
            RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(matrixServiceData.getVehiclePartData());
        }
        RegularVehiclePartsScreenSteps.savePriceMatrixData();
    }

    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectSubService(matrixServiceData.getMatrixServiceName());
        if (matrixServiceData.getHailMatrixName() != null) {
            RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen();
            priceMatricesScreen.selectPriceMatrice(matrixServiceData.getHailMatrixName());
        }
    }

    public static void selectBundleService(BundleServiceData bundleServiceData) {
        openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if ((serviceData.getServiceQuantity() != null) || (serviceData.getServicePrice() != null)) {
                selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
                if (serviceData.getServiceQuantity() != null)
                    RegularServiceDetailsScreenSteps.setServiceQuantityValue(serviceData.getServiceQuantity());
                if (serviceData.getServicePrice() != null)
                    RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else
                selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
        }
        RegularServiceDetailsScreenSteps.saveServiceDetails();
    }

    public static void selectServiceWithSubServices(DamageData damageData) {
        for (ServiceData serviceData : damageData.getMoneyServices()) {
            selectService(damageData.getDamageGroupName());
            final String subServiceName = damageData.getDamageGroupName() + " (" + serviceData.getServiceName() + ")";
            selectSubService(subServiceName);
        }
    }

    public static void selectPanelServiceData(DamageData damageData) {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(damageData.getDamageGroupName());
        if (damageData.getMoneyServices() != null) {
            for (ServiceData serviceData : damageData.getMoneyServices())
                selectServiceWithServiceData(serviceData);
        }
        if (damageData.getBundleService() != null) {
            selectBundleService(damageData.getBundleService());
        }
        if (damageData.getMatrixService() != null) {
            selectMatrixServiceData(damageData.getMatrixService());
        }

    }

}
