package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
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

    @FindBy(xpath = "//div[@data-page='repair-orders-work-queue-list' or @data-page='repair-orders-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action=\"show-locations\"]")
    private WebElement locationSelectionNavigationBar;

    @FindBy(xpath = "//div[@class=\"searchlist-filters\"]")
    private ActiveFiltersLabel activeFilterslabel;

    @FindBy(xpath = "//*[@data-autotests-id=\"repair-orders-list\"]/div")
    private List<RepairOrderListElement> repairOrderListElements;

    @FindBy(xpath = "//span[@data-automation-id=\"search-icon\"]")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class=\"searchlist-common-filters-toggle\"]")
    private WebElement commonFiltersToggle;

    @FindBy(xpath = "//div[@class=\"searchlist-nothing-found\"]")
    private WebElement nothingFoundLable;

    public RepairOrderScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public RepairOrderListElement getRepairOrderElement(String orderId) {
        BaseUtils.waitABit(2000);
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
        WaitUtils.click(locationSelectionNavigationBar);
    }

    public void openSearchMenu() {
        WaitUtils.click(searchButton);
    }

    public void openCommonFilters() {
        WaitUtils.click(commonFiltersToggle);
    }
}
