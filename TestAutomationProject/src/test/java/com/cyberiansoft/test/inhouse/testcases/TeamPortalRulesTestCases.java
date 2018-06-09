package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.InHouseRulesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.inhouse.pageObject.AccountsRulesPage;
import com.cyberiansoft.test.inhouse.pageObject.LeftMenuPanel;
import com.cyberiansoft.test.inhouse.pageObject.OrganizationsRulesPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TeamPortalRulesTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/inhouse/data/InHouseRules.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddNewOrganisationRules(String rowID, String description, JSONObject testData) {
        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        OrganizationsRulesPage organizationsRulesPage = leftMenuPanel
                .clickFinancialMapping()
                .clickOrganizationsRulesSubmenu();
        organizationsRulesPage
                .verifyRuleDoesNotExist(data.getRuleName())
                .clickAddNewRuleButton()
                .createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription());
        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName()));
        organizationsRulesPage.deleteRuleByName(data.getRuleName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanSelectOrganisationFromList(String rowID, String description, JSONObject testData) {
        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        OrganizationsRulesPage organizationsRulesPage = leftMenuPanel
                .clickFinancialMapping()
                .clickOrganizationsRulesSubmenu();
        organizationsRulesPage
                .verifyRuleDoesNotExist(data.getRuleName())
                .clickAddNewRuleButton()
                .createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription())
                .clickAddNewRuleButton()
                .selectRuleFromDropDown(data.getRuleName())
                .clickCloseRuleButton();
        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName()));
        organizationsRulesPage.deleteRuleByName(data.getRuleName());
    }

    //todo needs test data update from S. Zakaulov - ruleName2 cannot be updated
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanEditOrganisationRules(String rowID, String description, JSONObject testData) {
        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        OrganizationsRulesPage organizationsRulesPage = leftMenuPanel
                .clickFinancialMapping()
                .clickOrganizationsRulesSubmenu();
        organizationsRulesPage
                .verifyRuleDoesNotExist(data.getRuleName())
                .verifyRuleDoesNotExist(data.getRuleName2())
                .clickAddNewRuleButton()
                .createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription());
        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName()));
        organizationsRulesPage
                .clickEditRuleByName(data.getRuleName())
                //todo delete the next 3 lines after the test data update
                .editExistingRule(data.getRuleName(), data.getCondition2(), data.getOrder2(), data.getRuleDescription2());
        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName()));
        organizationsRulesPage.deleteRuleByName(data.getRuleName());
//              todo uncomment after the test data update
//              .editExistingRule(data.getRuleName2(), data.getCondition2(), data.getOrder2(), data.getRuleDescription2());
//        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName2()));
//        organizationsRulesPage.deleteRuleByName(data.getRuleName2());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddNewAccountsRules(String rowID, String description, JSONObject testData) {
        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        AccountsRulesPage accountsRulesPage = leftMenuPanel
                .clickFinancialMapping()
                .clickAccountsRulesSubmenu();
        accountsRulesPage
                .verifyRuleDoesNotExist(data.getRuleName())
                .clickAddNewRuleButton()
                .createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription());
        Assert.assertTrue(accountsRulesPage.checkRuleByName(data.getRuleName()));
        accountsRulesPage.deleteRuleByName(data.getRuleName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddSelectAccountFromList(String rowID, String description, JSONObject testData) {
        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        AccountsRulesPage accountsRulesPage = leftMenuPanel
                .clickFinancialMapping()
                .clickAccountsRulesSubmenu();
        accountsRulesPage
                .verifyRuleDoesNotExist(data.getRuleName())
                .verifyRuleDoesNotExist(data.getRuleName2())
                .clickAddNewRuleButton()
                .createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription())
                .clickAddNewRuleButton()
                .createNewRuleWithDropDown(data.getRuleName(), data.getCondition2(), data.getOrder2(), data.getRuleDescription2());
        Assert.assertTrue(accountsRulesPage.checkRuleByName(data.getRuleName()));
        accountsRulesPage.deleteRuleByName(data.getRuleName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanEditAccountsRules(String rowID, String description, JSONObject testData) {
        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        AccountsRulesPage accountsRulesPage = leftMenuPanel
                .clickFinancialMapping()
                .clickAccountsRulesSubmenu();
        accountsRulesPage
                .verifyRuleDoesNotExist(data.getRuleName())
                .verifyRuleDoesNotExist(data.getRuleName2())
                .clickAddNewRuleButton()
                .createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription())
                .clickEditRuleByName(data.getRuleName())
                .deleteRuleOrganisation()
                .editExistingRule(data.getRuleName2(), data.getCondition2(), data.getOrder2(), data.getRuleDescription2());
        Assert.assertTrue(accountsRulesPage.checkRuleByName(data.getRuleName2()));
                accountsRulesPage.deleteRuleByName(data.getRuleName2());
    }
}