package com.cyberiansoft.test.vnextbo.testcases.servicerequests;

import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsDialogSteps;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsSearchSteps;
import com.cyberiansoft.test.bo.steps.company.clients.BOClientsTableSteps;
import com.cyberiansoft.test.bo.steps.menu.BOMenuSteps;
import com.cyberiansoft.test.bo.steps.search.BOSearchSteps;
import com.cyberiansoft.test.bo.validations.company.clients.BOClientsTableValidations;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOReactSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.VNextBOSRTableSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOReactSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.VNextBOSRTableValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOSRSearchTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getSRSearchTD();
    }

    @BeforeMethod
    public void goToPage() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectServiceRequestsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByFirstCustomerName(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.searchByText(data.getSearchData().getCustomer());
        VNextBOSRTableValidations.verifyNotFoundNotificationIsDisplayed();
        VNextBOReactSearchPanelValidations.verifyClearSearchIconIsDisplayed();
        VNextBOReactSearchPanelSteps.clearSearchFilter();
        VNextBOReactSearchPanelValidations.verifySearchInputFieldIsEmpty();
        VNextBOReactSearchPanelValidations.verifyDefaultFilterInfoTextIsDisplayed();
        VNextBOReactSearchPanelSteps.searchByText(data.getSearchData().getHasThisText());
        final List<String> customerNameFields = VNextBOSRTableSteps.getUniqueCustomerNameFields();
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.COMPANY, SubMenu.CLIENTS);
        BOSearchSteps.expandSearchTab();
        customerNameFields.forEach(customer -> {
            BOClientsSearchSteps.setClientName(customer);
            BOSearchSteps.search();
            BOClientsTableSteps.openEditDialogForClient(customer);
            BOClientsTableValidations.verifyTheClientsFirstName(data.getSearchData().getHasThisText());
            BOClientsDialogSteps.cancel();
        });
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByLastCustomerName(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.searchByText(data.getSearchData().getHasThisText());
        final List<String> customerNameFields = VNextBOSRTableSteps.getUniqueCustomerNameFields();
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.COMPANY, SubMenu.CLIENTS);
        BOSearchSteps.expandSearchTab();
        customerNameFields.forEach(customer -> {
            BOClientsSearchSteps.setClientName(customer);
            BOSearchSteps.search();
            BOClientsTableSteps.openEditDialogForClient(customer);
            BOClientsTableValidations.verifyTheClientsLastName(data.getSearchData().getHasThisText());
            BOClientsDialogSteps.cancel();
        });
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByCompanyName(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.searchByText(data.getSearchData().getHasThisText());
        final List<String> customerNameFields = VNextBOSRTableSteps.getUniqueCustomerNameFields();
        customerNameFields.forEach(customer -> {
            Assert.assertTrue(customer.contains(data.getSearchData().getHasThisText()),
                    "The SR hasn't been found by customer");
        });
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByCustomerEmail(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.searchByText(data.getSearchData().getHasThisText());
        final List<String> customerNameFields = VNextBOSRTableSteps.getUniqueCustomerNameFields();
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.COMPANY, SubMenu.CLIENTS);
        BOSearchSteps.expandSearchTab();
        customerNameFields.forEach(customer -> {
            BOClientsSearchSteps.setClientName(customer);
            BOSearchSteps.search();
            BOClientsTableSteps.openEditDialogForClient(customer);
            BOClientsTableValidations.verifyTheCustomersEmail(data.getSearchData().getHasThisText());
            BOClientsDialogSteps.cancel();
        });
    }
}
