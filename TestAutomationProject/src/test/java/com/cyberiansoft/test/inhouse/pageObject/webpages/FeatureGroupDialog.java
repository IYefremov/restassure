package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FeatureGroupDialog extends BasePage {

    public FeatureGroupDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='dropdown-toggle btn-add-feature-group']/following-sibling::ul[contains(@class, 'feature-group')]//input[@name='Name']")
    private WebElement featureGroupNameField;

    @FindBy(xpath = "//div[@class='dropdown-toggle btn-add-feature-group']/following::select[1][@id='FeatureGroupStateID']")
    private WebElement featureGroupState;

    @FindBy(xpath = "//div[@class='dropdown-toggle btn-add-feature-group']/following::select[1][@id='FeatureGroupStateID']/option[@selected]")
    private WebElement featureGroupStateSelected;

    @FindBy(xpath = "//div[@class='dropdown-toggle btn-add-feature-group']/following-sibling::ul[contains(@class, 'feature-group')]//textarea[@name='MarketingInfo']")
    private WebElement featureGroupMarketingInfoField;

    @FindBy(xpath = "//button[@class='submit btn-save-feature-group']")
    private WebElement featureGroupSubmitButton;

    @Step
    public FeatureGroupDialog typeFeatureGroupName(String featureGroupName) {
        clearAndType(featureGroupNameField, featureGroupName);
        return this;
    }

    @Step
    public FeatureGroupDialog typeMarketingInfo(String marketingInfoValue) {
        clearAndType(featureGroupMarketingInfoField, marketingInfoValue);
        return this;
    }

    @Step
    public FeatureGroupDialog selectFeatureGroupState(String featureGroupStateOption) {
        selectValue(featureGroupState, featureGroupStateOption);
        return this;
    }

    @Step
    public FeatureGroupDialog clickFeatureGroupSubmitButton() {
        clickButton(featureGroupSubmitButton);
        waitForLoading();
        return this;
    }

    @Step
    public String getSelectedFeatureGroupState() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(featureGroupStateSelected)).getText();
        } catch (Exception ignored) {
            return "";
        }
    }
}