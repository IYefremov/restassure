package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROAdvancedSearchDialog;
import org.openqa.selenium.support.PageFactory;

public class VNextBOROAdvancedSearchDialogInteractions {

    private VNextBOROAdvancedSearchDialog advancedSearchDialog;

    public VNextBOROAdvancedSearchDialogInteractions() {
        advancedSearchDialog = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBOROAdvancedSearchDialog.class);
    }

    public boolean isAdvancedSearchDialogDisplayed() {
        return Utils.isElementDisplayed(advancedSearchDialog.getAdvancedSearchDialog());
    }

    public void setTimeFrame(String timeFrame) {
        Utils.clickElement(advancedSearchDialog.getTimeFrameListBox());
        Utils.selectOptionInDropDown(advancedSearchDialog.getTimeFrameDropDown(),
                advancedSearchDialog.getTimeFrameListBoxOptions(), timeFrame, true);
    }

    public void setPhase(String phase) {
        Utils.clickElement(advancedSearchDialog.getPhaseListBox());
        Utils.selectOptionInDropDown(
                advancedSearchDialog.getPhaseDropDown(), advancedSearchDialog.getPhaseListBoxOptions(), phase);
    }

    public void setPhaseStatus(String phaseStatus) {
        Utils.clickElement(advancedSearchDialog.getPhaseStatusListBox());
        Utils.selectOptionInDropDown(advancedSearchDialog.getPhaseStatusDropDown(),
                advancedSearchDialog.getPhaseStatusListBoxOptions(), phaseStatus);
    }

    public void clickSearchButton() {
        Utils.clickElement(advancedSearchDialog.getSearchButton());
        WaitUtilsWebDriver.waitForLoading();
    }
}