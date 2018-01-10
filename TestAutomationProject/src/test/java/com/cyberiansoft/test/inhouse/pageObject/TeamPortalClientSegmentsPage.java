package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TeamPortalClientSegmentsPage  extends BasePage {

    @FindBy(id="searchClient")
    WebElement searchField;

    @FindBy(id ="btnSearch")
    WebElement searchButton;

    public TeamPortalClientSegmentsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void expandAttributesList(String name) {
        driver.findElement(By.xpath("//td[text()='"+name+"']")).findElement(By.xpath("..")).
                findElement(By.xpath("//td[@class=' details-control']")).click();

    }

    public void searchClientSegment(String name) throws InterruptedException {
        searchField.clear();
        searchField.sendKeys(name);
        searchButton.click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("table-client-segments_processing")));
        Thread.sleep(200);
    }

    public void setAttributeValue(String attName, String attValue) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//td[contains(text(),'"+attName+"')]")).findElement(By.xpath(".."))
        .findElements(By.tagName("td")).get(1).findElement(By.className("empty-attribute-value")).click();
        Thread.sleep(500);
        driver.findElements(By.className("form-control")).get(2).sendKeys(attValue);
        driver.findElement(By.xpath("//button[@class='submit btn-save-update-client-attribute-value']")).click();
        Thread.sleep(1000);
    }

    public boolean checkAttributeValue(String attName, String attValue) throws InterruptedException {
        Thread.sleep(500);
      return  driver.findElement(By.xpath("//td[contains(text(),'"+attName+"')]")).findElement(By.xpath(".."))
                .findElements(By.tagName("td")).get(1).findElement(By.xpath("//span[@class='attribute-value truncate']"))
                .getText().equals(attValue);

    }

    public boolean checkClientyName(String clientName) throws InterruptedException {
        Thread.sleep(2000);
        return driver.findElement(By.className("sorting_1")).getText().equals(clientName);
    }
}
