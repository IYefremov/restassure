package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextSelectedServicesScreen extends VNextBaseInspectionsScreen {

    @FindBy(xpath="//div[@data-page='services-list']")
    private WebElement servicesscreen;

    @FindBy(xpath="//div[@data-page='services-add']")
    private WebElement selectservicesscreen;

    public VNextSelectedServicesScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(servicesscreen));
    }

    public VNextInspectionServicesScreen switchToAvalableServicesView() {
        tap(servicesscreen.findElement(By.xpath(".//*[@action='available']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='available' and @class='button active']")));
        return new VNextInspectionServicesScreen(appiumdriver);
    }

    public VNextSelectedServicesScreen switchToSelectedServicesView() {
        tap(servicesscreen.findElement(By.xpath(".//*[@action='selected']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='selected' and @class='button active']")));
        return  new VNextSelectedServicesScreen(appiumdriver);
    }

    public void setServiceAmountValue(String serviceName, String amount) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
            VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
            keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value"), amount);
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void clickServiceAmountField(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void setServiceQuantityValue(String serviceName, String quantity) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            tap(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")));
            VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
            keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")).getAttribute("value"), quantity);
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void addNotesToSelectedService(String serviceName, String notes) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            BaseUtils.waitABit(1000);
            WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
            wait.until(ExpectedConditions.elementToBeClickable(servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']"))));
            servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).clear();
            servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).sendKeys(notes);
            appiumdriver.hideKeyboard();
            tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public String getSelectedServiceNotesValue(String serviceName) {
        String notesvalue = "";
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            notesvalue = servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).getAttribute("value");
            tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        return notesvalue;
    }

    public VNextNotesScreen clickServiceNotesOption(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            tap(servicecell.findElement(By.xpath(".//div[@action='notes']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        return new VNextNotesScreen(appiumdriver);
    }

    public void uselectService(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell.findElement(By.xpath(".//input[@action='check-item']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public String getSelectedServicePriceValue(String servicename) {
        String serviceprice = "";
        WebElement servicerow = getSelectedServiceCell(servicename);
        if (servicerow != null) {
            serviceprice = servicerow.findElement(By.xpath(".//div[@class='item-subtitle']")).getText().trim();
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
            imagesammary = servicerow.findElement(By.xpath(".//div[@class='img-item summary-item']")).getText().trim();
        } else
            Assert.assertTrue(false, "Can't find service: " + servicename);
        return imagesammary;
    }

    public int getQuantityOfSelectedService(String servicename) {
        return getSelectedServicesList().findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")).size();
    }

    public boolean isServiceSelected(String servicename) {
        boolean selected = false;
        WebElement servicecell = getSelectedServiceCell(servicename);
        if (servicecell != null) {
            if (servicecell.findElement(By.xpath(".//input[@action='check-item']" )).getAttribute("checked") != null)
                if (servicecell.findElement(By.xpath(".//input[@action='check-item']" )).getAttribute("checked").equals("true"))
                    selected = true;
        } else
            Assert.assertTrue(false, "Can't find service: " + servicename);

        return selected;
    }


    public WebElement getSelectedServiceCell(String servicename) {
        WebElement serviceListItem = null;
        List<WebElement> services = getServicesListItems();
        for (WebElement srv: services)
            if (getServiceListItemName(srv).equals(servicename)) {
                serviceListItem =  srv;
                break;
            }
        return serviceListItem;
    }

    public String getServiceListItemName(WebElement srvlistitem) {
        return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
    }

    public List<WebElement> getServicesListItems() {
        return getSelectedServicesList().findElements(By.xpath(".//div[contains(@class, 'r360-accordion-item checked-accordion-item')]"));
    }

    public WebElement getSelectedServicesList() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        return wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//div[@data-autotests-id='all-services']"))));
    }

    public String getSelectedPriceMatrixValueForPriceMatrixService(String matrixservicename) {
        String pricematrixname = "";
        WebElement servicerow = getSelectedServiceCell(matrixservicename);
        if (servicerow != null)
            pricematrixname = servicerow.findElement(By.xpath(".//div[@class='checkbox-item-subtitle checkbox-item-price']")).getText().trim();
        else
            Assert.assertTrue(false, "Can't find service: " + matrixservicename);
        return pricematrixname;
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
        return servicesscreen.findElement(By.xpath(".//span[@id='total']")).getText().trim();
    }


    //============================================================



   /* public VNextServiceDetailsScreen openServiceDetails(String servicename) {
        WebElement servicerow = getSelectedServiceCell(servicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")));
        else
            Assert.assertTrue(false, "Can't find service: " + servicename);
        return new VNextServiceDetailsScreen(appiumdriver);
    }

    public WebElement getServicesList() {
        BaseUtils.waitABit(1000);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        return wait.until(ExpectedConditions.visibilityOf(selectservicesscreen.findElement(By.xpath(".//div[@class='services-list-block']"))));
    }


    public WebElement getServiceListItem(String servicename) {
        WebElement serviceListItem = null;
        List<WebElement> services = getServicesListItems();
        for (WebElement srv: services)
            if (getServiceListItemName(srv).equals(servicename)) {
                serviceListItem =  srv;
                break;
            }
        return serviceListItem;
    }


    public void selectServices(String[] serviceslist) {
        for (String servicename: serviceslist)
            selectService(servicename);
    }

    public void selectService(String servicename) {
        WebElement servicerow = getServiceListItem(servicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//input[@action='select']")));
        else
            Assert.assertTrue(false, "Can't find service: " + servicename);
    }

    public void unselectService(String servicename) {
        WebElement servicerow = getServiceListItem(servicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//input[@class='item-checked big-checkbox green']")));
        else
            Assert.assertTrue(false, "Can't find service: " + servicename);
        BaseUtils.waitABit(5000);
    }

    public VNextPriceMatrixesScreen openMatrixServiceDetails(String matrixservicename) {
        WebElement servicerow = getServiceListItem(matrixservicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + matrixservicename + "']")));
        else
            Assert.assertTrue(false, "Can't find service: " + matrixservicename);
        return new VNextPriceMatrixesScreen(appiumdriver);
    }




    public void selectMatrixService(String matrixservicename) {
        WebElement servicerow = getServiceListItem(matrixservicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//input[@action='select']")));
        else
            Assert.assertTrue(false, "Can't find service: " + matrixservicename);
    }



    public void clickSaveSelectedServicesButton() {
        tap(selectservicesscreen.findElement(By.xpath(".//span[@action='save']")));
    }

    public VNextInspectionServicesScreen clickBackButton() {
        clickScreenBackButton();
        return new VNextInspectionServicesScreen(appiumdriver);
    }*/
}
