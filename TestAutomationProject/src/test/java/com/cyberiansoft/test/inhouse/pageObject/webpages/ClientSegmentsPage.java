package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class ClientSegmentsPage extends BasePage {

    @FindBy(id = "searchClient")
    private WebElement searchField;

    @FindBy(id = "btnSearch")
    private WebElement searchButton;

    @FindBy(xpath = "//td[@data-col-name='Client Name']")
    private WebElement clientNameData;

    @FindBy(xpath = "//td/input[@type='checkbox']")
    private List<WebElement> clientsCheckboxes;

    @FindBy(xpath = "//i[@class='fa fa-chevron-down']")
    private WebElement detailsShown;

    @FindBy(xpath = "//i[@class='fa fa-chevron-right']")
    private WebElement detailsHidden;

    @FindBy(id = "filterAttributeValueInput")
    private WebElement categoriesSelection;

    @FindBy(xpath = "//table[@id='table-filter-client-attributes']")
    private WebElement categoriesSelectionDropDown;

    @FindBy(xpath = "//div[@id='client-segments-filter-container']//div[@class='dropdown open']")
    private WebElement categoriesSelectionOpened;

    @FindBy(xpath = "//div[@id='client-segments-filter-container']//div[@class='dropdown']")
    private WebElement categoriesSelectionHidden;

    @FindBy(xpath = "//td[@class='attribute-value-cell']")
    private List<WebElement> attributeValues;

    @FindBy(xpath = "//div[@class='dropup open']")
    private WebElement dropUpOpen;

    @FindBy(xpath = "//button[@class='submit btn-save-update-client-attribute-value']")
    private WebElement dropUpOpenSubmitButton;

    @FindBy(xpath = "//div[@class='dropup-child open']")
    private WebElement dropupAttributeOpen;

    @FindBy(xpath = "//div[@class='dropup-child open']//button[@class='submit btn-save-update-filter-client-attribute-value']")
    private WebElement dropupAttributeSubmitButton;

    @FindBy(id = "btnSearchFilterAttributesPopupTitle")
    private WebElement attributeSearchButton;

    @FindBy(id = "btnClearFilterAttributes")
    private WebElement attributeClearButton;

    @FindBy(id = "btnCloseFilterAttributesPopupTitle")
    private WebElement attributeCloseButton;

    @FindBy(xpath = "//div[@id='client-segments-filter-container']//a[@class='empty-attribute-value']")
    private List<WebElement> emptyAttributeValuesList;

    public ClientSegmentsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public ClientSegmentsPage selectAttribute(String attributeName, String attributeValue) {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesSelection)).click();
        wait.until(ExpectedConditions.visibilityOf(categoriesSelectionOpened));
        wait.until(ExpectedConditions
                .elementToBeClickable(categoriesSelectionDropDown
                        .findElement(By.xpath("//span[contains(text(), '"+ attributeName + " ')]"))
                        .findElement(By.xpath(".//following::a[1]")))).click();
        wait.until(ExpectedConditions.visibilityOf(dropupAttributeOpen))
                .findElement(By.className("form-control")).sendKeys(attributeValue);
        wait.until(ExpectedConditions.elementToBeClickable(dropupAttributeSubmitButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(attributeSearchButton)).click();
        wait.until(ExpectedConditions.visibilityOf(categoriesSelectionHidden));
        return this;
    }

    @Step
    public ClientSegmentsPage deselectAttribute() {
        waitABit(500);
        wait.until(ExpectedConditions.elementToBeClickable(categoriesSelection)).click();
        wait.until(ExpectedConditions.visibilityOf(categoriesSelectionOpened));
        int sizeBeforeDeselection = wait.until(ExpectedConditions.visibilityOfAllElements(emptyAttributeValuesList)).size();
        wait.until(ExpectedConditions.elementToBeClickable(attributeClearButton)).click();
        Assert.assertTrue(wait.until(e -> emptyAttributeValuesList.size() != sizeBeforeDeselection),
                "The attribute values have not been cleared");
        wait.until(ExpectedConditions.elementToBeClickable(attributeCloseButton)).click();
        wait.until(ExpectedConditions.visibilityOf(categoriesSelectionHidden));
        return this;
    }

    @Step
    public ClientSegmentsPage clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(searchButton, "disabled", "")));
        return this;
    }

    @Step
    public ClientSegmentsPage expandAttributesList(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[text()='" + name +
                    "']/preceding::td[contains(@class, 'details-control')]")))).click(); //td[text()='CompanyAutomation']/preceding::td[contains(@class, 'details-control')]
        wait.until(ExpectedConditions.visibilityOf(detailsShown));
        return this;
    }

    @Step
    public ClientSegmentsPage searchClientSegment(String client) {
        refreshPage();
        wait.until(ExpectedConditions.visibilityOf(searchField)).clear();
        searchField.sendKeys(client);
        clickSearch();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-client-segments_processing")));
        return this;
    }

    @Step
    public ClientSegmentsPage setAttributeValue(String attributeName, String attributeValue) {
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//td[contains(text(),'" + attributeName + "')]"))
                .findElement(By.xpath(".."))
                .findElements(By.tagName("td")).get(1)
                .findElement(By.className("empty-attribute-value")))).click();
        wait.until(ExpectedConditions.visibilityOf(dropUpOpen
                .findElement(By.className("form-control")))).sendKeys(attributeValue);
        wait.until(ExpectedConditions.elementToBeClickable(dropUpOpenSubmitButton)).click();
        waitForLoading();
        return this;
    }

    @Step
    public boolean isAttributeValueDisplayed(String attributeName, String attributeValue) {
        return wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//td[contains(text(),'" + attributeName + "')]/../td[2]//span"))))
                .getText()
                .equals(attributeValue);
    }

    @Step
    public boolean checkAttributeValue(String attName, String attValue) {
        waitABit(500);
        return driver.findElement(By.xpath("//td[contains(text(),'" + attName + "')]")).findElement(By.xpath(".."))
                .findElements(By.tagName("td")).get(1).findElement(By.xpath("//span[@class='attribute-value truncate']"))
                .getText().equals(attValue);

    }

    @Step
    public boolean verifyClientIsDisplayed(String client) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(clientNameData, client));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public boolean verifyAttributeNameIsDisplayed(String attribute, String category) {
        try {
            List<WebElement> data = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td")));
            return data.stream().anyMatch(e -> e.getText().equals(attribute + " (" + category + ")"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
