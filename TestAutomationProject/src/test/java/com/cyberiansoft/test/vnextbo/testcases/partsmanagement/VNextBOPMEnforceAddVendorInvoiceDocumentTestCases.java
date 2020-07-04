package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.steps.menu.BOMenuSteps;
import com.cyberiansoft.test.bo.steps.superuser.subscriptions.BOSubscriptionsPageSteps;
import com.cyberiansoft.test.dataclasses.vNextBO.alerts.VNextBOAlertMessages;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.partsmanagement.PartStatus;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogInteractions;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOChangePartsDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOToasterNotificationValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsOrdersListPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOChangePartsDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOPMEnforceAddVendorInvoiceDocumentTestCases extends BaseTestCase {

    private final String openStatus = PartStatus.OPEN.getStatus();
    private final String receivedStatus = PartStatus.RECEIVED.getStatus();

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMEnforceAddVendorInvoiceDocumentTD();
    }

    @BeforeMethod
    public void goToPage() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
    }

    private void openPMPage(VNextBOPartsManagementData data) {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
    }

    private int getOrderForOpenedDialog() {
        final int order = VNextBOPartsDetailsPanelSteps.getOrderOfPartWithProviderSet();
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOneProviderIsSet();
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(order, openStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(order, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(order);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        return order;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddDocumentWhileChangingStatusToReceivedForOnePartService(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        final int order = getOrderForOpenedDialog();
        final String documentNumber = RandomStringUtils.randomAlphanumeric(5);
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(documentNumber);
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, receivedStatus);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(order);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddDocumentWhileChangingStatusToReceivedForMultiplePartServices(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        VNextBOPartsDetailsPanelInteractions.setProvider(0, data.getProvider());
        VNextBOPartsDetailsPanelInteractions.setProvider(1, data.getProvider());
        VNextBOPartsDetailsPanelInteractions.setProvider(2, data.getProvider());
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOneProviderIsSet();
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(1, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(2, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(0);
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(1);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        final String documentNumber = RandomStringUtils.randomAlphanumeric(5);
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(documentNumber);
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, receivedStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(1, receivedStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(2, openStatus);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(0);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(1);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPartServiceStatusesAreNotChangedIfUserCancelsDocument(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        VNextBOPartsDetailsPanelInteractions.setProvider(0, data.getProvider());
        VNextBOPartsDetailsPanelInteractions.setProvider(1, data.getProvider());
        VNextBOPartsDetailsPanelInteractions.setProvider(2, data.getProvider());
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOneProviderIsSet();
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(1, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(2, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(0);
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(1);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.cancel();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, openStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(1, openStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(2, openStatus);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyDocumentTypeVendorInvoiceIsPreselected(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        getOrderForOpenedDialog();
        VNextBOPartAddNewDocumentDialogValidations.verifyTypeFieldValue(data.getDocumentData().getType());
        VNextBOPartAddNewDocumentDialogValidations.verifySavedButtonIsDisabled();
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyProviderDropDownContainsProvidersOfSelectedPartServices(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        VNextBOPartsDetailsPanelInteractions.setProvider(0, data.getProviderOptions()[0]);
        VNextBOPartsDetailsPanelInteractions.setProvider(1, data.getProviderOptions()[1]);
        VNextBOPartsDetailsPanelInteractions.setProvider(2, data.getProviderOptions()[2]);
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOneProviderIsSet();
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(1, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(2, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(0);
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(1);
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(2);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();

        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        final List<String> providersList = VNextBOPartAddNewDocumentDialogSteps.getProvidersList();
        Assert.assertTrue(providersList.containsAll(Arrays.asList(data.getProviderOptions())),
                "The providers list differs from the checked providers");
        VNextBOPartAddNewDocumentDialogSteps.closeDialogWithXIcon();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, openStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(1, openStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(2, openStatus);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotCreateDocumentIfProviderDropDownIsEmpty(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        final int order = VNextBOPartsDetailsPanelSteps.getOrderOfPartWithEmptyProviderField();
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOneServiceWithEmptyProviderIsDisplayed();
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(order, openStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(order, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(order);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.clickSubmitButton();
        VNextBOToasterNotificationValidations.verifyMessageContainsText(
                VNextBOAlertMessages.CANNOT_CHANGE_STATUS_TO_RECEIVED_WITHOUT_PROVIDER);
        VNextBOChangePartsDialogValidations.verifyChangePartsDialogIsClosed();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(order, openStatus);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyDocumentIsCreatedOnlyForChosenServicesAndDesignatedProvider(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelInteractions.setProvider(0, data.getProviderOptions()[0]);
        VNextBOPartsDetailsPanelInteractions.setProvider(1, data.getProviderOptions()[1]);
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(1, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(0);
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(1);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        final List<String> providersList = VNextBOPartAddNewDocumentDialogSteps.getProvidersList();
        Assert.assertTrue(providersList.containsAll(Arrays.asList(data.getProviderOptions())),
                "The providers list differs from the checked providers");
        final String documentNumber = RandomStringUtils.randomAlphanumeric(5);
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentProvider(data.getProviderOptions()[0]);
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(documentNumber);
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, receivedStatus);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(0);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(1, openStatus);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(1);
        Assert.assertNotEquals(VNextBOPartDocumentsDialogInteractions.getNumberValue(0), documentNumber,
                "The document number has been added to the wrong service");
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheDocumentTypeIsApplied(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        final int order = getOrderForOpenedDialog();
        final String documentNumber = RandomStringUtils.randomAlphanumeric(5);
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentType(data.getDocumentData().getType());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(documentNumber);
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, receivedStatus);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(order);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogValidations.verifyType(0, data.getDocumentData().getType());
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheDocumentsSearchWorksOnlyWithinTheCurrentOrder(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        VNextBOPartsDetailsPanelInteractions.setProvider(0, data.getProvider());
        VNextBOPartsDetailsPanelInteractions.setProvider(1, data.getProvider());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-062-00078");
        VNextBOPartsDetailsPanelInteractions.setProvider(0, data.getProvider());
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);

        final List<Integer> ordersWithTheSameProvider = VNextBOPartsDetailsPanelSteps.getOrderOfPartsWithProviderSet(data.getProvider());
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOneProviderIsSet();
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(0);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        final String documentNumber = RandomStringUtils.randomAlphanumeric(5);
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(documentNumber);
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, receivedStatus);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(0);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();

        ordersWithTheSameProvider.subList(1, ordersWithTheSameProvider.size()).forEach((o) -> {
            VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(o, openStatus);
            VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(o, openStatus);
            VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
            VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(o);
            VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
            VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
            VNextBOChangePartsDialogSteps.submit();
            VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
            final List<String> documentNumbersList = VNextBOPartAddNewDocumentDialogSteps.getDocumentNumbersList();
            Assert.assertTrue(documentNumbersList.contains(documentNumber),
                    "The document isn't displayed in the document numbers list");
            VNextBOPartAddNewDocumentDialogSteps.closeDialogWithXIcon();
        });
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-062-00078");
        final int orderWithProvider = VNextBOPartsDetailsPanelSteps.getOrderOfPartWithProviderSet();
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOneProviderIsSet();
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(orderWithProvider, openStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(orderWithProvider, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(orderWithProvider);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        final List<String> documentNumbersList = VNextBOPartAddNewDocumentDialogSteps.getDocumentNumbersList();
        Assert.assertFalse(documentNumbersList.contains(documentNumber),
                "The document is displayed in the document numbers list of the other order");
        VNextBOPartAddNewDocumentDialogSteps.closeDialogWithXIcon();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, openStatus);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheFieldsAreNotEditableAfterSelectingTheExistingDocumentNumber(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        VNextBOPartsDetailsPanelInteractions.setProvider(0, data.getProvider());
        VNextBOPartsDetailsPanelInteractions.setProvider(1, data.getProvider());
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        openPMPage(data);
        Assert.assertTrue(VNextBOPartsOrdersListPanelValidations.isPartStatusOpenedOrApproved(0),
                "The part doesn't have the 'Opened' or 'Approved' status");
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(1, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(0);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        VNextBOPartAddNewDocumentDialogSteps.setDocumentFields(data.getDocumentData());
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();

        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(1);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(data.getDocumentData().getNumber());
        VNextBOPartAddNewDocumentDialogSteps.waitForNotesFieldToBecomeDisabled();
        VNextBOPartAddNewDocumentDialogValidations.verifyFieldsAfterTheDocumentNumberAreDisabled();
        VNextBOPartAddNewDocumentDialogValidations.verifyFieldsValuesAfterTheDocumentField(data.getDocumentData());
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(1);
        VNextBOPartDocumentsDialogValidations.verifyPartDocumentsFields(0, data.getDocumentData());
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheSameDocumentLinkedToSeveralServicesIsDeletedOnlyFromCurrentService(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        verifyUserCanAddDocumentWhileChangingStatusToReceivedForMultiplePartServices(rowID, description, testData);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, openStatus);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(1, openStatus);
        VNextBOPartsDetailsPanelSteps.waitForVisibilityOfCheckboxes();
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(0);
        VNextBOPartsDetailsPanelSteps.checkServiceCheckbox(1);
        VNextBOPartsDetailsPanelSteps.openChangeStatusDialog();
        VNextBOChangePartsDialogSteps.setStatus(receivedStatus);
        VNextBOChangePartsDialogSteps.submit();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
        final String documentNumber = data.getDocumentData().getNumber();
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(documentNumber);
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, receivedStatus);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(1, receivedStatus);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(0);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(1);
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, documentNumber);
        VNextBOPartDocumentsDialogSteps.deleteDocument(0);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }
}
