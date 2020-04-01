package com.cyberiansoft.test.vnextbo.testcases.smoke;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBONewSmokeData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOQuickNotesData;
import com.cyberiansoft.test.dataclasses.vNextBO.deviceManagement.VNextBODeviceManagementData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.enums.addons.IntegrationStatus;
import com.cyberiansoft.test.enums.invoices.InvoiceStatuses;
import com.cyberiansoft.test.enums.partsmanagement.PartStatus;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.inspections.VNextBOInspectionsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.addOns.VNextBOAddOnsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.companyinfo.VNextBOCompanyInfoPageSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.*;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsApprovalPageSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.quicknotes.VNextBOQuickNotesWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORONotesPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.utils.InspectionsSearchFields;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOAccountInfoBlockValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOEmailOptionsBlockValidations;
import com.cyberiansoft.test.vnextbo.validations.companyinfo.VNextBOCompanyInfoPageValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOActiveDevicesTabValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOPendingRegistrationsTabValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsOrdersListPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.quicknotes.VNextBOQuickNotesWebPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOSmokeTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getSmokeTD();
    }

    @AfterMethod
    public void refreshPage() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMaximizeMinimizeMenu(String rowID, String description, JSONObject testData) {
        Assert.assertTrue(VNextBOLeftMenuValidations.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
        VNextBOLeftMenuInteractions.expandMainMenu();
        Assert.assertTrue(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been expanded");
        VNextBOLeftMenuInteractions.collapseMainMenu();
        Assert.assertFalse(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been collapsed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanApproveInspection(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        final String approvedStatus = InvoiceStatuses.APPROVED.getName();
        final String newStatus = InvoiceStatuses.NEW.getName();
        VNextBOLeftMenuInteractions.selectInspectionsMenu();

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField(InspectionsSearchFields.status, newStatus);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        final String inspectionNumber = VNextBOBreadCrumbInteractions.getLastBreadCrumbText();
        VNextBOInspectionsApprovalPageSteps.approveInspection(data.getNote());

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField(InspectionsSearchFields.inspectionNum, inspectionNumber);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField(InspectionsSearchFields.status, approvedStatus);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        Assert.assertEquals(VNextBOInspectionsPageInteractions.getFirstInspectionStatus(), approvedStatus,
                "The status of inspection hasn't been changed from 'New' to 'Approved'");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAndDeleteNewParts(String rowID, String description, JSONObject testData) {

        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);
        final String woNum = data.getSearchData().getWoNum();

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.addNewPart(data.getPartData());
        VNextBOPartsDetailsPanelSteps.updatePartsList(woNum);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(numberOfParts + 2);

        VNextBOPartsDetailsPanelSteps.deletePartByNumberInList(numberOfParts + 1);
        VNextBOPartsDetailsPanelSteps.deletePartByNumberInList(numberOfParts);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(numberOfParts);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfThePartToOrdered(String rowID, String description, JSONObject testData) {

        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);
        final String woNum = data.getSearchData().getWoNum();
        final String orderedStatus = PartStatus.ORDERED.getStatus();

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(woNum);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, orderedStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, orderedStatus);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDuplicateThePart(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);
        final String woNum = data.getSearchData().getWoNum();

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.duplicatePartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.updatePartsListAfterDuplicating(woNum, numberOfParts + 1);
        VNextBOPartsDetailsPanelValidations.verifyLaborsExtenderIsDisplayed(numberOfParts);
        VNextBOPartsDetailsPanelValidations.verifyPartCheckboxIsDisplayed(numberOfParts);
        VNextBOPartsDetailsPanelValidations.verifyPartDefaultValues(numberOfParts);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(numberOfParts, PartStatus.OPEN.getStatus());
        VNextBOPartsDetailsPanelSteps.deletePartByNumberInList(VNextBOPartsDetailsPanelSteps.getPartsListSize() - 1);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(numberOfParts);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAndDeleteLabor(String rowID, String description, JSONObject testData) {

        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);
        final String woNum = data.getSearchData().getWoNum();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(woNum);
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyAddLaborButtonIsDisplayed(0);
        final int numberOfLaborBlocksBefore = VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.addLaborForPartByNumberInList(0, data.getLabor());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(woNum);
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyAddLaborButtonIsDisplayed(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, numberOfLaborBlocksBefore + 1);
        VNextBOPartsDetailsPanelSteps.deleteLaborForPartByNumberInListAndLaborServiceName(0, data.getLabor());
        VNextBOSearchPanelSteps.refreshPageAndSearch(woNum);
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, numberOfLaborBlocksBefore);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeCompanyInfoAndSaveIt(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        VNextBOLeftMenuInteractions.selectCompanyInfoMenu();
        VNextBOCompanyInfoPageSteps.setCompanyInfoValues(data.getCompanyInfo(), 0);
        VNextBOCompanyInfoPageValidations.verifySuccessNotificationIsDisplayed();
        VNextBOCompanyInfoPageValidations.verifyCompanyInfoValues(data.getCompanyInfo(), 0);
        VNextBOCompanyInfoPageSteps.setCompanyInfoValues(data.getCompanyInfo(), 1);
        VNextBOCompanyInfoPageValidations.verifySuccessNotificationIsDisplayed();
        VNextBOCompanyInfoPageValidations.verifyCompanyInfoValues(data.getCompanyInfo(), 1);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddDeleteAndEditQuickNotes(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOLeftMenuInteractions.selectQuickNotesMenu();
        final int numberOfQuickNotes = VNextBOQuickNotesWebPageSteps.getNotesAmount();
        VNextBOQuickNotesWebPageSteps.addNewNote(data.getQuickNotesDescription());
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(numberOfQuickNotes + 1);
        VNextBOQuickNotesWebPageValidations.verifyLastNoteDescription(data.getQuickNotesDescription(), true);
        VNextBOQuickNotesWebPageSteps.updateNote(data.getQuickNotesDescription(), data.getQuickNotesDescriptionEdited());
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(numberOfQuickNotes + 1);
        VNextBOQuickNotesWebPageValidations.verifyLastNoteDescription(data.getQuickNotesDescriptionEdited(), true);
        VNextBOQuickNotesWebPageSteps.deleteNote(data.getQuickNotesDescriptionEdited());
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(numberOfQuickNotes);
        VNextBOQuickNotesWebPageValidations.verifyNoteIsNotPresentedInTheList(data.getQuickNotesDescriptionEdited());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditAddressFields(String rowID, String description, JSONObject testData) {
        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);

        VNextBOLeftMenuInteractions.selectClientsMenu();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearch());
        VNextBOClientsPageValidations.verifyClientsTableIsDisplayed();
        VNextBOClientsPageSteps.openClientsDetailsPage(data.getTypes()[0]);
        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(data.getAccountInfoData());
        Assert.assertTrue(VNextBOAccountInfoBlockValidations.isPoNumberUpfrontRequiredCheckboxClickable(false));
        VNextBOClientDetailsViewAccordionSteps.setAddressData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(data.getEmailOptionsData(), false);
        VNextBOEmailOptionsBlockValidations.verifyCheckboxesAreClickable(false);
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(data.getNotes());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteDeviceFromPendingRegistrationsList(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);

        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        VNextBOAddNewDeviceDialogSteps.setNewDeviceValuesAndSubmit(data);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(data.getNickname());
        VNextBOPendingRegistrationTabSteps.deletePendingRegistrationDeviceByUser(data.getNickname());
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(data.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUncoverHideNewRegistrationCode(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);

        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
        final String deviceName = data.getDeviceName();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(deviceName);
        VNextBOActiveDevicesTabValidations.verifyReplaceButtonByDeviceNameIsClickable(deviceName);
        VNextBOActiveDevicesTabSteps.clickReplaceButtonByDeviceName(deviceName);
        VNextBOActiveDevicesTabValidations.verifyRegistrationNumberIsDisplayedForDevice(deviceName);
        VNextBOActiveDevicesTabSteps.hideRegistrationCodeByDeviceName(deviceName);
        VNextBOActiveDevicesTabValidations.verifyReplaceButtonIsDisplayedForDevice(deviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditDeviceSettings(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBOActiveDevicesTabSteps.openActiveDevicesTab();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getDeviceName());
        VNextBOActiveDevicesTabSteps.openEditDeviceDialog(data.getDeviceName());
        VNextBOEditDeviceDialogSteps.setNewDeviceValuesAndSubmit(data);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getNickname());
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", data.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeVendorPrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(data.getOrderNumber(), TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageSteps.openServicesTableForStatus(data.getStatus(), data.getPhase());
        final String serviceId = VNextBORODetailsPageValidations.verifyServiceIsDisplayedForExpandedPhase(data.getService());
        VNextBORODetailsPageSteps.setVendorPrice(serviceId, data);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeAndCreateNotes(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(data.getOrderNumber(), TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBORONotesPageSteps.openNoteDialog(data.getOrderNumber());
        Assert.assertTrue(VNextBONotesPageValidations.isRoEditNotesModalDialogDisplayed(),
                "The edit notes dialog hasn't been opened");

        final String note = data.getNotesMessage() + RandomStringUtils.randomAlphanumeric(5);
        VNextBORONotesPageSteps.setRONoteMessageAndSave(note);
        Assert.assertTrue(VNextBONotesPageValidations.isRoEditNotesModalDialogHidden(),
                "The edit notes dialog hasn't been closed");
        VNextBOROPageValidations.verifyNoteTextIsDisplayed(note);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCheckInRO(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(data.getOrderNumber(), TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageSteps.confirmCheckInOptionIsDisplayedForPhase();
        VNextBORODetailsPageSteps.setCheckInOptionForPhase();
        VNextBORODetailsPageSteps.setCheckOutOptionForPhase();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReportProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(data.getOrderNumber(), TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageInteractions.waitForPhaseActionsTriggerToBeDisplayed(data.getPhase());
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPunchOutFunctionalityIsEnabledByFeatureOnTheAddOnPage(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String addOn = "Punch Out Process";
        VNextBOLeftMenuInteractions.selectAddOnsMenu();
        final String addOnStatus = VNextBOAddOnsPageSteps.getAddOnStatus(addOn);
        if (addOnStatus.equals(IntegrationStatus.ON.name())) {
            VNextBOLeftMenuInteractions.selectPartsManagementMenu();
            VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
            VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
            VNextBOPartsDetailsPanelInteractions.waitForGetQuotesButtonToBeDisplayed(true);
            VNextBOPartsDetailsPanelValidations.verifyGetQuotesButtonIsDisplayed(true);
            VNextBOLeftMenuInteractions.selectAddOnsMenu();
            VNextBOAddOnsPageSteps.turnOffAddOnByName(addOn);
            VNextBOAddOnsPageSteps.refreshPageWhileAddOnStatusIsChanged(addOn, IntegrationStatus.OFF);
            VNextBOLeftMenuInteractions.selectPartsManagementMenu();
            VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
            VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
            VNextBOPartsDetailsPanelInteractions.waitForGetQuotesButtonToBeDisplayed(false);
            VNextBOPartsDetailsPanelValidations.verifyGetQuotesButtonIsDisplayed(false);
            VNextBOLeftMenuInteractions.selectAddOnsMenu();
        }
        VNextBOAddOnsPageSteps.turnOnAddOnByName(addOn);
    }
}