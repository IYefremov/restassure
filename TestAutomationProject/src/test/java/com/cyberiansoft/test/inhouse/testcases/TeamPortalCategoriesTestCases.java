package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.pageObject.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

public class TeamPortalCategoriesTestCases extends BaseTestCase {
    @BeforeMethod
    @Parameters({"backoffice.url"})
    public void teamPortalLogin(String backofficeurl) throws InterruptedException {
        webdriverGotoWebPage(backofficeurl);
        TeamPortalLoginPage loginPage = PageFactory.initElements(webdriver,
                TeamPortalLoginPage.class);
        loginPage.loginByGmail();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void teamPortalLogout() throws InterruptedException {
        TeamPortalHeader headerPanel = PageFactory.initElements(webdriver,
                TeamPortalHeader.class);
        headerPanel.clickLogOutButton();
        Thread.sleep(1000);
    }

    @DataProvider
    public Object[][] provideNewClientData() {
        return new Object[][]{
                {"CompanyAutomation", "Nock for company", "Address 1", "Address 2", "123AB", "United States",
                        "California", "LA", "+380963665214", "+380963665214", "Test", "User",
                        "Job title", "automationvozniuk@gmail.com"}

        };
    }

    @Test(testName = "Test Case 59877:Verify user can add new categories.")
    public void testUserCanAddNewCategories() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Clients");
        BasePage page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        TeamPortalCategoriesPage categoriesPage = (TeamPortalCategoriesPage) page;
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.deleteCategory("Test Category");
    }

    @Test(testName = "Test Case 59879:Verify user can add new categories.")
    public void testUserCanAddAttributeToCategories() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Clients");
        BasePage page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        TeamPortalCategoriesPage categoriesPage = (TeamPortalCategoriesPage) page;
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillNotAutomatedAttributeFields("Test Attribute Name", "String");
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "Test Attribute Name"));
        categoriesPage.deleteCategory("Test Category");
    }

    // @Test(testName = "Test Case 59880:Verify automated attribute.")
    public void testUserCanAddAutomatedAttributeToCategories() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Clients");
        BasePage page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        TeamPortalCategoriesPage categoriesPage = (TeamPortalCategoriesPage) page;
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillAutomatedAttributeFields("Test Attribute Name", "Number");
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "Test Attribute Name"));
        categoriesPage.deleteCategory("Test Category");
    }

    @Test(testName = "Test Case 59881:Verify manual attribute.", dataProvider = "provideNewClientData")
    public void testUserCanManualAttribute(String name, String nickname, String address, String address2, String zip,
                                           String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                           String title, String email) throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Client Management");
        BasePage page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        TeamPortalClientQuotesPage clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip,
                country, state, city, businessPhone, cellPhone, firstName, lastName,
                title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        leftMenuPanel.clickOnMenu("Clients");
        page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        TeamPortalCategoriesPage categoriesPage = (TeamPortalCategoriesPage) page;
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillNotAutomatedAttributeFields("Manual attribute", "String");
        categoriesPage.clickAddAttributeButton();
        page = leftMenuPanel.clickOnMenu("Client Segments");
        Assert.assertTrue(page instanceof TeamPortalClientSegmentsPage);
        TeamPortalClientSegmentsPage clientSegmentsPage = (TeamPortalClientSegmentsPage) page;
        clientSegmentsPage.searchClientSegment("CompanyAutomation");
        clientSegmentsPage.expandAttributesList("CompanyAutomation");
        clientSegmentsPage.setAttributeValue("Manual attribute", "15");
        clientSegmentsPage.refreshPage();
        clientSegmentsPage.searchClientSegment("CompanyAutomation");
        clientSegmentsPage.expandAttributesList("CompanyAutomation");
        Assert.assertTrue(clientSegmentsPage.checkAttributeValue("Manual attribute", "15"));
        leftMenuPanel.clickOnMenu("Client Management");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser("CompanyAutomation");
        clientQuotesPage.deleteUser("CompanyAutomation");
        leftMenuPanel.clickOnMenu("Clients");
        page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        categoriesPage = (TeamPortalCategoriesPage) page;
        categoriesPage.deleteCategory("Test Category");
    }

    @Test(testName = "Test Case 59882:Verify user can search by key-word.")
    public void testUserCanSearchByKeyWord() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Clients");
        BasePage page = leftMenuPanel.clickOnMenu("Client Segments");
        Assert.assertTrue(page instanceof TeamPortalClientSegmentsPage);
        TeamPortalClientSegmentsPage clientSegmentsPage = (TeamPortalClientSegmentsPage) page;
        clientSegmentsPage.searchClientSegment("Google");
        Assert.assertTrue(clientSegmentsPage.checkClientyName("Google"));
    }

    //@Test(testName = "Test Case 59883:Verify user can search by categories.")
    public void testUserCanSearchCategories() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Clients");
        BasePage page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        TeamPortalCategoriesPage categoriesPage = (TeamPortalCategoriesPage) page;
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        page = leftMenuPanel.clickOnMenu("Client Segments");
        Assert.assertTrue(page instanceof TeamPortalClientSegmentsPage);
        TeamPortalClientSegmentsPage clientSegmentsPage = (TeamPortalClientSegmentsPage) page;
//no functional
        categoriesPage.deleteCategory("Test Category");
    }

    //    @Test(testName = "Test Case 59885:Verify created category displays with attributes on the page.")
    public void testCategiriesdisplaysWithAttributes() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Clients");
        BasePage page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        TeamPortalCategoriesPage categoriesPage = (TeamPortalCategoriesPage) page;
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillNotAutomatedAttributeFields("Test Attribute Name", "String");
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "Test Attribute Name"));
        page = leftMenuPanel.clickOnMenu("Client Segments");
        Assert.assertTrue(page instanceof TeamPortalClientSegmentsPage);
        TeamPortalClientSegmentsPage clientSegmentsPage = (TeamPortalClientSegmentsPage) page;
//no functional
        categoriesPage.deleteCategory("Test Category");
    }

    //Test Case 59886:Verify user can select/deselect category.
    //Test Case 59887:Verify user can add a value to attribute.
} 
