package com.cyberiansoft.test.vnextbo.testcases.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.services.VNextBOServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServicesAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.services.VNextBOServicesWebPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.services.VNextBOServicesPageValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOLaborAndPartServicesTestCases extends BaseTestCase {

    private VNextBOServiceData laborServiceBaseData;
    private VNextBOServiceData partServiceBaseData;
    private VNextBOServiceData partServiceWithAllData;
    private VNextBOServiceData editedLaborServiceBaseData;
    private VNextBOServiceData editedPartServiceBaseData;

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getServicesPartsAndLaborServicesTD();
        VNextBOLeftMenuInteractions.selectServicesMenu();
    }

    @AfterClass
    public void removeServices() {

        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedLaborServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedLaborServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(partServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(partServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedPartServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedPartServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanAddLaborPriceService(String rowID, String description, JSONObject testData) {

        laborServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        laborServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + laborServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.addLaborService(laborServiceBaseData);
        VNextBOSearchPanelSteps.searchByText(laborServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(laborServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanAddLaborPriceServiceWithUseLaborTimes(String rowID, String description, JSONObject testData) {

        laborServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        laborServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + laborServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.addLaborService(laborServiceBaseData);
        VNextBOSearchPanelSteps.searchByText(laborServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(laborServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCannotAddLaborServiceWithClickedRejectIcon(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.setLaborServiceDataAndCancel(serviceData);
        VNextBOSearchPanelSteps.searchByText(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyServicesNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanEditLaborPriceService(String rowID, String description, JSONObject testData) {

        editedLaborServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        editedLaborServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + editedLaborServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.searchByText(laborServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.editLaborService(laborServiceBaseData.getServiceName(), editedLaborServiceBaseData);
        VNextBOSearchPanelSteps.searchByText(editedLaborServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(editedLaborServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanDeleteLaborPriceService(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedLaborServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedLaborServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedLaborServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(editedLaborServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanRestoreDeletedLaborPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedLaborServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(editedLaborServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedLaborServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(editedLaborServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanAddPartPriceService(String rowID, String description, JSONObject testData) {

        partServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        partServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + partServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.addBasePartService(partServiceBaseData);
        VNextBOSearchPanelSteps.searchByText(partServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(partServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanAddPartPriceServiceWithCategorySubcategoryAndPartName(String rowID, String description, JSONObject testData) {

        partServiceWithAllData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        partServiceWithAllData.setServiceName(RandomStringUtils.randomAlphabetic(5) + partServiceWithAllData.getServiceName());
        VNextBOServicesWebPageSteps.addPartServiceWithAllData(partServiceWithAllData);
        VNextBOSearchPanelSteps.searchByText(partServiceWithAllData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(partServiceWithAllData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanEditPartPriceService(String rowID, String description, JSONObject testData) {

        editedPartServiceBaseData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        editedPartServiceBaseData.setServiceName(RandomStringUtils.randomAlphabetic(5) + editedPartServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.searchByText(partServiceWithAllData.getServiceName());
        VNextBOServicesWebPageSteps.editPartServiceWithAllData(partServiceWithAllData.getServiceName(), editedPartServiceBaseData);
        VNextBOSearchPanelSteps.searchByText(editedPartServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(editedPartServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanDeletePartPriceService(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedPartServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.deleteServiceByName(editedPartServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedPartServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(editedPartServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanRestoreDeletedPartPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(editedPartServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(editedPartServiceBaseData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(editedPartServiceBaseData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(editedPartServiceBaseData);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanChangeSequenceNumberForLaborServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedLaborServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(editedLaborServiceBaseData.getServiceName(), "13");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(editedLaborServiceBaseData.getServiceName(), "13");
        VNextBOSearchPanelSteps.clearSearchFilter();
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanChangeSequenceNumberForPartServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(editedPartServiceBaseData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(editedPartServiceBaseData.getServiceName(), "14");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(editedPartServiceBaseData.getServiceName(), "14");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}