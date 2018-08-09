package com.cyberiansoft.test.inhouse.pageObject.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class AddEditionDialog extends BasePage {

    @FindBy(xpath = "//div[@class='form-dialog add-edition-dialog active']")
    private WebElement addEditionDialogOpened;

    @FindBy(xpath = "//div[@class='form-dialog add-edition-dialog']")
    private WebElement addEditionDialogClosed;

    @FindBy(xpath = "//input[@class='form-control valid' and @id='Name']")
    private WebElement nameTextField;

    @FindBy(xpath = "//input[@class='form-control valid' and @id='PricePerMonth']")
    private WebElement priceTextField;

    @FindBy(xpath = "//select[@class='form-control mapping-ibs-service-select valid']")
    private WebElement mappingIBSservice;

    @FindBy(xpath = "//input[@class='valid' and @id='Recommended']")
    private WebElement recommendedCheckbox;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//button[@class='btn btn-outline btn-submit']")
    private WebElement addEditionSubmitButton;

    public AddEditionDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        try {
            wait.until(ExpectedConditions.visibilityOf(addEditionDialogOpened));
        } catch (Exception e) {
            Assert.fail("The add edition dialog has not been displayed", e);
        }
    }

    public AddEditionDialog typeEditionName(String name) {
        clearAndType(nameTextField, name);
        return this;
    }

    public AddEditionDialog typePrice(String price) {
        clearAndType(priceTextField, price);
        return this;
    }

    public AddEditionDialog selectRandomMappingIBSservice() {
        selectRandomValue(mappingIBSservice);
        return this;
    }

    public AddEditionDialog clickRecommendedCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(recommendedCheckbox)).click();
        return this;
    }

    public PricingPage clickAddEditionSubmitButton() {
        clickButton(addEditionSubmitButton);
        waitForLoading();
        wait.until(ExpectedConditions.visibilityOf(addEditionDialogClosed));
        return PageFactory.initElements(driver, PricingPage.class);
    }
}
