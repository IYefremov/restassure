package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class VNextWebServicesUtils {

	private static String getApiWebURL() {
		String apiURL="";
		EnvironmentType envType = EnvironmentType.QC;

		if (envType.equals(EnvironmentType.DEVELOPMENT))
			apiURL = VNextFreeRegistrationInfo.getInstance().getAPIStagingURL();
		else if (envType.equals(EnvironmentType.INTEGRATION))
			apiURL = VNextFreeRegistrationInfo.getInstance().getAPIIntegrationURL();
		return apiURL;
	}

	public static String getDeviceRegistrationCode(String usermail) throws IOException {
		URL url = new URL(getApiWebURL() + "userInfo/actions/getRegCode?email=" +
                usermail.replace("+", "%2B"));
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
		  StringBuilder response = new StringBuilder();

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
		URL url = new URL(getApiWebURL() + "userInfo/actions/getPhoneVerificationCode?email=" +
                usermail.replace("+", "%2B"));
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
		  StringBuilder response = new StringBuilder();

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
		URL url = new URL(getApiWebURL() + "userInfo/actions/getVerificationCodeByPhone?phone=" +
                userphone.replace("+", "%2B"));
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
		  StringBuilder response = new StringBuilder();

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
	
	public static String getProdRegCode(String phonenumber) {
		 
		String output = "";
		try {
			 HttpClient httpClient = HttpClientBuilder.create().build(); 
				HttpPost postRequest = new HttpPost(VNextFreeRegistrationInfo.getInstance().getAPIProductionURL()
						+ "users/actions/generatePhoneVerificationCode");

				StringEntity input = new StringEntity("{\"phone\":" + phonenumber + "}");
				input.setContentType("application/json");
				postRequest.setEntity(input);

				HttpResponse response = httpClient.execute(postRequest);

				BufferedReader br = new BufferedReader(
		                        new InputStreamReader((response.getEntity().getContent())));

				output = br.readLine();

			  } catch (IOException e) {

				e.printStackTrace();

			  }
		return output;

			}
	
}
