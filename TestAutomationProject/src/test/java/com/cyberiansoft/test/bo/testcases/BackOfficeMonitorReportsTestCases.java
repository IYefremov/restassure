package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BackOfficeMonitorReportsTestCases extends BaseTestCase {

	@Test(testName="Average Repair Time Report preconditions", description = "Average Repair Time Report preconditions")
	public void testOperationInvoiceSearch() {

        final String repairlocationname = "Time_Reports_01";
		final String approxrepairtime = "8.0";
		final String workingday = "Monday";
		final String starttime = "9:00 AM";
		final String finishtime = "5:00 PM";
		final String WOType = "01ZalexWO_tp";
		final String phaseNameStart = "Start";
		final String phaseNameOnHold = "On Hold";
		final String phaseType = "Pre-Repair";
		final String phaseTypeOnHold = "On Hold";
		final String transitionTime = "111.0";
		final String repairTime = "111.0";
        final String serviceNameVacuumCleaner = "AMoneyFlatFee_VacuumCleaner";
        final String serviceNameWashing = "AMoneyVehicleFF_Washing";
		final String vendorTeamName = "02_TimeRep_team";
		final String vendorTimeZone = "Pacific Standard Time";
		final String vendorDescription = "TF145";
		final String vendorTimeSheetType = "ClockIn / ClockOut";

        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backOfficeHeader
                .clickMonitorLink()
                .clickRepairLocationsLink()
		        .addNewRepairLocation(repairlocationname, approxrepairtime, workingday, starttime, finishtime, true)
                .makeSearchPanelVisible()
		        .setSearchLocation(repairlocationname)
		        .clickFindButton()
		        .addPhaseForRepairLocation(repairlocationname, phaseNameStart, phaseType, approxrepairtime, approxrepairtime, true)
		        .addPhaseForRepairLocation(repairlocationname, phaseNameOnHold, phaseTypeOnHold, transitionTime, repairTime, false)
                .assignServiceForRepairLocation(repairlocationname, WOType, serviceNameVacuumCleaner, phaseNameOnHold)
		        .assignServiceForRepairLocation(repairlocationname, WOType, serviceNameWashing, phaseNameStart);

        MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
        VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
        vendorsteamspage.createNewVendorTeam(vendorTeamName, vendorTimeZone, vendorDescription, vendorTimeSheetType, repairlocationname, repairlocationname);
	}
	
	@Test(testName="Test Case 25488:Monitor- Reports - Average Repair Time Report (Detailed automation - Part 1)",
            description = "Monitor- Reports - Average Repair Time Report (Detailed automation - Part 1)")
	public void testMonitorReportsAverageRepairTimeReport_Part1() throws Exception {
		
		final String addsrvalue = "01_Alex2SRT";
		final String VIN = "WAUBC044XMN771407";
		final String _make = "Audi";
		final String _model = "100";
		final String newservicerequest= "SASHAZ";
		final String searchLocation= "Time_Reports_01";

        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationsPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		String wonumber = servicerequestslistpage.getWOForFirstServiceRequestFromList();
		
		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(searchLocation);
		repairorderspage.setSearchWoNumber(wonumber);
		repairorderspage.clickFindButton();
		
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(wonumber));
		
		monitorpage = backOfficeHeader.clickMonitorLink();
		AverageRepairTimeReportWebPage averagerepairtimereportpage = monitorpage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		averagerepairtimereportpage.selectSearchLocation(searchLocation);
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d/MM/yyyy");

		 LocalDateTime now = LocalDateTime.now(ZoneId.of("US/Pacific"));
		 LocalDateTime then = now.minusDays(7);
		 LocalDateTime after = now.plusDays(1);
		 averagerepairtimereportpage.setSearchFromDate(then.format(format));
		 averagerepairtimereportpage.setSearchToDate(after.format(format));
		        
		averagerepairtimereportpage.clickFindButton();
		//Assert.assertFalse(averagerepairtimereportpage.isLocationResultsPresent("Time_Reports_01"));
		
		PrintWriter writer = new PrintWriter("data/averagerpairtimewonubers.txt", "UTF-8");
		writer.println(wonumber);
		writer.close();
	}
	
	@Test(testName="Test Case 25524:Monitor- Reports - Average Repair Time Report (Detailed automation - Part 2)", description = "Monitor- Reports - Average Repair Time Report (Detailed automation - Part 2)")
	public void testMonitorReportsAverageRepairTimeReport_Part2() {
		
		final String VIN = "WAUBC044XMN771407";
		final String _make = "Audi";
		final String _model = "100";
		final String _year = "1991";
        final String searchLocation= "Time_Reports_01";
        final String woType= "01ZalexWO_tp";

		/*BufferedReader in = new BufferedReader(new FileReader("data/averagerpairtimewonubers.txt"));
		String wonumber = in.readLine();

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);

		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation("Time_Reports_01");
		repairorderspage.setSearchWoNumber(wonumber);
		repairorderspage.clickFindButton();

		repairorderspage.verifyTableOderTypeColumnValuesAreVisible(wonumber);
		VendorOrderServicesWebPage vendorordersservicespage = repairorderspage.clickOnWorkOrderLinkInTable(wonumber);
		vendorordersservicespage.setStartPhaseStatus("Completed");
		vendorordersservicespage.setServicesStatus("Completed");
		repairorderspage = vendorordersservicespage.clickBackToROLink();*/

        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		AverageRepairTimeReportWebPage averagerepairtimereportpage = monitorpage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		averagerepairtimereportpage.selectSearchLocation(searchLocation);
		averagerepairtimereportpage.selectSearchWOType(woType);
		
		DateTimeFormatter formatting = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		LocalDateTime then = LocalDateTime.of(2015, 1, 3, 12, 12);
		LocalDateTime after = LocalDateTime.now(ZoneId.of("US/Pacific")).plusDays(7);
        averagerepairtimereportpage.setSearchFromDate(then.format(formatting));
		averagerepairtimereportpage.setSearchToDate(after.format(formatting));
		        
		averagerepairtimereportpage.clickFindButton();
		averagerepairtimereportpage.verifySearchResults(searchLocation, woType);

        averagerepairtimereportpage.checkShowDetails();
		averagerepairtimereportpage.clickFindButton();
		//todo uncomment if the data appears: NO VIN, _make, _model, _year displayed.
//		averagerepairtimereportpage.verifyDetailReportSearchResults("Time_Reports_01", "01ZalexWO_tp", VIN, _make, _model, _year);
	}
	
	@Test(testName="Test Case 25578:Monitor- Reports - Repair Location Time Tracking(Detailed automation - Part 1)", description = "Monitor- Reports - Repair Location Time Tracking(Detailed automation - Part 1)")
	public void testMonitorReportsRepairLocationTimeTracking_Part1() throws Exception {

		final String addsrvalue = "01_Alex2SRT";
		final String VIN = "WAUBC044XMN771407";
		final String _make = "Audi";
		final String _model = "100";
		final String newservicerequest= "SASHAZ";
        final String searchLocation= "Time_Reports_01";

        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operatonspage = backOfficeHeader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operatonspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue(addsrvalue);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(newservicerequest);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.setSearchFreeText(VIN);
		servicerequestslistpage.clickFindButton();
		String wonumber = servicerequestslistpage.getWOForFirstServiceRequestFromList();
		
		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(searchLocation);
		repairorderspage.setSearchWoNumber(wonumber);
		repairorderspage.clickFindButton();
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(wonumber));
		
		monitorpage = backOfficeHeader.clickMonitorLink();
		AverageRepairTimeReportWebPage averagerepairtimereportpage = monitorpage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		averagerepairtimereportpage.selectSearchLocation(searchLocation);
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d/MM/yyyy");

		LocalDateTime now = LocalDateTime.now(ZoneId.of("US/Pacific"));
		LocalDateTime then = now.minusDays(7);
		LocalDateTime after = now.plusDays(1);
		averagerepairtimereportpage.setSearchFromDate(then.format(format));
		averagerepairtimereportpage.setSearchToDate(after.format(format));
		        
		averagerepairtimereportpage.clickFindButton();
		//Assert.assertFalse(averagerepairtimereportpage.isLocationResultsPresent("Time_Reports_01"));
		
		PrintWriter writer = new PrintWriter("data/repairlocationtimetrackingwonubers.txt", "UTF-8");
		writer.println(wonumber);
		writer.close();
	}

	@Test(testName="Test Case 25579:Monitor- Reports - Repair Location Time Tracking (Detailed automation - Part 2)", description = "Monitor- Reports - Repair Location Time Tracking (Detailed automation - Part 2)")
	public void testMonitorReportsRepairLocationTimeTracking_Part2() {
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		String wonumber = "O-000-152054";
        final String searchLocation= "Time_Reports_01";
        final String searchTimeFrame= "Custom";
        final String servicesStatus= "Completed";

		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
        repairorderspage.makeSearchPanelVisible();
        repairorderspage.selectSearchLocation(searchLocation);
        repairorderspage.selectSearchTimeframe(searchTimeFrame);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("US/Pacific"));
        LocalDateTime then = LocalDateTime.of(2018, 4, 17, 12, 12);
        LocalDateTime after = now.plusDays(7);
        repairorderspage.setSearchFromDate(then.format(format));
        repairorderspage.setSearchToDate(after.format(format));

        repairorderspage.setSearchWoNumber(wonumber);
        repairorderspage.clickFindButton();
        Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(wonumber));

        VendorOrderServicesWebPage vendorordersservicespage = repairorderspage.clickOnWorkOrderLinkInTable(wonumber);
        vendorordersservicespage.setServicesStatus(servicesStatus);
        vendorordersservicespage.clickBackToROLink();

//        monitorpage = backOfficeHeader.clickMonitorLink();
//        RepairLocationTimeTrackingWebPage repairlocationtimetrackingpage = monitorpage.clickVehicleTimeTrackingLink();
//        repairlocationtimetrackingpage.makeSearchPanelVisible();
//        repairlocationtimetrackingpage.selectSearchLocation("Time_Reports_01");
//
//		repairlocationtimetrackingpage.setSearchFromDate(then.format(format));
//		repairlocationtimetrackingpage.setSearchToDate(after.format(format));
//
//		repairlocationtimetrackingpage.clickFindButton();
//
//		Assert.assertTrue(repairlocationtimetrackingpage.searchWorkOrderInTable(wonumber));
	}
}