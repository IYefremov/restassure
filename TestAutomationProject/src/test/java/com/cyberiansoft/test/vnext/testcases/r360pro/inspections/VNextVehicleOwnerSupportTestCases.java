package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextVehicleOwnerSupportTestCases extends BaseTestCaseTeamEditionRegistration {

    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Vehicle Owner Support Cases")
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

    @Test(testName = "Test Case 78390:Verify user can create new customer when select retail owner (Inspection)",
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinnumber);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        VNextNewCustomerScreen nextNewCustomerScreen = customersScreen.clickAddCustomerButton();
        final String customerFirstName = StringUtils.capitalize(GlobalUtils.getUUID());
        final String customerLastName = StringUtils.capitalize(GlobalUtils.getUUID());
        nextNewCustomerScreen.createNewCustomer(new RetailCustomer(customerFirstName, customerLastName));
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();


        VehicleInfoScreenValidations.cutomerContextShouldBe(customerFirstName + " " + customerLastName);
        vehicleInfoScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName = "Test Case 78391:Verify user can select created retail customer when assign owner",
            description = "Verify user can select created retail customer when assign owner")
    public void testVerifyUserCanSelectCreatedRetailCustomerWhenAssignOwner() {

        final String vinNumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinNumber);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer2.getFullName());
        vehicleInfoScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName = "Test Case 78392:Verify user can change retail owner to wholesale",
            description = "Verify user can change retail owner to wholesale")
    public void testVerifyUserCanChangeRetailOwnerToWholesale() {

        final String vinNumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinNumber);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer2.getFullName());
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VehicleInfoScreenValidations.cutomerContextShouldBe(testwholesailcustomer.getFullName());
        vehicleInfoScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName = "Test Case 78393:Verify user can change wholesale owner to retail",
            description = "Verify user can change wholesale owner to retail")
    public void testVerifyUserCanChangeWholesaleOwnerToRetail() {

        final String vinNumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinNumber);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VehicleInfoScreenValidations.cutomerContextShouldBe(testwholesailcustomer.getFullName());

        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer2.getFullName());
        vehicleInfoScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(testName = "Test Case 78394:Verify assigned owner for Inspection displays as selected when user create WO from this Inspection",
            description = "Verify assigned owner for Inspection displays as selected when user create WO from this Inspection")
    public void testVerifyAssignedOwnerForInspectionDisplaysAsSelectedWhenUserCreateWOFromThisInspection() {

        final String vinNumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinNumber);
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer2.getFullName());
        vehicleInfoScreen.saveInspectionViaMenu();

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
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer2.getFullName());
        vehicleInfoScreen.saveWorkOrderViaMenu();

        inspectionscreen.clickBackButton();
    }

    @Test(testName = "Test Case 78395:Verify user can change owner when create WO from Inspection",
            description = "Verify user can change owner when create WO from Inspection")
    public void testVerifyUserCanChangeOwnerWhenCreateWOFromInspection() {

        final String vinNumber = "TEST";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinNumber);
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer2.getFullName());
        vehicleInfoScreen.saveInspectionViaMenu();

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
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer2.getFullName());
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(testcustomer.getFullName());

        vehicleInfoScreen.saveWorkOrderViaMenu();

        inspectionscreen.clickBackButton();
    }
}
