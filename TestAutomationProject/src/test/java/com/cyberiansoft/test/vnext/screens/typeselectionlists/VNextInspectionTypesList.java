package com.cyberiansoft.test.vnext.screens.typeselectionlists;

import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextTypeScreenContext;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextInspectionTypesList extends VNextBaseTypeSelectionList {

	@FindBy(xpath="//div[@data-page='inspections-types']")
	private WebElement rootElement;

	public VNextInspectionTypesList(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public VNextInspectionTypesList() {
	}

	public void selectInspectionType(InspectionTypes inspectionType) {
		WaitUtils.elementShouldBeVisible(rootElement, true);
		WaitUtils.waitUntilElementIsClickable(rootElement);
		selectType(inspectionType.getInspectionTypeName());
		VNextBaseWizardScreen.inspectionType = inspectionType;
		VNextBaseWizardScreen.typeScreenContext = VNextTypeScreenContext.INSPECTION;
	}
	

}
