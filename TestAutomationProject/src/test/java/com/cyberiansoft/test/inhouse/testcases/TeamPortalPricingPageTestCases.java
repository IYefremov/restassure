package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.TeamPortalPricingData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.inhouse.pageObject.webpages.AddEditionDialog;
import com.cyberiansoft.test.inhouse.pageObject.webpages.LeftMenuPanel;
import com.cyberiansoft.test.inhouse.pageObject.webpages.PricingPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TeamPortalPricingPageTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/inhouse/data/TeamPortalPricingData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewEdition(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
                .deleteEditionIfDisplayed(data.getEditionName())
                .clickAddEditionButton()
                .typeEditionName(data.getEditionName())
                .selectRandomMappingIBSservice()
                .typePrice(data.getPrice())
                .clickRecommendedCheckbox()
                .clickAddEditionSubmitButton();
        Assert.assertTrue(pricingPage.isRecommendedEditionDisplayed(data.getEditionName()));
        pricingPage.deleteEditionIfDisplayed(data.getEditionName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddDiscountToEdition(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        AddEditionDialog addEditionDialog = pricingPage
                .deleteEditionIfDisplayed(data.getEditionName())
                .clickAddEditionButton()
                .typeEditionName(data.getEditionName())
                .selectRandomMappingIBSservice()
                .typePrice(data.getPrice())
                .clickRecommendedCheckbox()
                .clickAddDiscountButton()
                .typeMinimumLicenses(data.getMinCommitments().get(0))
                .typeNewPrice(data.getPrices().get(0))
                .clickCancelDiscountButton()
                .clickAddDiscountButton()
                .typeMinimumLicenses(data.getMinCommitments().get(1))
                .typeNewPrice(data.getPrices().get(1))
                .clickSubmitDiscountButton();
        Assert.assertTrue(addEditionDialog.areMinimumCommitmentValuesDisplayed(data.getMinCommitments()));
        Assert.assertTrue(addEditionDialog.areNewPriceValuesDisplayed(data.getPrices()));

        addEditionDialog.clickAddEditionSubmitButton();
        Assert.assertTrue(pricingPage.isRecommendedEditionDisplayed(data.getEditionName()));
        pricingPage.deleteEditionIfDisplayed(data.getEditionName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddSeveralDiscountsToEdition(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        AddEditionDialog addEditionDialog = pricingPage
                .deleteEditionIfDisplayed(data.getEditionName())
                .clickAddEditionButton()
                .typeEditionName(data.getEditionName())
                .selectRandomMappingIBSservice()
                .typePrice(data.getPrice())
                .clickRecommendedCheckbox();
        addEditionDialog.addDiscounts(data.getMinCommitments(), data.getPrices());
        Assert.assertTrue(addEditionDialog.areMinimumCommitmentValuesDisplayed(data.getMinCommitments()));
        Assert.assertTrue(addEditionDialog.areNewPriceValuesDisplayed(data.getPrices()));

        addEditionDialog.clickAddEditionSubmitButton();
        Assert.assertTrue(pricingPage.isRecommendedEditionDisplayed(data.getEditionName()));
        pricingPage.deleteEditionIfDisplayed(data.getEditionName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewFeatureGroup(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupName())
                .clickAddFeatureGroupButton()
                .typeFeatureGroupName(data.getFeatureGroupName())
                .selectFeatureGroupState(data.getFeatureGroupState())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfo())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddSeveralFeaturesToFeatureGroup(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupName())
                .clickAddFeatureGroupButton()
                .typeFeatureGroupName(data.getFeatureGroupName())
                .selectFeatureGroupState(data.getFeatureGroupState())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfo())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
        pricingPage.addFeaturesToFeatureGroup(data.getFeatureGroupName(), data.getFeatureNames(),
                data.getFeatureState(), data.getFeatureMarketingInfoList());
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteFeatureGroup(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupName())
                .clickAddFeatureGroupButton()
                .typeFeatureGroupName(data.getFeatureGroupName())
                .selectFeatureGroupState(data.getFeatureGroupState())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfo())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
        pricingPage.addFeaturesToFeatureGroup(data.getFeatureGroupName(), data.getFeatureNames(),
                data.getFeatureState(), data.getFeatureMarketingInfoList());
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
        pricingPage.verifyFeaturesAreDeleted(data.getFeatureNames());
        Assert.assertFalse(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectDraftStatusForFeatureGroup(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupName())
                .clickAddFeatureGroupButton()
                .typeFeatureGroupName(data.getFeatureGroupName())
                .selectFeatureGroupState(data.getFeatureGroupState())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfo())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
        Assert.assertFalse(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectInternalStatusForFeatureGroup(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupName())
                .clickAddFeatureGroupButton()
                .typeFeatureGroupName(data.getFeatureGroupName())
                .selectFeatureGroupState(data.getFeatureGroupState())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfo())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
        Assert.assertFalse(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
    }
}
