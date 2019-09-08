package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORepairOrdersPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORepairOrdersSimpleSearchInteractions;
import org.testng.Assert;

public class VNextBORepairOrdersSimpleSearchSteps {

    private VNextBORepairOrdersSimpleSearchInteractions repairOrdersSimpleSearchInteractions;
    private VNextBORepairOrdersPageInteractions repairOrdersPageInteractions;

    public VNextBORepairOrdersSimpleSearchSteps() {
        repairOrdersSimpleSearchInteractions = new VNextBORepairOrdersSimpleSearchInteractions();
        repairOrdersPageInteractions = new VNextBORepairOrdersPageInteractions();
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