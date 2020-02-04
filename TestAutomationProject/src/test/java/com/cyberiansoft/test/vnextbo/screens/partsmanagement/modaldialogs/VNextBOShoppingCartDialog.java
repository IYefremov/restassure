package com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
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
public class VNextBOShoppingCartDialog extends VNextBOBaseWebPage {

    @FindBy(id = "parts-providers-cart-modal")
    private WebElement shoppingCartDialog;

    @FindBy(xpath = "//div[contains(@data-template, 'cart')]/div[@class='grid']")
    private List<WebElement> shoppingCartStoreBlocks;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[contains(@class, 'k-list-container')]")
    private WebElement partStatusDropDown;

    @FindBy(xpath = "//button[text()='Order']")
    private WebElement orderButton;

    public VNextBOShoppingCartDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getPriceByPartName(String partName) {
        return shoppingCartDialog.findElement(By.xpath("//span[text()='" + partName
                + "']/../../following-sibling::div[contains(@data-bind, 'formattedPriceAndCorePrice')]"));
    }

    public List<WebElement> getPartStatusListBoxOptions() {
        return partStatusDropDown.findElements(By.xpath(".//li"));
    }

    public WebElement getRepairOrderPartInputByPartName(String store, String partName) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(shoppingCartStoreBlocks, 3);
        for (WebElement shoppingCartStoreBlock : shoppingCartStoreBlocks) {
            final String storeName = Utils.getText(shoppingCartStoreBlock.findElement(By.xpath(".//div[contains(@data-bind, 'name')]")));
            if (storeName.contains(store)) {
                return shoppingCartStoreBlock.findElement(By.xpath("//span[contains(text(), '" + partName
                        + "')]/../../following-sibling::div/span[contains(@class, 'k-header')]"));
            }
        }
        return null;
    }
}
