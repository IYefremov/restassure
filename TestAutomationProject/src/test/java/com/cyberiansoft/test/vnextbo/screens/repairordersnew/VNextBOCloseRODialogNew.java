package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOCloseRODialogNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='closeRoModal']//div[@class='modal-content']")
    private WebElement dialogContent;

    @FindBy(xpath = "//p[text()='Reason']/../span[@aria-owns]")
    private WebElement reasonDropDown;

    @FindBy(xpath = "//button[text()='Close RO']")
    private WebElement closeROButton;

    public WebElement reasonDropDownOption(String reason) {

       return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//ul[@aria-hidden='false']//*[text()='" + reason + "']"));
    }

    public VNextBOCloseRODialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}