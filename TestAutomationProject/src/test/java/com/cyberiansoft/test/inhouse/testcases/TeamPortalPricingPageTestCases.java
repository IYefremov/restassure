package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.TeamPortalPricingData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.inhouse.pageObject.webpages.*;
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
                .typeMinimumLicense(data.getMinCommitments().get(0))
                .typeNewPrice(data.getPrices().get(0))
                .clickCancelDiscountButton()
                .clickAddDiscountButton()
                .typeMinimumLicense(data.getMinCommitments().get(1))
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
    public void verifyUserCanDeleteFeatureFromFeatureGroup(String rowID, String description, JSONObject testData) {
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
        pricingPage.deleteFeature(data.getFeatureGroupName(), data.getFeatureNames().get(1));
        Assert.assertTrue(pricingPage.isFeatureNotDisplayed(data.getFeatureGroupName(), data.getFeatureNames().get(1)),
                "The feature has been not deleted");
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddSetupFeeToFeature(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode hasn't been enabled");
        pricingPage
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupName())
                .clickAddFeatureGroupButton()
                .typeFeatureGroupName(data.getFeatureGroupName())
                .selectFeatureGroupState(data.getFeatureGroupState())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfo())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
        UpdateFeatureDialog updateFeatureDialog = pricingPage
                .addFeaturesToFeatureGroup(data.getFeatureGroupName(), data.getFeatureNames(), data.getFeatureState(),
                        data.getFeatureMarketingInfoList())
                .clickFeature(data.getFeatureGroupName(), data.getFeatureNames().get(0));
        updateFeatureDialog
                .clickAddSetupFeeButton()
                .typeSetupFeeName(data.getSetupFeeName())
                .typeSetupFeeQuantity(data.getSetupFeeQuantity())
                .typeSetupFeePrice(data.getSetupFeePrice())
                .typeSetupFeeEstimatedHours(data.getSetupFeeEstimatedHours())
                .clickSetupFeeCancelButton();
        Assert.assertTrue(updateFeatureDialog.isEmptySetupFeeTableDisplayed(), "The setup fee table is not empty");

        updateFeatureDialog
                .clickAddSetupFeeButton()
                .typeSetupFeeName(data.getSetupFeeName2())
                .typeSetupFeeQuantity(data.getSetupFeeQuantity2())
                .typeSetupFeePrice(data.getSetupFeePrice2())
                .typeSetupFeeEstimatedHours(data.getSetupFeeEstimatedHours2())
                .clickSetupFeeSubmitButton();

        Assert.assertTrue(updateFeatureDialog.getSetupFeeTableDataText().containsAll(data.getSetupFeeData2()));

        updateFeatureDialog.clickUpdateFeatureButton();
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
        pricingPage.verifyFeaturesAreDeleted(data.getFeatureGroupName(), data.getFeatureNames());
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectPublicStatusForFeatureGroup(String rowID, String description, JSONObject testData) {
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
    public void verifyUserCanSelectDraftStatusForFeature(String rowID, String description, JSONObject testData) {
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

        AddFeatureDialog addFeatureDialog = (AddFeatureDialog) pricingPage
                .clickAddForFeatureGroupDisplayed(data.getFeatureGroupName())
                .typeFeatureName(data.getFeatureName())
                .selectFeatureState(data.getFeatureState())
                .typeMarketingInfo(data.getMarketingInfo());
        addFeatureDialog.clickAddFeatureButton();
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureName()));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectPrivateViewStatusForFeatureGroup(String rowID, String description, JSONObject testData) {
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

        AddFeatureDialog addFeatureDialog = (AddFeatureDialog) pricingPage
                .clickAddForFeatureGroupDisplayed(data.getFeatureGroupName())
                .typeFeatureName(data.getFeatureName())
                .selectFeatureState(data.getFeatureState())
                .typeDescription(data.getFeatureDescription())
                .typeMarketingInfo(data.getMarketingInfo());
        addFeatureDialog.clickAddFeatureButton();
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureName()));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectPublicViewStatusForFeatureGroup(String rowID, String description, JSONObject testData) {
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

        AddFeatureDialog addFeatureDialog = (AddFeatureDialog) pricingPage
                .clickAddForFeatureGroupDisplayed(data.getFeatureGroupName())
                .typeFeatureName(data.getFeatureName())
                .selectFeatureState(data.getFeatureState())
                .typeDescription(data.getFeatureDescription())
                .typeMarketingInfo(data.getMarketingInfo());
        addFeatureDialog.clickAddFeatureButton();
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureName()));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteCreatedEdition(String rowID, String description, JSONObject testData) {
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

        AddFeatureDialog addFeatureDialog = (AddFeatureDialog) pricingPage
                .clickAddForFeatureGroupDisplayed(data.getFeatureGroupName())
                .typeFeatureName(data.getFeatureName())
                .selectFeatureState(data.getFeatureState())
                .typeDescription(data.getFeatureDescription())
                .typeMarketingInfo(data.getMarketingInfo());

        addFeatureDialog.clickAddFeatureButton();
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureName()));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
        Assert.assertFalse(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectFeatureForEdition(String rowID, String description, JSONObject testData) {
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

        AddFeatureDialog addFeatureDialog = (AddFeatureDialog) pricingPage
                .clickAddForFeatureGroupDisplayed(data.getFeatureGroupName())
                .typeFeatureName(data.getFeatureNames().get(0))
                .selectFeatureState(data.getFeatureStates().get(0))
                .typeDescription(data.getFeatureDescriptions().get(0))
                .typeMarketingInfo(data.getMarketingInfoOptions().get(0));
        addFeatureDialog.clickAddFeatureButton();
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureNames().get(0)));

        UpdateFeatureDialog updateFeatureDialog = (UpdateFeatureDialog) pricingPage
                .clickFeature(data.getFeatureGroupName(), data.getFeatureNames().get(0))
                .typeFeatureName(data.getFeatureNames().get(1))
                .selectFeatureState(data.getFeatureStates().get(1))
                .typeDescription(data.getFeatureDescriptions().get(1))
                .typeMarketingInfo(data.getMarketingInfoOptions().get(1));
        updateFeatureDialog.clickUpdateFeatureButton();
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureNames().get(1)));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelFeatureForEdition(String rowID, String description, JSONObject testData) {
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

        AddFeatureDialog addFeatureDialog = (AddFeatureDialog) pricingPage
                .clickAddForFeatureGroupDisplayed(data.getFeatureGroupName())
                .typeFeatureName(data.getFeatureNames().get(0))
                .selectFeatureState(data.getFeatureStates().get(0))
                .typeDescription(data.getFeatureDescriptions().get(0))
                .typeMarketingInfo(data.getMarketingInfoOptions().get(0));
        addFeatureDialog.clickAddFeatureButton();
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureNames().get(0)));

        UpdateFeatureDialog updateFeatureDialog = (UpdateFeatureDialog) pricingPage
                .clickFeature(data.getFeatureGroupName(), data.getFeatureNames().get(0))
                .typeFeatureName(data.getFeatureNames().get(1))
                .selectFeatureState(data.getFeatureStates().get(1))
                .typeDescription(data.getFeatureDescriptions().get(1))
                .typeMarketingInfo(data.getMarketingInfoOptions().get(1));
        updateFeatureDialog.clickCloseButton();
        Assert.assertTrue(pricingPage.isFeatureNotDisplayed(data.getFeatureGroupName(), data.getFeatureNames().get(1)));
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureNames().get(0)));
        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCreateAddOnOptionForFeatureInEdition(String rowID, String description, JSONObject testData) {
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

        pricingPage.clickFirstRecommendedToggle();
        Assert.assertTrue(pricingPage.isRecommendedEditionDropupOpened(), "The dropup has not been opened for the recommended edition toggle");
        pricingPage
                .clickAddAddonEditionFeatureButton()
                .typePricePerMonth(data.getPricePerMonth())
                .typePricePerYear(data.getPricePerYear())
                .clickSubmitEditionFeatureButton();
        Assert.assertTrue(pricingPage.labelContainsPrice(data.getPricePerMonth()),
                "The price per month has not been saved for the recommended edition");
        Assert.assertTrue(pricingPage.labelContainsPrice(data.getPricePerYear()),
                "The price per year has not been saved for the recommended edition");
        pricingPage.deleteEditionIfDisplayed(data.getEditionName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeDiscountForEdition(String rowID, String description, JSONObject testData) {
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
                .typePrice(data.getPrices().get(0))
                .clickRecommendedCheckbox()
                .clickAddEditionSubmitButton();
        Assert.assertTrue(pricingPage.isRecommendedEditionDisplayed(data.getEditionName()));
        UpdateEditionDialog updateEditionDialog = pricingPage
                .clickEditEditionButton(data.getEditionName())
                .clickAddDiscountButton()
                .typeMinimumLicense(data.getMinCommitments().get(0))
                .typePrice(data.getPrices().get(0))
                .clickSubmitDiscountButton();
        updateEditionDialog.clickUpdateEditionButton();

        Assert.assertTrue(updateEditionDialog.areMinimumCommitmentValuesDisplayed(data.getMinCommitments()));
        Assert.assertTrue(updateEditionDialog.areNewPriceValuesDisplayed(data.getPrices()));

        pricingPage.deleteEditionIfDisplayed(data.getEditionName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditFeatureGroup(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .enableEditMode();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupName())
                .deleteFeatureGroupIfDisplayed(data.getFeatureGroupNameEdited())
                .clickAddFeatureGroupButton()
                .typeFeatureGroupName(data.getFeatureGroupName())
                .selectFeatureGroupState(data.getFeatureGroupState())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfo())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));

        pricingPage
                .clickFeatureGroup(data.getFeatureGroupName())
                .typeFeatureGroupName(data.getFeatureGroupNameEdited())
                .selectFeatureGroupState(data.getFeatureGroupStateEdited())
                .typeMarketingInfo(data.getFeatureGroupMarketingInfoEdited())
                .clickFeatureGroupSubmitButton();
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupNameEdited()));
        FeatureGroupDialog featureGroupDialog = pricingPage.clickFeatureGroup(data.getFeatureGroupNameEdited());
        Assert.assertEquals(featureGroupDialog.getSelectedFeatureGroupState(), data.getFeatureGroupStateEdited(),
                "The feature group state hasn't been changed after editing");
        featureGroupDialog.clickFeatureGroupCancelButton(data.getFeatureGroupNameEdited());

        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupNameEdited());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelDeletingFeatureGroup(String rowID, String description, JSONObject testData) {
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

        pricingPage.cancelDeletingFeatureGroupIfDisplayed(data.getFeatureGroupName());
        Assert.assertTrue(pricingPage.isFeatureGroupDisplayed(data.getFeatureGroupName()));

        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupNameEdited());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelDeletingFeatureFromFeatureGroup(String rowID, String description, JSONObject testData) {
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
        pricingPage.cancelDeletingFeature(data.getFeatureGroupName(), data.getFeatureNames().get(0));
        Assert.assertTrue(pricingPage.isFeatureDisplayed(data.getFeatureGroupName(), data.getFeatureNames().get(0)));

        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddSetupFeeForFeatureGroup(String rowID, String description, JSONObject testData) {
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

        UpdateFeatureDialog updateFeatureDialog = pricingPage.clickFeature(data.getFeatureGroupName(), data.getFeatureNames().get(0));
        updateFeatureDialog.clickAddSetupFeeButton()
                .typeSetupFeeName(data.getSetupFeeName())
                .typeSetupFeeQuantity(data.getSetupFeeQuantity())
                .typeSetupFeePrice(data.getSetupFeePrice())
                .typeSetupFeeEstimatedHours(data.getSetupFeeEstimatedHours())
                .clickSetupFeeSubmitButton();
        Assert.assertTrue(updateFeatureDialog.getSetupFeeTableDataText().containsAll(data.getSetupFeeData()));
        updateFeatureDialog.clickUpdateFeatureButton();

        pricingPage.deleteFeatureGroupIfDisplayed(data.getFeatureGroupName());
    }
}