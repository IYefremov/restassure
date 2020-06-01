package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import com.cyberiansoft.test.vnext.webelements.order.edit.PhaseElement;
import com.cyberiansoft.test.vnext.webelements.order.edit.ServiceElement;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PhasesScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@data-autotests-id='phases-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[contains(@class,'phase-item')]/div[contains(@class,'icon-item phase')]")
    private List<PhaseElement> phaseListElements;

    @FindBy(xpath = "//div[@class='phase-services-item']")
    private List<ServiceElement> serviceElementsList;

    @FindBy(xpath = "//*[@action='info']")
    private WebElement infoScreenButton;

    @FindBy(xpath = "//*[@action='parts']")
    private WebElement partsScreenButton;

    @FindBy(xpath = "//*[@class='navbar-panel  main-navbar']/.//*[@class='client-mode']")
    private WebElement workOrderNumber;

    @FindBy(xpath = "//*[@class='vin-number']")
    private WebElement phasesVINNumber;

    @FindBy(xpath = "//*[@class='stock-number']")
    private WebElement phasesStockNumber;

    @FindBy(xpath = "//*[@action='quick-actions']")
    private WebElement phasesMenuButton;

    @FindBy(xpath = "//*[@data-action-name='startServices']")
    private WebElement startServicesButton;

    @FindBy(xpath = "//*[@data-action-name='stopServices']")
    private WebElement stopServicesButton;

    @FindBy(xpath = "//*[@data-action-name='completeServices']")
    private WebElement completeServicesButton;

    @FindBy(xpath = "//*[@class='floating-button color-red']")
    private WebElement addButton;

    @FindBy(xpath = "//*[@class='speed-dial-icon task-button']")
    private WebElement taskButton;

    @FindBy(xpath = "//*[@class='speed-dial-icon all-services-button']")
    private WebElement allServicesButton;

    public PhasesScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
