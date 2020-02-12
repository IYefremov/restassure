package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOAddNewPartDialogInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOAddNewPartDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAddNewPartDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class VNextBOPartsManagementAddNewPartTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementAddNewPartTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddMoreThanOneNewPart(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String[] partNames = data.getPartData().getPartNames();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelSteps.setAddNewPartBasicDefinitions(data.getPartData());

        final int partsCounterValueBefore = Integer.valueOf(VNextBOAddNewPartDialogInteractions.getSelectedPartsCounter());
        final int partsListSizeBefore = VNextBOAddNewPartDialogInteractions.getPartsListSize();
        VNextBOAddNewPartDialogInteractions.clickSelectAllPartsButton();
        VNextBOAddNewPartDialogValidations.verifyPartsListSizeIsCorrect(0);
        VNextBOAddNewPartDialogValidations.verifySelectedPartsCounterValueIsCorrect(String.valueOf(partsListSizeBefore));

        VNextBOAddNewPartDialogInteractions.clickUnSelectAllPartsButton();
        VNextBOAddNewPartDialogValidations.verifyPartsListSizeIsCorrect(partsCounterValueBefore);
        VNextBOAddNewPartDialogValidations.verifySelectedPartsCounterValueIsCorrect(String.valueOf(0));

        VNextBOAddNewPartDialogSteps.selectPartsFromPartsList(Arrays.asList(data.getPartData().getPartItems()));
        VNextBOAddNewPartDialogValidations.verifySelectedPartsCounterValueIsCorrect(
                String.valueOf(partsCounterValueBefore + data.getPartData().getPartItems().length));
        VNextBOAddNewPartDialogSteps.submit();

        VNextBOPartsDetailsPanelSteps.updatePartsList(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelValidations.verifyPartIsDisplayed(partNames[0]);
        VNextBOPartsDetailsPanelValidations.verifyPartIsDisplayed(partNames[1]);
        VNextBOPartsDetailsPanelSteps.deleteServicesByName(partNames);
    }
}
