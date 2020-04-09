package com.cyberiansoft.test.bo.validations.workordertypes;

import com.cyberiansoft.test.bo.pageobjects.webpages.company.workordertypes.BOWorkOrderTypeDialog;

public class BOWorkOrderTypeDialogValidations {

    public static boolean isApprovalRequiredChecked() {
        return new BOWorkOrderTypeDialog().getApprovalRequiredCheckbox().isSelected();
    }
}
