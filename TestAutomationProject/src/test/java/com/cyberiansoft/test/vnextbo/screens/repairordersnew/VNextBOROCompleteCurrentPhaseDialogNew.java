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
public class VNextBOROCompleteCurrentPhaseDialogNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='problem-list-popup']//div[@class='modal-content']")
    private WebElement completeCurrentPhaseDialog;

    @FindBy(xpath = "//div[@id='problem-list-popup']//div[@class='modal-content']//th[text()!='']")
    private List<WebElement> servicesTableColumnsTitles;

    @FindBy(xpath = "(//td[@class='problem-list-cell'])[2]")
    private List<WebElement> servicesNamesList;

    @FindBy(xpath = "(//td[@class='problem-list-cell'])[3]")
    private List<WebElement> problemReasonsList;

    @FindBy(xpath = "//button[contains(@data-bind, 'click: problemList.resolveAction')]")
    private List<WebElement> resolveButtonsList;

    @FindBy(xpath = "//button[@data-bind='click: problemList.cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[contains(@data-bind,'click: problemList.completeAction')]")
    private WebElement completeCurrentPhaseButton;

    public WebElement resolveButtonByServiceName(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//td[contains(text(),'" + service + "')]/ancestor::tr//button[contains(@data-bind, 'click: problemList.resolveAction')]"));
    }

    public WebElement resolvedIconByServiceName(String service) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//td[contains(text(),'" + service + "')]/ancestor::tr//i[@data-bind='visible: isResolved']"));
    }

    public VNextBOROCompleteCurrentPhaseDialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
