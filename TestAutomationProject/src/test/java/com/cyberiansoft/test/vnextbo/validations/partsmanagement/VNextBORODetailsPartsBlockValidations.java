package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPartsBlock;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

import java.util.List;

public class VNextBORODetailsPartsBlockValidations {

    private static VNextBORODetailsPartsBlock detailsPartsBlock;

    static {
        detailsPartsBlock = new VNextBORODetailsPartsBlock();
    }

    public static void verifyServicePartsFieldsAreNotClickable(String partName) {
        final List<WebElement> parts = detailsPartsBlock.getPartsByName(partName);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(parts, 7);

        final boolean notClickable =
                WaitUtilsWebDriver.getShortWait().until(
                        (ExpectedCondition<Boolean>) driver -> detailsPartsBlock.getPartFieldsByName(parts)
                                .stream()
                                .anyMatch(part -> {
                                    System.out.println(part);
                                    System.out.println(part.isEnabled());
                                    return part.isEnabled();
                                }));
        Assert.assertTrue(notClickable, "The part fields are clickable");
    }
}
