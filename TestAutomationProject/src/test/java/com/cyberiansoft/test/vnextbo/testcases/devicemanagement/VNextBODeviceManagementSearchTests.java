package com.cyberiansoft.test.vnextbo.testcases.devicemanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.deviceManagement.VNextBODeviceManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBOActiveDevicesTabSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODevicesAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOActiveDevicesTabValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBODevicesAdvancedSearchValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBODeviceManagementSearchTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getDeviceManagementSearchTD();
        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchDevicesUsingSearch(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("vlad");
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", "vlad");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearSearchDetails(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("vlad");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelValidations.verifySearchFieldIsEmpty();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchValidations.verifyAdvancedSearchFormIsDisplayed();
        VNextBODevicesAdvancedSearchValidations.verifyAllElementsAreDisplayed();
        VNextBODevicesAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchDevicesByTeamUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOActiveDevicesTabSteps.searchDevicesByTeam("zayats test team");
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Team", "zayats test team");
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Team: zayats test team");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchDevicesByLicenseUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOActiveDevicesTabSteps.searchDevicesByLicense("0086");
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("License", "0086");
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("License: 0086");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchDevicesByNameUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOActiveDevicesTabSteps.searchDevicesByName("AndroidZak");
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", "AndroidZak");
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Name: AndroidZak");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchDevicesByVersionUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOActiveDevicesTabSteps.searchDevicesByVersion("2454");
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Platform", "2454");
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Version: 2454");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchDevicesByPlatformUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementSearchData.class);
        VNextBOActiveDevicesTabSteps.searchDevicesByPlatform(data.getPlatformTitle());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Platform: " + data.getPlatformTitle());

        if (!VNextBOActiveDevicesTabValidations.verifyActiveDevicesNotFoundMessageIsDisplayed()) {
            VNextBOActiveDevicesTabValidations.verifySearchResultByPlatformIsCorrect(data.getDeviceIconClass(), data.getPlatformTitle());
        }
        else {
            VNextBOActiveDevicesTabValidations.verifyActiveDevicesNotFoundMessageIsCorrect();
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}