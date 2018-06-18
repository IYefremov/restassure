package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.InHouseUserData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.inhouse.pageObject.AgreementApprovePage;
import com.cyberiansoft.test.inhouse.pageObject.ClientQuotesDetailPage;
import com.cyberiansoft.test.inhouse.pageObject.ClientQuotesPage;
import com.cyberiansoft.test.inhouse.pageObject.LeftMenuPanel;
import com.cyberiansoft.test.inhouse.utils.MailChecker;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TeamPortalUserTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/inhouse/data/InHouseUser.json";
    private MailChecker mailChecker;

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
        mailChecker = new MailChecker();
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
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatus(
                data.getNewAgreement(), data.getNoAgreement(),data.getNoAgreement(),data.getNoAgreement()));

        clientQuotesDetailPage
                .clickDiscountButton()
                .selectDiscount(data.getDiscount());
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice(data.getPrice()));
        clientQuotesDetailPage.selectSetupFeeForAllClients();
//        clientQuotesDetailPage.clickAddClientSupportItem("testFeature3 test mike");
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth(data.getPrice()));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee(clientQuotesDetailPage.getPricePerMonth()));
        clientQuotesDetailPage
                .clickFinalizeAgreementButton()
                .sendNotification();
        Assert.assertTrue(mailChecker.checkEmails(data.getEmailTitle()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanVerifyDatesWhenOpenMailWithLink(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        AgreementApprovePage agreementApprovePage = PageFactory.initElements(webdriver, AgreementApprovePage.class);

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
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatus(
                data.getNewAgreement(), data.getNoAgreement(),data.getNoAgreement(),data.getNoAgreement()));

        clientQuotesDetailPage
                .clickDiscountButton()
                .selectDiscount(data.getDiscount());
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice(data.getPrice()));
        clientQuotesDetailPage.selectSetupFeeForAllClients();
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth(data.getPrice()));
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee(clientQuotesDetailPage.getPricePerMonth()));
        clientQuotesDetailPage
                .clickFinalizeAgreementButton()
                .sendNotification();
        Assert.assertTrue(mailChecker.checkEmails(data.getEmailTitle()));
        String userMailContentFromSpam = mailChecker.getSpamMailMessage(data.getEmailTitle(), data.getBodySearchText());
        String clientAgreementLink = mailChecker.getAgreementLink(userMailContentFromSpam, data.getPartialLinkTextToAgreementPage());
        agreementApprovePage.openAgreementLinkFromGmail(clientAgreementLink);
        Assert.assertTrue(agreementApprovePage.isAgreementPageOpened());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanPayAgreementFromMailLink(String rowID, String description, JSONObject testData) {
        InHouseUserData data = JSonDataParser.getTestDataFromJson(testData, InHouseUserData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        AgreementApprovePage agreementApprovePage = PageFactory.initElements(webdriver, AgreementApprovePage.class);

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
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatus(
                data.getNewAgreement(), data.getNoAgreement(),data.getNoAgreement(),data.getNoAgreement()));

        clientQuotesDetailPage
                .clickDiscountButton()
                .selectDiscount(data.getDiscount());
        Assert.assertTrue(clientQuotesDetailPage.checkNewPrice(data.getPrice()));
        clientQuotesDetailPage.selectSetupFeeForAllClients();
        Assert.assertTrue(clientQuotesDetailPage.checkPricePerMonth(data.getPrice()));
        String pricePerMonth = clientQuotesDetailPage.getPricePerMonth();
        Assert.assertTrue(clientQuotesDetailPage.checkSetupFee(pricePerMonth));
        clientQuotesDetailPage
                .setBillingStartsFromToday()
                .clickFinalizeAgreementButton()
                .sendNotification();
        Assert.assertTrue(mailChecker.checkEmails(data.getEmailTitle()));
        String userMailContentFromSpam = mailChecker.getSpamMailMessage(data.getEmailTitle(), data.getBodySearchText());
        String clientAgreementLink = mailChecker.getAgreementLink(userMailContentFromSpam, data.getPartialLinkTextToAgreementPage());
        agreementApprovePage.openAgreementLinkFromGmail(clientAgreementLink);
        Assert.assertTrue(agreementApprovePage.isAgreementPageOpened());
        agreementApprovePage.fillClientInfo(data.getClientFirstName(), data.getClientLastName(), data.getClientCompanyName());
        Assert.assertTrue(agreementApprovePage.checkTermsAndConditions());
        agreementApprovePage
                .clickAgreeWithTermsAndConditionsButton()
                .clickAcceptAgreementButton()
                .fillSetupFeePayment(data.getCardNumber(), data.getMonthExpiration(), data.getYearExpiration(), data.getCvc())
                .clickPayButton();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage(pricePerMonth, data.getCardNumber()));
        agreementApprovePage
                .clickCancelPayButton()
                .clickPayButton();
        Assert.assertTrue(agreementApprovePage.checkPayConfirmationMessage(pricePerMonth, data.getCardNumber()));
        agreementApprovePage
                .clickApprovePayButton()
                .clickSuccessfulPaymentOkButton()
                .goToPreviousPage();
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatus(data.getSignedAgreement()),
                "The Agreement status has not been changed to signed after payment");
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
        Assert.assertTrue(clientQuotesDetailPage.checkAgreementStatus("New","No","No","No"));
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