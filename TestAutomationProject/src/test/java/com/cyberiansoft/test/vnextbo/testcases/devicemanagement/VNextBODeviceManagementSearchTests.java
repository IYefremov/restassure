package com.cyberiansoft.test.vnextbo.testcases.devicemanagement;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.devicemanagement.VNextBODeviceManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.verifications.devicemanagement.VNextBOActiveDevicesPageValidations;
import com.cyberiansoft.test.vnextbo.verifications.devicemanagement.VNextBODevicesAdvancedSearchValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBODeviceManagementSearchTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/devicemanagement/VNextBODeviceManagementSearchData.json";
    private VNextBOLoginScreenWebPage loginPage;
    String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = DATA_FILE;
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());

        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @AfterClass
    public void backOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSearchDevicesUsingSearch(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("vlad");
        VNextBOActiveDevicesPageValidations.isSearchResultCorrectForColumnWithText("Name", "vlad");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanClearSearchDetails(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("vlad");
        VNextBOActiveDevicesPageValidations.isSearchResultCorrectForColumnWithText("Name", "vlad");
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
        VNextBOActiveDevicesPageValidations.isSearchResultCorrectForColumnWithText("Team", "zayats test team");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Team: zayats test team");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanSearchDevicesByLicenseUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.searchDevicesByLicense("0086");
        VNextBOActiveDevicesPageValidations.isSearchResultCorrectForColumnWithText("License", "0086");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("License: 0086");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanSearchDevicesByNameUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.searchDevicesByName("AndroidZak");
        VNextBOActiveDevicesPageValidations.isSearchResultCorrectForColumnWithText("Name", "AndroidZak");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Name: AndroidZak");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchDevicesByVersionUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.searchDevicesByVersion("2454");
        VNextBOActiveDevicesPageValidations.isSearchResultCorrectForColumnWithText("Platform", "2454");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Version: 2454");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchDevicesByPlatformUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementSearchData.class);
        VNextBODeviceManagementSteps.searchDevicesByVersion(data.getPlatform());
        VNextBOActiveDevicesPageValidations.isSearchResultCorrectForColumnWithText("Platform", data.getPlatform());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Platform: " + data.getPlatform());
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}