package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceMatrixData;
import com.cyberiansoft.test.vnext.enums.partservice.PartInfoScreenField;
import com.cyberiansoft.test.vnext.interactions.PartInfoScreenInteractions;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class PartMatrixScreenValidations {
    public static void validatePartInfo(PartServiceMatrixData expectedPartServiceData) {
        if (expectedPartServiceData.getSize() != null)
            WaitUtils.assertEquals(expectedPartServiceData.getSize(),
                    PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.SIZE).findElement(By.xpath(".//input")).getAttribute("value"));
        if (expectedPartServiceData.getSeverity() != null)
            WaitUtils.assertEquals(expectedPartServiceData.getServiceName(),
                    PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.SEVERITY).findElement(By.xpath(".//input")).getAttribute("value"));
    }

    public static void fieldShouldBeReadonly(boolean shouldBeReadonly, PartInfoScreenField partInfoField) {
        WaitUtils.assertEquals(PartInfoScreenInteractions.getPartInfoScreenField(partInfoField).getAttribute("action") == null,
                shouldBeReadonly);
    }
}
