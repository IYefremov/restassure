package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOAddLaborPartsDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAddLaborPartsDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOPMOrderDetailsPartsDetailsLaborTests extends BaseTestCase {

    int laborsAmountBeforeAdding;

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsPartsDetailsLaborTD();
        com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
        VNextBOSearchPanelSteps.searchByText("O-000-152414");
    }

    @AfterMethod
    public void refreshPage() {

        Utils.refreshPage();
        VNextBOSearchPanelSteps.clearSearchFilter();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        VNextBOSearchPanelSteps.searchByText("O-000-152414");
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserMinimizeMaximizeThePartTab(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborBlockIsDisplayed(0, true);
        laborsAmountBeforeAdding = VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborBlockIsDisplayed(0, false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCancelAddingLaborXIcon(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(true);
        VNextBOAddLaborPartsDialogSteps.closeDialogWithXIcon();
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanCancelAddingLaborCancelButton(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(true);
        VNextBOAddLaborPartsDialogSteps.closeDialogWithCancelButton();
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanTypeLaborServiceNameAddingLabor(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogSteps.populateLaborServiceField("Paint");
        VNextBOAddLaborPartsDialogValidations.verifyLaborServiceDropDownIsDisplayed();
        VNextBOAddLaborPartsDialogValidations.verifyClearLaborServiceFieldIconIsDisplayed();
        VNextBOAddLaborPartsDialogValidations.verifyLaborServiceFieldContainsCorrectValue("Paint");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanClearTypedNameOfLaborServiceClickingOnXIcon(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogSteps.populateLaborServiceField("Paint");
        VNextBOAddLaborPartsDialogSteps.clearLaborServiceField();
        VNextBOAddLaborPartsDialogValidations.verifyLaborServiceFieldContainsCorrectValue("");
        VNextBOAddLaborPartsDialogValidations.verifyLaborServiceDropDownIsDisplayed();
        VNextBOAddLaborPartsDialogSteps.closeDialogWithXIcon();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanAddLabor(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.addLaborForPartByNumberInList(0, "Labor AM");
        refreshPage();
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, laborsAmountBeforeAdding + 1);
        VNextBOPartsDetailsPanelValidations.verifyPartContainsLaborByPartNumberAndLaborServiceName(0, "Labor AM (01_New)");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanCancelDeletingLaborXIcon(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickDeleteLaborButtonForPartByNumberInListAndServiceName(0, "Labor AM (01_New)");
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPartsDetailsPanelValidations.verifyPartContainsLaborByPartNumberAndLaborServiceName(0, "Labor AM (01_New)");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanCancelDeletingLaborNoButton(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickDeleteLaborButtonForPartByNumberInListAndServiceName(0, "Labor AM (01_New)");
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPartsDetailsPanelValidations.verifyPartContainsLaborByPartNumberAndLaborServiceName(0, "Labor AM (01_New)");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanDeleteLabor(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.deleteLaborForPartByNumberInListANdLaborServiceName(0, "Labor AM (01_New)");
        refreshPage();
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, laborsAmountBeforeAdding);
    }
}