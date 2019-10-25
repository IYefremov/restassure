package com.cyberiansoft.test.vnextbo.testcases.users;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.screens.users.VNextBOUsersAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.commonObjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOAddNewUserDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.commonObjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.verifications.users.VNextBOAddNewUserDialogValidations;
import com.cyberiansoft.test.vnextbo.verifications.users.VNextBOUsersAdvancedSearchValidations;
import com.cyberiansoft.test.vnextbo.verifications.users.VNextBOUsersPageValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOAddNewUserAndSearchTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/users/VNextBOAddNewUserAndSearchData.json";
    private VNextBOLoginScreenWebPage loginPage;
    String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
    String newUserEmail = "autoUsertest.mail.cyberiansoft@getnada.com";
    String newUserFirstName = "autoUserTestFirstName";
    String newUserLastName = "autoUserTestLastName";

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = DATA_FILE;
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());

        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectUsersMenu();
    }

    @AfterClass
    public void backOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyNewUserCantBeAddedWithoutRequiredFields(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickAddNewUserButton();
        VNextBOAddNewUserDialogSteps.clickSaveButton();
        VNextBOAddNewUserDialogValidations.isErrorMessageDisplayed("First Name is required");
        VNextBOAddNewUserDialogValidations.isErrorMessageDisplayed("Last Name is required");
        VNextBOAddNewUserDialogValidations.isErrorMessageDisplayed("Email is required");
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
        VNextBOAddNewUserDialogValidations.isDialogClosed(vNexBOAddNewUserDialog);
        VNextBOUsersPageSteps.searchUserByEmail(newUserEmail);
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "New user hasn't been found");
        Assert.assertTrue(VNextBOUsersPageValidations.isUserPresentOnCurrentPageByText(newUserEmail));
        Assert.assertTrue(VNextBOUsersPageValidations.isRedTriangleWarningIconDisplayed(),
                "Red triangle warning icon hasn't been displayed.");
        VNextBOUsersPageValidations.isReSendButtonDisplayed();
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
        VNextBOAddNewUserDialogValidations.isErrorMessageDisplayed(
                "Email address " + newUserEmail + " has been already registered. Please try another.");
        VNextBOAddNewUserDialogSteps.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanCloseAddNewUserDialog(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickAddNewUserButton();
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog();
        VNextBOAddNewUserDialogSteps.closeDialog();
        VNextBOAddNewUserDialogValidations.isDialogClosed(vNexBOAddNewUserDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanCloseAdvancedSearchForm(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchForm vNextBOUsersAdvancedSearchForm = new VNextBOUsersAdvancedSearchForm();
        VNextBOUsersAdvancedSearchSteps.clickCloseButton();
        VNextBOUsersAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(vNextBOUsersAdvancedSearchForm);
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchByEmail(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOUsersAdvancedSearchForm vNextBOUsersAdvancedSearchForm = new VNextBOUsersAdvancedSearchForm();
        VNextBOUsersAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
        VNextBOUsersAdvancedSearchValidations.isEmailFieldDisplayed();
        VNextBOUsersAdvancedSearchValidations.isPhoneFieldDisplayed();
        VNextBOUsersAdvancedSearchValidations.isSearchButtonDisplayed();
        VNextBOUsersAdvancedSearchValidations.isCloseButtonDisplayed();
        VNextBOUsersAdvancedSearchSteps.setEmailField(newUserEmail);
        VNextBOUsersAdvancedSearchSteps.clickSearchButton();
        VNextBOUsersAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(vNextBOUsersAdvancedSearchForm);
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "User hasn't been found by email");
        Assert.assertTrue(VNextBOUsersPageValidations.isUserPresentOnCurrentPageByText(newUserEmail));
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Email: " + newUserEmail);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanSearchByPhone(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.searchUserByPhone("1111111116");
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "User hasn't been found by phone");
        Assert.assertTrue(VNextBOUsersPageValidations.isUserPresentOnCurrentPageByText("1111111116"));
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Phone: 1111111116");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanClearAdvancedSearchFilter(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.searchUserByEmail(newUserEmail);
        VNextBOSearchPanelSteps.clearSearchFilter();
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 10,
                "Search filter hasn't been cleared, all records haven't been displayed");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSearchByName(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(newUserFirstName);
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "User hasn't been found");
        Assert.assertTrue(VNextBOUsersPageValidations.isUserPresentOnCurrentPageByText(newUserFirstName));
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Name: " + newUserFirstName);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanClearSearchByNameFilter(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(newUserFirstName);
        VNextBOSearchPanelSteps.clearSearchFilter();
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 10,
                "Search filter hasn't been cleared, all records haven't been displayed");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserSeesMessageWhenSearchResultIsNegative(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("abracadabra");
        VNextBOUsersPageValidations.isUsersNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanEditCreatedUserData(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOUsersPageSteps.searchUserByEmail(newUserEmail);
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        VNextBOUsersPageSteps.openUserDataForEdit();
        VNextBOAddNewUserDialogValidations.isEmailFieldDisabled();
        VNextBOAddNewUserDialogSteps.editUserData("autoUserEditedFirstName",
                "autoUseEditedLastName", "222222227", false);
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText("autoUserEditedFirstName autoUseEditedLastName");
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersTableRowsCount(), 1, "Edited user hasn't been found");
        Assert.assertTrue(VNextBOUsersPageValidations.isUserPresentOnCurrentPageByText("1222222227"));
        Assert.assertTrue(VNextBOUsersPageValidations.isRedTriangleWarningIconNotDisplayed(vNexBOUsersWebPage),
                "Red triangle warning icon has been displayed.");
        Assert.assertTrue(VNextBOUsersPageValidations.isReSendButtonNotDisplayed(vNexBOUsersWebPage),
                "Re-send button has been displayed.");
    }
}