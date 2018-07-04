package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Iterator;
import java.util.Set;

public class DuplicateInspectionsWebPage extends BaseWebPage {

    @FindBy(xpath = "//input[contains(@id, 'Next_ctl00_ctl00')]")
    private WebElement nextPage;

    public DuplicateInspectionsWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public void switchToDuplicateInspectionPage() {
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> it = handles.iterator();
        while (it.hasNext()) {
            it.next();
            String newWindow = it.next();
            driver.switchTo().window(newWindow);
        }
    }

    public boolean isInspectionDisplayed(String inspection) {
        try {
            wait.until(ExpectedConditions.visibilityOf(driver
                    .findElement(By.xpath("//div/a[contains(text(), '" + inspection + "')]"))))
                    .isDisplayed();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isROdisplayedForInspection(String inspection, String ROnum) {
        try {
            wait.until(ExpectedConditions.visibilityOf(driver
                    .findElement(By.xpath("//td[contains(text(), '" + ROnum +
                            "')]/preceding::td/a[contains(text(), '" + inspection + "')]"))))
                    .isDisplayed();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVINdisplayed(String VINnum) {
        try {
            wait.until(ExpectedConditions.visibilityOf(driver
                    .findElement(By.xpath("//div[contains(text(), '" + VINnum + "')]"))))
                    .isDisplayed();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
