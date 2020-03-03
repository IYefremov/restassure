package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextGroupServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath="//div[@data-page='services-list']")
    private WebElement servicesscreen;

    public VNextGroupServicesScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);

    }

    public WebElement getServicesList() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        return wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//*[@data-autotests-id='all-services']"))));
    }

    public VNextAvailableGroupServicesList openServiceGroup(String serviceGroupName) {
        WebElement servicerow = getServiceGroupItem(serviceGroupName);
        if (servicerow != null)
            tap(servicerow);
        else
            Assert.fail("Can't find service group: " + serviceGroupName);

        return new VNextAvailableGroupServicesList(appiumdriver);
    }

    private List<WebElement> getServiceGroupsItems() {
        return getServicesList().findElements(By.xpath(".//*[@action='select-group']"));
    }

    private WebElement getServiceGroupItem(String serviceGroupName) {
        WebElement serviceListItem = null;
        List<WebElement> services = getServiceGroupsItems();
        for (WebElement srv: services)
            if (srv.findElement(By.xpath(".//div[@class='list-item-text']")).getText().trim().equals(serviceGroupName)) {
                serviceListItem = srv;
                break;
            }
        return serviceListItem;
    }

    public VNextSelectedGroupServicesScreen switchToSelectedGroupServicesView() {
        tap(servicesscreen.findElement(By.xpath(".//*[@action='selected']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='selected' and @class='button active']")));
        wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='labor-list' and @data-view-mode='selected']")));
        return  new VNextSelectedGroupServicesScreen(appiumdriver);
    }
}
