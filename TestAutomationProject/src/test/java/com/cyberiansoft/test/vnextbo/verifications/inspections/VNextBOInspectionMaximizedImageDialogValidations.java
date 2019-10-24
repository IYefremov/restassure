package com.cyberiansoft.test.vnextbo.verifications.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionMaximizedImageDialog;
import org.testng.Assert;

public class VNextBOInspectionMaximizedImageDialogValidations {

    public static void isInspectionZoomedImageDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionMaximizedImageDialog().maximizedImage),
                "Inspection's image hasn't been maximized");
    }

    public static void isInspectionZoomedImageClosed(VNextBOInspectionMaximizedImageDialog maximizedImageDialog) {

        Assert.assertTrue(Utils.isElementDisplayed(maximizedImageDialog.dialogContainer),
                "Inspection's image hasn't been minimized");
    }
}