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

    public ClientSegmentsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void expandAttributesList(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[text()='" + name +
                "']/preceding::td[contains(@class, 'details-control')]")))).click();

//        driver.findElement(By.xpath("//td[text()='"+name+"']")).findElement(By.xpath("..")).
//                findElement(By.xpath("//td[@class=' details-control']")).click();
    }

    public void searchClientSegment(String client) {
        wait.until(ExpectedConditions.visibilityOf(searchField)).clear();
        searchField.sendKeys(client);
        searchButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-client-segments_processing")));
    }

    public void setAttributeValue(String attName, String attValue) {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[contains(text(),'" + attName + "')]")).findElement(By.xpath(".."))
                .findElements(By.tagName("td")).get(1).findElement(By.className("empty-attribute-value")))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElements(By.className("form-control")).get(2))).sendKeys(attValue);
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//button[@class='submit btn-save-update-client-attribute-value']")))).click();
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
}
