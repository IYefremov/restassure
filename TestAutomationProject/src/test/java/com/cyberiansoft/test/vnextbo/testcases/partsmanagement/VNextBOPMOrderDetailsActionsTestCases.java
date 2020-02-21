package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOPMOrderDetailsActionsTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsActionsTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanDeleteThePart(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.addPartWithPartsListUpdate(data.getPartData(), woNum);
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.deletePartByNumberInList(
                VNextBOPartsDetailsPanelSteps.getPartNumberInTheListByServiceName(data.getPartData().getPartItems()[0]));
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsCorrect(numberOfParts - 1);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanAddNewDocumentAndDeleteIt(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.addPartIfNotPresentWithPartsListUpdate(data.getPartData(), woNum);
        final int partNumber = VNextBOPartsDetailsPanelSteps
                .getPartNumberInTheListByServiceName(data.getPartData().getPartItems()[0]);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(partNumber);
        VNextBOPartDocumentsDialogInteractions.clickAddNewDocumentButton();
        VNextBOPartAddNewDocumentDialogSteps.setDocumentFields(data.getDocumentData());
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(false);
        VNextBOPartDocumentsDialogValidations.verifyPartDocumentsFields(0, data.getDocumentData());
        VNextBOPartDocumentsDialogSteps.deleteDocument(0);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }
}
