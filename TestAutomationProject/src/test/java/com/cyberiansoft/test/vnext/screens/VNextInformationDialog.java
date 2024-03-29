package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextInformationDialog extends VNextBaseScreen {

    @FindBy(xpath = "//body/div[contains(@class, 'modal-in')]")
    private WebElement modaldlg;

    @FindBy(xpath = "//div[@class='modal-text']")
    private WebElement modaldlgmsg;

    public VNextInformationDialog(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextInformationDialog() {
    }

    public boolean isInformationDialogExists() {
        return ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(By.xpath("//body/div[contains(@class, 'modal-warning')]")).size() > 0;
    }

    public String getInformationDialogMessage() {
        WaitUtils.elementShouldBeVisible(modaldlgmsg, true);
        return modaldlgmsg.getText();
    }

    public void clickInformationDialogOKButton() {
        //appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='OK']")));
        wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//span[text()='OK']"))));
        tap(modaldlg.findElement(By.xpath(".//span[text()='OK']")));
    }

    public void clickInformationDialogOKButtonUsingJs() {
        //appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='OK']")));
        wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//span[text()='OK']"))));

        WebDriver driver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", modaldlg.findElement(By.xpath(".//span[text()='OK']")));
        appiumdriver.switchTo().defaultContent();
    }

    public void clickInformationDialogYesButton() {
        tap(modaldlg.findElement(By.xpath(".//span[text()='Yes']")));
    }

    public void clickInformationDialogDontSaveButton() {
        List<WebElement> dlgbtns = modaldlg.findElements(By.xpath(".//span[@class='modal-button ']"));
        for (WebElement btn : dlgbtns)
            if (btn.getText().trim().equals("Don\'t save")) {
                tap(btn);
                break;
            }
    }

    public void clickInformationDialogSaveButton() {
        tap(modaldlg.findElement(By.xpath(".//span[text()='Save']")));
    }

    public void clickInformationDialogCancelButton() {
        tap(modaldlg.findElement(By.xpath(".//span[text()='Cancel']")));
    }

    public void clickInformationDialogNoButton() {
        tap(modaldlg.findElement(By.xpath(".//span[text()='No']")));
    }

    public void clickInformationDialogArchiveButton() {
        WaitUtils.waitUntilElementIsClickable(appiumdriver.findElement(By.xpath("//span[text()='Archive']"))).click();
    }

    public void clickInformationDialogDontArchiveButton() {
        WaitUtils.waitUntilElementIsClickable(appiumdriver.findElement(By.xpath("//span[text()='Archive']")));
        BaseUtils.waitABit(500);
        appiumdriver.findElement(By.xpath("//div[@class='modal-buttons']/span[@class='modal-button ']")).click();
        //WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        //wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']"))));
       // tap(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']")));
    }

    public void clickInformationDialogDeleteButton() {
        tap(modaldlg.findElement(By.xpath(".//span[text()='Delete']")));
    }

    public void clickInformationDialogRemoveButton() {
        tap(modaldlg.findElement(By.xpath(".//span[text()='Remove']")));
    }

    public void clickInformationDialogDontDeleteButton() {
        appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']"))));
        tap(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']")));
    }

    public String clickInformationDialogOKButtonAndGetMessage() {
        String msg = getInformationDialogMessage();
        clickInformationDialogOKButton();
        return msg;
    }

    public String clickInformationDialogOKButtonAndGetMessageUsingJs() {
        String msg = getInformationDialogMessage();
        clickInformationDialogOKButtonUsingJs();
        return msg;
    }

    public String clickInformationDialogYesButtonAndGetMessage() {
        String msg = getInformationDialogMessage();
        clickInformationDialogYesButton();
        return msg;
    }

    public String clickInformationDialogNoButtonAndGetMessage() {
        String msg = getInformationDialogMessage();
        clickInformationDialogNoButton();
        return msg;
    }

    public String clickInformationDialogDontSaveButtonAndGetMessage() {
        String msg = getInformationDialogMessage();
        clickInformationDialogCancelButton();
        return msg;
    }

    public void clickInformationDialogVoidButton() {
        appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Void']")));
        wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//span[text()='Void']"))));
        tap(modaldlg.findElement(By.xpath(".//span[text()='Void']")));
    }

    public void clickInformationDialogDontVoidButton() {
        appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']"))));
        tap(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']")));
    }

    public String clickInformationDialogVoidButtonAndGetMessage() {
        String msg = getInformationDialogMessage();
        clickInformationDialogVoidButton();
        return msg;
    }

    public String clickInformationDialogDontVoidButtonAndGetMessage() {
        String msg = getInformationDialogMessage();
        clickInformationDialogDontVoidButton();
        return msg;
    }

    public void clickInformationDialogStartSyncButton() {
        tap(modaldlg.findElement(By.xpath(".//span[text()='Start sync']")));
    }

    public String clickInformationDialogStartButton() {
        String msg = getInformationDialogMessage();
        tap(modaldlg.findElement(By.xpath(".//span[text()='Start']")));
        return msg;
    }

    public String clickInformationDialogStopButton() {
        String msg = getInformationDialogMessage();
        tap(modaldlg.findElement(By.xpath(".//span[text()='Stop']")));
        return msg;
    }

    public void clickDraftButton() {
        tap(modaldlg.findElement(By.xpath(".//span[ text()='Draft']")));
    }

    public void clickFinalButton() {
        //BaseUtils.waitABit(2000);
        WaitUtils.waitUntilElementIsClickable(modaldlg);
        WaitUtils.click(modaldlg.findElement(By.xpath(".//span[text()='Final']")));
    }

    public void clickSingleInvoiceButton() {
        tap(modaldlg.findElement(By.xpath(".//span[ text()='Single Invoice']")));
    }

    public void clickSeparateInvoicesButton() {
        tap(modaldlg.findElement(By.xpath(".//span[ text()='Separate Invoices']")));
    }

    public void clickCancelLoadingButton() {
        tap(modaldlg.findElement(By.xpath(".//span[ text()='Cancel loading']")));
    }
}
