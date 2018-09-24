package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateEditionDialog extends EditionDialog {

    @FindBy(id = "update-edition-dialog")
    private WebElement updateEditionDialog;

    @FindBy(xpath = "//div[@id='update-edition-dialog']//button[@class='btn btn-sm blue btn-add-edition-discount']")
    private WebElement addDiscountButton;

    @FindBy(xpath = "//div[@id='update-edition-dialog']//div[@class='dropup open']")
    private WebElement discountDropupOpen;

    @FindBy(xpath = "//div[@id='update-edition-dialog']//div[@class='dropup']")
    private WebElement discountDropupClosed;

    @FindBy(xpath = "//div[@id='update-edition-dialog']//input[@name='MinCommitment']")
    private WebElement discountMinimumLicense;

    @FindBy(xpath = "//div[@id='update-edition-dialog']//input[@name='NewPrice']")
    private WebElement priceTextField;

    @FindBy(xpath = "//div[@id='update-edition-dialog']//button[@class='submit btn-save-add-edition-discount']")
    private WebElement discountSubmitButton;

    @FindBy(xpath = "//div[@id='update-edition-dialog']//button[@class='btn btn-outline btn-submit']")
    private WebElement updateEditionButton;

    @FindBy(xpath = "//table[@id='table-update-edition-discounts']//td[@class]")
    private List<WebElement> minimumLicensesValues;

    @FindBy(xpath = "//table[@id='table-update-edition-discounts']//td[2]")
    private List<WebElement> newPriceValues;

    public UpdateEditionDialog(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(updateEditionDialog));
        PageFactory.initElements(driver, this);
    }

    @Step
    @Override
    public UpdateEditionDialog clickAddDiscountButton() {
        clickButton(addDiscountButton);
        try {
            wait.until(ExpectedConditions.visibilityOf(discountDropupOpen));
        } catch (Exception e) {
            Assert.fail("The discount dropup has not been opened", e);
        }
        return this;
    }

    @Step
    @Override
    public UpdateEditionDialog clickRecommendedCheckbox() {
        return this;
    }

    @Step
    @Override
    public UpdateEditionDialog selectRandomMappingIBSservice() {
        return this;
    }

    @Step
    @Override
    public UpdateEditionDialog typePrice(String price) {
        clearAndType(priceTextField, price);
        return this;
    }

    @Step
    @Override
    public UpdateEditionDialog typeEditionName(String name) {
        return this;
    }

    @Step
    @Override
    public UpdateEditionDialog clickSubmitDiscountButton() {
        clickButton(discountSubmitButton);
        verifyDiscountDropupIsClosed();
        return this;
    }

    @Step
    public UpdateEditionDialog typeMinimumLicense(String license) {
        clearAndType(discountMinimumLicense, license);
        return this;
    }

    @Step
    private void verifyDiscountDropupIsClosed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(discountDropupClosed));
        } catch (Exception e) {
            Assert.fail("The discount dropup has not been closed", e);
        }
    }

    @Step
    public PricingPage clickUpdateEditionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(updateEditionButton)).click();
        return PageFactory.initElements(driver, PricingPage.class);
    }

    @Step
    public boolean areMinimumCommitmentValuesDisplayed(List<String> textList) {
        return areValuesDisplayed(getValues(minimumLicensesValues), textList);
    }

    @Step
    public boolean areNewPriceValuesDisplayed(List<String> textList) {
        List<String> valuesDisplayed = getValues(newPriceValues)
                .stream()
                .map(e -> e.replace("$", "")
                        .replaceAll("[.]\\d{2}", ""))
                .collect(Collectors.toList());
        return areValuesDisplayed(valuesDisplayed, getEditedInputTestData(textList));
    }
}