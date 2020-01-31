package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
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
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
    }

    public static String createR360Inspection(AppCustomer customer, InspectionData inspectionData) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen.clickAddInspectionButton();
        VNextCustomersScreen vNextCustomersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        vNextCustomersScreen.selectCustomer(customer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        if (inspectionData.getInsuranceCompanyData() != null) {
            vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
            VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
            claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        }
        vehicleInfoScreen.clickSaveInspectionMenuButton();
        new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        return inspectionNumber;
    }

    public static void archiveInspection(String inspectionNumber) {
        VNextInspectionsScreen inspectionsscreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsscreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.archiveInspection();
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOf(ChromeDriverProvider.INSTANCE.getMobileChromeDriver().
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
        WaitUtils.elementShouldBeVisible(baseWizardScreen.getInspectionnumber(), true);
        WaitUtils.getGeneralFluentWait().until(driver -> (baseWizardScreen.getNewInspectionNumber() != "" && baseWizardScreen.getNewInspectionNumber() != null));
        String inspectionNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveInspectionViaMenu();
        return inspectionNumber;
    }

    public static void trySaveInspection() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> (baseWizardScreen.getNewInspectionNumber() != "" && baseWizardScreen.getNewInspectionNumber() != null));
        baseWizardScreen.clickSaveInspectionMenuButton();
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

    public static void selectInspection(String inspectionId) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.selectInspection(inspectionId);
    }

    public static void clickEmailButton() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.getMultiselectInspectionEmailBtn().click();
    }
}
