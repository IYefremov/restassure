package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.InHouseUserData;
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
                .clickClientQuotesSubmenu();
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));
        clientQuotesPage.deleteUser(data.getName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanEditClientInformation(String rowID, String description, JSONObject testData)  {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));
        clientQuotesPage.editClient(data.getName());
        clientQuotesPage.clearAndSetNewClientName(data.getNewName());
        clientQuotesPage.clickUpdateClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getNewName()));
        clientQuotesPage.deleteUser(data.getNewName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddAgreement(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));
        clientQuotesPage.clickAddAgreementBTN(data.getName());
        clientQuotesPage.setAgreement(data.getFirstAgreement(),data.getTeam());
        clientQuotesPage = leftMenuPanel.clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.expandAgreementList(data.getName());
        clientQuotesPage.clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        clientQuotesPage.deleteUser(data.getName());

    }

    //todo fails S. Zakaulov
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class")
    public void testUserCanSelectServiceInAgreement(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));
        clientQuotesPage.clickAddAgreementBTN(data.getName());
        clientQuotesPage.setAgreement(data.getFirstAgreement(), data.getTeam());

        clientQuotesPage = leftMenuPanel.clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.expandAgreementList(data.getName());
        clientQuotesPage.clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage
                .clickSetupAgreementBTN(data.getSecondAgreement());
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatuses("New","No","No","No"));
        clientQuotesDetailPage.clickDiscountBTN();
        //todo the discount selection options are absent!
        clientQuotesDetailPage.selectDiscount("1 min comm.-$150.10 per m.");
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice("$150.10"));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1578.00"));
        clientQuotesDetailPage.clickAddClientSupportItem("testFeature2_1 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee("$1776.00"));
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth("$165.10"));

        clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.deleteUser(data.getName());

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanSendNotifications(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));
        clientQuotesPage.clickAddAgreementBTN(data.getName());
        clientQuotesPage.setAgreement(data.getFirstAgreement(),data.getTeam());
        clientQuotesPage = leftMenuPanel.clickClientQuotesSubmenu();

        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.expandAgreementList(data.getName());
        clientQuotesPage.clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementBTN(data.getSecondAgreement());
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
        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.deleteUser(data.getName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanVerifyDatesWhenOpenMailWithLink(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));
        clientQuotesPage.clickAddAgreementBTN(data.getName());
        clientQuotesPage.setAgreement(data.getFirstAgreement(),data.getTeam());
        clientQuotesPage = leftMenuPanel.clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.expandAgreementList(data.getName());
        clientQuotesPage.clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementBTN(data.getSecondAgreement());
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

        leftMenuPanel.clickClientManagement().clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.deleteUser(data.getName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanPayAgreementFromMailLink(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel.clickClientManagement().clickClientQuotesSubmenu();
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));
        clientQuotesPage.clickAddAgreementBTN(data.getName());
        clientQuotesPage.setAgreement(data.getFirstAgreement(),data.getTeam());
        clientQuotesPage = leftMenuPanel.clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.expandAgreementList(data.getName());
        clientQuotesPage.clickEditAgreement(data.getFirstAgreement());
        Assert.assertFalse(clientQuotesPage.abilityToChangeAgreementEdition(data.getTeam()));
        Assert.assertTrue(clientQuotesPage.abilityToChangeAgreementName(data.getSecondAgreement()));
        clientQuotesPage.updateAgreement();
        Assert.assertTrue(clientQuotesPage.checkAgreementByName(data.getSecondAgreement()));
        ClientQuotesDetailPage clientQuotesDetailPage = clientQuotesPage.clickSetupAgreementBTN(data.getSecondAgreement());
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
        AgreementApprovePage agreementApprovePage = (AgreementApprovePage) clientQuotesDetailPage.goToAgreementApprovementPageFromEmail(link);
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

        leftMenuPanel.clickClientManagement().clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.deleteUser(data.getName());
    }
}