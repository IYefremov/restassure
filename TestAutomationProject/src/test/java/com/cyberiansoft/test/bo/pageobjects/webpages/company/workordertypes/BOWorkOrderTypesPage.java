package com.cyberiansoft.test.bo.pageobjects.webpages.company.workordertypes;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BOWorkOrderTypesPage extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00")
    private WebTable woTypesTable;

    @FindBy(xpath = "//table[@id='ctl00_ctl00_Content_Main_qv_ctl00']//input[contains(@title, 'Edit')]")
    private List<WebElement> editButtonsList;

    public BOWorkOrderTypesPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getWOTypeList() {
        return woTypesTable.getWrappedElement().findElements(
                By.xpath(".//td[" + woTypesTable.getTableColumnIndex("Type") + "]"));
    }
}
