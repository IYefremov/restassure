package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouse.InHouseRulesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
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
        organizationsRulesPage.verifyRuleDoesntExist(data.getRuleName());
        organizationsRulesPage.clickAddNewButton();
        organizationsRulesPage.createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription(), false);
        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName()));
    }

    //todo check the steps in the TC. Seems to be a repeater.
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanSelectOrganisationFromList(String rowID, String description, JSONObject testData) {
        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        OrganizationsRulesPage organizationsRulesPage = leftMenuPanel
                .clickFinancialMapping()
                .clickOrganizationsRulesSubmenu();
        organizationsRulesPage.clickAddNewButton();
        organizationsRulesPage.createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription(), true);
        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName()));
        organizationsRulesPage.deleteRuleByName(data.getRuleName());
    }

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testUserCanEditOrganisationRules(String rowID, String description, JSONObject testData) throws InterruptedException {
//        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
//        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
//        OrganizationsRulesPage organizationsRulesPage = leftMenuPanel
//                .clickFinancialMapping()
//                .clickOrganizationsRulesSubmenu();
////        leftMenuPanel.clickOnMenu("Financial Mapping");
////        BasePage page = leftMenuPanel.clickOnMenu("Organizations Rules");
////        Assert.assertTrue(page instanceof OrganizationsRulesPage);
////        OrganizationsRulesPage organizationsRulesPage = (OrganizationsRulesPage) page;
//        organizationsRulesPage.clickAddNewButton();
//        organizationsRulesPage.createNewRule(data.getRuleName(), data.getCondition(), data.getOrder(), data.getRuleDescription(), false);
//        Assert.assertTrue(organizationsRulesPage.checkRuleByName(data.getRuleName()));
//        organizationsRulesPage.clickEditRuleByName(data.getRuleName());
//        organizationsRulesPage.editExistingRule(data.getRuleDescription(), "Description LIKE '%MOTEL%'", "2", "Test 1234");
//        organizationsRulesPage.deleteRuleByName(data.getRuleDescription());
//    }
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testUserCanAddNewAccountsRules(String rowID, String description, JSONObject testData) throws InterruptedException {
//        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
//        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
//        AccountsRulesPage accountsRulesPage = leftMenuPanel
//                .clickFinancialMapping()
//                .clickAccountsRulesSubmenu();
//
////        leftMenuPanel.clickOnMenu("Financial Mapping");
////        BasePage page = leftMenuPanel.clickOnMenu("Accounts Rules");
////        Assert.assertTrue(page instanceof AccountsRulesPage);
////        AccountsRulesPage accountsRulesPage = (AccountsRulesPage) page;
//        accountsRulesPage.clickAddNewAccountRuleButton();
//        accountsRulesPage.createNewRule("Test account rule", "Description LIKE '%Skype%'", "2", "Test description", false);
//        accountsRulesPage.deleteRuleByName("Test account rule");
//    }
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testUserCanAddSelectAccountFromList(String rowID, String description, JSONObject testData) throws InterruptedException {
//        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
//        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
//        AccountsRulesPage accountsRulesPage = leftMenuPanel
//                .clickFinancialMapping()
//                .clickAccountsRulesSubmenu();
////        leftMenuPanel.clickOnMenu("Financial Mapping");
////        BasePage page = leftMenuPanel.clickOnMenu("Accounts Rules");
////        Assert.assertTrue(page instanceof AccountsRulesPage);
////        AccountsRulesPage accountsRulesPage = (AccountsRulesPage) page;
//        accountsRulesPage.clickAddNewAccountRuleButton();
//        accountsRulesPage.createNewRule("Test account rule", "Description LIKE '%Skype%'", "2", "Test description", true);
//        accountsRulesPage.deleteRuleByName("Test account rule");
//    }
//
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    public void testUserCanEditAccountsRules(String rowID, String description, JSONObject testData) throws InterruptedException {
//        InHouseRulesData data = JSonDataParser.getTestDataFromJson(testData, InHouseRulesData.class);
//        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
//        AccountsRulesPage accountsRulesPage = leftMenuPanel
//                .clickFinancialMapping()
//                .clickAccountsRulesSubmenu();
////        leftMenuPanel.clickOnMenu("Financial Mapping");
////        BasePage page = leftMenuPanel.clickOnMenu("Accounts Rules");
////        Assert.assertTrue(page instanceof AccountsRulesPage);
////        AccountsRulesPage accountsRulesPage = (AccountsRulesPage) page;
//        accountsRulesPage.clickAddNewAccountRuleButton();
//        accountsRulesPage.createNewRule("Test account rule", "Description LIKE '%Skype%'", "2", "Test description", false);
//        accountsRulesPage.clickEditRuleByName("Test account rule");
//        accountsRulesPage.editExistingRule("Account", "Description LIKE '%ADOBE%'", "5", "new description");
//        accountsRulesPage.deleteRuleByName("Account");
//    }
}