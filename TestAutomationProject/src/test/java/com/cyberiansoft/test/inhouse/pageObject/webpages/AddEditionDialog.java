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

public class AddEditionDialog extends EditionDialog {

    @FindBy(xpath = "//div[@class='form-dialog add-edition-dialog active']")
    private WebElement addEditionDialogOpened;

    @FindBy(xpath = "//div[@class='form-dialog add-edition-dialog']")
    private WebElement addEditionDialogClosed;

    @FindBy(xpath = "//input[@class='form-control valid' and @id='Name']")
    private WebElement nameTextField;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//input[@class='form-control valid' and @id='PricePerMonth']")
    private WebElement priceTextField;

    @FindBy(xpath = "//select[@class='form-control mapping-ibs-service-select valid']")
    private WebElement mappingIBSservice;

    @FindBy(xpath = "//input[@class='valid' and @id='Recommended']")
    private WebElement recommendedCheckbox;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//button[@class='btn btn-outline btn-submit']")
    private WebElement addEditionSubmitButton;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//button[@class='btn btn-sm blue btn-add-edition-discount']")
    private WebElement addDiscountButton;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//div[@class='dropup open']")
    private WebElement discountDropupOpen;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//div[@class='dropup']")
    private WebElement discountDropupClosed;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//input[@name='MinCommitment']")
    private WebElement discountMinimumLicenses;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//input[@name='NewPrice']")
    private WebElement discountNewPrice;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//button[@class='cancel btn-cancel']")
    private WebElement discountCancelButton;

    @FindBy(xpath = "//div[@id='add-edition-dialog']//button[@class='submit btn-save-add-edition-discount']")
    private WebElement discountSubmitButton;

    @FindBy(xpath = "//table[@id='table-add-edition-discounts']//td[@class]")
    private List<WebElement> minimumLicensesValues;

    @FindBy(xpath = "//table[@id='table-add-edition-discounts']//td[2]")
    private List<WebElement> newPriceValues;

    public AddEditionDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        try {
            wait.until(ExpectedConditions.visibilityOf(addEditionDialogOpened));
        } catch (Exception e) {
            Assert.fail("The add edition dialog has not been displayed", e);
        }
    }

    @Step
    @Override
    public AddEditionDialog typeEditionName(String name) {
        clearAndType(nameTextField, name);
        return this;
    }

    @Step
    @Override
    public AddEditionDialog typePrice(String price) {
        clearAndType(priceTextField, price);
        return this;
    }

    @Step
    @Override
    public AddEditionDialog selectRandomMappingIBSservice() {
        selectRandomValue(mappingIBSservice);
        return this;
    }

    @Step
    @Override
    public AddEditionDialog clickRecommendedCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(recommendedCheckbox)).click();
        return this;
    }

    @Step
    @Override
    public AddEditionDialog clickAddDiscountButton() {
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
    public AddEditionDialog typeMinimumLicense(String license) {
        clearAndType(discountMinimumLicenses, license);
        return this;
    }

    @Step
    public AddEditionDialog typeNewPrice(String price) {
        clearAndType(discountNewPrice, price);
        return this;
    }

    @Step
    public AddEditionDialog clickCancelDiscountButton() {
        clickButton(discountCancelButton);
        verifyDiscountDropupIsClosed();
        return this;
    }

    @Step
    @Override
    public AddEditionDialog clickSubmitDiscountButton() {
        clickButton(discountSubmitButton);
        verifyDiscountDropupIsClosed();
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

    @Step
    public PricingPage clickAddEditionSubmitButton() {
        clickButton(addEditionSubmitButton);
        waitForLoading();
        wait.until(ExpectedConditions.visibilityOf(addEditionDialogClosed));
        return PageFactory.initElements(driver, PricingPage.class);
    }

    @Step
    public void addDiscounts(List<String> minCommitments, List<String> newPrices) {
        for (int i = 0; i < minCommitments.size(); i++) {
            clickAddDiscountButton()
                    .typeMinimumLicense(minCommitments.get(i))
                    .typeNewPrice(newPrices.get(i))
                    .clickSubmitDiscountButton();
            waitABit(2000);
        }
    }
}
