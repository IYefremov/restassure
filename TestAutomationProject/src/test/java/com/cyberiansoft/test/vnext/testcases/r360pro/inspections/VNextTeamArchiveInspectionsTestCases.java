package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamArchiveInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Archive Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getArchiveInspectionsTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanArchiveCreatedInspection(String rowID,
                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspnumber = GeneralWizardInteractions.getObjectNumber();


        GeneralWizardInteractions.saveViaMenu();
        InspectionSteps.archiveInspection(inspnumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantArchiveTeamInspection(String rowID,
                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = GeneralWizardInteractions.getObjectNumber();

        GeneralWizardInteractions.saveViaMenu();
        inspectionscreen.switchToTeamInspectionsView();
        VNextInspectionsMenuScreen inspmenulist = inspectionscreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspmenulist.isApproveMenuPresent();

        WaitUtils.elementShouldBeVisible(inspmenulist.getArchiveinspectionbtn(), false);
        inspmenulist.clickCloseInspectionMenuButton();
        inspectionscreen.switchToMyInspectionsView();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanArchiveInspectionUsingSearch(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspnumber = GeneralWizardInteractions.getObjectNumber();

        GeneralWizardInteractions.saveViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
        inspectionscreen.selectInspection(inspnumber);
        inspectionscreen.clickMultiselectInspectionsArchiveButton();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogArchiveButton();

        Assert.assertTrue(inspectionscreen.waitUntilInspectionDisappears(inspnumber));
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCancelArchivingInspection(String rowID,
                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspnumber = GeneralWizardInteractions.getObjectNumber();
        GeneralWizardInteractions.saveViaMenu();

        VNextInspectionsMenuScreen inspmenulist = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
        inspmenulist.clickArchiveInspectionMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogDontArchiveButton();

        Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanArchiveSeveralInspections(String rowID,
                                                           String description, JSONObject testData) {

        final int INSP_TO_ARCHIVE = 3;
        List<String> inspNumbers = new ArrayList<>();

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        for (int i = 0; i < INSP_TO_ARCHIVE; i++) {
            VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
            customersscreen.switchToRetailMode();
            customersscreen.selectCustomer(testcustomer);
            VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
            insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
            VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
            HelpingScreenInteractions.dismissHelpingScreenIfPresent();
            VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
            inspNumbers.add(GeneralWizardInteractions.getObjectNumber());
            GeneralWizardInteractions.saveViaMenu();

        }
        inspectionscreen.clearSearchField();
        for (String inspNumber : inspNumbers) {
            inspectionscreen.selectInspection(inspNumber);
        }
        inspectionscreen.clickMultiselectInspectionsArchiveButton();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogArchiveButton();

        for (String inspNumber : inspNumbers) {

            Assert.assertTrue(inspectionscreen.waitUntilInspectionDisappears(inspNumber),
                    "Inspection: " + inspNumber + " still exists, but shouldn't");
        }
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyArchivedInspectionsDoesntDispalysOnTheListAfterDBUpdate(String rowID,
                                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspnumber = GeneralWizardInteractions.getObjectNumber();

        GeneralWizardInteractions.saveViaMenu();

        InspectionSteps.archiveInspection(inspnumber);
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyArchivedInspectionsDoesntDispalysOnTheTeamList(String rowID,
                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspnumber = GeneralWizardInteractions.getObjectNumber();

        GeneralWizardInteractions.saveViaMenu();

        InspectionSteps.archiveInspection(inspnumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
                " still exists, but shouldn't");
        inspectionscreen.clickBackButton();

    }
}
