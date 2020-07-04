package com.cyberiansoft.test.bo.validations.search;

import com.cyberiansoft.test.bo.pageobjects.webpages.BOSearchPanel;

public class SearchValidations {

    public static boolean isSearchExpanded() {
        return new BOSearchPanel().getSearchPanel().getAttribute("class").contains("open");
    }
}
