package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOCalendarWidgetDialog;

public class VNextBOCalendarWidgetDialogInteractions {

    public static void selectFromDate(String date) {

        Utils.clickElement(new VNextBOCalendarWidgetDialog().fromDateCalendarSpecificDateButton(date));
    }

    public static void selectToDate(String date) {

        Utils.clickElement(new VNextBOCalendarWidgetDialog().toDateCalendarSpecificDateButton(date));
    }
}
