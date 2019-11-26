package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOCalendarWidgetDialog extends VNextBOBaseWebPage {

    @FindBy(id = "advSearch_fromDate_dateview")
    private WebElement fromDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearch_toDate_dateview']")
    private WebElement toDateCalendarWidget;

    public VNextBOCalendarWidgetDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getDate(String date) {
        return driver.findElements(By.xpath("//a[@data-value='" + date + "']"));
    }
}