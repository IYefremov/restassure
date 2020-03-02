package com.cyberiansoft.test.bo.webelements.impl;

import com.cyberiansoft.test.bo.webelements.VNextWebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextWebTableImpl extends AbstractWebElement implements VNextWebTable {
	
	public VNextWebTableImpl(WebElement wrappedElement) {
		super(wrappedElement);
	}
	
	@Override
    public WebElement getTableBody() {
        return wrappedElement.findElement(By.xpath("./tbody[2]"));
    }
	
	@Override
    public List<WebElement> getTableRows() {
        return wrappedElement.findElements(By.xpath("./tbody[2]/tr"));
    }
	
	@Override
    public int getTableRowCount() {
		return getTableRows().size();
    }
	
	@Override
    public List<WebElement> getTableColumns() {
        return wrappedElement.findElements(By.xpath("./tbody/tr/th"));
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
