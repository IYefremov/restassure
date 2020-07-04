package com.cyberiansoft.test.bo.steps.tables;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.tables.BOTable;

import java.util.List;

public class BOTableSteps {

    public static <T extends BOTable> int getTableColumnIndex(T tablePage, String columnName) {
        final List<String> columns = Utils.getText(tablePage.getTableColumns());
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(columnName)) {
                return i + 1;
            }
        }
        return -1;
    }
}
