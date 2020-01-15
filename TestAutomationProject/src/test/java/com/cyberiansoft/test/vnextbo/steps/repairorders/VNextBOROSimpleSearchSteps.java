package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROSimpleSearchInteractions;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.testng.Assert;

public class VNextBOROSimpleSearchSteps {

    public static void searchByText(String searchText) {
        VNextBOROSimpleSearchInteractions.setRepairOrdersSearchText(searchText);
        VNextBOROSimpleSearchInteractions.clickSearchIcon();
        //Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(searchText, true),
        //        "The work order is not displayed after search by clicking the 'Search' icon");
    }

    public static void searchWithEnter(String searchText) {
        VNextBOROSimpleSearchInteractions.setRepairOrdersSearchText(searchText);
        VNextBOROSimpleSearchInteractions.clickEnterToSearch();
    }

    public static void search(String search) {
        VNextBOROSimpleSearchInteractions.setRepairOrdersSearchText(search);
        VNextBOROSimpleSearchInteractions.clickSearchIcon();
    }
}