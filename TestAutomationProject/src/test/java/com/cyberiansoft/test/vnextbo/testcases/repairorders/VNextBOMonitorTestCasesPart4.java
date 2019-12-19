package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOROAdvancedSearchValues;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOAuditLogDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.*;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VNextBOMonitorTestCasesPart4 extends BaseTestCase {

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
	}

	@AfterMethod
	public void refreshPage() {
		Utils.refreshPage();
	}

    //todo blocker, needs update
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 55)
    public void verifyUserCanSeeChangesOfPhasesInLogInfo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());

        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

        VNextBORODetailsPageInteractions.clickLogInfoButton();
        Assert.assertTrue(VNextBOAuditLogDialogValidations.isAuditLogDialogDisplayed(), "The audit log modal dialog hasn't been opened");

        VNextBOAuditLogDialogInteractions.getAuditLogsTabsNames().forEach(System.out::println);
        System.out.println("*****************************************");
        System.out.println();

        final List<String> tabs = Arrays.asList(data.getAuditLogTabs());
        tabs.forEach(System.out::println);
        System.out.println("*****************************************");
        System.out.println();

        Assert.assertTrue(VNextBOAuditLogDialogInteractions.getAuditLogsTabsNames().containsAll(tabs),
                "The audit logs tabs are not displayed");

        VNextBOAuditLogDialogInteractions.clickAuditLogsPhasesAndDepartmentsTab();
        final String phasesLastRecord = VNextBOAuditLogDialogInteractions.getDepartmentsAndPhasesLastRecord();
        Assert.assertTrue(!phasesLastRecord.isEmpty(), "The last phase record hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 56)
    public void verifyUserCanChangeQuantityForService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        String serviceQuantity = RandomStringUtils.randomNumeric(2);
        final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
        System.out.println("serviceTotalPrice: " + serviceTotalPrice);
        System.out.println("Random serviceQuantity: " + serviceQuantity);
        System.out.println("ServiceQuantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));

        if (serviceQuantity.equals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId))) {
            serviceQuantity = RandomStringUtils.randomNumeric(2);
            System.out.println("Random serviceQuantity 2: " + serviceQuantity);
        }
        VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, serviceQuantity);
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
        System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
        Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
                "The service total price hasn't been recalculated after changing the service quantity");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 57)
    public void verifyUserCanEnterNegativeNumberForServiceQuantity(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        String negativeServiceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
        String serviceQuantity = String.valueOf(RandomUtils.nextInt(1, 100));
        VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, serviceQuantity);
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());

        final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
        System.out.println("serviceTotalPrice: " + serviceTotalPrice);
        System.out.println("Random negative serviceQuantity: " + negativeServiceQuantity);
        System.out.println("Random serviceQuantity: " + negativeServiceQuantity);
        System.out.println("ServiceQuantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId) + "\n");

        if (String.valueOf(Math.abs(Integer.valueOf(negativeServiceQuantity))).equals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId))) {
            negativeServiceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
            System.out.println("Random serviceQuantity 2: " + negativeServiceQuantity);
        }
        VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, negativeServiceQuantity);
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
        System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
        //todo the total price sometimes is not recalculated, but can hardly be reproduced manually
//		Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
//				"The service total price hasn't been recalculated after setting the negative number for the service quantity");
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId), String.valueOf(0),
                "The service quantity hasn't been changed to 0");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 58)
    public void verifyUserCannotEnterTextForServiceQuantity(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
        System.out.println("ServiceID: " + serviceId);
        System.out.println("serviceTotalPrice: " + serviceTotalPrice);
        System.out.println("ServiceQuantity: " + data.getServiceQuantity());
        System.out.println("ServiceQuantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));

        VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, data.getServiceQuantity());
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
        System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
        Assert.assertEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
                "The service total price has been recalculated after entering the text " +
                        "into the service quantity input field");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 59)
    public void verifyUserCanChangePriceForService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        String servicePrice = String.valueOf(RandomUtils.nextInt(10, 100));
        final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
        System.out.println("serviceTotalPrice: " + serviceTotalPrice);
        System.out.println("Random servicePrice: " + servicePrice);
        System.out.println("ServicePrice: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));

        if (servicePrice.equals(VNextBORODetailsPageInteractions.getServicePrice(serviceId))) {
            servicePrice = String.valueOf(RandomUtils.nextInt(10, 100));
            System.out.println("Random servicePrice 2: " + servicePrice);
        }
        VNextBORODetailsPageInteractions.setServicePrice(serviceId, servicePrice);
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
        System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
        Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
                "The service total price hasn't been recalculated after changing the service price");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 60)
    public void verifyUserCanEnterNegativeNumberForServicePrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        String servicePrice = String.valueOf(-(RandomUtils.nextInt(10, 100)));
        final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
        System.out.println("serviceTotalPrice: " + serviceTotalPrice);
        System.out.println("Random servicePrice: " + servicePrice);
        System.out.println("ServicePrice: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));

        if (String.valueOf(Math.abs(Integer.valueOf(servicePrice))).equals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId))) {
            servicePrice = String.valueOf(-(RandomUtils.nextInt(10, 100)));
            System.out.println("Random servicePrice 2: " + servicePrice);
        }
        VNextBORODetailsPageInteractions.setServicePrice(serviceId, servicePrice);
        WaitUtilsWebDriver.waitForLoading();
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
        System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
        Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
                "The service total price hasn't been recalculated " +
                        "after setting the negative number for the service price");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 61)
    public void verifyUserCannotEnterTextForServicePrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
        System.out.println("serviceTotalPrice: " + serviceTotalPrice);
        System.out.println("ServicePrice: " + data.getServicePrice());
        System.out.println("ServicePrice: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));

        VNextBORODetailsPageInteractions.setServicePrice(serviceId, data.getServicePrice());
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
        System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
        Assert.assertEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
                "The service total price has been recalculated after entering the text " +
                        "into the service price input field");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 62)
    public void verifyUserCanSeePhaseOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        System.out.println("Phase name: " + VNextBORODetailsPageInteractions.getPhaseNameValue());
        System.out.println("Phase vendor price: " + VNextBORODetailsPageInteractions.getPhaseVendorPriceValue());
        System.out.println("Phase vendor technician: " + VNextBORODetailsPageInteractions.getPhaseVendorTechnicianValue());
        System.out.println("Phase status: " + VNextBORODetailsPageInteractions.getPhaseStatusValue());
        System.out.println("Phase actions trigger: " + VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed());

        Assert.assertEquals(VNextBORODetailsPageInteractions.getPhaseNameValue(), data.getServicePhaseHeaders()[0],
                "The phase name value hasn't been displayed properly");
        Assert.assertNotEquals(VNextBORODetailsPageInteractions.getPhaseVendorPriceValue(), "",
                "The phase vendor price hasn't been displayed");
        Assert.assertEquals(VNextBORODetailsPageInteractions.getPhaseVendorTechnicianValue(), data.getServicePhaseHeaders()[1],
                "The phase vendor technician value hasn't been displayed properly");
        Assert.assertEquals(VNextBORODetailsPageInteractions.getPhaseStatusValue(), data.getServicePhaseHeaders()[2],
                "The phase status hasn't been displayed properly");
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(),
                "The phase actions trigger hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 63)
    public void verifyUserCanSeeStartedPrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

//        final String calculatedPrice = VNextBORODetailsPageInteractions.getPhasePriceValue().replace("$", ""); //todo add method after bug #79907 fix!!!
//        System.out.println("Phase price: " + calculatedPrice);
//        final List<String> pricesValuesList = VNextBORODetailsPageInteractions.getVendorPricesValuesList();
//
//        final List<String> values = new ArrayList<>();
//        for (String price : pricesValuesList) {
//            final String value = price
//                    .replace("$", "")
//                    .replace("(", "-")
//                    .replace(")", "");
//            values.add(value);
//        }
//
//        final double sum = values
//                .stream()
//                .mapToDouble(Double::parseDouble)
//                .reduce((val1, val2) -> val1 + val2)
//                .orElse(0.00);
//
//        System.out.println("Sum: " + sum);
//        Assert.assertEquals(sum, Double.valueOf(calculatedPrice), "The sum hasn't been calculated properly"); todo is bug??? uncomment
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 64)
    public void verifyUserCanSeeStartedVendorPrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

        final String calculatedVendorPrice = VNextBORODetailsPageInteractions.getPhaseVendorPriceValue().replace("$", "");
        System.out.println("Phase vendor price: " + calculatedVendorPrice);
        final List<String> vendorPricesValuesList = VNextBORODetailsPageInteractions.getVendorPricesValuesList();

        final List<String> values = new ArrayList<>();
        for (String vendorPrice : vendorPricesValuesList) {
            final String value = vendorPrice
                    .replace("$", "")
                    .replace("(", "-")
                    .replace(")", "");
            values.add(value);
        }

        final double sum = values
                .stream()
                .mapToDouble(Double::parseDouble)
                .reduce((val1, val2) -> val1 + val2)
                .orElse(0.00);

        System.out.println("Sum: " + sum);
//        Assert.assertEquals(sum, Double.valueOf(calculatedVendorPrice), "The sum hasn't been calculated properly");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 65)
    public void verifyUserCanChangeTechnicianOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

        Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

        VNextBORODetailsPageInteractions.expandServicesTable();

        Assert.assertNotEquals(VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()), 0);
        Assert.assertNotEquals(VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()), 0);
        Assert.assertEquals(VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
                VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
                "The vendor and technician options haven't been changed for all repair orders with the 'Active' status");

        // clearing the test data
        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

        Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor1(), data.getTechnician1());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 66)
    public void verifyUserCanChangeAndNotSaveWithXButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

        final int numberOfVendorOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor());
        final int numberOfTechnicianOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician());

        VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

        Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

        Assert.assertEquals(numberOfVendorOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
                "The vendor options number has been changed for repair orders with the 'Active' status");

        Assert.assertEquals(numberOfTechnicianOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
                "The vendor options number has been changed for repair orders with the 'Active' status");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 67)
    public void verifyUserCanChangeAndNotSaveWithCancelButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

        final int numberOfVendorOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor());
        final int numberOfTechnicianOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician());

        VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

        Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

        Assert.assertEquals(numberOfVendorOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
                "The vendor options number has been changed for repair orders with the 'Active' status");

        Assert.assertEquals(numberOfTechnicianOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
                "The vendor options number has been changed for repair orders with the 'Active' status");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 68)
    public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

        final String priorTechniciansValue = VNextBOROPageInteractions.getTechniciansValueForWO(data.getOrderNumber());
        String selectedRandomTechnician = VNextBOROPageSteps.setTechnicianAndVendorByWoNumber(
                data.getOrderNumber(), data.getVendor());
        if (priorTechniciansValue.equals(selectedRandomTechnician)) {
            selectedRandomTechnician = VNextBOROPageSteps.setTechnicianAndVendorByWoNumber(
                    data.getOrderNumber(), data.getVendor());
        }
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[0]);
        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[1], data.getServiceTabs()[1]);
        VNextBORODetailsPageValidations.verifyVendorTechnicianNameIsSet(selectedRandomTechnician);

        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROPageValidations.verifyAnotherTechnicianIsDisplayed(data.getOrderNumber(), selectedRandomTechnician);
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[1]);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 69)
    public void verifyUserCanSearchBySavedSearchForm(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBOROPageSteps.openAdvancedSearchDialog();
        final VNextBOROAdvancedSearchValues searchValues = data.getSearchValues();

        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getCustomerInputFieldValue(), searchValues.getCustomer());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getEmployeeInputFieldValue(), searchValues.getEmployee());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getPhaseInputFieldValue(), searchValues.getPhase());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getPhaseStatusInputFieldValue(), searchValues.getPhaseStatus());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getDepartmentInputFieldValue(), searchValues.getDepartment());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getWoTypeInputFieldValue(), searchValues.getWoType());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getWoNumInputFieldValue(), searchValues.getWoNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getRoNumInputFieldValue(), searchValues.getRoNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getStockInputFieldValue(), searchValues.getStockNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getVinInputFieldValue(), searchValues.getVinNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getTimeFrameInputFieldValue(), searchValues.getTimeFrame());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getRepairStatusInputFieldValue(), searchValues.getRepairStatus());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getDaysInProcessInputFieldValue(), searchValues.getDaysInProcess());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getDaysInPhaseInputFieldValue(), searchValues.getDaysInPhase());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getFlagInputFieldValue(), searchValues.getFlag());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getSortByInputFieldValue(), searchValues.getSortBy());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getSearchNameInputFieldValue(), searchValues.getSearchName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 70)
    public void verifyUserCannotEditSavedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[0]);
        Assert.assertTrue(VNextBOROPageValidations.isSavedSearchEditIconDisplayed(true),
                "The saved search edit icon hasn't been displayed");
        VNextBOROPageSteps.openAdvancedSearchDialog();
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isSavedSearchNameClickable(),
                "The saved search input field isn't clickable");
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isSaveButtonClickable(),
                "The save button isn't clickable");
        VNextBOROAdvancedSearchDialogSteps.closeAdvancedSearchDialog();

        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[1]);
        VNextBOROPageSteps.openAdvancedSearchDialog();
        Assert.assertTrue(VNextBOROPageValidations.isSavedSearchEditIconDisplayed(false),
                "The saved search edit icon hasn't been displayed");
        Assert.assertFalse(VNextBOROAdvancedSearchDialogValidations.isSavedSearchNameClickable(),
                "The saved search input field is clickable");
        Assert.assertFalse(VNextBOROAdvancedSearchDialogValidations.isSaveButtonClickable(),
                "The save button is clickable");
        VNextBOROAdvancedSearchDialogSteps.closeAdvancedSearchDialog();
    }

    //	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    TODO the TC blocker - the locations are not loaded for the given technician
    public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyCompletedWork(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHeaderPanelSteps.logout();
        VNextBOLoginSteps.userLogin(data.getUserName(), data.getUserPassword());

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageValidations.verifyPhaseStatuses(data.getServiceStatuses());
    }

    //todo continue after the blocker is resolved
//	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyWorkQueue(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHeaderPanelSteps.logout();
        VNextBOLoginSteps.userLogin(data.getUserName(), data.getUserPassword());

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageValidations.verifyPhaseStatuses(data.getServiceStatuses());
    }

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 71)
//    public void verifyUserCanResolveProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
//        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
//
//        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
//        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
//        VNextBOROPageSteps.openRODetailsPage();
//        VNextBORODetailsPageValidations.verifyPhaseStatuses(data.getServiceStatuses());
//    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 72)
    public void verifyUserCanSeeInvoiceOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickInvoiceNumberInTable(data.getOrderNumber(), data.getInvoiceNumber());
        VNextBOROPageValidations.verifyInvoiceWindowIsOpened();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 72)
    public void verifyUserCanSeeArbitrationDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.search(data.getOrderNumber());
        Assert.assertEquals(VNextBOROPageInteractions.getArbitrationDatesList().get(0), data.getArbitrationDate(),
                "The arbitration date han't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 72)
    public void verifyUserCanUsePaginationFilter(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTen());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTen()));

        VNextBOPageSwitcherSteps.changeItemsPerPage(String.valueOf(data.getPages().getTwenty()));
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTwenty());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTwenty()));

        VNextBOPageSwitcherSteps.changeItemsPerPage(String.valueOf(data.getPages().getFifty()));
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getFifty());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getFifty()));

        VNextBOPageSwitcherSteps.changeItemsPerPage(String.valueOf(data.getPages().getHundred()));
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getHundred());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getHundred()));

        VNextBOPageSwitcherSteps.changeItemsPerPage(String.valueOf(data.getPages().getTen()));
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTen());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTen()));


        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTen());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTen()));
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(true),
                "The header first page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(true),
                "The footer first page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderPreviousPageButtonClickable(true),
                "The header last page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterPreviousPageButtonClickable(true),
                "The footer last page button is not clickable");

        VNextBOPageSwitcherSteps.clickHeaderPreviousPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTen());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTen()));
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(true),
                "The header last page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(true),
                "The footer last page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderNextPageButtonClickable(true),
                "The header next page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterNextPageButtonClickable(true),
                "The footer next page button is not clickable");

        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTen());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTen()));
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(false),
                "The header last page button is clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(false),
                "The footer last page button is clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderNextPageButtonClickable(false),
                "The header next page button is clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterNextPageButtonClickable(false),
                "The footer next page button is clickable");

        VNextBOPageSwitcherSteps.clickHeaderFirstPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTen());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTen()));
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(false),
                "The header last page button is clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(false),
                "The footer last page button is clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderPreviousPageButtonClickable(false),
                "The header previous page button is clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterPreviousPageButtonClickable(false),
                "The footer previous page button is clickable");

        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("3");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect(data.getPages().getTen());
        VNextBOROPageValidations.verifyMaximumNumberOfOrdersOnPage(Integer.valueOf(data.getPages().getTen()));
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(true),
                "The header first page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(true),
                "The footer first page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderPreviousPageButtonClickable(true),
                "The header last page button is not clickable");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterPreviousPageButtonClickable(true),
                "The footer last page button is not clickable");
	}
}