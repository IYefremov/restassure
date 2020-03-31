package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class VNextBOBreadCrumbPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
    private WebElement locationExpanded;

    @FindBy(xpath = "//div[contains(@class, 'menu-item active')]/label")
    private WebElement activeLocation;

    @FindBy(xpath = "//ul[@class='scroll-pane-locations']//label")
    private List<WebElement> locationsList;

    @FindBy(xpath = "//div[@class='drop department-drop']")
    private WebElement locationsDropDown;

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']/ul[@class='scroll-pane-locations']//label")
    private List<WebElement> locationLabels;

    @FindBy(id = "locSearchInput")
    private WebElement locationSearchInput;

    @FindBy(xpath = "//span[contains(@class, 'location-name')]")
    private WebElement locationName;

    @FindBy(className = "breadcrumbs")
    private WebElement breadCrumbsLink;

    @FindBy(xpath = "//div[@class='breadcrumbs']//a")
    private WebElement firstBreadCrumbElement;

    @FindBy(xpath = "//strong[contains(@data-bind, 'breadcrumb.last')]")
    private WebElement lastBreadCrumb;

    public VNextBOBreadCrumbPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}