package com.cyberiansoft.test.vnextbo.screens.repairOrders;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOROReportProblemDialog extends VNextBOBaseWebPage {

    @FindBy(id = "report-problem-popup")
    private WebElement reportProblemDialog;

    @FindBy(xpath = "//select[@data-text-field='reason']")
    private WebElement problemReason;

    @FindBy(id = "servicePopup-problemDescription")
    private WebElement problemReasonDescription;

    @FindBy(xpath = "//button[contains(@data-bind, 'reportProblemAction')]")
    private WebElement problemReasonAddButton;

    public VNextBOROReportProblemDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
