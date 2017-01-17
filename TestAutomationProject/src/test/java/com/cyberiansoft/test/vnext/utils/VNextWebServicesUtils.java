package com.cyberiansoft.test.vnext.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class VNextWebServicesUtils {

	public static String getDeviceRegistrationCode(String usermail) throws IOException {
		URL url = new URL("https://api.cyberianconcepts.com/v1/userInfo/actions/getRegCode?email=" + usermail.replace("+", "%2B"));
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		  httpCon.setDoOutput(true);
		  httpCon.setRequestMethod("POST");
		  OutputStreamWriter out = new OutputStreamWriter(
		      httpCon.getOutputStream());
		  System.out.println(httpCon.getResponseCode());
		  System.out.println(httpCon.getResponseMessage());
		  BufferedReader in = new BufferedReader(
			        new InputStreamReader(httpCon.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(usermail);
			System.out.println(response.toString());        
		  out.close();
		  return response.toString();
		
	}
	
	public static String getDevicePhoneVerificationCode(String usermail) throws IOException {
		URL url = new URL("https://api.cyberianconcepts.com/v1/userInfo/actions/getPhoneVerificationCode?email=" + usermail.replace("+", "%2B"));
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		  httpCon.setDoOutput(true);
		  httpCon.setRequestMethod("POST");
		  OutputStreamWriter out = new OutputStreamWriter(
		      httpCon.getOutputStream());
		  System.out.println(httpCon.getResponseCode());
		  System.out.println(httpCon.getResponseMessage());
		  BufferedReader in = new BufferedReader(
			        new InputStreamReader(httpCon.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(usermail);
			System.out.println(response.toString());        
		  out.close();
		  return response.toString();
		
	}
	
	public static String getVerificationCodeByPhone(String userphone) throws IOException {
		URL url = new URL("https://api.cyberianconcepts.com/v1/userInfo/actions/getVerificationCodeByPhone?phone=" + userphone.replace("+", "%2B"));
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		  httpCon.setDoOutput(true);
		  httpCon.setRequestMethod("POST");
		  OutputStreamWriter out = new OutputStreamWriter(
		      httpCon.getOutputStream());
		  System.out.println(httpCon.getResponseCode());
		  System.out.println(httpCon.getResponseMessage());
		  BufferedReader in = new BufferedReader(
			        new InputStreamReader(httpCon.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(userphone);
			System.out.println(response.toString());        
		  out.close();
		  return response.toString();
		
	}
	
	public static String deleteUserByMail(String usermail) throws IOException {
		URL url = new URL("https://api.cyberianconcepts.com/v1/users/actions/deleteUsers?pattern=" + usermail.replace("+", "%2B"));
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		  httpCon.setDoOutput(true);
		  httpCon.setRequestMethod("POST");
		  OutputStreamWriter out = new OutputStreamWriter(
		      httpCon.getOutputStream());
		  System.out.println(httpCon.getResponseCode());
		  System.out.println(httpCon.getResponseMessage());
		  BufferedReader in = new BufferedReader(
			        new InputStreamReader(httpCon.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(usermail);
			System.out.println(response.toString());        
		  out.close();
		  return response.toString();
		
	}
	
	public static String deleteClientsByMail(String clientmail) throws IOException {
		URL url = new URL("https://api.cyberianconcepts.com/v1/clients/actions/deleteClients?pattern=" + clientmail.replace("+", "%2B"));
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		  httpCon.setDoOutput(true);
		  httpCon.setRequestMethod("POST");
		  OutputStreamWriter out = new OutputStreamWriter(
		      httpCon.getOutputStream());
		  System.out.println(httpCon.getResponseCode());
		  System.out.println(httpCon.getResponseMessage());
		  BufferedReader in = new BufferedReader(
			        new InputStreamReader(httpCon.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(clientmail);
			System.out.println(response.toString());        
		  out.close();
		  return response.toString();
		
	}
}
