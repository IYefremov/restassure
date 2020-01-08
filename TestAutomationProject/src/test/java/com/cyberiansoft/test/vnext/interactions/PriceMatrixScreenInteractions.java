package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.wizardscreens.PriceMatrixListPage;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class PriceMatrixScreenInteractions {
    public static void selectItem(String matrixName) {
        PriceMatrixListPage priceMatrixListPage = new PriceMatrixListPage();
        BaseUtils.waitABit(1000);
        WaitUtils.click(priceMatrixListPage.getElements().stream()
                .filter(element -> element.getText().equals(matrixName))
                .findFirst().orElseThrow(() -> new RuntimeException("Element not found " + matrixName)));
    }
}
