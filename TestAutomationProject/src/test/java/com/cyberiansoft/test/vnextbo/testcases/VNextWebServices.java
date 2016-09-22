package com.cyberiansoft.test.vnextbo.testcases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.testcases.BaseTestCase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class VNextWebServices extends BaseTestCase {
	final String USER_AGENT = "Mozilla/5.0";
	
	@Test(description = "Test Case 45496:vNext: setup configuration to run Invoice list suite")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "backofficeold.url" })
	public void testSetupConfigurationToRunInvoiceListSuite(String backofficeurl,
			String userName, String userPassword, String oldbourl) throws Exception {
		
		webdriverGotoWebPage(backofficeurl);
		

		
		System.out.println("\nTesting 1 - Send Http POST request");
		//sendPost();
		/*URL url = new URL("https://api.cyberianconcepts.com/v1/userInfo/actions/generateRegCode?email=danyl@cyberiansoft.com");
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
			System.out.println(response.toString());        
		  out.close();*/
		  
		 // ++++++++++++++

		System.out.println("\nTesting 2 - Send Http POST request");
		//sendPost();
		URL url = new URL("https://api.cyberianconcepts.com/v1/userInfo/actions/getRegCode?email=danyl@cyberiansoft.com");
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
			System.out.println(response.toString());        
		  out.close();

	}
	
	private void sendPost() throws Exception {

		String url = "https://api.cyberianconcepts.com/v1/userInfo/actions/getRegCode";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "email=danyl@cyberiansoft.com";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

}
