package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.monitoring.TimeReportScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

public class TimeReportScreenVerifications {
    public static void startDateShouldBePresent(Boolean shouldBePresent) {
        WebElement startDateLable = new TimeReportScreen().getStartDateLable();
        WaitUtils.elementShouldBeVisible(startDateLable, shouldBePresent);
        if (shouldBePresent)
            Assert.assertNotNull(startDateLable.getText());
        else
            Assert.assertNull(startDateLable.getText());
    }

    public static void endDateShouldBePresent(Boolean shouldBePresent) {
        WebElement endDateLable = new TimeReportScreen().getEndDateLable();
        WaitUtils.elementShouldBeVisible(endDateLable, shouldBePresent);
        if (shouldBePresent)
            Assert.assertNotNull(endDateLable.getText());
        else
            Assert.assertEquals("", endDateLable.getText());
    }

    public static void validateTimeReportIsEmpty(Boolean isEmpty) {
        WaitUtils.waitUntilElementIsClickable(new TimeReportScreen().getRootElement());
        if (isEmpty) {
            WaitUtils.waitUntilElementIsClickable(new TimeReportScreen().getEmptyBlock());
            Assert.assertTrue(new TimeReportScreen().getEmptyBlock().isDisplayed());
        }
        else
            Assert.assertFalse(new TimeReportScreen().getEmptyBlock().isDisplayed());
    }
}
