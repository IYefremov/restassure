package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamArchiveInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-archive-inspections-testcases-data.json";

    @BeforeClass(description = "Team Archive Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanArchiveCreatedInspection(String rowID,
                                                                                         String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();

        inspectionscreen = inspectionscreen.archiveInspection(inspnumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantArchiveTeamInspection(String rowID,
                                                          String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspectionNumber = vehicleinfoscreen.getNewInspectionNumber();

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.switchToTeamInspectionsView();
        VNextInspectionsMenuScreen inspmenulist = inspectionscreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspmenulist.isApproveMenuPresent();

        Assert.assertFalse(inspmenulist.isArchivwMenuPresent());
        inspmenulist.clickCloseInspectionMenuButton();
        inspectionscreen.switchToMyInspectionsView();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanArchiveInspectionUsingSearch(String rowID,
                                                          String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
        inspectionscreen.selectInspection(inspnumber);
        inspectionscreen.clickMultiselectInspectionsArchiveButton();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        informationdlg.clickInformationDialogArchiveButton();

        Assert.assertTrue(inspectionscreen.waitUntilInspectionDisappears(inspnumber));
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelArchivingInspection(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();

        VNextInspectionsMenuScreen inspmenulist = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
        inspmenulist.clickArchiveInspectionMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        informationdlg.clickInformationDialogDontArchiveButton();

        Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanArchiveSeveralInspections(String rowID,
                                                          String description, JSONObject testData) {

        final int INSP_TO_ARCHIVE = 3;
        List<String> inspNumbers = new ArrayList<>();

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        for (int i = 0; i < INSP_TO_ARCHIVE; i++) {
            VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
            customersscreen.switchToRetailMode();
            customersscreen.selectCustomer(testcustomer);
            VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
            insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
            VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
            vehicleinfoscreen.setVIN(inspdata.getVinNumber());
            inspNumbers.add(vehicleinfoscreen.getNewInspectionNumber());

            vehicleinfoscreen.saveInspectionViaMenu();
        }

        for (String inspNumber : inspNumbers) {
            inspectionscreen.selectInspection(inspNumber);
        }
        inspectionscreen.clickMultiselectInspectionsArchiveButton();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        informationdlg.clickInformationDialogArchiveButton();

        for (String inspNumber : inspNumbers) {

            Assert.assertTrue(inspectionscreen.waitUntilInspectionDisappears(inspNumber),
                    "Inspection: " + inspNumber + " still exists, but shouldn't");
        }
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyArchivedInspectionsDoesntDispalysOnTheListAfterDBUpdate(String rowID,
                                                          String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();

        inspectionscreen = inspectionscreen.archiveInspection(inspnumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.clickBackButton();

        VNextStatusScreen statusScreen = homescreen.clickStatusMenuItem();
        statusScreen.updateMainDB();
        inspectionscreen = homescreen.clickInspectionsMenuItem();
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyArchivedInspectionsDoesntDispalysOnTheTeamList(String rowID,
                                                                                  String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();

        inspectionscreen = inspectionscreen.archiveInspection(inspnumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.clickBackButton();

    }
}
