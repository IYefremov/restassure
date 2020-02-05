package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

@Getter
public class VNextVisualScreen extends VNextBaseWizardScreen {

    @FindBy(xpath = "//div[contains(@data-page, 'visual')]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@class='car-image-wrapper']/img")
    private WebElement carimage;

    @FindBy(xpath = "//div[@class='car-marker']/img")
    private WebElement carmarker;

    @FindBy(xpath = "//a[@class='floating-button color-red']")
    private WebElement adddamagesbtn;

    @FindBy(xpath = "//div[@class='list-block breakage-types']")
    private WebElement damagetypeslist;

    @FindBy(xpath = "//*[@data-tab='default']")
    private WebElement defaulttab;

    @FindBy(xpath = "//*[@data-tab='custom']")
    private WebElement customtab;

    @FindBy(xpath = "//*[@action='remove-all-breakages']")
    private WebElement removebreakagesbtn;

    public VNextVisualScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@data-page, 'visual')]")));
        BaseUtils.waitABit(1000);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
    }

    public VNextVisualScreen() {
    }

    public VNextSelectDamagesScreen clickAddServiceButton() {
        tap(adddamagesbtn);
        tap(appiumdriver.findElement(By.xpath("//*[@action='add-other']")));
        return new VNextSelectDamagesScreen(appiumdriver);
    }

    public void selectDefaultDamage(String damageType) {
        clickAddServiceButton();
        clickDefaultDamageType(damageType);
    }

    public VNextSelectDamagesScreen clickOtherServiceOption() {
        tap(damagetypeslist.findElement(By.xpath(".//span[text()='Other']")));
        return new VNextSelectDamagesScreen(appiumdriver);
    }

    public void clickCarImage() {
        int servicesAdded = getNumberOfImageMarkers();
        if (servicesAdded > 0) {
            clickCarImageSecondTime();
        } else
            tap(carimage);
    }

    //todo: rewrite method not to use elements size()
    public void clickCarImageOnRandom() {
        WebElement elem = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//img[@class='car-image']"));
        Actions act = new Actions(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        //int x = new Random().nextInt(elem.getSize().getWidth() / 2) - new Random().nextInt(elem.getSize().getWidth() / 2);
        //int y = new Random().nextInt(elem.getSize().getHeight() / 2) - new Random().nextInt(elem.getSize().getHeight() / 2);

        int addedDamages = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(By.xpath("//*[@class='car-marker']")).size()+1;
        act.moveToElement(elem).moveByOffset(-elem.getSize().getWidth()/2+addedDamages*20, 0).click().perform();
        //act.moveToElement(elem).moveByOffset(x, y).click().perform();
    }

    public void clickCarImageSecondTime() {
        WebElement elem = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//img[@class='car-image']"));
        int width = elem.getSize().getWidth();
        Actions act = new Actions(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        act.moveToElement(elem).moveByOffset(20, 20).click().perform();
    }

    public void clickCarImageACoupleTimes(int touchTimes) {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class='car-image']")));
        WebElement elem = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//img[@class='car-image']"));
        Actions act = new Actions(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (int i = 0; i < touchTimes; i++) {
            int x = new Random().nextInt(elem.getSize().width / 2);
            int y = new Random().nextInt(elem.getSize().height / 2);
            if (new Random().nextBoolean()) {
                x *= -1;
                y *= -1;
            }
            act.moveToElement(elem).moveByOffset(x, y).click().perform();
        }
    }

    public VNextServiceDetailsScreen clickCarImageMarker() {
        tap(carmarker);
        return new VNextServiceDetailsScreen(appiumdriver);
    }

    public VNextServiceDetailsScreen clickCarImageMarker(int markerItemIndex) {
        List<WebElement> markerList = appiumdriver.findElements(By.xpath("//div[@class='car-marker']/img"));
        WaitUtils.click(markerList.get(markerItemIndex));
        return new VNextServiceDetailsScreen(appiumdriver);
    }

    public void clickDamageCancelEditingButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='icon cancel-editing-button']")));
        tap(rootElement.findElement(By.xpath(".//span[@class='icon cancel-editing-button']")));
    }

    public int getNumberOfImageMarkers() {
        return appiumdriver.findElements(By.xpath("//div[@class='car-marker']/img")).size();
    }

    public VNextVisualScreen clickDefaultDamageType(String damagetype) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Actions actions = new Actions(appiumdriver);
            actions.moveToElement(defaulttab, 30, 0).click().perform();
            return true;
        });
        tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
        return new VNextVisualScreen(appiumdriver);
    }

    public void clickRemoveAllBreakagesButton() {
        tap(removebreakagesbtn);
    }
}
