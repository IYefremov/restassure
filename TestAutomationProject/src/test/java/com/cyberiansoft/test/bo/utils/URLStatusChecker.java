package com.cyberiansoft.test.bo.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Set;
 

	public class URLStatusChecker {
		 
	    private URI linkToCheck;
	    private WebDriver driver;
	    private boolean mimicWebDriverCookieState = true;
	    private boolean followRedirects = false;
	    private RequestMethod httpRequestMethod = RequestMethod.GET ;
	 
	    public URLStatusChecker(WebDriver driverObject) throws MalformedURLException, URISyntaxException {
	        this.driver = driverObject;
	    }
	 
	    /**
	     * Specify a URL that you want to perform an HTTP Status Check upon
	     *
	     * @param linkToCheck
	     * @throws MalformedURLException
	     * @throws URISyntaxException
	     */
	    public void setURIToCheck(String linkToCheck) throws MalformedURLException, URISyntaxException {
	        this.linkToCheck = new URI(linkToCheck);
	    }
	 
	    /**
	     * Specify a URL that you want to perform an HTTP Status Check upon
	     *
	     * @param linkToCheck
	     * @throws MalformedURLException
	     */
	    public void setURIToCheck(URI linkToCheck) throws MalformedURLException {
	        this.linkToCheck = linkToCheck;
	    }
	 
	    /**
	     * Specify a URL that you want to perform an HTTP Status Check upon
	     *
	     * @param linkToCheck
	     */
	    public void setURIToCheck(URL linkToCheck) throws URISyntaxException {
	        this.linkToCheck = linkToCheck.toURI();
	    }
	 
	    /**
	     * Set the HTTP Request Method (Defaults to 'GET')
	     *
	     * @param requestMethod
	     */
	    public void setHTTPRequestMethod(RequestMethod requestMethod) {
	    	
	        this.httpRequestMethod = requestMethod;
	    }
	 
	    /**
	     * Should redirects be followed before returning status code?
	     * If set to true a 302 will not be returned, instead you will get the status code after the redirect has been followed
	     * DEFAULT: false
	     *
	     * @param value
	     */
	    public void followRedirects(Boolean value) {
	        this.followRedirects = value;
	    }
	 
	    /**
	     * Perform an HTTP Status check and return the response code
	     *
	     * @return
	     * @throws IOException
	     */
	    public int getHTTPStatusCode() throws IOException {
	 
	        HttpClient client = new DefaultHttpClient();
	        BasicHttpContext localContext = new BasicHttpContext();
	        System.out.println("Mimic WebDriver cookie state: " + this.mimicWebDriverCookieState);
	        if (this.mimicWebDriverCookieState) {
	            localContext.setAttribute(ClientContext.COOKIE_STORE, mimicCookieState(this.driver.manage().getCookies()));
	        }
	        HttpRequestBase requestMethod = this.httpRequestMethod.getRequestMethod();
	        requestMethod.setURI(this.linkToCheck);
	        HttpParams httpRequestParameters = requestMethod.getParams();
	        httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS, this.followRedirects);
	        requestMethod.setParams(httpRequestParameters);
	 
	        System.out.println("Sending " + requestMethod.getMethod() + " request for: " + requestMethod.getURI());
	        HttpResponse response = client.execute(requestMethod, localContext);
	        System.out.println("HTTP " + requestMethod.getMethod() + " request status: " + response.getStatusLine().getStatusCode());
	 
	        return response.getStatusLine().getStatusCode();
	    }
	 
	    
	    /**
	     * Perform an HTTPS Status check and return the response code
	     *
	     * @return
	     * @throws IOException
	     * @throws NoSuchAlgorithmException 
	     * @throws KeyManagementException 
	     */
	    public int getHTTPSStatusCode() throws IOException, NoSuchAlgorithmException, KeyManagementException {
	    	 /*
	         *  fix for
	         *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
	         *       sun.security.validator.ValidatorException:
	         *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
	         *               unable to find valid certification path to requested target
	         */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	           new X509TrustManager() {
	              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	              }

	              public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

	              public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

	           }
	        };

	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = (hostname, session) -> true;
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	        
	        
	        URL url = this.linkToCheck.toURL();
	        
	        SSLSocketFactory sslFactory = sc.getSocketFactory();
	        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
	        conn.setSSLSocketFactory(sslFactory);
	        return conn.getResponseCode();
	        
	       /* Reader reader = new InputStreamReader(con.getInputStream());
	        while (true) {
	          int ch = reader.read();
	          if (ch==-1) {
	            break;
	          }
	          System.out.print((char)ch);
	        }*/
	   }
	    
	    
	    
	    /**
	     * Mimic the cookie state of WebDriver (Defaults to true)
	     * This will enable you to access files that are only available when logged in.
	     * If set to false the connection will be made as an anonymouse user
	     *
	     * @param value
	     */
	    public void mimicWebDriverCookieState(boolean value) {
	        this.mimicWebDriverCookieState = value;
	    }
	 
	    /**
	     * Load in all the cookies WebDriver currently knows about so that we can mimic the browser cookie state
	     *
	     * @param seleniumCookieSet
	     * @return
	     */
	    private BasicCookieStore mimicCookieState(Set seleniumCookieSet) {
	        BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();

			for (Object o : seleniumCookieSet) {
				Cookie seleniumCookie = (Cookie) o;
				BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
				duplicateCookie.setDomain(seleniumCookie.getDomain());
				duplicateCookie.setSecure(seleniumCookie.isSecure());
				duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
				duplicateCookie.setPath(seleniumCookie.getPath());
				mimicWebDriverCookieStore.addCookie(duplicateCookie);
			}
	       /* for (Cookie seleniumCookie : seleniumCookieSet) {
	            BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
	            duplicateCookie.setDomain(seleniumCookie.getDomain());
	            duplicateCookie.setSecure(seleniumCookie.isSecure());
	            duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
	            duplicateCookie.setPath(seleniumCookie.getPath());
	            mimicWebDriverCookieStore.addCookie(duplicateCookie);
	        }*/
	 
	        return mimicWebDriverCookieStore;
	    }
	}
