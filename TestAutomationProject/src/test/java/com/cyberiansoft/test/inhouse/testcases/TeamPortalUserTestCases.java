package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.pageObject.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

public class TeamPortalUserTestCases extends BaseTestCase {

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

    @Test(testName = "Test Case 66618:Verify user can add new Client", dataProvider = "provideNewClientData")
    public void testUserCanAddNewClient(String name, String nickname, String address, String address2, String zip,
     String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                        String title, String email) throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Client Management");
        BasePage page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        TeamPortalClientQuotesPage clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile( name,  nickname,  address,  address2,  zip,
                 country,  state,  city,  businessPhone,  cellPhone,  firstName,  lastName,
                 title,  email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.deleteUser("@Client_name");

    }

    @DataProvider
    public Object[][] provideNewClientData() {
        return new Object[][]{
                {"@Client_name", "@Client_nickname", "@Client_address", "@Client_address2", "@Client_zip", "United States",
                        "@Client_state", "@Client_city", "@Business_phone", "@Cell_Phone", "@First_Name", "@Last_Name",
                        "@Title", "@Email"}

        };
    }

    @Test(testName = "Test Case 66619:Verify user can edit Client information." , dataProvider = "provideNewClientData")
    public void testUserCanEditClientInformation(String name, String nickname, String address, String address2, String zip,
                                                 String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                                 String title, String email) throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Client Management");
        BasePage page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        TeamPortalClientQuotesPage clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile( name,  nickname,  address,  address2,  zip,
                country,  state,  city,  businessPhone,  cellPhone,  firstName,  lastName,
                title,  email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.editClient("@Client_name");
        clientQuotesPage.clearAndSetNewClientName("@New_Name");
        clientQuotesPage.clickUpdateClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated("@New_Name"));
        clientQuotesPage.deleteUser("@New_Name");
    }

    @Test(testName = "Test Case 66618:Verify user can add new Client", dataProvider = "provideNewClientData")
    public void testUserCanAddAgreement(String name, String nickname, String address, String address2, String zip,
                                        String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                        String title, String email) throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Client Management");
        BasePage page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        TeamPortalClientQuotesPage clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile( name,  nickname,  address,  address2,  zip,
                country,  state,  city,  businessPhone,  cellPhone,  firstName,  lastName,
                title,  email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.clickAgreementBTN("@Client_name");
        clientQuotesPage.setAgreement("@Agreement_Name","Team");
        clientQuotesPage.refreshPage();
        clientQuotesPage.searchUser("@Client_name");
        clientQuotesPage.expandAgreementList("@Client_name");
        clientQuotesPage.clickEditAgreement("@Agreement_Name");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Team"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("@Agreement_Name2"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("@Agreement_Name2"));
        clientQuotesPage.deleteUser("@Client_name");

    }

}