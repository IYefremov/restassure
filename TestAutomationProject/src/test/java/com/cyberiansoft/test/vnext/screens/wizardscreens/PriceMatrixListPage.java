package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PriceMatrixListPage {

    @FindBy(xpath = "//div[@data-page='parts']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id='matrices-list']//li | //div[@data-autotests-id='matrix-parts-list']/*")
    private List<WebElement> elements;

    public PriceMatrixListPage() {
        PageFactory.initElements(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), this);
    }
}
