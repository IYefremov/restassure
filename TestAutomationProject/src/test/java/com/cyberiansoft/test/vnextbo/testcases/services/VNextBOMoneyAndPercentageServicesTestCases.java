package com.cyberiansoft.test.vnextbo.testcases.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.services.VNextBOServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServiceDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServicesAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServicesWebPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.validations.services.VNextBOServiceDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.services.VNextBOServicesPageValidations;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextBOMoneyAndPercentageServicesTestCases extends BaseTestCase {

    private VNextBOServiceData moneyServiceBaseData;
    private VNextBOServiceData percentageServiceBaseData;
    private VNextBOServiceData editedMoneyServiceData;
    private VNextBOServiceData editedPercentageServiceData;
    private VNextBOServiceData moneyServiceWithClarification;
    private VNextBOServiceData percentageServiceWithClarification;
    private VNextBOServiceData editedServiceWithClarification;

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getServicesMoneyAndPercentageTD();
        VNextBOLeftMenuInteractions.selectServicesMenu();
    }

    @AfterClass
    public void removeServices() {

        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedServiceWithClarification.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedServiceWithClarification.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(percentageServiceWithClarification.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(percentageServiceWithClarification.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanAddMoneyService(String rowID, String description, JSONObject testData) throws IOException, UnirestException {

        moneyServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        moneyServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + moneyServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(moneyServiceBaseData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(moneyServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(moneyServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanAddPercentageService(String rowID, String description, JSONObject testData) {

        percentageServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        percentageServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + percentageServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(percentageServiceBaseData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(percentageServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(percentageServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanEditMoneyService(String rowID, String description, JSONObject testData) {

        editedMoneyServiceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        editedMoneyServiceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(moneyServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(moneyServiceBaseData.getServiceName(), editedMoneyServiceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedMoneyServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanEditPercentageService(String rowID, String description, JSONObject testData) {

        editedPercentageServiceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        editedPercentageServiceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(percentageServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(percentageServiceBaseData.getServiceName(), editedPercentageServiceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedPercentageServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanRemoveMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.clickDeleteButtonForService(editedMoneyServiceData.getServiceName());
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanRemovePercentageService(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.clickDeleteButtonForService(editedPercentageServiceData.getServiceName());
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanResumeRemovedMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedMoneyServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanResumeRemovedPercentageService(String rowID, String description, JSONObject testData) {

        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedPercentageServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyErrorMessagesOnCreateEditServiceDialog(String rowID, String description, JSONObject testData) {

        VNextBOServicesWebPageSteps.clickAddNewServiceButton();
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.setServiceName(" ");
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.closeServiceDialog();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.clickEditButtonForService(editedPercentageServiceData.getServiceName());
        VNextBOServiceDialogSteps.setServiceName(" ");
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanAdNewServiceWithClarificationOptional(String rowID, String description, JSONObject testData) {

        moneyServiceWithClarification = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        moneyServiceWithClarification.setServiceName(RandomStringUtils.randomAlphabetic(5) + moneyServiceWithClarification.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(moneyServiceWithClarification);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(moneyServiceWithClarification.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(moneyServiceWithClarification);
        VNextBOServicesWebPageSteps.clickEditButtonForService(moneyServiceWithClarification.getServiceName());
        VNextBOServiceDialogValidations.verifyClarificationFields(moneyServiceWithClarification);
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanAdNewServiceWithClarificationRequired(String rowID, String description, JSONObject testData) {

        percentageServiceWithClarification = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        percentageServiceWithClarification.setServiceName(RandomStringUtils.randomAlphabetic(5) + percentageServiceWithClarification.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(percentageServiceWithClarification);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(percentageServiceWithClarification.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(percentageServiceWithClarification);
        VNextBOServicesWebPageSteps.clickEditButtonForService(percentageServiceWithClarification.getServiceName());
        VNextBOServiceDialogValidations.verifyClarificationFields(percentageServiceWithClarification);
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanEditServiceWithClarification(String rowID, String description, JSONObject testData) {

        editedServiceWithClarification = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        editedServiceWithClarification.setServiceName(RandomStringUtils.randomAlphabetic(5) + editedServiceWithClarification.getServiceName());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(moneyServiceWithClarification.getServiceName());
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(moneyServiceWithClarification.getServiceName(), editedServiceWithClarification);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedServiceWithClarification.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedServiceWithClarification);
        VNextBOServicesWebPageSteps.clickEditButtonForService(editedServiceWithClarification.getServiceName());
        VNextBOServiceDialogValidations.verifyClarificationFields(editedServiceWithClarification);
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanChangeSequenceNumberForMoneyServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(editedMoneyServiceData.getServiceName(), "13");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(editedMoneyServiceData.getServiceName(), "13");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanChangeSequenceNumberForPercentageServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(editedPercentageServiceData.getServiceName());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(editedPercentageServiceData.getServiceName(), "14");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(editedPercentageServiceData.getServiceName(), "14");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}