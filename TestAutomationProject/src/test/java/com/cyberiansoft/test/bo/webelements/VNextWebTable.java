package com.cyberiansoft.test.bo.webelements;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface VNextWebTable extends IWebElement {
	
	WebElement getTableBody();
	
	List<WebElement> getTableRows();

	int getTableRowCount();
	
	List<WebElement> getTableColumns();
	
	List<WebElement> getTableColumnCells(String columnname);
	
	int getTableColumnIndex(String columnname);
	
	boolean isTableColumnExists(String columnname);
}