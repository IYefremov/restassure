package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextVisualScreen extends VNextBaseWizardScreen {

    @FindBy(xpath = "//div[contains(@data-page, 'visual')]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@class='car-image-wrapper']/img")
    private WebElement carImage;

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

    public VNextVisualScreen() {
    }

    public void clickAddServiceButton() {
        tap(adddamagesbtn);
        tap(appiumdriver.findElement(By.xpath("//*[@action='add-other']")));
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

    public void clickCarImageMarker() {
        tap(carmarker);
    }

    public void clickCarImageMarker(int markerItemIndex) {
        List<WebElement> markerList = appiumdriver.findElements(By.xpath("//div[@class='car-marker']/img"));
        WaitUtils.click(markerList.get(markerItemIndex));
    }

    public void clickDamageCancelEditingButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='icon cancel-editing-button']")));
        tap(rootElement.findElement(By.xpath(".//span[@class='icon cancel-editing-button']")));
    }

    public int getNumberOfImageMarkers() {
        return appiumdriver.findElements(By.xpath("//div[@class='car-marker']/img")).size();
    }

    public void clickRemoveAllBreakagesButton() {
        tap(removebreakagesbtn);
    }
}
