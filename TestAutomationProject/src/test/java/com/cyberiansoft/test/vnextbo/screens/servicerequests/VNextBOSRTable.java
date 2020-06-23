package com.cyberiansoft.test.vnextbo.screens.servicerequests;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOSRTable extends VNextBOSRPage {

    @FindBy(xpath = "//div[@class='Table']")
    private WebElement serviceRequestsTable;

    @FindBy(xpath = "//div[contains(@class, 'Table-emptyRow')]")
    private WebElement emptyTable;

    @FindBy(xpath = "//div[@name='StatusBadge_name']")
    private List<WebElement> statusesList;

    @FindBy(xpath = "//div[@name='StatusBadge_letter']")
    private List<WebElement> statusBadgeLettersList;

    @FindBy(xpath = "//div[@name='Id_Customer']")
    private List<WebElement> customersList;

    @FindBy(xpath = "//div[@name='Desc_Customer']")
    private List<WebElement> stockNumbersList;

    @FindBy(xpath = "//div[@name='Id_serviceRequestNameInfo']")
    private List<WebElement> srNumbersList;

    @FindBy(xpath = "//div[@name='Desc_serviceRequestNameInfo']")
    private List<WebElement> assignedList;

    @FindBy(xpath = "//div[@name='Type_serviceRequestNameInfo']")
    private List<WebElement> srTypesList;

    @FindBy(xpath = "//div[@name='Created_Progress']")
    private List<WebElement> createdDatesList;

    @FindBy(xpath = "//div[contains(@class, 'ActionBadges-content--accept')]")
    private List<WebElement> acceptButtonsList;

    @FindBy(xpath = "//div[contains(@class, 'ActionBadges-content--reject')]")
    private List<WebElement> rejectButtonsList;

    @FindBy(xpath = "//div[contains(@class, 'ActionBadges-content--close')]")
    private List<WebElement> closeButtonsList;

    @FindBy(xpath = "//div[text()='Inspection']/preceding-sibling::div[contains(@class, 'DocumentsBlock-count')]")
    private List<WebElement> documentsInspectionsList;

    @FindBy(xpath = "//div[text()='Invoices']/preceding-sibling::div[contains(@class, 'DocumentsBlock-count')]")
    private List<WebElement> documentsInvoicesList;

    @FindBy(xpath = "//div[text()='WO']/preceding-sibling::div[contains(@class, 'DocumentsBlock-count')]")
    private List<WebElement> documentsWoList;

    public VNextBOSRTable() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
