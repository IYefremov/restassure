package com.cyberiansoft.test.vnextbo.testcases.services;

import com.cyberiansoft.test.baseutils.Utils;
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
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMoneyAndPercentageServicesTestCases extends BaseTestCase {

    private VNextBOServiceData moneyServiceBaseData;
    private VNextBOServiceData percentageServiceBaseData;
    private VNextBOServiceData editedMoneyServiceData;
    private VNextBOServiceData editedPercentageServiceData;

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getServicesMoneyAndPercentageTD();
        VNextBOLeftMenuInteractions.selectServicesMenu();
    }

    @AfterClass
    public void removeServices() {

        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanAddMoneyService(String rowID, String description, JSONObject testData) {

        moneyServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        moneyServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + moneyServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(moneyServiceBaseData);
        VNextBOSearchPanelSteps.searchByText(moneyServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(moneyServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanAddPercentageService(String rowID, String description, JSONObject testData) {

        percentageServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        percentageServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + percentageServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.addMoneyOrPercentageService(percentageServiceBaseData);
        VNextBOSearchPanelSteps.searchByText(percentageServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(percentageServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanEditMoneyService(String rowID, String description, JSONObject testData) {

        editedMoneyServiceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        editedMoneyServiceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.searchByText(moneyServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(moneyServiceBaseData.getServiceName(), editedMoneyServiceData);
        VNextBOSearchPanelSteps.searchByText(editedMoneyServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanEditPercentageService(String rowID, String description, JSONObject testData) {

        editedPercentageServiceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        editedPercentageServiceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.searchByText(percentageServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.editMoneyOrPercentageService(percentageServiceBaseData.getServiceName(), editedPercentageServiceData);
        VNextBOSearchPanelSteps.searchByText(editedPercentageServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanRemoveMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.clickDeleteButtonForService(editedMoneyServiceData.getServiceName());
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanRemovePercentageService(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.clickDeleteButtonForService(editedPercentageServiceData.getServiceName());
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanResumeRemovedMoneyService(String rowID, String description, JSONObject testData) {

        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(editedMoneyServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedMoneyServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyMoneyServiceRecordDataAreCorrect(editedMoneyServiceData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanResumeRemovedPercentageService(String rowID, String description, JSONObject testData) {

        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(editedPercentageServiceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedPercentageServiceData.getServiceName());
        VNextBOServicesPageValidations.verifyPercentageServiceRecordDataAreCorrect(editedPercentageServiceData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyErrorMessagesOnCreateEditServiceDialog(String rowID, String description, JSONObject testData) {

        VNextBOServicesWebPageSteps.clickAddNewServiceButton();
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.setServiceName(" ");
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOSearchPanelSteps.searchByText(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.clickEditButtonForService(editedPercentageServiceData.getServiceName());
        VNextBOServiceDialogSteps.setServiceName(" ");
        VNextBOServiceDialogSteps.clickSaveButton();
        VNextBOServiceDialogValidations.verifyErrorMessageIsCorrect("Service name is required!");
        VNextBOServiceDialogSteps.closeServiceDialog();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanChangeSequenceNumberForMoneyServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedMoneyServiceData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(editedMoneyServiceData.getServiceName(), "13");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(editedMoneyServiceData.getServiceName(), "13");
        VNextBOSearchPanelSteps.clearSearchFilter();
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanChangeSequenceNumberForPercentageServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedPercentageServiceData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(editedPercentageServiceData.getServiceName(), "14");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(editedPercentageServiceData.getServiceName(), "14");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}