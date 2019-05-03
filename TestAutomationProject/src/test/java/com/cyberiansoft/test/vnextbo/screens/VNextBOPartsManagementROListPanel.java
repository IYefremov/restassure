package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOPartsManagementROListPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']")
    private WebElement roListPanel;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']")
    private List<WebElement> roListOptions;

    public boolean isPartsManagementROListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(roListPanel));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public WebElement getRandomRO() {
        final int size = getROListSize();
        try {
            if (size > 1) {
                final int random = RandomUtils.nextInt(1, size);
                System.out.println("Random number from the list of ROs: " + random);
                return roListOptions.get(random);
            } else if (size == 1) {
                return roListOptions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getROListSize() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(roListOptions));
            return roListOptions.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public VNextBOPartsManagementROListPanel clickRO(WebElement ro) {
        wait.until(ExpectedConditions.elementToBeClickable(ro)).click();
        waitForLoading();
        return this;
    }

    public VNextBOPartsManagementROListPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}