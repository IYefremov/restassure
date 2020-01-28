package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.enums.partservice.PartInfoScreenField;
import com.cyberiansoft.test.vnext.interactions.PartInfoScreenInteractions;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class PartInfoScreenValidations {
    public static void validatePartInfo(PartServiceData expectedPartServiceData) {
        if (expectedPartServiceData.getCategory() != null)
            validateCategoryValue(expectedPartServiceData.getCategory());
        if (expectedPartServiceData.getSubCategory() != null)
            validateSubCategoryValue(expectedPartServiceData.getSubCategory());
        if (expectedPartServiceData.getPartName() != null)
            validatePartNameValue(expectedPartServiceData.getPartName().getPartNameList().get(0));
        if (expectedPartServiceData.getPartPosition() != null)
            validatePartPositionValue(expectedPartServiceData.getPartPosition());
    }

    public static void fieldShouldBeReadonly(boolean shouldBeReadonly, PartInfoScreenField partInfoField) {
        WaitUtils.assertEquals(PartInfoScreenInteractions.getPartInfoScreenField(partInfoField).getAttribute("action") == null,
                shouldBeReadonly);
    }

    public static void validateCategoryValue(String expectedCategory) {
        WaitUtils.assertEquals(expectedCategory,
                PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.CATEGORY).findElement(By.xpath(".//input")).getAttribute("value"));
    }

    public static void validateSubCategoryValue(String expectedSubCategory) {
        WaitUtils.assertEquals(expectedSubCategory,
                PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.SUB_CATEGORY).findElement(By.xpath(".//input")).getAttribute("value"));
    }

    public static void validatePartNameValue(String expectedPartName) {
        WaitUtils.assertEquals(expectedPartName,
                PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.PART_NAME).findElement(By.xpath(".//input")).getAttribute("value"));
    }

    public static void validatePartPositionValue(String expectedPartPosition) {
        WaitUtils.assertEquals(expectedPartPosition,
                PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.PART_POSITION).findElement(By.xpath(".//input")).getAttribute("value"));
    }
}
