package com.cyberiansoft.test.vnextbo.verifications.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROAdvancedSearchDialog;

public class VNextBOROAdvancedSearchDialogVerifications {

    private VNextBOROAdvancedSearchDialog advancedSearchDialog;

    public VNextBOROAdvancedSearchDialogVerifications() {
        advancedSearchDialog = new VNextBOROAdvancedSearchDialog();
    }

    public boolean isSaveButtonClickable() {
        return Utils.isElementClickable(advancedSearchDialog.getSaveButton(), 3);
    }

    public boolean isSavedSearchNameClickable() {
        return Utils.isElementClickable(advancedSearchDialog.getSearchNameInputField(), 3);
    }
}
