package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.NonNull;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOPartsDetailsPanel extends VNextBOBaseWebPage {

    @FindBy(id = "partsTable")
    private WebElement partsDetailsTable;

    @FindBy(xpath = "//div[@id='part-entity-details']//div[contains(@data-bind, 'deleteParts')]")
    private WebElement deletePartButton;

    @FindBy(xpath = "//div[contains(@class, 'all-items-toggle--checked')]")
    private WebElement partsDetailsGeneralCheckbox;

    @FindBy(xpath = "//button[contains(@data-bind, 'addLaborServices')]")
    private List<WebElement> addLaborButtons;

    @FindBy(xpath = "//button[contains(@data-bind, 'deleteLaborService')]")
    private List<WebElement> deleteLaborButtons;

    @FindBy(xpath = "//div[@id='partsTable']//button/i[contains(@class, 'icon-arrow-down2')]")
    private List<WebElement> partsArrowsDown;

    @FindBy(xpath = "//div[@id='partsTable']/div[@role='option']")
    private List<WebElement> partsDetailsOptions;

    @FindBy(xpath = "//div[contains(@data-bind, 'isExpanded') and not(contains(@style, 'display: none'))]//div[contains(@data-bind, 'partLaborServices')]/div")
    private List<WebElement> partsLaborDescriptionBlocks;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[contains(@data-bind, 'canDelete')]")
    private List<WebElement> partsCheckboxes;

    @FindBy(id = "dialogModal")
    private WebElement modalDialog;

    @FindBy(xpath = "//div[@id='partsTable']/div[1]//div[contains(@data-bind, 'isExpanded')]")
    private WebElement firstPartBlock;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalConfirmButton']")
    private WebElement confirmDeletingButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCancelButton']")
    private WebElement cancelDeletingButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCancelButton']")
    private WebElement xIconForDeletingLabor;

    @FindBy(xpath = "//div[@id='partsTable']/div[1]//input[contains(@class, 'service-oem-number-combobox') and not(@data-bind)]")
    private WebElement firstPartOEMInputField;

    public VNextBOPartsDetailsPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isPartsDetailsTableDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(partsDetailsTable));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public WebElement getRandomPartsDetailsOption() {
        final int size = getPartsDetailsOptionsListSize();
        try {
            if (size > 1) {
                final int random = RandomUtils.nextInt(1, size);
                System.out.println("Random parts details option: " + random);
                return partsDetailsOptions.get(random);
            } else if (size == 1) {
                return partsDetailsOptions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getPartsDetailsOptionsListSize() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(partsDetailsOptions));
            return partsDetailsOptions.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getPartsOrderStatusValue(WebElement order) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(order))
                    .findElement(By.xpath(".//span[contains(@class, 'service-status')]//span[@class='k-input']"))
                    .getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @NonNull
    public String getEstimatedTimeArrivalValue(WebElement order) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(order))
                    .findElement(By.xpath("//input[contains(@data-bind, 'estimatedTimeArrival')]"))
                    .getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public VNextBOPartsDetailsPanel selectPartCheckbox(int index) {
        try {
            wait.until(ExpectedConditions.visibilityOf(partsCheckboxes.get(index))).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isDeleteButtonDisplayed() {
        return isElementDisplayed(deletePartButton);
    }

    public boolean isPartsDetailsGeneralOptionChecked() {
        return isElementDisplayed(partsDetailsGeneralCheckbox);
    }

    public boolean isAddLaborButtonDisplayed(int index) {
        return isElementDisplayed(addLaborButtons.get(index));
    }

    public VNextBOAddLaborPartsDialog clickAddLaborButton(int index) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addLaborButtons.get(index))).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PageFactory.initElements(driver, VNextBOAddLaborPartsDialog.class);
    }

    public VNextBOPartsDetailsPanel clickPartsArrow(int index) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(partsArrowsDown.get(index))).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public int getNumberOfLaborBlocks() {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(partsLaborDescriptionBlocks)).size();
        } catch (Exception ignored) {
            return 0;
        }
    }

    public void deleteLastLabor() {
        final int numberOfLaborBlocks = getNumberOfLaborBlocks();
        if (numberOfLaborBlocks > 0) {
            clickDeleteLaborButton(numberOfLaborBlocks - 1)
                    .clickConfirmDeletingButton();
        }
    }

    public VNextBOPartsDetailsPanel clickDeleteLaborButton(int index) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(deleteLaborButtons.get(index))).click();
            wait.until(ExpectedConditions.visibilityOf(modalDialog));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void clickConfirmDeletingButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeletingButton)).click();
        waitForLoading();
    }

    public void clickCancelDeletingButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelDeletingButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(cancelDeletingButton));
    }

    public void clickXIconForDeletingLabor() {
        wait.until(ExpectedConditions.elementToBeClickable(xIconForDeletingLabor)).click();
        wait.until(ExpectedConditions.invisibilityOf(xIconForDeletingLabor));
    }

    public boolean isFirstPartLaborBlockExpanded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstPartBlock));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void setOEM(String oem) {
        setData(firstPartOEMInputField, oem);
    }

    public String getPartOEMValue() {
        wait.until(ExpectedConditions.visibilityOf(firstPartOEMInputField));
        return firstPartOEMInputField.getAttribute("value");
    }
}