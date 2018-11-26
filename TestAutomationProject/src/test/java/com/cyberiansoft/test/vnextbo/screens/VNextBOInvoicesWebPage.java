package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

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
	
	public VNextBOInvoicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(invoices));
	}

	public boolean areHeaderIconsDisplayed() {
	    wait.until(ExpectedConditions.visibilityOf(headerIcons));
	    return headerIconVoidButton.isDisplayed() &&
                headerIconApproveButton.isDisplayed() &&
                headerIconArchiveButton.isDisplayed();
    }

    public VNextConfirmationDialog clickHeaderIconVoidButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(headerIconVoidButton)).click();
	    return PageFactory.initElements(driver, VNextConfirmationDialog.class);
    }

    public VNextConfirmationDialog clickHeaderIconUnvoidButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(headerIconUnvoidButton)).click();
	    return PageFactory.initElements(driver, VNextConfirmationDialog.class);
    }

	public void selectInvoiceInTheList(String invoice) {
		getInvoiceByName(invoice).click();
		waitForLoading();
	}

    public WebElement getInvoiceByName(String invoice) {
        return invoices.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b[text()='" + invoice + "']"));
    }

    public VNextBOInvoicesWebPage clickCheckbox(String ...invoiceNames) {
        for (String invoiceName : invoiceNames) {
            getInvoiceByName(invoiceName).findElement(By.xpath(".//../../..//input")).click();
            waitABit(1000);
        }
        return this;
    }

    public String getSelectedInvoiceCustomerName() {
		return invoiceDetailsPanel.findElement(By.xpath(".//h5[@data-bind='text: customer.clientName']")).getText();
	}
	
	public String getSelectedInvoiceNote() {
		return invoiceDetailsPanel.findElement(By.xpath(".//p[@data-bind='text: note']")).getText();
	}

    public String getFirstInvoiceName() {
	    return getInvoiceName(0);
    }

    public String getInvoiceName(int index) {
	    return wait.until(ExpectedConditions.visibilityOfAllElements(invoiceNumbers)).get(index).getText();
    }

    public String[] getFirstInvoiceNames(int number) {
        wait.until(ExpectedConditions.visibilityOfAllElements(invoiceNumbers));
        String[] invoices = new String[number];
        for (int i = 0; i < number; i++) {
            invoices[i] = invoiceNumbers.get(i).getText();
        }
        return invoices;
    }

    public VNextBOInvoicesWebPage clickFirstInvoice() {
	    wait.until(ExpectedConditions.visibilityOfAllElements(invoiceNumbers)).get(0).click();
	    waitForLoading();
	    return this;
    }

    public VNextConfirmationDialog clickVoidButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(voidButton)).click();
	    return PageFactory.initElements(driver, VNextConfirmationDialog.class);
    }

    public void scrollInvoices() {
        while (true) {
            scrollDownToLastInvoice();
            waitABit(1000);
            try {
                waitShort.until(ExpectedConditions.visibilityOf(progressMessage));
                break;
            } catch (Exception ignored) {}
        }
    }

    public boolean isInvoiceDisplayed(String invoice) {
        wait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(invoiceNumbers));
        return invoiceNumbers.stream().anyMatch(n -> n.getText().equals(invoice));
    }

    private void scrollDownToLastInvoice() {
        final WebElement invoice = waitShort
                .until(ExpectedConditions.visibilityOf(invoicesList.get(invoicesList.size() - 1)));
        scrollToElement(invoice);
    }

    public String getCheckedItemsNote() {
	    return wait.until(ExpectedConditions.visibilityOf(checkedItemsNote)).getText();
	}

	public AdvancedSearchInvoiceForm clickAdvancedSearchCaret() {
	    wait.until(ExpectedConditions.elementToBeClickable(advancedSearchCaret)).click();
        return PageFactory.initElements(driver, AdvancedSearchInvoiceForm.class);
    }
}
