package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextErrorDialog;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextGroupServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedGroupServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Work Orders Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanDeleteWOIfAllowDeleteON(String rowID,
                                                         String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String workOrderNumber = WorkOrderSteps.createSimpleWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO, workOrderData);
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.DELETE);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogDontDeleteButton();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        WorkOrderSteps.deleteWorkOrder(workOrderNumber);
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, false);
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openStatus();
        StatusScreenSteps.updateMainDB();
        HomeScreenSteps.openWorkOrders();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, false);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantDeleteWOIfAllowDeleteOFF(String rowID,
                                                           String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        homeScreen.clickNewWorkOrderPopupMenu();

        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO2, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextGroupServicesScreen groupServicesScreen = new VNextGroupServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        groupServicesScreen.openServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        workOrderData.getDamageData().getMoneyServices().forEach(serviceData -> AvailableServicesScreenSteps.selectService(serviceData));
        ScreenNavigationSteps.pressBackButton();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        ClaimInfoSteps.selectInsuranceCompany(workOrderData.getInsuranceCompanyData().getInsuranceCompanyName());
        ClaimInfoSteps.setClaimNumber(workOrderData.getInsuranceCompanyData().getClaimNumber());
        ClaimInfoSteps.setPolicyNumber(workOrderData.getInsuranceCompanyData().getPolicyNumber());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        WorkOrderSteps.openMenu(workOrderNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.DELETE, false);
        MenuSteps.closeMenu();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTeamWODisplaysInMyWOsListIfWOWasCreatedFromTeamInspection(String rowID,
                                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        final String searchtext = "E-357-00295";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        SearchSteps.searchByText(searchtext);
        BaseUtils.waitABit(5000);
        InspectionSteps.openInspectionMenu(searchtext);
        MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.KRAMAR_AUTO, workOrderData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);

        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateWOFromTeamInspection(String rowID,
                                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2);
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.switchToTeamInspections();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        InspectionsValidations.verifyInspectionExists(inspectionNumber, true);

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);

        WorkOrderSteps.createWorkOrder(WorkOrderTypes.KRAMAR_AUTO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        workOrderData.getServicesList().forEach(serviceData -> {
            AvailableServicesScreenSteps.openServiceDetails(serviceData);
            ServiceDetailsScreenSteps.saveServiceDetails();
        });
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);

        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        WorkOrderSteps.switchToMyWorkOrdersView();

        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.switchToSelectedService();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ListServicesValidations.verifyServiceSelected(serviceData.getServiceName(), true);
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateWOOnlyFromApprovedTeamInspection(String rowID,
                                                                        String description, JSONObject testData) {

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickOnFirstInspectionWithStatus(InspectionStatus.NEW.getStatusString());
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, false);
        MenuSteps.closeMenu();

        inspectionsScreen.clickOnFirstInspectionWithStatus(InspectionStatus.APPROVED.getStatusString());
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, true);
        MenuSteps.closeMenu();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAllSelectedServicesFromInspectionDisplaysWhenUserCreateWO(String rowID,
                                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        ClaimInfoSteps.selectInsuranceCompany(workOrderData.getInsuranceCompanyData().getInsuranceCompanyName());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        workOrderData.getServicesList().forEach(serviceData -> {
            AvailableServicesScreenSteps.openServiceDetails(serviceData);
            ServiceDetailsScreenSteps.saveServiceDetails();
        });
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        inspectionsScreen.waitForInspectionsListIsVisibile();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.KRAMAR_AUTO);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.switchToSelectedService();
        workOrderData.getServicesList().forEach(serviceData -> ListServicesValidations.verifyServiceSelected(serviceData.getServiceName(), true));
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        ScreenNavigationSteps.pressBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanDeleteWOIfThisWOWasCreatedFromTeamInspectionScreen(String rowID,
                                                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.KRAMAR_AUTO);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, true);
        WorkOrderSteps.switchToMyWorkOrdersView();
        WorkOrderSteps.deleteWorkOrder(workOrderNumber);
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, false);

        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderNumber, false);
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyItIsNotPossibleToEditTeamWorkOrderWithDeviceOnFlyMode(String rowID,
                                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        homeScreen.clickNewWorkOrderPopupMenu();

        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO, workOrderData);
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();

        WorkOrderSteps.switchToTeamWorkOrdersView();
        SearchSteps.searchByText(workOrderNumber);
        BaseUtils.waitABit(10 * 1000);
        AppiumUtils.setNetworkOff();
        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextErrorDialog errorDialog = new VNextErrorDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(errorDialog.clickOKButtonAndGetMessage(),
                VNextAlertMessages.CONNECTION_IS_NOT_AVAILABLE);
        AppiumUtils.setAndroidNetworkOn();
        GeneralSteps.closeErrorDialog();
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWorkOrder(String rowID,
                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String newvinnumber = "19UUA66278A050105";

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.switchToTeamWorkOrdersView();

        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, newvinnumber);
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(10 * 1000);
        HomeScreenSteps.openWorkOrders();
        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.VIN, newvinnumber);
        WorkOrderSteps.cancelWorkOrder();
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWorkOrderAddAndRemoveServices(String rowID,
                                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        final int amountToSelect = 3;
        final int defaultCountForMoneyService = 1;

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.switchToTeamWorkOrdersView();

        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        for (int i = 0; i < amountToSelect; i++)
            AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        ListServicesValidations.validateAvailableServiceCount(workOrderData.getMoneyServiceData().getServiceName(), amountToSelect);
        for (int i = 0; i < amountToSelect; i++)
            AvailableServicesScreenSteps.selectService(workOrderData.getPercentageServiceData());
        ListServicesValidations.validateAvailableServiceCount(workOrderData.getPercentageServiceData().getServiceName(), amountToSelect);
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenValidations.validateNumberOfSelectedServices(workOrderData.getMoneyServiceData().getServiceName(), amountToSelect);
        SelectedServicesScreenValidations.validateNumberOfSelectedServices(workOrderData.getPercentageServiceData().getServiceName(), amountToSelect);

        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(10 * 1000);
        HomeScreenSteps.openWorkOrders();
        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        ListServicesValidations.validateAvailableServiceCount(workOrderData.getMoneyServiceData().getServiceName(), amountToSelect);
        ListServicesValidations.validateAvailableServiceCount(workOrderData.getPercentageServiceData().getServiceName(), amountToSelect);
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenValidations.validateNumberOfSelectedServices(workOrderData.getMoneyServiceData().getServiceName(), amountToSelect);
        SelectedServicesScreenValidations.validateNumberOfSelectedServices(workOrderData.getPercentageServiceData().getServiceName(), amountToSelect);
        SelectedServicesScreenSteps.unSelectService(workOrderData.getPercentageServiceData().getServiceName());
        SelectedServicesScreenSteps.unSelectService(workOrderData.getPercentageServiceData().getServiceName());
        SelectedServicesScreenSteps.unSelectService(workOrderData.getMoneyServiceData().getServiceName());
        SelectedServicesScreenValidations.validateNumberOfSelectedServices(workOrderData.getPercentageServiceData().getServiceName(), defaultCountForMoneyService);
        AvailableServicesScreenSteps.switchToAvailableServices();
        ListServicesValidations.validateAvailableServiceCount(workOrderData.getMoneyServiceData().getServiceName(), 2);
        ListServicesValidations.validateAvailableServiceCount(workOrderData.getPercentageServiceData().getServiceName(), defaultCountForMoneyService);
        AvailableServicesScreenSteps.selectService(workOrderData.getPercentageServiceData());
        AvailableServicesScreenSteps.selectService(workOrderData.getPercentageServiceData());
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());

        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenValidations.validateNumberOfSelectedServices(workOrderData.getMoneyServiceData().getServiceName(), amountToSelect);
        SelectedServicesScreenValidations.validateNumberOfSelectedServices(workOrderData.getPercentageServiceData().getServiceName(), amountToSelect);
        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyItIsPossibleToAddServiceByOpeningServiceDetailsScreenAndSave(String rowID,
                                                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(workOrderData.getMoneyServiceData().getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(workOrderData.getMoneyServiceData().getServiceQuantity());
        ServiceDetailsScreenSteps.saveServiceDetails();
        AvailableServicesScreenSteps.openServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(workOrderData.getPercentageServiceData().getServicePrice());
        ServiceDetailsScreenSteps.saveServiceDetails();
        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), workOrderData.getWorkOrderPrice());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        BaseUtils.waitABit(10 * 1000);
        ScreenNavigationSteps.pressBackButton();
        HomeScreenSteps.openWorkOrders();
        WorkOrdersScreenValidations.validateWorkOrderPriceValue(workOrderNumber, workOrderData.getWorkOrderPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanUnselectServiceForCreatedWO(String rowID,
                                                             String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String amountTotalEdited = "$26.26";

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_3_SERVICE_GROUPING, workOrderData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextGroupServicesScreen groupServicesScreen = new VNextGroupServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (DamageData damageData : workOrderData.getDamagesData()) {
            groupServicesScreen.openServiceGroup(damageData.getDamageGroupName());
            for (ServiceData serviceData : damageData.getMoneyServices()) {
                AvailableServicesScreenSteps.selectService(serviceData);
            }

            ScreenNavigationSteps.pressBackButton();
        }
        Assert.assertEquals(groupServicesScreen.getInspectionTotalPriceValue(), workOrderData.getWorkOrderPrice());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextSelectedGroupServicesScreen selectedGroupServicesScreen = groupServicesScreen.switchToSelectedGroupServicesView();
        selectedGroupServicesScreen.uselectService(workOrderData.getDamagesData().get(0).getMoneyServices().get(0).getServiceName());
        selectedGroupServicesScreen.switchToGroupServicesScreen();
        Assert.assertEquals(groupServicesScreen.getInspectionTotalPriceValue(), amountTotalEdited);
        groupServicesScreen.switchToSelectedGroupServicesView();
        Assert.assertEquals(selectedGroupServicesScreen.getInspectionTotalPriceValue(), amountTotalEdited);
        selectedGroupServicesScreen.switchToGroupServicesScreen();
        WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderPriceValue(workOrderNumber, amountTotalEdited);
        ScreenNavigationSteps.pressBackButton();
    }
}
