package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsDialogSteps;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsSearchSteps;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsTableSteps;
import com.cyberiansoft.test.bo.steps.company.teams.BOTeamsDialogSteps;
import com.cyberiansoft.test.bo.steps.company.teams.BOTeamsSearchSteps;
import com.cyberiansoft.test.bo.steps.company.teams.BOTeamsTableSteps;
import com.cyberiansoft.test.bo.steps.menu.BOMenuSteps;
import com.cyberiansoft.test.bo.steps.search.BOSearchSteps;
import com.cyberiansoft.test.bo.steps.superuser.subscriptions.BOSubscriptionsPageSteps;
import com.cyberiansoft.test.bo.validations.company.teams.BOTeamsDialogValidations;
import com.cyberiansoft.test.bo.validations.company.teams.BOTeamsTableValidations;
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
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOPMWithTurnedOffPunchOutAddOnTestCases extends BaseTestCase {

    final String addOn = "Punch Out Process";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMWithTurnedOffPunchOutAddOnTD();
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectAddOnsMenu();
        VNextBOAddOnsPageSteps.turnOffAddOnByName(addOn);
        VNextBOAddOnsPageSteps.checkAddOnIsChangedToStatus(addOn, IntegrationStatus.OFF);
    }

    @AfterClass
    public void reset() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectAddOnsMenu();
        VNextBOAddOnsPageSteps.turnOnAddOnByName(addOn);
    }

    @BeforeMethod
    public void goToPage() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyProviderDropDownIsEnabledIfGetQuotesButtonIsNotAvailable(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelSteps.waitForGetQuotesButtonToBeDisplayed(false);
        VNextBOPartsDetailsPanelValidations.verifyGetQuotesButtonIsDisplayed(false);
        final List<String> providerDropDownOptions = VNextBOPartsDetailsPanelSteps.getProviderDropDownOptions();
        Assert.assertTrue(providerDropDownOptions.size() > 0, "The providers dropdown hasn't been opened");
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setFullModeForSubscriptions(data.getSubscriptions());
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAllGenericPunchoutPartProvidersWithNATeamTypeHaveTheSameLocationsOrAreaAsSelectedRO(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);
        final String area = data.getAreaLocationData().getArea();

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        final String customer = VNextBOPartsOrdersListPanelSteps.getCustomerNamesListOptions().get(0);
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.SUPER_USER, SubMenu.SUBSCRIBE);
        BOSubscriptionsPageSteps.setNoneModeForSubscriptions(data.getSubscriptions());
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
        final List<String> providerOptions = VNextBOPartsDetailsPanelSteps.getProviderDropDownOptions();
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.COMPANY, SubMenu.TEAMS);
        BOSearchSteps.expandSearchTab();
        providerOptions.forEach(team -> {
            BOTeamsSearchSteps.setTeamLocation(team);
            BOSearchSteps.search();
            if (BOTeamsTableSteps.getValueByColumnName("Location").isEmpty()) {
                BOTeamsTableValidations.verifyEitherLocationOrAreaIsSet(area);
            }
            BOTeamsTableSteps.openEditDialog(team);
            BOTeamsDialogValidations.verifyProviderValue(data.getAreaLocationData().getProvider());
            BOTeamsDialogSteps.closeDialog();
        });
    }
}
