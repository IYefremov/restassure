package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class MatrixServiceDetailsScreen extends VNextBaseScreen {
    @FindBy(xpath = "//form[@data-autotests-id='matrix-part-info-form']")
    private WebElement rootElement;
    @FindBy(xpath = "//div[@action='size']/input")
    private WebElement sizeField;
    @FindBy(xpath = "//div[@action='severity']/input")
    private WebElement severityField;
    @FindBy(xpath = "//div[@id='price']/input")
    private WebElement priceField;
    @FindBy(xpath = "//span[@action='available']")
    private WebElement availableButton;
    @FindBy(xpath = "//span[@action='selected']")
    private WebElement selectedButton;
    @FindBy(xpath = "//*[contains(@data-autotests-id, 'services-list')]/div")
    private List<ServiceListItem> serviceListItems;

    public MatrixServiceDetailsScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public ServiceListItem getServiceListItem(String itemName) {
        BaseUtils.waitABit(2000);
        return WaitUtils.getGeneralFluentWait().until(driver -> serviceListItems.stream()
                .filter(item -> item.getServiceName().equals(itemName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("service not found " + itemName)));
    }
}
