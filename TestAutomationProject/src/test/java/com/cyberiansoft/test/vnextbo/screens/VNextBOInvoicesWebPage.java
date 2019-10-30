package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOInvoicesWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']")
	private WebElement invoices;

	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']/li")
	private List<WebElement> invoicesList;

	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']//b")
	private List<WebElement> invoiceNumbers;
	
	@FindBy(id = "invoice-details")
	private WebElement invoiceDetailsPanel;

	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']//div[@class='entity-list__item__description']")
	private List<WebElement> invoicesDescriptionBlocks;

	@FindBy(xpath = "//div[@id='invoice-details']//span[@title='Void Invoice']")
	private WebElement voidButton;

	@FindBy(xpath = "//div[@id='invoice-details']//span[@title='Unvoid Invoice']")
	private WebElement unvoidButton;

	@FindBy(xpath = "//section[@id='invoices-view']//div[@class='pull-right header-icons-group']")
	private WebElement headerIcons;

    @FindBy(xpath = "//span[@title='Void selected invoices']")
    private WebElement headerIconVoidButton;

    @FindBy(xpath = "//span[@title='Unvoid selected invoices']")
    private WebElement headerIconUnvoidButton;

	@FindBy(xpath = "//span[@title='Approve selected items']")
	private WebElement headerIconApproveButton;

	@FindBy(xpath = "//span[@title='Archive selected invoices']")
	private WebElement headerIconArchiveButton;

	@FindBy(xpath = "//div[@data-automation-id='invoiceList']")
	private WebElement checkedItemsNote;

	@FindBy(id = "advSearchInvoice-caret")
	private WebElement advancedSearchCaret;

	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']/following-sibling::div[@class='progress-wrapper']")
	private WebElement progressBar;

	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']/following-sibling::div[@class='progress-wrapper progress--active']")
	private WebElement progressBarActive;

	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']/following-sibling::div/div[@class='progress-message' and text()]")
	private WebElement progressMessage;

    @FindBy(xpath = "//span[contains(@class, 'location-name')]")
    private WebElement locationElement;

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
    private WebElement locationExpanded;

    @FindBy(xpath = "//button[contains(@data-bind, 'approveItem')]")
    private WebElement approveInvoiceButton;

    @FindBy(xpath = "//div[@id='invoice-details']//span[@title='Approve invoice']")
    private WebElement approveInvoiceIcon;

    @FindBy(xpath = "//button[contains(@data-bind, 'rollbackApprovalItem')]")
    private WebElement rollbackApprovalButton;

    @FindBy(xpath = "//div[@id='invoice-details']//span[contains(@data-bind, 'rollbackApprovalItem')]")
    private WebElement rollbackApprovalIcon;

    @FindBy(id = "advSearchInvoice-freeText")
    private WebElement searchInputField;

    @FindBy(xpath = "//div[@id='invoices-search']//i[contains(@data-bind, 'click: clear')]")
    private WebElement clearSearchIcon;
	
	public VNextBOInvoicesWebPage() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getInvoiceByName(String invoice) {
        return invoices.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b[text()='" + invoice + "']"));
    }

    public WebElement getInvoiceStatusByName(String invoice) {
        return invoices.findElement(By.xpath("//ul[@data-automation-id='invoiceList']//b[text()='" + invoice + "']/../../div[2]"));
    }
}
