package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.MatrixServicePdrScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class MatrixServicePdrScreenInteractions {
    public static void openSize() {
        MatrixServicePdrScreen pdrScreen = new MatrixServicePdrScreen();
        WaitUtils.click(pdrScreen.getSizeField());
    }

    public static void openSeverity() {
        MatrixServicePdrScreen pdrScreen = new MatrixServicePdrScreen();
        WaitUtils.click(pdrScreen.getSeverityField());
    }

    public static void selectSize(String size) {
        MatrixServicePdrScreen pdrScreen = new MatrixServicePdrScreen();
        WaitUtils.click(pdrScreen.getListItem(size));

    }

    public static void selectSeverity(String severity) {
        MatrixServicePdrScreen pdrScreen = new MatrixServicePdrScreen();
        WaitUtils.click(pdrScreen.getListItem(severity));
    }

    public static void selectAvailableServices() {
        MatrixServicePdrScreen pdrScreen = new MatrixServicePdrScreen();
        WaitUtils.click(pdrScreen.getAvailableServices());
    }
}
