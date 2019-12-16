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

public class VNextBOPMOrderDetailsPartsDetailsLabourTests extends BaseTestCase {

    int laboursAmountBeforeAdding;

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsPartsDetailsLabourTD();
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
        VNextBOPartsDetailsPanelValidations.verifyLabourBlockIsDisplayed(0, true);
        laboursAmountBeforeAdding = VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLabourBlockIsDisplayed(0, false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCancelAddingLabourXIcon(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(true);
        VNextBOAddLaborPartsDialogSteps.closeDialogWithXIcon();
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanCancelAddingLabourCancelButton(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(true);
        VNextBOAddLaborPartsDialogSteps.closeDialogWithCancelButton();
        VNextBOAddLaborPartsDialogValidations.verifyDialogIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanTypeLabourServiceNameAddingLabour(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogSteps.populateLaborServiceField("Paint");
        VNextBOAddLaborPartsDialogValidations.verifyLabourServiceDropDownIsDisplayed();
        VNextBOAddLaborPartsDialogValidations.verifyClearLabourServiceFieldIconIsDisplayed();
        VNextBOAddLaborPartsDialogValidations.verifyLabourServiceFieldContainsCorrectValue("Paint");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanClearTypedNameOfLabourServiceClickingOnXIcon(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickAddLaborButtonForPartByNumberInList(0);
        VNextBOAddLaborPartsDialogSteps.populateLaborServiceField("Paint");
        VNextBOAddLaborPartsDialogSteps.clearLabourServiceField();
        VNextBOAddLaborPartsDialogValidations.verifyLabourServiceFieldContainsCorrectValue("");
        VNextBOAddLaborPartsDialogValidations.verifyLabourServiceDropDownIsDisplayed();
        VNextBOAddLaborPartsDialogSteps.closeDialogWithXIcon();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanAddLabour(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.addLaborForPartByNumberInList(0, "Labor AM");
        refreshPage();
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, laboursAmountBeforeAdding + 1);
        VNextBOPartsDetailsPanelValidations.verifyPartContainsLabourByPartNumberAndLabourServiceName(0, "Labor AM (01_New)");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanCancelDeletingLabourXIcon(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickDeleteLaborButtonForPartByNumberInListAndServiceName(0, "Labor AM (01_New)");
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPartsDetailsPanelValidations.verifyPartContainsLabourByPartNumberAndLabourServiceName(0, "Labor AM (01_New)");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanCancelDeletingLabourNoButton(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.clickDeleteLaborButtonForPartByNumberInListAndServiceName(0, "Labor AM (01_New)");
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPartsDetailsPanelValidations.verifyPartContainsLabourByPartNumberAndLabourServiceName(0, "Labor AM (01_New)");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanDeleteLabour(String rowID, String description, JSONObject testData) {

        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.deleteLaborForPartByNumberInListANdLaborServiceName(0, "Labor AM (01_New)");
        refreshPage();
        VNextBOPartsDetailsPanelSteps.expandLaborBlockForPartByNumberInList(0);
        VNextBOPartsDetailsPanelValidations.verifyLaborsAmountIsCorrect(0, laboursAmountBeforeAdding);
    }
}