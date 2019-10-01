package com.cyberiansoft.test.vnextbo.testcases.VNextBOInspectionsTests;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsGeneralTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOInspectionsGeneralData.json";
    private VNextBOInspectionsWebPage inspectionsWebPage;
    private VNextBOLoginScreenWebPage loginPage;
    private List<String> expectedAdvancedSearchFields =
            Arrays.asList("Customer", "PO#", "RO#", "Stock#", "VIN",
                    "Status", "Inspection#", "Timeframe");

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
        String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = new VNextBOLoginScreenWebPage(webdriver);
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectInspectionsMenu();
        inspectionsWebPage = new VNextBOInspectionsWebPage(webdriver);
    }

    @AfterClass
    public void BackOfficeLogout() {
        try {
            VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel(webdriver);
            if (headerPanel.logOutLinkExists()) {
                headerPanel.userLogout();
            }
        } catch (RuntimeException ignored) {}

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyInspectionPageCanBeOpened(String rowID, String description, JSONObject testData) {

        Assert.assertTrue(inspectionsWebPage.isTermsAndConditionsLinkDisplayed(), "Terms and Conditions link hasn't been displayed");
        Assert.assertTrue(inspectionsWebPage.isPrivacyPolicyLinkDisplayed(), "Privacy Policy link hasn't been displayed");
        Assert.assertTrue(inspectionsWebPage.isInspectionsListDisplayed(), "Inspection list hasn't been displayed");
        Assert.assertTrue(inspectionsWebPage.isSearchFieldDisplayed(), "Search field hasn't been displayed");
        Assert.assertTrue(inspectionsWebPage.isInspectionDetailsPanelDisplayed(), "Inspection details panel hasn't been displayed");
        Assert.assertTrue(inspectionsWebPage.isIntercomButtonDisplayed(), "Intercom button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosedOkBtn(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogDisplayed(),
                "Terms and Conditions dialog hasn't been displayed");
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isOkButtonDisplayed(),
                "OK button hasn't been displayed");
        Assert.assertEquals(vNextBOTermsAndConditionsDialog.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        vNextBOTermsAndConditionsDialog.clickOkButton();
        Assert.assertTrue(inspectionsWebPage.isSearchFieldDisplayed(), "Search field hasn't been displayed");
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogClosed(),
                "Terms and Conditions dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosedCloseBtn(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogDisplayed(),
                "Terms and Conditions dialog hasn't been displayed");
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isOkButtonDisplayed(),
                "OK button hasn't been displayed");
        Assert.assertEquals(vNextBOTermsAndConditionsDialog.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        vNextBOTermsAndConditionsDialog.clickCloseButton();
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogClosed(),
                "Terms and Conditions dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosedOkBtn(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogDisplayed(),
                "Privacy Policy dialog hasn't been displayed");
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isOkButtonDisplayed(),
                "OK button hasn't been displayed");
        Assert.assertEquals(vNextBOTermsAndConditionsDialog.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        vNextBOTermsAndConditionsDialog.clickOkButton();
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogClosed(),
                "Privacy Policy dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosedCloseBtn(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogDisplayed(),
                "Privacy Policy dialog hasn't been displayed");
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isOkButtonDisplayed(),
                "OK button hasn't been displayed");
        Assert.assertEquals(vNextBOTermsAndConditionsDialog.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        vNextBOTermsAndConditionsDialog.clickCloseButton();
        Assert.assertTrue(vNextBOTermsAndConditionsDialog.isDialogClosed(),
                "Privacy Policy dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyIntercomMessengerCanBeOpenedClosed(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.openIntercomMessenger();
        Assert.assertTrue(inspectionsWebPage.isIntercomMessengerOpened(),
                "Intercom messenger hasn't been opened");
        inspectionsWebPage.closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.openAdvancedSearchForm();
        VNextBOInspectionAdvancedSearchForm vNextBOInspectionAdvancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(webdriver);
        Assert.assertTrue(vNextBOInspectionAdvancedSearchForm.isAdvancedSearchFormDisplayed(),
                "Advanced search form hasn't been opened");
        List<String> actualResult = vNextBOInspectionAdvancedSearchForm.getAllAdvancedSearchFieldsLabels();
        Assert.assertEquals(actualResult, expectedAdvancedSearchFields);
        Assert.assertTrue(vNextBOInspectionAdvancedSearchForm.isSearchButtonDisplayed(),
                "Advanced Search button hasn't been displayed");
    }
}
