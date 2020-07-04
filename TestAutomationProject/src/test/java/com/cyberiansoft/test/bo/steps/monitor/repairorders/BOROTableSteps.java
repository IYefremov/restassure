package com.cyberiansoft.test.bo.steps.monitor.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairorders.BOROTable;

import java.util.List;

public class BOROTableSteps {

    public static List<String> getWOList() {
        return Utils.getText(new BOROTable().getOrdersList());
    }
}
