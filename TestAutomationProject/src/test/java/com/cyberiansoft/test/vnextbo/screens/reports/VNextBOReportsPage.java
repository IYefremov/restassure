package com.cyberiansoft.test.vnextbo.screens.reports;

import com.cyberiansoft.test.baseutils.Utils;
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
public class VNextBOReportsPage extends VNextBOBaseWebPage {

    @FindBy(id = "timeReports-view")
    private WebElement reportsBlock;

    @FindBy(xpath = "//div[contains(@class, 'Table-header')]/div[text()='Report']")
    private WebElement tableHeaderReport;

    @FindBy(xpath = "//div[contains(@class, 'Table-header')]/div[text()='Action']")
    private WebElement tableHeaderAction;

    @FindBy(xpath = "//div[@class='Table-row']/div[text()]")
    private List<WebElement> reportOptions;

    public VNextBOReportsPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getReportTable() {
        return reportsBlock.findElement(By.className("PageBody"));
    }

    public List<WebElement> getTableColumns() {
        return getReportTable().findElements(By.xpath(".//div[@class='Table-row']"));
    }

    public WebElement getGenerateButtonForReport(String report) {
        return getTableColumns().stream().map(row -> {
            if (Utils.getText(row.findElement(By.xpath(".//div"))).equals(report)) {
                return row.findElement(By.xpath(".//button"));
            }
            return null;
        }).findFirst().orElseThrow(() ->
                new RuntimeException("The 'Generate Report' button hasn't been found for the " + report + "."));
    }
}
