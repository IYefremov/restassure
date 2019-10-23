package com.cyberiansoft.test.vnextbo.testcases.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.inspections.VNextBOInspectionsAdvancedSearchValidations;
import com.cyberiansoft.test.vnextbo.verifications.inspections.VNextBOInspectionsPageValidations;
import com.cyberiansoft.test.vnextbo.verifications.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsGeneralTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/inspections/VNextBOInspectionsGeneralData.json";
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

        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectInspectionsMenu();
    }

    @AfterClass
    public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyInspectionPageCanBeOpened(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageValidations.isTermsAndConditionsLinkDisplayed();
        VNextBOInspectionsPageValidations.isPrivacyPolicyLinkDisplayed();
        VNextBOInspectionsPageValidations.isInspectionsListDisplayed();
        VNextBOInspectionsPageValidations.isSearchFieldDisplayed();
        VNextBOInspectionsPageValidations.isInspectionDetailsPanelDisplayed();
        VNextBOInspectionsPageValidations.isIntercomButtonDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosedOkBtn(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOInspectionsPageValidations.isSearchFieldDisplayed();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosedCloseBtn(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosedOkBtn(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosedCloseBtn(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog(webdriver);
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyIntercomMessengerCanBeOpenedClosed(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openIntercomMessenger();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOInspectionsPageValidations.isIntercomMessengerOpened();
        VNextBOInspectionsPageSteps.closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getAllAdvancedSearchFieldsLabels(),
                expectedAdvancedSearchFields);
        VNextBOInspectionsAdvancedSearchValidations.isSearchButtonDisplayed();
    }
}