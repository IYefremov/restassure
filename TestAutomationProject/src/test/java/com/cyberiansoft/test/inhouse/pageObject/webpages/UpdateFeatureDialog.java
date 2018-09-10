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

public class UpdateFeatureDialog extends BasePage {

    @FindBy(xpath = "//div[@class='form-dialog update-feature-dialog active']")
    private WebElement updateFeatureDialog;

    @FindBy(xpath = "//div[@class='form-dialog update-feature-dialog active']//button[@class='btn btn-outline btn-submit']")
    private WebElement updateFeatureButton;

    @FindBy(xpath = "//div[@class='form-dialog update-feature-dialog active']//button[@class='btn btn-sm blue btn-add-setup-fee']")
    private WebElement addSetupFeeButton;

    @FindBy(xpath = "//div[@class='form-dialog update-feature-dialog active']//ul[@class='dropdown-menu dropdown-menu-setup-fee-button']")
    private WebElement setupFeeDropDown;

    @FindBy(xpath = "//div[@class='form-dialog update-feature-dialog active']//div[@class='dropup']")
    private WebElement setupFeeDropDownClosed;

    @FindBy(xpath = "//input[@name='addfeatureSetupFeeName']")
    private WebElement setupFeeNameTextField;

    @FindBy(xpath = "//input[@name='addfeatureSetupFeeQuantity']")
    private WebElement setupFeeQuantityTextField;

    @FindBy(xpath = "//input[@name='addfeatureSetupFeePrice']")
    private WebElement setupFeePriceTextField;

    @FindBy(xpath = "//input[@name='addfeatureEstimatedHours']")
    private WebElement setupFeeEstimatedHoursTextField;

    @FindBy(xpath = "//button[@class='submit btn-save-add-setup-fee']")
    private WebElement setupFeeSubmitButton;

    @FindBy(xpath = "//button[@class='cancel btn-cancel']")
    private WebElement setupFeeCancelButton;

    @FindBy(xpath = "//table[@id='table-update-feature-setup-fees']//td[@class='dataTables_empty']")
    private WebElement emptySetupFeeTable;

    @FindBy(xpath = "//table[@id='table-update-feature-setup-fees']/tbody//td[position()<5]")
    private List<WebElement> setupFeeTableDataList;

    public UpdateFeatureDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        try {
            wait.until(ExpectedConditions.visibilityOf(updateFeatureDialog));
        } catch (Exception e) {
            Assert.fail("The Update Feature dialog hasn't been opened");
        }
    }

    public UpdateFeatureDialog clickAddSetupFeeButton() {
        clickButton(addSetupFeeButton);
        try {
            wait.until(ExpectedConditions.visibilityOf(setupFeeDropDown));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Step
    public UpdateFeatureDialog typeSetupFeeName(String setupFeeName) {
        clearAndType(setupFeeNameTextField, setupFeeName);
        return this;
    }

    @Step
    public UpdateFeatureDialog typeSetupFeeQuantity(String setupFeeQuantity) {
        clearAndType(setupFeeQuantityTextField, setupFeeQuantity);
        return this;
    }

    @Step
    public UpdateFeatureDialog typeSetupFeePrice(String setupFeePrice) {
        clearAndType(setupFeePriceTextField, setupFeePrice);
        return this;
    }

    @Step
    public UpdateFeatureDialog typeSetupFeeEstimatedHours(String setupFeeEstimatedHours) {
        clearAndType(setupFeeEstimatedHoursTextField, setupFeeEstimatedHours);
        return this;
    }

    @Step
    public UpdateFeatureDialog clickSetupFeeSubmitButton() {
        clickButton(setupFeeSubmitButton);
        waitForDropDownToBeClosed();
        return this;
    }

    @Step
    public UpdateFeatureDialog clickSetupFeeCancelButton() {
        clickButton(setupFeeCancelButton);
        waitForDropDownToBeClosed();
        return this;
    }

    public void waitForDropDownToBeClosed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(setupFeeDropDownClosed));
        } catch (Exception e) {
            Assert.fail("The setup fee drop down hasn't been closed", e);
        }
    }

    @Step
    public PricingPage clickUpdateFeatureButton() {
        clickButton(updateFeatureButton);
        waitForLoading();
        waitABit(2000);
        return PageFactory.initElements(driver, PricingPage.class);
    }

    @Step
    public boolean isEmptySetupFeeTableDisplayed() {
        try {
            return waitShortly.until(ExpectedConditions.visibilityOf(emptySetupFeeTable)).isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }

    @Step
    public List<WebElement> getSetupFeeTableDataElements() {
        try {
            return waitShortly.until(ExpectedConditions.visibilityOfAllElements(setupFeeTableDataList));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Step
    public List<String> getSetupFeeTableDataText() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(setupFeeTableDataList));
            return setupFeeTableDataList
                    .stream()
                    .map(WebElement::getText)
                    .map(e -> e.replace("$", "")
                            .replaceAll("[.]\\d{2}", ""))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
