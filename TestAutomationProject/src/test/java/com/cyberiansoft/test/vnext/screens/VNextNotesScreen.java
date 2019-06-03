package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobilePlatform;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class VNextNotesScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-autotests-id=\"accordion-item-comment\"]")
    private WebElement rootNotesElement;

    @FindBy(xpath = "//div[@data-autotests-id=\"accordion-item-comment\"]//span[@class=\"icon-toggle\"]")
    private WebElement toggleNotes;

    @FindBy(xpath = "//textarea[@name=\"comment\"]")
    private WebElement noteEditField;

    @FindBy(xpath = "//div[@data-page='notes']")
    private WebElement servicenotessscreen;

    @FindBy(xpath = "//span[@action='clear']")
    private WebElement clearnotesbtn;

    @FindBy(xpath = "//textarea[@name='notes']")
    private WebElement notestextfld;

    @FindBy(xpath = "//div[contains(@class, 'accordion-item accordion-item-quick-notes')]")
    private WebElement quicknotescontent;

    @FindBy(xpath = "//a[@action='select-text']")
    private WebElement notestexttab;

    @FindBy(xpath = "//div[@action='take-lib']")
    private WebElement notespicturestab;

    @FindBy(xpath = "//*[@action='take-camera']")
    private WebElement notescamerabtn;

    @FindBy(xpath = "//*[@action='take-lib']")
    private WebElement notesgallerybtn;

    @FindBy(id = "notes-pictures")
    private WebElement notespicturesframe;

    public VNextNotesScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='notes']")));
        BaseUtils.waitABit(1000);
    }

    public VNextNotesScreen() {
        super(DriverBuilder.getInstance().getAppiumDriver());
        PageFactory.initElements(DriverBuilder.getInstance().getAppiumDriver(), this);
    }

    public List<WebElement> getListOfQuickNotes() {
        return quicknotescontent.findElements(By.xpath(".//div[@action='quick-note']"));
    }

    public void addQuickNote(String quicknote) {
        if (!quicknotescontent.getAttribute("class").contains("accordion-item-expanded"))
            tap(quicknotescontent);
        tap(quicknotescontent.findElement(By.xpath(".//div[@action='quick-note' and contains(text(), '" + quicknote + "')]")));
    }

    public String getSelectedNotes() {
        return notestextfld.getAttribute("value");
    }

    public void setNoteText(String notetext) {
        notestextfld.clear();
        //notestextfld.sendKeys(notetext);
        //notestextfld.click();
        notestextfld.sendKeys(notetext);
        //setValue(notestextfld, notetext);
    }

    public void clickNotesBackButton() {
        clickScreenBackButton();
    }

    public void selectNotesPicturesTab() {
        tap(notespicturestab);
    }

    public void selectNotesTextTab() {
        tap(notestexttab);
    }

    public void clickCameraIcon() {
        tap(notescamerabtn);
    }

    public void clickGalleryIcon() {
        tap(notesgallerybtn);
    }

    public void clickClearNotesButton() {
        tap(clearnotesbtn);
    }

    public void addCameraPictureToNote() {
        selectNotesPicturesTab();
        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        BaseUtils.waitABit(8000);
        //appiumdriver.pressKeyCode(AndroidKeyCode.KEYCODE_CAMERA);
        BaseUtils.waitABit(8000);
        if (appiumdriver.findElements(By.xpath("//android.widget.ImageView[contains(@resource-id,'btn_done')]")).size() > 0)
            appiumdriver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'btn_done')]")).click();
        else
            appiumdriver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'ok')]")).click();
        BaseUtils.waitABit(4000);
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        Assert.assertTrue(isPictureaddedToNote());
    }

    public boolean isPictureaddedToNote() {
        return notespicturesframe.findElement(By.xpath("./ul/li[@class='picture-item']/div")).isDisplayed();
    }

    public void addFakeImageNote() {
        if (appiumdriver instanceof JavascriptExecutor)
            ((JavascriptExecutor) appiumdriver).executeScript("$('[action=take-camera]').trigger('click:fake')");
    }

    public void clickAllowIfAppears() {
        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).size() > 0) {
            appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).click();
            BaseUtils.waitABit(1000);
            AppiumUtils.clickHardwareBackButton();
        }

        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
    }

    public void addImageToNotesFromGallery() {
        clickCameraIcon();
        if (DriverBuilder.getInstance().getMobilePlatform().name().toUpperCase().equals(MobilePlatform.ANDROID.toUpperCase())) {
            AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
            BaseUtils.waitABit(3000);
            if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).size() > 0)
                appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).click();
            else if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).size() > 0)
                appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).click();

            BaseUtils.waitABit(1000);
            if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).size() > 0)
                appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).click();
            else if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).size() > 0)
                appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).click();
            //BaseUtils.waitABit(2000);
            WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//*[@class='GLButton' and @text='Shutter']")));
            wait = new WebDriverWait(appiumdriver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElement(MobileBy.xpath("//*[@class='GLButton' and @text='Shutter']")))).click();
            //appiumdriver.findElement(MobileBy.xpath("//*[@class='GLButton' and @text='Shutter']")).click();
            //BaseUtils.waitABit(10000);
            wait = new WebDriverWait(appiumdriver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='OK']")));
            wait = new WebDriverWait(appiumdriver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='OK']")))).click();
            //appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='OK']")).click();

            AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        } else {
            addFakeImageNote();
            BaseUtils.waitABit(3000);
        }
    }

    public int getNumberOfAddedNotesPictures() {
        if (!(servicenotessscreen.findElements(By.xpath(".//div[@class='accordion-item accordion-item-pictures accordion-item-expanded']")).size() > 0))
            tap(servicenotessscreen.findElement(By.xpath(".//div[@class='accordion-item accordion-item-pictures']")));
        return servicenotessscreen.findElement(By.xpath(".//div[@class='images-row']")).findElements(By.xpath(".//div[contains(@class, 'img-item') and @action='fullscreen']")).size();
    }

    public void deletePictureFromNotes() {
        if (!(servicenotessscreen.findElements(By.xpath(".//div[@class='accordion-item accordion-item-pictures accordion-item-expanded']")).size() > 0))
            tap(servicenotessscreen.findElement(By.xpath(".//div[@class='accordion-item accordion-item-pictures']")));
        tap(servicenotessscreen.findElement(By.xpath(".//div[@class='images-row']")).findElement(By.xpath(".//div[contains(@class, 'img-item') and @action='fullscreen']")));
        WaitUtils.click(By.xpath(".//*[@action='remove']"));
        VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
        informationdialog.clickInformationDialogRemoveButton();
    }

    public void addQuickNotes(List<String> quickNotes) {
        for (String quickNote : quickNotes) {
            if (!quicknotescontent.getAttribute("class").contains("accordion-item-expanded"))
                tap(quicknotescontent);
            appiumdriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            if (servicenotessscreen.findElements(By.xpath("//*[@action='show-more']")).size() > 0)
                tap(servicenotessscreen.findElement(By.xpath("//*[@action='show-more']")));
            appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement quickNoteCell = servicenotessscreen.findElement(By.xpath("//*[@action='quick-note' and contains(text(), '" +
                    quickNote + "')]"));
            JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
            je.executeScript("arguments[0].scrollIntoView(true);", quickNoteCell);
            tap(quickNoteCell);
        }
    }

    public void expandNote() {
        if (!rootNotesElement.getAttribute("class").contains("expanded")) {
            toggleNotes.click();
        }
    }

    public void enterNoteText(String noteText) {
        noteEditField.sendKeys(noteText);
    }
}

