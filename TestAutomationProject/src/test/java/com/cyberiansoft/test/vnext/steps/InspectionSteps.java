package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class InspectionSteps {
    public static void createInspection(AppCustomer customer, InspectionTypes inspectionTypes) {
        CustomersSreenSteps.selectCustomer(customer);
        InspectionSteps.selectInspectionType(inspectionTypes);
        VehicleInfoScreenSteps.setVIN("777777");
    }

    public static void createInspection(AppCustomer customer, InspectionTypes inspectionTypes, InspectionData inspectionData) {
        CustomersSreenSteps.selectCustomer(customer);
        InspectionSteps.selectInspectionType(inspectionTypes);
        VehicleInfoScreenSteps.setVIN(inspectionData.getVinNumber());
    }

    public static String createR360Inspection(AppCustomer customer, InspectionData inspectionData) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickAddInspectionButton();
        VNextCustomersScreen vNextCustomersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        vNextCustomersScreen.selectCustomer(customer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
       VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        if (inspectionData.getInsuranceCompanyData() != null) {
            vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
            VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
            claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
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
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickOnInspectionByInspNumber(inspectionId);
    }

    public static void openInspectionToEdit(String inspectionId) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickOpenInspectionToEdit(inspectionId);
    }

    public static void selectInspectionType(InspectionTypes inspectionTypes) {
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList();
        inspectionTypesList.selectInspectionType(inspectionTypes);
    }

    public static String saveInspection() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String inspectionNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveInspectionViaMenu();
        return inspectionNumber;
    }

    public static void cancelInspection() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog();
        informationDialog.clickInformationDialogYesButton();
    }

    public static void navigateHomeScreen() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickBackButton();
    }

    public static void verifyInspectionTotalPrice(String inspectionId, String expectedPrice) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionId), expectedPrice);
    }

    public static void switchToTeamInspections() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.switchToTeamInspectionsView();
    }

    public static void switchToMyInspections() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.switchToMyInspectionsView();
    }
}
