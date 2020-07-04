package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.ApproveInspectionListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextApproveInspectionsScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='approve-inspections']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='select-all' and @value='1']")
    private WebElement approveAllBtn;

    @FindBy(xpath = "//*[@action='select-all' and @value='3']")
    private WebElement declineAllBtn;

    @FindBy(xpath = "//*[@class='approve-inspections-container']/div")
    private List<ApproveInspectionListElement> approveInspectionsList;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement saveBtn;

    public VNextApproveInspectionsScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public boolean isInspectionExistsForApprove(String inspectionNumber) {
        return approveInspectionsList.stream().anyMatch(listElement -> listElement.getId().equals(inspectionNumber));
    }

    public ApproveInspectionListElement getInspectionElement(String inspectionId) {
        return approveInspectionsList.stream().filter(listElement -> listElement.getId().equals(inspectionId)).findFirst().orElseThrow(() -> new RuntimeException("inspection not found " + inspectionId));
    }

}
