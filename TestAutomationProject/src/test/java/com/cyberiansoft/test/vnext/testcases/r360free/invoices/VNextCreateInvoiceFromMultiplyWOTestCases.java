package com.cyberiansoft.test.vnext.testcases.r360free.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InvoiceInfoScreenValidations;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNextCreateInvoiceFromMultiplyWOTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Create Invoice FromMultiply WO Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getCreateInvoiceFromMultipleWOsTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceFromMultiplyWO(String rowID,
												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();

        VNextHomeScreen homescreen = new VNextHomeScreen();
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.selectCustomer(testcustomer);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
            HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
            //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			//vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());

			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

        for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
        workOrders.forEach(workOrderId -> InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderId, true));
		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		ArrayList<String> woNumbers = invoicesScreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(woNumbers.size(), testCaseData.getWorkOrdersData().size());
		for (String woNumber : woNumbers)
			Assert.assertTrue(workOrders.contains(woNumber.trim()));
		ScreenNavigationSteps.pressBackButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateInvoiceWithMultipleWOAndDifferentCustomers(String rowID,
															 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();

        VNextHomeScreen homescreen = new VNextHomeScreen();
		homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			CustomerServiceSteps.createCustomerIfNotExist(workOrderData.getWorlOrderRetailCustomer());
		}
		ScreenNavigationSteps.pressBackButton();
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
            HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
            //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			//vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

        for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
        CustomersScreenSteps.selectCustomer(testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
        InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrders.get(0), true);
		InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrders.get(1),false);
		InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrders.get(2), false);
		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		ArrayList<String> woNumbers = invoicesScreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(woNumbers.size(), 1);
		for (String woNumber : woNumbers) {
			Assert.assertTrue(woNumber.contains(testCaseData.getWorkOrdersData().get(0).getVehicleInfoData().getVINNumber()));
		}
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateInvoiceWithAFewWO(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final int wosToCreate = 2;
		Map<String, List<String>> workOrdersMap = new HashMap<>();

        VNextHomeScreen homescreen = new VNextHomeScreen();
		homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			CustomerServiceSteps.createCustomerIfNotExist(workOrderData.getWorlOrderRetailCustomer());
		}
		ScreenNavigationSteps.pressBackButton();
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			for (int i = 0; i < wosToCreate; i++) {
				WorkOrderSteps.clickAddWorkOrderButton();
				CustomersScreenSteps.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
				VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
                HelpingScreenInteractions.dismissHelpingScreenIfPresent();
				VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
                //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
				//vehicleVINHistoryScreen.clickBackButton();
				workOrdersMap.computeIfAbsent(workOrderData.getWorlOrderRetailCustomer().getFullName(), k -> new ArrayList<>()).add(vehicleInfoScreen.getNewInspectionNumber());
				vehicleInfoScreen.saveWorkOrderViaMenu();
			}
		}

        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			for (int j = 0; j < wosToCreate; j++) {
				workordersscreen.selectWorkOrder(workOrdersMap.get(workOrderData.getWorlOrderRetailCustomer().getFullName()).get(j));
			}
		}
		workordersscreen.clickCreateInvoiceIcon();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
		BaseUtils.waitABit(20000);
		CustomersScreenSteps.selectCustomer(testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
        for (int j = 0; j < wosToCreate; j++) {
			InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrdersMap.get(
					testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer().getFullName()).get(j), true);
		}

		for (int i = 1; i < testCaseData.getWorkOrdersData().size(); i++) {
			for (int j = 0; j < wosToCreate; j++) {
				InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrdersMap.get(
						testCaseData.getWorkOrdersData().get(i).getWorlOrderRetailCustomer().getFullName()).get(j), false);
			}
		}

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		ArrayList<String> wonumbers = invoicesScreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), wosToCreate);
		ScreenNavigationSteps.pressBackButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCancelCreatingInvoiceWithMultiplyWOOnInfoScreen(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();


		HomeScreenSteps.openCustomers();

		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			CustomerServiceSteps.createCustomerIfNotExist(workOrderData.getWorlOrderRetailCustomer());
		}
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openWorkOrders();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
            HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
            //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			//vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

		VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        for (String woNumber : workOrders) {
			workOrdersScreen.selectWorkOrder(woNumber);
		}
		workOrdersScreen.clickCreateInvoiceIcon();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
		CustomersScreenSteps.selectCustomer(testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
        InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrders.get(0), true);
		InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrders.get(1), false);
		InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrders.get(2), false);
		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		ScreenNavigationSteps.pressBackButton();
        VNextInformationDialog informationdialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(informationdialog.clickInformationDialogNoButtonAndGetMessage(),
				VNextAlertMessages.CANCEL_CREATING_INVOICE);
		ScreenNavigationSteps.pressBackButton();
        informationdialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		Assert.assertEquals(informationdialog.clickInformationDialogYesButtonAndGetMessage(),
				VNextAlertMessages.CANCEL_CREATING_INVOICE);
        for (String woNumber : workOrders)
			workOrdersScreen.selectWorkOrder(woNumber);

		workOrdersScreen.clickCreateInvoiceIcon();
        informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountDisplaysForAllSelectedWOWithTheSameCustomer(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();

        VNextHomeScreen homescreen = new VNextHomeScreen();
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
            HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
            //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			//vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
			AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
			SelectedServicesScreenSteps.changeSelectedServicePrice(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
        workOrders.forEach(workOrderId -> InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderId, true));
		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		InvoiceInfoScreenValidations.validateInvoiceTotal(testCaseData.getInvoiceData().getInvoiceTotal());
		InvoiceSteps.saveInvoiceAsFinal();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountDisplaysForAllSelectedWOWithDifferentCustomers(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();


        VNextHomeScreen homescreen = new VNextHomeScreen();
		homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			CustomerServiceSteps.createCustomerIfNotExist(workOrderData.getWorlOrderRetailCustomer());
		}
		ScreenNavigationSteps.pressBackButton();
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
            HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
            //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			//vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
			SelectedServicesScreenSteps.changeSelectedServicePrice(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

        for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
		CustomersScreenSteps.selectCustomer(testCaseData.getWorkOrdersData().get(1).getWorlOrderRetailCustomer());

        for (int i = 1; i < testCaseData.getWorkOrdersData().size(); i++) {
			InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrders.get(i), true);
		}
		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		InvoiceInfoScreenValidations.validateInvoiceTotal(testCaseData.getInvoiceData().getInvoiceTotal());
		InvoiceSteps.saveInvoiceAsFinal();
		ScreenNavigationSteps.pressBackButton();
	}
} 