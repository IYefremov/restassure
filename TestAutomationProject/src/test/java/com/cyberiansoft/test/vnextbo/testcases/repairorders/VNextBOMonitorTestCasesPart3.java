package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORONotesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOAddNewServiceMonitorDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORONotesPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOAddNewServiceMonitorDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class VNextBOMonitorTestCasesPart3 extends BaseTestCase {

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
	}

	@AfterMethod
	public void refreshPage() {
		Utils.refreshPage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItXIcon(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(),
                "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps
                .setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithXButton(data, serviceDescription);

        VNextBORODetailsPageInteractions.expandPhasesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		Assert.assertEquals(serviceId, "",
				"The service has been added after closing the 'New Service Dialog' with X button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItCancelButton(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps
                .setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithCancelButton(data, serviceDescription);

		VNextBORODetailsPageInteractions.expandPhasesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		Assert.assertEquals(serviceId, "",
				"The service has been added after closing the 'New Service Dialog' with X button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
	public void verifyUserCanChangeFlagOfRoToWhite(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
	public void verifyUserCanChangeFlagOfRoToRed(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
	public void verifyUserCanChangeFlagOfRoToOrange(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
	public void verifyUserCanChangeFlagOfRoToYellow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
	public void verifyUserCanChangeFlagOfRoToGreen(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
	public void verifyUserCanChangeFlagOfRoToBlue(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
	public void verifyUserCanChangeFlagOfRoToPurple(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
	public void verifyUserCanSeeServicesOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final List<String> fields = Arrays.asList(data.getServicesTableFields());
		fields.forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		VNextBORODetailsPageInteractions.getServicesTableHeaderValues().forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		Assert.assertTrue(VNextBORODetailsPageInteractions.getServicesTableHeaderValues()
						.containsAll(fields),
				"The services table header values have not been displayed properly");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
	public void verifyUserCanChangeVendorPriceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		System.out.println("Vendor price: " + VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId));
		VNextBORODetailsPageInteractions.setServiceVendorPrice(serviceId, data.getServiceVendorPrices()[0]);
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId), data.getServiceVendorPrices()[0],
				"The Vendor Price hasn't been changed");

		System.out.println("Vendor price: " + VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId));
		VNextBORODetailsPageInteractions.setServiceVendorPrice(serviceId, data.getServiceVendorPrices()[1]);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId), data.getServiceVendorPrices()[1],
				"The Vendor Price hasn't been changed");

		System.out.println("Vendor price: " + VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
	public void verifyUserCanChangeVendorTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
        VNextBORODetailsPageInteractions.setVendor(serviceId, data.getVendor());
        VNextBORODetailsPageInteractions.setTechnician(serviceId, data.getTechnician());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
	public void verifyUserCanCreateNoteOfServiceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        final int notesNumber = VNextBORONotesPageInteractions.getRepairNotesListNumber();

        VNextBORONotesPageSteps.setRONoteMessage(data.getNotesMessage());
		VNextBORONotesPageInteractions.clickRONoteSaveButton();

		Assert.assertTrue(VNextBONotesPageValidations.isEditOrderServiceNotesBlockDisplayed(), "The notes dialog hasn't been opened");
		Assert.assertEquals(notesNumber + 1, VNextBORONotesPageInteractions.getRepairNotesListNumber(),
				"The services notes list number is not updated");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
	public void verifyUserCanChangeTargetDateOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
        VNextBORODetailsPageInteractions.setVendor(serviceId, data.getVendor());
        VNextBORODetailsPageInteractions.setTechnician(serviceId, data.getTechnician());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
	public void verifyUserCanSeeMoreInformationOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.openMoreInformation();
		final List<String> infoFields = Arrays.asList(data.getInformationFields());
		System.out.println();
		infoFields.forEach(System.out::println);
		System.out.println();

		Assert.assertTrue(VNextBORODetailsPageInteractions
						.getMoreInformationFieldsText()
						.containsAll(infoFields),
				"The More Information fields haven't been fully displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
	public void verifyUserCanTypeAndNotSaveNoteOfRoService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        final int notesNumber = VNextBORONotesPageInteractions.getRepairNotesListNumber();

        VNextBORONotesPageSteps.setRONoteMessage(data.getNotesMessage());
        VNextBORONotesPageInteractions.clickRepairNotesXButton();

		Assert.assertEquals(VNextBORONotesPageInteractions.getRONoteTextAreaValue(), "");
        VNextBORONotesPageInteractions.closeRONoteDialog();

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        Assert.assertEquals(notesNumber, VNextBORONotesPageInteractions.getRepairNotesListNumber(),
				"The services notes list number has been updated, although the 'X' button was clicked");

        VNextBORONotesPageSteps.setRONoteMessage(data.getNotesMessage());
        VNextBORONotesPageInteractions.closeRONoteDialog();

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        Assert.assertEquals(notesNumber, VNextBORONotesPageInteractions.getRepairNotesListNumber(),
				"The services notes list number has been updated, although the 'X' button was clicked");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
	public void verifyUserCanChangeStatusOfRoServiceToActive(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");
		Assert.assertFalse(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date shouldn't be displayed with the Active status");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[0]));

		System.out.println("JSON: " + data.getServicePhase());
		System.out.println(VNextBORODetailsPageInteractions.getOrderCurrentPhase());

		Assert.assertEquals(VNextBORODetailsPageInteractions.getOrderCurrentPhase(), data.getServicePhase(),
				"The Current Phase is not displayed properly");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
	public void verifyUserCanChangeStatusOfRoServiceToCompleted(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Completed status");
		Assert.assertEquals(serviceCompletedDate, CustomDateProvider.getCurrentDateInFullFormat(true),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
	public void verifyUserCanChangeStatusOfRoServiceToAudited(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String status = data.getServiceStatuses()[1];
        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, status);
		WaitUtilsWebDriver.waitForLoading();
        VNextBORODetailsPageInteractions.waitForServiceStatusToBeChanged(serviceId, status);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), status);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);

		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Audited status");

        Assert.assertEquals(serviceCompletedDate, CustomDateProvider.getCurrentDateInFullFormat(true),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, status),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
	public void verifyUserCanChangeStatusOfRoServiceToRefused(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Refused status");
		Assert.assertEquals(serviceCompletedDate, CustomDateProvider.getCurrentDateInFullFormat(true),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
	public void verifyUserCanChangeStatusOfRoServiceToRework(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");
		Assert.assertFalse(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date shouldn't be displayed with the Rework status");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 21)
	public void verifyUserCanChangeStatusOfRoServiceToSkipped(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandPhasesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Refused status");
		Assert.assertEquals(serviceCompletedDate, CustomDateProvider.getCurrentDateInFullFormat(true),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}
}