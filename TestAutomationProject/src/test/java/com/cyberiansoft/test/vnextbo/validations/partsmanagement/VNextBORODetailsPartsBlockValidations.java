package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPartsBlock;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class VNextBORODetailsPartsBlockValidations {

    public static void verifyServicePartsFieldsAreNotChangeable(String partName) {
        final VNextBORODetailsPartsBlock detailsPartsBlock = new VNextBORODetailsPartsBlock();
        final List<WebElement> parts = detailsPartsBlock.getPartsByName(partName);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(parts, 7);

        final boolean changeable = detailsPartsBlock.getPartFieldsByName(parts)
                                .stream()
                                .anyMatch(part -> !(part.getAttribute("onchange") == null));
        Assert.assertFalse(changeable, "The part fields are changeable");
    }
}
