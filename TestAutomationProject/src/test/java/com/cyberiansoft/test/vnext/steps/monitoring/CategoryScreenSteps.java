package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.CategoryScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class CategoryScreenSteps {

    public static void selectCategory(String category) {

        WaitUtils.collectionSizeIsGreaterThan(new CategoryScreen().getCategoriesRecordsList(), 0);
        WaitUtils.click(new CategoryScreen().getCategoriesRecordsList().
                stream()
                .filter(categoryRecord -> categoryRecord.getRecordText().contains(category))
                .findFirst().get().getRootElement());
    }
}
