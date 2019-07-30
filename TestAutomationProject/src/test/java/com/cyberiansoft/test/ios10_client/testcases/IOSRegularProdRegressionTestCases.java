package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.UATInspectionTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.UATWorkOrderTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSRegularProdRegressionTestCases extends ReconProBaseTestCase {

    @BeforeClass
    public void setUpSuite() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getProdRegressionSuiteTestCasesDataPath();
        mobilePlatform = MobilePlatform.IOS_REGULAR;
        initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
                ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "oksi opr",
                envType);

        RegularMainScreen mainscr = new RegularMainScreen();
        RegularMainScreenSteps.userLogin("Oksana Manager", iOSInternalProjectConstants.USER_PASSWORD);
        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreenSteps.setShowAvailableSelectedServicesOn();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInspectionVerifyOnBO(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Amazimg Nissan");


        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreenSteps.switchToWholesaleMode();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(wholesailCustomer, UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionID = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.selectBundleService(inspectionData.getServicesScreen().getBundleService());
        RegularServicesScreenSteps.selectLaborServiceAndSetData(inspectionData.getServicesScreen().getLaborService());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularNavigationSteps.swipeScreenLeft();
        RegularPriceMatrixScreenSteps.selectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
        RegularVehiclePartsScreenValidations.verifyVehiclePartScreenSubTotalValue(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartTotalPrice());
        RegularVehiclePartsScreenSteps.saveVehiclePart();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(inspectionData.getPriceMatrixScreenData().getMatrixScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(inspectionData.getPriceMatrixScreenData().getMatrixScreenTotalPrice());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(inspectionData.getServicesScreen().getScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(inspectionData.getServicesScreen().getScreenTotalPrice());
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsScreenValidations.verifyInspectionTotalPrice(inspectionID, inspectionData.getInspectionTotalPrice());

        RegularMyInspectionsSteps.selectInspectionForApprovaViaAction(inspectionID);
        RegularApproveInspectionScreenActions.clickApproveAllServicessButton();
        RegularApproveInspectionScreenActions.saveApprovedServices();
        RegularApproveInspectionScreenActions.clickSingnAndDrawSignature();

        RegularMyInspectionsScreenValidations.verifyInspectionTotalPrice(inspectionID, inspectionData.getInspectionTotalPrice());
        RegularMyInspectionsScreenValidations.verifyInspectionApprovedPrice(inspectionID, inspectionData.getInspectionTotalPrice());

        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionID, UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelected(serviceData.getServiceName(), true);
        }
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getBundleService().getBundleServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getLaborService().getServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName(), false);

        RegularWizardScreenValidations.verifyScreenSubTotalPrice(testCaseData.getWorkOrderData().getServicesScreen().getScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(testCaseData.getWorkOrderData().getServicesScreen().getScreenTotalPrice());

        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, testCaseData.getWorkOrderData().getWorkOrderPrice());

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateRetailCustomer(String rowID,
                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreenSteps.switchToRetailMode();
        RegularCustomersScreenSteps.addNewRetailCustomer(inspectionData.getInspectionRetailCustomer());
        RegularCustomersScreenValidations.validateCustomerExists(inspectionData.getInspectionRetailCustomer());
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        CompanyWebPage companyWebPage = backOfficeHeaderPanel.clickCompanyLink();
        ClientsWebPage clientsWebPage = companyWebPage.clickClientsLink();

        clientsWebPage.deleteUserViaSearch(inspectionData.getInspectionRetailCustomer().getFullName());

        DriverBuilder.getInstance().getDriver().quit();
    }
}
