package com.cyberiansoft.test.vnextbo.testcases.services;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.services.VNextBOServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServiceDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServicesAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServicesWebPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.services.VNextBOServiceDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.services.VNextBOServicesPageValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMoneyAndPercentageServicesTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getServicesMoneyAndPercentageTD();
        VNextBOLeftMenuInteractions.selectServicesMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddPercentageService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        String baseServiceName = serviceData.getServiceName();
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + "Edited Test Money Service 90569");
        serviceData.setServiceType("Autre");
        serviceData.setServiceDescription("Edited test money service");
        serviceData.setServicePriceType("Money");
        serviceData.setServicePrice("35.01");
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(baseServiceName, serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditPercentageService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        String baseServiceName = serviceData.getServiceName();
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + "Edited Test Percentage Service 90570");
        serviceData.setServiceType("Body Shop");
        serviceData.setServiceDescription("Edited test percentage service");
        serviceData.setServicePriceType("Percentage");
        serviceData.setServicePrice("99.999");
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(baseServiceName, serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanRemoveMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.clickDeleteButtonForService(serviceData.getServiceName());
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanRemovePercentageService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.clickDeleteButtonForService(serviceData.getServiceName());
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResumeRemovedMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(serviceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResumeRemovedPercentageService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(serviceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyErrorMessagesOnCreateEditServiceDialog(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOServicesWebPageSteps.clickAddNewServiceButton();
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.setServiceName(" ");
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.closeServiceDialog();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.clickEditButtonForService(serviceData.getServiceName());
        VNextBOServiceDialogSteps.setServiceName(" ");
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAdNewServiceWithClarificationOptional(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.clickEditButtonForService(serviceData.getServiceName());
        VNextBOServiceDialogValidations.verifyClarificationFields(serviceData);
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAdNewServiceWithClarificationRequired(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.clickEditButtonForService(serviceData.getServiceName());
        VNextBOServiceDialogValidations.verifyClarificationFields(serviceData);
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditServiceWithClarification(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        String baseServiceName = serviceData.getServiceName();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + "Edited Test Service 91577");
        serviceData.setServiceType("Detail");
        serviceData.setServiceDescription("test service with none clarification");
        serviceData.setServicePriceType("Money");
        serviceData.setServicePrice("53.47");
        serviceData.setServiceClarification("None");
        serviceData.setServiceClarificationPrefix(null);
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(baseServiceName, serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.clickEditButtonForService(serviceData.getServiceName());
        VNextBOServiceDialogValidations.verifyClarificationFields(serviceData);
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForMoneyServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(serviceData.getServiceName(), "13");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(serviceData.getServiceName(), "13");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForPercentageServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(serviceData.getServiceName(), "14");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(serviceData.getServiceName(), "14");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }
}