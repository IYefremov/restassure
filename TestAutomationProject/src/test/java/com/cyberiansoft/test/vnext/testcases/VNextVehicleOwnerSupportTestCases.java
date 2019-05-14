package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextVehicleOwnerSupportTestCases extends BaseTestCaseTeamEditionRegistration {

    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description="Vehicle Owner Support Cases")
    public void beforeClass() {
        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        customersscreen.switchToRetailMode();
        if (!customersscreen.isCustomerExists(testcustomer2)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer2);
            customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        }
        customersscreen.clickBackButton();
    }

    @Test(testName= "Test Case 78390:Verify user can create new customer when select retail owner (Inspection)",
            description = "Verify user can create new customer when select retail owner (Inspection)")
    public void testVerifyUserCanCreateNewCustomerWhenSelectRetailOwner_Inspection() {

        final String vinnumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        VNextCustomersScreen customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        VNextNewCustomerScreen nextNewCustomerScreen = customersScreen.clickAddCustomerButton();
        final String customerFirstName = StringUtils.capitalize(GlobalUtils.getUUID());
        final String customerLastName = StringUtils.capitalize(GlobalUtils.getUUID());
        nextNewCustomerScreen.createNewCustomer(new RetailCustomer(customerFirstName, customerLastName));
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), customerFirstName + " " + customerLastName);
        vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName= "Test Case 78391:Verify user can select created retail customer when assign owner",
            description = "Verify user can select created retail customer when assign owner")
    public void testVerifyUserCanSelectCreatedRetailCustomerWhenAssignOwner() {

        final String vinnumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        VNextCustomersScreen customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer2.getFullName());
        vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName= "Test Case 78392:Verify user can change retail owner to wholesale",
            description = "Verify user can change retail owner to wholesale")
    public void testVerifyUserCanChangeRetailOwnerToWholesale() {

        final String vinnumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        VNextCustomersScreen customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer2.getFullName());

        customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testwholesailcustomer.getFullName());
        vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName= "Test Case 78393:Verify user can change wholesale owner to retail",
            description = "Verify user can change wholesale owner to retail")
    public void testVerifyUserCanChangeWholesaleOwnerToRetail() {

        final String vinnumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        VNextCustomersScreen customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testwholesailcustomer.getFullName());

        customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer2.getFullName());
        vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName= "Test Case 78394:Verify assigned owner for Inspection displays as selected when user create WO from this Inspection",
            description = "Verify assigned owner for Inspection displays as selected when user create WO from this Inspection")
    public void testVerifyAssignedOwnerForInspectionDisplaysAsSelectedWhenUserCreateWOFromThisInspection() {

        final String vinnumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        VNextCustomersScreen customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer2.getFullName());
        vehicleinfoscreen.saveInspectionViaMenu();

        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickApproveInspectionMenuItem();
        VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        approveScreen.drawSignature();
        approveScreen.saveApprovedInspection();
        inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer2.getFullName());
        vehicleinfoscreen.saveWorkOrderViaMenu();

        inspectionscreen.clickBackButton();
    }

    @Test(testName= "Test Case 78395:Verify user can change owner when create WO from Inspection",
            description = "Verify user can change owner when create WO from Inspection")
    public void testVerifyUserCanChangeOwnerWhenCreateWOFromInspection() {

        final String vinnumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        VNextCustomersScreen customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer2.getFullName());
        vehicleinfoscreen.saveInspectionViaMenu();

        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickApproveInspectionMenuItem();
        VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        approveScreen.drawSignature();
        approveScreen.saveApprovedInspection();
        inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer2.getFullName());
        customersScreen = vehicleinfoscreen.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleinfoscreen.getOwnerCellValue(), testcustomer.getFullName());

        vehicleinfoscreen.saveWorkOrderViaMenu();

        inspectionscreen.clickBackButton();
    }
}
