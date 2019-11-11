package com.cyberiansoft.test.vnextbo.validations.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionMaximizedImageDialog;
import org.testng.Assert;

public class VNextBOInspectionMaximizedImageDialogValidations {

    public static void verifyInspectionZoomedImageIsDisplayed() {
        VNextBOInspectionMaximizedImageDialog maximizedImageDialog =
                new VNextBOInspectionMaximizedImageDialog();
        Assert.assertTrue(Utils.isElementDisplayed(maximizedImageDialog.maximizedImage),
                "Inspection's image hasn't been maximized");
    }

    public static void verifyInspectionZoomedImageIsClosed(VNextBOInspectionMaximizedImageDialog maximizedImageDialog) {
        Assert.assertTrue(Utils.isElementDisplayed(maximizedImageDialog.dialogContainer),
                "Inspection's image hasn't been minimized");
    }
}