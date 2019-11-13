package com.cyberiansoft.test.vnextbo.testcases.users;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.screens.users.VNextBOUsersAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOAddNewUserDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOAddNewUserDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOUsersAdvancedSearchValidations;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOUsersPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOAddNewUserAndSearchTests extends BaseTestCase {

    String newUserEmail = "autoNewUsertest.mail.cyberiansoft@getnada.com";
    String newUserFirstName = "autoNewUserTestFirstName";
    String newUserLastName = "autoNewUserTestLastName";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getUsersAddingNewUserAndSearchTD();
        VNextBOLeftMenuInteractions.selectUsersMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyNewUserCantBeAddedWithoutRequiredFields(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickAddNewUserButton();
        VNextBOAddNewUserDialogSteps.clickSaveButton();
        VNextBOAddNewUserDialogValidations.verifyErrorMessageIsDisplayed("First Name is required");
        VNextBOAddNewUserDialogValidations.verifyErrorMessageIsDisplayed("Last Name is required");
        VNextBOAddNewUserDialogValidations.verifyErrorMessageIsDisplayed("Email is required");
        VNextBOAddNewUserDialogSteps.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanAddNewUser(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(newUserEmail);
        VNextBOUsersPageSteps.clickAddNewUserButton();
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        VNextBOAddNewUserDialogSteps.createNewUser(newUserFirstName, newUserLastName,
                newUserEmail,"111111116", true);
        VNextBOAddNewUserDialogValidations.verifyDialogIsClosed(vNexBOAddNewUserDialog);
        VNextBOUsersPageSteps.searchUserByEmail(newUserEmail);
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "New user hasn't been found");
        Assert.assertTrue(VNextBOUsersPageValidations.verifyUserIsPresentOnCurrentPageByText(newUserEmail));
        Assert.assertTrue(VNextBOUsersPageValidations.verifyRedTriangleWarningIconIsDisplayed(),
                "Red triangle warning icon hasn't been displayed.");
        VNextBOUsersPageValidations.verifyReSendButtonIsDisplayed();
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("REGISTRATION");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "click here", "http", "\">").get(0);
        Assert.assertTrue(resetPasswordUrl.contains("confirm?invitation"), "User hasn't got link to complete registration");
        nada.deleteAllMessages();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyRegistrationMailCanBeResend(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(newUserEmail);
        VNextBOUsersPageSteps.searchUserByEmail(newUserEmail);
        VNextBOUsersPageSteps.resendConfirmationEmail();
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("REGISTRATION");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "click here", "http", "\">").get(0);
        Assert.assertTrue(resetPasswordUrl.contains("confirm?invitation"), "User hasn't got link to complete registration");
        nada.deleteAllMessages();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyNewUserCantBeAddedWithRegisteredEmail(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickAddNewUserButton();
        VNextBOAddNewUserDialogSteps.setUserFirstName(newUserFirstName);
        VNextBOAddNewUserDialogSteps.setUserLastName(newUserLastName);
        VNextBOAddNewUserDialogSteps.setUserEmail(newUserEmail);
        VNextBOAddNewUserDialogSteps.clickSaveButton();
        VNextBOAddNewUserDialogValidations.verifyErrorMessageIsDisplayed(
                "Email address " + newUserEmail + " has been already registered. Please try another.");
        VNextBOAddNewUserDialogSteps.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanCloseAddNewUserDialog(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickAddNewUserButton();
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        VNextBOAddNewUserDialogSteps.closeDialog();
        VNextBOAddNewUserDialogValidations.verifyDialogIsClosed(vNexBOAddNewUserDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanCloseAdvancedSearchForm(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchForm vNextBOUsersAdvancedSearchForm = new VNextBOUsersAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.clickCloseButton();
        VNextBOUsersAdvancedSearchValidations.verifyAdvancedSearchFormIsNotDisplayed(vNextBOUsersAdvancedSearchForm);
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchByEmail(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchForm vNextBOUsersAdvancedSearchForm = new VNextBOUsersAdvancedSearchForm();
        VNextBOUsersAdvancedSearchValidations.verifyAdvancedSearchFormIsDisplayed();
        VNextBOUsersAdvancedSearchValidations.verifyEmailFieldIsDisplayed();
        VNextBOUsersAdvancedSearchValidations.verifyPhoneFieldIsDisplayed();
        VNextBOUsersAdvancedSearchValidations.verifySearchButtonIsDisplayed();
        VNextBOUsersAdvancedSearchValidations.verifyCloseButtonIsDisplayed();
        VNextBOUsersAdvancedSearchSteps.setEmailField(newUserEmail);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        VNextBOUsersAdvancedSearchValidations.verifyAdvancedSearchFormIsNotDisplayed(vNextBOUsersAdvancedSearchForm);
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "User hasn't been found by email");
        Assert.assertTrue(VNextBOUsersPageValidations.verifyUserIsPresentOnCurrentPageByText(newUserEmail));
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Email: " + newUserEmail);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanSearchByPhone(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.searchUserByPhone("1111111116");
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "User hasn't been found by phone");
        Assert.assertTrue(VNextBOUsersPageValidations.verifyUserIsPresentOnCurrentPageByText("1111111116"));
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Phone: 1111111116");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanClearAdvancedSearchFilter(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.searchUserByEmail(newUserEmail);
        VNextBOSearchPanelSteps.clearSearchFilter();
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 10,
                "Search filter hasn't been cleared, all records haven't been displayed");
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSearchByName(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(newUserFirstName);
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "User hasn't been found");
        Assert.assertTrue(VNextBOUsersPageValidations.verifyUserIsPresentOnCurrentPageByText(newUserFirstName));
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Name: " + newUserFirstName);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanClearSearchByNameFilter(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(newUserFirstName);
        VNextBOSearchPanelSteps.clearSearchFilter();
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 10,
                "Search filter hasn't been cleared, all records haven't been displayed");
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserSeesMessageWhenSearchResultIsNegative(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("abracadabra");
        VNextBOUsersPageValidations.verifyUsersNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanEditCreatedUserData(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOUsersPageSteps.searchUserByEmail(newUserEmail);
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        VNextBOUsersPageSteps.openUserDataForEdit();
        VNextBOAddNewUserDialogValidations.verifyEmailFieldIsDisabled();
        VNextBOAddNewUserDialogSteps.editUserData("autoNewUserEditedFirstName",
                "autoNewUserEditedLastName", "222222227", false);
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText("autoNewUserEditedFirstName autoNewUserEditedLastName");
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "Edited user hasn't been found");
        Assert.assertTrue(VNextBOUsersPageValidations.verifyUserIsPresentOnCurrentPageByText("222222227"));
        Assert.assertTrue(VNextBOUsersPageValidations.verifyRedTriangleWarningIconIsNotDisplayed(vNexBOUsersWebPage),
                "Red triangle warning icon has been displayed.");
        Assert.assertTrue(VNextBOUsersPageValidations.verifyReSendButtonIsNotDisplayed(vNexBOUsersWebPage),
                "Re-send button has been displayed.");
    }
}