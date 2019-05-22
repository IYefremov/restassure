package com.cyberiansoft.test.vnext.screens.typeselectionlists;

import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextTypeScreenContext;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class VNextInspectionTypesList extends VNextBaseTypeSelectionList {
	
	public VNextInspectionTypesList(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public VNextInspectionTypesList() {
	}

	public void selectInspectionType(InspectionTypes inspectionType) {
		selectType(inspectionType.getInspectionTypeName());
		VNextBaseWizardScreen.inspectionType = inspectionType;
		VNextBaseWizardScreen.typeScreenContext = VNextTypeScreenContext.INSPECTION;
	}
	

}
