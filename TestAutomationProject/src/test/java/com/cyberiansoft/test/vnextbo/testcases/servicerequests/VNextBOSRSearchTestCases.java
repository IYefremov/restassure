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
import com.cyberiansoft.test.vnextbo.validations.servicerequests.details.VNextBOSRGeneralInfoValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.details.VNextBOSRVehicleInfoValidations;
import org.json.simple.JSONObject;
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
    public void verifySRIsFoundByCustomersFirstName(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String firstName = data.getSearchData().getCustomer();
        VNextBOReactSearchPanelSteps.searchByText(firstName);
        VNextBOSRTableValidations.verifyNotFoundNotificationIsDisplayed();
        VNextBOReactSearchPanelValidations.verifyClearSearchIconIsDisplayed();
        VNextBOReactSearchPanelSteps.clearSearchFilter();
        VNextBOReactSearchPanelValidations.verifySearchInputFieldIsEmpty();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed();
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
    public void verifySRIsFoundByCustomersLastName(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String lastName = data.getSearchData().getHasThisText();
        VNextBOReactSearchPanelSteps.searchByText(lastName);
        final List<String> customerNameFields = VNextBOSRTableSteps.getUniqueCustomerNameFields();
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.COMPANY, SubMenu.CLIENTS);
        BOSearchSteps.expandSearchTab();
        customerNameFields.forEach(customer -> {
            BOClientsSearchSteps.setClientName(customer);
            BOSearchSteps.search();
            BOClientsTableSteps.openEditDialogForClient(customer);
            BOClientsTableValidations.verifyTheClientsLastName(lastName);
            BOClientsDialogSteps.cancel();
        });
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByCompanyName(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String company = data.getSearchData().getHasThisText();
        VNextBOReactSearchPanelSteps.searchByText(company);
        VNextBOSRTableSteps.getUniqueCustomerNameFields()
                .forEach(customer -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(customer, company));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByCustomerEmail(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String email = data.getSearchData().getHasThisText();
        VNextBOReactSearchPanelSteps.searchByText(email);
        final List<String> customerNameFields = VNextBOSRTableSteps.getUniqueCustomerNameFields();
        VNextBOBaseWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.COMPANY, SubMenu.CLIENTS);
        BOSearchSteps.expandSearchTab();
        customerNameFields.forEach(customer -> {
            BOClientsSearchSteps.setClientName(customer);
            BOSearchSteps.search();
            BOClientsTableSteps.openEditDialogForClient(customer);
            BOClientsTableValidations.verifyTheCustomersEmail(email);
            BOClientsDialogSteps.cancel();
        });
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByVIN(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String vinNum = data.getSearchData().getHasThisText();
        VNextBOReactSearchPanelSteps.searchByText(vinNum);
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRVehicleInfoValidations.verifyVinIsDisplayed(vinNum);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByRO(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String roNum = data.getSearchData().getHasThisText();
        VNextBOReactSearchPanelSteps.searchByText(roNum);
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRGeneralInfoValidations.verifyRoIsDisplayed(roNum);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundByStockNum(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String stockNum = data.getSearchData().getHasThisText();
        VNextBOReactSearchPanelSteps.searchByText(stockNum);
        VNextBOSRTableSteps.getUniqueStockNumbersFields()
                .forEach(stock -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(stock, stockNum));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySRIsFoundBySRNum(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String srNum = data.getSearchData().getHasThisText();
        VNextBOReactSearchPanelSteps.searchByText(srNum);
        VNextBOSRTableSteps.getSRNumValues()
                .forEach(sr -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(sr, srNum));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteSearchOptions(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        final String type = data.getSearchData().getHasThisText();
        final String infoText = data.getInfoText();
        VNextBOReactSearchPanelSteps.searchByText(type);
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(infoText);
        VNextBOReactSearchPanelSteps.clearSearchFilter();
        VNextBOReactSearchPanelValidations.verifySearchInputFieldIsEmpty();
        VNextBOSRTableValidations.verifyEitherNotificationOrSRsListIsDisplayed();
    }
}
