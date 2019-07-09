package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.BundleServiceData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import org.testng.Assert;

import java.util.List;

public class ServicesScreenSteps {

    public static void verifyServicesAreSelected(List<ServiceData> servicesData) {
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
        ServiceDetailsScreenSteps.setServiceDetailsData(serviceData);
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
                ServiceDetailsScreenSteps.saveServiceDetails();
            } else
                selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
        }
        ServiceDetailsScreenSteps.saveServiceDetails();
    }

    public static void selectService(String sericeName) {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(sericeName);
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
        PriceMatrixScreenSteps.savePriceMatrixData();
    }

    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        if (matrixServiceData.getHailMatrixName() != null) {
            servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
        }
    }
}