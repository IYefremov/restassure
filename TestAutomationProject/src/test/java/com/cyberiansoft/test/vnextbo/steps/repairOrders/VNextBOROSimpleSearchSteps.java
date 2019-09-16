package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROSimpleSearchInteractions;
import org.testng.Assert;

public class VNextBOROSimpleSearchSteps {

    private VNextBOROSimpleSearchInteractions repairOrdersSimpleSearchInteractions;
    private VNextBOROPageInteractions repairOrdersPageInteractions;

    public VNextBOROSimpleSearchSteps() {
        repairOrdersSimpleSearchInteractions = new VNextBOROSimpleSearchInteractions();
        repairOrdersPageInteractions = new VNextBOROPageInteractions();
    }

    public void searchByText(String searchText) {
        repairOrdersSimpleSearchInteractions.setRepairOrdersSearchText(searchText);
        repairOrdersSimpleSearchInteractions.clickSearchIcon();
        Assert.assertTrue(repairOrdersPageInteractions.isWorkOrderDisplayedByName(searchText),
                "The work order is not displayed after search by clicking the 'Search' icon");
    }

    public void searchByTextWithEnter(String searchText) {
        repairOrdersSimpleSearchInteractions.setRepairOrdersSearchText(searchText);
        repairOrdersSimpleSearchInteractions.clickEnterToSearch();
    }
}