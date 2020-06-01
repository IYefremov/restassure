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

    public VNextBOSRTable() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
