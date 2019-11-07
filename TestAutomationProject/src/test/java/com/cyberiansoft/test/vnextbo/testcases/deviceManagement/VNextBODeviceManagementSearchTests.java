package com.cyberiansoft.test.vnextbo.testcases.deviceManagement;

import com.cyberiansoft.test.dataclasses.vNextBO.devicemanagement.VNextBODeviceManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonObjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.deviceManagement.VNextBODeviceManagementPageValidations;
import com.cyberiansoft.test.vnextbo.validations.deviceManagement.VNextBODevicesAdvancedSearchValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBODeviceManagementSearchTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getDeviceManagementSearchTD();
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSearchDevicesUsingSearch(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("vlad");
        VNextBODeviceManagementPageValidations.isSearchResultCorrectForColumnWithText("Name", "vlad");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanClearSearchDetails(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("vlad");
        VNextBODeviceManagementPageValidations.isSearchResultCorrectForColumnWithText("Name", "vlad");
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelValidations.isSearchFieldEmpty();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanOpenAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
        VNextBODevicesAdvancedSearchValidations.verifyAllElementsAreDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanSearchDevicesByTeamUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.searchDevicesByTeam("zayats test team");
        VNextBODeviceManagementPageValidations.isSearchResultCorrectForColumnWithText("Team", "zayats test team");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Team: zayats test team");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanSearchDevicesByLicenseUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.searchDevicesByLicense("0086");
        VNextBODeviceManagementPageValidations.isSearchResultCorrectForColumnWithText("License", "0086");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("License: 0086");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanSearchDevicesByNameUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.searchDevicesByName("AndroidZak");
        VNextBODeviceManagementPageValidations.isSearchResultCorrectForColumnWithText("Name", "AndroidZak");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Name: AndroidZak");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchDevicesByVersionUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.searchDevicesByVersion("2454");
        VNextBODeviceManagementPageValidations.isSearchResultCorrectForColumnWithText("Platform", "2454");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Version: 2454");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanSearchDevicesByPlatformUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementSearchData.class);
        VNextBODeviceManagementSteps.searchDevicesByPlatform(data.getPlatformTitle());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Platform: " + data.getPlatformTitle());

        if (!VNextBODeviceManagementPageValidations.isDevicesNotFoundMessageDisplayed()) {
            VNextBODeviceManagementPageValidations.verifySearchResultByPlatformIsCorrect(data.getDeviceIconClass(), data.getPlatformTitle());
        }
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}