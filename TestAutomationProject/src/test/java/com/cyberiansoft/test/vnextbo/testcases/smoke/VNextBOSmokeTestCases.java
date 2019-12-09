package com.cyberiansoft.test.vnextbo.testcases.smoke;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOAccountInfoBlockInteractions;
import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOEmailOptionsBlockInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.*;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsApprovalPageSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOAddNewPartDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORONotesPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOActiveDevicesTabValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOPendingRegistrationsTabValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAddNewPartDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsOrdersListPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

public class VNextBOSmokeTestCases extends BaseTestCase {

    private VNextBOCompanyInfoWebPage companyInfoWebPage;
    private VNextBOHomeWebPage homePage;
    private VNextBOInspectionsWebPage inspectionsWebPage;
    private VNextBOQuickNotesWebPage quickNotesPage;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getSmokeTD();
    }

    @BeforeMethod
    public void BackOfficeLogin() {

        companyInfoWebPage = PageFactory.initElements(webdriver, VNextBOCompanyInfoWebPage.class);
        homePage = PageFactory.initElements(webdriver, VNextBOHomeWebPage.class);
        inspectionsWebPage = PageFactory.initElements(webdriver, VNextBOInspectionsWebPage.class);
        quickNotesPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOQuickNotesWebPage.class);
    }

    @AfterMethod
    public void refreshPage() {

        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanAddNewParts(String rowID, String description, JSONObject testData) {

        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(data.getWoNum());
        VNextBOPartsDetailsPanelSteps.clickAddNewPartButton();
        VNextBOAddNewPartDialogValidations.verifyDialogIsDisplayed(true);
        VNextBOAddNewPartDialogSteps.setServiceField(data.getService());
        VNextBOAddNewPartDialogValidations.verifyServiceFieldIsCorrect(data.getService());
        VNextBOAddNewPartDialogSteps.setDescription(data.getServiceDescription());
        VNextBOAddNewPartDialogSteps.setCategory(data.getServiceCategory());
        VNextBOAddNewPartDialogSteps.setSubCategory(data.getServiceSubcategory());
        final int partsCounterValueBefore = Integer.valueOf(VNextBOAddNewPartDialogSteps.getSelectedPartsCounter());
        VNextBOAddNewPartDialogSteps.selectPartsFromPartsList(Arrays.asList(data.getPartItems()));
        VNextBOAddNewPartDialogValidations.verifySelectedPartsCounterValueIsCorrect(String.valueOf(partsCounterValueBefore + data.getPartItems().length));
        VNextBOAddNewPartDialogSteps.clickSubmitButton();
        VNextBOAddNewPartDialogValidations.verifyDialogIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanChangeStatusOfThePartToOrdered(String rowID, String description, JSONObject testData) {

        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(data.getWoNum());
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, data.getStatus());
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanDuplicateThePart(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(data.getWoNum());
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.duplicatePartByNumberInList(0);
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsCorrect(numberOfParts + 1);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanDeleteThePart(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(data.getWoNum());
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.deletePartByNumberInList(VNextBOPartsDetailsPanelSteps.getPartNumberInTheListByServiceName(data.getPartItems()[0]));
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsCorrect(numberOfParts - 1);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanAddAndDeleteLabor(String rowID, String description, JSONObject testData) {

        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(data.getWoNum());
        VNextBOPartsDetailsPanelSteps.expandLaborBlock(0);
        VNextBOPartsDetailsPanelValidations.verifyAddLaborButtonIsDisplayed(0);
        final int numberOfLaborBlocksBefore = VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.addLaborForPartByNumberInList(0, data.getLabor());
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect(data.getWoNum());
        VNextBOPartsDetailsPanelSteps.expandLaborBlock(0);
        VNextBOPartsDetailsPanelValidations.verifyAddLaborButtonIsDisplayed(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, numberOfLaborBlocksBefore + 1);
        VNextBOPartsDetailsPanelSteps.deleteLaborForPartByNumberInListANdLaborServiceName(0, data.getLabor());
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(data.getWoNum());
        VNextBOPartsDetailsPanelSteps.expandLaborBlock(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, numberOfLaborBlocksBefore);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanChangeCompanyInfoAndSaveIt(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        VNextBOLeftMenuInteractions.selectCompanyInfoMenu();
        companyInfoWebPage
                .setCompanyName(data.getCompany()[0])
                .setAddressLine1(data.getAddressLine1()[0])
                .setAddressLine2(data.getAddressLine2()[0])
                .setCity(data.getCity()[0])
                .setCountry(data.getCountry()[0])
                .setStateProvince(data.getStateProvince()[0])
                .setZip(data.getZip()[0])
                .setPhoneCode(data.getPhoneCode()[0])
                .setPhone(data.getPhone()[0])
                .setEmail(data.getEmail()[0])
                .clickSaveButton();

        Assert.assertTrue(companyInfoWebPage.isSuccessNotificationDisplayed(),
                "The success notification hasn't been displayed after clicking the 'Save' button");

        Assert.assertEquals(companyInfoWebPage.getCompanyValue(), data.getCompany()[0]);
        Assert.assertEquals(companyInfoWebPage.getAddressLine1Value(), data.getAddressLine1()[0]);
        Assert.assertEquals(companyInfoWebPage.getAddressLine2Value(), data.getAddressLine2()[0]);
        Assert.assertEquals(companyInfoWebPage.getCityValue(), data.getCity()[0]);
        Assert.assertEquals(companyInfoWebPage.getCountryValue(), data.getCountry()[0]);
        Assert.assertEquals(companyInfoWebPage.getStateProvinceValue(), data.getStateProvince()[0]);
        Assert.assertEquals(companyInfoWebPage.getZipValue(), data.getZip()[0]);
        Assert.assertEquals(companyInfoWebPage.getPhoneCodeValue(), data.getPhoneCode()[0]);
        Assert.assertEquals(companyInfoWebPage.getPhoneValue(), data.getPhone()[0]);
        Assert.assertEquals(companyInfoWebPage.getEmailValue(), data.getEmail()[0]);

        companyInfoWebPage
                .setCompanyName(data.getCompany()[1])
                .setAddressLine1(data.getAddressLine1()[1])
                .setAddressLine2(data.getAddressLine2()[1])
                .setCity(data.getCity()[1])
                .setCountry(data.getCountry()[1])
                .setStateProvince(data.getStateProvince()[1])
                .setZip(data.getZip()[1])
                .setPhoneCode(data.getPhoneCode()[1])
                .setPhone(data.getPhone()[1])
                .setEmail(data.getEmail()[1])
                .clickSaveButton();

        Assert.assertTrue(companyInfoWebPage.isSuccessNotificationDisplayed(),
                "The success notification hasn't been displayed after clicking the 'Save' button");

        Assert.assertEquals(companyInfoWebPage.getCompanyValue(), data.getCompany()[1]);
        Assert.assertEquals(companyInfoWebPage.getAddressLine1Value(), data.getAddressLine1()[1]);
        Assert.assertEquals(companyInfoWebPage.getAddressLine2Value(), data.getAddressLine2()[1]);
        Assert.assertEquals(companyInfoWebPage.getCityValue(), data.getCity()[1]);
        Assert.assertEquals(companyInfoWebPage.getCountryValue(), data.getCountry()[1]);
        Assert.assertEquals(companyInfoWebPage.getStateProvinceValue(), data.getStateProvince()[1]);
        Assert.assertEquals(companyInfoWebPage.getZipValue(), data.getZip()[1]);
        Assert.assertEquals(companyInfoWebPage.getPhoneCodeValue(), data.getPhoneCode()[1]);
        Assert.assertEquals(companyInfoWebPage.getPhoneValue(), data.getPhone()[1]);
        Assert.assertEquals(companyInfoWebPage.getEmailValue(), data.getEmail()[1]);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanAddDeleteAndEditQuickNotes(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOLeftMenuInteractions.selectQuickNotesMenu();
        final int numberOfQuickNotes = quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription());
        quickNotesPage
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertEquals(numberOfQuickNotes + 1,
                quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription()));

        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescription()));
        quickNotesPage
                .deleteQuickNote(data.getQuickNotesDescription())
                .deleteQuickNotesIfPresent(data.getQuickNotesDescriptionEdited())
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescription()));

        quickNotesPage
                .clickEditQuickNote(data.getQuickNotesDescription())
                .typeDescription(data.getQuickNotesDescriptionEdited())
                .clickQuickNotesDialogUpdateButton();
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescriptionEdited()));
        quickNotesPage.deleteQuickNote(data.getQuickNotesDescriptionEdited());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanEditAddressFields(String rowID, String description, JSONObject testData) {
        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);

        VNextBOLeftMenuInteractions.selectClientsMenu();
        VNextBOSearchPanelSteps.searchByText(data.getSearch());
        VNextBOClientsPageValidations.verifyClientsTableIsDisplayed();

        VNextBOClientsPageSteps.openClientsDetailsPage(data.getTypes()[0]);
        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(data.getEmployee());

        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(data.getAccountInfoData());
        Assert.assertFalse(new VNextBOAccountInfoBlockInteractions().isPoNumberUpfrontRequiredCheckboxClickable());
        VNextBOClientDetailsViewAccordionSteps.setAddressData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(data.getEmailOptionsData(), false);

        Assert.assertFalse(VNextBOEmailOptionsBlockInteractions.isInvoicesCheckboxClickable(),
                "The invoices checkbox is clickable");
        Assert.assertFalse(VNextBOEmailOptionsBlockInteractions.isInspectionsCheckboxClickable(),
                "The inspections checkbox is clickable");
        Assert.assertFalse(VNextBOEmailOptionsBlockInteractions.isIncludeInspectionCheckboxClickable(),
                "The include inspections checkbox is clickable");
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(data.getNotes());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanDeleteDeviceFromPendingRegistrationsList(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);

        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        VNextBOAddNewDeviceDialogSteps.setNewDeviceValuesAndSubmit(data);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(data.getNickname());
        VNextBOPendingRegistrationTabSteps.deletePendingRegistrationDeviceByUser(data.getNickname());
        if (VNextBOPendingRegistrationTabSteps.checkWhetherDevicesNotFoundMessageIsDisplayed())
            VNextBOPendingRegistrationsTabValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        else VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(data.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanUncoverHideNewRegistrationCode(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);

        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
        VNextBOSearchPanelSteps.searchByText(data.getDeviceName());
        VNextBOActiveDevicesTabSteps.clickReplaceButtonByDeviceName(data.getDeviceName());
        VNextBOActiveDevicesTabValidations.verifyRegistrationNumberIsDisplayedForDevice(data.getDeviceName());
        VNextBOActiveDevicesTabSteps.hideRegistrationCodeByDeviceName(data.getDeviceName());
        VNextBOActiveDevicesTabValidations.verifyReplaceButtonIsDisplayedForDevice(data.getDeviceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanEditDeviceSettings(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBOActiveDevicesTabSteps.openActiveDevicesTab();
        VNextBOSearchPanelSteps.searchByText(data.getDeviceName());
        VNextBOActiveDevicesTabSteps.openEditDeviceDialog(data.getDeviceName());
        VNextBOEditDeviceDialogSteps.setNewDeviceValuesAndSubmit(data);
        VNextBOSearchPanelSteps.searchByText(data.getNickname());
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", data.getNickname());
    }

    //todo bug 87314 change the serviceVendorPrice test data from 5 to 1 to test the boundary values after the bug fix
    //todo bug fixed, add advanced search options steps
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanChangeVendorPrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

//        roPageVerifications.verifyAdvancedSearchDialogIsDisplayed();
//
//        new VNextBOROAdvancedSearchDialogSteps().searchByActivePhase(
//                data.getPhase(), data.getPhaseStatus(), data.getTimeFrame());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
//        roDetailsPageSteps.openServicesTableForStatus(data.getStatus(), data.getPhase());
//        final String serviceId = roDetailsPageVerifications
//                .verifyServiceIsDisplayedForExpandedPhase(data.getService());
//
//        Arrays.asList(data.getServiceVendorPrices())
//                .forEach(vendorPrice -> roDetailsPageVerifications
//                        .verifyServiceVendorPriceIsSet(serviceId, data.getService(), vendorPrice));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanSeeAndCreateNotes(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBORONotesPageSteps.openNoteDialog(data.getOrderNumber());
        Assert.assertTrue(VNextBONotesPageValidations.isRoEditNotesModalDialogDisplayed(),
                "The edit notes dialog hasn't been opened");

        final String note = data.getNotesMessage() + RandomStringUtils.randomAlphanumeric(5);
        VNextBORONotesPageSteps.setRONoteMessageAndSave(note);
        Assert.assertTrue(VNextBONotesPageValidations.isRoEditNotesModalDialogHidden(),
                "The edit notes dialog hasn't been closed");
        VNextBOROPageValidations.verifyNoteTextIsDisplayed(note);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanCheckInRO(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(),
                "The phase actions trigger hasn't been displayed");
        VNextBORODetailsPageValidations.verifyCheckInOptionIsDisplayedForPhase();
        VNextBORODetailsPageSteps.setCheckInOptionForPhase();
        VNextBORODetailsPageSteps.setCheckOutOptionForPhase();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
    public void verifyUserCanReportProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
    public void verifyUserCanMaximizeMinimizeMenu(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        Assert.assertTrue(VNextBOLeftMenuValidations.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
        Assert.assertTrue(homePage.isLogoDisplayed(), "The logo hasn't been displayed");
        Assert.assertTrue(homePage.isUserEmailDisplayed(), "The email hasn't been displayed");
        Assert.assertTrue(homePage.isLogoutButtonDisplayed(), "The logout button hasn't been displayed");
        Assert.assertTrue(homePage.isHelpButtonDisplayed(), "The help button hasn't been displayed");
        Assert.assertTrue(homePage.isAccessClientPortalLinkDisplayed(),
                "The access client portal link hasn't been displayed");
        Assert.assertTrue(homePage.isAccessReconProBOLinkDisplayed(),
                "The access ReconPro BO link hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForBOButtonDisplayed(),
                "The support for BO button hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForMobileAppButtonDisplayed(),
                "The support for Mobile App button hasn't been displayed");
        Assert.assertTrue(homePage.isTermsAndConditionsLinkDisplayed(),
                "The Terms And Conditions Link hasn't been displayed");
        Assert.assertTrue(homePage.isPrivacyPolicyLinkDisplayed(),
                "The Privacy Policy Link hasn't been displayed");
        Assert.assertTrue(homePage.isIntercomDisplayed(),
                "The Intercom Link hasn't been displayed");

        VNextBOLeftMenuInteractions.expandMainMenu();
        Assert.assertTrue(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been expanded");
        VNextBOLeftMenuInteractions.collapseMainMenu();
        Assert.assertFalse(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been collapsed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
    public void verifyUserCanApproveInspection(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        VNextBOLeftMenuInteractions.selectInspectionsMenu();

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", data.getStatuses()[0]);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        final String inspectionNumber = VNextBOBreadCrumbInteractions.getLastBreadCrumbText();
        VNextBOInspectionsApprovalPageSteps.approveInspection(data.getNote());

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#", inspectionNumber);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", data.getStatuses()[1]);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        Assert.assertEquals(inspectionsWebPage.getFirstInspectionStatus(), data.getStatuses()[1],
                "The status of inspection hasn't been changed from 'New' to 'Approved'");
    }
}