package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.InHouseUserData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.inhouse.pageObject.AgreementApprovePage;
import com.cyberiansoft.test.inhouse.pageObject.ClientQuotesDetailPage;
import com.cyberiansoft.test.inhouse.pageObject.ClientQuotesPage;
import com.cyberiansoft.test.inhouse.pageObject.LeftMenuPanel;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TeamPortalUserTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/inhouse/data/InHouseUser.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddNewClient(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName())
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanEditClientInformation(String rowID, String description, JSONObject testData)  {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName()).clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()));
        clientQuotesPage.editClient(data.getName())
                .clearAndSetNewClientName(data.getNewName())
                .clickUpdateClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getNewName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddAgreement(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName()).clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()));
        clientQuotesPage
                .clickAddAgreementBTN(data.getName())
                .setAgreement(data.getFirstAgreement(),data.getTeam());

        leftMenuPanel
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .expandAgreementList(data.getName())
                .clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.verifyAgreementEditionCannotBeChanged(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.isAgreementNameChangeable(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanSendNotifications(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName())
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()));
        clientQuotesPage
                .clickAddAgreementBTN(data.getName())
                .setAgreement(data.getFirstAgreement(), data.getTeam());

        leftMenuPanel
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .expandAgreementList(data.getName())
                .clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.verifyAgreementEditionCannotBeChanged(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.isAgreementNameChangeable(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementButton(data.getSecondAgreement());
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses(
                data.getNewAgreement(), data.getNoAgreement(),data.getNoAgreement(),data.getNoAgreement()));

        clientQuotesDetailPage
                .clickDiscountButton()
                .selectDiscount(data.getDiscount());
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice(data.getPrice()));
        clientQuotesDetailPage.selectSetupFeeForAllClients();
//        clientQuotesDetailPage.clickAddClientSupportItem("testFeature3 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$" + clientQuotesDetailPage.calculatePricePerMonth()));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth(data.getPrice()));
        clientQuotesDetailPage
                .clickFinalizeAgreementButton()
                .sendNotification();
        Assert.assertTrue(clientQuotesDetailPage.checkEmails(data.getEmailTitle()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanVerifyDatesWhenOpenMailWithLink(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName()).clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()));
        clientQuotesPage
                .clickAddAgreementBTN(data.getName())
                .setAgreement(data.getFirstAgreement(), data.getTeam());

        leftMenuPanel
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .expandAgreementList(data.getName())
                .clickEditAgreement(data.getFirstAgreement());

        Assert.assertFalse(clientQuotesPage.verifyAgreementEditionCannotBeChanged(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.isAgreementNameChangeable(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementButton(data.getSecondAgreement());
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage
                .clickDiscountButton()
                .selectDiscount("1 min comm.-$150.10 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));
        clientQuotesDetailPage
                .clickFinalizeAgreementButton()
                .sendNotification();
        //TODO when dates will be shown correct
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanPayAgreementFromMailLink(String rowID, String description, JSONObject testData) throws IOException {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName()).clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()));
        clientQuotesPage
                .clickAddAgreementBTN(data.getName())
                .setAgreement(data.getFirstAgreement(),data.getTeam());

        leftMenuPanel
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .expandAgreementList(data.getName())
                .clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.verifyAgreementEditionCannotBeChanged(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.isAgreementNameChangeable(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementButton(data.getSecondAgreement());
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage
                .clickDiscountButton()
                .selectDiscount("12 min comm.-$2 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));
        clientQuotesDetailPage
                .clickFinalizeAgreementButton()
                .sendNotification();
        Assert.assertTrue(clientQuotesDetailPage.checkEmails("Agreement"));
        String link = clientQuotesDetailPage.getAgreementApproveLink();
        AgreementApprovePage agreementApprovePage = (AgreementApprovePage) clientQuotesDetailPage
                .goToAgreementApprovementPageFromEmail(link);
        agreementApprovePage.fillClientInfo("Anastasia","Maksimova","automationCompany");
        Assert.assertTrue(agreementApprovePage.checkTermsAndConditions());
        agreementApprovePage
                .clickAgreeWithTermsAndConditionsBTN()
                .clickAcceptAgreementBTN()
                .fillFeesPayment("4242424242424242","10","2026","123")
                .clickPayBTN();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage("$1,776.00","4242424242424242"));
        agreementApprovePage
                .clickCancelPayBTN()
                .clickPayBTN();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage("$1,776.00","4242424242424242"));
        agreementApprovePage
                .clickApprovePayBTN()
                .goToPreviousPage();
    }


    //todo fails S. Zakaulov
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class")
    public void testUserCanSelectServiceInAgreement(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName())
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()));
        clientQuotesPage.clickAddAgreementBTN(data.getName());
        clientQuotesPage.setAgreement(data.getFirstAgreement(), data.getTeam());

        clientQuotesPage = leftMenuPanel.clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.expandAgreementList(data.getName());
        clientQuotesPage.clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.verifyAgreementEditionCannotBeChanged(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.isAgreementNameChangeable(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage
                .clickSetupAgreementButton(data.getSecondAgreement());
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage.clickDiscountButton();
        //todo the discount selection options are absent!
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.10 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));
    }
}