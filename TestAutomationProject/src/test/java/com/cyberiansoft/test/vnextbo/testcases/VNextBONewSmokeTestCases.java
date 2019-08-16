package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBONewSmokeData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOQuickNotesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOAccountInfoBlockInteractions;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOClientsListViewInteractions;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOEmailOptionsBlockInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBODeviceManagementInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOPendingRegistrationsInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsListViewSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.deviceManagement.VNextBOAddNewDeviceSteps;
import com.cyberiansoft.test.vnextbo.steps.deviceManagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsApprovalPageSteps;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBONewSmokeTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBONewSmokeData.json";
    private VNexBOLeftMenuPanel leftMenu;
    private VNextBOBreadCrumbPanel breadCrumbPanel;
    private VNextBOPartsManagementSearchPanel partsManagementSearch;
    private VNextBOPartsOrdersListPanel partsOrdersListPanel;
    private VNextBOPartsDetailsPanel partsDetailsPanel;
    private VNextBOCompanyInfoWebPage companyInfoWebPage;
    private VNextBOHomeWebPage homePage;
    private VNextBOInspectionsWebPage inspectionsWebPage;
    private VNextBOInspectionsApprovalPageSteps inspectionsApprovalSteps;
    private VNextBOClientsListViewInteractions listViewInteractions;
    private VNextBOEmailOptionsBlockInteractions emailOptionsBlockInteractions;
    private VNextBODeviceManagementSteps deviceManagementSteps;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);

        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
        breadCrumbPanel = PageFactory.initElements(webdriver, VNextBOBreadCrumbPanel.class);
        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        partsOrdersListPanel = PageFactory.initElements(webdriver, VNextBOPartsOrdersListPanel.class);
        partsDetailsPanel = PageFactory.initElements(webdriver, VNextBOPartsDetailsPanel.class);
        companyInfoWebPage = PageFactory.initElements(webdriver, VNextBOCompanyInfoWebPage.class);
        homePage = PageFactory.initElements(webdriver, VNextBOHomeWebPage.class);
        inspectionsWebPage = PageFactory.initElements(webdriver, VNextBOInspectionsWebPage.class);
        inspectionsApprovalSteps = new VNextBOInspectionsApprovalPageSteps();
        listViewInteractions = new VNextBOClientsListViewInteractions();
        emailOptionsBlockInteractions = new VNextBOEmailOptionsBlockInteractions();
        deviceManagementSteps = new VNextBODeviceManagementSteps();
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerPanel = PageFactory.initElements(webdriver, VNextBOHeaderPanel.class);
        if (headerPanel.logOutLinkExists()) {
            headerPanel.userLogout();
        }

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMaximizeMinimizeMenu(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        Assert.assertTrue(leftMenu.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
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

        leftMenu.expandMainMenu();
        Assert.assertTrue(leftMenu.isMainMenuExpanded(), "The main menu hasn't been expanded");
        leftMenu.collapseMainMenu();
        Assert.assertFalse(leftMenu.isMainMenuExpanded(), "The main menu hasn't been collapsed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanApproveInspection(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        leftMenu.selectInspectionsMenu();

        inspectionsWebPage
                .openAdvancedSearchPanel()
                .selectAdvancedSearchByStatus(data.getStatuses()[0])
                .clickSearchButton();
        final String inspectionNumber = breadCrumbPanel.getLastBreadCrumbText();
        inspectionsApprovalSteps.approveInspection(data.getNote());

        inspectionsWebPage
                .openAdvancedSearchPanel()
                .setAdvancedSearchByInspectionNumber(inspectionNumber)
                .selectAdvancedSearchByStatus(data.getStatuses()[1])
                .clickSearchButton();
        Assert.assertEquals(inspectionsWebPage.getFirstInspectionStatus(), data.getStatuses()[1],
                "The status of inspection hasn't been changed from 'New' to 'Approved'");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewParts(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        final VNextBOAddNewPartDialog addNewPartDialog = partsDetailsPanel.clickAddNewPartButton();
        Assert.assertTrue(partsDetailsPanel.isAddNewPartDialogDisplayed(), "The part dialog hasn't been displayed");

        addNewPartDialog.setService(data.getService());
        Assert.assertEquals(addNewPartDialog.getServiceFieldValue(), data.getService(), "The service hasn't been set");

        addNewPartDialog
                .setServiceDescription(data.getServiceDescription())
                .setCategory(data.getServiceCategory())
                .setSubcategory(data.getServiceSubcategory());

        final int partsCounterValueBefore = Integer.valueOf(addNewPartDialog.getPartsCounterValue());

        addNewPartDialog.selectPartsFromPartsList(Arrays.asList(data.getPartItems()));
        final int partsCounterValueAfter = Integer.valueOf(addNewPartDialog.getPartsCounterValue());

        Assert.assertEquals(partsCounterValueBefore + data.getPartItems().length, partsCounterValueAfter,
                "The parts counter value hasn't been recalculated");

        addNewPartDialog.clickSubmitButton();
        Assert.assertTrue(partsDetailsPanel.isAddNewPartDialogNotDisplayed());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfThePartToOrdered(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.verifyStatusIsChanged(data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDuplicateThePart(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        final int numberOfParts = partsDetailsPanel.getNumberOfParts();

        partsDetailsPanel.clickActionsButton(0);
        Assert.assertTrue(partsDetailsPanel.isActionsPartsMenuDisplayed(0));
        partsDetailsPanel.clickDuplicateActionsButton(0);

        Assert.assertTrue(partsDetailsPanel.isConfirmationPartDialogDisplayed(),
                "The Confirm duplicating dialog hasn't been displayed");

        partsDetailsPanel.clickConfirmationPartButton();

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();
        Assert.assertEquals(numberOfParts + 1, partsDetailsPanel.getNumberOfParts(),
                "The number of parts hasn't been increased by 1 after duplicating");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteThePart(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        final int numberOfParts = partsDetailsPanel.getNumberOfParts();
        final int partOrder = partsDetailsPanel.getPartOrderByName(data.getPartItems()[0]);
        System.out.println(partOrder);
        partsDetailsPanel.clickActionsButton(partOrder);

        Assert.assertTrue(partsDetailsPanel.isActionsPartsMenuDisplayed(partOrder));
        partsDetailsPanel.clickDeleteActionsButton(partOrder);

        Assert.assertTrue(partsDetailsPanel.isConfirmationPartDialogDisplayed(),
                "The Confirm deleting part dialog hasn't been displayed");

        partsDetailsPanel.clickConfirmationPartButton();

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();
        Assert.assertEquals(numberOfParts - 1, partsDetailsPanel.getNumberOfParts(),
                "The number of parts hasn't been decreased by 1 after deleting");
    }

    // Company Info

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAndDeleteLabor(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertTrue(partsDetailsPanel.isAddLaborButtonDisplayed(0),
                "The Add Labor button hasn't been displayed");

        final int numberOfLaborBlocksBefore = partsDetailsPanel.getNumberOfLaborBlocks();
        System.out.println("before: " + numberOfLaborBlocksBefore);

        final VNextBOAddLaborPartsDialog laborPartsDialog = partsDetailsPanel.clickAddLaborButton(0);
        Assert.assertTrue(laborPartsDialog.isAddLaborDialogDisplayed(),
                "The Labor dialog hasn't been displayed");

        Assert.assertFalse(laborPartsDialog.isLaborClearIconDisplayed(),
                "The Labor Clear icon has been displayed before selecting the labor");
        laborPartsDialog.setLaborService(data.getLabor());
        Assert.assertTrue(laborPartsDialog.isLaborClearIconDisplayed(), "The Labor Clear icon hasn't been displayed");

        laborPartsDialog.clickAddLaborButtonForDialog();
        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");

        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertTrue(partsDetailsPanel.isAddLaborButtonDisplayed(0),
                "The Add Labor button hasn't been displayed");
        final int numberOfLaborBlocksAfter = partsDetailsPanel.getNumberOfLaborBlocks();
        System.out.println("after: " + numberOfLaborBlocksAfter);
        Assert.assertEquals(numberOfLaborBlocksAfter, numberOfLaborBlocksBefore + 1,
                "The labor hasn't been added");

        partsDetailsPanel
                .clickDeleteLaborButton(numberOfLaborBlocksBefore)
                .clickConfirmDeletingButton();
        partsDetailsPanel.refreshPage();

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        partsDetailsPanel.clickPartsArrow(0);

        Assert.assertEquals(partsDetailsPanel.getNumberOfLaborBlocks(), numberOfLaborBlocksBefore,
                "The labor hasn't been deleted");
    }
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeCompanyInfoAndSaveIt(String rowID, String description, JSONObject testData) {
        VNextBONewSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBONewSmokeData.class);

        leftMenu.selectCompanyInfoMenu();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddDeleteAndEditQuickNotes(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditAddressFields(String rowID, String description, JSONObject testData) {
        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);

        leftMenu.selectClientsMenu();
        new VNextBOClientsSearchSteps().searchWithSimpleSearch(data.getSearch());
        Assert.assertTrue(listViewInteractions.isClientsTableDisplayed(), "The clients table hasn't been displayed");

        new VNextBOClientsListViewSteps().openClientsDetailsPage(data.getTypes()[0]);
        final VNextBOClientDetailsViewAccordionSteps accordionSteps = new VNextBOClientDetailsViewAccordionSteps();
        accordionSteps.setClientInfoData(data.getEmployee());

        accordionSteps.setAccountInfoData(data.getAccountInfoData());
        Assert.assertFalse(new VNextBOAccountInfoBlockInteractions().isPoNumberUpfrontRequiredCheckboxClickable());
        accordionSteps.setAddressData(data.getAddressData());
        accordionSteps.setEmailOptionsData(data.getEmailOptionsData());

        Assert.assertFalse(emailOptionsBlockInteractions.isInvoicesCheckboxClickable(),
                "The invoices checkbox is clickable");
        Assert.assertFalse(emailOptionsBlockInteractions.isInspectionsCheckboxClickable(),
                "The inspections checkbox is clickable");
        Assert.assertFalse(emailOptionsBlockInteractions.isIncludeInspectionCheckboxClickable(),
                "The include inspections checkbox is clickable");
        accordionSteps.setPreferencesData(data.getDefaultArea());
        accordionSteps.setMiscellaneousData(data.getNotes());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteLicenceOfDevice(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);

        leftMenu.selectDeviceManagementMenu();
        new VNextBODeviceManagementInteractions().clickAddNewDeviceButton();
        final String randomUser = data.getNickname();
        new VNextBOAddNewDeviceSteps().setNewDeviceValuesAndSubmit(data, randomUser);
        final VNextBOPendingRegistrationsInteractions pendingRegistrationsInteractions =
                new VNextBOPendingRegistrationsInteractions();

        Assert.assertTrue(pendingRegistrationsInteractions.isUserDisplayedInPendingRegistrationTable(randomUser),
                "The user hasn't been displayed in the pending registration table");
        deviceManagementSteps.deletePendingRegistrationDeviceByUser(randomUser);
        Assert.assertTrue(pendingRegistrationsInteractions.isUserNotDisplayedInPendingRegistrationTable(randomUser),
                "The user hasn't disappeared from the pending registration table");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUncoverHideNewRegistrationCode(String rowID, String description, JSONObject testData) {
        VNextBODeviceManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);

        leftMenu.selectDeviceManagementMenu();
        deviceManagementSteps.verifyUserCanUncoverRegistrationCode(data.getDeviceName());
        deviceManagementSteps.verifyUserCanHideRegistrationCode(data.getDeviceName());
    }
}