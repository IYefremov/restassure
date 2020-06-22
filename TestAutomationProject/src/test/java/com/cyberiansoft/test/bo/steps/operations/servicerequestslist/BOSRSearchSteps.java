package com.cyberiansoft.test.bo.steps.operations.servicerequestslist;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist.BOSRSearchBlock;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRSearchData;
import com.cyberiansoft.test.enums.TimeFrameValues;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOSRSearchSteps {

    public static void setFreeText(String freeText) {
        Utils.clearAndType(new BOSRSearchBlock().getFreeText(), freeText);
    }

    public static void setTimeFrame(TimeFrameValues timeFrame) {
        final BOSRSearchBlock searchBlock = new BOSRSearchBlock();
        selectComboboxValue(searchBlock.getTimeFrameCbx(), searchBlock.getTimeFrameDd(), timeFrame.getName());
    }

    public static void setFromAndToTimeFrameFields(VNextBOSRSearchData searchData) {
        final BOSRSearchBlock searchBlock = new BOSRSearchBlock();
        Utils.clearAndType(searchBlock.getFrom(), searchData.getFromDate());
        Utils.clearAndType(searchBlock.getTo(), searchData.getToDate());
    }

    public static void setFromAndToTimeFrameFields(String from, String to) {
        final BOSRSearchBlock searchBlock = new BOSRSearchBlock();
        Utils.clearAndType(searchBlock.getFrom(), from);
        Utils.clearAndType(searchBlock.getTo(), to);
    }

    public static void search() {
        Utils.clickElement(new BOSRSearchBlock().getSearchButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitABit(1000);
    }
}
