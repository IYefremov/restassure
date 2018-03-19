package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.pageObject.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

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
        clientQuotesPage.deleteUser(name);

    }

    @DataProvider
    public Object[][] provideNewClientData() {
        return new Object[][]{
                {"CompanyAutomation", "Nock for company", "Address 1", "Address 2", "123AB", "United States",
                        "California", "LA", "+380963665214", "+380963665214", "Test", "User",
                        "Job title", "test.cyberiansoft@gmail.com"}

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
        clientQuotesPage.editClient(name);
        clientQuotesPage.clearAndSetNewClientName("@New_Name");
        clientQuotesPage.clickUpdateClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated("@New_Name"));
        clientQuotesPage.deleteUser("@New_Name");
    }

    @Test(testName = "Test Case 66621:Verify user can add agreement.", dataProvider = "provideNewClientData")
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
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement","Repair360 FREE");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        clientQuotesPage.deleteUser(name);

    }

    @Test(testName = "Test Case 66622:Verify user can select service in agreement.", dataProvider = "provideNewClientData")
    public void testUserCanSelectServiceInAgreement(String name, String nickname, String address, String address2, String zip,
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
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement","Repair360 FREE");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        page = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesDetailPage);
        TeamPortalClientQuotesDetailPage clientQuotesDetailPage = (TeamPortalClientQuotesDetailPage)page;
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage.clickDiscountBTN();
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.10 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));

        leftMenuPanel.clickOnMenu("Client Management");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);

    }

    @Test(testName = "Test Case 66650:Verify user can send notification (sms/email)", dataProvider = "provideNewClientData")
    public void testUserCanSendNotifications(String name, String nickname, String address, String address2, String zip,
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
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement","Repair360 FREE");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        page = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesDetailPage);
        TeamPortalClientQuotesDetailPage clientQuotesDetailPage = (TeamPortalClientQuotesDetailPage)page;
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage.clickDiscountBTN();
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.10 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));
        clientQuotesDetailPage.clickFinalizeAgreementBTN();
        clientQuotesDetailPage.clickSendNotificationButton();
        Assert.assertTrue(clientQuotesDetailPage.checkEmails("Agreement"));
        leftMenuPanel.clickOnMenu("Client Management");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);
    }

    @Test(testName = "Test Case 66655:Verify \"Viewed letter:<date>\", \"Viewed agreement: <date>\" if user open mail with link.", dataProvider = "provideNewClientData")
    public void testUserCanVerifyDatesWhenOpenMailWithLink(String name, String nickname, String address, String address2, String zip,
                                             String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                             String title, String email) throws InterruptedException, IOException {
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
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement","Repair360 FREE");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        page = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesDetailPage);
        TeamPortalClientQuotesDetailPage clientQuotesDetailPage = (TeamPortalClientQuotesDetailPage)page;
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage.clickDiscountBTN();
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.10 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));
        clientQuotesDetailPage.clickFinalizeAgreementBTN();
        clientQuotesDetailPage.clickSendNotificationButton();
        //TODO when dates will be shown correct


        leftMenuPanel.clickOnMenu("Client Management");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);
    }

    @Test(testName = "Test Case 66656:Verify user can pay agreement from mail link.", dataProvider = "provideNewClientData")
    public void testUserCanPayAgreementFromMailLink(String name, String nickname, String address, String address2, String zip,
                                                           String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                                           String title, String email) throws InterruptedException, IOException {
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
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement","Repair360 FREE");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        page = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesDetailPage);
        TeamPortalClientQuotesDetailPage clientQuotesDetailPage = (TeamPortalClientQuotesDetailPage)page;
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage.clickDiscountBTN();
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.1 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));
        clientQuotesDetailPage.clickFinalizeAgreementBTN();
        clientQuotesDetailPage.clickSendNotificationButton();
        Assert.assertTrue(clientQuotesDetailPage.checkEmails("Agreement"));
        String link = clientQuotesDetailPage.getAgreementApproveLink();
        page = clientQuotesDetailPage.goToAgreemntApprovmentPageFromEmail(link);
        Assert.assertTrue(page instanceof TeamPortalAgreementApprovePage);
        TeamPortalAgreementApprovePage agreementApprovePage = (TeamPortalAgreementApprovePage)page;
        agreementApprovePage.fillClientInfo("Anastasia","Maksimova","automationCompany");
        Assert.assertTrue(agreementApprovePage.checkTermsAndConditions());
        agreementApprovePage.clickAgreeWithTermsAndConditionsBTN();
        agreementApprovePage.clickAcceptAgreementBTN();
        agreementApprovePage.fillFeesPayment("4242424242424242","10","2026","123");
        agreementApprovePage.clickPayBTN();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage("$1,776.00","4242424242424242"));
        agreementApprovePage.clickCancelPayBTN();
        agreementApprovePage.clickPayBTN();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage("$1,776.00","4242424242424242"));
        agreementApprovePage.clickApprovePayBTN();
        agreementApprovePage.goToPreviousPage();

        leftMenuPanel.clickOnMenu("Client Management");
        page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);
    }

}