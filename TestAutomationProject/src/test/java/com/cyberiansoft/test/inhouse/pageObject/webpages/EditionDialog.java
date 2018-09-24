package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public abstract class EditionDialog extends BasePage {

    EditionDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public abstract EditionDialog clickAddDiscountButton();

    public abstract EditionDialog clickRecommendedCheckbox();

    public abstract EditionDialog selectRandomMappingIBSservice();

    public abstract EditionDialog typePrice(String price);

    public abstract EditionDialog typeEditionName(String name);

    public abstract EditionDialog typeMinimumLicense(String license);

    public abstract EditionDialog clickSubmitDiscountButton();

    public abstract boolean areMinimumCommitmentValuesDisplayed(List<String> textList);

    public abstract boolean areNewPriceValuesDisplayed(List<String> textList);

    @Step
    boolean areValuesDisplayed(List<String> values, List<String> listText) {
        try {
            return values
                    .stream()
                    .limit(3)
                    .collect(Collectors.toList())
                    .stream()
                    .anyMatch(listText::contains);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    List<String> getValues(List<WebElement> newPriceValues) {
        return newPriceValues
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step
    List<String> getEditedInputTestData(List<String> textList) {
        return textList
                .stream()
                .map(e -> e.replaceFirst("[.]\\d*", ""))
                .collect(Collectors.toList());
    }
}