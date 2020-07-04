package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularAssignTechniciansScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility = "NavigationBarItemDone")
    private IOSElement navigationBarItemDoneBtn;

    @iOSXCUITFindBy(accessibility = "NavigationBarItemClose")
    private IOSElement navigationBarItemCloseBtn;

    @iOSXCUITFindBy(accessibility = "AssignSelectedTechniciansCell")
    private IOSElement assignSelectedTechniciansCell;

    @iOSXCUITFindBy(accessibility = "AssignServicesView")
    private IOSElement assignServicesView;

    public RegularAssignTechniciansScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void clickTechniciansCell() {
        assignSelectedTechniciansCell.click();
    }

    public void selectWorkOrderToAssingTechnicians() {
        assignServicesView.findElementByAccessibilityId("AssignServiceCell").findElementByAccessibilityId("lblEntityName").click();
    }

    public void clickDoneButton() {
        navigationBarItemDoneBtn.click();
    }

    public void clickCancelButton() {
        navigationBarItemCloseBtn.click();
    }

}
