package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.ListSelectPage;
import com.cyberiansoft.test.vnext.screens.PartInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

import java.util.List;

public class AvailableServicesScreenSteps {

    public static void selectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToAvalableServicesView();
        servicesScreen.selectServices(serviceDataList);
    }

    public static void selectPartService(String serviceName, String subCategory, String partName, String partPosition) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        ListSelectPage listPage = new ListSelectPage();
        PartInfoScreen partInfoScreen = new PartInfoScreen();

        servicesScreen.selectPartService(serviceName);
        WaitUtils.collectionSizeIsGreaterThan(listPage.getItemList(), 0);
        listPage.getItemElementByText(subCategory).click();
        BaseUtils.waitABit(2000);
        listPage.getItemElementByText(partName).click();
        WaitUtils.waitUntilElementIsClickable(listPage.getSaveButton());
        WaitUtils.click(listPage.getSaveButton());
        BaseUtils.waitABit(2000);
        WaitUtils.collectionSizeIsGreaterThan(listPage.getItemList(), 0);
        WaitUtils.click(listPage.getItemElementByText(partPosition));
        WaitUtils.elementShouldBeVisible(partInfoScreen.getRootElement(), true);
        partInfoScreen.getSaveButton().click();
    }

    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        if (matrixServiceData.getHailMatrixName() != null) {
            VNextPriceMatrixesScreen priceMatrixesScreen = new VNextPriceMatrixesScreen() ;
            priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        }
    }
}
