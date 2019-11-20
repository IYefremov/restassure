package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class VNextBORODetailsPage extends VNextBOBaseWebPage {

	@FindBy(xpath = "//*[@data-bind='click: startRO']")
	private WebElement startOrderButton;

	@FindBy(xpath = "//div[@class='order-info-details']/div")
	private WebElement orderDetails;

	@FindBy(id = "phaseName")
	private WebElement orderDetailsPhaseName;

	@FindBy(id = "orderServices")
	private WebElement orderServicesTable;

	@FindBy(id = "reconmonitordetails-view")
	private WebElement roDetailsSection;

	@FindBy(xpath = "//div[@class='status']//span[@class='k-widget k-dropdown k-header']//span[@class='k-input']")
	private WebElement roStatusElement;

	@FindBy(xpath = "//div[@class='order-info-details']/div[contains(@class, 'on-hold')]")
	private WebElement onHoldValue;

	@FindBy(xpath = "//input[contains(@data-bind, 'data.stockNo')]")
	private WebElement stockNumInputField;

	@FindBy(xpath = "//input[contains(@data-bind, 'data.roNo')]")
	private WebElement roNumInputField;

	@FindBy(xpath = "//span[text()='Phase']")
	private WebElement phaseTextElement;

	@FindBy(className = "breadcrumbs")
	private WebElement mainBreadCrumbsLink;

	@FindBy(xpath = "//strong[contains(@data-bind, 'breadcrumb.last')]")
	private WebElement lastBreadCrumb;

	@FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
	private WebElement locationExpanded;

	@FindBy(xpath = "//div[@class='k-animation-container']")
	private WebElement dropDownContainer;

	@FindBy(id = "reconmonitor-details-status-list")
	private WebElement statusDropDownContainer;

	@FindBy(xpath = "//div[@class='k-animation-container']//ul[@data-role='staticlist']")
	private WebElement statusDropDown;

	@FindBy(xpath = "//input[@data-automation-id='reconmonitor-details-status']/../span")
	private WebElement statusListBox;

	@FindBy(xpath = "//div[contains(@class, 'priority')]//span[@title]")
	private WebElement priorityListBox;

	@FindBy(id = "reconmonitordetails-view-add-order-button")
	private WebElement addNewServiceButton;

	@FindBy(xpath = "//i[@class='switchTable icon-arrow-down5']")
	private WebElement servicesExpandArrow;

	@FindBy(xpath = "//div[@id='order']//i[contains(@class, 'icon-flag')]")
	private WebElement flagIcon;

	@FindBy(xpath = "//div[@id='order']//div[@class='drop flags']")
	private WebElement flagsDropDown;

	@FindBy(xpath = "//button[@title='Log Info']")
	private WebElement logInfoButton;

	@FindBy(xpath = "//div[@class='order-info-details']//span[text()='More information']")
	private WebElement moreInformationLink;

	@FindBy(xpath = "//div[@class='order-info-details']//span[text()='Less information']")
	private WebElement lessInformationLink;

	@FindBy(xpath = "//div[@class='orderInfoWrapper secondLine clearfix']")
	private WebElement moreInformationBlock;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_2']")
	private WebElement phaseName;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_3_1']/span")
	private WebElement phaseVendorPrice;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_4']/div")
	private WebElement phaseVendorTechnician;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_5']//div[contains(@data-bind, 'statusText')]")
	private List<WebElement> phaseStatus;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]")
	private WebElement phaseActionsTrigger;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]//div[@class='drop checkout']")
	private WebElement phaseActionsDropDown;

	@FindBy(xpath = "//div[@data-name]//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]//div[@class='drop checkout hidden']")
	private WebElement phaseActionsDropDownHidden;

	@FindBy(xpath = "//div[@class='drop checkout']/div[contains(@data-bind, 'phaseCheckIn')]")
	private WebElement phaseActionsCheckInOption;

	@FindBy(xpath = "//div[@class='drop checkout']/div[contains(@data-bind, 'phaseCheckOut')]")
	private WebElement phaseActionsCheckOutOption;

	@FindBy(xpath = "//span[contains(@class, 'location-name')]")
	private WebElement locationElement;

	@FindBy(xpath = "//div[@data-template='order-service-item-template']//div[@class='clmn_5']//span[@title]")
	private List<WebElement> servicesStatusWidgetList;

	@FindBy(xpath = "//div[@class='serviceRow theader clearfix']/div")
	private List<WebElement> servicesTableColumns;

	@FindBy(xpath = "//div[@class='row order-info-content']//p/span[text()]")
	private List<WebElement> moreInformationFields;

	@FindBy(xpath = "//strong[contains(@data-bind, 'amountField.totalAmountF')]")
	private WebElement totalServicePrice;

	@FindBy(xpath = "//div[contains(@data-bind, 'cachedServices')]")
	private WebElement toBeAddedLaterServiceNotification;

	@FindBy(xpath = "//div[text()='Vendor Price']")
	private WebElement vendorPriceTitle;

	@FindBy(xpath = "//div[@aria-hidden='false']//span[@data-automation-label='reconmonitor-details-service-status-item']/../../..")
	private WebElement serviceStatusDropDown;

	@FindBy(xpath = "//div[@aria-hidden='false']//span[@data-automation-label='reconmonitor-details-service-status-item']")
	private List<WebElement> serviceStatusListBoxOptions;

	@FindBy(xpath = "//div[contains(@class, 'innerTable')]//div[@class='clmn_3_1']/span")
	private List<WebElement> vendorPricesList;

	@FindBy(xpath = "//tbody[contains(@data-template, 'part-list') and not (contains(@data-bind, 'Cached'))]/tr")
	private List<WebElement> partsList;

	@FindBy(xpath = "//div[contains(@data-bind, 'partActionsVisible')]")
	private List<WebElement> partsActions;

	@FindBy(xpath = "//div[@id='reconmonitordetails-parts']//b")
	private List<WebElement> partsNames;

	@FindBy(xpath = "//div[@id='reconmonitordetails-parts']//td[@class='grid__centered'][4]/div")
	private List<WebElement> partsOrderedFromTableValues;

	@FindBy(xpath = "//ul[@class='k-list k-reset' and @aria-hidden='false']/li")
	private List<WebElement> listBoxOptions;

	@FindBy(xpath = "//span[@data-automation-label='reconmonitor-details-phase-status-item']/..")
	private List<WebElement> phaseStatusListBoxOptions;


	public VNextBORODetailsPage() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public WebElement getFlagColorElement(String color) {
	    return driver.findElement(By.xpath("//div[@class='drop flags']//span[contains(@title, '" + color + "')]"));
    }

    public WebElement getOrderServiceArrowDown() {
	    return orderServicesTable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-down5']"));
    }

    public WebElement getOrderServiceArrowUp() {
	    return orderServicesTable.findElement(By.xpath(".//i[@class='switchTable icon-arrow-up5']"));
    }

    public WebElement getPhaseStatus() {
	    return orderServicesTable.findElement(By.xpath(".//div[@class='clmn_5']/div/div"));
    }

    public List<WebElement> getRepairOrderServicePopups() {
	    return driver.findElements(By.xpath("//div[@class='k-list-scroller']/ul[@data-role='staticlist']"));
    }

    public WebElement getRepairOrderServiceColumn(String serviceName) {
        List<WebElement> servicesRows = orderServicesTable.findElement(By.xpath(".//div[@class='innerTable']"))
                .findElements(By.xpath("./div[@class='serviceRow']"));
        for (WebElement serviceRow : servicesRows) {
            if (Utils.getText(serviceRow.findElement(By.xpath(".//div[@class='clmn_2']/strong"))).trim().equals(serviceName)) {
                return serviceRow;
            }
        }
        return null;
    }

	public WebElement getDropDownContainer() {
		return WaitUtilsWebDriver.waitForVisibility(dropDownContainer, 7);
	}

	public WebElement getStatusDropDownContainer() {
		return WaitUtilsWebDriver.waitForVisibility(statusDropDownContainer, 7);
	}

	public WebElement getVendorBox(String serviceId) {
	    return driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
                + "']//span[contains(@class, 'team-dropdown')]"));
    }

	public WebElement getTechnicianBox(String serviceId) {
	    return driver.findElement(By.xpath("//div[@data-order-service-id='" + serviceId
                + "']//span[contains(@class, 'technician-dropdown')]"));
    }

	public List<WebElement> getPriorityListBoxOptions() {
	    return getDropDownContainer().findElements(By.xpath("//ul[@data-role='staticlist']/li"));
    }

	public WebElement getElementInServicesTable(String serviceId, String xpath) {
		return driver.findElement(By.xpath("//div[@class='serviceRow' and @data-order-service-id='"
				+ serviceId + "']" + xpath));
	}

	@Nullable
	public WebElement getServiceByName(String service) {
		try {
		    return WaitUtilsWebDriver.waitForVisibility(By.xpath("//div[text()='" + service + "']"));
		} catch (NoSuchElementException ignored) {
			try {
				final WebElement element = driver.findElement(By.xpath("//div[contains(text(), '" + service + "')]"));
                WaitUtilsWebDriver.waitForVisibilityIgnoringException(element);
				return element.getText().trim().equals(service) ? element : null;
			} catch (NoSuchElementException ignore) {
                return null;
            }
		}
	}

    @Nullable
    public WebElement getServiceContainingName(String service) {
        try {
            return WaitUtilsWebDriver.waitForVisibility(By.xpath("//div[contains(text(), '" + service + "')]"));
        } catch (NoSuchElementException ignore) {
            return null;
        }
    }

    public WebElement getActionIconForServiceId(String serviceId) {
        return driver.findElement(By
                .xpath("//div[@class='serviceRow' and @data-order-service-id='" +
                        serviceId + "']//div[@class='clmn_7']/div[contains(@class, 'order-service-menu')]"));
    }

    public By getInfoDialog(String serviceId, String status) {
	    return By.xpath("//div[@data-order-service-id='" + serviceId
                        + "']//i[@class='help']/span[text()='" + status + "']");
    }

    public By getServiceCompletedDate(String serviceId) {
	    return By.xpath("//div[@data-order-service-id='" + serviceId + "']//div[@class='clmn_6']//span[text()][2]");
    }

	private WebElement getPartActionElement(int index) {
		try {
			final WebElement partAction = partsActions.get(index);
			System.out.println("element");
			System.out.println(partsActions.get(index));
			Utils.clickElement(partAction);
			return WaitUtilsWebDriver.waitForVisibility(partAction.findElement(By.xpath("./div[@class='drop checkout']")));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public WebElement clickPartActionsIconForPart(String part) {
		try {
			WaitUtilsWebDriver.waitForVisibilityOfAllOptions(partsNames, 10);
		} catch (Exception e) {
			Assert.fail("The Parts section is empty", e);
		}
		try {
			final List<String> partsList = partsNames
					.stream()
					.map(WebElement::getText)
					.collect(Collectors.toList());
			return getPartActionElement(partsList.indexOf(part));
		} catch (Exception e) {
			Assert.fail("The Part hasn't been displayed");
		}
		return null;
	}

	public WebElement getPhaseActionsTrigger(String phase) {
	    return WaitUtilsWebDriver.waitForElementNotToBeStale(By.xpath("//div[@data-name='" + phase
                + "']//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]"));
    }

	public WebElement getPhaseActionsReportProblemOption(String phase) {
	    return WaitUtilsWebDriver.waitForElementNotToBeStale(By.xpath("//div[@data-name='" + phase
                + "']//label[text()='Report Problem']"));
    }

	public WebElement getPhaseActionsResolveProblemOption(String phase) {
	    return WaitUtilsWebDriver.waitForElementNotToBeStale(By.xpath("//div[@data-name='" + phase
                + "']//label[text()='Resolve Problem']"));
    }

	public WebElement getCompleteCurrentPhaseActionsOption(String phase) {
	    return WaitUtilsWebDriver.waitForElementNotToBeStale(By.xpath("//div[@data-name='" + phase
                + "']//div[contains(@data-bind, 'showCompletePhase')]"));
    }

	public WebElement getPhaseProblemIcon(String phase) {
	    return WaitUtilsWebDriver.waitForElementNotToBeStale(By.xpath("//div[@data-name='" + phase
                + "']//i[@class='icon-problem-indicator']"));
    }

	public WebElement getServiceProblemIcon(String serviceId) {
	    return WaitUtilsWebDriver.waitForElementNotToBeStale(By.xpath("//div[@data-order-service-id='" + serviceId
                + "']//i[@class='icon-problem-indicator']"));
    }

    public WebElement clickActionsIcon(String serviceId) {
        final WebElement actionsIcon = new VNextBORODetailsPage().getActionIconForServiceId(serviceId);
        Utils.clickWithActions(actionsIcon);
        WaitUtilsWebDriver.waitForVisibility(actionsIcon.findElement(By.xpath("./div[@class='drop checkout']")));
        return actionsIcon;
    }

    public WebElement getPhaseStatusBox(String phaseStatusBox) {
	    return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phaseStatusBox +
                "']//div[contains(@class, 'clmn_5')]//span[contains(@class, 'group-status-dropdown')]"));
    }

    public WebElement getPhaseStatusBoxValue(String phaseStatusBox) {
	    return getPhaseStatusBox(phaseStatusBox).findElement(By.xpath(".//span[contains(@class, 'k-input')]"));
    }

    public WebElement getActionsTriggerForPhase(String phase) {
        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase
                + "']//div[@class='clmn_7']/div[contains(@data-bind, 'actions')]"));
    }

    public WebElement getServiceReportProblemOption(String serviceId) {
        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-order-service-id='"
                + serviceId + "']//label[text()='Report Problem']"));
    }

    public WebElement getServiceResolveProblemOption(String serviceId) {
        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-order-service-id='"
                + serviceId + "']//label[text()='Resolve Problem']"));
    }

    public WebElement getServiceStatusByServiceId(String serviceId) {
	    return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-order-service-id='" + serviceId
                        + "']//span[contains(@class, 'service-status-dropdown')]//span[@class='k-input']"));
    }

    public WebElement getServiceStatusBoxByServiceId(String serviceId) {
	    return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-order-service-id='" + serviceId
                        + "']//div[contains(@data-bind, 'orderServiceStatusName')]/../span[@title]"));
    }
}