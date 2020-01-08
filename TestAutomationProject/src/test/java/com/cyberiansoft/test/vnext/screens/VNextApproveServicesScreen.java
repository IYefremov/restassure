package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextApproveServicesScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='approve-services']")
    private WebElement approveservicesscreen;

    @FindBy(xpath = "//*[@action='select-all' and @value='1']")
    private WebElement approveallbtn;

    @FindBy(xpath = "//*[@action='select-all' and @value='2']")
    private WebElement declineallbtn;

    @FindBy(xpath = "//*[@action='select-all' and @value='3']")
    private WebElement skipallbtn;

    @FindBy(xpath = "//*[@data-autotests-id='sapprove-services-list']")
    private WebElement serviceslist;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement savebtn;

    public VNextApproveServicesScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='approve-services']")));
    }

    public VNextApproveServicesScreen() {
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
    }

    public void clickApproveAllButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        WaitUtils.elementShouldBeVisible(approveallbtn, true);
        wait.until(ExpectedConditions.elementToBeClickable(approveallbtn));
        tap(approveallbtn);
    }

    public void clickDeclineAllButton() {
        WaitUtils.elementShouldBeVisible(declineallbtn, true);
        WaitUtils.click(declineallbtn);
    }

    public void clickSkipAllButton() {
        tap(skipallbtn);
    }

    public boolean isApproveAllButtonDisplayed() {
        return WaitUtils.isElementPresent(approveallbtn);
    }

    public boolean isDeclineAllButtonDisplayed() {
        return WaitUtils.isElementPresent(declineallbtn);
    }

    public boolean isSkipAllButtonDisplayed() {
        return WaitUtils.isElementPresent(skipallbtn);
    }

    public boolean isServicePresentInTheList(String serviceName) {
        return serviceslist.findElements(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']")).size() > 0;
    }

    public String getServicePriceValue(String serviceName) {
        WebElement serviceCell = serviceslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']/../.."));
        return serviceCell.findElement(By.xpath(".//*[@class='entity-item-amount']")).getText().trim();
    }

    public void clickSaveButton() {
        tap(savebtn);
    }

    public void setServiceStatus(String serviceNaame, ServiceStatus serviceStatus) {
        switch (serviceStatus) {
            case APPROVED:
                clickApproveButtonForService(serviceNaame);
                break;
            case DECLINED:
            case SKIPPED:
                clickDeclineButtonForService(serviceNaame);
                break;
        }
    }

    private void clickApproveButtonForService(String serviceName) {
        WebElement serviceRow = getServiceRowByServiceName(serviceName);
        if (serviceRow != null) {
            tap(serviceRow.findElement(By.xpath(".//label[contains(@for, 'approve')]")));
        } else {
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        }
    }

    private void clickDeclineButtonForService(String serviceName) {
        WebElement serviceRow = getServiceRowByServiceName(serviceName);
        if (serviceRow != null) {
            tap(serviceRow.findElement(By.xpath(".//label[contains(@for, 'decline')]")));
        } else {
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        }
    }

    private void clickSkipButtonForService(String serviceName) {
        WebElement serviceRow = getServiceRowByServiceName(serviceName);
        if (serviceRow != null) {
            tap(serviceRow.findElement(By.xpath(".//label[contains(@for, 'skip')]")));
        } else {
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        }
    }

    private WebElement getServiceRowByServiceName(String serviceName) {
        WebElement serviceRow = null;
        List<WebElement> rows = serviceslist.findElements(By.xpath(".//div[contains(@class, 'entity-item accordion-item')]"));
        for (WebElement row : rows) {
            if (row.findElement(By.xpath(".//div[@class='item-title']")).getText().equals(serviceName)) {
                serviceRow = row;
                break;
            }
        }
        return serviceRow;
    }

    public VNextApproveInspectionsScreen clickBackButton() {
        clickScreenBackButton();
        return new VNextApproveInspectionsScreen(appiumdriver);
    }
}
