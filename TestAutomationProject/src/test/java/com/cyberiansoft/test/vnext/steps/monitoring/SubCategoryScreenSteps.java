package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.SubCategoryScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class SubCategoryScreenSteps {

    public static void selectSubCategory(String subCategory) {

        WaitUtils.collectionSizeIsGreaterThan(new SubCategoryScreen().getSubCategoriesRecordsList(), 0);
        WaitUtils.click(new SubCategoryScreen().getSubCategoriesRecordsList().
                stream()
                .filter(sBCategory -> sBCategory.getRecordText().contains(subCategory))
                .findFirst().get().getRootElement());
    }
}
