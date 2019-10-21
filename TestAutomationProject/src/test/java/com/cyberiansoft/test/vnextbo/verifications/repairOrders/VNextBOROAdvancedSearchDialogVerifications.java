package com.cyberiansoft.test.vnextbo.verifications.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROAdvancedSearchDialog;

public class VNextBOROAdvancedSearchDialogVerifications {

    public static boolean isSaveButtonClickable() {
        return Utils.isElementClickable(new VNextBOROAdvancedSearchDialog().getSaveButton(), 3);
    }

    public static boolean isSavedSearchNameClickable() {
        return Utils.isElementClickable(new VNextBOROAdvancedSearchDialog().getSearchNameInputField(), 3);
    }
}
