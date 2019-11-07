package com.cyberiansoft.test.vnextbo.testcases.services;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOServicesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAddNewServiceDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOServicesWebPage;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.utils.VNextPriceCalculations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOServicesTestCases extends BaseTestCase {

    private VNexBOLeftMenuPanel leftMenu;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getServicesTD();
    }

    @Override
    @BeforeMethod
    public void login() {
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginSteps.userLogin(userName, userPassword);
        VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel();
        headerPanel.executeJsForAddOnSettings(); //todo use the method getJsForAddOnSettings() from VNextBOServicesPartsAndLaborBundleData.java after fix
        leftMenu = new VNexBOLeftMenuPanel();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddMoneyService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName() + data.getServiceEdited())
                .clickAddNewServiceButton()
                .addNewService(data.getPriceServiceName(), data.getServiceType(), data.getServiceDescription(),
                        data.getServicePriceType(), data.getServicePrice())
                .searchServiceByServiceName(data.getPriceServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddPercentageService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName() + data.getServiceEdited());

        VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewServiceButton();
        servicespage = addnewservicedialog.addNewPercentageService(data.getPercentageServiceName(),
                data.getServiceType(), data.getPercentageServiceDescription(), data.getServicePercentageType(),
                data.getServicePrice());
        servicespage.searchServiceByServiceName(data.getPercentageServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditMoneyService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName() + data.getServiceEdited())
                .clickAddNewServiceButton()
                .addNewService(data.getPriceServiceName(), data.getServiceType(), data.getServiceDescription(),
                        data.getServicePriceType(), data.getServicePrice())
                .searchServiceByServiceName(data.getPriceServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()));

        VNextBOAddNewServiceDialog addnewservicedialog = servicespage
                .clickEditServiceByServiceName(data.getPriceServiceName());
        Assert.assertEquals(addnewservicedialog.getServiceName(), data.getPriceServiceName());
//        Assert.assertEquals(addnewservicedialog.getServiceType(), data.getServiceType());
        Assert.assertEquals(addnewservicedialog.getServiceDescription(), data.getServiceDescription());
        Assert.assertEquals(addnewservicedialog.getServicePricePercentageValueTxtField().getAttribute("value"),
                VNextPriceCalculations.getPriceRepresentation(data.getServicePrice()));
        Assert.assertTrue(addnewservicedialog.isServicePriceTypeVisible());

        addnewservicedialog.setServiceName(data.getPriceServiceName() + data.getServiceEdited());
        addnewservicedialog.selectServiceType(data.getServiceTypeEdited());
        addnewservicedialog.setServiceDescription(data.getServiceDescription() + data.getServiceEdited());
        addnewservicedialog.setServicePriceValue(data.getServicePriceEdited());
        addnewservicedialog.saveNewService();
        servicespage.searchServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName() +
                data.getServiceEdited()));

        Assert.assertEquals(servicespage.getServiceTypeValue(data.getPriceServiceName() +
                data.getServiceEdited()), data.getServiceTypeEdited());
        Assert.assertEquals(servicespage.getServicePriceValue(data.getPriceServiceName() +
                data.getServiceEdited()), VNextPriceCalculations.getPriceRepresentation(data.getServicePriceEdited()));
        Assert.assertEquals(servicespage.getServiceDescriptionValue(data.getPriceServiceName() +
                data.getServiceEdited()), data.getServiceDescription() + data.getServiceEdited());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testRemoveMoneyService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName() + data.getServiceEdited())
                .clickAddNewServiceButton()
                .addNewService(data.getPriceServiceName(), data.getServiceType(), data.getServiceDescription(),
                        data.getServicePriceType(), data.getServicePrice());

        leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()));
        VNextBOAddNewServiceDialog addNewServiceDialog = servicespage
                .clickEditServiceByServiceName(data.getPriceServiceName());
        Assert.assertEquals(addNewServiceDialog.getServiceName(), data.getPriceServiceName());
//        Assert.assertEquals(addNewServiceDialog.getServiceType(), data.getServiceType());
        Assert.assertEquals(addNewServiceDialog.getServiceDescription(), data.getServiceDescription());
        Assert.assertEquals(addNewServiceDialog.getServicePricePercentageValueTxtField().getAttribute("value"),
                VNextPriceCalculations.getPriceRepresentation(data.getServicePrice()));
        Assert.assertTrue(addNewServiceDialog.isServicePriceTypeVisible());

        addNewServiceDialog.setServiceName(data.getPriceServiceName() + data.getServiceEdited());
        String serviceTypeEdited = data.getServiceTypeEdited();
        addNewServiceDialog.selectServiceType(serviceTypeEdited);
        addNewServiceDialog.setServiceDescription(data.getServiceDescription() + data.getServiceEdited());
        addNewServiceDialog.setServicePriceValue(data.getServicePriceEdited());
        servicespage = addNewServiceDialog.clickSaveNewServiceButton();
        servicespage.searchServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName() +
                data.getServiceEdited()));

        Assert.assertEquals(servicespage.getServiceTypeValue(data.getPriceServiceName() +
                data.getServiceEdited()), serviceTypeEdited);
        System.out.println(servicespage.getServicePriceValue(data.getPriceServiceName() + data.getServiceEdited()));
        System.out.println(data.getServicePriceEdited());
        Assert.assertEquals(servicespage.getServicePriceValue(data.getPriceServiceName() + data.getServiceEdited()),
                VNextPriceCalculations.getPriceRepresentation(data.getServicePriceEdited()));
        Assert.assertEquals(servicespage.getServiceDescriptionValue(data.getPriceServiceName() +
                data.getServiceEdited()), data.getServiceDescription() + data.getServiceEdited());

        leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()
                + data.getServiceEdited()));
        servicespage.deleteServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertFalse(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()
                + data.getServiceEdited()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditPercentageService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName() + data.getServiceEdited());

        VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewServiceButton();
        servicespage = addnewservicedialog.addNewPercentageService(data.getPercentageServiceName(),
                data.getServiceType(), data.getPercentageServiceDescription(), data.getServicePercentageType(),
                data.getServicePrice());
        servicespage.searchServiceByServiceName(data.getPercentageServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()));

        addnewservicedialog = servicespage
                .clickEditServiceByServiceName(data.getPercentageServiceName());
        Assert.assertEquals(addnewservicedialog.getServiceName(), data.getPercentageServiceName());
//        Assert.assertEquals(addnewservicedialog.getServiceType(), data.getServiceType());
        Assert.assertEquals(addnewservicedialog.getServiceDescription(), data.getPercentageServiceDescription());
        Assert.assertEquals(addnewservicedialog.getServicePricePercentageValueTxtField().getAttribute("value"),
                VNextPriceCalculations.getPercentageRepresentation(data.getServicePrice()).replace("%", ""));
        Assert.assertTrue(addnewservicedialog.isServicePriceTypeVisible());

        addnewservicedialog.setServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        addnewservicedialog.selectServiceType(data.getServiceTypeEdited());
        addnewservicedialog.setServiceDescription(data.getPercentageServiceDescription()
                + data.getServiceEdited());
        addnewservicedialog.setServicePercentageValue(data.getServicePriceEdited());
        servicespage = addnewservicedialog.saveNewService();
        servicespage.searchServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));

        Assert.assertEquals(servicespage.getServiceTypeValue(data.getPercentageServiceName()
                + data.getServiceEdited()), data.getServiceTypeEdited());
        Assert.assertEquals(servicespage.getServicePriceValue(data.getPercentageServiceName()
                + data.getServiceEdited()), VNextPriceCalculations.getPercentageRepresentation(data.getServicePriceEdited()));
        Assert.assertEquals(servicespage.getServiceDescriptionValue(data.getPercentageServiceName()
                + data.getServiceEdited()), data.getPercentageServiceDescription() + data.getServiceEdited());
    }

    //TODO BUG #94337
    //TODO https://cyb.tpondemand.com/RestUI/Board.aspx#page=bug/94337&appConfig=eyJhY2lkIjoiNkZENTE5RjAzRTRCN0NCNTU0NzNFMEQ3NjE5QTgxOTIifQ==
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testRemovePercentageService(String rowID, String description, JSONObject testData) {

        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);
        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName() + data.getServiceEdited());

        VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewServiceButton();
        servicespage = addnewservicedialog.addNewPercentageService(data.getPercentageServiceName(),
                data.getServiceType(), data.getPercentageServiceDescription(), data.getServicePercentageType(),
                data.getServicePrice());
        servicespage.searchServiceByServiceName(data.getPercentageServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()));

        addnewservicedialog = servicespage
                .clickEditServiceByServiceName(data.getPercentageServiceName());
        Assert.assertEquals(addnewservicedialog.getServiceName(), data.getPercentageServiceName());
//        Assert.assertEquals(addnewservicedialog.getServiceType(), data.getServiceType());
        Assert.assertEquals(addnewservicedialog.getServiceDescription(), data.getPercentageServiceDescription());
        Assert.assertEquals(addnewservicedialog.getServicePricePercentageValueTxtField().getAttribute("value"),
                VNextPriceCalculations.getPercentageRepresentation(data.getServicePrice()).replace("%", ""));
        Assert.assertTrue(addnewservicedialog.isServicePriceTypeVisible());

        addnewservicedialog.setServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        addnewservicedialog.selectServiceType(data.getServiceTypeEdited());
        addnewservicedialog.setServiceDescription(data.getPercentageServiceDescription()
                + data.getServiceEdited());
        addnewservicedialog.setServicePercentageValue(data.getServicePriceEdited());
        servicespage = addnewservicedialog.saveNewService();
        servicespage.searchServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));

        Assert.assertEquals(servicespage.getServiceTypeValue(data.getPercentageServiceName()
                + data.getServiceEdited()), data.getServiceTypeEdited());
        Assert.assertEquals(servicespage.getServicePriceValue(data.getPercentageServiceName()
                + data.getServiceEdited()), VNextPriceCalculations.getPercentageRepresentation(data.getServicePriceEdited()));
        Assert.assertEquals(servicespage.getServiceDescriptionValue(data.getPercentageServiceName()
                + data.getServiceEdited()), data.getPercentageServiceDescription() + data.getServiceEdited());
        leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));
        servicespage.deleteServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertFalse(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));
        }

    //TODO BUG #94337
    //TODO https://cyb.tpondemand.com/RestUI/Board.aspx#page=bug/94337&appConfig=eyJhY2lkIjoiNkZENTE5RjAzRTRCN0NCNTU0NzNFMEQ3NjE5QTgxOTIifQ==
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testResumeRemovedMoneyService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName())
                .deleteServiceIfPresent(data.getPriceServiceName() + data.getServiceEdited())
                .clickAddNewServiceButton()
                .addNewService(data.getPriceServiceName(), data.getServiceType(), data.getServiceDescription(),
                        data.getServicePriceType(), data.getServicePrice());

        leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()));
        VNextBOAddNewServiceDialog addNewServiceDialog = servicespage
                .clickEditServiceByServiceName(data.getPriceServiceName());
        Assert.assertEquals(addNewServiceDialog.getServiceName(), data.getPriceServiceName());
//        Assert.assertEquals(addNewServiceDialog.getServiceType(), data.getServiceType());
        Assert.assertEquals(addNewServiceDialog.getServiceDescription(), data.getServiceDescription());
        Assert.assertEquals(addNewServiceDialog.getServicePricePercentageValueTxtField().getAttribute("value"),
                VNextPriceCalculations.getPriceRepresentation(data.getServicePrice()));
        Assert.assertTrue(addNewServiceDialog.isServicePriceTypeVisible());

        addNewServiceDialog.setServiceName(data.getPriceServiceName() + data.getServiceEdited());
        String serviceTypeEdited = data.getServiceTypeEdited();
        addNewServiceDialog.selectServiceType(serviceTypeEdited);
        addNewServiceDialog.setServiceDescription(data.getServiceDescription() + data.getServiceEdited());
        addNewServiceDialog.setServicePriceValue(data.getServicePriceEdited());
        servicespage = addNewServiceDialog.clickSaveNewServiceButton();
        servicespage.searchServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName() +
                data.getServiceEdited()));

        Assert.assertEquals(servicespage.getServiceTypeValue(data.getPriceServiceName() +
                data.getServiceEdited()), serviceTypeEdited);
        System.out.println(servicespage.getServicePriceValue(data.getPriceServiceName() + data.getServiceEdited()));
        System.out.println(data.getServicePriceEdited());
        Assert.assertEquals(servicespage.getServicePriceValue(data.getPriceServiceName() + data.getServiceEdited()),
                VNextPriceCalculations.getPriceRepresentation(data.getServicePriceEdited()));
        Assert.assertEquals(servicespage.getServiceDescriptionValue(data.getPriceServiceName() +
                data.getServiceEdited()), data.getServiceDescription() + data.getServiceEdited());

        leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()
                + data.getServiceEdited()));
        servicespage.deleteServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertFalse(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()
                + data.getServiceEdited()));

        leftMenu.selectServicesMenu();
        servicespage.advancedSearchService(data.getPriceServiceName() + data.getServiceEdited(), false);
        servicespage.clickUnarchiveButtonForService(data.getPriceServiceName() + data.getServiceEdited());
        Assert.assertEquals(VNextBOConfirmationDialogInteractions.clickNoAndGetConfirmationDialogMessage(),
                "Are you sure you want to restore \"" + data.getPriceServiceName() + data.getServiceEdited()
                        + "\" service?");
        servicespage.unarchiveServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
        servicespage.advancedSearchService(data.getPriceServiceName() + data.getServiceEdited(), true);
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPriceServiceName()
                + data.getServiceEdited()));
        servicespage.deleteServiceByServiceName(data.getPriceServiceName() + data.getServiceEdited());
    }

    //TODO BUG #94337
    //TODO https://cyb.tpondemand.com/RestUI/Board.aspx#page=bug/94337&appConfig=eyJhY2lkIjoiNkZENTE5RjAzRTRCN0NCNTU0NzNFMEQ3NjE5QTgxOTIifQ==
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testResumeRemovedPercentageService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);
        VNextBOServicesWebPage servicespage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName())
                .deleteServiceIfPresent(data.getPercentageServiceName() + data.getServiceEdited());

        VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewServiceButton();
        servicespage = addnewservicedialog.addNewPercentageService(data.getPercentageServiceName(),
                data.getServiceType(), data.getPercentageServiceDescription(), data.getServicePercentageType(),
                data.getServicePrice());
        servicespage.searchServiceByServiceName(data.getPercentageServiceName());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()));

        addnewservicedialog = servicespage.clickEditServiceByServiceName(data.getPercentageServiceName());
        Assert.assertEquals(addnewservicedialog.getServiceName(), data.getPercentageServiceName());
//        Assert.assertEquals(addnewservicedialog.getServiceType(), data.getServiceType());
        Assert.assertEquals(addnewservicedialog.getServiceDescription(), data.getPercentageServiceDescription());
        Assert.assertEquals(addnewservicedialog.getServicePricePercentageValueTxtFieldValue(),
                VNextPriceCalculations.getPercentageRepresentation(data.getServicePrice()).replace("%", ""));
        Assert.assertTrue(addnewservicedialog.isServicePriceTypeVisible());

        addnewservicedialog.setServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        addnewservicedialog.selectServiceType(data.getServiceTypeEdited());
        addnewservicedialog.setServiceDescription(data.getPercentageServiceDescription()
                + data.getServiceEdited());
        addnewservicedialog.setServicePercentageValue(data.getServicePriceEdited());
        servicespage = addnewservicedialog.saveNewService();
        servicespage.searchServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));

        Assert.assertEquals(servicespage.getServiceTypeValue(data.getPercentageServiceName()
                + data.getServiceEdited()), data.getServiceTypeEdited());
        System.out.println(servicespage.getServicePriceValue(data.getPercentageServiceName() + data.getServiceEdited()));
        System.out.println(VNextPriceCalculations.getPercentageRepresentation(data.getServicePriceEdited()));
        Assert.assertEquals(servicespage.getServicePriceValue(data.getPercentageServiceName()
                + data.getServiceEdited()), VNextPriceCalculations.getPercentageRepresentation(data.getServicePriceEdited()));
        Assert.assertEquals(servicespage.getServiceDescriptionValue(data.getPercentageServiceName()
                + data.getServiceEdited()), data.getPercentageServiceDescription() + data.getServiceEdited());
        leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));
        servicespage.deleteServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertFalse(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));

        servicespage = leftMenu.selectServicesMenu();
        servicespage.advancedSearchService(data.getPercentageServiceName()
                + data.getServiceEdited(), false);
        servicespage.clickUnarchiveButtonForService(data.getPercentageServiceName() + data.getServiceEdited());
        Assert.assertEquals(VNextBOConfirmationDialogInteractions.clickNoAndGetConfirmationDialogMessage(),
                "Are you sure you want to restore \"" + data.getPercentageServiceName()
                        + data.getServiceEdited() + "\" service?");
        servicespage.unarchiveServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
        servicespage.advancedSearchService(data.getPercentageServiceName()
                + data.getServiceEdited(), true);
        Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(data.getPercentageServiceName()
                + data.getServiceEdited()));
        servicespage.deleteServiceByServiceName(data.getPercentageServiceName() + data.getServiceEdited());
    }

    //TODO BUG 93334
    //TODO https://cyb.tpondemand.com/RestUI/Board.aspx#page=bug/94334&appConfig=eyJhY2lkIjoiNkZENTE5RjAzRTRCN0NCNTU0NzNFMEQ3NjE5QTgxOTIifQ==
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyErrorMessagesOnCreateEditServiceDialog(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicespage = leftMenu.selectServicesMenu();
        VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewServiceButton();
        addnewservicedialog.clickServiceAddButton();
        Assert.assertEquals(addnewservicedialog.getErrorMessage(), "Service name is required!");
        addnewservicedialog.setServiceName(data.getEmptyServiceName());
        addnewservicedialog.clickServiceAddButton();
        Assert.assertEquals(addnewservicedialog.getErrorMessage(), "Service name is required!");
        addnewservicedialog.setServiceName(data.getServiceName());
        addnewservicedialog.saveNewService();
        servicespage.searchServiceByServiceName(data.getServiceName());
        addnewservicedialog = servicespage.clickEditServiceByServiceName(data.getServiceName());
        addnewservicedialog.setServiceName(data.getEmptyServiceName());
        addnewservicedialog.clickServiceAddButton();
        Assert.assertEquals(addnewservicedialog.getErrorMessage(), "Service name is required!");
        servicespage = addnewservicedialog.closeNewServiceDialog();
        servicespage.deleteServiceByServiceName(data.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditMatrixService(String rowID, String description, JSONObject testData) {
        VNextBOServicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceType(data.getMatrixServiceType());
        String firstServiceNameInTableRow = servicesPage.getFirstServiceNameInTableRow();
        servicesPage
                .clickEditServiceByServiceName(firstServiceNameInTableRow)
                .setServiceName(data.getNewMatrixServiceName())
                .setServiceDescription(data.getServiceDescription())
                .saveNewService()
                .searchServiceByServiceName(data.getNewMatrixServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getNewMatrixServiceName()));
    }
}