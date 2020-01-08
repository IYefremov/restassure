package com.cyberiansoft.test.vnext.screens.typeselectionlists;

import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextTypeScreenContext;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextWorkOrderTypesList extends VNextBaseTypeSelectionList {

	@FindBy(xpath="//div[@data-page='orders-types']")
	private WebElement rootElement;


    public VNextWorkOrderTypesList(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
	}

	public VNextWorkOrderTypesList() {
	}

	public void selectWorkOrderType(WorkOrderTypes workorderType) {
		WaitUtils.elementShouldBeVisible(rootElement, true);
		WaitUtils.waitUntilElementIsClickable(rootElement);
		selectType(workorderType.getWorkOrderTypeName());
		VNextBaseWizardScreen.typeScreenContext = VNextTypeScreenContext.WORKORDER;
		VNextBaseWizardScreen.workOrderType = workorderType;
	}
}
