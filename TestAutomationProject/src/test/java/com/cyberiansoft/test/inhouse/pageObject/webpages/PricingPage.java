package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class PricingPage extends BasePage {

    @FindBy(xpath = "//a[@class='btn btn-sm btn-edit-mode']")
    private WebElement editModeButton;

    @FindBy(xpath = "//div[@class='box-body edit-mode-enabled']")
    private WebElement editModeEnabled;

    @FindBy(xpath = "//div[@class='box-body']")
    private WebElement editModeDisabled;

    @FindBy(xpath = "//span[@class='btn-add-addition btn-add-edition']")
    private WebElement addEditionButton;

    @FindBy(xpath = "//span[@class='btn-add-feature']")
    private WebElement addFeatureGroupButton;

    @FindBy(xpath = "//h3[@class='box-title']")
    private WebElement tablePricingConfigurator;

    @FindBy(xpath = "//title[text()='Support Console | Pricing']")
    private WebElement pricingPage;

    @FindBy(xpath = "//td[@class='col-feature recommended']//div[@class='btn-actions dropdown-toggle btn-add-edition-feature']")
    private List<WebElement> recommendedToggleButtons;

    @FindBy(xpath = "//td[@class='col-feature recommended']//div[@class='dropup open']")
    private WebElement recommendedEditionDropupOpen;

    @FindBy(xpath = "//td[@class='col-feature recommended']//div[@class='dropup open']//a[@class='btn-add-addon-edition-feature-inline']")
    private WebElement addAddonEditionFeatureButton;

    @FindBy(xpath = "//td[@class='col-feature recommended']//div[@class='dropup open']//div[@class='btn-row']")
    private WebElement buttonsRow;

    @FindBy(xpath = "//td[@class='col-feature recommended']//label")
    private List<WebElement> recommendedFeatureLabels;

    public PricingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public boolean labelContainsPrice(String price) {
        wait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(recommendedFeatureLabels));
        try {
            return recommendedFeatureLabels.stream().anyMatch(e -> e.getText().contains(price));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    public PricingPage clickFirstRecommendedToggle() {
        wait.until(ExpectedConditions.visibilityOfAllElements(recommendedToggleButtons));
        clickButton(recommendedToggleButtons.get(0));
        return this;
    }

    @Step
    public boolean isRecommendedEditionDropupOpened() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(recommendedEditionDropupOpen)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public EditionFeatureDialog clickAddAddonEditionFeatureButton() {
        clickButton(addAddonEditionFeatureButton);
        try {
            wait.until(ExpectedConditions.attributeContains(buttonsRow, "style", "display: none"));
        } catch (Exception ignored) {}
        return PageFactory.initElements(driver, EditionFeatureDialog.class);
    }

    @Step
    public PricingPage enableEditMode() {
        if (isEditModeDisabled()) {
            clickButton(editModeButton);
        }
        return this;
    }

    @Step
    public boolean isEditModeEnabled() {
        try {
            wait.until(ExpectedConditions.visibilityOf(editModeEnabled));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public boolean isEditModeDisabled() {
        try {
            waitShortly.until(ExpectedConditions.visibilityOf(editModeDisabled));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public AddEditionDialog clickAddEditionButton() {
        clickButton(addEditionButton);
        waitForLoading();
        return PageFactory.initElements(driver, AddEditionDialog.class);
    }

    @Step
    public AddFeatureGroupDialog clickAddFeatureGroupButton() {
        clickButton(addFeatureGroupButton);
        waitForLoading();
        return PageFactory.initElements(driver, AddFeatureGroupDialog.class);
    }

    @Step
    public boolean isRecommendedEditionDisplayed(String editionName) {
        By edition = By.xpath("//p[@class='recommended']/following-sibling::h3[text()='" + editionName + "']");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(edition));
            return true;
        } catch (Exception ignored) {
            try {
                wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(driver.findElement(edition))));
                wait.until(ExpectedConditions.visibilityOfElementLocated(edition));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Step
    public PricingPage deleteEditionIfDisplayed(String editionName) {
        try {
            WebElement edition = driver.findElement(By.xpath(".//h3[text()='" +
                    editionName + "']/preceding-sibling::div/a[@class='btn-delete-edition']"));
            if (isEditionDisplayed(edition)) {
                deleteEdition(edition);
            }
        } catch (Exception ignored) {}
        return this;
    }

    @Step
    private boolean isEditionDisplayed(WebElement edition) {
        try {
            return wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(edition))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    private void deleteEdition(WebElement edition) {
        wait.until(ExpectedConditions.elementToBeClickable(edition)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        waitForLoading();
        waitABit(2000);
    }

    @Step
    public boolean isFeatureGroupDisplayed(String featureGroupName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(tablePricingConfigurator));
        } catch (Exception ignored) {}
        try {
            return waitShortly
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(By
                            .xpath("//span[@title='" + featureGroupName + "']")))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public AddFeatureDialog clickAddForFeatureGroupDisplayed(String featureGroupName) {
        try {
            waitShortly
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(By
                                .xpath("//span[@title='" + featureGroupName + "']/following::a[@class='btn-add']")))
                    .click();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return PageFactory.initElements(driver, AddFeatureDialog.class);
    }

    @Step
    public PricingPage deleteFeatureGroupIfDisplayed(String featureGroupName) {
        if (isFeatureGroupDisplayed(featureGroupName)) {
            clickDeleteFeatureGroup(featureGroupName);
            try {
                acceptAlert();
            } catch (Exception ignored) {}
        }
        return this;
    }

    @Step
    public PricingPage cancelDeletingFeatureGroupIfDisplayed(String featureGroupName) {
        if (isFeatureGroupDisplayed(featureGroupName)) {
            clickDeleteFeatureGroup(featureGroupName);
            try {
                dismissAlert();
            } catch (Exception ignored) {}
        }
        return this;
    }

    public void clickDeleteFeatureGroup(String featureGroupName) {
        wait
                .ignoring(Exception.class)
                .until(ExpectedConditions.elementToBeClickable(driver
                        .findElement(By.xpath("//span[@title='" + featureGroupName +
                "']//following::a[@class='btn-delete btn-delete-feature-group'][1]"))))
                .click();
    }

    @Step
    public PricingPage addFeaturesToFeatureGroup(String featureGroupName, List<String> featureNames,
                                                 String featureState, List<String> marketingInfoList) {
        for (int i = 0; i < featureNames.size(); i++) {
            AddFeatureDialog addFeatureDialog = (AddFeatureDialog) clickAddForFeatureGroupDisplayed(featureGroupName)
                    .typeFeatureName(featureNames.get(i))
                    .selectFeatureState(featureState)
                    .typeMarketingInfo(marketingInfoList.get(i));
            addFeatureDialog.clickAddFeatureButton();
            Assert.assertTrue(isFeatureDisplayed(featureGroupName, featureNames.get(i)),
                    "The feature " + featureNames.get(i) + "is not displayed");
        }
        return this;
    }

    @Step
    public boolean isFeatureDisplayed(String featureGroupName, String feature) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By
                    .xpath("//span[@title='" + featureGroupName + "']//following::span[text()='" + feature + "']")))
                    .isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    public boolean isFeatureNotDisplayed(String featureGroup, String feature) {
        try {
            return waitShortly.until(ExpectedConditions.invisibilityOfElementLocated(By
                        .xpath("//span[@title='" + featureGroup + "']//following::span[text()='" + feature + "']")));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    public PricingPage deleteFeature(String featureGroup, String feature) {
        clickDeleteIfFeatureIsDisplayed(featureGroup, feature);
        try {
            acceptAlert();
        } catch (Exception ignored) {}
        return this;
    }

    public void clickDeleteIfFeatureIsDisplayed(String featureGroup, String feature) {
        if (isFeatureDisplayed(featureGroup, feature)) {
            clickDeleteFeatureButton(featureGroup, feature);
        }
    }

    @Step
    public PricingPage cancelDeletingFeature(String featureGroup, String feature) {
        clickDeleteIfFeatureIsDisplayed(featureGroup, feature);
        try {
            dismissAlert();
        } catch (Exception ignored) {}
        return this;
    }

    public void clickDeleteFeatureButton(String featureGroup, String feature) {
        try {
            wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='" + featureGroup +
                            "']//following::span[text()='" + feature + "']//following::a[@class='btn-delete btn-delete-feature']")))
                    .click();
        } catch (Exception e) {
            Assert.fail("The feature " + feature + " \"Delete\" button has been not clicked", e);
        }
    }

    @Step
    public void verifyFeaturesAreDeleted(String featureGroup, List<String> features) {
        for (String feature: features) {
            Assert.assertTrue(isFeatureNotDisplayed(featureGroup, feature),
                    "The feature " + feature + "has not been deleted");
        }
    }

    @Step
    public UpdateEditionDialog clickEditEditionButton(String editionName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By
                    .xpath("//h3[text()='" + editionName +
                            "']/preceding-sibling::div/div[@class='dropdown add-edition-cell-container']")))
                    .click();
        } catch (Exception e) {
            Assert.fail("The edition " + editionName + " hasn't been clicked", e);
        }
        return PageFactory.initElements(driver, UpdateEditionDialog.class);
    }

    @Step
    public UpdateFeatureDialog clickFeature(String featureGroup, String feature) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By
                    .xpath("//span[text()='" + featureGroup + "']//following::span[text()='" + feature + "']")))
                    .click();
        } catch (Exception e) {
            Assert.fail("The feature " + feature + " hasn't been clicked", e);
        }
        return PageFactory.initElements(driver, UpdateFeatureDialog.class);
    }

    @Step
    public FeatureGroupDialog clickFeatureGroup(String featureGroup) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By
                    .xpath("//span[@title='" + featureGroup + "']")))
                    .click();
        } catch (Exception e) {
            Assert.fail("The feature group " + featureGroup + " hasn't been clicked", e);
        }
        return PageFactory.initElements(driver, FeatureGroupDialog.class);
    }
}