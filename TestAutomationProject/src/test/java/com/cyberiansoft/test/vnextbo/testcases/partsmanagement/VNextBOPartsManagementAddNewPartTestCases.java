package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOAddNewPartDialogInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOAddNewPartDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAddNewPartDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class VNextBOPartsManagementAddNewPartTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMAddNewPartTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddMoreThanOneNewPart(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String[] partNames = data.getPartData().getPartNames();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelSteps.setAddNewPartValues(data.getPartData());

        final int partsCounterValueBefore = Integer.parseInt(VNextBOAddNewPartDialogInteractions.getSelectedPartsCounter());
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
        VNextBOPartsDetailsPanelValidations.verifyPartIsDisplayed(partNames);
        VNextBOPartsDetailsPanelSteps.deleteServicesByName(partNames);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFilterParts(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelSteps.setAddNewPartValues(data.getPartData());
        final int prevSize = VNextBOAddNewPartDialogInteractions.getDisplayedPartsListOptionsSize();
        VNextBOAddNewPartDialogInteractions.setPartName(data.getPartData().getPartItems()[0]);
        VNextBOAddNewPartDialogInteractions.waitForPartsListToBeUpdated(prevSize);
        Assert.assertTrue(prevSize > VNextBOAddNewPartDialogInteractions.getDisplayedPartsListOptionsNames().size(),
                "The displayed parts list options size hasn't been reduced after filtering");
        VNextBOAddNewPartDialogValidations.verifyAllPartsListOptionsContainText(data.getPartData().getPartItems()[0]);
        VNextBOAddNewPartDialogSteps.cancel();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanTypeService(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String service = data.getPartData().getService();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());

        VNextBOPartsDetailsPanelSteps.clickAddNewPartButton();
        VNextBOAddNewPartDialogValidations.verifyDialogIsDisplayed(true);
        VNextBOAddNewPartDialogInteractions.setServiceName(service);
        final List<String> highlightedServices = VNextBOAddNewPartDialogInteractions
                .getHighlightedServicesInDropDown(service);
        Assert.assertEquals(highlightedServices.size(), 1, "Only one service has to be highlighted");
        Assert.assertTrue(highlightedServices.get(0).contains(service),
                "The highlighted service doesn't contain the given substring");
        VNextBOAddNewPartDialogSteps.cancel();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeletePart(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());

        VNextBOPartsDetailsPanelSteps.addNewPart(data.getPartData());
        VNextBOPartsDetailsPanelSteps.updatePartsList(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelSteps.deleteServicesByName(data.getPartData().getPartItems());
        VNextBOPartsDetailsPanelValidations.verifyPartIsDisplayed(data.getPartData().getPartItems()[0], false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickDeleteButtonAndRefuseDeletingThePartUsingXIcon(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        String partName = VNextBOPartsDetailsPanelInteractions.getPartNameByIndex(0);
        if (partName != null) {
            VNextBOPartsDetailsPanelInteractions.selectCheckboxesForServicesByName(partName);
            VNextBOPartsDetailsPanelSteps.clickDeleteServicesAndCancelWithXIcon();
            VNextBOPartsDetailsPanelValidations.isDeleteSelectedPartsButtonDisplayed(true);
            VNextBOPartsDetailsPanelValidations.verifyPartIsDisplayed(partName, true);
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickDeleteButtonAndRefuseDeletingThePartUsingCancelButton(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        String partName = VNextBOPartsDetailsPanelInteractions.getPartNameByIndex(0);
        if (partName != null) {
            VNextBOPartsDetailsPanelInteractions.selectCheckboxesForServicesByName(partName);
            VNextBOPartsDetailsPanelSteps.clickDeleteServicesAndCancel();
            VNextBOPartsDetailsPanelValidations.isDeleteSelectedPartsButtonDisplayed(true);
            VNextBOPartsDetailsPanelValidations.verifyPartIsDisplayed(partName, true);
        }
    }
}
