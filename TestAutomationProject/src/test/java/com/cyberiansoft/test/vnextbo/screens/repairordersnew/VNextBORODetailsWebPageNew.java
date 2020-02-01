package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBORODetailsWebPageNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='orderServices']//div[@data-item-id]/div[@class='clmn_2']/div[1]")
    private List<WebElement> serviceAndTaskDescriptionsList;

    @FindBy(xpath = "//div[@class='clmn_1']/*[@class='switchTable icon-arrow-down5']")
    private List<WebElement> serviceExpanderList;

    @FindBy(xpath = "//div[contains(@data-bind,'phaseReportProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement reportProblemActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'resolveProblemMenu') and not(contains(@style,'display: none'))]")
    private WebElement resolveProblemActionButton;

    public WebElement actionsMenuButtonByPhase(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//div[not(contains(@style,'display: none;'))]/i[@class='icon-list menu-trigger']"));
    }

    public WebElement problemIndicatorByPhase(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//i[@class='icon-problem-indicator']"));
    }

    public WebElement phaseStatusDropDownByPhase(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@data-name='" + phase + "']//span[contains(@class,'group-status-dropdown')]//span[@class='k-input']"));
    }

    public WebElement phaseStatusDropDownOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//span[text()=\"" + optionName + "\"]"));
    }

    public VNextBORODetailsWebPageNew() {

        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
