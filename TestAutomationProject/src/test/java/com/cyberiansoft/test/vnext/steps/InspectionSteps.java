package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.InspectionListElement;

public class InspectionSteps {
    public static void createInspection(AppCustomer customer, InspectionTypes inspectionTypes) {
        CustomersScreenSteps.selectCustomer(customer);
        selectInspectionType(inspectionTypes);
        VehicleInfoScreenSteps.setVIN("777777");
    }

    public static void createInspection(AppCustomer customer, InspectionTypes inspectionTypes, InspectionData inspectionData) {
        CustomersScreenSteps.selectCustomer(customer);
        createInspection(inspectionTypes, inspectionData);
    }

    public static void createInspection(InspectionTypes inspectionTypes, InspectionData inspectionData) {
        InspectionSteps.selectInspectionType(inspectionTypes);
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
    }

    public static String createR360Inspection(AppCustomer customer, InspectionData inspectionData) {
        clickAddInspectionButton();
        CustomersScreenSteps.selectCustomer(customer);
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        if (inspectionData.getInsuranceCompanyData() != null) {
            WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
            ClaimInfoSteps.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        }
        return InspectionSteps.saveInspection();
    }

    public static void createInspection(AppCustomer customer, InspectionData inspectionData) {
        CustomersScreenSteps.selectCustomer(customer);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
    }

    public static void archiveInspection(String inspectionNumber) {
        openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.ARCHIVE);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogArchiveButton();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.waitNotificationMessageDissapears();
    }

    public static void openInspectionMenu(String inspectionId) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        InspectionListElement inspectionListElement = inspectionsScreen.getInspectionElement(inspectionId);
        inspectionListElement.openMenu();
    }

    public static void openInspectionToEdit(String inspectionId) {
        openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
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

    public static String saveInspectionAsDraft() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        WaitUtils.elementShouldBeVisible(baseWizardScreen.getInspectionnumber(), true);
        WaitUtils.getGeneralFluentWait().until(driver -> (baseWizardScreen.getNewInspectionNumber() != "" && baseWizardScreen.getNewInspectionNumber() != null));
        String inspectionNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveInspectionAsDraft();
        return inspectionNumber;
    }

    public static String saveInspectionAsFinal() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        WaitUtils.elementShouldBeVisible(baseWizardScreen.getInspectionnumber(), true);
        WaitUtils.getGeneralFluentWait().until(driver -> (baseWizardScreen.getNewInspectionNumber() != "" && baseWizardScreen.getNewInspectionNumber() != null));
        String inspectionNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.clickWizardMenuSaveButton();
        baseWizardScreen.clickSaveViaMenuAsFinal();
        return inspectionNumber;
    }

    public static void trySaveInspection() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> (baseWizardScreen.getNewInspectionNumber() != "" && baseWizardScreen.getNewInspectionNumber() != null));
        baseWizardScreen.clickWizardMenuSaveButton();
    }

    public static void cancelInspection() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog();
        informationDialog.clickInformationDialogYesButton();
    }

    public static void switchToTeamInspections() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.switchToTeamInspectionsView();
    }

    public static void switchToMyInspections() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        //inspectionsScreen.waitForInspectionsListIsVisibile();
        inspectionsScreen.switchToMyInspectionsView();
    }

    public static void selectInspection(String inspectionId) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.selectInspection(inspectionId);
    }

    public static void unSelectInspection(String inspectionId) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.unselectInspection(inspectionId);
    }

    public static void clickEmailButton() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.getMultiselectInspectionEmailBtn().click();
        WaitUtils.waitLoadDialogDisappears();
    }

    public static void changeCustomerForInspection(String inspectionNumber, AppCustomer newCustomer) {
        openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_CUSTOMER);
        CustomersScreenSteps.selectCustomer(newCustomer);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogYesButton();
        WaitUtils.waitLoadDialogDisappears();
    }

    public static void clickAddInspectionButton() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickAddButton();
    }

    public static void clickMultiSelectInspectionsApproveButton() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.getMultiSelectInspApproveBtn().click();
        WaitUtils.waitLoadDialogDisappears();
    }

    public static void trySaveInspectionsFinalViaMenu() {

        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickMenuButton();
        MenuSteps.selectMenuItem(MenuItems.SAVE_INPSECTION);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickFinalButton();
    }
}
