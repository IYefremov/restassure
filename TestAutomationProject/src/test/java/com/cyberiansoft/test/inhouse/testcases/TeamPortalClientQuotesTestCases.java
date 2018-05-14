package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import com.cyberiansoft.test.inhouse.pageObject.AgreementApprovePage;
import com.cyberiansoft.test.inhouse.pageObject.ClientQuotesDetailPage;
import com.cyberiansoft.test.inhouse.pageObject.ClientQuotesPage;
import com.cyberiansoft.test.inhouse.pageObject.LeftMenuPanel;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TeamPortalClientQuotesTestCases extends BaseTestCase {

    @Test(testName = "Test Case 66618:Verify user can add new Client", dataProvider = "provideNewClientData")
    public void testUserCanAddNewClient(String name, String nickname, String address, String address2, String zip,
                                        String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                        String title, String email) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip,
                country, state, city, businessPhone, cellPhone, firstName, lastName,
                title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.deleteUser(name);
    }

    @DataProvider
    public Object[][] provideNewClientData() {
        return new Object[][]{
                {"CompanyAutomation", "Nock for company", "Address 1", "Address 2", "123AB", "United States",
                        "California", "LA", "+380963665214", "+380963665214", "Test", "User",
//                        "Job title", "automationvozniuk@gmail.com"}
                        "Job title", InHouseConfigInfo.getInstance().getUserEmail()}

        };
    }

    @Test(testName = "Test Case 66619:Verify user can edit Client information.", dataProvider = "provideNewClientData")
    public void testUserCanEditClientInformation(String name, String nickname, String address, String address2, String zip,
                                                 String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                                 String title, String email) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip,
                country, state, city, businessPhone, cellPhone, firstName, lastName,
                title, email);
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
                                        String title, String email) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip,
                country, state, city, businessPhone, cellPhone, firstName, lastName,
                title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement", "Repair360 FREE");

        leftMenuPanel.clickClientQuotesSubmenu();
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
                                                    String title, String email) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip,
                country, state, city, businessPhone, cellPhone, firstName, lastName,
                title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement", "Repair360 FREE");
        leftMenuPanel.clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New", "No", "No", "No"));
        clientQuotesDetailPage.clickDiscountBTN();
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.10 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);

    }

    @Test(testName = "Test Case 66650:Verify user can send notification (sms/email)", dataProvider = "provideNewClientData")
    public void testUserCanSendNotifications(String name, String nickname, String address, String address2, String zip,
                                             String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                             String title, String email) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip,
                country, state, city, businessPhone, cellPhone, firstName, lastName,
                title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement", "Repair360 FREE");
        leftMenuPanel.clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New", "No", "No", "No"));
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

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);
    }

    @Test(testName = "Test Case 66655:Verify \"Viewed letter:<date>\", \"Viewed agreement: <date>\" if user open mail with link.", dataProvider = "provideNewClientData")
    public void testUserCanVerifyDatesWhenOpenMailWithLink(String name, String nickname, String address, String address2, String zip,
                                                           String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                                           String title, String email) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip, country, state, city,
                businessPhone, cellPhone, firstName, lastName, title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement", "Repair360 FREE");
        clientQuotesPage = leftMenuPanel.clickClientQuotesSubmenu();

        clientQuotesPage.searchUser(name);
        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New", "No", "No", "No"));
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

        clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);
    }

    @Test(testName = "Test Case 66656:Verify user can pay agreement from mail link.", dataProvider = "provideNewClientData")
    public void testUserCanPayAgreementFromMailLink(String name, String nickname, String address, String address2,
                                                    String zip, String country, String state, String city,
                                                    String businessPhone, String cellPhone, String firstName,
                                                    String lastName, String title, String email) {

        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        // the user is deleted in preconditions, because otherwise the Agreement statuses are not stable
        // (instead of "New" the "Finalized" can appear)
        clientQuotesPage.searchUser(name);
        clientQuotesPage.deleteUser(name);

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip, country, state, city,
                businessPhone, cellPhone, firstName, lastName, title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));
        clientQuotesPage.clickAddAgreementBTN(name);
        clientQuotesPage.setAgreement("First agreement", "Repair360 FREE");
        clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.verifyUserWasCreated(name);

        clientQuotesPage.expandAgreementList(name);
        clientQuotesPage.clickEditAgreement("First agreement");
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition("Repair360 FREE"));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName("Second Agreement"));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName("Second Agreement"));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementBTN("Second Agreement");
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New", "No", "No", "No"));
        clientQuotesDetailPage.clickDiscountBTN();
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.1 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));
        clientQuotesDetailPage.selectSetupFeeForAllClients();
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1746.00"), "The setupFee is not calculated properly!");
        clientQuotesDetailPage.clickFinalizeAgreementBTN();
        clientQuotesDetailPage.clickSendNotificationButton();
        Assert.assertTrue(clientQuotesDetailPage.checkEmails("AMT Agreement for Approval"));
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("Finalized", "No", "No", "No"));
        String link = clientQuotesDetailPage.getAgreementApproveLink();

        AgreementApprovePage agreementApprovePage =
                (AgreementApprovePage) clientQuotesDetailPage.goToAgreementApprovementPageFromEmail(link);
        agreementApprovePage.fillClientInfo("Anastasia", "Maksimova", name);
        Assert.assertTrue(agreementApprovePage.checkTermsAndConditions());
        agreementApprovePage.clickAgreeWithTermsAndConditionsBTN();
        agreementApprovePage.clickAcceptAgreementBTN();
        agreementApprovePage.fillFeesPayment("4242424242424242", "10", "2026", "123");
        agreementApprovePage.clickPayBTN();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage("$1,746.00", "4242424242424242"));
        agreementApprovePage.clickCancelPayBTN();
        agreementApprovePage.clickPayBTN();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage("$1,746.00", "4242424242424242"));
        agreementApprovePage.clickApprovePayBTN();
        agreementApprovePage.goToPreviousPage();

        //        leftMenuPanel.clickOnMenu("Client Management");
//        page = leftMenuPanel.clickOnMenu("Client Quotes");
//        Assert.assertTrue(page instanceof ClientQuotesPage);
//        clientQuotesPage = (ClientQuotesPage) page;
//        clientQuotesPage.searchUser(name);
//        clientQuotesPage.deleteUser(name);}
    }
}