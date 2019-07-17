package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.enums.OrderPriority;
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

    @FindBy(xpath = "//*[@action=\"change-priority\"]")
    private List<WebElement> priorityItemsList;

    public void selectServiceInfo() {
        serviceInfo.click();
    }

    public void setOrderPriority(OrderPriority orderPriority) {
        priorityItemsList.stream()
                .filter(element -> element.getText().toLowerCase().contains(orderPriority.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Priority not found " + orderPriority.getValue()))
                .click();
    }
}
