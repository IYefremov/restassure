package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientServicesPageSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientDetailsValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientServicesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOClientsClientServicesTests extends BaseTestCase {

    private static final String PRECONDITION_WHOLESALE_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsPreconditionsWholesaleClient.json";
    VNextBOClientsData baseWholesaleClient;
    final String servicePackageName = "01alex_Pack_ExteriorInspection";
    final String serviceName = "AMoneyFlatFee_VacuumCleaner";

    @BeforeClass
    public void settingUp() throws Exception {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsServicesTD();
        VNextBOLeftMenuInteractions.selectClientsMenu();
        baseWholesaleClient = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITION_WHOLESALE_FILE), VNextBOClientsData.class);
        baseWholesaleClient.getEmployee().setCompanyName(baseWholesaleClient.getEmployee().getCompanyName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.createNewClient(baseWholesaleClient, true);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientDetailsViewAccordionSteps.clickServicesTab("true");
        VNextBOClientServicesPageSteps.setServicePackage(servicePackageName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSeeServicesDetailsAndSelectServicePackage(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelValidations.verifySearchFieldIsDisplayed();
        VNextBOPageSwitcherValidations.verifyPageNavigationElementsAreDisplayed();
        VNextBOClientServicesPageValidations.verifyServicesTableIsDisplayed();
        VNextBOClientServicesPageValidations.verifyAllColumnsAreDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanGoToPreviousPage(String rowID, String description, JSONObject testData) {

        VNextBOClientServicesPageSteps.clickClientServicesBackButton();
        VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanSearchServices(String rowID, String description, JSONObject testData) {

        VNextBOClientDetailsViewAccordionSteps.clickServicesTab("true");
        VNextBOClientServicesPageSteps.setServicePackage(servicePackageName);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
        VNextBOClientServicesPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientServicesPageValidations.verifySearchResultIsCorrectForColumnWithText("Service", "AMoneyFlatFee_VacuumCleaner");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("jkfhajkklaspasdklja");
        VNextBOClientServicesPageValidations.verifyServicesNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOClientServicesPageValidations.verifyServicesTableIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanSwitchBetweenPages(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("2");
        VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(),
                "Bottom Last page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(),
                "Top Last page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(), "Top First page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(), "Bottom First page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("3");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanUsePageFilter(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("20");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOClientServicesPageValidations.verifyCorrectRecordsAmountIsDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("50");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOClientServicesPageValidations.verifyCorrectRecordsAmountIsDisplayed(50);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanChangeRequiredOfClientServices(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
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

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("Yes");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("Yes");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanChangeRequiredToNo(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("No");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("No");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanChangeRequiredToUseDefault(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("(use default)");
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("(use default)");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanChangeRequiredOfClientServicesAndSaveIt(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
        VNextBOClientServicesPageSteps.changeFirstLineRequiredFieldValue("Yes");
        VNextBOClientServicesPageSteps.clickClientServicesBackButton();
        VNextBOClientDetailsViewAccordionSteps.clickServicesTab("true");
        VNextBOClientServicesPageSteps.setServicePackage(servicePackageName);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
        VNextBOClientServicesPageValidations.verifyFirstLineRequiredDropDownFieldContainsCorrectValue("Yes");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanChangeTechnicianEffectiveDateEffectivePriceOfClientServices(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(serviceName);
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