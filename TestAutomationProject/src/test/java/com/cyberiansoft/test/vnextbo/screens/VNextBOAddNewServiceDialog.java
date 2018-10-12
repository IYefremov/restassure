package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOAddNewServiceDialog extends VNextBOBaseWebPage {
	
	@FindBy(id = "service-popup")
	private WebElement newservicepopup;

	@FindBy(xpath = "//div[@class='modal fade in' and contains(@style, 'display: block;')]")
	private WebElement newServicePopupDisplayed;
	
	@FindBy(xpath = "//input[@data-automation-id='servicePopup-serviceName']")
	private TextField servicenamefld;
	
	@FindBy(xpath = "//span[@aria-owns='popup-services-type_listbox']/span/span/span")
	private WebElement servicetypecmb;
	
	@FindBy(xpath = "//textarea[@data-automation-id='servicePopup-description']")
	private TextField servicedescfld;
	
//	@FindBy(xpath = "//span[@aria-owns='price-type_listbox']/span/span")
	@FindBy(xpath = "//span[@aria-owns='price-type_listbox']/span/span[@class='k-input']")
	private WebElement servicepricetypecmb;
	
	@FindBy(xpath = "//input[@id='priceForMoneyType']/preceding-sibling::input")
	private WebElement servicePriceField;
	
	@FindBy(id = "priceForMoneyType")
	private WebElement servicePriceTypingField;
	
	@FindBy(id = "priceForPercentageType")
	private TextField servicepercentagefld;
	
	@FindBy(xpath = "//div[@class='errorMessege']/p")
	private WebElement errormsg;

	@FindBy(xpath = "//div[@data-bind='visible: priceTypes.visible']//span[@class='k-dropdown-wrap k-state-default']")
	private WebElement priceTypeClosed;

	@FindBy(xpath = "//div[@data-bind='visible: priceTypes.visible']//span[contains(@class, 'k-widget k-dropdown k-header')]")
	private WebElement priceTypeDropDown;

	@FindBy(xpath = "//button[@data-automation-id='servicePopup-submit']")
	private WebElement saveNewServiceButton;

	@FindBy(xpath = "//div[@data-bind='visible: priceFieldVisible']//input[@id='priceForMoneyType']")
	private WebElement laborRateTypingField;

	@FindBy(xpath = "//div[@data-bind='visible: laborTimes.isVisible']//input[@id='laborTime']")
	private WebElement defaultLaborTimeField;

	@FindBy(xpath = "//div[@data-bind='visible: laborTimes.isVisible']//input[@type='checkbox']")
	private WebElement useLaborTimesCheckbox;

	@FindBy(xpath = "//div[@data-bind='visible: laborTimes.useLaborTimes']/label[@id='servicePopup-laborTime-source_label']/..")
	private WebElement serviceSource;

	@FindBy(xpath = "//div[@data-bind='visible: laborTimes.useLaborTimes']/label[@id='servicePopup-laborTime-operation_label']/..")
	private WebElement serviceOperation;

	final By serviceaddbtnxpath = By.xpath(".//button[@data-automation-id='servicePopup-submit']");
	final By closenewservicedialogbtnxpath = By.xpath(".//button[@class='close']");
	
	public VNextBOAddNewServiceDialog(WebDriver driver) {
		super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.visibilityOf(newServicePopupDisplayed));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public VNexBOServicesWebPage addNewService(String servicename, String servicetype, String servicedesc, String servicepricetype, String serviceprice) {
		setServiceName(servicename);
		selectServiceType(servicetype);
		setServiceDescription(servicedesc);
		selectServicePriceType(servicepricetype);
		setServicePriceValue(serviceprice);
        clickSaveNewServiceButton();
        waitForLoading();
		return PageFactory.initElements(driver, VNexBOServicesWebPage.class);
	}

	public VNexBOServicesWebPage addNewService(String servicename, String servicedesc, String servicepricetype, String serviceprice) {
		setServiceName(servicename);
		setServiceDescription(servicedesc);
		selectServicePriceType(servicepricetype);
		setServicePriceValue(serviceprice);
        clickSaveNewServiceButton();
        waitForLoading();
		return PageFactory.initElements(driver, VNexBOServicesWebPage.class);
	}
	
	public VNexBOServicesWebPage addNewPercentageService(String servicename, String servicetype, String servicedesc, String servicepricetype, String serviceprice) {
		setServiceName(servicename);
		selectServiceType(servicetype);
		setServiceDescription(servicedesc);
		selectServicePriceType(servicepricetype);
		setServicePercentageValue(serviceprice);
		return saveNewService();
	}
	
	public VNextBOAddNewServiceDialog setServiceName(String servicename) {
		wait.until(ExpectedConditions.elementToBeClickable(servicenamefld.getWrappedElement()));
        servicenamefld.clearAndType(servicename);
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
	}
	
	public String getServiceName() {
		return servicenamefld.getValue();
	}
	
	public String getServiceType() {
		return servicetypecmb.getText();
	}
	
	public VNextBOAddNewServiceDialog selectServiceType(String servicetype) {
		wait.until(ExpectedConditions.elementToBeClickable(servicetypecmb)).click();
		waitABit(300);
		waitLong.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//ul[@id='popup-services-type_listbox']/li/span[text()='" + servicetype + "']"))))
                .click();
		waitABit(500);
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }
	
	public VNextBOAddNewServiceDialog setServiceDescription(String servicedesc) {
	    wait.until(ExpectedConditions.elementToBeClickable(servicedescfld.getWrappedElement()));
	    servicedescfld.clearAndType(servicedesc);
	    waitABit(500);
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }
	
	public String getServiceDescription() {
		return servicedescfld.getValue();
	}
	
	public VNextBOAddNewServiceDialog selectServicePriceType(String servicepricetype) {
		wait.until(ExpectedConditions.elementToBeClickable(servicepricetypecmb)).click();
		waitABit(300);
		waitLong
                .until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//ul[@id='price-type_listbox']/li/div/span[text()='" + servicepricetype + "']"))))
                .click();
        try {
            wait.until(ExpectedConditions.visibilityOf(priceTypeClosed));
        } catch (Exception ignored) {
            waitABit(500);
        }
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }
	
	public boolean isServicePriceTypeVisible() {
		return driver.findElements(By.xpath("//span[@aria-owns='price-type_listbox']/span/span")).size() > 0;
	}
	
	public VNextBOAddNewServiceDialog setServicePriceValue(String servicepricevalue) {
//		WebElement pricepercentagefld = getServicePricePercentageValueTxtField();
//		Actions act = new Actions(driver);
//		act.click(waitLong.until(ExpectedConditions.elementToBeClickable(pricepercentagefld)));
//		pricepercentagefld.clear();

        setAttributeWithJS(servicePriceTypingField, "style", "display: inline-block;");
        servicePriceTypingField.clear();
        servicePriceTypingField.sendKeys(servicepricevalue);
        setAttributeWithJS(servicePriceTypingField, "aria-valuenow", servicepricevalue);
        setAttributeWithJS(priceTypeDropDown, "aria-expanded", "false");
        waitABit(500);
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }

	public VNextBOAddNewServiceDialog setServiceLaborRate(String laborRateValue) {
        setAttributeWithJS(laborRateTypingField, "style", "display: inline-block;");
        laborRateTypingField.clear();
        laborRateTypingField.sendKeys(laborRateValue);
        setAttributeWithJS(laborRateTypingField, "aria-valuenow", laborRateValue);
        waitABit(500);
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }

	public VNextBOAddNewServiceDialog setServiceDefaultLaborTime(String defaultLaborTime) {
        setAttributeWithJS(defaultLaborTimeField, "style", "display: inline-block;");
        defaultLaborTimeField.clear();
        defaultLaborTimeField.sendKeys(defaultLaborTime);
        setAttributeWithJS(defaultLaborTimeField, "aria-valuenow", defaultLaborTime);
        waitABit(500);
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }

    public VNextBOAddNewServiceDialog checkUseLaborTimesCheckbox() {
//        wait.until(ExpectedConditions.visibilityOf(serviceSource));
        waitABit(1000);
        try {
            if (serviceSource.getAttribute("style").contains("display: none;")) {
                wait.until(ExpectedConditions.elementToBeClickable(useLaborTimesCheckbox)).click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }

    public VNextBOAddNewServiceDialog setServicePercentageValue(String servicepercentagevalue) {
		WebElement pricepercentagefld = getServicePricePercentageValueTxtField();				
		Actions act = new Actions(driver);
		act.click(waitLong.until(ExpectedConditions.elementToBeClickable(pricepercentagefld)));
		pricepercentagefld.clear();
		waitLong.until(ExpectedConditions.elementToBeClickable(servicepercentagefld.getWrappedElement()));
		servicepercentagefld.clearAndType(servicepercentagevalue);
        return PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
    }
	
	public WebElement getServicePricePercentageValueTxtField() {
		WebElement pricepercentagefld = null;
		List<WebElement> priceflds = driver.findElements(By.xpath("//span[@class='k-numeric-wrap k-state-default k-expand-padding']/input[@class='k-formatted-value k-input']"));
		for (WebElement elm : priceflds) {
			if (elm.isDisplayed()) {				
				pricepercentagefld = elm;
			}
		}
		return pricepercentagefld;
	}
	
	public VNexBOServicesWebPage saveNewService() {
		clickServiceAddButton();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(serviceaddbtnxpath));
        waitForLoading();
		return PageFactory.initElements(
				driver, VNexBOServicesWebPage.class);
	}
	
	public void clickServiceAddButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(newservicepopup.findElement(serviceaddbtnxpath))).click();
        } catch (Exception e) {
            Assert.fail("Tha \"Add service\" button has not been clicked!");
        }
    }

	public VNexBOServicesWebPage clickSaveNewServiceButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(saveNewServiceButton));
        } catch (Exception ignored) {}
        try {
            clickWithJS(saveNewServiceButton);
            try {
                wait.until(ExpectedConditions.attributeToBe(saveNewServiceButton, "disabled", "disabled"));
            } catch (Exception ignored) {}
        } catch (Exception e) {
            Assert.fail("Tha \"Add service\" button has not been clicked!", e);
        }
        waitForLoading();
        return PageFactory.initElements(driver, VNexBOServicesWebPage.class);
    }
	
	public VNexBOServicesWebPage closeNewServiceDialog() {
		newservicepopup.findElement(closenewservicedialogbtnxpath).click();
		waitABit(500);
		return PageFactory.initElements(
				driver, VNexBOServicesWebPage.class);
	}
	
	public String getErrorMessage() {
		return errormsg.getText();
	}

}
