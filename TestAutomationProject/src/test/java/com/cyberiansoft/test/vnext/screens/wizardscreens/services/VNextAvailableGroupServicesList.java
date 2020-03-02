package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextAvailableGroupServicesList extends  VNextBaseGroupServicesScreen {

    public VNextAvailableGroupServicesList(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);

    }

    public void selectService(String serviceName) {
        WebElement servicerow = getServiceListItem(serviceName);
        if (servicerow != null) {
            try {
                tap(WaitUtils.waitUntilElementIsClickable(servicerow.findElement(By.xpath(".//*[@action='select-item']"))));
                WaitUtils.waitUntilElementInvisible(By.xpath("//div[@class='notifier-contaier']"));
            } catch (WebDriverException e) {
                WaitUtils.waitUntilElementInvisible(By.xpath("//div[@data-type='approve']"));
                WaitUtils.click(servicerow.findElement(By.xpath(".//*[@action='select-item']")));
            }
            WaitUtils.waitUntilElementInvisible(By.xpath("//div[@data-type='approve']"));
        }
        else
            Assert.fail("Can't find service: " + serviceName);
    }

    public WebElement getServiceListItem(String servicename) {
        WebElement serviceListItem = null;
        List<WebElement> services = getServicesListItems();
        for (WebElement srv: services)
            if (getServiceListItemName(srv).equals(servicename)) {
                serviceListItem = srv;
                break;
            }
        return serviceListItem;
    }

    public List<WebElement> getServicesListItems() {
        return getServicesList().findElements(By.xpath(".//div[@class='checkbox-item']"));
    }

    public WebElement getServicesList() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        return wait.until(ExpectedConditions.visibilityOf(groupservicesscreen.findElement(By.xpath(".//*[@data-autotests-id='labor-list']"))));
    }

    public String getServiceListItemName(WebElement srvlistitem) {
        return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
    }

    public VNextGroupServicesScreen clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='back']")));
        WaitUtils.click(By.xpath("//*[@action='back']"));
        return new VNextGroupServicesScreen(appiumdriver);
    }
}
