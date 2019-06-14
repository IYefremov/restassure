package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextCustomKeyboard;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextQuestionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
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

public class VNextSelectedServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[@data-page='services-list']")
    private WebElement servicesscreen;

    @FindBy(xpath = "//div[@data-page='services-add']")
    private WebElement selectservicesscreen;

    public VNextSelectedServicesScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public VNextSelectedServicesScreen() {
    }

    public void setServiceAmountValue(String serviceName, String amount) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                WaitUtils.click(servicecell);
            BaseUtils.waitABit(500);
            tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
            VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
            String defAmaunt = servicecell.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value");
            keyboard.setFieldValue(defAmaunt, amount);
            WaitUtils.click(servicecell.findElement(By.xpath(".//*[@class='checkbox-item-title']")));
            if (elementExists("//div[@class='modal-text']")) {
                VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
                informationDialog.clickInformationDialogNoButton();
            }

        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void clickServiceAmountField(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
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
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            tap(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")));
            VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
            keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")).getAttribute("value"), quantity);
            WaitUtils.click(servicecell.findElement(By.xpath(".//*[@class='checkbox-item-title']")));
            if (elementExists("//div[@class='modal-text']")) {
                VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
                informationDialog.clickInformationDialogNoButton();
            }
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void addNotesToSelectedService(String serviceName, String notes) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            BaseUtils.waitABit(1000);
            WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
            wait.until(ExpectedConditions.elementToBeClickable(servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']"))));
            servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).clear();
            servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).sendKeys(notes);
            tap(appiumdriver.findElement(By.xpath("//div[@class='checkbox-item-title' and text()='" + serviceName + "']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public String getSelectedServiceNotesValue(String serviceName) {
        String notesvalue = "";
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            notesvalue = servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).getAttribute("value");
            tap(appiumdriver.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + serviceName + "']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        return notesvalue;
    }

    public WebElement expandServiceDetails(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded")) {
                tap(servicecell);
                BaseUtils.waitABit(1000);
            }
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded")) {
                tap(servicecell);
                BaseUtils.waitABit(1000);
            }
        }
        return servicecell;
    }

    public WebElement collapseServiceDetails(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (servicecell.getAttribute("class").contains("accordion-item-expanded")) {
                tap(servicecell.findElement(By.xpath(".//*[@action='toggle-item']")));
                BaseUtils.waitABit(1000);
            }

        }

        return servicecell;
    }

    public VNextQuestionsScreen clickServiceQuestionSection(String serviceName, String questionSectionName) {
        WebElement servicecell = expandServiceDetails(serviceName);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(servicecell.findElement(By.xpath("//*[@action='select-question-section']/input[@value='" +
                questionSectionName + "']"))));
        servicecell.findElement(By.xpath("//*[@action='select-question-section']/input[@value='" +
                questionSectionName + "']")).click();
        return new VNextQuestionsScreen(appiumdriver);
    }

    public VNextNotesScreen clickServiceNotesOption(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            BaseUtils.waitABit(1000);
            servicecell = getSelectedServiceCell(serviceName);
            if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
                tap(servicecell);
            tap(servicecell.findElement(By.xpath(".//div[@action='notes']")));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        return new VNextNotesScreen();
    }

    public void uselectService(String serviceName) {
        WebElement servicecell = getSelectedServiceCell(serviceName);
        if (servicecell != null) {
            tap(servicecell.findElement(By.xpath(".//input[@action='unselect-item']")));
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
        if (servicesscreen.findElement(By.xpath(".//div[@data-autotests-id='all-services']")).isDisplayed())
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
        return getSelectedServicesList().findElements(By.xpath(".//div[contains(@class, 'r360-accordion-item checked-accordion-item')]"));
    }

    public WebElement getSelectedServicesList() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        return wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//div[@data-autotests-id='all-services']"))));
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
}
