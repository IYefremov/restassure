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
		int iterator = 1;
		List<WebElement> columns =  getTableColumns();
		for (WebElement column:columns) {
			
			try{
			if (column.getText().equals(columnname)) {
				icolumn = iterator;
				break;		
			}
			}catch(Throwable e){
				System.out.println("--------------------------------");
				System.out.println("--------------------------------");
				System.out.println("--------------------------------");
				System.out.println("--------------------------------");
				System.out.println(e.getMessage());
				System.out.println("--------------------------------");
				System.out.println("--------------------------------");
				System.out.println("--------------------------------");
				System.out.println("--------------------------------");

			}
			++iterator;
		}
		return icolumn;
    }
	
	@Override
    public boolean tableColumnExists(String columnname) {
		boolean exists = false;
		if (getTableColumnIndex(columnname) > 0)
			exists = true;
        return exists;
    }

}
