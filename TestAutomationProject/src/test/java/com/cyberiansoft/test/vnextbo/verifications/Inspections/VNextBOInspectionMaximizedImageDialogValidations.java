package com.cyberiansoft.test.vnextbo.verifications.Inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionMaximizedImageDialog;
import org.testng.Assert;

public class VNextBOInspectionMaximizedImageDialogValidations {

    public static void isInspectionZoomedImageDisplayed()
    {
        VNextBOInspectionMaximizedImageDialog maximizedImageDialog =
                new VNextBOInspectionMaximizedImageDialog(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(maximizedImageDialog.maximizedImage),
                "Inspection's image hasn't been maximized");
    }

    public static void isInspectionZoomedImageClosed(VNextBOInspectionMaximizedImageDialog maximizedImageDialog)
    {
        Assert.assertTrue(Utils.isElementDisplayed(maximizedImageDialog.dialogContainer),
                "Inspection's image hasn't been minimized");
    }
}