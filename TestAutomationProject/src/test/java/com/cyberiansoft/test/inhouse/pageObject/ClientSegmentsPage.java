package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    //    @FindBy(id = "filterAttributeValueInput")
    @FindBy(className = "ms-sel-ctn") //todo change after the new functionality works
    private WebElement categoriesSelection;

    @FindBy(xpath = "//div[@class='ms-res-ctn dropdown-menu']/div")
    private List<WebElement> categoriesSelectionDropDown;

    @FindBy(xpath = "//td[@class='attribute-value-cell']")
    private List<WebElement> attributeValues;

    @FindBy(xpath = "//div[@class='dropup open']")
    private WebElement dropUpOpen;

    public ClientSegmentsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ClientSegmentsPage selectCategory(String category) {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesSelection)).click();
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(categoriesSelectionDropDown));
            waitABit(1000);
        } catch (Exception e) {
            waitABit(2000);
            if (categoriesSelectionDropDown.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(categoriesSelection)).click();
                wait.until(ExpectedConditions.visibilityOfAllElements(categoriesSelectionDropDown));
            }
        }
        categoriesSelectionDropDown
                .stream()
                .filter(e -> e.getText().equals(category))
                .findFirst()
                .ifPresent(WebElement::click);
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    public boolean isCategorySelected(String category) {
        try {
            wait.until(ExpectedConditions.visibilityOf(driver
                    .findElement(By.xpath("//div[@class='ms-sel-item ' and contains(text(), '" +
                            category + "')]/span")))).isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ClientSegmentsPage deselectCategory(String category) {
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//div[@class='ms-sel-item ' and contains(text(), '" + category + "')]/span"))))
                .click();
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    public ClientSegmentsPage clickSearch() {
        searchButton.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(searchButton, "disabled", "")));
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    public ClientSegmentsPage expandAttributesList(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[text()='" + name +
                    "']/preceding::td[contains(@class, 'details-control')]")))).click(); //td[text()='CompanyAutomation']/preceding::td[contains(@class, 'details-control')]
        wait.until(ExpectedConditions.visibilityOf(detailsShown));
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    public ClientSegmentsPage searchClientSegment(String client) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchField)).clear();
        } catch (Exception e) {
            refreshPage();
            wait.until(ExpectedConditions.visibilityOf(searchField)).clear();
        }
        searchField.sendKeys(client);
        clickSearch();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-client-segments_processing")));
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    public ClientSegmentsPage setAttributeValue(String attName, String attValue) {
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//td[contains(text(),'" + attName + "')]"))
                .findElement(By.xpath(".."))
                .findElements(By.tagName("td")).get(1)
                .findElement(By.className("empty-attribute-value")))).click();
        wait.until(ExpectedConditions.visibilityOf(dropUpOpen
                .findElement(By.className("form-control")))).sendKeys(attValue);
        wait.until(ExpectedConditions.elementToBeClickable(dropUpOpen
                .findElement(By.xpath("//button[@class='submit btn-save-update-client-attribute-value']")))).click();
        waitForLoading();
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    public boolean checkAttributeValue(String attName, String attValue) {
        waitABit(500);
        return driver.findElement(By.xpath("//td[contains(text(),'" + attName + "')]")).findElement(By.xpath(".."))
                .findElements(By.tagName("td")).get(1).findElement(By.xpath("//span[@class='attribute-value truncate']"))
                .getText().equals(attValue);

    }

    public boolean verifyClientIsDisplayed(String client) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(clientNameData, client));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyAttributeNameIsDisplayed(String attribute, String category) {
        try {
            List<WebElement> data = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td")));
            return data.stream().anyMatch(e -> e.getText().equals(attribute + " (" + category + ")"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyAttributeValueIsDisplayed(String attribute) {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(attributeValues))
                    .stream()
                    .anyMatch(e -> e.getText().equals(attribute));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
