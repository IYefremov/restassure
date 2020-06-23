package com.cyberiansoft.test.bo.steps.monitor.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairorders.BOROSearchBlock;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRSearchData;
import com.cyberiansoft.test.enums.TimeFrameValues;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOROSearchBlockSteps {

    public static void setLocation(String location) {
        final BOROSearchBlock searchBlock = new BOROSearchBlock();
        selectComboboxValue(searchBlock.getLocationCombobox(), searchBlock.getLocationDropDown(), location);
    }

    public static void setRepairStatus(String repairStatus) {
        final BOROSearchBlock searchBlock = new BOROSearchBlock();
        selectComboboxValue(searchBlock.getRepairStatusCombobox(), searchBlock.getRepairStatusDropDown(), repairStatus);
    }

    public static void setTimeFrame(TimeFrameValues timeFrame) {
        final BOROSearchBlock searchBlock = new BOROSearchBlock();
        selectComboboxValue(searchBlock.getTimeFrameCombobox(), searchBlock.getTimeFrameDropDown(), timeFrame.getName());
    }

    public static void setFromAndToTimeFrameFields(VNextBOSRSearchData searchData) {
        final BOROSearchBlock searchBlock = new BOROSearchBlock();
        Utils.clearAndType(searchBlock.getFrom(), searchData.getFromDate());
        Utils.clearAndType(searchBlock.getTo(), searchData.getToDate());
    }

    public static void setFromAndToTimeFrameFields(String from, String to) {
        final BOROSearchBlock searchBlock = new BOROSearchBlock();
        Utils.clearAndType(searchBlock.getFrom(), from);
        Utils.clearAndType(searchBlock.getTo(), to);
    }

    public static void setWoNum(String woNum) {
        Utils.clearAndType(new BOROSearchBlock().getWoNum(), woNum);
    }
}
