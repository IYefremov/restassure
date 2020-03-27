package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOSearchPanel extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_cpFilterer")
    private WebElement searchToggler;

    @FindBy(xpath = "//input[contains(@id, 'BtnFind')]")
    private WebElement searchButton;

    public BOSearchPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getSearchPanel() {
        return searchToggler.findElement(By.tagName("div"));
    }

    public WebElement getSearchTogglerButton() {
        return searchToggler.findElement(By.linkText("Search"));
    }
}
