package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOLogInfoDialogNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@id, 'log') and contains(@style, 'block')]//li[contains(@data-bind, 'services')]")
    private WebElement servicesTab;

    @FindBy(xpath = "(//div[@id='repairOrder_servicesLog']//div[@data-bind='text: serviceName'])[1]")
    private WebElement firstRecordService;

    @FindBy(xpath = "(//div[@id='repairOrder_servicesLog']//div[@data-bind='text: phaseName'])[1]")
    private WebElement firstRecordPhase;

    @FindBy(xpath = "(//div[@id='repairOrder_servicesLog']//div[@data-bind='text: serviceStatusName'])[1]")
    private WebElement firstRecordStatus;

    @FindBy(xpath = "(//div[@id='repairOrder_servicesLog']//div[@data-bind='text: notes'])[1]")
    private WebElement firstRecordNote;

    @FindBy(xpath = "//div[contains(@id, 'log') and contains(@style, 'block')]//button[@class='close']")
    private WebElement closeDialogButton;

    public VNextBOLogInfoDialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}