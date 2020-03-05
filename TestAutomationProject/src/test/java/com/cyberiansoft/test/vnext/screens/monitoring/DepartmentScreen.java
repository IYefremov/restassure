package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class DepartmentScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page=\"departments\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@data-autotests-id=\"departments-list\"]/div")
    private List<WebElement> departmentList;

    public void selectDepartmentByText(String department) {
        WaitUtils.click(departmentList.stream()
                .filter(locationElement -> locationElement.getText().contains(department))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Monitoring location not found: " + department)));
    }
}
