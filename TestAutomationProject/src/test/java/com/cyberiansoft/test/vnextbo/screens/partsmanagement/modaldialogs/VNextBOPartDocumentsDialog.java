package com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPartDocumentsDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='part-entity-details']//div[@id='part-documents-popup']")
    private WebElement partDocumentsDialog;

    @FindBy(xpath = "//span[contains(@data-bind, 'partDocumentTypeName')]")
    private List<WebElement> typeList;

    @FindBy(xpath = "//span[contains(@data-bind, 'documentDateFormatted')]")
    private List<WebElement> dateList;

    @FindBy(xpath = "//span[contains(@data-bind, 'dueDateFormatted')]")
    private List<WebElement> dueDateList;

    @FindBy(xpath = "//span[contains(@data-bind, 'amountFormatted')]")
    private List<WebElement> amountList;

    @FindBy(xpath = "//span[contains(@data-bind, 'notes')]")
    private List<WebElement> notesList;

    @FindBy(xpath = "//a[contains(@data-bind, 'attachmentName')]")
    private List<WebElement> attachmentList;

    @FindBy(xpath = "//button[contains(@data-bind, 'onDocumentDelete')]")
    private List<WebElement> deleteButtons;

    public WebElement getAddNewDocumentButton() {
        return partDocumentsDialog.findElement(By.xpath(".//button[contains(@data-bind, 'onDocumentAdd')]"));
    }

    public WebElement getXIcon() {
        return partDocumentsDialog.findElement(By.xpath(".//button[@aria-label='Close']"));
    }

    public List<WebElement> getNumberList() {
        return partDocumentsDialog.findElements(By.xpath(".//span[contains(@data-bind, 'number')]"));
    }

    public VNextBOPartDocumentsDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
