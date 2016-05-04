package com.cyberiansoft.test.ios_client.utils;

public class PricesCalculations {
	
	public static String getPriceRepresentation(String price) {
  	  String formedprice = "";
  	  final String dollarsymbol = "$";
  	  final String floatingpart = ".00";
  	  if (price.contains(".")) {
  		  if (isDouble(price))
  			  formedprice = dollarsymbol + price; 
  	  } else if (isInteger(price)) {
  		  formedprice = dollarsymbol + price + floatingpart; 
  	  } else if (price.contains("$")) {
  		  formedprice = price;
  	  }
  	  return formedprice;
    }
    
    public static boolean isInteger(String value) {
	    try { 
	        Integer.parseInt(value); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isDouble(String value) {
		try { 
	        Double.parseDouble(value); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
    }
	
	

}
