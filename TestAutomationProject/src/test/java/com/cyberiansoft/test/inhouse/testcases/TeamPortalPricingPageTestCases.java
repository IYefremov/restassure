package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.TeamPortalPricingData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
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
    public void testUserCanAddNewOrganisationRules(String rowID, String description, JSONObject testData) {
        TeamPortalPricingData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalPricingData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        PricingPage pricingPage = leftMenuPanel
                .clickPricing()
                .clickEditModeButton();
        Assert.assertTrue(pricingPage.isEditModeEnabled(), "The Edit mode has not been enabled");
        pricingPage
//                .verifyEditionIsNotDisplayed(data.getEditionName())
                .clickAddEditionButton()
                .typeEditionName(data.getEditionName())
                .selectRandomMappingIBSservice()
                .typePrice(data.getPrice())
                .clickRecommendedCheckbox()
                .clickAddEditionSubmitButton();
        Assert.assertTrue(pricingPage.isRecommendedEditionDisplayed(data.getEditionName()));
    }
}
