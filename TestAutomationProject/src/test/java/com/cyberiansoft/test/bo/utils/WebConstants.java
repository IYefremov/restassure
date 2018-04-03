package com.cyberiansoft.test.bo.utils;

public class WebConstants {

	public static enum TimeFrameValues {
		TIMEFRAME_WEEKTODATE("Week to date"),
		TIMEFRAME_LASTWEEK("Last Week"),
		TIMEFRAME_MONTHTODATE("Month to date"),
		TIMEFRAME_LASTMONTH("Last Month"),
		TIMEFRAME_30_DAYS("Last 30 Days"),
		TIMEFRAME_90_DAYS("Last 90 Days"),
		TIMEFRAME_YEARTODATE("Year to date"),
		TIMEFRAME_LASTYEAR("Last Year"),
		TIMEFRAME_CUSTOM("custom");
		 
		private String name;
		 
		TimeFrameValues(final String name) {
		   this.name = name;
		}
		 
		public String getName() {
		   return name;
		}
	}	
	
	public enum InvoiceStatuses {
		INVOICESTATUS_ALL("All"),
		INVOICESTATUS_APPROVED("Approved"),
		INVOICESTATUS_DRAFT("Draft"),
		INVOICESTATUS_EXPORT_FAILED("Export Failed"),
		INVOICESTATUS_EXPORTED("Exported"),
		INVOICESTATUS_NEW("New"),
		INVOICESTATUS_VOID("Void");
		 
		private String name;
		 
		InvoiceStatuses(final String name) {
		   this.name = name;
		}
		 
		public String getName() {
		   return name;
		}
	}
}
