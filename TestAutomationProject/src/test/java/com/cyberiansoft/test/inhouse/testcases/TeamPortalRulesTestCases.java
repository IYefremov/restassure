package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.pageObject.BasePage;
import com.cyberiansoft.test.inhouse.pageObject.OrganizationsRulesPage;
import com.cyberiansoft.test.inhouse.pageObject.TeamPortalAccountsRulesPage;
import com.cyberiansoft.test.inhouse.pageObject.TeamPortalLeftMenuPanel;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TeamPortalRulesTestCases extends BaseTestCase {

    @Test(testName = "Test Case 59858:Verify user can add new organization rules.")
    public void testUserCanAddNewOrganisationRules() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Financial Mapping");
        BasePage page = leftMenuPanel.clickOnMenu("Organizations Rules");
        Assert.assertTrue(page instanceof OrganizationsRulesPage);
        OrganizationsRulesPage organizationsRulesPage = (OrganizationsRulesPage) page;
        organizationsRulesPage.clickAddNewButton();
        organizationsRulesPage.createNewRule("Test Organization", "Description LIKE '%AMT%'", "1", "Test", false);
        Assert.assertTrue(organizationsRulesPage.checkRuleByName("Test Organization"));
        organizationsRulesPage.deleteRuleByName("Test Organization");
    }

    @Test(testName = "Test Case 59859:Verify user can select organization from the list.")
    public void testUserCanSelecyOrganisationFromList() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Financial Mapping");
        BasePage page = leftMenuPanel.clickOnMenu("Organizations Rules");
        Assert.assertTrue(page instanceof OrganizationsRulesPage);
        OrganizationsRulesPage organizationsRulesPage = (OrganizationsRulesPage) page;
        organizationsRulesPage.clickAddNewButton();
        organizationsRulesPage.createNewRule("Test Organization", "Description LIKE '%AMT%'", "1", "Test", true);
        Assert.assertTrue(organizationsRulesPage.checkRuleByName("Test Organization"));
        organizationsRulesPage.deleteRuleByName("Test Organization");
    }

    @Test(testName = "Test Case 59861:Verify user can edit organization rules.")
    public void testUserCanEditOrganisationRules() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Financial Mapping");
        BasePage page = leftMenuPanel.clickOnMenu("Organizations Rules");
        Assert.assertTrue(page instanceof OrganizationsRulesPage);
        OrganizationsRulesPage organizationsRulesPage = (OrganizationsRulesPage) page;
        organizationsRulesPage.clickAddNewButton();
        organizationsRulesPage.createNewRule("Test Organization", "Description LIKE '%AMT%'", "1", "Test", false);
        Assert.assertTrue(organizationsRulesPage.checkRuleByName("Test Organization"));
        organizationsRulesPage.clickEditRuleByName("Test Organization");
        organizationsRulesPage.editExistingRule("Test", "Description LIKE '%MOTEL%'", "2", "Test 1234");

        organizationsRulesPage.deleteRuleByName("Test");
    }

    @Test(testName = "Test Case 59864:Verify user can add new Accounts Rules.")
    public void testUserCanAddNewAccountsRules() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Financial Mapping");
        BasePage page = leftMenuPanel.clickOnMenu("Accounts Rules");
        Assert.assertTrue(page instanceof TeamPortalAccountsRulesPage);
        TeamPortalAccountsRulesPage accountsRulesPage = (TeamPortalAccountsRulesPage) page;
        accountsRulesPage.clickAddNewAccountRuleButton();
        accountsRulesPage.createNewRule("Test account rule", "Description LIKE '%Skype%'", "2", "Test description", false);
        accountsRulesPage.deleteRuleByName("Test account rule");
    }

    @Test(testName = "Test Case 59865:Verify user can select account from the list.")
    public void testUserCanAddSelectAccountFromList() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Financial Mapping");
        BasePage page = leftMenuPanel.clickOnMenu("Accounts Rules");
        Assert.assertTrue(page instanceof TeamPortalAccountsRulesPage);
        TeamPortalAccountsRulesPage accountsRulesPage = (TeamPortalAccountsRulesPage) page;
        accountsRulesPage.clickAddNewAccountRuleButton();
        accountsRulesPage.createNewRule("Test account rule", "Description LIKE '%Skype%'", "2", "Test description", true);
        accountsRulesPage.deleteRuleByName("Test account rule");
    }

    @Test(testName = "Test Case 59867:Verify user can edit Accounts Rules.")
    public void testUserCanEditAccountsRules() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Financial Mapping");
        BasePage page = leftMenuPanel.clickOnMenu("Accounts Rules");
        Assert.assertTrue(page instanceof TeamPortalAccountsRulesPage);
        TeamPortalAccountsRulesPage accountsRulesPage = (TeamPortalAccountsRulesPage) page;
        accountsRulesPage.clickAddNewAccountRuleButton();
        accountsRulesPage.createNewRule("Test account rule", "Description LIKE '%Skype%'", "2", "Test description", false);
        accountsRulesPage.clickEditRuleByName("Test account rule");
        accountsRulesPage.editExistingRule("Account", "Description LIKE '%ADOBE%'", "5", "new description");
        accountsRulesPage.deleteRuleByName("Account");
    }
} 
