package com.cyberiansoft.test.bo.webelements;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface WebTable extends IWebElement {
	
	WebElement getTableBody();
	
	List<WebElement> getTableRows();

	int getTableRowCount();
	
	List<WebElement> getTableColumns();
	
	List<WebElement> getTableColumnCells(String columnname);
	
	int getTableColumnIndex(String columnname);
	
	boolean tableColumnExists(String columnname);
}
