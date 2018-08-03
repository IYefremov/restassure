package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class ServiceRequestListServiceDialog extends BaseWebPage {

    @FindBy(xpath = "//div[@class='infoBlock-item infoBlock-edit servicesBlock']")
    private WebElement servicesBlock;

    @FindBy(id = "Card_comboService_Arrow")
    private WebElement serviceComboArrow;

    @FindBy(xpath = "//form/div[@class='rcbSlide servicesBlock_drop']")
    private WebElement serviceComboDropDown;

    @FindBy(xpath = "//form/div[@class='rcbSlide servicesBlock_drop']//ul/li/input[@class='templateServiceCheckbox']")
    private List<WebElement> serviceComboDropDownCheckboxOptions;

    @FindBy(id = "Card_btnAddServiceToList")
    private WebElement addServiceOptionButton;

    @FindBy(xpath = "//div[@class='container-service container-service-selected']")
    private List<WebElement> selectedServiceContainer;

    @FindBy(xpath = "//div[@class='container-service container-service-selected']/span[@id='showVehiclePart']")
    private WebElement selectedServiceContainerFirstVehiclePart;

    @FindBy(xpath = "//div[@class='infoBlock-list-doneBtn rp-btn-blue']")
    private WebElement servicesDoneButton;

    @FindBy(xpath = "//div[@class='infoBlock-cancelBtn cancelList rp-btn-grey']")
    private WebElement servicesCancelButton;

    public ServiceRequestListServiceDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        try {
            wait.until(ExpectedConditions.attributeContains(servicesBlock, "display", "block"));
        } catch (Exception e) {
            Assert.fail("The services edit block has not been opened", e);
        }
    }

    public ServiceRequestListServiceDialog openServicesDropDown() {
        wait.until(ExpectedConditions.textToBePresentInElement(serviceComboArrow, "select"));
        wait.until(ExpectedConditions.elementToBeClickable(serviceComboArrow)).click();
        wait.until(ExpectedConditions.attributeContains(serviceComboDropDown, "display", "block"));
        return this;
    }

    public String getAllAvailableServices() {
        wait.until(ExpectedConditions.elementToBeClickable(serviceComboArrow)).click();
        wait.until(ExpectedConditions.attributeContains(serviceComboDropDown, "display", "block"));
        return driver.findElement(By.id("Card_comboService_MoreResultsBox")).findElements(By.tagName("b")).get(2)
                .getText();
    }

    public int countAvailableServices() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(serviceComboDropDownCheckboxOptions)).size();
    }

    public ServiceRequestListServiceDialog checkRandomServiceOption() {
        int i = RandomUtils.nextInt(0, countAvailableServices());
        wait.until(ExpectedConditions.elementToBeClickable(serviceComboDropDownCheckboxOptions.get(i))).click();
        return this;
    }

    public ServiceRequestListServiceDialog clickAddServiceOption() {
        wait.until(ExpectedConditions.elementToBeClickable(addServiceOptionButton)).click();
        wait.until(ExpectedConditions.attributeContains(serviceComboDropDown, "display", "none"));
        return this;
    }

    public ServiceRequestListServiceVehiclePartDialog clickVehiclePart() {
        wait.until(ExpectedConditions.elementToBeClickable(selectedServiceContainerFirstVehiclePart)).click();
        return PageFactory.initElements(driver, ServiceRequestListServiceVehiclePartDialog.class);
    }

    public boolean isSelectedServiceContainerDisplayed() {
        return !wait.until(ExpectedConditions.visibilityOfAllElements(selectedServiceContainer)).isEmpty();
    }

    public int getNumberOfSelectedServiceContainersDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(selectedServiceContainer)).size();
    }

    public ServiceRequestListServiceDialog verifyOneServiceContainerIsDisplayed() {
        Assert.assertEquals(getNumberOfSelectedServiceContainersDisplayed(), 1,
                "The number of service containers displayed is not 1");
        return this;
    }

    public ServiceRequestsListWebPage clickDoneServicesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(servicesDoneButton)).click();
        return PageFactory.initElements(driver, ServiceRequestsListWebPage.class);
    }

    public ServiceRequestsListWebPage clickCancelServicesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(servicesCancelButton)).click();
        return PageFactory.initElements(driver, ServiceRequestsListWebPage.class);
    }
}
