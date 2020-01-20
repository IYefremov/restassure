package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
public class VNextSelectedServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[@data-page='services-list']")
    private WebElement servicesscreen;

    @FindBy(xpath = "//div[@data-page='services-add']")
    private WebElement selectservicesscreen;

    @FindBy(xpath = "//div[@class='checkbox-item-content-main']")
    private List<ServiceListItem> serviceList;

    public VNextSelectedServicesScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextSelectedServicesScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public void setServiceAmountValue(String serviceName, String serviceAmount) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell);
            VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
            serviceDetailsScreen.setServiceAmountValue(serviceAmount);
            serviceDetailsScreen.clickServiceDetailsDoneButton();
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void setServiceQuantityValue(String serviceName, String serviceQuantity) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell);
            VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
            serviceDetailsScreen.setServiceQuantityValue(serviceQuantity);
            serviceDetailsScreen.clickServiceDetailsDoneButton();
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void clickOnSelectedService(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell);
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public VNextNotesScreen clickServiceNotesOption(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell);
            VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
            serviceDetailsScreen.clickServiceNotesOption();
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        return new VNextNotesScreen();
    }

    public void uselectService(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell.findElement(By.xpath(".//*[@action='unselect-item']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public String getSelectedServicePriceValue(String servicename) {
        String serviceprice = "";
        WebElement servicerow = getSelectedServiceCell(servicename);
        if (servicerow != null) {
            serviceprice = servicerow.findElement(By.xpath(".//div[@class='checkbox-item-subtitle checkbox-item-price']")).getText().trim();
        } else
            Assert.assertTrue(false, "Can't find service: " + servicename);
        return serviceprice;
    }

    public String getSelectedServiceImageSummaryValue(String servicename) {
        String imagesammary = "";
        WebElement servicerow = getSelectedServiceCell(servicename);
        if (servicerow != null) {
            if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicerow);
            if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicerow);
            imagesammary = servicerow.findElement(By.xpath(".//div[@class='img-item summary-item']")).getText().trim();
        } else
            Assert.assertTrue(false, "Can't find service: " + servicename);
        return imagesammary;
    }

    public int getQuantityOfSelectedService(String servicename) {
        return getSelectedServicesList().findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")).size();
    }

    public boolean isServiceSelected(String servicename) {
        boolean exists = false;
        appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        if (servicesscreen.findElement(By.xpath(".//*[@data-autotests-id='all-services']")).isDisplayed())
            exists = getSelectedServicesList().findElements(By.xpath((".//div[@class='checkbox-item-title' and text()='" + servicename + "']"))).size() > 0;
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return exists;
    }


    public WebElement getSelectedServiceCell(String servicename) {
        WebElement serviceListItem = null;
        List<WebElement> services = getServicesListItems();
        for (WebElement srv : services)
            if (getServiceListItemName(srv).equals(servicename)) {
                serviceListItem = srv;
                break;
            }
        return serviceListItem;
    }

    public String getServiceListItemName(WebElement srvlistitem) {
        return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
    }

    public List<WebElement> getServicesListItems() {
        return getSelectedServicesList().findElements(By.xpath(".//*[@action='open-added-service-details']"));
    }

    public WebElement getSelectedServicesList() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        return wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//*[@data-autotests-id='all-services']"))));
    }

    public boolean isPriceMatrixValuePresentForSelectedServicesByName(String serviceName, String matrixservicename) {
        boolean exists = false;
        List<WebElement> servcells = getServicesListItems().stream().
                filter(elemnt -> elemnt.findElement(By.xpath(".//div[@class='checkbox-item-title']")).
                        getText().trim().equals(serviceName)).collect(Collectors.toCollection(ArrayList::new));
        for (WebElement srv : servcells) {
            if (srv.findElement(By.xpath(".//div[@class='checkbox-item-subtitle checkbox-item-price']")).
                    getText().trim().equals(matrixservicename))
                return true;
        }
        return exists;
    }

    public int getNumberOfServicesSelectedByName(String serviceName) {
        return getServicesListItems().stream().
                filter(elemnt -> elemnt.findElement(By.xpath(".//div[@class='checkbox-item-title']")).
                        getText().trim().equals(serviceName)).collect(Collectors.toCollection(ArrayList::new)).size();
    }

    public VNextVehiclePartsScreen openSelectedMatrixServiceDetails(String matrixservicename) {
        WebElement servicerow = getSelectedServiceCell(matrixservicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + matrixservicename + "']")));
        else
            Assert.assertTrue(false, "Can't find service: " + matrixservicename);
        return new VNextVehiclePartsScreen(appiumdriver);
    }

    public String getTotalPriceValue() {
        WaitUtils.elementShouldBeVisible(servicesscreen, true);
        WaitUtils.waitUntilElementIsClickable(servicesscreen.findElement(By.xpath(".//span[@id='total']")));
        return servicesscreen.findElement(By.xpath(".//span[@id='total']")).getText().trim();
    }
}
