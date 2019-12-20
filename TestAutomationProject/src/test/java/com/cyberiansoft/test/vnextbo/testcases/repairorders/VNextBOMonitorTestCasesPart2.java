package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOAddNewServiceMonitorDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.*;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOAddNewServiceMonitorDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorTestCasesPart2 extends BaseTestCase {

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
	}

	@AfterMethod
	public void refreshPage() {
		Utils.refreshPage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
	public void verifyUserCanTypeAndNotSaveNotesWithXIcon(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
        //todo finish (hasn't been completed because of the bug 78127)
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
	public void verifyUserCanTypeAndNotSaveNotesWithClose(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
		//todo bug fix #78127
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
	public void verifyUserCanOpenRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
	public void verifyUserCanChangeStockOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.typeStockNumber(data.getStockNumbers()[1]);
        VNextBORODetailsPageInteractions.typeStockNumber(data.getStockNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
	public void verifyUserCanChangeRoNumOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.typeRoNumber(data.getRoNumbers()[1]);
        VNextBORODetailsPageInteractions.typeRoNumber(data.getRoNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 21)
	public void verifyUserCanChangeStatusOfRoToNew(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 22)
	public void verifyUserCanChangeStatusOfRoToOnHold(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status hasn't been set");
		Assert.assertTrue(VNextBORODetailsPageValidations.isImageOnHoldStatusDisplayed(),
				"The On Hold image notification hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 23)
	public void verifyUserCanChangeStatusOfRoToApproved(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 24)
	public void verifyUserCannotChangeStatusOfRoToDraft(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		Assert.assertNotEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status has been changed to 'Draft'");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 25)
	public void verifyUserCanChangeStatusOfRoToClosedWithNoneReason(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps
                .searchByActivePhase(data.getPhase(), data.getPhaseStatus(), data.getTimeFrame());

        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBOCloseRODialogSteps.closeROWithReason(data.getProblemReason());
        Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatusValue(), data.getStatus(),
                "The status hasn't been changed to 'Closed'");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 26)
	public void verifyUserCanChangePriorityOfRoToLow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setPriority(data.getPriority());
        VNextBORODetailsPageInteractions.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber(), true)) {
			Assert.assertTrue(VNextBOROPageValidations.isArrowDownDisplayed(data.getOrderNumber(), true),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 27)
	public void verifyUserCanChangePriorityOfRoToNormal(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setPriority(data.getPriority());
        VNextBORODetailsPageInteractions.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber(), true)) {
			Assert.assertFalse(VNextBOROPageValidations.isArrowDownDisplayed(data.getOrderNumber(), true),
					"The work order arrow down should not be displayed");
			Assert.assertFalse(VNextBOROPageValidations.isArrowUpDisplayed(data.getOrderNumber(), true),
					"The work order arrow down should not be displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 28)
	public void verifyUserCanAddNewService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps
                .setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithSubmit(data, serviceDescription);
        Utils.refreshPage();

		VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(VNextBORODetailsPageValidations.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			Assert.assertNotEquals(serviceId, "",
					"The created service hasn't been displayed");
			System.out.println("description: " + VNextBORODetailsPageInteractions.getServiceDescription(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId), data.getServiceQuantity(),
					"The service quantity is not equal to the inserted quantity value");

			System.out.println("price: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServicePrice(serviceId), data.getServicePrice(),
					"The service price is not equal to the inserted price value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 29)
	public void verifyUserCanChangePriorityOfRoToHigh(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setPriority(data.getPriority());
		VNextBORODetailsPageInteractions.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber(), true)) {
			Assert.assertTrue(VNextBOROPageValidations.isArrowUpDisplayed(data.getOrderNumber(), true),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 30)
	public void verifyUserCanAddNewMoneyService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(),
                "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps.setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithSubmit(data, serviceDescription);

		VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(VNextBORODetailsPageValidations.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			Assert.assertNotEquals(serviceId, "", "The created service hasn't been displayed");
			System.out.println("description: " + VNextBORODetailsPageInteractions.getServiceDescription(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId), data.getServiceQuantity(),
					"The service quantity is not equal to the inserted quantity value");

			System.out.println("price: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServicePrice(serviceId), data.getServicePrice(),
					"The service price is not equal to the inserted price value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 31)
	public void verifyUserCanAddNewLaborService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);

        VNextBOAddNewServiceMonitorDialogSteps.setAddNewLaborServiceMonitorValues(data, serviceDescription);

        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(VNextBORODetailsPageValidations.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			System.out.println("description: " + VNextBORODetailsPageInteractions.getServiceDescription(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + VNextBORODetailsPageInteractions.getServiceLaborTime(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceLaborTime(serviceId), data.getServiceLaborTime(),
					"The service labor time is not equal to the inserted labor time value");

			System.out.println("price: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServicePrice(serviceId), data.getServiceLaborRate(),
					"The service labor rate is not equal to the inserted labor rate value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 32)
	public void verifyUserCanAddNewPartService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		final String selectedCategory = VNextBOAddNewServiceMonitorDialogSteps
                .getSubcategoryWhileAddingNewPartServiceMonitorValues(data, serviceDescription);
		System.out.println("*******************************************");
		System.out.println(selectedCategory);

		final String selectedAddPartsNumberBefore =
                VNextBOAddNewServiceMonitorDialogInteractions.getSelectedAddPartsNumber();
		if (!VNextBOAddNewServiceMonitorDialogValidations.arePartsOptionsDisplayed()) {
            VNextBOAddNewServiceMonitorDialogInteractions.selectRandomAddPartsOption();

			final String selectedAddPartsNumberAfter =
                    VNextBOAddNewServiceMonitorDialogInteractions.getSelectedAddPartsNumber();
			Assert.assertNotEquals(selectedAddPartsNumberBefore, selectedAddPartsNumberAfter);
			Assert.assertTrue(Integer.valueOf(selectedAddPartsNumberBefore) < Integer.valueOf(selectedAddPartsNumberAfter));

            VNextBOAddNewServiceMonitorDialogInteractions.clickSubmitButton();
			if (!VNextBOAddNewServiceMonitorDialogValidations.isPartDescriptionDisplayed(data.getServiceCategory()
                    + " -> " + selectedCategory)) {
				Utils.refreshPage();
			}

			Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations
							.isPartDescriptionDisplayed(data.getServiceCategory() + " -> " + selectedCategory),
					"The Part service description hasn't been displayed.");
		}
	}
}