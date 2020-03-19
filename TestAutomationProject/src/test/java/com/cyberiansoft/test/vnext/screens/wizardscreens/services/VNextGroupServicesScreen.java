package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.GroupServiceListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextGroupServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[@data-page='services-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='select-group']")
    private List<GroupServiceListItem> groupServiceList;

    @FindBy(xpath="//div[@data-page='services-list']")
    private WebElement servicesscreen;

    public VNextGroupServicesScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public GroupServiceListItem getGroupServiceElement(String groupName) {
        return groupServiceList.stream().filter(listElement -> listElement.getServiceName().equals(groupName)).findFirst().orElseThrow(() -> new RuntimeException("service group not found " + groupName));
    }

    public void switchToAvailableGroupServicesView() {
        tap(servicesscreen.findElement(By.xpath(".//*[@action='available']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='available' and @class='button active']")));
    }
}
