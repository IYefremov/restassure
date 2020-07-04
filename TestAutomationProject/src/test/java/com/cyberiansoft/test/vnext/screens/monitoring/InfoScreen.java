package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class InfoScreen extends MonitorScreen {

    @FindBy(xpath = "//input[@data-name=\"VIN\"]")
    private WebElement vinField;

    @FindBy(xpath = "//input[@data-name=\"startDate\"]")
    private WebElement startedDate;

    @FindBy(xpath = "//*[@action=\"info\"]")
    private WebElement serviceInfo;

    @FindBy(xpath = "//*[@data-autotests-id=\"phases-list\"]")
    private WebElement phasesList;

    @FindBy(xpath = "//*[@action=\"change-priority\"]")
    private List<WebElement> priorityItemsList;

    public void selectServiceInfo() {
        serviceInfo.click();
    }

    public void setOrderPriority(OrderPriority orderPriority) {
        priorityItemsList.stream()
                .filter(element -> element.getText().contains(orderPriority.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Priority not found " + orderPriority.getValue()))
                .click();
    }

    public boolean isOrderPrioritySelected(OrderPriority orderPriority) {
        WaitUtils.elementShouldBeVisible(phasesList, true);
        return priorityItemsList.stream()
                .filter(element -> element.getText().contains(orderPriority.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Priority not found " + orderPriority.getValue()))
                .getAttribute("class").contains("selected-priority");
    }
}
