package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROSimpleSearchInteractions;
import com.cyberiansoft.test.vnextbo.validations.repairOrders.VNextBOROPageValidations;
import org.testng.Assert;

public class VNextBOROSimpleSearchSteps {

    public static void searchByText(String searchText) {
        final VNextBOROSimpleSearchInteractions simpleSearchInteractions = new VNextBOROSimpleSearchInteractions();
        simpleSearchInteractions.setRepairOrdersSearchText(searchText);
        simpleSearchInteractions.clickSearchIcon();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(searchText),
                "The work order is not displayed after search by clicking the 'Search' icon");
    }

    public static void searchByTextWithEnter(String searchText) {
        final VNextBOROSimpleSearchInteractions simpleSearchInteractions = new VNextBOROSimpleSearchInteractions();
        simpleSearchInteractions.setRepairOrdersSearchText(searchText);
        simpleSearchInteractions.clickEnterToSearch();
    }
}