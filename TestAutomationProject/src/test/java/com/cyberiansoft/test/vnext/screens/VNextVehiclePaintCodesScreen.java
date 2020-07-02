package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.PaintCodeListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextVehiclePaintCodesScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-autotests-id='colors-list']/div")
    private List<PaintCodeListItem> colorsList;

    @FindBy(xpath="//*[@class='list-empty-block-sub-header']")
    private WebElement emptyList;

    public VNextVehiclePaintCodesScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public PaintCodeListItem getPaintCodeListItemByColorName(String colorName) {
        return WaitUtils.getGeneralFluentWait().until(driver -> colorsList.stream()
                .filter(item -> item.getColorValue().equals(colorName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("color not found " + colorName)));
    }
}
