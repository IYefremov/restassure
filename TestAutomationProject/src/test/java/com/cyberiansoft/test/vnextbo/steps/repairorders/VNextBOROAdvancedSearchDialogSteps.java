package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.enums.monitor.OrderMonitorFlags;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROAdvancedSearchDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.testng.Assert;

public class VNextBOROAdvancedSearchDialogSteps {

    public static void searchByActivePhase(String phase, String phaseStatus, String timeFrame) {
        openAdvancedSearchDialog();
        VNextBOROAdvancedSearchDialogInteractions.setPhase(phase);
        VNextBOROAdvancedSearchDialogInteractions.setPhaseStatus(phaseStatus);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(timeFrame);
        search();
    }

    public static void searchByWoAndTimeFrame(String order, TimeFrameValues timeFrameValue) {
        openAdvancedSearchDialog();
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(order);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(timeFrameValue.getName());
        search();
    }

    public static void openAdvancedSearchDialog() {
        if (!VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(1)) {
            VNextBOROPageInteractions.clickAdvancedSearchCaret();
        }
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(3),
                "The advanced search dialog is not opened");
    }

    public static void search() {
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    public static void closeAdvancedSearchDialog() {
        VNextBOROAdvancedSearchDialogInteractions.closeAdvancedSearchDialog();
        VNextBOROPageValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void setAdvancedSearchDialogData(VNextBOMonitorData data, TimeFrameValues timeFrameValue, OrderMonitorFlags flag) {
        openAdvancedSearchDialog();
        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setEmployee(data.getEmployee());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(timeFrameValue.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.setFlag(flag.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(data.getSearchName());
    }
}