package com.cyberiansoft.test.ios10_client.utils;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private static XSSFSheet TestCasesDataWSheet;
	private static XSSFSheet RetailHailDataWSheet;
	 
	private static XSSFWorkbook ExcelWBook;

	protected static String testcasecolumnname = "Test case";
	protected static String vincolumnname = "VIN";
	protected static String makecolumnname = "Make";
	protected static String modelcolumnname = "Model";
	protected static String yearcolumnname = "Year";
	protected static String stockcolumnname = "Stock";
	protected static String rocolumnname = "RO";
	
	//Prices
	protected static String serviceprice = "ServicePrice";	
	protected static String serviceprice2 = "ServicePrice2";	
	protected static String serviceprice3 = "ServicePrice3";
	protected static String serviceprice4 = "ServicePrice4";
	
	protected static String totalsumm = "TotalSumm";
	protected static String totalsumm2 = "TotalSumm2";
	
	//Retail Hail
	protected static String ownernamecolumn = "OwnerName";
	protected static String owneraddresscolumn = "OwnerAddress";
	protected static String ownercitycolumnn= "OwnerCity";
	protected static String ownerstatecolumnn = "OwnerState";
	protected static String ownerzipcolumnn= "OwnerZip";
	protected static String insurancecompanycolumnn = "InsuranceCompany";
	protected static String claimcolumnn = "Claim";
	protected static String pricematrix1column = "PriceMatrix1";
	protected static String pricematrix2column = "PriceMatrix2";
	protected static String discount1column= "Discaunt1";
	protected static String discount2column = "Discaunt2";
	protected static String discount3column = "Discaunt3";
	
	protected static FileInputStream fis;
	      
	public static void setDentWizardExcelFile() throws Exception {

		try {
			// Open the Excel file
			File file =    new File("data/DentWizardData.xlsx");
			System.out.println("Running test case " + file.getAbsolutePath());
		    fis = new FileInputStream(file);
			// Access the required test data sheet
		    ExcelWBook = new XSSFWorkbook(fis);
			TestCasesDataWSheet = ExcelWBook.getSheet("TestCasesData");
			RetailHailDataWSheet = ExcelWBook.getSheet("RetailHailData");

		} catch (Exception e){

			throw (e);
			}
		}

	public static int getColumnIndex(XSSFSheet DataWSheet, String columnname) throws Exception {
		int collnum  = -1;
		for (int i = 0; i < DataWSheet.getRow(0).getPhysicalNumberOfCells(); i++) {
			if (DataWSheet.getRow(0).getCell(i).getStringCellValue().equals(columnname)) {
				collnum = i;
				return collnum;
			}
		}
		return collnum;		
	}
	
	public static int getTestCaseRow(String testcase) throws Exception {
		int collnum  = getColumnIndex(TestCasesDataWSheet, testcasecolumnname);
		if (collnum == -1) {
			return -1;
		}
		for (int i = 0; i <= TestCasesDataWSheet.getLastRowNum(); i++) {
			if (TestCasesDataWSheet.getRow(i).getCell(collnum).getStringCellValue().equals(testcase)) {
				return i;
			}
		}
		return -1;		
	}
	
	public static int getRetailHailDataRow(String testcase) throws Exception {
		int collnum  = getColumnIndex(RetailHailDataWSheet, testcasecolumnname);
		if (collnum == -1) {
			return -1;
		}
		for (int i = 0; i <= RetailHailDataWSheet.getLastRowNum(); i++) {
			if (RetailHailDataWSheet.getRow(i).getCell(collnum).getStringCellValue().equals(testcase)) {
				return i;
			}
		}
		return -1;		
	}
	
	public static String getTestCasesData(int testcaserow, String columnname) throws Exception {
		if (testcaserow == -1) {
			return "";
		}
		if (TestCasesDataWSheet.getRow(testcaserow).getCell(getColumnIndex(TestCasesDataWSheet, columnname)).getCellType() == 1)
			return TestCasesDataWSheet.getRow(testcaserow).getCell(getColumnIndex(TestCasesDataWSheet, columnname)).getStringCellValue();
		else 
			return TestCasesDataWSheet.getRow(testcaserow).getCell(getColumnIndex(TestCasesDataWSheet, columnname)).getRawValue();
	}
	
	public static String getVIN(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, vincolumnname);
	}
	
	public static String getMake(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, makecolumnname);
	}
	
	public static String getModel(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, modelcolumnname);
	}
	
	public static String getYear(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, yearcolumnname);
	}
	
	public static String getStock(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, stockcolumnname);
	}
	
	public static String getRO(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, rocolumnname);
	}

	//================================== Prices ================
	
	public static String getServicePrice(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, serviceprice);
	}
	
	public static String getServicePrice2(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, serviceprice2);
	}
	
	public static String getServicePrice3(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, serviceprice3);
	}
	
	public static String getServicePrice4(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, serviceprice4);
	}
	
	
	public static String getTotalSumm(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, totalsumm);
	}
	
	public static String getTotalSumm2(int testcaserow) throws Exception {
		return getTestCasesData(testcaserow, totalsumm2);
	}
	
	//================================== Retail Hail Data ================
	public static String getRetailHailData(int testcaserow, String columnname) throws Exception {
		if (testcaserow == -1) {
			return "";
		}
		if (RetailHailDataWSheet.getRow(testcaserow).getCell(getColumnIndex(RetailHailDataWSheet, columnname)).getCellType() == 1)
			return RetailHailDataWSheet.getRow(testcaserow).getCell(getColumnIndex(RetailHailDataWSheet, columnname)).getStringCellValue();
		else 
			return RetailHailDataWSheet.getRow(testcaserow).getCell(getColumnIndex(RetailHailDataWSheet, columnname)).getRawValue();
	}
	
	public static String getOwnerName(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, ownernamecolumn);
	}
	
	public static String getOwnerAddress(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, owneraddresscolumn);
	}
	
	public static String getownerState(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, ownerstatecolumnn);
	}
	
	public static String getownerCity(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, ownercitycolumnn);
	}
	
	public static String getOwnerZip(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, ownerzipcolumnn);
	}
	
	public static String getInsuranceCompany(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, insurancecompanycolumnn);
	}
	
	public static String getClaim(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, claimcolumnn);
	}
	
	public static String getPriceMatrix(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, pricematrix1column);
	}
	
	public static String getPriceMatrix2(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, pricematrix2column);
	}
	
	public static String getDiscount(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, discount1column);
	}
	
	public static String getDiscount2(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, discount2column);
	}
	
	public static String getDiscount3(int testcaserow) throws Exception {
		return getRetailHailData(testcaserow, discount3column);
	}
}
