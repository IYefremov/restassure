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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOPartsDetailsPanel extends VNextBOBaseWebPage {

    @FindBy(id = "partsTable")
    private WebElement partsDetailsTable;

    @FindBy(xpath = "//div[@id='partsTable']/div[@role='option']")
    private List<WebElement> partsDetailsOptions;

    private final DateTimeFormatter formatter;

    public VNextBOPartsDetailsPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
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

    public boolean isDateBefore(String before, String after) {
        LocalDate dateBefore = LocalDate.parse(before, formatter);
        LocalDate dateAfter = LocalDate.parse(after, formatter);
        return dateBefore.isBefore(dateAfter);
    }

    public boolean isDateAfter(String before, String after) {
        LocalDate dateBefore = LocalDate.parse(before, formatter);
        LocalDate dateAfter = LocalDate.parse(after, formatter);
        return dateBefore.isAfter(dateAfter);
    }

    public boolean isDateEqual(String before, String after) {
        LocalDate dateBefore = LocalDate.parse(before, formatter);
        LocalDate dateAfter = LocalDate.parse(after, formatter);
        return dateBefore.isEqual(dateAfter);
    }
}