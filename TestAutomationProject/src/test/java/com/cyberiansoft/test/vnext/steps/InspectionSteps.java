package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InspectionSteps {
    public static String createInspection(AppCustomer customer, InspectionTypes inspectionTypes) {
        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        homeScreen.clickInspectionsMenuItem();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickAddInspectionButton();
        VNextCustomersScreen vNextCustomersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        vNextCustomersScreen.selectCustomer(customer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(inspectionTypes);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN("777777");
        String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.saveInspectionViaMenu();
        return inspectionNumber;
    }

    public static String createR360Inspection(AppCustomer customer, InspectionData inspectionData) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickAddInspectionButton();
        VNextCustomersScreen vNextCustomersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        vNextCustomersScreen.selectCustomer(customer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        if (inspectionData.getInsuranceCompanyData() != null) {
            vehicleInfoScreen.changeScreen("Claim");
            VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
            claiminfoscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        }
        vehicleInfoScreen.clickSaveInspectionMenuButton();
        new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        return inspectionNumber;
    }

    public static void archiveInspection(String inspectionNumber) {
        VNextInspectionsScreen inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsscreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.archiveInspection();
        WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOf(DriverBuilder.getInstance().getAppiumDriver().
                findElement(By.xpath("//div[@class='checkbox-item-title' and text()='" + inspectionNumber + "']"))));
    }

    public static void openInspectionMenu(String inspectionId) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickOnInspectionByInspNumber(inspectionId);
    }
}
