package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ActiveFiltersLabel;
import com.cyberiansoft.test.vnext.webelements.RepairOrderListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class RepairOrderScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@data-page=\"list\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@action=\"show-locations\"]")
    private WebElement locationSelectionNavigationBar;

    @FindBy(xpath = "//div[@class=\"searchlist-filters\"]")
    private ActiveFiltersLabel activeFilterslabel;

    @FindBy(xpath = "//div[@data-autotests-id=\"repair-orders-list\"]/div")
    private List<RepairOrderListElement> repairOrderListElements;

    public RepairOrderScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public RepairOrderListElement getRepairOrderElement(String orderId) {
        return repairOrderListElements
                .stream()
                .filter((repairOrder) -> {
                    repairOrder.expand();
                    return orderId.equals(repairOrder.getRepairOrderId());
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Repair order with ID not found " + orderId));
    }

    public void openChangeLocationPage() {
        WaitUtils.elementShouldBeVisible(locationSelectionNavigationBar, true);
        locationSelectionNavigationBar.click();
    }
}
