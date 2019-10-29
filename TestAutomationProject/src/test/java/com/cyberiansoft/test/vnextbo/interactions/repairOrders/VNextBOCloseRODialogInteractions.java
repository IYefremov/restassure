package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOCloseRODialog;
import org.openqa.selenium.support.PageFactory;

public class VNextBOCloseRODialogInteractions {

    public static void setReason(String reason) {
        clickReasonBox();
        selectReason(reason);
    }

    private static void clickReasonBox() {
        Utils.clickElement(new VNextBOCloseRODialog().getReasonBox());
    }

    private static void selectReason(String reason) {
        final VNextBOCloseRODialog closeRODialog = new VNextBOCloseRODialog();
        Utils.selectOptionInDropDown(closeRODialog.getListBoxDropDown(), closeRODialog.getListBoxOptions(), reason);
    }

    public static void clickCloseROButton() {
        Utils.clickElement(new VNextBOCloseRODialog().getCloseROButton());
    }
}