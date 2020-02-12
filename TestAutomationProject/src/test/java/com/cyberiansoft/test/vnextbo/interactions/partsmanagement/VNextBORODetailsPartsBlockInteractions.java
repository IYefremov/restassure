package com.cyberiansoft.test.vnextbo.interactions.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPartsBlock;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBORODetailsPartsBlockInteractions {

    public static void updatePartsBlock(String roWindow) {
        Utils.openTab(roWindow);
        Utils.refreshPage();
    }

    public static String getPartStatusByPartName(String partName) {
        final VNextBORODetailsPartsBlock detailsPartsBlock = new VNextBORODetailsPartsBlock();
        final List<WebElement> parts = detailsPartsBlock.getPartsByName(partName);
        return Utils.getText(detailsPartsBlock.getStatus(parts).get(0));
    }

    public static String getPartNumberValueByPartName(String partName) {
        final VNextBORODetailsPartsBlock detailsPartsBlock = new VNextBORODetailsPartsBlock();
        final List<WebElement> parts = detailsPartsBlock.getPartsByName(partName);
        return Utils.getText(detailsPartsBlock.getPartNumber(parts).get(0));
    }
}