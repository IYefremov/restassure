package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAdvancedSearchDialog;
import org.testng.Assert;

public class VNextBOAdvancedSearchDialogValidations {

    public static void verifyAdvancedSearchIsDisplayedWithAllElements() {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getCustomerField()), "Customer field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getPhaseField()), "Phase field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getWoTypeField()), "WO type field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getRepairStatusField()), "Repair status field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getWoNumberField()), "WO# field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getStockNumberField()), "Stock# field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getEtaFromDateField()), "ETA From field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getEtaToDateField()), "ETA To field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getSearchButton()), "Search button hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getVinField()), "VIN field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getPartNumberField()), "Part# field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getNotesField()), "Notes field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getFromDateField()), "From field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getToDateField()), "To field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getOrderedFromField()), "Order From field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getCorePriceCheckBox()), "Core Price checkbox hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getLaborCreditCheckBox()), "Labor Credit checkbox hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getCloseButton()), "Close button hasn't been displayed");
    }

    public static void verifyAdvancedSearchFieldsHaveCorrectData(VNextBOPartsManagementSearchData searchData) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getCustomerField()), searchData.getCustomer(), "Customer field hasn't been correct");
        Assert.assertEquals(Utils.getText(advancedSearchDialog.getPhaseField()), searchData.getPhase(), "Phase field hasn't been correct");
        Assert.assertEquals(Utils.getText(advancedSearchDialog.getWoTypeField()), searchData.getWoType(), "WO type field hasn't been correct");
        Assert.assertEquals(Utils.getText(advancedSearchDialog.getRepairStatusField()), searchData.getRepairStatus(), "Repair status field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getWoNumberField()), searchData.getWoNum(), "WO# field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getStockNumberField()), searchData.getStockNum(), "Stock# field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getEtaFromDateField()), searchData.getEtaFromDate(), "ETA From field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getEtaToDateField()), searchData.getEtaToDate(), "ETA To field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getVinField()), searchData.getVinNum(), "VIN field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getPartNumberField()), searchData.getPartNum(), "Part# field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getNotesField()), searchData.getNotes(), "Notes field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getFromDateField()), searchData.getFromDate(), "From field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getToDateField()), searchData.getToDate(), "To field hasn't been correct");
        Assert.assertEquals(Utils.getText(advancedSearchDialog.getOrderedFromField()), searchData.getOrderedFrom(), "Ordered from field hasn't been correct");
        Assert.assertEquals(Utils.getInputFieldValue(advancedSearchDialog.getSearchNameField()), searchData.getSearchName(), "Search name field hasn't been correct");

    }

    public static void verifyAdvancedSearchFormIsDisplayed(boolean shouldBeDisplayed) {

        VNextBOAdvancedSearchDialog advancedSearchDialog = new VNextBOAdvancedSearchDialog();
        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(advancedSearchDialog.getAdvancedSearchForm()),
                "Advanced search form hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(advancedSearchDialog.getAdvancedSearchForm()),
                "Advanced search form has been displayed");
    }
}
