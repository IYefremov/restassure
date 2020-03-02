package com.cyberiansoft.test.vnext.screens.monitoring;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class ProblemReasonPage extends MonitorScreen {

    @FindBy(xpath = "//div[@data-page=\"problems\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//textarea[@name=\"entityProblem\"]")
    private WebElement problemEditBox;

    @FindBy(xpath = "//*[@action=\"save\"]")
    private WebElement completeButton;

}
