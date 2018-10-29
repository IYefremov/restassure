package com.cyberiansoft.test.vnextbo.utils;

public class WebConstants {

	public enum TimeFrameValues {
		TIMEFRAME_WEEKTODATE("Week to date"),
		TIMEFRAME_LASTWEEK("Last Week"),
		TIMEFRAME_MONTHTODATE("Month to date"),
		TIMEFRAME_LASTMONTH("Last Month"),
		TIMEFRAME_30_DAYS("Last 30 Days"),
		TIMEFRAME_90_DAYS("Last 90 Days"),
		TIMEFRAME_YEARTODATE("Year to date"),
		TIMEFRAME_LASTYEAR("Last Year"),
		TIMEFRAME_CUSTOM("Custom");
		 
		private String name;
		 
		TimeFrameValues(final String name) {
		   this.name = name;
		}
		 
		public String getName() {
		   return name;
		}
	}

    public enum VNextBOErrorMessages {
        FIRST_NAME_IS_REQUIRED("First Name is required"),
        LAST_NAME_IS_REQUIRED("Last Name is required"),
        EMAIL_IS_REQUIRED("Email is required"),
        EMAIL_IS_INVALID("Email is not valid!"),

        PLEASE_ENTER_PASSWORD("Please enter your password!"),
        PASSWORD_SHOULD_BE_LONGER("Password should be longer than 5 symbols!"),
        PLEASE_CONFIRM_PASSWORD("Please confirm password!");

        private String errorMessage;

        VNextBOErrorMessages(final String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
