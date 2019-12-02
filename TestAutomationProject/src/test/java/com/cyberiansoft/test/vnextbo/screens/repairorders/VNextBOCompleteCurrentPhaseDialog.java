package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOCompleteCurrentPhaseDialog extends VNextBOBaseWebPage {

    @FindBy(id = "problem-list-popup")
    private WebElement completeCurrentPhaseDialog;

    @FindBy(xpath = "//div[@id='problem-list-popup']//th[text()='Service Name']")
    private WebElement serviceNameGrid;

    @FindBy(xpath = "//div[@id='problem-list-popup']//th[text()='Problem Reason']")
    private WebElement problemReasonGrid;

    @FindBy(xpath = "//div[@id='problem-list-popup']//th[text()='Problem Description']")
    private WebElement problemDescriptionGrid;

    @FindBy(xpath = "//div[@id='problem-list-popup']//th[text()='Action']")
    private WebElement actionGrid;

    @FindBy(xpath = "//div[@id='problem-list-popup']//button[@data-automation-id='editDevicePopup-cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@id='problem-list-popup']//button[@data-automation-id='editDevicePopup-submit' and text()='Complete current phase']")
    private WebElement completeCurrentPhaseButton;

    public VNextBOCompleteCurrentPhaseDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getService(String service) {
        return driver.findElement(By.xpath("//div[@id='problem-list-popup']//td[contains(text(), '" + service + "')]"));
    }

    public WebElement getResolveButtonForService(String service) {
        return getService(service)
                .findElement(By.xpath("//..//button[contains(@data-bind, 'problemList.resolveAction')]"));
    }

    public WebElement getResolvedButtonForService(String service) {
        return getService(service)
                .findElement(By.xpath("//..//button/span[text()='Resolved']"));
    }
}
