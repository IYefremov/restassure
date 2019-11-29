package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROAdvancedSearchDialog;

public class VNextBOROAdvancedSearchDialogValidations {

    public static boolean isSaveButtonClickable() {
        return Utils.isElementClickable(new VNextBOROAdvancedSearchDialog().getSaveButton(), 3);
    }

    public static boolean isSavedSearchNameClickable() {
        return Utils.isElementClickable(new VNextBOROAdvancedSearchDialog().getSearchNameInputField(), 3);
    }

    public static boolean isAdvancedSearchDialogDisplayed() {
        return Utils.isElementDisplayed(new VNextBOROAdvancedSearchDialog().getAdvancedSearchDialog());
    }

    public static boolean isAdvancedSearchDialogDisplayed(int timeOut) {
        return Utils.isElementDisplayed(new VNextBOROAdvancedSearchDialog().getAdvancedSearchDialog(), timeOut);
    }

    public static boolean isAdvancedSearchDialogNotDisplayed() {
        return Utils.isElementNotDisplayed(new VNextBOROAdvancedSearchDialog().getAdvancedSearchDialog());
    }

    public static boolean isCustomerDisplayed(String customer) {
        return Utils.isDataDisplayed(new VNextBOROAdvancedSearchDialog().getCustomerListBoxOptions(), customer);
    }

    public static boolean isEmployeeDisplayed(String employee) {
        return Utils.isDataDisplayed(new VNextBOROAdvancedSearchDialog().getEmployeeListBoxOptions(), employee);
    }
}
