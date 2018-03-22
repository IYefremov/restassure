package com.cyberiansoft.test.core;
	
	public enum MobilePlatform {
	    IOS_HD("ios_hd"), IOS_REGULAR("ios_regular"), ANDROID("android");

	
		private final String mobilePlatform; 
	
		MobilePlatform(final String mobilePlatform) { 
			this.mobilePlatform = mobilePlatform; 
		} 
	
		public String getMobilePlatformString() { 
			return mobilePlatform; 
		} 
	}

