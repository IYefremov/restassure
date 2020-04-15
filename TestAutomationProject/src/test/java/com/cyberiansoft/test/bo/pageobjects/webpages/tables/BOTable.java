package com.cyberiansoft.test.bo.pageobjects.webpages.tables;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Setter
@Getter
public abstract class BOTable extends BaseWebPage {

    public WebElement table;

    public BOTable() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getTableRows() {
        return table.findElements(By.xpath("./tbody/tr"));
    }

    public List<WebElement> getTableColumns() {
        return table.findElements(By.xpath("./thead/tr/th"));
    }

    public int getTableColumnIndex(String columnName) {
        final List<String> columns = Utils.getText(getTableColumns());
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(columnName)) {
                return i + 1;
            }
        }
        return -1;
    }
}
