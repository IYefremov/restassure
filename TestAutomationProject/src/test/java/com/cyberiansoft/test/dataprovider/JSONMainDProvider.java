package com.cyberiansoft.test.dataprovider;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;

public class JSONMainDProvider {
	
	 private static final String dataFile = "src\\test\\resources\\main-data.json";
	
	public JSONMainDProvider() throws Exception {
    }
    
    @DataProvider(name = "fetchData_JSON")
    
    public static Object[][] fetchData() throws Exception {
    	Object[][] result;
    	List<JSONObject> testDataList = new ArrayList<JSONObject>();
        
    	 JSONArray testData = (JSONArray) extractData_JSON(dataFile).get("mainData");
    	 for ( int i = 0; i < testData.size(); i++ ) {
             testDataList.add((JSONObject) testData.get(i));
         }
    	
    	 try {
             result = new Object[testDataList.size()][testDataList.get(0).size()];

             for ( int i = 0; i < testDataList.size(); i++ ) {
                 result[i] = new Object[] { testDataList.get(i) };
             }
         }

         catch(IndexOutOfBoundsException ie) {
             result = new Object[0][0];
         }

         return result;
    }
    
    public static JSONObject extractData_JSON(String file) throws Exception {
    	File filejson =
                new File(file);
    	System.out.println("+++++++" + file);
    	FileReader reader = new FileReader(filejson);
        JSONParser jsonParser = new JSONParser();

        return (JSONObject) jsonParser.parse(reader);
    }

}
