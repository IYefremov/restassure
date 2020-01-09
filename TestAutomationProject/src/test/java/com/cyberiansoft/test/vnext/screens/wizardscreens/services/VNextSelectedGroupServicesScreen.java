package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextSelectedGroupServicesScreen extends VNextBaseGroupServicesScreen {


    public VNextSelectedGroupServicesScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public void uselectService(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell.findElement(By.xpath(".//*[@action='unselect-item']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public WebElement getSelectedServiceCell(String servicename) {
        WebElement serviceListItem = null;
        List<WebElement> services = getServicesListItems();
        for (WebElement srv: services)
            if (getServiceListItemName(srv).equals(servicename)) {
                serviceListItem =  srv;
                break;
            }
        return serviceListItem;
    }

    public String getServiceListItemName(WebElement srvlistitem) {
        return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
    }

    public List<WebElement> getServicesListItems() {
        return getSelectedServicesList().findElements(By.xpath(".//*[@action='edit-item']"));
    }
    public WebElement getSelectedServicesList() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        return wait.until(ExpectedConditions.visibilityOf(groupservicesscreen.findElement(By.xpath(".//*[@data-autotests-id='labor-list']"))));
    }

    public VNextGroupServicesScreen switchToGroupServicesScreen() {
        tap(groupservicesscreen.findElement(By.xpath(".//*[@action='available']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='available' and @class='button active']")));
        wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='all-services']")));
        return  new VNextGroupServicesScreen(appiumdriver);
    }


}
