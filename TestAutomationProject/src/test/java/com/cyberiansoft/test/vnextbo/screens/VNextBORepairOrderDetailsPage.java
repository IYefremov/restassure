package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBORepairOrderDetailsPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//*[@data-bind='click: startRO']")
	private WebElement startorderbtn;
	
	@FindBy(xpath = "//div[@class='order-info-details']/div")
	private WebElement orderdetails;
	
	@FindBy(id = "orderServices")
	private WebElement orderservicestable;

    @FindBy(id = "reconmonitordetails-view")
    private WebElement roDetailsSection;

    @FindBy(xpath = "//input[contains(@data-bind, 'data.stockNo')]")
    private WebElement stockNumInputField;

    @FindBy(xpath = "//input[contains(@data-bind, 'data.roNo')]")
    private WebElement roNumInputField;

    @FindBy(xpath = "//span[text()='Phase']")
    private WebElement phaseTextElement;

    @FindBy(xpath = "//h5[@id='breadcrumb']//strong[text()='Repair Orders']")
    private WebElement repairOrdersBackwardsLink;

//    @FindBy(xpath = "//div[@class='status']//span[@title]")
//    private WebElement statusDropDown; //todo use the locator instead of statusListBoxOptions.get(0)
                                        // todo after the developers add the unique identifiers;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']")
    private WebElement statusDropDown;

    @FindBy(xpath = "//div[@class='status']//span[@title]")
    private WebElement statusListBox;

    @FindBy(xpath = "//div[contains(@class, 'priority')]//span[@title]")
    private WebElement priorityListBox;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> statusListBoxOptions; //todo add unique identifiers

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']/li")
    private List<WebElement> priorityListBoxOptions; //todo add unique identifiers

    public VNextBORepairOrderDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        wait.until(ExpectedConditions.visibilityOf(startorderbtn));
    }

    public boolean isRoDetailsSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(roDetailsSection));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
	
	public String getRepairOrderActivePhaseStatus() {
		return orderdetails.findElement(By.id("phaseName")).getText().trim();
	}
	
	public String getRepairOrderCompletedValue() {
		return orderdetails.findElement(By.id("progressBarText")).getText().trim();
	}
	
	public void expandRepairOrderServiceDetailsTable() {
		if (orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).isDisplayed()) {
			orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']")).click();
			new WebDriverWait(driver, 30)
			  .until(ExpectedConditions.visibilityOf(orderservicestable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-up5']"))));
		}
	}
	
	public void clickStartOrderButton() {
		startorderbtn.click();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.invisibilityOf(startorderbtn));
		waitABit(1000);
	}
	
	public boolean isStartOrderButtonVisible() {
		return startorderbtn.isDisplayed();
	}
	
	public String getRepairOrderServicesPhaseStatus() {
		return orderservicestable.findElement(By.xpath(".//div[@class='clmn_5']/div/div")).getText().trim();
	}
	
	public String getRepairOrderServicesStatus(String serviceName) {
		WebElement servicerow = getRepairOrderServiceColumn(serviceName);
		return servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div")).getText().trim();
	}
	
	public void changeStatusForrepairorderService(String serviceName, String newStatus) {
		waitABit(2000);
		WebElement servicerow = getRepairOrderServiceColumn(serviceName);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div/span/span")))).click();
		servicerow = getRepairOrderServiceColumn(serviceName);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(servicerow.findElement(By.xpath(".//div[@class='clmn_5']/div/span/span")))).click();
		waitABit(2000);
		List<WebElement> popups = driver.findElements(By.xpath("//div[@class='k-list-scroller']/ul[@data-role='staticlist']"));
		for (WebElement pp : popups)
			if (pp.isDisplayed()) {
				pp.findElement(By.xpath("./li[text()='" +  newStatus + "']")).click();
				
			}
		WebDriverWait wait = new WebDriverWait(driver, 15, 1);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='k-loading-mask']")));
		waitABit(1000);
	}
	
	public WebElement getRepairOrderServiceColumn(String serviceName) {
		WebElement servicerow = null;
		List<WebElement> servicesrows = orderservicestable.findElement(By.xpath(".//div[@class='innerTable']"))
				.findElements(By.xpath("./div[@class='serviceRow']"));
		for (WebElement srvrow : servicesrows)
			if (srvrow.findElement(By.xpath(".//div[@class='clmn_2']/strong")).getText().trim().equals(serviceName)) {
				servicerow = srvrow;
				break;
			}
		return servicerow;
	}

	public VNextBORepairOrdersWebPage clickBackwardsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(repairOrdersBackwardsLink)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrdersWebPage.class);
    }

	public VNextBORepairOrderDetailsPage typeStockNumber(String stockNumber) {
        clearAndTypeOrderNumber(stockNumInputField, stockNumber);
        return this;
    }

	public VNextBORepairOrderDetailsPage typeRoNumber(String roNumber) {
        clearAndTypeOrderNumber(roNumInputField, roNumber);
        return this;
    }

    private void clearAndTypeOrderNumber(WebElement inputField, String roNumber) {
        clearAndType(inputField, roNumber);
        wait.until(ExpectedConditions.elementToBeClickable(phaseTextElement)).click();
    }

    public VNextBORepairOrderDetailsPage setStatus(String status) {
        clickStatusBox();
        selectStatus(status);
        return this;
    }

    private VNextBORepairOrderDetailsPage clickStatusBox() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(statusListBox)).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage selectStatus(String status) {
//        selectOptionInDropDown(statusDropDown, statusListBoxOptions, status); todo use this line after unique identifiers will be implemented
        selectOptionInDropDown(statusListBoxOptions.get(0), statusListBoxOptions, status);
        return this;
    }

    public VNextBORepairOrderDetailsPage setPriority(String priority) {
        clickPriorityBox();
        selectPriority(priority);
        return this;
    }

    private VNextBORepairOrderDetailsPage clickPriorityBox() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(priorityListBox)).click();
        return this;
    }

    private VNextBORepairOrderDetailsPage selectPriority(String priority) {
//        selectOptionInDropDown(priorityDropDown, priorityListBoxOptions, priority); todo use this line after unique identifiers will be implemented
        selectOptionInDropDown(priorityListBoxOptions.get(0), priorityListBoxOptions, priority);
        return this;
    }
}