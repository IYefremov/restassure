package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsDialogSteps;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsSearchSteps;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsTableSteps;
import com.cyberiansoft.test.bo.steps.company.teams.BOTeamsSearchSteps;
import com.cyberiansoft.test.bo.steps.company.teams.BOTeamsTableSteps;
import com.cyberiansoft.test.bo.steps.menu.BOMenuSteps;
import com.cyberiansoft.test.bo.steps.search.BOSearchSteps;
import com.cyberiansoft.test.bo.steps.superuser.subscriptions.BOSubscriptionsPageSteps;
import com.cyberiansoft.test.bo.validations.teams.BOTeamsTableValidations;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.addons.IntegrationStatus;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.addOns.VNextBOAddOnsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsOrdersListPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOPMWithTurnedOnPunchOutAddOnTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMWithTurnedOnPunchOutAddOnTD();
        final String addOn = "Punch Out Process";
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectAddOnsMenu();
        VNextBOAddOnsPageSteps.turnOnAddOnByName(addOn);
        VNextBOAddOnsPageSteps.checkAddOnIsChangedToStatus(addOn, IntegrationStatus.ON);
    }

    @BeforeMethod
    public void goToPage() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyProviderDropDownIsDisabledIfGetQuotesButtonIsAvailable(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelSteps.waitForGetQuotesButtonToBeDisplayed(true);
        VNextBOPartsDetailsPanelValidations.verifyGetQuotesButtonIsDisplayed(true);
        final List<String> providerDropDownOptions = VNextBOPartsDetailsPanelSteps.getProviderDropDownOptions();
        Assert.assertTrue(providerDropDownOptions.isEmpty(), "The providers dropdown is displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAllGenericPunchoutPartProvidersWithTheSameRepairLocationsOrAreaAreDisplayed(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);
        final String area = data.getAreaLocationData().getArea();

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        final String customer = VNextBOPartsOrdersListPanelSteps.getCustomerNamesListOptions().get(0);
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        BOMenuSteps.open(Menu.COMPANY, SubMenu.CLIENTS);
        BOSearchSteps.expandSearchTab();
        BOClientsSearchSteps.setClientName(customer);
        BOSearchSteps.search();
        BOClientsTableSteps.openEditDialogForClient(customer);
        BOClientsDialogSteps.setDefaultArea(area);
        BOClientsDialogSteps.submit();
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelSteps.openPartsProvidersModalDialog();
        final List<String> providersList = VNextBOPartsProvidersDialogSteps.getOptionsList();
        VNextBOPartsProvidersDialogSteps.closePartsProvidersDialog();
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.COMPANY, SubMenu.TEAMS);
        BOSearchSteps.expandSearchTab();
        providersList.forEach(provider -> {
            BOTeamsSearchSteps.setTeamLocation(provider);
            BOSearchSteps.search();
            if (BOTeamsTableSteps.getValueByColumnName("Location").isEmpty()) {
                BOTeamsTableValidations.verifyEitherLocationOrAreaIsSet(area);
            }
        });
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyWarningMessageIsShownAfterClickingTheGetQuoteButton(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        final String customer = VNextBOPartsOrdersListPanelSteps.getCustomerNamesListOptions().get(0);
        VNextBOPartsDetailsPanelSteps.openPartsProvidersModalDialog();
        VNextBOPartsProvidersDialogValidations.verifyNotificationText(VNextBOAlertMessages.NO_PART_PROVIDER);
        VNextBOPartsProvidersDialogValidations.verifyProvidersListIsEmpty();
        VNextBOPartsProvidersDialogSteps.closePartsProvidersDialog();
    }
}
