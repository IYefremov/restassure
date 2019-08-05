package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.enums.partservice.PartInfoScreenField;
import com.cyberiansoft.test.vnext.interactions.PartInfoScreenInteractions;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class PartInfoScreenValidations {
    public static void validatePartInfo(PartServiceData expectedPartServiceData) {
        if (expectedPartServiceData.getCategory() != null)
            WaitUtils.assertEquals(expectedPartServiceData.getCategory(),
                    PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.CATEGORY).findElement(By.xpath(".//input")).getAttribute("value"));
        if (expectedPartServiceData.getSubCategory() != null)
            WaitUtils.assertEquals(expectedPartServiceData.getSubCategory(),
                    PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.SUB_CATEGORY).findElement(By.xpath(".//input")).getAttribute("value"));
        if (expectedPartServiceData.getPartName() != null)
            WaitUtils.assertEquals(expectedPartServiceData.getPartName().getPartNameList().get(0),
                    PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.PART_NAME).findElement(By.xpath(".//input")).getAttribute("value"));
        if (expectedPartServiceData.getPartPosition() != null)
            WaitUtils.assertEquals(expectedPartServiceData.getPartPosition(),
                    PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.PART_POSITION).findElement(By.xpath(".//input")).getAttribute("value"));
    }

    public static void fieldShouldBeReadonly(boolean shouldBeReadonly, PartInfoScreenField partInfoField) {
        WaitUtils.assertEquals(PartInfoScreenInteractions.getPartInfoScreenField(partInfoField).getAttribute("action") == null,
                shouldBeReadonly);
    }
}
