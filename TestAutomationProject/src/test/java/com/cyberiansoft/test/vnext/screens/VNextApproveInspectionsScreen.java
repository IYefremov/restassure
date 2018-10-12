package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextApproveInspectionsScreen extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='approve-inspections']")
    private WebElement approveservicesscreen;

    @FindBy(xpath="//*[@action='select-all' and @value='1']")
    private WebElement approveallbtn;

    @FindBy(xpath="//*[@action='select-all' and @value='3']")
    private WebElement declineallbtn;

    @FindBy(xpath="//*[@class='approve-inspections-container']")
    private WebElement approveinsplist;

    @FindBy(xpath="//*[@action='save']")
    private WebElement savebtn;

    public VNextApproveInspectionsScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='select-all' and @value='1']")));
        BaseUtils.waitABit(1000);
    }

    public boolean isInspectionExistsForApprove(String inspectionNumber) {
        boolean exists = false;
        List<WebElement> inspections = getInspectionsListForApprove();
        for (WebElement insp : inspections)
            if (insp.findElement(By.xpath(".//*[@class='checkbox-item-title']")).getText().trim().equals(inspectionNumber)) {
                exists = true;
                break;
            }
        return exists;
    }

    private List<WebElement> getInspectionsListForApprove() {
        return approveservicesscreen.findElements(By.xpath(".//*[@class='approve-inspections-container']/div"));
    }

    private WebElement getInspectionCell(String inspectionNumber) {
        WebElement inspCell = null;
        List<WebElement> inspections = getInspectionsListForApprove();
        for (WebElement insp : inspections)
            if (insp.findElement(By.xpath(".//*[@class='checkbox-item-title']")).getText().trim().equals(inspectionNumber)) {
                inspCell = insp;
                break;
            }
        return inspCell;
    }

    public boolean isApproveIconForInspectionSelected(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElement(By.xpath(".//button[contains(@class, 'status-button-group-approve')]")).
                getAttribute("class").contains("item-checked");
    }

    public boolean isInspectionServicesStatusesCanBeChanged(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return !inspCell.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-arrow-icon ')]")).
                getAttribute("class").contains("not-visible");
    }

    public VNextInspectionsScreen clickBackButton() {
        clickScreenBackButton();
        return new VNextInspectionsScreen(appiumdriver);
    }

    public void clickSaveutton() {
        tap(savebtn);
    }

    public VNextApproveServicesScreen openApproveServicesScreenForInspection(String inspectionNumber) {
        tap(approveservicesscreen.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + inspectionNumber + "']")));
        return new VNextApproveServicesScreen(appiumdriver);
    }

    public VNextDeclineReasonScreen clickInspectionDeclineButton(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        tap(inspCell.findElement(By.xpath(".//button[@value='3']")));
        return new VNextDeclineReasonScreen(appiumdriver);
    }

    public void clickInspectionApproveButton(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        tap(inspCell.findElement(By.xpath(".//button[@value='1']")));
    }

    public void clickInspectionDeleteButton(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        tap(inspCell.findElement(By.xpath(".//button[@value='2']")));
    }

    public String getInspectionTotalAmaunt(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return  inspCell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText().trim();
    }

    public String getInspectionApprovedAmaunt(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return  inspCell.findElement(By.xpath(".//div[@class='entity-item-approved-amount']")).getText().trim();
    }
}
