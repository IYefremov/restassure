package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;

import java.util.List;

public class GeneralListSteps {
    public static void selectListItems(List<String> itemList) {
        itemList.forEach(ListSelectPageInteractions::selectItem);
        ListSelectPageInteractions.saveListPage();
    }

    public static void selectListItem(String item) {
        ListSelectPageInteractions.selectItem(item);
        ListSelectPageInteractions.saveListPage();
    }
}
