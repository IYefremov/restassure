package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.screens.monitoring.InfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class RepairOrderInfoValidations {

    public static void validateRepairOrderPriority(OrderPriority orderPriority, boolean isSelected) {
        InfoScreen infoScreen = new InfoScreen();
        if (isSelected)
            Assert.assertTrue(infoScreen.isOrderPrioritySelected(orderPriority));
        else
            Assert.assertFalse(infoScreen.isOrderPrioritySelected(orderPriority));
    }

    public static void verifyOrderInfo(OrderInfoDto expectedOrderInfo) {
        InfoScreen infoScreen = new InfoScreen();
        OrderInfoDto actualOrderInfo = new OrderInfoDto();
        WaitUtils.elementShouldBeVisible(infoScreen.getVinField(), true);
        actualOrderInfo.setVin(infoScreen.getVinField().getAttribute("value"));
        actualOrderInfo.setStartDate(infoScreen.getStartedDate().getAttribute("value"));
        WaitUtils.elementShouldBeVisible(infoScreen.getStartedDate(), true);
        Assert.assertEquals(actualOrderInfo.getVin(), expectedOrderInfo.getVin());
        Assert.assertNotNull(actualOrderInfo.getStartDate());
    }
}
