package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAddNewTaskDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='add-task-form']//div[@class='modal-content']")
    private WebElement dialogContent;

    @FindBy(xpath = "//span[@aria-owns='add-task-form-phases-dropdown_listbox']//span[@class='k-input']")
    private WebElement phaseDropDownField;

    @FindBy(xpath = "//label[@for='add-task-form-tasks-dropdown']/following-sibling::div//span[@class='k-select']")
    private WebElement taskDropDownField;

    @FindBy(xpath = "//span[@aria-owns='add-task-form-teams-dropdown_listbox']//span[@class='k-input']")
    private WebElement teamDropDownField;

    @FindBy(xpath = "//input[@aria-owns='add-task-form-technicians-dropdown_listbox']")
    private WebElement technicianDropDownField;

    @FindBy(xpath = "//textarea[@data-bind='value: notes']")
    private WebElement noteTextarea;

    @FindBy(xpath = "//button[@data-automation-id='add-task-form-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='add-task-form']//button[@class='close']")
    private WebElement closeXIconButton;

    @FindBy(xpath = "//button[@data-automation-id='add-task-form-cancel']")
    private WebElement cancelButton;


    public WebElement dropDownOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//li[contains(text(),'" + optionName + "')]"));
    }

    public VNextBOAddNewTaskDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
