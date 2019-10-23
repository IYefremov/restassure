package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionMaximizedImageDialog;

public class VNextBOInspectionMaximizedImageDialogSteps {

    public static void closeInspectionMaximizedImageDialog()
    {
        VNextBOInspectionMaximizedImageDialog maximizedImageDialog =
                new VNextBOInspectionMaximizedImageDialog(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(maximizedImageDialog.closeDialogButton);
    }
}