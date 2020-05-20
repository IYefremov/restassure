package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SignatureCanvas implements IWebElement {
    private WebElement rootElement;
    private By signatureCanvasElement = By.id("handwriting-canvas");
    private By closePickerButton = By.xpath("//*[contains(@class, 'save-answer-signature')]");

    public SignatureCanvas(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void drawSignature() {
        int centerX = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(signatureCanvasElement).getRect().getWidth()/2;
        int centerY = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(signatureCanvasElement).getRect().getHeight()/2;
        Actions touch = new Actions(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        touch.moveToElement(ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(signatureCanvasElement), 0, 0);
        touch.moveByOffset(centerX, centerY).click().build().perform();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(closePickerButton).click();
    }
}
