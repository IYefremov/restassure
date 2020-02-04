package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.PartStatuses;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsOrdersListPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores.VNextBOAutoZoneProductResultsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores.VNextBOAutoZoneQuoteDetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOShoppingCartDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.stores.VNextBOAutoZoneLoginPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.stores.VNextBOAutoZoneMyShopPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.stores.VNextBOAutoZoneProductResultsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.stores.VNextBOAutoZoneValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class VNextBOPartsManagementGenericPartProviderFunctionalityTestCases extends BaseTestCase {

    private String status;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMGenericPartProviderFunctionalityTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        status = PartStatuses.OPEN.getStatus();
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanStartPartsQuoteActivityOnlyForOrdersWithOpenServiceStatus(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyPartStatusesDoNotContainRestrictedStatus(PartStatuses.OPEN.getStatus());
        VNextBOPartsDetailsPanelInteractions.clickGetQuotesPartButton();
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been opened");
        VNextBOPartsProvidersDialogSteps.openRequestFormDialogWithGetNewQuoteButton(data.getProvider());
        Assert.assertTrue(VNextBOPartsProvidersRequestFormDialogValidations.isRequestFormDialogMessageDisplayed(),
                "The warning message hasn't been displayed");
        Assert.assertEquals(VNextBOPartsProvidersRequestFormDialogInteractions.getRequestFormDialogMessage(),
                VNextBOAlertMessages.NO_PARTS_TO_ORDER, "The message hasn't been shown correctly");
        Assert.assertTrue(VNextBOPartsProvidersRequestFormDialogValidations.isRequestQuoteButtonDisabled(),
                "The request quote button is not disabled");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAllOpenStatusServicesAreDisplayedOnQuotePageWithCorrectQuantity(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        final String vin = VNextBOPartsOrdersListPanelInteractions.getFirstOrderVinNumber();

        VNextBOPartsDetailsPanelSteps.addPartIfOpenStatusIsNotPresent(data.getPartData(), data.getSearchData().getWoNum());
        final List<String> detailsPanelPartNamesByStatus = VNextBOPartsDetailsPanelInteractions.getPartNamesByStatus(status);
        VNextBOPartsDetailsPanelSteps.clickGetQuotesPartButton();
        VNextBOPartsProvidersDialogSteps.openRequestFormDialogWithGetNewQuoteButton(data.getProvider());

        final String title = VNextBOPartsProvidersRequestFormDialogInteractions.getTitle();
        Assert.assertEquals(vin, VNextBOPartsProvidersRequestFormDialogInteractions.getVinFromTitle(title),
                "The request form dialog title doesn't contain the VIN number");
        Assert.assertTrue(title.contains("Cadillac"),
                "The request form dialog title doesn't contain the vehicle info");
        final List<String> requestFormPartNames = VNextBOPartsProvidersRequestFormDialogInteractions.getPartNamesList();
        Assert.assertTrue(detailsPanelPartNamesByStatus.containsAll(requestFormPartNames),
                "The parts names on the PM page are not displayed properly in the request form dialog");
        Assert.assertEquals(detailsPanelPartNamesByStatus.size(), requestFormPartNames.size(),
                "The number of parts on the PM page and in the request form dialog differs");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkGenericPartProvider(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());

        VNextBOPartsDetailsPanelSteps.deleteServicesByStatus(status);
        VNextBOPartsDetailsPanelSteps.addPartIfOpenStatusIsNotPresent(
                data.getPartData(), data.getSearchData().getWoNum(), 4);
        VNextBOPartsDetailsPanelInteractions.setPoByStatusIfEmpty(status);
        VNextBOPartsDetailsPanelValidations.verifyPoNumbersAreFilledForStatus(status);
        final List<String> detailsPanelPartNamesByStatus = VNextBOPartsDetailsPanelInteractions.getPartNamesByStatus(status);
        final List<String> partIdsByStatus = VNextBOPartsDetailsPanelInteractions.getPartIdsByStatus(status);

        VNextBOPartsDetailsPanelSteps.clickGetQuotesPartButton();
        VNextBOPartsProvidersDialogSteps.openRequestFormDialogWithGetNewQuoteButton(data.getProvider());

        final List<String> partNames = VNextBOPartsProvidersRequestFormDialogInteractions.getPartNamesList();
        Assert.assertTrue(detailsPanelPartNamesByStatus.containsAll(partNames),
                "The parts names on the PM page are not displayed properly in the request form dialog");
        Assert.assertEquals(detailsPanelPartNamesByStatus.size(), partNames.size(),
                "The number of parts on the PM page and in the request form dialog differs");
        Assert.assertTrue(VNextBOPartsProvidersRequestFormDialogValidations.isRequestQuoteButtonDisabled(),
                "The request quote button hasn't been disabled");
        final int firstRandom = Utils.getRandomNumber(0, 2);
        final int secondRandom = Utils.getRandomNumber(2, 4);
        VNextBOPartsProvidersRequestFormDialogInteractions.clickPartCheckbox(firstRandom);
        VNextBOPartsProvidersRequestFormDialogInteractions.clickPartCheckbox(secondRandom);

        final List<Integer> quantity = Arrays.asList(
                VNextBOPartsProvidersRequestFormDialogInteractions.getPartQuantityByOrder(firstRandom),
                VNextBOPartsProvidersRequestFormDialogInteractions.getPartQuantityByOrder(secondRandom));

        final List<String> parts = Arrays.asList(partNames.get(firstRandom), partNames.get(secondRandom));
        final String title = VNextBOPartsProvidersRequestFormDialogInteractions.getTitle();
        VNextBOPartsProvidersRequestFormDialogInteractions.clickCancelButton();
        VNextBOPartsProvidersDialogSteps.openRequestFormDialogWithGetNewQuoteButton(data.getProvider());
        Assert.assertTrue(VNextBOPartsProvidersRequestFormDialogValidations.isRequestQuoteButtonDisabled(),
                "The request quote button hasn't been disabled");

        VNextBOPartsProvidersRequestFormDialogSteps.getQuotesForFirstParts(4);
        VNextBOPartsProvidersDialogSteps.closePartsProvidersDialog();
        Assert.assertTrue(VNextBOPartsDetailsPanelInteractions.getPartIdsByStatus(PartStatuses.QUOTE_REQUESTED.getStatus())
                        .containsAll(partIdsByStatus.subList(0, 4)),
                "The status of parts hasn't been changed from 'Open' to 'Quote Requested'");

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanImportCorePriceFromPunchoutWebStore(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);
        final VNextBOPartsData partData = data.getPartData();
        final VNextBOPartsManagementSearchData searchData = data.getSearchData();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(searchData.getWoNum());

//        VNextBOPartsDetailsPanelSteps.deleteServicesByStatus(status);
        VNextBOPartsDetailsPanelSteps.addPartIfOpenStatusIsNotPresent(partData, searchData.getWoNum());
        VNextBOPartsDetailsPanelInteractions.clickGetQuotesPartButton();
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been opened");
        VNextBOPartsProvidersDialogSteps.openStore(data.getProvider());
        VNextBOAutoZoneValidations.verifyAutoZonePageIsOpened("AutoZonePro.com");
        VNextBOAutoZoneLoginPageSteps.loginToAutoZone();
        VNextBOAutoZoneValidations.verifyAutoZonePageIsOpened("MyShop");
        VNextBOAutoZoneMyShopPageSteps.lookupForBatteriesCables();
        VNextBOAutoZoneValidations.verifyAutoZonePageIsOpened("Product Results");
        final String corePrice = VNextBOAutoZoneProductResultsPageInteractions.getCorePriceValue();
        final String price = VNextBOAutoZoneProductResultsPageInteractions.getPriceValue();
        VNextBOAutoZoneProductResultsPageSteps.makeOrder();
        VNextBOAutoZoneValidations.verifyAutoZonePageIsOpened("Quote Details");
        Assert.assertEquals(price, VNextBOAutoZoneQuoteDetailsPageInteractions.getTotalPriceValue(),
                "The price is not calculated properly");
        VNextBOAutoZoneQuoteDetailsPageInteractions.clickTransferCartButton();

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(searchData.getWoNum());
        Assert.assertTrue(VNextBOPartsDetailsPanelValidations.isShoppingCartButtonDisplayed(true),
                "The shopping cart button hasn't been displayed");
        VNextBOShoppingCartDialogSteps.completeOrder(partData, price, corePrice);
    }
}
