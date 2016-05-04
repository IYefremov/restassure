package com.cyberiansoft.test.bo.webelements.impl;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cyberiansoft.test.bo.webelements.WebTable;

public class WebTableImpl extends AbstractWebElement implements WebTable {

	public WebTableImpl(WebElement wrappedElement) {
		super(wrappedElement);
	}
	
	@Override
    public WebElement getTableBody() {
        return wrappedElement.findElement(By.xpath("./tbody"));
    }
	
	@Override
    public List<WebElement> getTableRows() {
        return wrappedElement.findElements(By.xpath("./tbody/tr"));
    }
	
	@Override
    public int getTableRowCount() {
		if (wrappedElement.findElement(By.xpath("./tbody/tr[1]")).getAttribute("class").equals("rgNoRecords"))
			return 0;
		else
			return getTableRows().size();
    }
	
	@Override
    public List<WebElement> getTableColumns() {
        return wrappedElement.findElements(By.xpath("./thead/tr/th"));
    }
	
	@Override
    public List<WebElement> getTableColumnCells(String columnname) {
		List<WebElement> columncells = null;
		if (getTableColumnIndex(columnname) > 0)
			columncells = getTableBody().findElements(By.xpath(".//td[" + getTableColumnIndex(columnname) + "]"));
        return columncells;
    }
	
	@Override
    public int getTableColumnIndex(String columnname) {
		int icolumn = -1;
		int iterator = 0;
		List<WebElement> columns =  getTableColumns();
		for (WebElement column:columns) {
			++iterator;
			if (column.getText().equals(columnname)) {
				icolumn = iterator;
				break;		
			}
		}
		return icolumn;
    }
	
	@Override
    public boolean isTableColumnExists(String columnname) {
		boolean exists = false;
		if (getTableColumnIndex(columnname) > 0)
			exists = true;
        return exists;
    }

}
