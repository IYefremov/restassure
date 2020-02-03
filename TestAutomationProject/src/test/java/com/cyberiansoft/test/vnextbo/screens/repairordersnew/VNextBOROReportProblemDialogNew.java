package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOROReportProblemDialogNew extends VNextBOBaseWebPage {

    @FindBy(id = "report-problem-popup")
    private WebElement reportProblemDialog;

    @FindBy(xpath = "//select[@data-text-field='reason']")
    private WebElement problemReasonDropDown;

    @FindBy(xpath = "//div[@id='report-problem-popup']//option")
    private List<WebElement> problemReasonsDropDownList;

    @FindBy(xpath = "//textarea[@id='servicePopup-problemDescription']")
    private WebElement problemReasonDescription;

    @FindBy(xpath = "//button[@data-bind='click: reportProblemAction']")
    private WebElement addButton;

    public WebElement problemReasonDropDownFieldOption(String reason) {

        return driver.findElement(By.xpath("//div[@id='report-problem-popup']//option[text()='" + reason + "']"));
    }

    public VNextBOROReportProblemDialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
