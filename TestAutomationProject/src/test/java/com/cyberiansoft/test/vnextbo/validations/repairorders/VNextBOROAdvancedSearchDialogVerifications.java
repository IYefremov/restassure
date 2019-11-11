package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROAdvancedSearchDialog;

public class VNextBOROAdvancedSearchDialogVerifications {

    public static boolean isSaveButtonClickable() {
        return Utils.isElementClickable(new VNextBOROAdvancedSearchDialog().getSaveButton(), 3);
    }

    public static boolean isSavedSearchNameClickable() {
        return Utils.isElementClickable(new VNextBOROAdvancedSearchDialog().getSearchNameInputField(), 3);
    }
}
