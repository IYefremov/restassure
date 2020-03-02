package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

@Getter
public class DatePicker implements IWebElement {
    private WebElement rootElement;
    private By pickerDateColumns = By.xpath("//*[contains(@class, 'picker-items-col-absolute')]");
    private String elementsLocator = ".//*[@class=\"picker-item\"]";
    private By closePickerButton = By.xpath("//a[@class='link close-picker']");

    public DatePicker(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void selectDateOption(int month, int date, int year) {
        WaitUtils.click(rootElement);
        WaitUtils.getGeneralFluentWait().until((webdriver) -> webdriver.findElements(pickerDateColumns).size() > 0);
        BaseUtils.waitABit(500);
        if (!ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(pickerDateColumns).get(0)
        .findElement(By.xpath(".//*[@class='picker-item picker-selected']")).getAttribute("data-picker-value").equals(String.valueOf(month-1))) {
            WebElement monthElement = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(pickerDateColumns).get(0)
                    .findElements(By.xpath(elementsLocator))
                    .stream()
                    .filter(element -> element.getAttribute("data-picker-value").equals(String.valueOf(month - 1)))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("element not found in list " + month));
            ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                    .executeScript("arguments[0].click();", monthElement);
        }
        if (!ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(pickerDateColumns).get(1)
                .findElement(By.xpath(".//*[@class='picker-item picker-selected']")).getAttribute("data-picker-value").equals(String.valueOf(date))) {
            WebElement dateElement = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(pickerDateColumns).get(1)
                    .findElements(By.xpath(elementsLocator))
                    .stream()
                    .filter(element -> element.getAttribute("data-picker-value").equals(String.valueOf(date)))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("element not found in list " + date));
            ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                    .executeScript("arguments[0].click();", dateElement);
        }
        if (!ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(pickerDateColumns).get(2)
                .findElement(By.xpath(".//*[@class='picker-item picker-selected']")).getAttribute("data-picker-value").equals(String.valueOf(year))) {
            WebElement yearElement = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(pickerDateColumns).get(2)
                    .findElements(By.xpath(elementsLocator))
                    .stream()
                    .filter(element -> element.getAttribute("data-picker-value").equals(String.valueOf(year)))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("element not found in list " + year));
            ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                    .executeScript("arguments[0].click();", yearElement);
        }
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(closePickerButton).click();
        BaseUtils.waitABit(500);
    }
}
