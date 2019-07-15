package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.NonNull;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;
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
    private WebElement confirmButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCancelButton']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
    private WebElement xIconButton;

    @FindBy(xpath = "//div[@id='partsTable']/div[1]//input[contains(@class, 'service-oem-number-combobox') and not(@data-bind)]")
    private WebElement firstPartOEMInputField;

    @FindBy(xpath = "//span[contains(@class, 'service-status-dropdown')]//span[contains(@class, 'k-input')]")
    private WebElement firstPartStatusInputField;

    @FindBy(xpath = "//span[contains(@class, 'service-status-dropdown')]")
    private WebElement serviceStatusListBox;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[contains(@class, 'k-list-container')]") //todo add identifier if added by developers
    private WebElement statusDropDown;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[contains(@class, 'k-list-container')]//li")
    private List<WebElement> statusListBoxOptions;

    @FindBy(xpath = "//div[@id='partsTable']/div[1]//input[contains(@data-bind, 'estimatedTimeArrival')]")
    private WebElement firstPartETAInputField;

    @FindBy(xpath = "//input[contains(@data-bind, 'estimatedTimeArrival')]/following-sibling::span[@role='button']")
    private List<WebElement> etaCalendarButtons;

    @FindBy(xpath = "//div[contains(@class, 'k-animation-container')]")
    private WebElement etaCalendarWidget;

    @FindBy(xpath = "//input[contains(@data-bind, 'estimatedTimeArrival')]")
    private WebElement etaInputField;

    @FindBy(xpath = "//td[contains(@class, 'k-today')]/a")
    private WebElement todayETADate;

    @FindBy(xpath = "//div[@data-role='calendar']//a[@aria-label='Next']")
    private WebElement calendarWidgetNextButton;

    @FindBy(xpath = "//div[@data-role='calendar']//a[@aria-label='Previous']")
    private WebElement calendarWidgetPreviousButton;

    @FindBy(xpath = "//div[@id='part-entity-details']//div[contains(@data-bind, 'addPart')]")
    private WebElement addPartButton;

    @FindBy(id = "service-instance-form")
    private WebElement addPartDialog;

    @FindBy(xpath = "//td[@role='gridcell']/a")
    private List<WebElement> calendarCells;

    @FindBy(xpath = "//td[@role='gridcell' and not(contains(@class, 'disabled'))]")
    private List<WebElement> calendarEnabledCells;

    @FindBy(xpath = "//div[@id='partsTable']//input[contains(@data-bind, 'vendorPriceFormatted')]")
    private List<WebElement> vendorPriceInputFields;

    @FindBy(xpath = "//div[contains(@data-bind, 'serviceName')]")
    private List<WebElement> partsNamesList;

    @FindBy(xpath = "//div[@id='partsTable']//input[contains(@data-bind, 'amountFormatted')]")
    private List<WebElement> priceInputFields;

    @FindBy(xpath = "//div[@id='partsTable']//input[contains(@data-bind, 'quantityFormatted')]")
    private List<WebElement> quantityInputFields;

    @FindBy(xpath = "//div[contains(@data-bind, 'isActionsButtonVisible')]")
    private List<WebElement> actionsButtons;

    @FindBy(xpath = "//div[contains(@data-bind, 'partsMenuVisible')]")
    private List<WebElement> actionsPartsMenuBlocks;

    @FindBy(xpath = "//div[contains(@data-bind, 'partsMenuVisible')]//label[text()='Duplicate']")
    private List<WebElement> duplicateActionsButtons;

    @FindBy(xpath = "//div[contains(@data-bind, 'partsMenuVisible')]//label[text()='Delete']")
    private List<WebElement> deleteActionsButtons;

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
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        waitForLoading();
    }

    public void clickCancelDeletingButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(cancelButton));
    }

    public void clickXIconForDeletingLabor() {
        wait.until(ExpectedConditions.elementToBeClickable(xIconButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(xIconButton));
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

    public VNextBOPartsDetailsPanel setStatus(String status) {
        clickStatusBox();
        selectStatus(status);
        return this;
    }

    public VNextBOPartsDetailsPanel verifyStatusIsChanged(String status) {
        setStatus(status);
        Assert.assertEquals(status, getPartStatusValue(), "The ETA value hasn't been set");
        return this;
    }

    private VNextBOPartsDetailsPanel clickStatusBox() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(serviceStatusListBox)).click();
        } catch (ElementClickInterceptedException e) {
            waitABit(1500);
            clickWithJS(serviceStatusListBox);
        }
        waitABit(1000);
        return this;
    }

    private VNextBOPartsDetailsPanel selectStatus(String status) {
        selectOptionInDropDown(statusDropDown, statusListBoxOptions, status, true);
        waitForLoading();
        return this;
    }

    public void setETADate(String etaDate, int index) {
        openETACalendarWidget(index);

        while (!areEnabledCalendarCellsDisplayed()) {
            wait.until(ExpectedConditions.elementToBeClickable(calendarWidgetNextButton)).click();
        }
        try {
            Objects.requireNonNull(getDateCellWebElement(etaDate)).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            wait.until(ExpectedConditions.invisibilityOf(etaCalendarWidget));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean areEnabledCalendarCellsDisplayed() {
        try {
            waitShort.until(ExpectedConditions.visibilityOfAllElements(calendarEnabledCells));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private WebElement getDateCellWebElement(String date) {
        try {
            waitABit(1000);
            waitShort
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfAllElements(calendarCells));
            return calendarCells
                    .stream()
                    .filter(cell -> cell.getAttribute("title").contains(date))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void openETACalendarWidget(int index) {
        wait.until(ExpectedConditions.elementToBeClickable(etaCalendarButtons.get(index))).click();
//        wait.until(ExpectedConditions.visibilityOf(etaCalendarWidget));
        waitABit(1000);
    }

    public String getPartOEMValue() {
        return wait.until(ExpectedConditions.visibilityOf(firstPartOEMInputField)).getAttribute("value");
    }

    public String getPartETAValue() {
        return wait.until(ExpectedConditions.visibilityOf(firstPartETAInputField)).getAttribute("value");
    }

    public String getPartStatusValue() {
        return wait.until(ExpectedConditions.visibilityOf(firstPartStatusInputField)).getText();
    }

    public void setVendorPrice(int order, String vendorPrice) {
        setPartValue(order, vendorPrice, vendorPriceInputFields);
    }

    public String getVendorPriceValue(int order) {
        return getPartValue(order, vendorPriceInputFields);
    }

    public void setPrice(int order, String price) {
        setPartValue(order, price, priceInputFields);
    }

    public void setQuantity(int order, String price) {
        setPartValue(order, price, quantityInputFields);
    }

    public String getPriceValue(int order) {
        return getPartValue(order, priceInputFields);
    }

    public String getQuantityValue(int order) {
        return getPartValue(order, quantityInputFields);
    }

    private void setPartValue(int order, String price, List<WebElement> inputFields) {
        wait.until(ExpectedConditions.elementToBeClickable(inputFields.get(order))).click();
        inputFields.get(order).sendKeys(price);
        wait.until(ExpectedConditions.elementToBeClickable(partsNamesList.get(order))).click();
        waitABit(500);
    }

    private String getPartValue(int order, List<WebElement> inputFields) {
        return wait.until(ExpectedConditions.visibilityOf(inputFields.get(order)))
                .getAttribute("value")
                .replace("$", "");
    }

    public VNextBOAddNewPartDialog clickAddNewPartButton() {
        clickElement(addPartButton);
        waitABit(1000);
        return PageFactory.initElements(driver, VNextBOAddNewPartDialog.class);
    }

    public boolean isAddNewPartDialogDisplayed() {
        return isElementDisplayed(addPartDialog);
    }

    public boolean isAddNewPartDialogNotDisplayed() {
        return isElementNotDisplayed(addPartDialog);
    }

    public VNextBOPartsDetailsPanel clickActionsButton(int partOrder) {
        clickElement(actionsButtons.get(partOrder));
        return this;
    }

    public VNextBOPartsDetailsPanel clickDuplicateActionsButton(int partOrder) {
        clickElement(duplicateActionsButtons.get(partOrder));
        return this;
    }

    public VNextBOPartsDetailsPanel clickDeleteActionsButton(int partOrder) {
        clickElement(deleteActionsButtons.get(partOrder));
        return this;
    }

    public boolean isActionsPartsMenuDisplayed(int partOrder) {
        return isElementDisplayed(actionsPartsMenuBlocks.get(partOrder));
    }

    public boolean isConfirmationPartDialogDisplayed() {
        return isElementDisplayed(modalDialog);
    }

    public VNextBOPartsDetailsPanel clickConfirmationPartButton() {
        clickElement(confirmButton);
        waitForLoading();
        refreshPage();
        return this;
    }

    public int getNumberOfParts() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(partsNamesList));
            return partsNamesList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getPartOrderByName(String partsName) {
        for (int i = 0; i < partsNamesList.size(); i++) {
            if (partsNamesList.get(i).getText().contains(partsName)) {
                return i;
            }
        }
        return 0;
    }
}