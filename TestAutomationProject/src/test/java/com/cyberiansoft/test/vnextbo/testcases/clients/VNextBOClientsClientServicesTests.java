package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientServicesPageSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientDetailsValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientServicesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.commonObjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonObjects.VNextBOSearchPanelValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOClientsClientServicesTests extends BaseTestCase {

    private static final String PRECONDITION_WHOLESALE_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsPreconditionsWholesaleClient.json";
    String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
    VNextBOClientsData baseWholesaleClient;
    final String servicePackageName = "01alex_Pack_ExteriorInspection";
    final String serviceName = "AMoneyFlatFee_VacuumCleaner";

    @BeforeClass
    public void settingUp() throws Exception {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsServicesTD();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());

        VNextBOLoginSteps.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions.selectClientsMenu();

        baseWholesaleClient = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITION_WHOLESALE_FILE), VNextBOClientsData.class);
        baseWholesaleClient.getEmployee().setCompanyName(baseWholesaleClient.getEmployee().getCompanyName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.createNewClient(baseWholesaleClient, true);
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientDetailsViewAccordionSteps.clickServicesTab();
        VNextBOClientServicesPageSteps.setServicePackage(servicePackageName);
    }

    @Override
    @BeforeMethod
    public void login() {}

    @Override
    @AfterMethod
    public void logout() {}

    @AfterClass
    public void backOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSeeServicesDetailsAndSelectServicePackage(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelValidations.isSearchFieldDisplayed();
        VNextBOPageSwitcherValidations.arePageNavigationElementsDisplayed();
        VNextBOClientServicesPageValidations.isServicesTableDisplayed();
        VNextBOClientServicesPageValidations.verifyAllColumnsAreDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanGoToPreviousPage(String rowID, String description, JSONObject testData) {

        VNextBOClientServicesPageSteps.clickClientServicesBackButton();
        VNextBOClientDetailsValidations.isClientInfoPanelExpanded();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanSearchServices(String rowID, String description, JSONObject testData) {

        VNextBOClientDetailsViewAccordionSteps.clickServicesTab();
        VNextBOClientServicesPageSteps.setServicePackage(servicePackageName);
        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOClientServicesPageValidations.isSearchResultCorrectForColumnWithText("Service", "AMoneyFlatFee_VacuumCleaner");
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText("jkfhajkklaspasdklja");
        VNextBOClientServicesPageValidations.isServicesNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOClientServicesPageValidations.isServicesTableDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanSwitchBetweenPages(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("2");
        VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(),
                "Bottom Last page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(),
                "Top Last page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(), "Top First page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(), "Bottom First page button has been clickable.");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("3");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanUsePageFilter(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("20");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOClientServicesPageValidations.isCorrectRecordsAmountDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("50");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOClientServicesPageValidations.isCorrectRecordsAmountDisplayed(50);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanChangeRequiredOfClientServices(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("Yes");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("Yes");
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("No");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("No");
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("(use default)");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("(use default)");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanChangeRequiredToYes(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("Yes");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("Yes");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanChangeRequiredToNo(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("No");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("No");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanChangeRequiredToUseDefault(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("(use default)");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("(use default)");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanChangeRequiredOfClientServicesAndSaveIt(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("Yes");
        VNextBOClientServicesPageSteps.clickClientServicesBackButton();
        VNextBOClientDetailsViewAccordionSteps.clickServicesTab();
        VNextBOClientServicesPageSteps.setServicePackage(servicePackageName);
        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("Yes");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanChangeTechnicianEffectiveDateEffectivePriceOfClientServices(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineEffectiveDate();
        VNextBOClientServicesPageValidations.verifyFirstLineEffectiveDateFieldContainsCorrectValue();
        VNextBOClientServicesPageSteps.changeFirstLineEffectivePrice("234");
        VNextBOClientServicesPageValidations.verifyFirstLineEffectivePriceFieldContainsCorrectValue("234");
        VNextBOClientServicesPageSteps.clearFirstLineEffectiveDate();
        VNextBOClientServicesPageSteps.changeFirstLineTechnicianFieldValue("Custom Technician");
        VNextBOClientServicesPageValidations.verifyFirstLineTechnicianDropDownFieldContainsCorrectValue("Custom Technician");
        VNextBOClientServicesPageSteps.changeFirstLineTechnicianFieldValue("(None)");
    }
}