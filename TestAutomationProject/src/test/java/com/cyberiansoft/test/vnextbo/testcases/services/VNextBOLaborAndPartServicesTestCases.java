package com.cyberiansoft.test.vnextbo.testcases.services;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOLaborAndPartServicesTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getServicesPartsAndLaborServicesTD();
        VNextBOLeftMenuInteractions.selectServicesMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddLaborPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addLaborService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddLaborPriceServiceWithUseLaborTimes(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addLaborService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotAddLaborServiceWithClickedRejectIcon(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.setLaborServiceDataAndCancel(serviceData);
        VNextBOSearchPanelSteps.searchByText(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyServicesNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditLaborPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        String baseServiceName = serviceData.getServiceName();
        VNextBOServicesWebPageSteps.addLaborService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + "Edited Labor service 91570");
        serviceData.setServiceType("Autre");
        serviceData.setServiceDescription("Edited Test description");
        serviceData.setServicePriceType("Labor");
        serviceData.setServiceLaborRate("6.00");
        serviceData.setServiceDefaultLaborTime("7.0");
        serviceData.setServiceUseSelectedLaborTimes("false");
        VNextBOServicesWebPageSteps.editLaborService(baseServiceName, serviceData);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteLaborPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addLaborService(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanRestoreDeletedLaborPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addLaborService(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(serviceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyLaborServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddPartPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addBasePartService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddPartPriceServiceWithCategorySubcategoryAndPartName(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addPartServiceWithAllData(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditPartPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        String baseServiceName = serviceData.getServiceName();
        VNextBOServicesWebPageSteps.addPartServiceWithAllData(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + "Edited Part service 91569");
        serviceData.setServiceType("Body Shop");
        serviceData.setServiceDescription("Edited Test description");
        serviceData.setServicePriceType("Part");
        serviceData.setServiceCategory("Brake");
        serviceData.setServiceSubCategory("Assortments");
        serviceData.setServicePartName("Brake Fitting Assortment");
        VNextBOServicesWebPageSteps.editPartServiceWithAllData(baseServiceName, serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeletePartPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addPartServiceWithAllData(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(serviceData);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanRestoreDeletedPartPriceService(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addPartServiceWithAllData(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
        VNextBOServicesAdvancedSearchSteps.searchArchivedServiceByName(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.restoreServiceByName(serviceData.getServiceName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesPageValidations.verifyPartServiceRecordDataAreCorrect(serviceData);
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForLaborServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addLaborService(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(serviceData.getServiceName(), "13");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(serviceData.getServiceName(), "13");
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForPartServiceOrder(String rowID, String description, JSONObject testData) {

        VNextBOServiceData serviceData = JSonDataParser.getTestDataFromJson(testData, VNextBOServiceData.class);
        serviceData.setServiceName(RandomStringUtils.randomAlphabetic(5) + serviceData.getServiceName());
        VNextBOServicesWebPageSteps.addPartServiceWithAllData(serviceData);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceData.getServiceName());
        VNextBOServicesWebPageSteps.changeOrderNumberByServiceName(serviceData.getServiceName(), "14");
        VNextBOServicesPageValidations.verifyServiceOrderNumberIsCorrect(serviceData.getServiceName(), "14");
        VNextBOServicesWebPageSteps.deleteServiceByName(serviceData.getServiceName());
    }
}