package com.cyberiansoft.test.dataprovider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.poi.ss.formula.functions.T;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JSonDataParser {
	
	public static <T>T getTestDataFromJson(JSONObject jsondata, Class<T> classOfT) {
		Gson gson= new Gson();
		return gson.fromJson(jsondata.toString(), classOfT);
	}
	
	public static <T>T getTestDataFromJson(String jsonFilePath, Class<T> classOfT) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper obj = new ObjectMapper();
		Reader reader = null;
		try {
			File filejson =
	                new File(jsonFilePath);
	    	reader = new FileReader(filejson);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj.readValue(reader, classOfT);
	}

}
