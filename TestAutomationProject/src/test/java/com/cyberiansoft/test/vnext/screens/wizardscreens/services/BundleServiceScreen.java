package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BundleServiceScreen extends VNextBaseScreen {
    @FindBy(xpath = "//*[@data-page='bundle']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='selected']")
    private WebElement selectedServiceScreen;

    @FindBy(xpath = "//input[@data-name='Amount']")
    private WebElement bundlePriceInput;

    @FindBy(xpath = "//*[@class='amount selected-services-amount']")
    private WebElement selectedServicesAmount;

    @FindBy(xpath = "//*[@data-entity-service-id]")
    private List<ServiceListItem> serviceList;

    public BundleServiceScreen() {
        PageFactory.initElements(new FiledDecorator(appiumdriver), this);
    }

    public ServiceListItem getServiceListItem(String itemName) {
        BaseUtils.waitABit(2000);
        return serviceList.stream()
                .filter(item -> item.getServiceName().equals(itemName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("service not found " + itemName));
    }

    public List<ServiceListItem> getServiceListItems(String itemName) {
        BaseUtils.waitABit(2000);
        return serviceList.stream()
                .filter(item -> item.getServiceName().equals(itemName))
                .collect(Collectors.toList());
    }
}