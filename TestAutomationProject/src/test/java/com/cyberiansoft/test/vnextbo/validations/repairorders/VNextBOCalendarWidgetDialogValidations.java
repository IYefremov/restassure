package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebElement;

public class VNextBOCalendarWidgetDialogValidations {

    public static boolean isCalendarOpened(WebElement calendarType) {
        return Utils.isElementWithAttributeContainingValueDisplayed(calendarType, "style", "display: block", 10);
    }
}
